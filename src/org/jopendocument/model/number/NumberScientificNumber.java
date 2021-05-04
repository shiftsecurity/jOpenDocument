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

package org.jopendocument.model.number;

/**
 * 
 */
public class NumberScientificNumber {

    protected String numberDecimalPlaces;
    protected String numberGrouping;
    protected String numberMinExponentDigits;
    protected String numberMinIntegerDigits;

    /**
     * Gets the value of the numberDecimalPlaces property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberDecimalPlaces() {
        return this.numberDecimalPlaces;
    }

    /**
     * Gets the value of the numberGrouping property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberGrouping() {
        if (this.numberGrouping == null) {
            return "false";
        } else {
            return this.numberGrouping;
        }
    }

    /**
     * Gets the value of the numberMinExponentDigits property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberMinExponentDigits() {
        return this.numberMinExponentDigits;
    }

    /**
     * Gets the value of the numberMinIntegerDigits property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberMinIntegerDigits() {
        return this.numberMinIntegerDigits;
    }

    /**
     * Sets the value of the numberDecimalPlaces property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberDecimalPlaces(final String value) {
        this.numberDecimalPlaces = value;
    }

    /**
     * Sets the value of the numberGrouping property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberGrouping(final String value) {
        this.numberGrouping = value;
    }

    /**
     * Sets the value of the numberMinExponentDigits property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberMinExponentDigits(final String value) {
        this.numberMinExponentDigits = value;
    }

    /**
     * Sets the value of the numberMinIntegerDigits property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberMinIntegerDigits(final String value) {
        this.numberMinIntegerDigits = value;
    }

}
