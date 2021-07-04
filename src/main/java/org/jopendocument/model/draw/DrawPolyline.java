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

import org.jopendocument.model.office.OfficeEvents;
import org.jopendocument.model.text.TextOrderedList;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextUnorderedList;

/**
 * 
 */
public class DrawPolyline {

    protected String drawId;
    protected String drawLayer;
    protected String drawPoints;
    protected String drawStyleName;
    protected String drawTextStyleName;
    protected String drawTransform;
    protected String drawZIndex;
    protected OfficeEvents officeEvents;
    protected String presentationStyleName;
    protected String svgHeight;
    protected String svgViewBox;
    protected String svgWidth;
    protected String svgX;
    protected String svgY;
    protected String tableEndCellAddress;
    protected String tableEndX;
    protected String tableEndY;
    protected String tableTableBackground;
    protected String textAnchorPageNumber;
    protected String textAnchorType;
    protected List<Object> textPOrTextUnorderedListOrTextOrderedList;

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
     * Gets the value of the drawLayer property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawLayer() {
        return this.drawLayer;
    }

    /**
     * Gets the value of the drawPoints property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawPoints() {
        return this.drawPoints;
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
     * Gets the value of the officeEvents property.
     * 
     * @return possible object is {@link OfficeEvents }
     * 
     */
    public OfficeEvents getOfficeEvents() {
        return this.officeEvents;
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
     * Gets the value of the svgHeight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgHeight() {
        return this.svgHeight;
    }

    /**
     * Gets the value of the svgViewBox property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSvgViewBox() {
        return this.svgViewBox;
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

    /**
     * Gets the value of the textPOrTextUnorderedListOrTextOrderedList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textPOrTextUnorderedListOrTextOrderedList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextPOrTextUnorderedListOrTextOrderedList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextP }
     * {@link TextUnorderedList } {@link TextOrderedList }
     * 
     * 
     */
    public List<Object> getTextPOrTextUnorderedListOrTextOrderedList() {
        if (this.textPOrTextUnorderedListOrTextOrderedList == null) {
            this.textPOrTextUnorderedListOrTextOrderedList = new ArrayList<Object>();
        }
        return this.textPOrTextUnorderedListOrTextOrderedList;
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
     * Sets the value of the drawLayer property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawLayer(final String value) {
        this.drawLayer = value;
    }

    /**
     * Sets the value of the drawPoints property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawPoints(final String value) {
        this.drawPoints = value;
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
     * Sets the value of the officeEvents property.
     * 
     * @param value allowed object is {@link OfficeEvents }
     * 
     */
    public void setOfficeEvents(final OfficeEvents value) {
        this.officeEvents = value;
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
     * Sets the value of the svgHeight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgHeight(final String value) {
        this.svgHeight = value;
    }

    /**
     * Sets the value of the svgViewBox property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSvgViewBox(final String value) {
        this.svgViewBox = value;
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

}
