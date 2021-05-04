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
public class TextS {

    protected String textC;

    /**
     * Gets the value of the textC property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextC() {
        if (this.textC == null) {
            return "1";
        } else {
            return this.textC;
        }
    }

    /**
     * Gets the number of spaces
     * */
    public int getSpaceCount() {
        if (this.textC == null)
            return 1;
        else
            return Integer.parseInt(this.textC);
    }

    /**
     * Sets the value of the textC property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextC(final String value) {
        this.textC = value;
    }

}
