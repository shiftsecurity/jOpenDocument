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
import org.jopendocument.model.office.OfficeForms;
import org.jopendocument.model.presentation.PresentationNotes;

/**
 * 
 */
public class StyleMasterPage {

    protected List<Object> drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    protected String drawStyleName;
    protected OfficeForms officeForms;
    protected PresentationNotes presentationNotes;
    protected StyleFooter styleFooter;
    protected StyleFooterLeft styleFooterLeft;
    protected StyleHeader styleHeader;
    protected StyleHeaderLeft styleHeaderLeft;
    protected String styleName;
    protected String styleNextStyleName;
    private String stylePageLayoutName;
    protected String stylePageMasterName;
    protected List<StyleStyle> styleStyle;

    /**
     * Gets the value of the
     * drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DrawRect } {@link DrawLine }
     * {@link DrawPolyline } {@link DrawPolygon } {@link DrawPath } {@link DrawCircle }
     * {@link DrawEllipse } {@link DrawG } {@link DrawPageThumbnail } {@link DrawTextBox }
     * {@link DrawImage } {@link DrawObject } {@link DrawObjectOle } {@link DrawApplet }
     * {@link DrawFloatingFrame } {@link DrawPlugin } {@link DrawMeasure } {@link DrawCaption }
     * {@link DrawConnector } {@link ChartChart } {@link Dr3DScene } {@link DrawControl }
     * {@link DrawCustomShape }
     * 
     * 
     */
    public List<Object> getDrawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape() {
        if (this.drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape == null) {
            this.drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape = new ArrayList<Object>();
        }
        return this.drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    }

    /**
     * Gets the value of the drawStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawStyleName() {
        return this.drawStyleName;
    }

    /**
     * Gets the value of the officeForms property.
     * 
     * @return possible object is {@link OfficeForms }
     * 
     */
    public OfficeForms getOfficeForms() {
        return this.officeForms;
    }

    /**
     * Gets the value of the presentationNotes property.
     * 
     * @return possible object is {@link PresentationNotes }
     * 
     */
    public PresentationNotes getPresentationNotes() {
        return this.presentationNotes;
    }

    /**
     * Gets the value of the styleFooter property.
     * 
     * @return possible object is {@link StyleFooter }
     * 
     */
    public StyleFooter getStyleFooter() {
        return this.styleFooter;
    }

    /**
     * Gets the value of the styleFooterLeft property.
     * 
     * @return possible object is {@link StyleFooterLeft }
     * 
     */
    public StyleFooterLeft getStyleFooterLeft() {
        return this.styleFooterLeft;
    }

    /**
     * Gets the value of the styleHeader property.
     * 
     * @return possible object is {@link StyleHeader }
     * 
     */
    public StyleHeader getStyleHeader() {
        return this.styleHeader;
    }

    /**
     * Gets the value of the styleHeaderLeft property.
     * 
     * @return possible object is {@link StyleHeaderLeft }
     * 
     */
    public StyleHeaderLeft getStyleHeaderLeft() {
        return this.styleHeaderLeft;
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
     * Gets the value of the styleNextStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleNextStyleName() {
        return this.styleNextStyleName;
    }

    public String getStylePageLayoutName() {
        if (this.stylePageLayoutName == null) {
            throw new NullPointerException("null page layout name for StyleMasterPage:" + this.styleName);
        }
        return this.stylePageLayoutName;
    }

    /**
     * Gets the value of the stylePageMasterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStylePageMasterName() {
        return this.stylePageMasterName;
    }

    /**
     * Gets the value of the styleStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the styleStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStyleStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StyleStyle }
     * 
     * 
     */
    public List<StyleStyle> getStyleStyle() {
        if (this.styleStyle == null) {
            this.styleStyle = new ArrayList<StyleStyle>();
        }
        return this.styleStyle;
    }

    /**
     * Sets the value of the drawStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawStyleName(final String value) {
        this.drawStyleName = value;
    }

    /**
     * Sets the value of the officeForms property.
     * 
     * @param value allowed object is {@link OfficeForms }
     * 
     */
    public void setOfficeForms(final OfficeForms value) {
        this.officeForms = value;
    }

    /**
     * Sets the value of the presentationNotes property.
     * 
     * @param value allowed object is {@link PresentationNotes }
     * 
     */
    public void setPresentationNotes(final PresentationNotes value) {
        this.presentationNotes = value;
    }

    /**
     * Sets the value of the styleFooter property.
     * 
     * @param value allowed object is {@link StyleFooter }
     * 
     */
    public void setStyleFooter(final StyleFooter value) {
        this.styleFooter = value;
    }

    /**
     * Sets the value of the styleFooterLeft property.
     * 
     * @param value allowed object is {@link StyleFooterLeft }
     * 
     */
    public void setStyleFooterLeft(final StyleFooterLeft value) {
        this.styleFooterLeft = value;
    }

    /**
     * Sets the value of the styleHeader property.
     * 
     * @param value allowed object is {@link StyleHeader }
     * 
     */
    public void setStyleHeader(final StyleHeader value) {
        this.styleHeader = value;
    }

    /**
     * Sets the value of the styleHeaderLeft property.
     * 
     * @param value allowed object is {@link StyleHeaderLeft }
     * 
     */
    public void setStyleHeaderLeft(final StyleHeaderLeft value) {
        this.styleHeaderLeft = value;
    }

    /**
     * Sets the value of the styleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleName(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.styleName = value;
    }

    /**
     * Sets the value of the styleNextStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleNextStyleName(final String value) {
        this.styleNextStyleName = value;
    }

    public void setStylePageLayoutName(final String stylePageLayoutName) {
        if (stylePageLayoutName == null) {
            throw new NullPointerException("null page layout name for StyleMasterPage:" + this.styleName);
        }
        this.stylePageLayoutName = stylePageLayoutName;
    }

    /**
     * Sets the value of the stylePageMasterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStylePageMasterName(final String value) {
        this.stylePageMasterName = value;
    }

    @Override
    public String toString() {
        return "StyleMasterPage: name:" + this.styleName + " layoutName:" + this.stylePageLayoutName;
    }
}
