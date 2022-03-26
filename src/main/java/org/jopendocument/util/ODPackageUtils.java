package org.jopendocument.util;

import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODPackageEntry;

public class ODPackageUtils {
    public static String simpledPath(String path) {
        if(path.startsWith("./")) {
            return path.substring("./".length());
        } else {
            return path;
        }
    }
    public static void copyPackageDir(ODPackage fromPkg, ODPackage toPkg, String fromDir, String toDir, String mediaType) {
        String simpledFrom = simpledPath(fromDir);
        String simpledTo = simpledPath(toDir);
        for(String entry:fromPkg.getEntries()) {
            if(entry.startsWith(simpledFrom+"/")) {
                String toEntry = entry.replace(simpledFrom+"/", simpledTo+"/");
                toPkg.putCopy(fromPkg.getEntry(entry), toEntry);
                if(!mediaType.isEmpty()) {
                    ODPackageEntry dir = new ODPackageEntry(simpledTo+"/", mediaType, new byte[0], true);
                    toPkg.putCopy(dir);
                }
            }
        }
    }
    public static void putEntry(ODPackage pkg, ODPackageEntry entry, String name) {
        String path = simpledPath(name);
        pkg.putCopy(entry, path);
    }
}
