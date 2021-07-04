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

/**
 * 
 */
public class StyleColumnSep {

    protected String styleColor;
    protected String styleHeight;
    protected String styleStyle;
    protected String styleVerticalAlign;
    protected String styleWidth;

    /**
     * Gets the value of the styleColor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleColor() {
        if (this.styleColor == null) {
            return "#000000";
        } else {
            return this.styleColor;
        }
    }

    /**
     * Gets the value of the styleHeight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleHeight() {
        if (this.styleHeight == null) {
            return "100%";
        } else {
            return this.styleHeight;
        }
    }

    /**
     * Gets the value of the styleStyle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleStyle() {
        if (this.styleStyle == null) {
            return "solid";
        } else {
            return this.styleStyle;
        }
    }

    /**
     * Gets the value of the styleVerticalAlign property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleVerticalAlign() {
        if (this.styleVerticalAlign == null) {
            return "top";
        } else {
            return this.styleVerticalAlign;
        }
    }

    /**
     * Gets the value of the styleWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleWidth() {
        return this.styleWidth;
    }

    /**
     * Sets the value of the styleColor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleColor(final String value) {
        this.styleColor = value;
    }

    /**
     * Sets the value of the styleHeight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleHeight(final String value) {
        this.styleHeight = value;
    }

    /**
     * Sets the value of the styleStyle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleStyle(final String value) {
        this.styleStyle = value;
    }

    /**
     * Sets the value of the styleVerticalAlign property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleVerticalAlign(final String value) {
        this.styleVerticalAlign = value;
    }

    /**
     * Sets the value of the styleWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleWidth(final String value) {
        this.styleWidth = value;
    }

}
