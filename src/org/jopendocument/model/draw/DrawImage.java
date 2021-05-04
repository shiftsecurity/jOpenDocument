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

import org.jopendocument.model.office.OfficeBinaryData;
import org.jopendocument.model.office.OfficeEvents;
import org.jopendocument.model.text.TextP;

/**
 * 
 */
public class DrawImage {

    protected List<Object> drawContourPolygonOrDrawContourPath;
    protected String drawFilterName;
    protected String drawId;
    protected DrawImageMap drawImageMap;
    protected String drawLayer;
    protected String drawName;
    protected String drawStyleName;
    protected String drawTextStyleName;
    protected String drawTransform;
    protected String drawZIndex;
    protected OfficeBinaryData officeBinaryData;
    protected OfficeEvents officeEvents;
    protected String presentationClass;
    protected String presentationPlaceholder;
    protected String presentationStyleName;
    protected String presentationUserTransformed;
    protected String styleRelHeight;
    protected String styleRelWidth;
    protected SvgDesc svgDesc;
    protected String svgHeight;
    protected String svgWidth;
    protected String svgX;
    protected String svgY;
    protected String tableEndCellAddress;
    protected String tableEndX;
    protected String tableEndY;
    protected String tableTableBackground;
    protected String textAnchorPageNumber;
    protected String textAnchorType;
    private TextP textp;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the drawContourPolygonOrDrawContourPath property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the drawContourPolygonOrDrawContourPath
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDrawContourPolygonOrDrawContourPath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DrawContourPolygon }
     * {@link DrawContourPath }
     * 
     * 
     */
    public List<Object> getDrawContourPolygonOrDrawContourPath() {
        if (this.drawContourPolygonOrDrawContourPath == null) {
            this.drawContourPolygonOrDrawContourPath = new ArrayList<Object>();
        }
        return this.drawContourPolygonOrDrawContourPath;
    }

    /**
     * Gets the value of the drawFilterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawFilterName() {
        return this.drawFilterName;
    }

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
     * Gets the value of the drawImageMap property.
     * 
     * @return possible object is {@link DrawImageMap }
     * 
     */
    public DrawImageMap getDrawImageMap() {
        return this.drawImageMap;
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
     * Gets the value of the drawName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawName() {
        return this.drawName;
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
     * Gets the value of the drawTransform property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawTransform() {
        return this.drawTransform;
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
     * Gets the value of the officeBinaryData property.
     * 
     * @return possible object is {@link OfficeBinaryData }
     * 
     */
    public OfficeBinaryData getOfficeBinaryData() {
        return this.officeBinaryData;
    }

    /**
     * Gets the value of the officeEvents property.
     * 
     * @return possible object is {@link OfficeEvents }
     * 
     */
    public OfficeEvents getOfficeEvents() {
        return this.officeEvents;
    }

    /**
     * Gets the value of the presentationClass property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationClass() {
        return this.presentationClass;
    }

    /**
     * Gets the value of the presentationPlaceholder property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationPlaceholder() {
        return this.presentationPlaceholder;
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
     * Gets the value of the presentationUserTransformed property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationUserTransformed() {
        return this.presentationUserTransformed;
    }

    /**
     * Gets the value of the styleRelHeight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleRelHeight() {
        return this.styleRelHeight;
    }

    /**
     * Gets the value of the styleRelWidth property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleRelWidth() {
        return this.styleRelWidth;
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
     * Gets the value of the tableTableBackground property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTableBackground() {
        return this.tableTableBackground;
    }

    /**
     * Gets the value of the textAnchorPageNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextAnchorPageNumber() {
        return this.textAnchorPageNumber;
    }

    /**
     * Gets the value of the textAnchorType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextAnchorType() {
        return this.textAnchorType;
    }

    public TextP getTextp() {
        return this.textp;
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
     * Sets the value of the drawFilterName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawFilterName(final String value) {
        this.drawFilterName = value;
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
     * Sets the value of the drawImageMap property.
     * 
     * @param value allowed object is {@link DrawImageMap }
     * 
     */
    public void setDrawImageMap(final DrawImageMap value) {
        this.drawImageMap = value;
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
     * Sets the value of the drawTextStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawTextStyleName(final String value) {
        this.drawTextStyleName = value;
    }

    /**
     * Sets the value of the drawTransform property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawTransform(final String value) {
        this.drawTransform = value;
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
     * Sets the value of the officeBinaryData property.
     * 
     * @param value allowed object is {@link OfficeBinaryData }
     * 
     */
    public void setOfficeBinaryData(final OfficeBinaryData value) {
        this.officeBinaryData = value;
    }

    /**
     * Sets the value of the officeEvents property.
     * 
     * @param value allowed object is {@link OfficeEvents }
     * 
     */
    public void setOfficeEvents(final OfficeEvents value) {
        this.officeEvents = value;
    }

    /**
     * Sets the value of the presentationClass property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationClass(final String value) {
        this.presentationClass = value;
    }

    /**
     * Sets the value of the presentationPlaceholder property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationPlaceholder(final String value) {
        this.presentationPlaceholder = value;
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
     * Sets the value of the presentationUserTransformed property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationUserTransformed(final String value) {
        this.presentationUserTransformed = value;
    }

    /**
     * Sets the value of the styleRelHeight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleRelHeight(final String value) {
        this.styleRelHeight = value;
    }

    /**
     * Sets the value of the styleRelWidth property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleRelWidth(final String value) {
        this.styleRelWidth = value;
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
     * Sets the value of the tableTableBackground property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTableBackground(final String value) {
        this.tableTableBackground = value;
    }

    /**
     * Sets the value of the textAnchorPageNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextAnchorPageNumber(final String value) {
        this.textAnchorPageNumber = value;
    }

    /**
     * Sets the value of the textAnchorType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextAnchorType(final String value) {
        this.textAnchorType = value;
    }

    public void setTextP(final TextP p) {
        this.textp = p;

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
