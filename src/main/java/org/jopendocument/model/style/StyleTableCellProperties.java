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

import org.jopendocument.util.Tuple3;

import java.awt.Color;

import org.jopendocument.util.ValueHelper;

public class StyleTableCellProperties {

    // see http://www.w3.org/TR/2001/REC-xsl-20011015/slice7.html#border-left
    // http://www.w3.org/TR/CSS2/box.html#propdef-border-left
    static private Tuple3<Integer, String, Color> parseBorder(String value) {
        if (value == null || "none".equals(value)) {
            return null;
        }
        final String s[] = value.split(" ");

        // http://www.w3.org/TR/CSS2/box.html#value-def-border-width : The interpretation of the
        // values depends on the user agent, so we've taken these from trying with OO
        String widthS = s[0];
        if (widthS.equals("thin")) {
            widthS = "0.002cm";
        } else if (widthS.equals("medium")) {
            widthS = "0.035cm";
        } else if (widthS.equals("thick")) {
            widthS = "0.088cm";
        }
        final int width = ValueHelper.getLength(widthS);
        final String type = s[1];
        final Color c;
        if (s.length >= 3) {
            c = ValueHelper.getColor(s[2]);
        } else {
            // http://www.w3.org/TR/CSS2/box.html#propdef-border-top-color says the initial value
            // should be the 'color' property but there isn't any in cell properties so we do as OO
            c = Color.BLACK;
        }
        return Tuple3.create(width, type, c);
    }

    private String backgroundColor;

    private String border;

    private Color borderColorBottom;

    private Color borderColorLeft;

    private Color borderColorRight;

    private Color borderColorTop;

    private String borderTypeBottom; // solid, none

    private String borderTypeLeft; // solid, none

    private String borderTypeRight; // solid, none

    private String borderTypeTop; // solid, none

    private int borderWidthBottom;

    // Borders
    private int borderWidthLeft;

    private int borderWidthRight;

    private int borderWidthTop;

    private boolean hasBottomBorder;

    private boolean hasLeftBorder;

    private boolean hasRightBorder;

    private boolean hasTopBorder;

    private String padding;

    private String repeatContent;
    private String textAlignSource;
    private String verticalAlign;
    private String wrapOption;

    private boolean backgroundImage;

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getBorder() {
        return this.border;
    }

    public final Color getBorderColorBottom() {
        return this.borderColorBottom;
    }

    public final Color getBorderColorLeft() {
        return this.borderColorLeft;
    }

    public final Color getBorderColorRight() {
        return this.borderColorRight;
    }

    public final Color getBorderColorTop() {
        return this.borderColorTop;
    }

    public final String getBorderTypeBottom() {
        return this.borderTypeBottom;
    }

    public final String getBorderTypeLeft() {
        return this.borderTypeLeft;
    }

    public final String getBorderTypeRight() {
        return this.borderTypeRight;
    }

    public final String getBorderTypeTop() {
        return this.borderTypeTop;
    }

    public final int getBorderWidthBottom() {
        return this.borderWidthBottom;
    }

    public final int getBorderWidthLeft() {
        return this.borderWidthLeft;
    }

    public final int getBorderWidthRight() {
        return this.borderWidthRight;
    }

    public final int getBorderWidthTop() {
        return this.borderWidthTop;
    }

    public String getPadding() {
        return this.padding;
    }

    public String getRepeatContent() {
        return this.repeatContent;
    }

    public String getTextAlignSource() {
        return this.textAlignSource;
    }

    /**
     * getters
     */
    public String getVerticalAlign() {
        return this.verticalAlign;
    }

    public String getWrapOption() {
        return this.wrapOption;
    }

    public final boolean hasBottomBorder() {
        return this.hasBottomBorder;
    }

    public final boolean hasLeftBorder() {
        return this.hasLeftBorder;
    }

    public final boolean hasRightBorder() {
        return this.hasRightBorder;
    }

    public final boolean hasTopBorder() {
        return this.hasTopBorder;
    }

    public void setBackgroundColor(final String value) {
        this.backgroundColor = value;

    }

    public void setBorder(final String value) {
        if (value != null) {
            this.setBorderLeft(value);
            this.setBorderTop(value);
            this.setBorderRight(value);
            this.setBorderBottom(value);
        }
    }

    public void setBorderBottom(String value) {
        final Tuple3<Integer, String, Color> b = parseBorder(value);
        if (b == null) {
            return;
        }
        this.hasBottomBorder = true;
        this.borderWidthBottom = b.get0();
        this.borderTypeBottom = b.get1();
        this.borderColorBottom = b.get2();
    }

    public void setBorderLeft(String value) {
        final Tuple3<Integer, String, Color> b = parseBorder(value);
        if (b == null) {
            // borderTypeLeft = value;

            return;
        }
        this.hasLeftBorder = true;
        this.borderWidthLeft = b.get0();
        this.borderTypeLeft = b.get1();
        this.borderColorLeft = b.get2();
    }

    public void setBorderRight(String value) {
        final Tuple3<Integer, String, Color> b = parseBorder(value);
        if (b == null) {
            return;
        }
        this.hasRightBorder = true;
        this.borderWidthRight = b.get0();
        this.borderTypeRight = b.get1();
        this.borderColorRight = b.get2();

    }

    public void setBorderTop(String value) {
        final Tuple3<Integer, String, Color> b = parseBorder(value);
        if (b == null) {
            return;
        }
        this.hasTopBorder = true;
        this.borderWidthTop = b.get0();
        this.borderTypeTop = b.get1();
        this.borderColorTop = b.get2();
    }

    public void setPadding(final String value) {
        this.padding = value;

    }

    public void setRepeatContent(final String value) {
        this.repeatContent = value;
    }

    public void setTextAlignSource(final String value) {
        this.textAlignSource = value;
    }

    /**
     * setters
     */
    public void setVerticalAlign(final String value) {
        this.verticalAlign = value;

    }

    public void setWrapOption(final String value) {
        this.wrapOption = value;
    }

    @Override
    public String toString() {

        return "StyleTableCellProperties: border: L:" + this.borderTypeLeft + " R:" + this.borderTypeRight + " T:" + this.borderTypeTop + " B:" + this.borderTypeTop;
    }

    // TODO <style:background-image/> usage
    public void setBackgroundImage(boolean b) {
        this.backgroundImage = b;

    }
}
