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

import java.awt.Font;
import java.awt.FontMetrics;

public class DummyFontMetrics extends FontMetrics {

    protected DummyFontMetrics(Font font) {
        super(font);

    }

    // don't overload getFontRenderContext() to be able to compile for java5

    @Override
    public int getLeading() {
        return 10;
    }

    @Override
    public int getAscent() {
        return 0;
    }

    @Override
    public int getDescent() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getMaxAscent() {
        return getAscent();
    }

    @Override
    public int getMaxDescent() {
        return getDescent();
    }

    @Override
    public int getMaxAdvance() {
        return -1;
    }

    @Override
    public int charWidth(int codePoint) {
        return 20;
    }

    @Override
    public int charWidth(char ch) {
        if (ch < 256) {
            return getWidths()[ch];
        }
        char data[] = { ch };
        return charsWidth(data, 0, 1);
    }

    @Override
    public int charsWidth(char data[], int off, int len) {
        return len * 10;
    }

    @Override
    public int bytesWidth(byte data[], int off, int len) {
        return stringWidth(new String(data, off, len));
    }

    @Override
    public boolean hasUniformLineMetrics() {
        return true;
    }

}
