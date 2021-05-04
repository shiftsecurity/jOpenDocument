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
public class TextRuby {

    protected String textRubyBase;
    protected TextRubyText textRubyText;
    protected String textStyleName;

    /**
     * Gets the value of the textRubyBase property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextRubyBase() {
        return this.textRubyBase;
    }

    /**
     * Gets the value of the textRubyText property.
     * 
     * @return possible object is {@link TextRubyText }
     * 
     */
    public TextRubyText getTextRubyText() {
        return this.textRubyText;
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
     * Sets the value of the textRubyBase property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextRubyBase(final String value) {
        this.textRubyBase = value;
    }

    /**
     * Sets the value of the textRubyText property.
     * 
     * @param value allowed object is {@link TextRubyText }
     * 
     */
    public void setTextRubyText(final TextRubyText value) {
        this.textRubyText = value;
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
