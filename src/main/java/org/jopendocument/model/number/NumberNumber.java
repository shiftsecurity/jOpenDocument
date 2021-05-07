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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class NumberNumber {

    protected String numberDecimalPlaces;
    protected String numberDecimalReplacement;
    protected String numberDisplayFactor;
    protected List<NumberEmbeddedText> numberEmbeddedText;
    protected String numberGrouping;
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
     * Gets the value of the numberDecimalReplacement property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberDecimalReplacement() {
        return this.numberDecimalReplacement;
    }

    /**
     * Gets the value of the numberDisplayFactor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberDisplayFactor() {
        if (this.numberDisplayFactor == null) {
            return "1";
        } else {
            return this.numberDisplayFactor;
        }
    }

    /**
     * Gets the value of the numberEmbeddedText property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the numberEmbeddedText property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getNumberEmbeddedText().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link NumberEmbeddedText }
     * 
     * 
     */
    public List<NumberEmbeddedText> getNumberEmbeddedText() {
        if (this.numberEmbeddedText == null) {
            this.numberEmbeddedText = new ArrayList<NumberEmbeddedText>();
        }
        return this.numberEmbeddedText;
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
     * Sets the value of the numberDecimalReplacement property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberDecimalReplacement(final String value) {
        this.numberDecimalReplacement = value;
    }

    /**
     * Sets the value of the numberDisplayFactor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberDisplayFactor(final String value) {
        this.numberDisplayFactor = value;
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
     * Sets the value of the numberMinIntegerDigits property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberMinIntegerDigits(final String value) {
        this.numberMinIntegerDigits = value;
    }

}
