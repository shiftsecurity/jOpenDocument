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

package org.jopendocument.model.text;

/**
 * 
 */
public class TextA {

    protected String officeName;
    protected String officeTargetFrameName;
    protected String textStyleName;
    protected String textVisitedStyleName;
    protected String value = "";
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the officeName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeName() {
        return this.officeName;
    }

    /**
     * Gets the value of the officeTargetFrameName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeTargetFrameName() {
        return this.officeTargetFrameName;
    }

    /**
     * Gets the value of the textStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStyleName() {
        return this.textStyleName;
    }

    /**
     * Gets the value of the textVisitedStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextVisitedStyleName() {
        return this.textVisitedStyleName;
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
     * Gets the value of the xlinkActuate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkActuate() {
        if (this.xlinkActuate == null) {
            return "onRequest";
        } else {
            return this.xlinkActuate;
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
     * Gets the value of the xlinkShow property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkShow() {
        if (this.xlinkShow == null) {
            return "replace";
        } else {
            return this.xlinkShow;
        }
    }

    /**
     * Gets the value of the xlinkType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkType() {
        if (this.xlinkType == null) {
            return "simple";
        } else {
            return this.xlinkType;
        }
    }

    /**
     * Sets the value of the officeName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeName(final String value) {
        this.officeName = value;
    }

    /**
     * Sets the value of the officeTargetFrameName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeTargetFrameName(final String value) {
        this.officeTargetFrameName = value;
    }

    /**
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyleName(final String value) {
        this.textStyleName = value;
    }

    /**
     * Sets the value of the textVisitedStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextVisitedStyleName(final String value) {
        this.textVisitedStyleName = value;
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

    /**
     * Sets the value of the xlinkActuate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkActuate(final String value) {
        this.xlinkActuate = value;
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

    /**
     * Sets the value of the xlinkShow property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkShow(final String value) {
        this.xlinkShow = value;
    }

    /**
     * Sets the value of the xlinkType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkType(final String value) {
        this.xlinkType = value;
    }

    public void addTextSpan(TextSpan textspan) {
        // TODO Auto-generated method stub
        // FIXME: add TextSpan in TextA
    }

    public void concatValue(String string) {
        value += string;

    }

}
