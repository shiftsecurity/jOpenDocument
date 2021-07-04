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

import org.jopendocument.model.form.FormForm;

/**
 * 
 */
public class OfficeForms {

    protected String formApplyDesignMode;
    protected String formAutomaticFocus;
    protected List<FormForm> formForm;

    /**
     * Gets the value of the formApplyDesignMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormApplyDesignMode() {
        if (this.formApplyDesignMode == null) {
            return "true";
        } else {
            return this.formApplyDesignMode;
        }
    }

    /**
     * Gets the value of the formAutomaticFocus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormAutomaticFocus() {
        if (this.formAutomaticFocus == null) {
            return "false";
        } else {
            return this.formAutomaticFocus;
        }
    }

    /**
     * Gets the value of the formForm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the formForm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormForm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormForm }
     * 
     * 
     */
    public List<FormForm> getFormForm() {
        if (this.formForm == null) {
            this.formForm = new ArrayList<FormForm>();
        }
        return this.formForm;
    }

    /**
     * Sets the value of the formApplyDesignMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormApplyDesignMode(final String value) {
        this.formApplyDesignMode = value;
    }

    /**
     * Sets the value of the formAutomaticFocus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormAutomaticFocus(final String value) {
        this.formAutomaticFocus = value;
    }

}
