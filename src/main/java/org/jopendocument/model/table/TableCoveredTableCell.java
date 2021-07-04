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

package org.jopendocument.model.table;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.chart.ChartChart;
import org.jopendocument.model.draw.Dr3DScene;
import org.jopendocument.model.draw.DrawApplet;
import org.jopendocument.model.draw.DrawCaption;
import org.jopendocument.model.draw.DrawCircle;
import org.jopendocument.model.draw.DrawConnector;
import org.jopendocument.model.draw.DrawControl;
import org.jopendocument.model.draw.DrawCustomShape;
import org.jopendocument.model.draw.DrawEllipse;
import org.jopendocument.model.draw.DrawFloatingFrame;
import org.jopendocument.model.draw.DrawG;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.draw.DrawLine;
import org.jopendocument.model.draw.DrawMeasure;
import org.jopendocument.model.draw.DrawObject;
import org.jopendocument.model.draw.DrawObjectOle;
import org.jopendocument.model.draw.DrawPageThumbnail;
import org.jopendocument.model.draw.DrawPath;
import org.jopendocument.model.draw.DrawPlugin;
import org.jopendocument.model.draw.DrawPolygon;
import org.jopendocument.model.draw.DrawPolyline;
import org.jopendocument.model.draw.DrawRect;
import org.jopendocument.model.draw.DrawTextBox;
import org.jopendocument.model.office.OfficeAnnotation;
import org.jopendocument.model.text.TextH;
import org.jopendocument.model.text.TextOrderedList;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextUnorderedList;

/**
 * 
 */
public class TableCoveredTableCell {

    protected OfficeAnnotation officeAnnotation;
    protected String tableBooleanValue;
    protected TableCellRangeSource tableCellRangeSource;
    protected String tableCurrency;
    protected String tableDateValue;
    protected TableDetective tableDetective;
    protected String tableFormula;
    protected String tableNumberColumnsRepeated;
    protected String tableNumberMatrixColumnsSpanned;
    protected String tableNumberMatrixRowsSpanned;
    protected String tableStringValue;
    protected String tableStyleName;
    protected List<Object> tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    protected String tableTimeValue;
    protected String tableValidationName;
    protected String tableValue;
    protected String tableValueType;

    /**
     * Gets the value of the officeAnnotation property.
     * 
     * @return possible object is {@link OfficeAnnotation }
     * 
     */
    public OfficeAnnotation getOfficeAnnotation() {
        return this.officeAnnotation;
    }

    /**
     * Gets the value of the tableBooleanValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableBooleanValue() {
        return this.tableBooleanValue;
    }

    /**
     * Gets the value of the tableCellRangeSource property.
     * 
     * @return possible object is {@link TableCellRangeSource }
     * 
     */
    public TableCellRangeSource getTableCellRangeSource() {
        return this.tableCellRangeSource;
    }

    /**
     * Gets the value of the tableCurrency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCurrency() {
        return this.tableCurrency;
    }

    /**
     * Gets the value of the tableDateValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDateValue() {
        return this.tableDateValue;
    }

    /**
     * Gets the value of the tableDetective property.
     * 
     * @return possible object is {@link TableDetective }
     * 
     */
    public TableDetective getTableDetective() {
        return this.tableDetective;
    }

