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
public class TextAlphabeticalIndexMark {

    protected String textKey1;
    protected String textKey2;
    protected String textMainEtry;
    protected String textStringValue;

    /**
     * Gets the value of the textKey1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextKey1() {
        return this.textKey1;
    }

    /**
     * Gets the value of the textKey2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextKey2() {
        return this.textKey2;
    }

    /**
     * Gets the value of the textMainEtry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextMainEtry() {
        if (this.textMainEtry == null) {
            return "false";
        } else {
            return this.textMainEtry;
        }
    }

    /**
     * Gets the value of the textStringValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStringValue() {
        return this.textStringValue;
    }

    /**
     * Sets the value of the textKey1 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextKey1(final String value) {
        this.textKey1 = value;
    }

    /**
     * Sets the value of the textKey2 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextKey2(final String value) {
        this.textKey2 = value;
    }

    /**
     * Sets the value of the textMainEtry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextMainEtry(final String value) {
        this.textMainEtry = value;
    }

    /**
     * Sets the value of the textStringValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStringValue(final String value) {
        this.textStringValue = value;
    }

}
