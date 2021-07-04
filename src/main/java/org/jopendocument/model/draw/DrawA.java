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

package org.jopendocument.model.draw;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class DrawA {

    protected List<Object> drawImageOrDrawTextBox;
    protected String officeName;
    protected String officeServerMap;
    protected String officeTargetFrameName;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the drawImageOrDrawTextBox property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the drawImageOrDrawTextBox property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDrawImageOrDrawTextBox().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DrawImage }
     * {@link DrawTextBox }
     * 
     * 
     */
    public List<Object> getDrawImageOrDrawTextBox() {
        if (this.drawImageOrDrawTextBox == null) {
            this.drawImageOrDrawTextBox = new ArrayList<Object>();
        }
        return this.drawImageOrDrawTextBox;
    }

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
     * Gets the value of the officeServerMap property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeServerMap() {
        if (this.officeServerMap == null) {
            return "false";
        } else {
            return this.officeServerMap;
        }
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
        return this.xlinkShow;
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
     * Sets the value of the officeServerMap property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeServerMap(final String value) {
        this.officeServerMap = value;
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

}
