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

import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.ODFrame;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.StyleProperties;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public final class ValueHelper {

    public static final Color TRANSPARENT = StyleProperties.TRANSPARENT;
    // Cache for Color resolution
    private static final Map<String, Color> colors = new HashMap<String, Color>();

    static {
        // from http://www.w3.org/TR/CSS2/syndata.html#value-def-color
        colors.put("maroon", new Color(0x80, 0, 0));
        colors.put("red", Color.RED);
        colors.put("orange", new Color(0xff, 0xA5, 0));
        colors.put("yellow", Color.YELLOW);
        colors.put("olive", new Color(0x80, 0x80, 0));
        colors.put("purple", new Color(0x80, 0, 0x80));
        colors.put("fuchsia", Color.MAGENTA);
        colors.put("white", Color.WHITE);
        colors.put("lime", Color.GREEN);
        colors.put("green", new Color(0, 0x80, 0));
        colors.put("navy", new Color(0, 0, 0x80));
        colors.put("blue", Color.BLUE);
        colors.put("aqua", Color.CYAN);
        colors.put("teal", new Color(0, 0x80, 0x80));
        colors.put("black", Color.BLACK);
        colors.put("silver", Color.LIGHT_GRAY);
        colors.put("gray", Color.GRAY);
    }

    // in micron
    private static Map<String, Integer> cacheLength = new HashMap<String, Integer>();

    public static final int getLength(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        Integer length = cacheLength.get(value);
        if (length == null) {
            length = Math.round(ODFrame.parseLength(value, LengthUnit.MM) * 1000);
            cacheLength.put(value, length);
        }
        return length;
    }

    public static final boolean getBoolean(String value) {
        if (value == null) {
            return false;
        } else if (value.equals("true")) {
            return (true);
        } else if (value.equals("false")) {
            return (false);
        } else {
            throw new IllegalArgumentException(value + " not a boolean value");
        }
    }

    public final static Color getColor(final String value) {
        if (value == null)
            return null;

        Color c = colors.get(value);
        if (c != null) {
            return c;
        }
        if (value.equals(StyleProperties.TRANSPARENT_NAME)) {
            c = TRANSPARENT;
        } else {
            c = OOUtils.decodeRGB(value);
        }
        colors.put(value, c);
        return c;
    }
}
