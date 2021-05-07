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
public class OfficeDocumentMeta {

    protected OfficeMeta officeMeta;
    protected String officeVersion;
    protected String xmlnsChart;
    protected String xmlnsDc;
    protected String xmlnsDr3D;
    protected String xmlnsDraw;
    protected String xmlnsFo;
    protected String xmlnsForm;
    protected String xmlnsMath;
    protected String xmlnsMeta;
    protected String xmlnsNumber;
    protected String xmlnsOffice;
    protected String xmlnsScript;
    protected String xmlnsStyle;
    protected String xmlnsSvg;
    protected String xmlnsTable;
    protected String xmlnsText;
    protected String xmlnsXlink;

    /**
     * Gets the value of the officeMeta property.
     * 
     * @return possible object is {@link OfficeMeta }
     * 
     */
    public OfficeMeta getOfficeMeta() {
        return this.officeMeta;
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
     * Gets the value of the xmlnsChart property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsChart() {
        if (this.xmlnsChart == null) {
            return "http://openoffice.org/2000/chart";
        } else {
            return this.xmlnsChart;
        }
    }

    /**
     * Gets the value of the xmlnsDc property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsDc() {
        if (this.xmlnsDc == null) {
            return "http://purl.org/dc/elements/1.1/";
        } else {
            return this.xmlnsDc;
        }
    }

    /**
     * Gets the value of the xmlnsDr3D property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsDr3D() {
        if (this.xmlnsDr3D == null) {
            return "http://openoffice.org/2000/dr3d";
        } else {
            return this.xmlnsDr3D;
        }
    }

    /**
     * Gets the value of the xmlnsDraw property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsDraw() {
        if (this.xmlnsDraw == null) {
            return "http://openoffice.org/2000/drawing";
        } else {
            return this.xmlnsDraw;
        }
    }

    /**
     * Gets the value of the xmlnsFo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsFo() {
        if (this.xmlnsFo == null) {
            return "http://www.w3.org/1999/XSL/Format";
        } else {
            return this.xmlnsFo;
        }
    }

    /**
     * Gets the value of the xmlnsForm property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsForm() {
        if (this.xmlnsForm == null) {
            return "http://openoffice.org/2000/form";
        } else {
            return this.xmlnsForm;
        }
    }

    /**
     * Gets the value of the xmlnsMath property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsMath() {
        if (this.xmlnsMath == null) {
            return "http://www.w3.org/1998/Math/MathML";
        } else {
            return this.xmlnsMath;
        }
    }

    /**
     * Gets the value of the xmlnsMeta property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsMeta() {
        if (this.xmlnsMeta == null) {
            return "http://openoffice.org/2000/meta";
        } else {
            return this.xmlnsMeta;
        }
    }

    /**
     * Gets the value of the xmlnsNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsNumber() {
        if (this.xmlnsNumber == null) {
            return "http://openoffice.org/2000/datastyle";
        } else {
            return this.xmlnsNumber;
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
     * Gets the value of the xmlnsScript property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsScript() {
        if (this.xmlnsScript == null) {
            return "http://openoffice.org/2000/script";
        } else {
            return this.xmlnsScript;
        }
    }

    /**
     * Gets the value of the xmlnsStyle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsStyle() {
        if (this.xmlnsStyle == null) {
            return "http://openoffice.org/2000/style";
        } else {
            return this.xmlnsStyle;
        }
    }

    /**
     * Gets the value of the xmlnsSvg property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsSvg() {
        if (this.xmlnsSvg == null) {
            return "http://www.w3.org/2000/svg";
        } else {
            return this.xmlnsSvg;
        }
    }

    /**
     * Gets the value of the xmlnsTable property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsTable() {
        if (this.xmlnsTable == null) {
            return "http://openoffice.org/2000/table";
        } else {
            return this.xmlnsTable;
        }
    }

    /**
     * Gets the value of the xmlnsText property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsText() {
        if (this.xmlnsText == null) {
            return "http://openoffice.org/2000/text";
        } else {
            return this.xmlnsText;
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
     * Sets the value of the officeMeta property.
     * 
     * @param value allowed object is {@link OfficeMeta }
     * 
     */
    public void setOfficeMeta(final OfficeMeta value) {
        this.officeMeta = value;
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
     * Sets the value of the xmlnsChart property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsChart(final String value) {
        this.xmlnsChart = value;
    }

    /**
     * Sets the value of the xmlnsDc property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsDc(final String value) {
        this.xmlnsDc = value;
    }

    /**
     * Sets the value of the xmlnsDr3D property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsDr3D(final String value) {
        this.xmlnsDr3D = value;
    }

    /**
     * Sets the value of the xmlnsDraw property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsDraw(final String value) {
        this.xmlnsDraw = value;
    }

    /**
     * Sets the value of the xmlnsFo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsFo(final String value) {
        this.xmlnsFo = value;
    }

    /**
     * Sets the value of the xmlnsForm property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsForm(final String value) {
        this.xmlnsForm = value;
    }

    /**
     * Sets the value of the xmlnsMath property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsMath(final String value) {
        this.xmlnsMath = value;
    }

    /**
     * Sets the value of the xmlnsMeta property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsMeta(final String value) {
        this.xmlnsMeta = value;
    }

    /**
     * Sets the value of the xmlnsNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsNumber(final String value) {
        this.xmlnsNumber = value;
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
     * Sets the value of the xmlnsScript property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsScript(final String value) {
        this.xmlnsScript = value;
    }

    /**
     * Sets the value of the xmlnsStyle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsStyle(final String value) {
        this.xmlnsStyle = value;
    }

    /**
     * Sets the value of the xmlnsSvg property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsSvg(final String value) {
        this.xmlnsSvg = value;
    }

    /**
     * Sets the value of the xmlnsTable property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsTable(final String value) {
        this.xmlnsTable = value;
    }

    /**
     * Sets the value of the xmlnsText property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsText(final String value) {
        this.xmlnsText = value;
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
