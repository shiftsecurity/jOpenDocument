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

import org.jopendocument.model.draw.Dr3DLight;

/**
 * 
 */
public class ChartPlotArea {

    protected List<ChartAxis> chartAxis;
    protected ChartCategories chartCategories;
    protected String chartDataSourceHasLabels;
    protected ChartFloor chartFloor;
    protected List<ChartSeries> chartSeries;
    protected ChartStockGainMarker chartStockGainMarker;
    protected ChartStockLossMarker chartStockLossMarker;
    protected ChartStockRangeLine chartStockRangeLine;
    protected String chartStyleName;
    protected String chartTableNumberList;
    protected ChartWall chartWall;
    protected String dr3DAmbientColor;
    protected String dr3DDistance;
    protected String dr3DFocalLength;
    protected List<Dr3DLight> dr3DLight;
    protected String dr3DLightingMode;
    protected String dr3DProjection;
    protected String dr3DShadeMode;
    protected String dr3DShadowSlant;
    protected String dr3DTransform;
    protected String dr3DVpn;
    protected String dr3DVrp;
    protected String dr3DVup;
    protected String svgHeight;
    protected String svgWidth;
    protected String svgX;
    protected String svgY;
    protected String tableCellRangeAddress;

    /**
     * Gets the value of the chartAxis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the chartAxis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getChartAxis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ChartAxis }
     * 
     * 
     */
    public List<ChartAxis> getChartAxis() {
        if (this.chartAxis == null) {
            this.chartAxis = new ArrayList<ChartAxis>();
        }
        return this.chartAxis;
    }

    /**
     * Gets the value of the chartCategories property.
     * 
     * @return possible object is {@link ChartCategories }
     * 
     */
    public ChartCategories getChartCategories() {
        return this.chartCategories;
    }

    /**
     * Gets the value of the chartDataSourceHasLabels property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartDataSourceHasLabels() {
        if (this.chartDataSourceHasLabels == null) {
            return "none";
        } else {
            return this.chartDataSourceHasLabels;
        }
    }

    /**
     * Gets the value of the chartFloor property.
     * 
     * @return possible object is {@link ChartFloor }
     * 
     */
    public ChartFloor getChartFloor() {
        return this.chartFloor;
    }

    /**
     * Gets the value of the chartSeries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the chartSeries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getChartSeries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ChartSeries }
     * 
     * 
     */
    public List<ChartSeries> getChartSeries() {
        if (this.chartSeries == null) {
            this.chartSeries = new ArrayList<ChartSeries>();
        }
        return this.chartSeries;
    }

    /**
     * Gets the value of the chartStockGainMarker property.
     * 
     * @return possible object is {@link ChartStockGainMarker }
     * 
     */
    public ChartStockGainMarker getChartStockGainMarker() {
        return this.chartStockGainMarker;
    }

    /**
     * Gets the value of the chartStockLossMarker property.
     * 
     * @return possible object is {@link ChartStockLossMarker }
     * 
     */
    public ChartStockLossMarker getChartStockLossMarker() {
        return this.chartStockLossMarker;
    }

