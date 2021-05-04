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

package org.jopendocument.model;

/**
 * 
 */
public class MetaTemplate {

    protected String metaDate;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkTitle;
    protected String xlinkType;

    /**
     * Gets the value of the metaDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaDate() {
        return this.metaDate;
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
     * Gets the value of the xlinkTitle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkTitle() {
        return this.xlinkTitle;
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
     * Sets the value of the metaDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaDate(final String value) {
        this.metaDate = value;
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
     * Sets the value of the xlinkTitle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkTitle(final String value) {
        this.xlinkTitle = value;
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
