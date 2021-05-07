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

/**
 * 
 */
public class OfficeDocumentSettings {

    protected OfficeSettings officeSettings;
    protected String officeVersion;
    protected String xmlnsConfig;
    protected String xmlnsOffice;
    protected String xmlnsXlink;

    /**
     * Gets the value of the officeSettings property.
     * 
     * @return possible object is {@link OfficeSettings }
     * 
     */
    public OfficeSettings getOfficeSettings() {
        return this.officeSettings;
    }

    /**
     * Gets the value of the officeVersion property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeVersion() {
        return this.officeVersion;
    }

    /**
     * Gets the value of the xmlnsConfig property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsConfig() {
        if (this.xmlnsConfig == null) {
            return "http://openoffice.org/2001/config";
        } else {
            return this.xmlnsConfig;
        }
    }

    /**
     * Gets the value of the xmlnsOffice property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsOffice() {
        if (this.xmlnsOffice == null) {
            return "http://openoffice.org/2000/office";
        } else {
            return this.xmlnsOffice;
        }
    }

    /**
     * Gets the value of the xmlnsXlink property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsXlink() {
        if (this.xmlnsXlink == null) {
            return "http://www.w3.org/1999/xlink";
        } else {
            return this.xmlnsXlink;
        }
    }

    /**
     * Sets the value of the officeSettings property.
     * 
     * @param value allowed object is {@link OfficeSettings }
     * 
     */
    public void setOfficeSettings(final OfficeSettings value) {
        this.officeSettings = value;
    }

    /**
     * Sets the value of the officeVersion property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeVersion(final String value) {
        this.officeVersion = value;
    }

    /**
     * Sets the value of the xmlnsConfig property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsConfig(final String value) {
        this.xmlnsConfig = value;
    }

    /**
     * Sets the value of the xmlnsOffice property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsOffice(final String value) {
        this.xmlnsOffice = value;
    }

    /**
     * Sets the value of the xmlnsXlink property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsXlink(final String value) {
        this.xmlnsXlink = value;
    }

}
