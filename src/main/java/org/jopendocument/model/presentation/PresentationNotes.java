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

package org.jopendocument.model.presentation;

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

/**
 * 
 */
public class PresentationNotes {

    protected List<Object> drawRectOrDrawLineOrDrawPolylineOrDrawPolygonOrDrawPathOrDrawCircleOrDrawEllipseOrDrawGOrDrawPageThumbnailOrDrawTextBoxOrDrawImageOrDrawObjectOrDrawObjectOleOrDrawAppletOrDrawFloatingFrameOrDrawPluginOrDrawMeasureOrDrawCaptionOrDrawConnectorOrChartChartOrDr3DSceneOrDrawControlOrDrawCustomShape;
    protected String drawStyleName;
    protected String stylePageMasterName;

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
     * Gets the value of the stylePageMasterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStylePageMasterName() {
        return this.stylePageMasterName;
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
     * Sets the value of the stylePageMasterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStylePageMasterName(final String value) {
        this.stylePageMasterName = value;
    }

}
