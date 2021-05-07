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
public class TextHiddenText {

    protected String textCondition;
    protected String textIsHidden;
    protected String textStringValue;
    protected String value;

    /**
     * Gets the value of the textCondition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCondition() {
        return this.textCondition;
    }

    /**
     * Gets the value of the textIsHidden property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIsHidden() {
        if (this.textIsHidden == null) {
            return "false";
        } else {
            return this.textIsHidden;
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
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
    }

    /**
     * Sets the value of the textCondition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCondition(final String value) {
        this.textCondition = value;
    }

    /**
     * Sets the value of the textIsHidden property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIsHidden(final String value) {
        this.textIsHidden = value;
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
