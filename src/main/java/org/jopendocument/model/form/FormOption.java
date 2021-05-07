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

/**
 * 
 */
public class FormOption {

    protected String formCurrentSelected;
    protected String formLabel;
    protected String formSelected;
    protected String formValue;
    protected String value;

    /**
     * Gets the value of the formCurrentSelected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormCurrentSelected() {
        if (this.formCurrentSelected == null) {
            return "false";
        } else {
            return this.formCurrentSelected;
        }
    }

    /**
     * Gets the value of the formLabel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormLabel() {
        return this.formLabel;
    }

    /**
     * Gets the value of the formSelected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormSelected() {
        if (this.formSelected == null) {
            return "false";
        } else {
            return this.formSelected;
        }
    }

    /**
     * Gets the value of the formValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormValue() {
        return this.formValue;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
    }

    /**
     * Sets the value of the formCurrentSelected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormCurrentSelected(final String value) {
        this.formCurrentSelected = value;
    }

    /**
     * Sets the value of the formLabel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormLabel(final String value) {
        this.formLabel = value;
    }

    /**
     * Sets the value of the formSelected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormSelected(final String value) {
        this.formSelected = value;
    }

    /**
     * Sets the value of the formValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormValue(final String value) {
        this.formValue = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setvalue(final String value) {
        this.value = value;
    }

}
