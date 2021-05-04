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

import org.jopendocument.model.office.OfficeEvents;

/**
 * 
 */
public class FormRadio {

    protected String formCurrentSelected;
    protected String formDataField;
    protected String formDisabled;
    protected String formImageAlign;
    protected String formImagePosition;
    protected String formLabel;
    protected String formLinkedCell;
    protected String formPrintable;
    protected FormProperties formProperties;
    protected String formSelected;
    protected String formTabIndex;
    protected String formTabStop;
    protected String formTitle;
    protected String formValue;
    protected String formVisualEffect;
    protected OfficeEvents officeEvents;

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
     * Gets the value of the formDataField property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormDataField() {
        return this.formDataField;
    }

    /**
     * Gets the value of the formDisabled property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormDisabled() {
        if (this.formDisabled == null) {
            return "false";
        } else {
            return this.formDisabled;
        }
    }

    /**
     * Gets the value of the formImageAlign property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormImageAlign() {
        if (this.formImageAlign == null) {
            return "center";
        } else {
            return this.formImageAlign;
        }
    }

    /**
     * Gets the value of the formImagePosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormImagePosition() {
        if (this.formImagePosition == null) {
            return "center";
        } else {
            return this.formImagePosition;
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
     * Gets the value of the formLinkedCell property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormLinkedCell() {
        return this.formLinkedCell;
    }

    /**
     * Gets the value of the formPrintable property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormPrintable() {
        if (this.formPrintable == null) {
            return "true";
        } else {
            return this.formPrintable;
        }
    }

    /**
     * Gets the value of the formProperties property.
     * 
     * @return possible object is {@link FormProperties }
     * 
     */
    public FormProperties getFormProperties() {
        return this.formProperties;
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
     * Gets the value of the formTabIndex property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormTabIndex() {
        return this.formTabIndex;
    }

    /**
     * Gets the value of the formTabStop property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormTabStop() {
        if (this.formTabStop == null) {
            return "true";
        } else {
            return this.formTabStop;
        }
    }

    /**
     * Gets the value of the formTitle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormTitle() {
        return this.formTitle;
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
     * Gets the value of the formVisualEffect property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormVisualEffect() {
        return this.formVisualEffect;
    }

    /**
     * Gets the value of the officeEvents property.
     * 
     * @return possible object is {@link OfficeEvents }
     * 
     */
    public OfficeEvents getOfficeEvents() {
        return this.officeEvents;
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
     * Sets the value of the formDataField property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormDataField(final String value) {
        this.formDataField = value;
    }

    /**
     * Sets the value of the formDisabled property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormDisabled(final String value) {
        this.formDisabled = value;
    }

    /**
     * Sets the value of the formImageAlign property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormImageAlign(final String value) {
        this.formImageAlign = value;
    }

    /**
     * Sets the value of the formImagePosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormImagePosition(final String value) {
        this.formImagePosition = value;
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
     * Sets the value of the formLinkedCell property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormLinkedCell(final String value) {
        this.formLinkedCell = value;
    }

    /**
     * Sets the value of the formPrintable property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormPrintable(final String value) {
        this.formPrintable = value;
    }

    /**
     * Sets the value of the formProperties property.
     * 
     * @param value allowed object is {@link FormProperties }
     * 
     */
    public void setFormProperties(final FormProperties value) {
        this.formProperties = value;
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
     * Sets the value of the formTabIndex property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormTabIndex(final String value) {
        this.formTabIndex = value;
    }

    /**
     * Sets the value of the formTabStop property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormTabStop(final String value) {
        this.formTabStop = value;
    }

    /**
     * Sets the value of the formTitle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormTitle(final String value) {
        this.formTitle = value;
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
     * Sets the value of the formVisualEffect property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormVisualEffect(final String value) {
        this.formVisualEffect = value;
    }

    /**
     * Sets the value of the officeEvents property.
     * 
     * @param value allowed object is {@link OfficeEvents }
     * 
     */
    public void setOfficeEvents(final OfficeEvents value) {
        this.officeEvents = value;
    }

}
