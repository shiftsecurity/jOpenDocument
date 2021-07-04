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

import org.jopendocument.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.util.List;
import java.util.Map;
import java.util.PropertyPermission;
import java.util.WeakHashMap;

/**
 * Connexion avec une instance d'OO
 * 
 * @author Administrateur
 */
public abstract class OOConnexion {

    // weak to let go OOInstallation instances
    private static final Map<OOInstallation, ClassLoader> loaders = new WeakHashMap<OOInstallation, ClassLoader>(2);
    // TODO use OOInstallation.getVersion()
    // for now only one version (but also works with OO2)
    private static final String OO_VERSION = "3";

    protected static final int PORT = 8100;

    static {
        // needed to access .class inside a jar inside a jar.
        org.jopendocument.util.protocol.Helper.register();
    }

    static private final URL[] getURLs(final OOInstallation ooInstall) {
        final List<URL> res = ooInstall.getURLs(CollectionUtils.createSet("ridl.jar", "jurt.jar", "juh.jar", "unoil.jar"));

        final String jarName = "OO" + OO_VERSION + "-link.jar";
        final URL resource = OOConnexion.class.getResource(jarName);
        if (resource == null)
            // Did you run ant in the OO3Link project (or in ours) ?
            throw new IllegalStateException("Missing " + jarName);
        res.add(org.jopendocument.util.protocol.Helper.toJarJar(resource));

        return res.toArray(new URL[res.size()]);
    }

    static private final ClassLoader getLoader(final OOInstallation ooInstall) {
        ClassLoader res = loaders.get(ooInstall);
        if (res == null) {
            // pass our classloader otherwise the system class loader will be used. This won't work
            // in webstart since the classpath is loaded by JNLPClassLoader, thus a class loaded by
            // res couldn't refer to the classpath (e.g. this class) but only to the java library.
            res = new URLClassLoader(getURLs(ooInstall), OOConnexion.class.getClassLoader()) {
                @Override
                protected PermissionCollection getPermissions(CodeSource codesource) {
                    final PermissionCollection perms = super.getPermissions(codesource);
                    perms.add(new FilePermission(ooInstall.getExecutable().getAbsolutePath(), "execute"));
                    perms.add(new SocketPermission("localhost:" + PORT + "-" + (PORT + 10), "connect"));
                    // needed by OO jars
                    perms.add(new PropertyPermission("*", "read"));
                    // to be able to open any document
                    perms.add(new FilePermission("<<ALL FILES>>", "read"));
                    // needed by ThreadPoolExecutor.shutdown()
                    perms.add(new RuntimePermission("modifyThread"));
                    return perms;
                }
            };
            loaders.put(ooInstall, res);
        }
        return res;
    }

    /**
     * Return a connection to the default OpenOffice installation.
     * 
     * @return a connection to the default OpenOffice or <code>null</code> if none is found.
     * @throws IllegalStateException if an error occurs while searching.
     */
    static public OOConnexion create() throws IllegalStateException {
        final OOInstallation ooInstall;
        try {
            ooInstall = OOInstallation.getInstance();
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't find default OO installation", e);
        }
        return create(ooInstall);
    }

    static public OOConnexion create(final OOInstallation ooInstall) throws IllegalStateException {
        if (ooInstall == null)
            return null;
        try {
            final Class<?> c = getLoader(ooInstall).loadClass("org.jopendocument.link" + OO_VERSION + ".OOConnexion");
            return (OOConnexion) c.getConstructor(OOInstallation.class).newInstance(ooInstall);
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't create OOCOnnexion", e);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage : " + OOConnexion.class.getName() + " officeFile");
            System.out.println("Open officeFile in the default installation of LibreOffice");
            System.exit(1);
        }
        final OOConnexion conn = OOConnexion.create();
        if (conn == null)
            throw new IllegalStateException("No Office found");
        conn.loadDocument(new File(args[0]), false);
        conn.closeConnexion();
    }

    protected abstract Component loadDocumentFromURLAsync(final String url, final boolean hidden);

    /**
     * Load a document in OpenOffice.
     * 
     * @param f the file to load.
     * @param hidden <code>true</code> if no frame should be visible.
     * @return the new component.
     * @throws IOException if an error occurs.
     */
    public final Component loadDocument(final File f, final boolean hidden) throws IOException {
        if (!f.exists())
            throw new FileNotFoundException(f.getAbsolutePath());

        return loadDocumentFromURLAsync(convertToUrl(f.getAbsolutePath()), hidden);
    }

    protected abstract String convertToUrl(String path) throws MalformedURLException;

    public abstract void closeConnexion();

    /**
     * Whether the bridge is closed.
     * 
     * @return <code>true</code> if {@link #closeConnexion()} was called or OpenOffice is now longer
     *         running.
     */
    public abstract boolean isClosed();
}
