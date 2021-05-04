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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TextIndexSourceStyles {

    protected List<TextIndexSourceStyle> textIndexSourceStyle;
    protected String textOutlineLevel;

    /**
     * Gets the value of the textIndexSourceStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textIndexSourceStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextIndexSourceStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextIndexSourceStyle }
     * 
     * 
     */
    public List<TextIndexSourceStyle> getTextIndexSourceStyle() {
        if (this.textIndexSourceStyle == null) {
            this.textIndexSourceStyle = new ArrayList<TextIndexSourceStyle>();
        }
        return this.textIndexSourceStyle;
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
     * Sets the value of the textOutlineLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextOutlineLevel(final String value) {
        this.textOutlineLevel = value;
    }

}
