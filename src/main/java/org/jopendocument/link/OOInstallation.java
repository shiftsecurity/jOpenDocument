/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.link;

import org.jopendocument.util.DesktopEnvironment;
import org.jopendocument.util.DesktopEnvironment.Mac;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class finds out where OpenOffice.org is installed.
 * 
 * @author Sylvain CUAZ
 * @see #getInstance()
 */
public class OOInstallation {

    private static OOInstallation instance;

    /**
     * Return the installation for this machine.
     * 
     * @return the installation for this machine, <code>null</code> if not installed.
     * @throws IOException if an error occurs while searching.
     */
    public static OOInstallation getInstance() throws IOException {
        // since null means never detected or not installed, this will keep searching when not
        // installed
        if (instance == null) {
            instance = detectInstall();
        }
        return instance;
    }

    /**
     * Forget the current installation to pick up a change (e.g. updated version).
     */
    public static void reset() {
        instance = null;
    }

    // UREINSTALLLOCATION REG_SZ C:\Program Files\OpenOffice.org 3\URE\
    // \1 is the name, \2 the value
    // cannot use \p{} since some names/values can be non-ASCII
    private static final Pattern stringValuePattern = Pattern.compile("^\\s*(.+?)\\s+REG_SZ\\s+(.+?)$", Pattern.MULTILINE);

    private static final String LOBundleID = "org.libreoffice.script";
    private static final String OOBundleID = "org.openoffice.script";

    // return the standard out (not the standard error)
    private static String cmdSubstitution(String... args) throws IOException {
        final ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(false);
        return DesktopEnvironment.cmdSubstitution(pb.start());
    }

    static final URL toURL(final File f) {
        try {
            return f.toURI().toURL();
        } catch (MalformedURLException e) {
            // shouldn't happen since constructed from a file
            throw new IllegalStateException("Couldn't transform to URL " + f, e);
        }
    }