    /**
     * Gets the value of the chartStockRangeLine property.
     * 
     * @return possible object is {@link ChartStockRangeLine }
     * 
     */
    public ChartStockRangeLine getChartStockRangeLine() {
        return this.chartStockRangeLine;
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
     * Gets the value of the chartTableNumberList property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChartTableNumberList() {
        return this.chartTableNumberList;
    }

    /**
     * Gets the value of the chartWall property.
     * 
     * @return possible object is {@link ChartWall }
     * 
     */
    public ChartWall getChartWall() {
        return this.chartWall;
    }

    /**
     * Gets the value of the dr3DAmbientColor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DAmbientColor() {
        return this.dr3DAmbientColor;
    }

    /**
     * Gets the value of the dr3DDistance property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DDistance() {
        return this.dr3DDistance;
    }

    /**
     * Gets the value of the dr3DFocalLength property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DFocalLength() {
        return this.dr3DFocalLength;
    }

    /**
     * Gets the value of the dr3DLight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the dr3DLight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDr3DLight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Dr3DLight }
     * 
     * 
     */
    public List<Dr3DLight> getDr3DLight() {
        if (this.dr3DLight == null) {
            this.dr3DLight = new ArrayList<Dr3DLight>();
        }
        return this.dr3DLight;
    }

    /**
     * Gets the value of the dr3DLightingMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DLightingMode() {
        return this.dr3DLightingMode;
    }

    /**
     * Gets the value of the dr3DProjection property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DProjection() {
        return this.dr3DProjection;
    }

    /**
     * Gets the value of the dr3DShadeMode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DShadeMode() {
        return this.dr3DShadeMode;
    }

    /**
     * Gets the value of the dr3DShadowSlant property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DShadowSlant() {
        return this.dr3DShadowSlant;
    }

    /**
     * Gets the value of the dr3DTransform property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DTransform() {
        return this.dr3DTransform;
    }

    /**
     * Gets the value of the dr3DVpn property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DVpn() {
        return this.dr3DVpn;
    }

    /**
     * Gets the value of the dr3DVrp property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DVrp() {
        return this.dr3DVrp;
    }

    /**
     * Gets the value of the dr3DVup property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDr3DVup() {
        return this.dr3DVup;
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
     * Gets the value of the svgWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgWidth() {
        return this.svgWidth;
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
     * Gets the value of the tableCellRangeAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCellRangeAddress() {
        return this.tableCellRangeAddress;
    }

    /**
     * Sets the value of the chartCategories property.
     * 
     * @param value allowed object is {@link ChartCategories }
     * 
     */
    public void setChartCategories(final ChartCategories value) {
        this.chartCategories = value;
    }

    /**
     * Sets the value of the chartDataSourceHasLabels property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartDataSourceHasLabels(final String value) {
        this.chartDataSourceHasLabels = value;
    }

    /**
     * Sets the value of the chartFloor property.
     * 
     * @param value allowed object is {@link ChartFloor }
     * 
     */
    public void setChartFloor(final ChartFloor value) {
        this.chartFloor = value;
    }

    /**
     * Sets the value of the chartStockGainMarker property.
     * 
     * @param value allowed object is {@link ChartStockGainMarker }
     * 
     */
    public void setChartStockGainMarker(final ChartStockGainMarker value) {
        this.chartStockGainMarker = value;
    }

    /**
     * Sets the value of the chartStockLossMarker property.
     * 
     * @param value allowed object is {@link ChartStockLossMarker }
     * 
     */
    public void setChartStockLossMarker(final ChartStockLossMarker value) {
        this.chartStockLossMarker = value;
    }

    /**
     * Sets the value of the chartStockRangeLine property.
     * 
     * @param value allowed object is {@link ChartStockRangeLine }
     * 
     */
    public void setChartStockRangeLine(final ChartStockRangeLine value) {
        this.chartStockRangeLine = value;
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
     * Sets the value of the chartTableNumberList property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setChartTableNumberList(final String value) {
        this.chartTableNumberList = value;
    }

    /**
     * Sets the value of the chartWall property.
     * 
     * @param value allowed object is {@link ChartWall }
     * 
     */
    public void setChartWall(final ChartWall value) {
        this.chartWall = value;
    }

    /**
     * Sets the value of the dr3DAmbientColor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DAmbientColor(final String value) {
        this.dr3DAmbientColor = value;
    }

    /**
     * Sets the value of the dr3DDistance property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DDistance(final String value) {
        this.dr3DDistance = value;
    }

    /**
     * Sets the value of the dr3DFocalLength property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DFocalLength(final String value) {
        this.dr3DFocalLength = value;
    }

    /**
     * Sets the value of the dr3DLightingMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DLightingMode(final String value) {
        this.dr3DLightingMode = value;
    }

    /**
     * Sets the value of the dr3DProjection property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DProjection(final String value) {
        this.dr3DProjection = value;
    }

    /**
     * Sets the value of the dr3DShadeMode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DShadeMode(final String value) {
        this.dr3DShadeMode = value;
    }

    /**
     * Sets the value of the dr3DShadowSlant property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DShadowSlant(final String value) {
        this.dr3DShadowSlant = value;
    }

    /**
     * Sets the value of the dr3DTransform property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DTransform(final String value) {
        this.dr3DTransform = value;
    }

    /**
     * Sets the value of the dr3DVpn property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DVpn(final String value) {
        this.dr3DVpn = value;
    }

    /**
     * Sets the value of the dr3DVrp property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DVrp(final String value) {
        this.dr3DVrp = value;
    }

    /**
     * Sets the value of the dr3DVup property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDr3DVup(final String value) {
        this.dr3DVup = value;
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
     * Sets the value of the svgWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgWidth(final String value) {
        this.svgWidth = value;
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
     * Sets the value of the tableCellRangeAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCellRangeAddress(final String value) {
        this.tableCellRangeAddress = value;
    }

}
