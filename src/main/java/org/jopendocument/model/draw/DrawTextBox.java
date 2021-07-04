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
import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.text.TextAlphabeticalIndex;
import org.jopendocument.model.text.TextBibliography;
import org.jopendocument.model.text.TextChange;
import org.jopendocument.model.text.TextChangeEnd;
import org.jopendocument.model.text.TextChangeStart;
import org.jopendocument.model.text.TextH;
import org.jopendocument.model.text.TextIllustrationIndex;
import org.jopendocument.model.text.TextIndexTitle;
import org.jopendocument.model.text.TextObjectIndex;
import org.jopendocument.model.text.TextOrderedList;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextSection;
import org.jopendocument.model.text.TextTableIndex;
import org.jopendocument.model.text.TextTableOfContent;
import org.jopendocument.model.text.TextUnorderedList;
import org.jopendocument.model.text.TextUserIndex;

/**
 * 
 */
public class DrawTextBox {

    protected String drawChainNextName;
    protected String drawCornerRadius;
    protected String drawId;
    protected DrawImageMap drawImageMap;
    protected String drawLayer;
    protected String drawName;
    protected String drawStyleName;
    protected String drawTextStyleName;
    protected String drawTransform;
    protected String drawZIndex;
    protected String foMinHeight;
    protected OfficeEvents officeEvents;
    protected String presentationClass;
    protected String presentationPlaceholder;
    protected String presentationStyleName;
    protected String presentationUserTransformed;
    protected String styleRelHeight;
    protected String styleRelWidth;
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

    // List of elements:
    // textH or TextP or TextOrderedList
    // or TextUnorderedList or TableTable
    // or TextSection Or TextTableOfContent
    // or TextIllustrationIndex Or TextTableIndex
    // or TextObjectIndex or TextUserIndex
    // or TextAlphabeticalIndex or TextBibliography
    // or TextIndexTitle or TextChange
    // or TextChangeStart or TextChangeEnd
    protected List<Object> elements = new ArrayList<Object>();

    /**
     * Gets the value of the drawChainNextName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawChainNextName() {
        return this.drawChainNextName;
    }

    /**
     * Gets the value of the drawCornerRadius property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawCornerRadius() {
        return this.drawCornerRadius;
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
     * Gets the value of the foMinHeight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoMinHeight() {
        return this.foMinHeight;
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

    /**
     * Sets the value of the drawChainNextName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawChainNextName(final String value) {
        this.drawChainNextName = value;
    }

    /**
     * Sets the value of the drawCornerRadius property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawCornerRadius(final String value) {
        this.drawCornerRadius = value;
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
     * Sets the value of the foMinHeight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoMinHeight(final String value) {
        this.foMinHeight = value;
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

    public void addTextElement(TextP p) {
        this.elements.add(p);
    }

}
