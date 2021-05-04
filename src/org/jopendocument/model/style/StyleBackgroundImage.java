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

package org.jopendocument.model.style;

import org.jopendocument.model.office.OfficeBinaryData;

/**
 * 
 */
public class StyleBackgroundImage {

    protected String drawTransparency;
    protected OfficeBinaryData officeBinaryData;
    protected String styleFilterName;
    protected String stylePosition;
    protected String styleRepeat;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the drawTransparency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawTransparency() {
        return this.drawTransparency;
    }

    /**
     * Gets the value of the officeBinaryData property.
     * 
     * @return possible object is {@link OfficeBinaryData }
     * 
     */
    public OfficeBinaryData getOfficeBinaryData() {
        return this.officeBinaryData;
    }

    /**
     * Gets the value of the styleFilterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleFilterName() {
        return this.styleFilterName;
    }

    /**
     * Gets the value of the stylePosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStylePosition() {
        if (this.stylePosition == null) {
            return "center";
        } else {
            return this.stylePosition;
        }
    }

    /**
     * Gets the value of the styleRepeat property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleRepeat() {
        if (this.styleRepeat == null) {
            return "repeat";
        } else {
            return this.styleRepeat;
        }
    }

    /**
     * Gets the value of the xlinkActuate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkActuate() {
        return this.xlinkActuate;
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
        return this.xlinkType;
    }

    /**
     * Sets the value of the drawTransparency property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawTransparency(final String value) {
        this.drawTransparency = value;
    }

    /**
     * Sets the value of the officeBinaryData property.
     * 
     * @param value allowed object is {@link OfficeBinaryData }
     * 
     */
    public void setOfficeBinaryData(final OfficeBinaryData value) {
        this.officeBinaryData = value;
    }

    /**
     * Sets the value of the styleFilterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleFilterName(final String value) {
        this.styleFilterName = value;
    }

    /**
     * Sets the value of the stylePosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStylePosition(final String value) {
        this.stylePosition = value;
    }

    /**
     * Sets the value of the styleRepeat property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleRepeat(final String value) {
        this.styleRepeat = value;
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
