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

/**
 * 
 */
public class DrawApplet {

    protected String drawArchive;
    protected String drawCode;
    protected String drawLayer;
    protected String drawMayScript;
    protected String drawName;
    protected String drawObject;
    protected List<DrawParam> drawParam;
    protected String drawStyleName;
    protected String drawTextStyleName;
    protected DrawThumbnail drawThumbnail;
    protected String drawZIndex;
    protected String presentationStyleName;
    protected SvgDesc svgDesc;
    protected String svgHeight;
    protected String svgWidth;
    protected String svgX;
    protected String svgY;
    protected String tableEndCellAddress;
    protected String tableEndX;
    protected String tableEndY;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the drawArchive property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawArchive() {
        return this.drawArchive;
    }

    /**
     * Gets the value of the drawCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawCode() {
        return this.drawCode;
    }

    /**
     * Gets the value of the drawLayer property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawLayer() {
        return this.drawLayer;
    }

    /**
     * Gets the value of the drawMayScript property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawMayScript() {
        if (this.drawMayScript == null) {
            return "false";
        } else {
            return this.drawMayScript;
        }
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
     * Gets the value of the drawObject property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawObject() {
        return this.drawObject;
    }

    /**
     * Gets the value of the drawParam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the drawParam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDrawParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DrawParam }
     * 
     * 
     */
    public List<DrawParam> getDrawParam() {
        if (this.drawParam == null) {
            this.drawParam = new ArrayList<DrawParam>();
        }
        return this.drawParam;
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
     * Gets the value of the drawTextStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawTextStyleName() {
        return this.drawTextStyleName;
    }

    /**
     * Gets the value of the drawThumbnail property.
     * 
     * @return possible object is {@link DrawThumbnail }
     * 
     */
    public DrawThumbnail getDrawThumbnail() {
        return this.drawThumbnail;
    }

    /**
     * Gets the value of the drawZIndex property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawZIndex() {
        return this.drawZIndex;
    }

    /**
     * Gets the value of the presentationStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationStyleName() {
        return this.presentationStyleName;
    }

    /**
     * Gets the value of the svgDesc property.
     * 
     * @return possible object is {@link SvgDesc }
     * 
     */
    public SvgDesc getSvgDesc() {
        return this.svgDesc;
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
     * Gets the value of the tableEndCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableEndCellAddress() {
        return this.tableEndCellAddress;
    }

    /**
     * Gets the value of the tableEndX property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableEndX() {
        return this.tableEndX;
    }

    /**
     * Gets the value of the tableEndY property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableEndY() {
        return this.tableEndY;
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
     * Sets the value of the drawArchive property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawArchive(final String value) {
        this.drawArchive = value;
    }

    /**
     * Sets the value of the drawCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawCode(final String value) {
        this.drawCode = value;
    }

    /**
     * Sets the value of the drawLayer property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawLayer(final String value) {
        this.drawLayer = value;
    }

    /**
     * Sets the value of the drawMayScript property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawMayScript(final String value) {
        this.drawMayScript = value;
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
     * Sets the value of the drawObject property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawObject(final String value) {
        this.drawObject = value;
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
     * Sets the value of the drawTextStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawTextStyleName(final String value) {
        this.drawTextStyleName = value;
    }

    /**
     * Sets the value of the drawThumbnail property.
     * 
     * @param value allowed object is {@link DrawThumbnail }
     * 
     */
    public void setDrawThumbnail(final DrawThumbnail value) {
        this.drawThumbnail = value;
    }

    /**
     * Sets the value of the drawZIndex property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawZIndex(final String value) {
        this.drawZIndex = value;
    }

    /**
     * Sets the value of the presentationStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationStyleName(final String value) {
        this.presentationStyleName = value;
    }

    /**
     * Sets the value of the svgDesc property.
     * 
     * @param value allowed object is {@link SvgDesc }
     * 
     */
    public void setSvgDesc(final SvgDesc value) {
        this.svgDesc = value;
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
     * Sets the value of the tableEndCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableEndCellAddress(final String value) {
        this.tableEndCellAddress = value;
    }

    /**
     * Sets the value of the tableEndX property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableEndX(final String value) {
        this.tableEndX = value;
    }

    /**
     * Sets the value of the tableEndY property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableEndY(final String value) {
        this.tableEndY = value;
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
