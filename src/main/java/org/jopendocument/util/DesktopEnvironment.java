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

package org.jopendocument.util;

import org.jopendocument.util.OSFamily.Unix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

/**
 * A desktop environment like Gnome or MacOS.
 * 
 * @author Sylvain CUAZ
 * @see #getDE()
 */
public abstract class DesktopEnvironment {

    static public final class Gnome extends DesktopEnvironment {

        @Override
        protected String findVersion() {
            try {
                // e.g. GNOME gnome-about 2.24.1
                final String line = cmdSubstitution(Runtime.getRuntime().exec(new String[] { "gnome-about", "--version" }));
                final String[] words = line.split(" ");
                return words[words.length - 1];
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static public final class KDE extends DesktopEnvironment {

        private static final Pattern versionPattern = Pattern.compile("^KDE: (.*)$", Pattern.MULTILINE);

        @Override
        protected String findVersion() {
            try {
                // Qt: 3.3.8b
                // KDE: 3.5.10
                // kde-config: 1.0
                final String line = cmdSubstitution(Runtime.getRuntime().exec(new String[] { "kde-config", "--version" }));
                final Matcher matcher = versionPattern.matcher(line);
                matcher.find();
                return matcher.group(1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static public final class XFCE extends DesktopEnvironment {

        @Override
        protected String findVersion() {
            // TODO
            return "";
        }
    }

    static public final class Unknown extends DesktopEnvironment {
        @Override
        protected String findVersion() {
            return "";
        }
    }

    static private class DEisOS extends DesktopEnvironment {
        @Override
        protected String findVersion() {
            return System.getProperty("os.version");
        }
    }

    static public final class Windows extends DEisOS {
        static private boolean needsQuoting(String s) {
            final int len = s.length();
            if (len == 0) // empty string have to be quoted
                return true;
            for (int i = 0; i < len; i++) {
                switch (s.charAt(i)) {
                    case ' ':
                    case '\t':
                    case '\\':
                    case '"':
                        return true;
                }
            }
            return false;
        }

        // on Windows program themselves are required to parse the command line, thus a lot of them
        // do it differently, see http://www.autohotkey.net/~deleyd/parameters/parameters.htm

        static private final Pattern quotePatrn = Pattern.compile("([\\\\]*)\"");
        static private final Pattern endSlashPatrn = Pattern.compile("([\\\\]*)\\z");

        // see http://bugs.sun.com/view_bug.do?bug_id=6468220
        // e.g. find.exe, choice.exe
        public String quoteParamForMsftC(String s) {
            if (!needsQuoting(s))
                return s;
            if (s.length() > 0) {
                // replace '(\*)"' by '$1$1\"', e.g. '\quote " \"' by '\quote \" \\\"'
                // $1 needed so that the backslash we add isn't escaped itself by a preceding
                // backslash
                s = quotePatrn.matcher(s).replaceAll("$1$1\\\\\"");
                // replace '(\*)\z' by '$1$1', e.g. 'foo\' by 'foo\\'
                // needed to not escape closing quote
                s = endSlashPatrn.matcher(s).replaceAll("$1$1");
            }
            return '"' + s + '"';
        }

        // e.g. bash.exe
        public String quoteParamForGCC(String s) {
            return StringUtils.doubleQuote(s);
        }

        public String quoteParamForScript(final String s) {
            if (s.indexOf('"') >= 0)
                throw new IllegalArgumentException("Can not pass a double quote as part of a parameter");
            return '"' + s + '"';
        }

        @Override
        public String quoteParamForExec(final String s) {
            return quoteParamForMsftC(s);
        }
    }

    static public final class Mac extends DEisOS {

        // From CarbonCore/Folders.h
        private static final String kDocumentsDirectory = "docs";
        private static final String kPreferencesDirectory = "pref";
        private static Class<?> FileManagerClass;
        private static Short kUserDomain;
        private static Method OSTypeToInt;

        private static Class<?> getFileManagerClass() {
            if (FileManagerClass == null) {
                try {
                    FileManagerClass = Class.forName("com.apple.eio.FileManager");
                    OSTypeToInt = FileManagerClass.getMethod("OSTypeToInt", String.class);
                    kUserDomain = (Short) FileManagerClass.getField("kUserDomain").get(null);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return FileManagerClass;
        }

        @Override
        public File getDocumentsFolder() {
            return getFolder(kDocumentsDirectory);
        }

        @Override
        public File getPreferencesFolder(String appName) {
            return new File(getFolder(kPreferencesDirectory), appName);
        }

        public File getFolder(String type) {
            try {
                final Method findFolder = getFileManagerClass().getMethod("findFolder", Short.TYPE, Integer.TYPE);
                final String path = (String) findFolder.invoke(null, kUserDomain, OSTypeToInt.invoke(null, type));
                return new File(path);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public File getAppDir(final String bundleID) throws IOException {
            // we used to ask for the URL of the application file but since 10.7 it returns a
            // file reference URL like "file:///.file/id=6723689.35865"
            final ProcessBuilder processBuilder = new ProcessBuilder("osascript", "-e", "tell application id \"com.apple.Finder\" to POSIX path of (application file id \"" + bundleID
                    + "\" as string)");
            // if not found prints nothing to out and a cryptic error to the standard error stream
            final String dir = cmdSubstitution(processBuilder.start()).trim();
            return dir.length() == 0 ? null : new File(dir);
        }
    }

    /**
     * Execute the passed command and test its return code.
     * 
     * @param command the command to {@link Runtime#exec(String[]) execute}.
     * @return <code>false</code> if the {@link Process#waitFor() return code} is not 0 or an
     *         exception is thrown.
     * @throws RTInterruptedException if this is interrupted while waiting.
     */
    public static final boolean test(final String... command) throws RTInterruptedException {
        try {
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (InterruptedException e) {
            throw new RTInterruptedException(e);
        } catch (IOException e) {
            Log.get().finer(e.getLocalizedMessage());
            return false;
        }
    }

    public final static String cmdSubstitution(Process p) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(100 * 1024);
        StreamUtils.copy(p.getInputStream(), out);
        return out.toString();
    }

    private static final DesktopEnvironment detectDE() {
        final OSFamily os = OSFamily.getInstance();
        if (os == OSFamily.Windows) {
            return new Windows();
        } else if (os == OSFamily.Mac) {
            return new Mac();
        } else if (os instanceof Unix) {
            // from redhat xdg-utils 1.0.2-14.20091016cvs
            if ("true".equalsIgnoreCase(System.getenv("KDE_FULL_SESSION")))
                return new KDE();
            else if (System.getenv("GNOME_DESKTOP_SESSION_ID") != null)
                return new Gnome();
            // the above variable is deprecated, the line below only works for newer gnome
            else if (test("dbus-send", "--print-reply", "--dest=org.freedesktop.DBus", "/org/freedesktop/DBus", "org.freedesktop.DBus.GetNameOwner", "string:org.gnome.SessionManager"))
                return new Gnome();
            else {
                try {
                    final String saveMode = cmdSubstitution(Runtime.getRuntime().exec(new String[] { "xprop", "-root", "_DT_SAVE_MODE" }));
                    if (saveMode.endsWith(" = \"xfce4\""))
                        return new XFCE();
                } catch (IOException e) {
                    Log.get().fine(e.getLocalizedMessage());
                }
            }
        }
        return new Unknown();
    }

    private static DesktopEnvironment DE = null;

    public static final DesktopEnvironment getDE() {
        if (DE == null) {
            DE = detectDE();
        }
        return DE;
    }

    public static final void resetDE() {
        DE = null;
    }

    private String version;

    private DesktopEnvironment() {
        this.version = null;
    }

    protected abstract String findVersion();

    public final String getVersion() {
        if (this.version == null)
            this.version = this.findVersion();
        return this.version;
    }

    public File getDocumentsFolder() {
        return FileSystemView.getFileSystemView().getDefaultDirectory();
    }

    public File getPreferencesFolder(final String appName) {
        return new File(System.getProperty("user.home"), "." + appName);
    }

    // on some systems arguments are not passed correctly by ProcessBuilder
    public String quoteParamForExec(String s) {
        return s;
    }

    @Override
    public String toString() {
        return "DesktopEnvironment " + this.getClass().getSimpleName();
    }

    public static void main(String[] args) {
        System.out.println(getDE() + " version " + getDE().getVersion());
    }
}
