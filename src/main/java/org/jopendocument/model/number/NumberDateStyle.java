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
import org.jopendocument.model.style.StyleStyle;

/**
 * 
 */
public class NumberDateStyle extends StyleStyle {

    private String numberAutomaticOrder;
    private String numberCountry;
    private String numberFormatSource;
    private String numberLanguage;
    private List<Object> numberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds = new ArrayList<Object>();;
    private String numberTitle;
    private String numberTransliterationCountry;
    private String numberTransliterationFormat;
    private String numberTransliterationLanguage;
    private String numberTransliterationStyle;

    private List<StyleMap> styleMap;

    private String styleVolatile;

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
     * Gets the value of the numberFormatSource property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberFormatSource() {
        if (this.numberFormatSource == null) {
            return "fixed";
        } else {
            return this.numberFormatSource;
        }
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
     * GetsthevalueofthenumberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE>methodforthenumberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getNumberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link NumberText } {@link NumberDay }
     * {@link NumberMonth } {@link NumberYear } {@link NumberEra } {@link NumberDayOfWeek }
     * {@link NumberWeekOfYear } {@link NumberQuarter } {@link NumberHours } {@link NumberAmPm }
     * {@link NumberMinutes } {@link NumberSeconds }
     * 
     * 
     */
    public List<Object> getNumberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds() {
        if (this.numberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds == null) {
            this.numberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds = new ArrayList<Object>();
        }
        return this.numberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds;
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
     * Gets the value of the styleMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the styleMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStyleMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StyleMap }
     * 
     * 
     */
    public List<StyleMap> getStyleMap() {
        if (this.styleMap == null) {
            this.styleMap = new ArrayList<StyleMap>();
        }
        return this.styleMap;
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
     * Gets the value of the styleProperties property.
     * 
     * @return possible object is {@link StyleProperties }
     * 
     */
    public StyleProperties getStyleProperties() {
        return this.styleProperties;
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
     * Sets the value of the numberFormatSource property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberFormatSource(final String value) {
        this.numberFormatSource = value;
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
     * Sets the value of the styleVolatile property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleVolatile(final String value) {
        this.styleVolatile = value;
    }

    public void addElement(Object e) {
        this.numberTextOrNumberDayOrNumberMonthOrNumberYearOrNumberEraOrNumberDayOfWeekOrNumberWeekOfYearOrNumberQuarterOrNumberHoursOrNumberAmPmOrNumberMinutesOrNumberSeconds.add(e);

    }

}
