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

package org.jopendocument.io.svm;

import java.io.IOException;

import org.jopendocument.io.LittleEndianInputStream;

public class MapNode {

    private int version;
    private int length;
    private int x;
    private int y;
    private Fraction scaleX;
    private Fraction scaleY;
    private int unit;
    private boolean isSimple;

    public MapNode(LittleEndianInputStream in) throws IOException {
        version = in.readUnsignedShort();
        length = in.readInt();
        unit = in.readUnsignedShort();
        x = in.readInt();
        y = in.readInt();
        int n1 = in.readInt();
        int d1 = in.readInt();
        scaleX = new Fraction(n1, d1);
        int n2 = in.readInt();
        int d2 = in.readInt();
        scaleY = new Fraction(n2, d2);
        isSimple = in.readBoolean();

    }

    @Override
    public String toString() {
        return "MapNode: v:" + version + " x:" + x + " y:" + y + " l:" + length + " sx:" + scaleX + " sy;" + scaleY + " unit:" + unit + " simple:" + isSimple;
    }

}
