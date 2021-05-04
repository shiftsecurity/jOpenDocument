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
public class FormForm {

    protected String formAllowDeletes;
    protected String formAllowInserts;
    protected String formAllowUpdates;
    protected String formApplyFilter;
    protected String formCommand;
    protected String formCommandType;
    protected List<Object> formControlOrFormForm;
    protected String formDatasource;
    protected String formDetailFields;
    protected String formEnctype;
    protected String formEscapeProcessing;
    protected String formFilter;
    protected String formIgnoreResult;
    protected String formMasterFields;
    protected String formMethod;
    protected String formName;
    protected String formNavigationMode;
    protected String formOrder;
    protected FormProperties formProperties;
    protected String formServiceName;
    protected String formTabCycle;
    protected OfficeEvents officeEvents;
    protected String officeTargetFrame;
    protected String xlinkHref;

    /**
     * Gets the value of the formAllowDeletes property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormAllowDeletes() {
        if (this.formAllowDeletes == null) {
            return "true";
        } else {
            return this.formAllowDeletes;
        }
    }

    /**
     * Gets the value of the formAllowInserts property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormAllowInserts() {
        if (this.formAllowInserts == null) {
            return "true";
        } else {
            return this.formAllowInserts;
        }
    }

    /**
     * Gets the value of the formAllowUpdates property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormAllowUpdates() {
        if (this.formAllowUpdates == null) {
            return "true";
        } else {
            return this.formAllowUpdates;
        }
    }

    /**
     * Gets the value of the formApplyFilter property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormApplyFilter() {
        if (this.formApplyFilter == null) {
            return "false";
        } else {
            return this.formApplyFilter;
        }
    }

    /**
     * Gets the value of the formCommand property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormCommand() {
        return this.formCommand;
    }

    /**
     * Gets the value of the formCommandType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormCommandType() {
        if (this.formCommandType == null) {
            return "command";
        } else {
            return this.formCommandType;
        }
    }

    /**
     * Gets the value of the formControlOrFormForm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the formControlOrFormForm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFormControlOrFormForm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FormControl }
     * {@link FormForm }
     * 
     * 
     */
    public List<Object> getFormControlOrFormForm() {
        if (this.formControlOrFormForm == null) {
            this.formControlOrFormForm = new ArrayList<Object>();
        }
        return this.formControlOrFormForm;
    }

    /**
     * Gets the value of the formDatasource property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormDatasource() {
        return this.formDatasource;
    }

    /**
     * Gets the value of the formDetailFields property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormDetailFields() {
        return this.formDetailFields;
    }

    /**
     * Gets the value of the formEnctype property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormEnctype() {
        if (this.formEnctype == null) {
            return "application/x-www-form-urlencoded";
        } else {
            return this.formEnctype;
        }
    }

    /**
     * Gets the value of the formEscapeProcessing property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormEscapeProcessing() {
        if (this.formEscapeProcessing == null) {
            return "true";
        } else {
            return this.formEscapeProcessing;
        }
    }

    /**
     * Gets the value of the formFilter property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormFilter() {
        return this.formFilter;
    }

    /**
     * Gets the value of the formIgnoreResult property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormIgnoreResult() {
        if (this.formIgnoreResult == null) {
            return "false";
        } else {
            return this.formIgnoreResult;
        }
    }

    /**
     * Gets the value of the formMasterFields property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormMasterFields() {
        return this.formMasterFields;
    }

    /**
     * Gets the value of the formMethod property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormMethod() {
        if (this.formMethod == null) {
            return "get";
        } else {
            return this.formMethod;
        }
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
     * Gets the value of the formNavigationMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormNavigationMode() {
        return this.formNavigationMode;
    }

    /**
     * Gets the value of the formOrder property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormOrder() {
        return this.formOrder;
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
     * Gets the value of the formServiceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormServiceName() {
        return this.formServiceName;
    }

    /**
     * Gets the value of the formTabCycle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormTabCycle() {
        return this.formTabCycle;
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
     * Gets the value of the officeTargetFrame property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeTargetFrame() {
        if (this.officeTargetFrame == null) {
            return "_blank";
        } else {
            return this.officeTargetFrame;
        }
    }

    /**
     * Gets the value of the xlinkHref property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkHref() {
        return this.xlinkHref;
    }

    /**
     * Sets the value of the formAllowDeletes property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormAllowDeletes(final String value) {
        this.formAllowDeletes = value;
    }

    /**
     * Sets the value of the formAllowInserts property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormAllowInserts(final String value) {
        this.formAllowInserts = value;
    }

    /**
     * Sets the value of the formAllowUpdates property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormAllowUpdates(final String value) {
        this.formAllowUpdates = value;
    }

    /**
     * Sets the value of the formApplyFilter property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormApplyFilter(final String value) {
        this.formApplyFilter = value;
    }

    /**
     * Sets the value of the formCommand property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormCommand(final String value) {
        this.formCommand = value;
    }

    /**
     * Sets the value of the formCommandType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormCommandType(final String value) {
        this.formCommandType = value;
    }

    /**
     * Sets the value of the formDatasource property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormDatasource(final String value) {
        this.formDatasource = value;
    }

    /**
     * Sets the value of the formDetailFields property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormDetailFields(final String value) {
        this.formDetailFields = value;
    }

    /**
     * Sets the value of the formEnctype property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormEnctype(final String value) {
        this.formEnctype = value;
    }

    /**
     * Sets the value of the formEscapeProcessing property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormEscapeProcessing(final String value) {
        this.formEscapeProcessing = value;
    }

    /**
     * Sets the value of the formFilter property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormFilter(final String value) {
        this.formFilter = value;
    }

    /**
     * Sets the value of the formIgnoreResult property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormIgnoreResult(final String value) {
        this.formIgnoreResult = value;
    }

    /**
     * Sets the value of the formMasterFields property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormMasterFields(final String value) {
        this.formMasterFields = value;
    }

    /**
     * Sets the value of the formMethod property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormMethod(final String value) {
        this.formMethod = value;
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
     * Sets the value of the formNavigationMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormNavigationMode(final String value) {
        this.formNavigationMode = value;
    }

    /**
     * Sets the value of the formOrder property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormOrder(final String value) {
        this.formOrder = value;
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
     * Sets the value of the formServiceName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormServiceName(final String value) {
        this.formServiceName = value;
    }

    /**
     * Sets the value of the formTabCycle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormTabCycle(final String value) {
        this.formTabCycle = value;
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

    /**
     * Sets the value of the officeTargetFrame property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeTargetFrame(final String value) {
        this.officeTargetFrame = value;
    }

    /**
     * Sets the value of the xlinkHref property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkHref(final String value) {
        this.xlinkHref = value;
    }

}
