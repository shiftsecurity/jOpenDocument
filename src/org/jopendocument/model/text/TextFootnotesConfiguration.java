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
public class TextFootnotesConfiguration {

    protected String styleNumFormat;
    protected String styleNumLetterSync;
    protected String styleNumPrefix;
    protected String styleNumSuffix;
    protected String textCitationBodyStyleName;
    protected String textCitationStyleName;
    protected String textDefaultStyleName;
    protected String textFootnoteContinuationNoticeBackward;
    protected String textFootnoteContinuationNoticeForward;
    protected String textFootnotesPosition;
    protected String textMasterPageName;
    protected String textStartNumberingAt;
    protected String textStartValue;

    /**
     * Gets the value of the styleNumFormat property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumFormat() {
        return this.styleNumFormat;
    }

    /**
     * Gets the value of the styleNumLetterSync property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumLetterSync() {
        return this.styleNumLetterSync;
    }

    /**
     * Gets the value of the styleNumPrefix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumPrefix() {
        return this.styleNumPrefix;
    }

    /**
     * Gets the value of the styleNumSuffix property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumSuffix() {
        return this.styleNumSuffix;
    }

    /**
     * Gets the value of the textCitationBodyStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCitationBodyStyleName() {
        return this.textCitationBodyStyleName;
    }

    /**
     * Gets the value of the textCitationStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCitationStyleName() {
        return this.textCitationStyleName;
    }

    /**
     * Gets the value of the textDefaultStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDefaultStyleName() {
        return this.textDefaultStyleName;
    }

    /**
     * Gets the value of the textFootnoteContinuationNoticeBackward property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextFootnoteContinuationNoticeBackward() {
        return this.textFootnoteContinuationNoticeBackward;
    }

    /**
     * Gets the value of the textFootnoteContinuationNoticeForward property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextFootnoteContinuationNoticeForward() {
        return this.textFootnoteContinuationNoticeForward;
    }

    /**
     * Gets the value of the textFootnotesPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextFootnotesPosition() {
        if (this.textFootnotesPosition == null) {
            return "page";
        } else {
            return this.textFootnotesPosition;
        }
    }

    /**
     * Gets the value of the textMasterPageName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextMasterPageName() {
        return this.textMasterPageName;
    }

    /**
     * Gets the value of the textStartNumberingAt property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStartNumberingAt() {
        if (this.textStartNumberingAt == null) {
            return "document";
        } else {
            return this.textStartNumberingAt;
        }
    }

    /**
     * Gets the value of the textStartValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStartValue() {
        return this.textStartValue;
    }

    /**
     * Sets the value of the styleNumFormat property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumFormat(final String value) {
        this.styleNumFormat = value;
    }

    /**
     * Sets the value of the styleNumLetterSync property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumLetterSync(final String value) {
        this.styleNumLetterSync = value;
    }

    /**
     * Sets the value of the styleNumPrefix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumPrefix(final String value) {
        this.styleNumPrefix = value;
    }

    /**
     * Sets the value of the styleNumSuffix property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNumSuffix(final String value) {
        this.styleNumSuffix = value;
    }

    /**
     * Sets the value of the textCitationBodyStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCitationBodyStyleName(final String value) {
        this.textCitationBodyStyleName = value;
    }

    /**
     * Sets the value of the textCitationStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCitationStyleName(final String value) {
        this.textCitationStyleName = value;
    }

    /**
     * Sets the value of the textDefaultStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDefaultStyleName(final String value) {
        this.textDefaultStyleName = value;
    }

    /**
     * Sets the value of the textFootnoteContinuationNoticeBackward property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextFootnoteContinuationNoticeBackward(final String value) {
        this.textFootnoteContinuationNoticeBackward = value;
    }

    /**
     * Sets the value of the textFootnoteContinuationNoticeForward property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextFootnoteContinuationNoticeForward(final String value) {
        this.textFootnoteContinuationNoticeForward = value;
    }

    /**
     * Sets the value of the textFootnotesPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextFootnotesPosition(final String value) {
        this.textFootnotesPosition = value;
    }

    /**
     * Sets the value of the textMasterPageName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextMasterPageName(final String value) {
        this.textMasterPageName = value;
    }

    /**
     * Sets the value of the textStartNumberingAt property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStartNumberingAt(final String value) {
        this.textStartNumberingAt = value;
    }

    /**
     * Sets the value of the textStartValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStartValue(final String value) {
        this.textStartValue = value;
    }

}
