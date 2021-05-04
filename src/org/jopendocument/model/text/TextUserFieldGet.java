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

package org.jopendocument.model.text;

/**
 * 
 */
public class TextUserFieldGet {

    protected String styleDataStyleName;
    protected String textDisplay;
    protected String textName;
    protected String value;

    /**
     * Gets the value of the styleDataStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDataStyleName() {
        return this.styleDataStyleName;
    }

    /**
     * Gets the value of the textDisplay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDisplay() {
        if (this.textDisplay == null) {
            return "value";
        } else {
            return this.textDisplay;
        }
    }

    /**
     * Gets the value of the textName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextName() {
        return this.textName;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
    }

    /**
     * Sets the value of the styleDataStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDataStyleName(final String value) {
        this.styleDataStyleName = value;
    }

    /**
     * Sets the value of the textDisplay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDisplay(final String value) {
        this.textDisplay = value;
    }

    /**
     * Sets the value of the textName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextName(final String value) {
        this.textName = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setvalue(final String value) {
        this.value = value;
    }

}
