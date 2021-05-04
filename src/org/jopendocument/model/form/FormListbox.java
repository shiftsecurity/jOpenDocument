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
public class FormListbox {

    protected String formBoundColumn;
    protected String formDataField;
    protected String formDisabled;
    protected String formDropdown;
    protected String formLinkedCell;
    protected String formListLinkageType;
    protected String formListSource;
    protected String formListSourceType;
    protected String formMultiple;
    protected List<FormOption> formOption;
    protected String formPrintable;
    protected FormProperties formProperties;
    protected String formSize;
    protected String formSourceCellRange;
    protected String formTabIndex;
    protected String formTabStop;
    protected String formTitle;
    protected OfficeEvents officeEvents;

    /**
     * Gets the value of the formBoundColumn property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormBoundColumn() {
        return this.formBoundColumn;
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
     * Gets the value of the formLinkedCell property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormLinkedCell() {
        return this.formLinkedCell;
    }

    /**
     * Gets the value of the formListLinkageType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormListLinkageType() {
        return this.formListLinkageType;
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
     * Gets the value of the formMultiple property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormMultiple() {
        if (this.formMultiple == null) {
            return "false";
        } else {
            return this.formMultiple;
        }
    }

    /**
     * Gets the value of the formOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the formOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormOption }
     * 
     * 
     */
    public List<FormOption> getFormOption() {
        if (this.formOption == null) {
            this.formOption = new ArrayList<FormOption>();
        }
        return this.formOption;
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
     * Gets the value of the officeEvents property.
     * 
     * @return possible object is {@link OfficeEvents }
     * 
     */
    public OfficeEvents getOfficeEvents() {
        return this.officeEvents;
    }

    /**
     * Sets the value of the formBoundColumn property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormBoundColumn(final String value) {
        this.formBoundColumn = value;
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
     * Sets the value of the formListLinkageType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormListLinkageType(final String value) {
        this.formListLinkageType = value;
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
     * Sets the value of the formMultiple property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormMultiple(final String value) {
        this.formMultiple = value;
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
     * Sets the value of the officeEvents property.
     * 
     * @param value allowed object is {@link OfficeEvents }
     * 
     */
    public void setOfficeEvents(final OfficeEvents value) {
        this.officeEvents = value;
    }

}
