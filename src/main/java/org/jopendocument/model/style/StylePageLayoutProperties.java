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

import java.awt.Color;

import org.jopendocument.util.ValueHelper;

public class StylePageLayoutProperties {

    private Color backgroundColor;
    private int marginBottom;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int pageHeight;
    private int pageWidth;
    private String scaleTo;
    private String tableCentering;
    private String writingMode;

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getMarginBottom() {
        return this.marginBottom;
    }

    public int getMarginLeft() {
        return this.marginLeft;
    }

    public int getMarginRight() {
        return this.marginRight;
    }

    public int getMarginTop() {

        return this.marginTop;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public String getScaleTo() {
        return this.scaleTo;
    }

    public String getTableCentering() {
        return this.tableCentering;
    }

    public String getWritingMode() {
        return this.writingMode;
    }

    public StylePageLayoutProperties reset() {
        this.setBackgroundColor(null);
        this.setMarginBottom(null);
        this.setMarginLeft(null);
        this.setMarginRight(null);
        this.setMarginTop(null);
        this.setPageHeight(null);
        this.setPageWidth(null);
        return this;
    }

    public void setBackgroundColor(final String value) {
        if (value == null) {
            this.backgroundColor = Color.white;
        } else {
            this.backgroundColor = ValueHelper.getColor(value);
        }
    }

    public void setMarginBottom(String value) {
        if (value == null) {
            value = "2.0cm";
        }
        this.marginBottom = ValueHelper.getLength(value);
    }

    public void setMarginLeft(String value) {
        if (value == null) {
            value = "2.0cm";
        }
        this.marginLeft = ValueHelper.getLength(value);
    }

    public void setMarginRight(String value) {
        if (value == null) {
            value = "2.0cm";
        }
        this.marginRight = ValueHelper.getLength(value);
    }

    public void setMarginTop(String value) {
        if (value == null) {
            value = "2.0cm";
        }
        this.marginTop = ValueHelper.getLength(value);
    }

    public void setPageHeight(String value) {
        if (value == null) {
            value = "29.7cm";
            System.err.println("StylePageLayoutProperties: Assuming Page Format A4: Height: " + value);
        }
        this.pageHeight = ValueHelper.getLength(value);
    }

    public void setPageWidth(String value) {
        if (value == null) {
            value = "21.0cm";
            System.err.println("StylePageLayoutProperties: Assuming Page Format A4: Width:  " + value);
        }
        this.pageWidth = ValueHelper.getLength(value);
    }

    public void setScaleTo(final String value) {
        this.scaleTo = value;
    }

    public void setShadow(final String value) {
    }

    public void setTableCentering(final String value) {
        this.tableCentering = value;
    }

    public void setWritingMode(final String value) {
        this.writingMode = value;
    }
}
