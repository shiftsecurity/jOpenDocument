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
public class TextDatabaseRowNumber {

    protected String styleNumFormat;
    protected String styleNumLetterSync;
    protected String textDatabaseName;
    protected String textDisplay;
    protected String textTableName;
    protected String textTableType;
    protected String textValue;
    protected String value;

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
     * Gets the value of the textDatabaseName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDatabaseName() {
        return this.textDatabaseName;
    }

    /**
     * Gets the value of the textDisplay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDisplay() {
        return this.textDisplay;
    }

    /**
     * Gets the value of the textTableName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTableName() {
        return this.textTableName;
    }

    /**
     * Gets the value of the textTableType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTableType() {
        return this.textTableType;
    }

    /**
     * Gets the value of the textValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextValue() {
        return this.textValue;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
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
     * Sets the value of the textDatabaseName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDatabaseName(final String value) {
        this.textDatabaseName = value;
    }

    /**
     * Sets the value of the textDisplay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDisplay(final String value) {
        this.textDisplay = value;
    }

    /**
     * Sets the value of the textTableName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTableName(final String value) {
        this.textTableName = value;
    }

    /**
     * Sets the value of the textTableType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTableType(final String value) {
        this.textTableType = value;
    }

    /**
     * Sets the value of the textValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextValue(final String value) {
        this.textValue = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setvalue(final String value) {
        this.value = value;
    }

}
