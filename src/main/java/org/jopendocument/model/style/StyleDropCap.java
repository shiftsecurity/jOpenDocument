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
public class StyleDropCap {

    protected String styleDistance;
    protected String styleLength;
    protected String styleLines;
    protected String styleStyleName;

    /**
     * Gets the value of the styleDistance property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDistance() {
        if (this.styleDistance == null) {
            return "0cm";
        } else {
            return this.styleDistance;
        }
    }

    /**
     * Gets the value of the styleLength property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleLength() {
        if (this.styleLength == null) {
            return "1";
        } else {
            return this.styleLength;
        }
    }

    /**
     * Gets the value of the styleLines property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleLines() {
        if (this.styleLines == null) {
            return "1";
        } else {
            return this.styleLines;
        }
    }

    /**
     * Gets the value of the styleStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleStyleName() {
        return this.styleStyleName;
    }

    /**
     * Sets the value of the styleDistance property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDistance(final String value) {
        this.styleDistance = value;
    }

    /**
     * Sets the value of the styleLength property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleLength(final String value) {
        this.styleLength = value;
    }

    /**
     * Sets the value of the styleLines property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleLines(final String value) {
        this.styleLines = value;
    }

    /**
     * Sets the value of the styleStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleStyleName(final String value) {
        this.styleStyleName = value;
    }

}
