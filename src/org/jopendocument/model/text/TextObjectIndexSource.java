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
public class TextObjectIndexSource {

    protected String foCountry;
    protected String foLanguage;
    protected String textIndexScope;
    protected TextIndexTitleTemplate textIndexTitleTemplate;
    protected TextObjectIndexEntryTemplate textObjectIndexEntryTemplate;
    protected String textRelativeTabStopPosition;
    protected String textSortAlgorithm;
    protected String textUseChartObjects;
    protected String textUseDrawObjects;
    protected String textUseMathObjects;
    protected String textUseOtherObjects;
    protected String textUseSpreadsheetObjects;

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
     * Gets the value of the textObjectIndexEntryTemplate property.
     * 
     * @return possible object is {@link TextObjectIndexEntryTemplate }
     * 
     */
    public TextObjectIndexEntryTemplate getTextObjectIndexEntryTemplate() {
        return this.textObjectIndexEntryTemplate;
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
     * Gets the value of the textUseChartObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseChartObjects() {
        if (this.textUseChartObjects == null) {
            return "false";
        } else {
            return this.textUseChartObjects;
        }
    }

    /**
     * Gets the value of the textUseDrawObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseDrawObjects() {
        if (this.textUseDrawObjects == null) {
            return "false";
        } else {
            return this.textUseDrawObjects;
        }
    }

    /**
     * Gets the value of the textUseMathObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseMathObjects() {
        if (this.textUseMathObjects == null) {
            return "false";
        } else {
            return this.textUseMathObjects;
        }
    }

    /**
     * Gets the value of the textUseOtherObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseOtherObjects() {
        if (this.textUseOtherObjects == null) {
            return "false";
        } else {
            return this.textUseOtherObjects;
        }
    }

    /**
     * Gets the value of the textUseSpreadsheetObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseSpreadsheetObjects() {
        if (this.textUseSpreadsheetObjects == null) {
            return "false";
        } else {
            return this.textUseSpreadsheetObjects;
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
     * Sets the value of the textObjectIndexEntryTemplate property.
     * 
     * @param value allowed object is {@link TextObjectIndexEntryTemplate }
     * 
     */
    public void setTextObjectIndexEntryTemplate(final TextObjectIndexEntryTemplate value) {
        this.textObjectIndexEntryTemplate = value;
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
     * Sets the value of the textUseChartObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseChartObjects(final String value) {
        this.textUseChartObjects = value;
    }

    /**
     * Sets the value of the textUseDrawObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseDrawObjects(final String value) {
        this.textUseDrawObjects = value;
    }

    /**
     * Sets the value of the textUseMathObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseMathObjects(final String value) {
        this.textUseMathObjects = value;
    }

    /**
     * Sets the value of the textUseOtherObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseOtherObjects(final String value) {
        this.textUseOtherObjects = value;
    }

    /**
     * Sets the value of the textUseSpreadsheetObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseSpreadsheetObjects(final String value) {
        this.textUseSpreadsheetObjects = value;
    }

}
