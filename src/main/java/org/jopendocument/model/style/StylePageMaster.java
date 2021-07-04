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

/**
 * 
 */
public class StylePageMaster {

    protected StyleFooterStyle styleFooterStyle;
    protected StyleHeaderStyle styleHeaderStyle;
    protected String styleName;
    protected String stylePageUsage;
    protected StyleProperties styleProperties;

    /**
     * Gets the value of the styleFooterStyle property.
     * 
     * @return possible object is {@link StyleFooterStyle }
     * 
     */
    public StyleFooterStyle getStyleFooterStyle() {
        return this.styleFooterStyle;
    }

    /**
     * Gets the value of the styleHeaderStyle property.
     * 
     * @return possible object is {@link StyleHeaderStyle }
     * 
     */
    public StyleHeaderStyle getStyleHeaderStyle() {
        return this.styleHeaderStyle;
    }

    /**
     * Gets the value of the styleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleName() {
        return this.styleName;
    }

    /**
     * Gets the value of the stylePageUsage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStylePageUsage() {
        if (this.stylePageUsage == null) {
            return "all";
        } else {
            return this.stylePageUsage;
        }
    }

    /**
     * Gets the value of the styleProperties property.
     * 
     * @return possible object is {@link StyleProperties }
     * 
     */
    public StyleProperties getStyleProperties() {
        return this.styleProperties;
    }

    /**
     * Sets the value of the styleFooterStyle property.
     * 
     * @param value allowed object is {@link StyleFooterStyle }
     * 
     */
    public void setStyleFooterStyle(final StyleFooterStyle value) {
        this.styleFooterStyle = value;
    }

    /**
     * Sets the value of the styleHeaderStyle property.
     * 
     * @param value allowed object is {@link StyleHeaderStyle }
     * 
     */
    public void setStyleHeaderStyle(final StyleHeaderStyle value) {
        this.styleHeaderStyle = value;
    }

    /**
     * Sets the value of the styleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleName(final String value) {
        this.styleName = value;
    }

    /**
     * Sets the value of the stylePageUsage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStylePageUsage(final String value) {
        this.stylePageUsage = value;
    }

    /**
     * Sets the value of the styleProperties property.
     * 
     * @param value allowed object is {@link StyleProperties }
     * 
     */
    public void setStyleProperties(final StyleProperties value) {
        this.styleProperties = value;
    }

}
