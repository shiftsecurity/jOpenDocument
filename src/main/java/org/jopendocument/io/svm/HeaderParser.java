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

public class HeaderParser {

    private int version;
    private int length;
    private int compressionMode;
    private int width;
    private int height;
    private int actionCount;
    private MapNode mapNode;

    public HeaderParser(LittleEndianInputStream in) throws IOException {
        byte[] mcode = new byte[6];
        in.read(mcode);
        version = in.readUnsignedShort();
        length = in.readInt();
        compressionMode = in.readInt();
        mapNode = new MapNode(in);
        width = in.readInt();
        height = in.readInt();
        actionCount = in.readInt();

    }

    @Override
    public String toString() {
        return "Header: version:" + version + " size:" + width + "x" + height + " comp:" + compressionMode + " actions:" + actionCount + " lenght:" + length;
    }

    public MapNode getMapNode() {
        return mapNode;
    }

    public int getActionCount() {
        return actionCount;
    }

}
