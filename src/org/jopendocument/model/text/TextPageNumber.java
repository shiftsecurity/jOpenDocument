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
public class TextPageNumber {

    protected String styleNumFormat;
    protected String styleNumLetterSync;
    protected String textPageAdjust;
    protected String textSelectPage;
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
     * Gets the value of the textPageAdjust property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextPageAdjust() {
        return this.textPageAdjust;
    }

    /**
     * Gets the value of the textSelectPage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSelectPage() {
        if (this.textSelectPage == null) {
            return "current";
        } else {
            return this.textSelectPage;
        }
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
     * Sets the value of the textPageAdjust property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextPageAdjust(final String value) {
        this.textPageAdjust = value;
    }

    /**
     * Sets the value of the textSelectPage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSelectPage(final String value) {
        this.textSelectPage = value;
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
