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

package org.jopendocument.model.form;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class FormProperty {

    protected String formPropertyIsList;
    protected String formPropertyName;
    protected String formPropertyType;
    protected List<FormPropertyValue> formPropertyValue;

    /**
     * Gets the value of the formPropertyIsList property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormPropertyIsList() {
        return this.formPropertyIsList;
    }

    /**
     * Gets the value of the formPropertyName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormPropertyName() {
        return this.formPropertyName;
    }

    /**
     * Gets the value of the formPropertyType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormPropertyType() {
        return this.formPropertyType;
    }

    /**
     * Gets the value of the formPropertyValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the formPropertyValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormPropertyValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormPropertyValue }
     * 
     * 
     */
    public List<FormPropertyValue> getFormPropertyValue() {
        if (this.formPropertyValue == null) {
            this.formPropertyValue = new ArrayList<FormPropertyValue>();
        }
        return this.formPropertyValue;
    }

    /**
     * Sets the value of the formPropertyIsList property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormPropertyIsList(final String value) {
        this.formPropertyIsList = value;
    }

    /**
     * Sets the value of the formPropertyName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormPropertyName(final String value) {
        this.formPropertyName = value;
    }

    /**
     * Sets the value of the formPropertyType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormPropertyType(final String value) {
        this.formPropertyType = value;
    }

}
