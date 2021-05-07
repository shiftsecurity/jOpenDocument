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

package org.jopendocument.model.style;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public class StyleTextProperties {

    private static Map<String, StyleTextProperties> cache = new HashMap<String, StyleTextProperties>();

    private String color;

    private String fontName;

    private String fontSize;
    private String fontStyle;
    private String fontWeight;

    Font lastFont;

    double lastResizeFactor;

    public StyleTextProperties(String name, String size, String weight, String style, String color) {
        this.fontName = name;
        this.fontSize = size;
        this.fontWeight = weight;
        this.fontStyle = style;
        this.color = color;

    }

    public static StyleTextProperties getStyleTextProperties(String name, String size, String weight, String style, String color) {
        String key = name + size + weight + style + color;
        StyleTextProperties p = cache.get(key);
        if (p == null) {
            p = new StyleTextProperties(name, size, weight, style, color);
            cache.put(key, p);
        }
        return p;
    }

    public String getColor() {
        return this.color;
    }

    public Font getFont(final double resizeFactor) {
        if (this.lastResizeFactor == resizeFactor) {
            return this.lastFont;
        }
        String currentFontName = this.fontName;

        int fonttype = Font.PLAIN;
        if (this.fontWeight != null && this.fontWeight.equals("bold")) {
            fonttype = Font.BOLD;
        }
        if (this.fontStyle != null && this.fontStyle.equals("italic")) {
            fonttype = Font.ITALIC;
            if (fonttype == Font.BOLD) {
                fonttype = Font.ITALIC | Font.BOLD;
            }
        }
        if (this.fontSize == null) {
            this.fontSize = "10pt";
            System.err.println("Assert default font size: 10");
        }
        final String substring = this.fontSize.substring(0, this.fontSize.length() - 2);
        final int fSize = Integer.valueOf(substring).intValue();
        if (currentFontName.equalsIgnoreCase("Times")) {
            currentFontName = "Times New Roman";
        }

        final Font font = new Font(currentFontName, fonttype, (int) (fSize * 360 / resizeFactor));
        // System.err.println("StyleTextProperties.getFont():"+font.getFontName());
        // System.err.println("StyleTextProperties.getFont() " + currentFontName + " font size:" +
        // this.fontSize + " " + (int) ((fSize * 360) / resizeFactor));
        this.lastFont = font;
        this.lastResizeFactor = resizeFactor;
        return font;
    }

    public String getFontName() {
        return this.fontName;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public String getFontStyle() {
        return this.fontStyle;
    }

    public String getFontWeight() {
        return this.fontWeight;
    }

    @Override
    public String toString() {
        return "StyleTextProperty:" + this.fontName + " " + this.fontSize + " " + this.fontWeight;
    }

}