    /**
     * Gets the value of the tableFormula property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFormula() {
        return this.tableFormula;
    }

    /**
     * Gets the value of the tableNumberColumnsRepeated property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableNumberColumnsRepeated() {
        if (this.tableNumberColumnsRepeated == null) {
            return "1";
        } else {
            return this.tableNumberColumnsRepeated;
        }
    }

    /**
     * Gets the value of the tableNumberMatrixColumnsSpanned property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableNumberMatrixColumnsSpanned() {
        return this.tableNumberMatrixColumnsSpanned;
    }

    /**
     * Gets the value of the tableNumberMatrixRowsSpanned property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableNumberMatrixRowsSpanned() {
        return this.tableNumberMatrixRowsSpanned;
    }

    /**
     * Gets the value of the tableStringValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStringValue() {
        return this.tableStringValue;
    }

    /**
     * Gets the value of the tableStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStyleName() {
        return this.tableStyleName;
    }

    /**
     * Gets the value of the
     * tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableSubTable }
     * {@link TextH } {@link TextP } {@link TextOrderedList } {@link TextUnorderedList }
     * {@link DrawRect } {@link DrawLine } {@link DrawPolyline } {@link DrawPolygon }
     * {@link DrawPath } {@link DrawCircle } {@link DrawEllipse } {@link DrawG }
     * {@link DrawPageThumbnail } {@link DrawTextBox } {@link DrawImage } {@link DrawObject }
     * {@link DrawObjectOle } {@link DrawApplet } {@link DrawFloatingFrame } {@link DrawPlugin }
     * {@link DrawMeasure } {@link DrawCaption } {@link DrawConnector } {@link ChartChart }
     * {@link Dr3DScene } {@link DrawControl } {@link DrawCustomShape }
     * 
     * 
     */
    public List<Object> getTableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape() {
        if (this.tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape == null) {
            this.tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape = new ArrayList<Object>();
        }
        return this.tableSubTableOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    }

    /**
     * Gets the value of the tableTimeValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTimeValue() {
        return this.tableTimeValue;
    }

    /**
     * Gets the value of the tableValidationName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableValidationName() {
        return this.tableValidationName;
    }

    /**
     * Gets the value of the tableValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableValue() {
        return this.tableValue;
    }

    /**
     * Gets the value of the tableValueType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableValueType() {
        if (this.tableValueType == null) {
            return "string";
        } else {
            return this.tableValueType;
        }
    }

    /**
     * Sets the value of the officeAnnotation property.
     * 
     * @param value allowed object is {@link OfficeAnnotation }
     * 
     */
    public void setOfficeAnnotation(final OfficeAnnotation value) {
        this.officeAnnotation = value;
    }

    /**
     * Sets the value of the tableBooleanValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableBooleanValue(final String value) {
        this.tableBooleanValue = value;
    }

    /**
     * Sets the value of the tableCellRangeSource property.
     * 
     * @param value allowed object is {@link TableCellRangeSource }
     * 
     */
    public void setTableCellRangeSource(final TableCellRangeSource value) {
        this.tableCellRangeSource = value;
    }

    /**
     * Sets the value of the tableCurrency property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCurrency(final String value) {
        this.tableCurrency = value;
    }

    /**
     * Sets the value of the tableDateValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDateValue(final String value) {
        this.tableDateValue = value;
    }

    /**
     * Sets the value of the tableDetective property.
     * 
     * @param value allowed object is {@link TableDetective }
     * 
     */
    public void setTableDetective(final TableDetective value) {
        this.tableDetective = value;
    }

    /**
     * Sets the value of the tableFormula property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFormula(final String value) {
        this.tableFormula = value;
    }

    /**
     * Sets the value of the tableNumberColumnsRepeated property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberColumnsRepeated(final String value) {
        this.tableNumberColumnsRepeated = value;
    }

    /**
     * Sets the value of the tableNumberMatrixColumnsSpanned property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberMatrixColumnsSpanned(final String value) {
        this.tableNumberMatrixColumnsSpanned = value;
    }

    /**
     * Sets the value of the tableNumberMatrixRowsSpanned property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberMatrixRowsSpanned(final String value) {
        this.tableNumberMatrixRowsSpanned = value;
    }

    /**
     * Sets the value of the tableStringValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStringValue(final String value) {
        this.tableStringValue = value;
    }

    /**
     * Sets the value of the tableStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStyleName(final String value) {
        this.tableStyleName = value;
    }

    /**
     * Sets the value of the tableTimeValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTimeValue(final String value) {
        this.tableTimeValue = value;
    }

    /**
     * Sets the value of the tableValidationName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableValidationName(final String value) {
        this.tableValidationName = value;
    }

    /**
     * Sets the value of the tableValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableValue(final String value) {
        this.tableValue = value;
    }

    /**
     * Sets the value of the tableValueType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableValueType(final String value) {
        this.tableValueType = value;
    }

}
