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
public class OfficeDdeSource {

    protected String officeAutomaticUpdate;
    protected String officeDdeApplication;
    protected String officeDdeItem;
    protected String officeDdeTopic;
    protected String officeName;
    protected String tableConversionMode;

    /**
     * Gets the value of the officeAutomaticUpdate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeAutomaticUpdate() {
        if (this.officeAutomaticUpdate == null) {
            return "false";
        } else {
            return this.officeAutomaticUpdate;
        }
    }

    /**
     * Gets the value of the officeDdeApplication property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeDdeApplication() {
        return this.officeDdeApplication;
    }

    /**
     * Gets the value of the officeDdeItem property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeDdeItem() {
        return this.officeDdeItem;
    }

    /**
     * Gets the value of the officeDdeTopic property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOfficeDdeTopic() {
        return this.officeDdeTopic;
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
     * Gets the value of the tableConversionMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableConversionMode() {
        if (this.tableConversionMode == null) {
            return "into-default-style-data-style";
        } else {
            return this.tableConversionMode;
        }
    }

    /**
     * Sets the value of the officeAutomaticUpdate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeAutomaticUpdate(final String value) {
        this.officeAutomaticUpdate = value;
    }

    /**
     * Sets the value of the officeDdeApplication property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeDdeApplication(final String value) {
        this.officeDdeApplication = value;
    }

    /**
     * Sets the value of the officeDdeItem property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeDdeItem(final String value) {
        this.officeDdeItem = value;
    }

    /**
     * Sets the value of the officeDdeTopic property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOfficeDdeTopic(final String value) {
        this.officeDdeTopic = value;
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
     * Sets the value of the tableConversionMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableConversionMode(final String value) {
        this.tableConversionMode = value;
    }

}
