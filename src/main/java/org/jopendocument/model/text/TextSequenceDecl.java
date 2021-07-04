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
public class TextSequenceDecl {

    protected String textDisplayOutlineLevel;
    protected String textName;
    protected String textSeparationCharacter;

    /**
     * Gets the value of the textDisplayOutlineLevel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDisplayOutlineLevel() {
        if (this.textDisplayOutlineLevel == null) {
            return "0";
        } else {
            return this.textDisplayOutlineLevel;
        }
    }

    /**
     * Gets the value of the textName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextName() {
        return this.textName;
    }

    /**
     * Gets the value of the textSeparationCharacter property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSeparationCharacter() {
        if (this.textSeparationCharacter == null) {
            return ".";
        } else {
            return this.textSeparationCharacter;
        }
    }

    /**
     * Sets the value of the textDisplayOutlineLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDisplayOutlineLevel(final String value) {
        this.textDisplayOutlineLevel = value;
    }

    /**
     * Sets the value of the textName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextName(final String value) {
        this.textName = value;
    }

    /**
     * Sets the value of the textSeparationCharacter property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSeparationCharacter(final String value) {
        this.textSeparationCharacter = value;
    }

}
