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
public class TextAlphabeticalIndexSource {

    protected String foCountry;
    protected String foLanguage;
    protected List<TextAlphabeticalIndexEntryTemplate> textAlphabeticalIndexEntryTemplate;
    protected String textAlphabeticalSeparators;
    protected String textCapitalizeEntries;
    protected String textCombineEntries;
    protected String textCombineEntriesWithDash;
    protected String textCombineEntriesWithPp;
    protected String textCommaSeparated;
    protected String textIgnoreCase;
    protected String textIndexScope;
    protected TextIndexTitleTemplate textIndexTitleTemplate;
    protected String textMainEntryStyleName;
    protected String textRelativeTabStopPosition;
    protected String textSortAlgorithm;
    protected String textUseKeysAsEntries;

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
     * Gets the value of the textAlphabeticalIndexEntryTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textAlphabeticalIndexEntryTemplate
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextAlphabeticalIndexEntryTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextAlphabeticalIndexEntryTemplate }
     * 
     * 
     */
    public List<TextAlphabeticalIndexEntryTemplate> getTextAlphabeticalIndexEntryTemplate() {
        if (this.textAlphabeticalIndexEntryTemplate == null) {
            this.textAlphabeticalIndexEntryTemplate = new ArrayList<TextAlphabeticalIndexEntryTemplate>();
        }
        return this.textAlphabeticalIndexEntryTemplate;
    }

    /**
     * Gets the value of the textAlphabeticalSeparators property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextAlphabeticalSeparators() {
        if (this.textAlphabeticalSeparators == null) {
            return "false";
        } else {
            return this.textAlphabeticalSeparators;
        }
    }

    /**
     * Gets the value of the textCapitalizeEntries property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCapitalizeEntries() {
        if (this.textCapitalizeEntries == null) {
            return "false";
        } else {
            return this.textCapitalizeEntries;
        }
    }

    /**
     * Gets the value of the textCombineEntries property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCombineEntries() {
        if (this.textCombineEntries == null) {
            return "true";
        } else {
            return this.textCombineEntries;
        }
    }

    /**
     * Gets the value of the textCombineEntriesWithDash property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCombineEntriesWithDash() {
        if (this.textCombineEntriesWithDash == null) {
            return "false";
        } else {
            return this.textCombineEntriesWithDash;
        }
    }

    /**
     * Gets the value of the textCombineEntriesWithPp property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCombineEntriesWithPp() {
        if (this.textCombineEntriesWithPp == null) {
            return "true";
        } else {
            return this.textCombineEntriesWithPp;
        }
    }

    /**
     * Gets the value of the textCommaSeparated property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCommaSeparated() {
        if (this.textCommaSeparated == null) {
            return "false";
        } else {
            return this.textCommaSeparated;
        }
    }

    /**
     * Gets the value of the textIgnoreCase property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIgnoreCase() {
        if (this.textIgnoreCase == null) {
            return "false";
        } else {
            return this.textIgnoreCase;
        }
    }

    /**
     * Gets the value of the textIndexScope property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIndexScope() {
        if (this.textIndexScope == null) {
            return "document";
        } else {
            return this.textIndexScope;
        }
    }

    /**
     * Gets the value of the textIndexTitleTemplate property.
     * 
     * @return possible object is {@link TextIndexTitleTemplate }
     * 
     */
    public TextIndexTitleTemplate getTextIndexTitleTemplate() {
        return this.textIndexTitleTemplate;
    }

    /**
     * Gets the value of the textMainEntryStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextMainEntryStyleName() {
        return this.textMainEntryStyleName;
    }

    /**
     * Gets the value of the textRelativeTabStopPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextRelativeTabStopPosition() {
        if (this.textRelativeTabStopPosition == null) {
            return "true";
        } else {
            return this.textRelativeTabStopPosition;
        }
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
     * Gets the value of the textUseKeysAsEntries property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseKeysAsEntries() {
        if (this.textUseKeysAsEntries == null) {
            return "false";
        } else {
            return this.textUseKeysAsEntries;
        }
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
     * Sets the value of the textAlphabeticalSeparators property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextAlphabeticalSeparators(final String value) {
        this.textAlphabeticalSeparators = value;
    }

    /**
     * Sets the value of the textCapitalizeEntries property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCapitalizeEntries(final String value) {
        this.textCapitalizeEntries = value;
    }

    /**
     * Sets the value of the textCombineEntries property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCombineEntries(final String value) {
        this.textCombineEntries = value;
    }

    /**
     * Sets the value of the textCombineEntriesWithDash property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCombineEntriesWithDash(final String value) {
        this.textCombineEntriesWithDash = value;
    }

    /**
     * Sets the value of the textCombineEntriesWithPp property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCombineEntriesWithPp(final String value) {
        this.textCombineEntriesWithPp = value;
    }

    /**
     * Sets the value of the textCommaSeparated property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCommaSeparated(final String value) {
        this.textCommaSeparated = value;
    }

    /**
     * Sets the value of the textIgnoreCase property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIgnoreCase(final String value) {
        this.textIgnoreCase = value;
    }

    /**
     * Sets the value of the textIndexScope property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIndexScope(final String value) {
        this.textIndexScope = value;
    }

    /**
     * Sets the value of the textIndexTitleTemplate property.
     * 
     * @param value allowed object is {@link TextIndexTitleTemplate }
     * 
     */
    public void setTextIndexTitleTemplate(final TextIndexTitleTemplate value) {
        this.textIndexTitleTemplate = value;
    }

    /**
     * Sets the value of the textMainEntryStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextMainEntryStyleName(final String value) {
        this.textMainEntryStyleName = value;
    }

    /**
     * Sets the value of the textRelativeTabStopPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextRelativeTabStopPosition(final String value) {
        this.textRelativeTabStopPosition = value;
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
     * Sets the value of the textUseKeysAsEntries property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseKeysAsEntries(final String value) {
        this.textUseKeysAsEntries = value;
    }

}
