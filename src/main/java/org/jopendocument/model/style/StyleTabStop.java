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
public class StyleTabStop {

    protected String styleChar;
    protected String styleLeaderChar;
    protected String stylePosition;
    protected String styleType;

    /**
     * Gets the value of the styleChar property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleChar() {
        return this.styleChar;
    }

    /**
     * Gets the value of the styleLeaderChar property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleLeaderChar() {
        if (this.styleLeaderChar == null) {
            return "";
        } else {
            return this.styleLeaderChar;
        }
    }

    /**
     * Gets the value of the stylePosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStylePosition() {
        return this.stylePosition;
    }

    /**
     * Gets the value of the styleType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleType() {
        if (this.styleType == null) {
            return "left";
        } else {
            return this.styleType;
        }
    }

    /**
     * Sets the value of the styleChar property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleChar(final String value) {
        this.styleChar = value;
    }

    /**
     * Sets the value of the styleLeaderChar property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleLeaderChar(final String value) {
        this.styleLeaderChar = value;
    }

    /**
     * Sets the value of the stylePosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStylePosition(final String value) {
        this.stylePosition = value;
    }

    /**
     * Sets the value of the styleType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleType(final String value) {
        this.styleType = value;
    }

}
