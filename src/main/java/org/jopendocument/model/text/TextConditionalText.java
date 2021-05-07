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
public class TextConditionalText {

    protected String textCondition;
    protected String textCurrentValue;
    protected String textStringValueIfFalse;
    protected String textStringValueIfTrue;
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
     * Gets the value of the textCurrentValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCurrentValue() {
        if (this.textCurrentValue == null) {
            return "false";
        } else {
            return this.textCurrentValue;
        }
    }

    /**
     * Gets the value of the textStringValueIfFalse property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStringValueIfFalse() {
        return this.textStringValueIfFalse;
    }

    /**
     * Gets the value of the textStringValueIfTrue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStringValueIfTrue() {
        return this.textStringValueIfTrue;
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
     * Sets the value of the textCurrentValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCurrentValue(final String value) {
        this.textCurrentValue = value;
    }

    /**
     * Sets the value of the textStringValueIfFalse property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStringValueIfFalse(final String value) {
        this.textStringValueIfFalse = value;
    }

    /**
     * Sets the value of the textStringValueIfTrue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStringValueIfTrue(final String value) {
        this.textStringValueIfTrue = value;
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
