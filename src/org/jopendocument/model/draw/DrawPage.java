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

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.chart.ChartChart;
import org.jopendocument.model.office.OfficeForms;
import org.jopendocument.model.presentation.PresentationAnimations;
import org.jopendocument.model.presentation.PresentationNotes;

/**
 * 
 */
public class DrawPage {

    protected String drawId;
    protected String drawMasterPageName;
    protected String drawName;
    protected List<Object> drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    protected String drawStyleName;
    protected OfficeForms officeForms;
    protected PresentationAnimations presentationAnimations;
    protected PresentationNotes presentationNotes;
    protected String presentationPresentationPageLayoutName;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the drawId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawId() {
        return this.drawId;
    }

    /**
     * Gets the value of the drawMasterPageName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawMasterPageName() {
        return this.drawMasterPageName;
    }

    /**
     * Gets the value of the drawName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawName() {
        return this.drawName;
    }

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
     * Gets the value of the presentationAnimations property.
     * 
     * @return possible object is {@link PresentationAnimations }
     * 
     */
    public PresentationAnimations getPresentationAnimations() {
        return this.presentationAnimations;
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
     * Gets the value of the presentationPresentationPageLayoutName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationPresentationPageLayoutName() {
        return this.presentationPresentationPageLayoutName;
    }

    /**
     * Gets the value of the xlinkActuate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkActuate() {
        return this.xlinkActuate;
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
        return this.xlinkShow;
    }

    /**
     * Gets the value of the xlinkType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkType() {
        return this.xlinkType;
    }

    /**
     * Sets the value of the drawId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawId(final String value) {
        this.drawId = value;
    }

    /**
     * Sets the value of the drawMasterPageName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawMasterPageName(final String value) {
        this.drawMasterPageName = value;
    }

    /**
     * Sets the value of the drawName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawName(final String value) {
        this.drawName = value;
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
     * Sets the value of the presentationAnimations property.
     * 
     * @param value allowed object is {@link PresentationAnimations }
     * 
     */
    public void setPresentationAnimations(final PresentationAnimations value) {
        this.presentationAnimations = value;
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
     * Sets the value of the presentationPresentationPageLayoutName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationPresentationPageLayoutName(final String value) {
        this.presentationPresentationPageLayoutName = value;
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
