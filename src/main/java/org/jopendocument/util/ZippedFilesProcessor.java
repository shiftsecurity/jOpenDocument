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

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * Class for processing a zip file.
 */
public abstract class ZippedFilesProcessor {

    /**
     * Helper class to prevent external code (e.g. SAXParser) from closing the input ZIP stream
     * while it is still being read.
     */
    private static class JarInputStreamWrapper extends JarInputStream {
        public JarInputStreamWrapper(InputStream in) throws IOException {
            super(in);
        }

        public void close() {
            // do nothing
        }

        public void closeForReal() throws IOException {
            super.close();
        }
    }

    public void process(InputStream input) throws IOException {
        JarInputStreamWrapper in = new JarInputStreamWrapper(input);
        while (true) {
            JarEntry entry = in.getNextJarEntry();
            if (entry == null)
                break;
            processEntry(entry, in);
            in.closeEntry();
        }
        in.closeForReal();
    }

    protected abstract void processEntry(ZipEntry entry, InputStream in) throws IOException;

}
