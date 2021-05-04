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
public class FormControl {

    protected String formId;
    protected String formName;
    protected String formServiceName;
    protected List<Object> formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl;

    /**
     * Gets the value of the formId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormId() {
        return this.formId;
    }

    /**
     * Gets the value of the formName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormName() {
        return this.formName;
    }

    /**
     * Gets the value of the formServiceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormServiceName() {
        return this.formServiceName;
    }

    /**
     * Gets the value of the
     * formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormText }
     * {@link FormTextarea } {@link FormFixedText } {@link FormFile } {@link FormPassword }
     * {@link FormFormattedText } {@link FormButton } {@link FormImage } {@link FormCheckbox }
     * {@link FormRadio } {@link FormListbox } {@link FormCombobox } {@link FormFrame }
     * {@link FormHidden } {@link FormImageFrame } {@link FormGrid } {@link FormValueRange }
     * {@link FormGenericControl }
     * 
     * 
     */
    public List<Object> getFormTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl() {
        if (this.formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl == null) {
            this.formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl = new ArrayList<Object>();
        }
        return this.formTextOrFormTextareaOrFormFixedTextOrFormFileOrFormPasswordOrFormFormattedTextOrFormButtonOrFormImageOrFormCheckboxOrFormRadioOrFormListboxOrFormComboboxOrFormFrameOrFormHiddenOrFormImageFrameOrFormGridOrFormValueRangeOrFormGenericControl;
    }

    /**
     * Sets the value of the formId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormId(final String value) {
        this.formId = value;
    }

    /**
     * Sets the value of the formName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormName(final String value) {
        this.formName = value;
    }

    /**
     * Sets the value of the formServiceName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormServiceName(final String value) {
        this.formServiceName = value;
    }

}