    // handle windows x64
    private static String findRootPath() {
        final String[] rootPaths = { "HKEY_LOCAL_MACHINE\\SOFTWARE\\LibreOffice", "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\LibreOffice", "HKEY_LOCAL_MACHINE\\SOFTWARE\\OpenOffice.org",
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\OpenOffice.org" };
        for (final String p : rootPaths) {
            if (DesktopEnvironment.test("reg", "query", p))
                return p;
        }
        return null;
    }

    private static File findBundleDir() throws IOException {
        final Mac de = (Mac) DesktopEnvironment.getDE();
        for (final String bundleID : new String[] { LOBundleID, OOBundleID }) {
            final File url = de.getAppDir(bundleID);
            if (url != null)
                return url;
        }
        return null;
    }

    // all string values for the passed registry path
    private static Map<String, String> getStringValues(final String path, final String option) throws IOException {
        final Map<String, String> values = new HashMap<String, String>();
        final String out = DesktopEnvironment.cmdSubstitution(Runtime.getRuntime().exec(new String[] { "reg", "query", path, option }));
        final Matcher matcher = stringValuePattern.matcher(out);
        while (matcher.find()) {
            values.put(matcher.group(1), matcher.group(2));
        }
        return values;
    }

    // add jar directories for OpenOffice 2 or 3
    private static final void addPaths(final List<File> cp, final File progDir, final String basisDir, final String ureDir) throws IOException {
        // oo2
        // all in C:\Program Files\OpenOffice.org 2.4\program\classes
        add(cp, new File(progDir, "classes"));

        // oo3
        // some in C:\Program Files\OpenOffice.org 3\URE\java
        // the rest in C:\Program Files\OpenOffice.org 3\Basis\program\classes
        if (ureDir != null) {
            // Windows
            add(cp, ureDir + File.separator + "java");
            // MacOS and Linux
            add(cp, ureDir + File.separator + "share" + File.separator + "java");
        }
        if (basisDir != null)
            add(cp, basisDir + File.separator + "program" + File.separator + "classes");
    }

    private static final void addUnixPaths(final List<File> cp, final File progDir) throws IOException {
        final File baseDir = progDir.getParentFile();
        final File basisDir = new File(baseDir, "basis-link");
        // basis-link was dropped from LO 3.5
        final String basisPath = (basisDir.exists() ? basisDir : baseDir).getPath();
        final String ureDir = basisPath + File.separator + "ure-link";
        addPaths(cp, progDir, basisPath, ureDir);
    }

    private static void add(final List<File> res, final File f) {
        // e.g. on LO 3.5 BASIS is no longer 'Basis/' but './'
        if (f != null && f.isDirectory() && !res.contains(f)) {
            res.add(f);
        }
    }

    private static void add(final List<File> res, final String f) {
        if (f != null)
            add(res, new File(f));
    }

    private static OOInstallation detectInstall() throws IOException {
        final File exe;
        final List<File> cp = new ArrayList<File>(3);

        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            final String rootPath = findRootPath();
            // not installed
            if (rootPath == null)
                return null;
            final boolean libreOffice = rootPath.contains("LibreOffice");

            // Only the default value so pass '/ve'
            final Map<String, String> unoValues = getStringValues(rootPath + "\\UNO\\InstallPath", "/ve");
            if (unoValues.size() != 1)
                throw new IOException("No UNO install path: " + unoValues);
            // e.g. C:\Program Files\OpenOffice.org 2.4\program
            final File unoPath = new File(unoValues.values().iterator().next());
            if (!unoPath.isDirectory())
                throw new IOException(unoPath + " is not a directory");
            exe = new File(unoPath, "soffice.exe");

            // Perhaps check out parallel install but in Windows it's really cumbersome :
            // http://wiki.documentfoundation.org/Installing_in_parallel

            final String layerPath;
            if (!libreOffice) {
                layerPath = "\\Layers\\OpenOffice.org";
            } else if (DesktopEnvironment.test("reg", "query", rootPath + "\\Layers")) {
                layerPath = "\\Layers\\LibreOffice";
            } else {
                // LO 3.4
                layerPath = "\\Layers_\\LibreOffice";
            }
            // '/s' since variables are one level (the version) deeper
            final Map<String, String> layersValues = getStringValues(rootPath + layerPath, "/s");
            addPaths(cp, unoPath, layersValues.get("BASISINSTALLLOCATION"), layersValues.get("UREINSTALLLOCATION"));
        } else if (os.startsWith("Mac OS")) {
            final File appPkg = findBundleDir();
            if (appPkg == null)
                return null;
            // need to call soffice from the MacOS directory otherwise it fails
            exe = new File(appPkg, "Contents/MacOS/soffice");
            addUnixPaths(cp, new File(appPkg, "Contents/program"));
        } else if (os.startsWith("Linux")) {
            // soffice is usually a symlink in /usr/bin
            // if not found prints nothing at all
            final String binPath = cmdSubstitution("which", "soffice").trim();
            if (binPath.length() != 0) {
                exe = new File(binPath).getCanonicalFile();
            } else {
                // e.g. Ubuntu 6.06
                final File defaultInstall = new File("/usr/lib/openoffice/program/soffice");
                exe = defaultInstall.canExecute() ? defaultInstall : null;
            }
            if (exe != null)
                addUnixPaths(cp, exe.getParentFile());
        } else
            exe = null;
        return exe == null ? null : new OOInstallation(exe, cp);
    }

    private final File executable;
    private final List<File> classpath;

    // TODO parse program/version.ini

    private OOInstallation(File executable, List<File> classpath) throws IOException {
        super();
        if (!executable.isFile())
            throw new IOException("executable not found at " + executable);

        this.executable = executable;
        this.classpath = Collections.unmodifiableList(new ArrayList<File>(classpath));
    }

    public final File getExecutable() {
        return this.executable;
    }

    public final List<File> getClasspath() {
        return this.classpath;
    }

    public final List<URL> getURLs(final Set<String> jars) {
        final int stop = this.getClasspath().size();
        final List<URL> res = new ArrayList<URL>();
        for (int i = 0; i < stop; i++) {
            final File[] foundJars = this.getClasspath().get(i).listFiles(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return jars.contains(f.getName());
                }
            });
            for (final File foundJar : foundJars) {
                res.add(toURL(foundJar));
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " exe: " + this.getExecutable() + " classpath: " + this.getClasspath();
    }

    public static void main(String[] args) {
        try {
            final OOInstallation i = getInstance();
            System.out.println(i == null ? "Not installed" : i);
        } catch (IOException e) {
            System.out.println("Couldn't detect OpenOffice.org: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
