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

import org.jopendocument.model.office.OfficeEvents;

/**
 * 
 */
public class FormCombobox {

    protected String formAutoComplete;
    protected String formConvertEmptyToNull;
    protected String formCurrentValue;
    protected String formDataField;
    protected String formDisabled;
    protected String formDropdown;
    protected List<FormItem> formItem;
    protected String formLinkedCell;
    protected String formListSource;
    protected String formListSourceType;
    protected String formMaxLength;
    protected String formPrintable;
    protected FormProperties formProperties;
    protected String formReadonly;
    protected String formSize;
    protected String formSourceCellRange;
    protected String formTabIndex;
    protected String formTabStop;
    protected String formTitle;
    protected String formValue;
    protected OfficeEvents officeEvents;

    /**
     * Gets the value of the formAutoComplete property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormAutoComplete() {
        return this.formAutoComplete;
    }

    /**
     * Gets the value of the formConvertEmptyToNull property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormConvertEmptyToNull() {
        if (this.formConvertEmptyToNull == null) {
            return "false";
        } else {
            return this.formConvertEmptyToNull;
        }
    }

    /**
     * Gets the value of the formCurrentValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormCurrentValue() {
        return this.formCurrentValue;
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
     * Gets the value of the formDropdown property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormDropdown() {
        if (this.formDropdown == null) {
            return "false";
        } else {
            return this.formDropdown;
        }
    }

    /**
     * Gets the value of the formItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the formItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormItem }
     * 
     * 
     */
    public List<FormItem> getFormItem() {
        if (this.formItem == null) {
            this.formItem = new ArrayList<FormItem>();
        }
        return this.formItem;
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
     * Gets the value of the formListSource property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormListSource() {
        return this.formListSource;
    }

    /**
     * Gets the value of the formListSourceType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormListSourceType() {
        return this.formListSourceType;
    }

    /**
     * Gets the value of the formMaxLength property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormMaxLength() {
        return this.formMaxLength;
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
     * Gets the value of the formReadonly property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormReadonly() {
        if (this.formReadonly == null) {
            return "false";
        } else {
            return this.formReadonly;
        }
    }

    /**
     * Gets the value of the formSize property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormSize() {
        return this.formSize;
    }

    /**
     * Gets the value of the formSourceCellRange property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormSourceCellRange() {
        return this.formSourceCellRange;
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
     * Gets the value of the officeEvents property.
     * 
     * @return possible object is {@link OfficeEvents }
     * 
     */
    public OfficeEvents getOfficeEvents() {
        return this.officeEvents;
    }

    /**
     * Sets the value of the formAutoComplete property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormAutoComplete(final String value) {
        this.formAutoComplete = value;
    }

    /**
     * Sets the value of the formConvertEmptyToNull property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormConvertEmptyToNull(final String value) {
        this.formConvertEmptyToNull = value;
    }

    /**
     * Sets the value of the formCurrentValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormCurrentValue(final String value) {
        this.formCurrentValue = value;
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
     * Sets the value of the formDropdown property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormDropdown(final String value) {
        this.formDropdown = value;
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
     * Sets the value of the formListSource property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormListSource(final String value) {
        this.formListSource = value;
    }

    /**
     * Sets the value of the formListSourceType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormListSourceType(final String value) {
        this.formListSourceType = value;
    }

    /**
     * Sets the value of the formMaxLength property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormMaxLength(final String value) {
        this.formMaxLength = value;
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
     * Sets the value of the formReadonly property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormReadonly(final String value) {
        this.formReadonly = value;
    }

    /**
     * Sets the value of the formSize property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormSize(final String value) {
        this.formSize = value;
    }

    /**
     * Sets the value of the formSourceCellRange property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormSourceCellRange(final String value) {
        this.formSourceCellRange = value;
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
     * Sets the value of the officeEvents property.
     * 
     * @param value allowed object is {@link OfficeEvents }
     * 
     */
    public void setOfficeEvents(final OfficeEvents value) {
        this.officeEvents = value;
    }

}
