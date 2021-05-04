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

/**
 * 
 */
public class DrawContourPath {

    protected String drawRecreateOnEdit;
    protected String svgD;
    protected String svgHeight;
    protected String svgViewBox;
    protected String svgWidth;

    /**
     * Gets the value of the drawRecreateOnEdit property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawRecreateOnEdit() {
        return this.drawRecreateOnEdit;
    }

    /**
     * Gets the value of the svgD property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgD() {
        return this.svgD;
    }

    /**
     * Gets the value of the svgHeight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgHeight() {
        return this.svgHeight;
    }

    /**
     * Gets the value of the svgViewBox property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgViewBox() {
        return this.svgViewBox;
    }

    /**
     * Gets the value of the svgWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgWidth() {
        return this.svgWidth;
    }

    /**
     * Sets the value of the drawRecreateOnEdit property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawRecreateOnEdit(final String value) {
        this.drawRecreateOnEdit = value;
    }

    /**
     * Sets the value of the svgD property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgD(final String value) {
        this.svgD = value;
    }

    /**
     * Sets the value of the svgHeight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgHeight(final String value) {
        this.svgHeight = value;
    }

    /**
     * Sets the value of the svgViewBox property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgViewBox(final String value) {
        this.svgViewBox = value;
    }

    /**
     * Sets the value of the svgWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgWidth(final String value) {
        this.svgWidth = value;
    }

}
