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

public abstract class OSFamily {

    static public class Unix extends OSFamily {
    }

    static public final Unix Mac = new Unix();
    static public final Unix Linux = new Unix();
    static public final Unix FreeBSD = new Unix();
    static public final OSFamily Windows = new OSFamily() {
    };

    static private final OSFamily INSTANCE = getCurrentOS();

    // perhaps create an OS class to which we pass the os.* properties
    static private final OSFamily getCurrentOS() {
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            return Windows;
        } else if (os.startsWith("Mac OS")) {
            return Mac;
        } else if (os.startsWith("Linux")) {
            return Linux;
        } else if (os.startsWith("FreeBSD")) {
            return FreeBSD;
        } else {
            System.err.println("Unsupported OS " + os);
            return null;
        }
    }

    public final static OSFamily getInstance() {
        return INSTANCE;
    }
}
