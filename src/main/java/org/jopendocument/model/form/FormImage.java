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
public class FormImage {

    protected String formButtonType;
    protected String formDisabled;
    protected String formImageData;
    protected String formPrintable;
    protected FormProperties formProperties;
    protected String formTabIndex;
    protected String formTabStop;
    protected String formTitle;
    protected String formValue;
    protected OfficeEvents officeEvents;
    protected String officeTargetFrame;
    protected String xlinkHref;

    /**
     * Gets the value of the formButtonType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormButtonType() {
        if (this.formButtonType == null) {
            return "push";
        } else {
            return this.formButtonType;
        }
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
     * Gets the value of the formImageData property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFormImageData() {
        return this.formImageData;
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
     * Sets the value of the formButtonType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormButtonType(final String value) {
        this.formButtonType = value;
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
     * Sets the value of the formImageData property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFormImageData(final String value) {
        this.formImageData = value;
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
