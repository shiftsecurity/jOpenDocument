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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ChartAxis {

    protected String chartClass;
    protected List<ChartGrid> chartGrid;
    protected String chartName;
    protected String chartStyleName;
    protected ChartTitle chartTitle;

    /**
     * Gets the value of the chartClass property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartClass() {
        return this.chartClass;
    }

    /**
     * Gets the value of the chartGrid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the chartGrid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getChartGrid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ChartGrid }
     * 
     * 
     */
    public List<ChartGrid> getChartGrid() {
        if (this.chartGrid == null) {
            this.chartGrid = new ArrayList<ChartGrid>();
        }
        return this.chartGrid;
    }

    /**
     * Gets the value of the chartName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartName() {
        return this.chartName;
    }

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
     * Gets the value of the chartTitle property.
     * 
     * @return possible object is {@link ChartTitle }
     * 
     */
    public ChartTitle getChartTitle() {
        return this.chartTitle;
    }

    /**
     * Sets the value of the chartClass property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartClass(final String value) {
        this.chartClass = value;
    }

    /**
     * Sets the value of the chartName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartName(final String value) {
        this.chartName = value;
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
     * Sets the value of the chartTitle property.
     * 
     * @param value allowed object is {@link ChartTitle }
     * 
     */
    public void setChartTitle(final ChartTitle value) {
        this.chartTitle = value;
    }

}
