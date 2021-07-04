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

import org.jopendocument.model.style.StyleListLevelProperties;
import org.jopendocument.model.style.StyleProperties;

/**
 * 
 */
public class TextListLevelStyleNumber {

    protected String styleNumFormat;
    protected String styleNumLetterSync;
    protected String styleNumPrefix;
    protected String styleNumSuffix;
    protected StyleProperties styleProperties;
    protected String textDisplayLevels;
    protected String textLevel;
    protected String textStartValue;
    protected String textStyleName;
    private StyleListLevelProperties styleListLevelProperties;

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
        if (this.styleNumLetterSync == null) {
            return "false";
        } else {
            return this.styleNumLetterSync;
        }
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
     * Gets the value of the styleProperties property.
     * 
     * @return possible object is {@link StyleProperties }
     * 
     */
    public StyleProperties getStyleProperties() {
        return this.styleProperties;
    }

    /**
     * Gets the value of the textDisplayLevels property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDisplayLevels() {
        if (this.textDisplayLevels == null) {
            return "1";
        } else {
            return this.textDisplayLevels;
        }
    }

    /**
     * Gets the value of the textLevel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextLevel() {
        return this.textLevel;
    }

    /**
     * Gets the value of the textStartValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStartValue() {
        if (this.textStartValue == null) {
            return "1";
        } else {
            return this.textStartValue;
        }
    }

    /**
     * Gets the value of the textStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStyleName() {
        return this.textStyleName;
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
     * Sets the value of the styleProperties property.
     * 
     * @param value allowed object is {@link StyleProperties }
     * 
     */
    public void setStyleProperties(final StyleProperties value) {
        this.styleProperties = value;
    }

    /**
     * Sets the value of the textDisplayLevels property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDisplayLevels(final String value) {
        this.textDisplayLevels = value;
    }

    /**
     * Sets the value of the textLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextLevel(final String value) {
        this.textLevel = value;
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

    /**
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyleName(final String value) {
        this.textStyleName = value;
    }

    public void setStyleListLevelProperties(StyleListLevelProperties props) {
        styleListLevelProperties = props;
    }

    public StyleListLevelProperties getStyleListLevelProperties() {
        return styleListLevelProperties;
    }
}
