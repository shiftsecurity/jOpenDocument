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

package org.jopendocument.model.chart;

import org.jopendocument.model.text.TextP;

/**
 * 
 */
public class ChartTitle {

    protected String chartStyleName;
    protected String svgX;
    protected String svgY;
    protected String tableCellRange;
    protected TextP textP;

    /**
     * Gets the value of the chartStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartStyleName() {
        return this.chartStyleName;
    }

    /**
     * Gets the value of the svgX property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgX() {
        return this.svgX;
    }

    /**
     * Gets the value of the svgY property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgY() {
        return this.svgY;
    }

    /**
     * Gets the value of the tableCellRange property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCellRange() {
        return this.tableCellRange;
    }

    /**
     * Gets the value of the textP property.
     * 
     * @return possible object is {@link TextP }
     * 
     */
    public TextP getTextP() {
        return this.textP;
    }

    /**
     * Sets the value of the chartStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartStyleName(final String value) {
        this.chartStyleName = value;
    }

    /**
     * Sets the value of the svgX property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgX(final String value) {
        this.svgX = value;
    }

    /**
     * Sets the value of the svgY property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgY(final String value) {
        this.svgY = value;
    }

    /**
     * Sets the value of the tableCellRange property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCellRange(final String value) {
        this.tableCellRange = value;
    }

    /**
     * Sets the value of the textP property.
     * 
     * @param value allowed object is {@link TextP }
     * 
     */
    public void setTextP(final TextP value) {
        this.textP = value;
    }

}
