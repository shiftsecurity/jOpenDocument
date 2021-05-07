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
public class TextSectionSource {

    protected String textFilterName;
    protected String textSectionName;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the textFilterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextFilterName() {
        return this.textFilterName;
    }

    /**
     * Gets the value of the textSectionName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSectionName() {
        return this.textSectionName;
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
            return "embed";
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
     * Sets the value of the textFilterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextFilterName(final String value) {
        this.textFilterName = value;
    }

    /**
     * Sets the value of the textSectionName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSectionName(final String value) {
        this.textSectionName = value;
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
