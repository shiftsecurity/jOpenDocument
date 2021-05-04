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
public class TextTableIndexSource {

    protected String foCountry;
    protected String foLanguage;
    protected String textCaptionSequenceFormat;
    protected String textCaptionSequenceName;
    protected String textIndexScope;
    protected TextIndexTitleTemplate textIndexTitleTemplate;
    protected String textRelativeTabStopPosition;
    protected String textSortAlgorithm;
    protected TextTableIndexEntryTemplate textTableIndexEntryTemplate;
    protected String textUseCaption;

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
     * Gets the value of the textCaptionSequenceFormat property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCaptionSequenceFormat() {
        if (this.textCaptionSequenceFormat == null) {
            return "text";
        } else {
            return this.textCaptionSequenceFormat;
        }
    }

    /**
     * Gets the value of the textCaptionSequenceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCaptionSequenceName() {
        return this.textCaptionSequenceName;
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
     * Gets the value of the textTableIndexEntryTemplate property.
     * 
     * @return possible object is {@link TextTableIndexEntryTemplate }
     * 
     */
    public TextTableIndexEntryTemplate getTextTableIndexEntryTemplate() {
        return this.textTableIndexEntryTemplate;
    }

    /**
     * Gets the value of the textUseCaption property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseCaption() {
        if (this.textUseCaption == null) {
            return "true";
        } else {
            return this.textUseCaption;
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
     * Sets the value of the textCaptionSequenceFormat property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCaptionSequenceFormat(final String value) {
        this.textCaptionSequenceFormat = value;
    }

    /**
     * Sets the value of the textCaptionSequenceName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCaptionSequenceName(final String value) {
        this.textCaptionSequenceName = value;
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
     * Sets the value of the textTableIndexEntryTemplate property.
     * 
     * @param value allowed object is {@link TextTableIndexEntryTemplate }
     * 
     */
    public void setTextTableIndexEntryTemplate(final TextTableIndexEntryTemplate value) {
        this.textTableIndexEntryTemplate = value;
    }

    /**
     * Sets the value of the textUseCaption property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseCaption(final String value) {
        this.textUseCaption = value;
    }

}
