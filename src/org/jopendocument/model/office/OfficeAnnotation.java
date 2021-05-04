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
public class OfficeAnnotation {

    protected String officeAuthor;
    protected String officeCreateDate;
    protected String officeCreateDateString;
    protected String officeDisplay;
    protected List<TextP> textP;

    /**
     * Gets the value of the officeAuthor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeAuthor() {
        return this.officeAuthor;
    }

    /**
     * Gets the value of the officeCreateDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeCreateDate() {
        return this.officeCreateDate;
    }

    /**
     * Gets the value of the officeCreateDateString property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeCreateDateString() {
        return this.officeCreateDateString;
    }

    /**
     * Gets the value of the officeDisplay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeDisplay() {
        if (this.officeDisplay == null) {
            return "false";
        } else {
            return this.officeDisplay;
        }
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
     * Sets the value of the officeAuthor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeAuthor(final String value) {
        this.officeAuthor = value;
    }

    /**
     * Sets the value of the officeCreateDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeCreateDate(final String value) {
        this.officeCreateDate = value;
    }

    /**
     * Sets the value of the officeCreateDateString property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeCreateDateString(final String value) {
        this.officeCreateDateString = value;
    }

    /**
     * Sets the value of the officeDisplay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeDisplay(final String value) {
        this.officeDisplay = value;
    }

}
