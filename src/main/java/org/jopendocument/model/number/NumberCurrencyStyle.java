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

import org.jopendocument.model.style.StyleMap;
import org.jopendocument.model.style.StyleProperties;

/**
 * 
 */
public class NumberCurrencyStyle {

    protected String numberAutomaticOrder;
    protected String numberCountry;
    protected String numberLanguage;
    protected String numberTitle;
    protected String numberTransliterationCountry;
    protected String numberTransliterationFormat;
    protected String numberTransliterationLanguage;
    protected String numberTransliterationStyle;
    protected String styleFamily;
    protected String styleName;
    protected List<Object> stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap;
    protected String styleVolatile;

    /**
     * Gets the value of the numberAutomaticOrder property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberAutomaticOrder() {
        if (this.numberAutomaticOrder == null) {
            return "false";
        } else {
            return this.numberAutomaticOrder;
        }
    }

    /**
     * Gets the value of the numberCountry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberCountry() {
        return this.numberCountry;
    }

    /**
     * Gets the value of the numberLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberLanguage() {
        return this.numberLanguage;
    }

    /**
     * Gets the value of the numberTitle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberTitle() {
        return this.numberTitle;
    }

    /**
     * Gets the value of the numberTransliterationCountry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberTransliterationCountry() {
        return this.numberTransliterationCountry;
    }

    /**
     * Gets the value of the numberTransliterationFormat property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberTransliterationFormat() {
        if (this.numberTransliterationFormat == null) {
            return "1";
        } else {
            return this.numberTransliterationFormat;
        }
    }

    /**
     * Gets the value of the numberTransliterationLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberTransliterationLanguage() {
        return this.numberTransliterationLanguage;
    }

    /**
     * Gets the value of the numberTransliterationStyle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberTransliterationStyle() {
        if (this.numberTransliterationStyle == null) {
            return "short";
        } else {
            return this.numberTransliterationStyle;
        }
    }

    /**
     * Gets the value of the styleFamily property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleFamily() {
        return this.styleFamily;
    }

    /**
     * Gets the value of the styleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleName() {
        return this.styleName;
    }

    /**
     * Gets the value of the
     * stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StyleProperties }
     * {@link NumberText } {@link NumberNumber } {@link NumberCurrencySymbol } {@link StyleMap }
     * 
     * 
     */
    public List<Object> getStylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap() {
        if (this.stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap == null) {
            this.stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap = new ArrayList<Object>();
        }
        return this.stylePropertiesOrNumberTextOrNumberNumberOrNumberCurrencySymbolOrStyleMap;
    }

    /**
     * Gets the value of the styleVolatile property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleVolatile() {
        return this.styleVolatile;
    }

    /**
     * Sets the value of the numberAutomaticOrder property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberAutomaticOrder(final String value) {
        this.numberAutomaticOrder = value;
    }

    /**
     * Sets the value of the numberCountry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberCountry(final String value) {
        this.numberCountry = value;
    }

    /**
     * Sets the value of the numberLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberLanguage(final String value) {
        this.numberLanguage = value;
    }

    /**
     * Sets the value of the numberTitle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberTitle(final String value) {
        this.numberTitle = value;
    }

    /**
     * Sets the value of the numberTransliterationCountry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberTransliterationCountry(final String value) {
        this.numberTransliterationCountry = value;
    }

    /**
     * Sets the value of the numberTransliterationFormat property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberTransliterationFormat(final String value) {
        this.numberTransliterationFormat = value;
    }

    /**
     * Sets the value of the numberTransliterationLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberTransliterationLanguage(final String value) {
        this.numberTransliterationLanguage = value;
    }

    /**
     * Sets the value of the numberTransliterationStyle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberTransliterationStyle(final String value) {
        this.numberTransliterationStyle = value;
    }

    /**
     * Sets the value of the styleFamily property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleFamily(final String value) {
        this.styleFamily = value;
    }

    /**
     * Sets the value of the styleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleName(final String value) {
        this.styleName = value;
    }

    /**
     * Sets the value of the styleVolatile property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleVolatile(final String value) {
        this.styleVolatile = value;
    }

}
