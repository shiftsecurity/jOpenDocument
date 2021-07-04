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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TextBibliographyConfiguration {

    protected String foCountry;
    protected String foLanguage;
    protected String textNumberedEntries;
    protected String textPrefix;
    protected String textSortAlgorithm;
    protected String textSortByPosition;
    protected List<TextSortKey> textSortKey;
    protected String textSuffix;

    /**
     * Gets the value of the foCountry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoCountry() {
        return this.foCountry;
    }

    /**
     * Gets the value of the foLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoLanguage() {
        return this.foLanguage;
    }

    /**
     * Gets the value of the textNumberedEntries property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextNumberedEntries() {
        if (this.textNumberedEntries == null) {
            return "false";
        } else {
            return this.textNumberedEntries;
        }
    }

    /**
     * Gets the value of the textPrefix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextPrefix() {
        return this.textPrefix;
    }

    /**
     * Gets the value of the textSortAlgorithm property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSortAlgorithm() {
        return this.textSortAlgorithm;
    }

    /**
     * Gets the value of the textSortByPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSortByPosition() {
        if (this.textSortByPosition == null) {
            return "true";
        } else {
            return this.textSortByPosition;
        }
    }

    /**
     * Gets the value of the textSortKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textSortKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextSortKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextSortKey }
     * 
     * 
     */
    public List<TextSortKey> getTextSortKey() {
        if (this.textSortKey == null) {
            this.textSortKey = new ArrayList<TextSortKey>();
        }
        return this.textSortKey;
    }

    /**
     * Gets the value of the textSuffix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSuffix() {
        return this.textSuffix;
    }

    /**
     * Sets the value of the foCountry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoCountry(final String value) {
        this.foCountry = value;
    }

    /**
     * Sets the value of the foLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoLanguage(final String value) {
        this.foLanguage = value;
    }

    /**
     * Sets the value of the textNumberedEntries property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextNumberedEntries(final String value) {
        this.textNumberedEntries = value;
    }

    /**
     * Sets the value of the textPrefix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextPrefix(final String value) {
        this.textPrefix = value;
    }

    /**
     * Sets the value of the textSortAlgorithm property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSortAlgorithm(final String value) {
        this.textSortAlgorithm = value;
    }

    /**
     * Sets the value of the textSortByPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSortByPosition(final String value) {
        this.textSortByPosition = value;
    }

    /**
     * Sets the value of the textSuffix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSuffix(final String value) {
        this.textSuffix = value;
    }

}
