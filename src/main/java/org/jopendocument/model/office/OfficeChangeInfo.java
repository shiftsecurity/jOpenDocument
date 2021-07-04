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

package org.jopendocument.model.office;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.text.TextP;

/**
 * 
 */
public class OfficeChangeInfo {

    protected String officeChgAuthor;
    protected String officeChgDateTime;
    protected List<TextP> textP;

    /**
     * Gets the value of the officeChgAuthor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeChgAuthor() {
        return this.officeChgAuthor;
    }

    /**
     * Gets the value of the officeChgDateTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeChgDateTime() {
        return this.officeChgDateTime;
    }

    /**
     * Gets the value of the textP property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textP property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextP }
     * 
     * 
     */
    public List<TextP> getTextP() {
        if (this.textP == null) {
            this.textP = new ArrayList<TextP>();
        }
        return this.textP;
    }

    /**
     * Sets the value of the officeChgAuthor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeChgAuthor(final String value) {
        this.officeChgAuthor = value;
    }

    /**
     * Sets the value of the officeChgDateTime property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeChgDateTime(final String value) {
        this.officeChgDateTime = value;
    }

}
