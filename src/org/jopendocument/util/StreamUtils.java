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

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class StreamUtils {

    public static final OutputStream NULL_OS = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            // ignore
        }

        public void write(byte b[], int off, int len) throws IOException {
            if (b == null)
                throw new NullPointerException();
            // ignore
        }
    };

    /**
     * Verbatim copy an entry from input to output stream.
     * 
     * @param in the source.
     * @param out the destination.
     * @throws IOException if an error occurs while reading or writing.
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        copy(in, out, 512 * 1024);
    }

    public static void copy(InputStream in, OutputStream out, final int bufferSize) throws IOException {
        copy(in, out, bufferSize, -1);
    }

    public static long copy(InputStream in, OutputStream out, final int bufferSize, final long length) throws IOException {
        final byte[] buffer = new byte[bufferSize];
        long totalCount = 0;
        final boolean copyAll = length < 0;
        while (copyAll || totalCount < length) {
            final long toRead = copyAll ? buffer.length : Math.min(length - totalCount, buffer.length);
            // since buffer.length is an int
            assert 0 <= toRead && toRead <= Integer.MAX_VALUE;
            final int count = in.read(buffer, 0, (int) toRead);
            if (count == -1)
                break;
            totalCount += count;
            out.write(buffer, 0, count);
        }
        // < if end of stream
        assert copyAll || totalCount <= length;
        return totalCount;
    }

    public static void copy(InputStream ins, File out) throws IOException {
        // buffered since read() in copy(InputStream, OutputStream) may return 1 byte at a time
        final OutputStream ous = new BufferedOutputStream(new FileOutputStream(out));
        try {
            copy(ins, ous);
        } finally {
            ous.close();
        }
    }

    public static void writeln(final String s, final OutputStream out) throws IOException {
        write(s + "\n", out);
    }

    public static void write(final String s, final OutputStream out) throws IOException {
        write(s, out, StringUtils.UTF8);
    }

    public static void write(final String s, final OutputStream out, Charset charset) throws IOException {
        out.write(s.getBytes(charset));
    }

    /**
     * Wrap the output stream into a writer, and write the XML declaration.
     * 
     * @param outs an output stream.
     * @return a writer with the same encoding as the XML.
     * @throws IOException if an error occurs.
     */
    public static BufferedWriter createXMLWriter(OutputStream outs) throws IOException {
        return new BufferedWriter(createXMLUnbufferedWriter(outs));
    }

    public static Writer createXMLUnbufferedWriter(OutputStream outs) throws IOException {
        // see http://www.w3.org/TR/REC-xml/#sec-guessing
        // don't use UTF-8 BOM as Java does not support it :
        // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058
        final Writer res = new OutputStreamWriter(outs, StringUtils.UTF8);
        res.write("<?xml version='1.0' encoding='UTF-8' ?>\n");
        return res;
    }
}
