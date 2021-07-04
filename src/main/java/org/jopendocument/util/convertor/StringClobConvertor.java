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

package org.jopendocument.util.convertor;

import java.sql.Clob;

import javax.sql.rowset.serial.SerialClob;

public class StringClobConvertor extends NullIsNullConvertor<String, Clob> {

    public static final StringClobConvertor INSTANCE = new StringClobConvertor(null);
    public static final StringClobConvertor INSTANCE_LENIENT = new StringClobConvertor(Integer.MAX_VALUE);

    // since String.length() is an integer
    private final Integer maxLength;

    public StringClobConvertor(final int maxLength) {
        this(Integer.valueOf(maxLength));
    }

    /**
     * Create a new instance. Since {@link String} can contain at most {@link Integer#MAX_VALUE}
     * characters the maximum length of {@link #unconvert(Clob)} must be specified.
     * 
     * @param maxLength the maximum length, <code>null</code> to throw an exception if the clob
     *        doesn't fit in a String.
     */
    public StringClobConvertor(final Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    protected Clob convertNonNull(String o) {
        try {
            return new SerialClob(o.toCharArray());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected String unconvertNonNull(Clob o) {
        try {
            final long clobL = o.length();
            final int l;
            if (this.maxLength != null)
                l = (int) Math.min(clobL, this.maxLength.longValue());
            else if (clobL > Integer.MAX_VALUE)
                throw new IllegalStateException("Clob too long: " + clobL);
            else
                l = (int) clobL;
            // The first character is at position 1
            return o.getSubString(1, l);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
