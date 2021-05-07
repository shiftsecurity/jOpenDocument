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
public class ChartSeries {

    protected String chartAttachedAxis;
    protected String chartClass;
    protected List<ChartDataPoint> chartDataPoint;
    protected List<ChartDomain> chartDomain;
    protected ChartErrorIndicator chartErrorIndicator;
    protected String chartLabelCellAddress;
    protected ChartMeanValue chartMeanValue;
    protected ChartRegressionCurve chartRegressionCurve;
    protected String chartStyleName;
    protected String chartValuesCellRangeAddress;

    /**
     * Gets the value of the chartAttachedAxis property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartAttachedAxis() {
        return this.chartAttachedAxis;
    }

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
     * Gets the value of the chartDataPoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the chartDataPoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getChartDataPoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ChartDataPoint }
     * 
     * 
     */
    public List<ChartDataPoint> getChartDataPoint() {
        if (this.chartDataPoint == null) {
            this.chartDataPoint = new ArrayList<ChartDataPoint>();
        }
        return this.chartDataPoint;
    }

    /**
     * Gets the value of the chartDomain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the chartDomain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getChartDomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ChartDomain }
     * 
     * 
     */
    public List<ChartDomain> getChartDomain() {
        if (this.chartDomain == null) {
            this.chartDomain = new ArrayList<ChartDomain>();
        }
        return this.chartDomain;
    }

    /**
     * Gets the value of the chartErrorIndicator property.
     * 
     * @return possible object is {@link ChartErrorIndicator }
     * 
     */
    public ChartErrorIndicator getChartErrorIndicator() {
        return this.chartErrorIndicator;
    }

    /**
     * Gets the value of the chartLabelCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartLabelCellAddress() {
        return this.chartLabelCellAddress;
    }

    /**
     * Gets the value of the chartMeanValue property.
     * 
     * @return possible object is {@link ChartMeanValue }
     * 
     */
    public ChartMeanValue getChartMeanValue() {
        return this.chartMeanValue;
    }

    /**
     * Gets the value of the chartRegressionCurve property.
     * 
     * @return possible object is {@link ChartRegressionCurve }
     * 
     */
    public ChartRegressionCurve getChartRegressionCurve() {
        return this.chartRegressionCurve;
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
     * Gets the value of the chartValuesCellRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartValuesCellRangeAddress() {
        return this.chartValuesCellRangeAddress;
    }

    /**
     * Sets the value of the chartAttachedAxis property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartAttachedAxis(final String value) {
        this.chartAttachedAxis = value;
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
     * Sets the value of the chartErrorIndicator property.
     * 
     * @param value allowed object is {@link ChartErrorIndicator }
     * 
     */
    public void setChartErrorIndicator(final ChartErrorIndicator value) {
        this.chartErrorIndicator = value;
    }

    /**
     * Sets the value of the chartLabelCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartLabelCellAddress(final String value) {
        this.chartLabelCellAddress = value;
    }

    /**
     * Sets the value of the chartMeanValue property.
     * 
     * @param value allowed object is {@link ChartMeanValue }
     * 
     */
    public void setChartMeanValue(final ChartMeanValue value) {
        this.chartMeanValue = value;
    }

    /**
     * Sets the value of the chartRegressionCurve property.
     * 
     * @param value allowed object is {@link ChartRegressionCurve }
     * 
     */
    public void setChartRegressionCurve(final ChartRegressionCurve value) {
        this.chartRegressionCurve = value;
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
     * Sets the value of the chartValuesCellRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartValuesCellRangeAddress(final String value) {
        this.chartValuesCellRangeAddress = value;
    }

}
