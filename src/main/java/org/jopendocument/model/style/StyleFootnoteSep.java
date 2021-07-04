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
public class StyleFootnoteSep {

    protected String styleAdjustment;
    protected String styleColor;
    protected String styleDistanceAfterSep;
    protected String styleDistanceBeforeSep;
    protected String styleRelWidth;
    protected String styleWidth;

    /**
     * Gets the value of the styleAdjustment property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleAdjustment() {
        if (this.styleAdjustment == null) {
            return "left";
        } else {
            return this.styleAdjustment;
        }
    }

    /**
     * Gets the value of the styleColor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleColor() {
        return this.styleColor;
    }

    /**
     * Gets the value of the styleDistanceAfterSep property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDistanceAfterSep() {
        return this.styleDistanceAfterSep;
    }

    /**
     * Gets the value of the styleDistanceBeforeSep property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDistanceBeforeSep() {
        return this.styleDistanceBeforeSep;
    }

    /**
     * Gets the value of the styleRelWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleRelWidth() {
        return this.styleRelWidth;
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
     * Sets the value of the styleAdjustment property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleAdjustment(final String value) {
        this.styleAdjustment = value;
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
     * Sets the value of the styleDistanceAfterSep property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDistanceAfterSep(final String value) {
        this.styleDistanceAfterSep = value;
    }

    /**
     * Sets the value of the styleDistanceBeforeSep property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDistanceBeforeSep(final String value) {
        this.styleDistanceBeforeSep = value;
    }

    /**
     * Sets the value of the styleRelWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleRelWidth(final String value) {
        this.styleRelWidth = value;
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
