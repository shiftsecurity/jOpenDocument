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
public class TextUserIndexMarkStart {

    protected String textId;
    protected String textIndexName;
    protected String textOutlineLevel;

    /**
     * Gets the value of the textId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextId() {
        return this.textId;
    }

    /**
     * Gets the value of the textIndexName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIndexName() {
        return this.textIndexName;
    }

    /**
     * Gets the value of the textOutlineLevel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextOutlineLevel() {
        return this.textOutlineLevel;
    }

    /**
     * Sets the value of the textId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextId(final String value) {
        this.textId = value;
    }

    /**
     * Sets the value of the textIndexName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIndexName(final String value) {
        this.textIndexName = value;
    }

    /**
     * Sets the value of the textOutlineLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextOutlineLevel(final String value) {
        this.textOutlineLevel = value;
    }

}
