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
public class TextLinenumberingConfiguration {

    protected String styleNumFormat;
    protected String styleNumLetterSync;
    protected String textCountEmptyLines;
    protected String textCountInFloatingFrames;
    protected String textIncrement;
    protected TextLinenumberingSeparator textLinenumberingSeparator;
    protected String textNumberLines;
    protected String textNumberPosition;
    protected String textOffset;
    protected String textRestartNumbering;
    protected String textStyleName;

    /**
     * Gets the value of the styleNumFormat property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNumFormat() {
        if (this.styleNumFormat == null) {
            return "1";
        } else {
            return this.styleNumFormat;
        }
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
     * Gets the value of the textCountEmptyLines property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCountEmptyLines() {
        if (this.textCountEmptyLines == null) {
            return "true";
        } else {
            return this.textCountEmptyLines;
        }
    }

    /**
     * Gets the value of the textCountInFloatingFrames property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCountInFloatingFrames() {
        if (this.textCountInFloatingFrames == null) {
            return "false";
        } else {
            return this.textCountInFloatingFrames;
        }
    }

    /**
     * Gets the value of the textIncrement property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIncrement() {
        return this.textIncrement;
    }

    /**
     * Gets the value of the textLinenumberingSeparator property.
     * 
     * @return possible object is {@link TextLinenumberingSeparator }
     * 
     */
    public TextLinenumberingSeparator getTextLinenumberingSeparator() {
        return this.textLinenumberingSeparator;
    }

    /**
     * Gets the value of the textNumberLines property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextNumberLines() {
        if (this.textNumberLines == null) {
            return "true";
        } else {
            return this.textNumberLines;
        }
    }

    /**
     * Gets the value of the textNumberPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextNumberPosition() {
        if (this.textNumberPosition == null) {
            return "left";
        } else {
            return this.textNumberPosition;
        }
    }

    /**
     * Gets the value of the textOffset property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextOffset() {
        return this.textOffset;
    }

    /**
     * Gets the value of the textRestartNumbering property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextRestartNumbering() {
        if (this.textRestartNumbering == null) {
            return "false";
        } else {
            return this.textRestartNumbering;
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
     * Sets the value of the textCountEmptyLines property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCountEmptyLines(final String value) {
        this.textCountEmptyLines = value;
    }

    /**
     * Sets the value of the textCountInFloatingFrames property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCountInFloatingFrames(final String value) {
        this.textCountInFloatingFrames = value;
    }

    /**
     * Sets the value of the textIncrement property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIncrement(final String value) {
        this.textIncrement = value;
    }

    /**
     * Sets the value of the textLinenumberingSeparator property.
     * 
     * @param value allowed object is {@link TextLinenumberingSeparator }
     * 
     */
    public void setTextLinenumberingSeparator(final TextLinenumberingSeparator value) {
        this.textLinenumberingSeparator = value;
    }

    /**
     * Sets the value of the textNumberLines property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextNumberLines(final String value) {
        this.textNumberLines = value;
    }

    /**
     * Sets the value of the textNumberPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextNumberPosition(final String value) {
        this.textNumberPosition = value;
    }

    /**
     * Sets the value of the textOffset property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextOffset(final String value) {
        this.textOffset = value;
    }

    /**
     * Sets the value of the textRestartNumbering property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextRestartNumbering(final String value) {
        this.textRestartNumbering = value;
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

}
