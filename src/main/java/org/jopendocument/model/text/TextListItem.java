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
public class TextListItem {

    protected String textRestartNumbering;
    protected String textStartValue;
    private List<Object> elements=new ArrayList<Object>();

    /**
     * Gets the value of the textPOrTextHOrTextOrderedListOrTextUnorderedList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textPOrTextHOrTextOrderedListOrTextUnorderedList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextPOrTextHOrTextOrderedListOrTextUnorderedList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextP } {@link TextH }
     * {@link TextOrderedList } {@link TextUnorderedList }
     * 
     * 
     */
    public List<Object> getElements() {
      
        return this.elements;
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
     * Gets the value of the textStartValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStartValue() {
        return this.textStartValue;
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
     * Sets the value of the textStartValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStartValue(final String value) {
        this.textStartValue = value;
    }

    public void addTextElement(Object p) {
        this.elements.add(p);
    }

}
