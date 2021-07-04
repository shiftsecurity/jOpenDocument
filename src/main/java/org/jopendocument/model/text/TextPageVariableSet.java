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
public class TextPageVariableSet {

    protected String textActive;
    protected String textPageAdjust;

    /**
     * Gets the value of the textActive property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextActive() {
        if (this.textActive == null) {
            return "true";
        } else {
            return this.textActive;
        }
    }

    /**
     * Gets the value of the textPageAdjust property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextPageAdjust() {
        if (this.textPageAdjust == null) {
            return "0";
        } else {
            return this.textPageAdjust;
        }
    }

    /**
     * Sets the value of the textActive property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextActive(final String value) {
        this.textActive = value;
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

}
