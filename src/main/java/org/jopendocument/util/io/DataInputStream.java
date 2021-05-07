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

package org.jopendocument.util.io;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStream extends FilterInputStream {

    private Boolean littleEndian;

    public DataInputStream(final InputStream in) {
        this(in, null);
    }

    public DataInputStream(final InputStream in, final Boolean littleEndian) {
        super(in);
        this.littleEndian = littleEndian;
    }

    public final void setLittleEndian(Boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    public final int readInt() throws IOException {
        return this.readInt(this.littleEndian);
    }

    public int readInt(final boolean littleEndian) throws IOException {
        final int ch1 = this.in.read();
        final int ch2 = this.in.read();
        final int ch3 = this.in.read();
        final int ch4 = this.in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        if (littleEndian)
            return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
        else
            return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
    }

    public final short readShort() throws IOException {
        return this.readShort(this.littleEndian);
    }

    public short readShort(final boolean littleEndian) throws IOException {
        final int ch1 = this.in.read();
        final int ch2 = this.in.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short) (littleEndian ? (ch2 << 8) + ch1 : (ch1 << 8) + ch2);
    }
}
