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
public class TextVariableSet {

    protected String styleDataStyleName;
    protected String textBooleanValue;
    protected String textCurrency;
    protected String textDateValue;
    protected String textDisplay;
    protected String textFormula;
    protected String textName;
    protected String textStringValue;
    protected String textTimeValue;
    protected String textValue;
    protected String textValueType;
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
     * Gets the value of the textBooleanValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextBooleanValue() {
        return this.textBooleanValue;
    }

    /**
     * Gets the value of the textCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCurrency() {
        return this.textCurrency;
    }

    /**
     * Gets the value of the textDateValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDateValue() {
        return this.textDateValue;
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
     * Gets the value of the textFormula property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextFormula() {
        return this.textFormula;
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
     * Gets the value of the textStringValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStringValue() {
        return this.textStringValue;
    }

    /**
     * Gets the value of the textTimeValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTimeValue() {
        return this.textTimeValue;
    }

    /**
     * Gets the value of the textValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextValue() {
        return this.textValue;
    }

    /**
     * Gets the value of the textValueType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextValueType() {
        return this.textValueType;
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
     * Sets the value of the textBooleanValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextBooleanValue(final String value) {
        this.textBooleanValue = value;
    }

    /**
     * Sets the value of the textCurrency property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCurrency(final String value) {
        this.textCurrency = value;
    }

    /**
     * Sets the value of the textDateValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDateValue(final String value) {
        this.textDateValue = value;
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
     * Sets the value of the textFormula property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextFormula(final String value) {
        this.textFormula = value;
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
     * Sets the value of the textStringValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStringValue(final String value) {
        this.textStringValue = value;
    }

    /**
     * Sets the value of the textTimeValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTimeValue(final String value) {
        this.textTimeValue = value;
    }

    /**
     * Sets the value of the textValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextValue(final String value) {
        this.textValue = value;
    }

    /**
     * Sets the value of the textValueType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextValueType(final String value) {
        this.textValueType = value;
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
