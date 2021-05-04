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

import org.jopendocument.io.StyleTableProperties;
import org.jopendocument.model.office.OfficeEvents;
import org.jopendocument.model.script.StyleGraphicProperties;

/**
 * 
 */
public class StyleStyle {

    protected OfficeEvents officeEvents;

    private StyleParagraphProperties paragraphProperties;

    protected String styleAutoUpdate;

    protected String styleClass;

    protected String styleDataStyleName;

    protected String styleFamily;

    protected String styleListStyleName;

    protected List<StyleMap> styleMap;

    private String styleMasterPageName;

    protected String styleName;

    protected String styleNextStyleName;

    protected String styleParentStyleName;

    protected StyleProperties styleProperties;

    private StyleTableCellProperties styleTableCellProperties;

    private StyleTableColumnProperties styleTableColumnProperties;

    private StyleTableProperties styleTableProperties;

    private StyleTableRowProperties styleTableRowProperties;

    private StyleTextProperties styleTextProperties;

    private StyleGraphicProperties styleGraphicProperties;

    private StyleSectionProperties styleSectionProperties;

    /**
     * Gets the value of the styleMasterPageName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMasterPageName() {
        return this.styleMasterPageName;
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

    public StyleParagraphProperties getParagraphProperties() {
        return this.paragraphProperties;
    }

    /**
     * Gets the value of the styleAutoUpdate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleAutoUpdate() {
        if (this.styleAutoUpdate == null) {
            return "false";
        } else {
            return this.styleAutoUpdate;
        }
    }

    /**
     * Gets the value of the styleClass property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleClass() {
        return this.styleClass;
    }

    /**
     * Gets the value of the styleDataStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDataStyleName() {
        return this.styleDataStyleName;
    }

    /**
     * Gets the value of the styleFamily property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleFamily() {
        return this.styleFamily;
    }

    /**
     * Gets the value of the styleListStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleListStyleName() {
        return this.styleListStyleName;
    }

    /**
     * Gets the value of the styleMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the styleMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStyleMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StyleMap }
     * 
     * 
     */
    public List<StyleMap> getStyleMap() {
        if (this.styleMap == null) {
            this.styleMap = new ArrayList<StyleMap>();
        }
        return this.styleMap;
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

    /**
     * Gets the value of the styleParentStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleParentStyleName() {
        return this.styleParentStyleName;
    }

    /**
     * Gets the value of the styleProperties property.
     * 
     * @return possible object is {@link StyleProperties }
     * 
     */
    public StyleProperties getStyleProperties() {
        return this.styleProperties;
    }

    public StyleTableCellProperties getStyleTableCellProperties() {
        return this.styleTableCellProperties;
    }

    public StyleTableColumnProperties getStyleTableColumnProperties() {
        return this.styleTableColumnProperties;
    }

    public StyleTableRowProperties getStyleTableRowProperties() {
        return this.styleTableRowProperties;
    }

    public StyleTextProperties getStyleTextProperties() {
        return this.styleTextProperties;
    }

    public StyleTableProperties getTableProperties() {
        return this.styleTableProperties;
    }

    public StyleGraphicProperties getStyleGraphicProperties() {
        return styleGraphicProperties;
    }

    public StyleSectionProperties getStyleSectionProperties() {
        return styleSectionProperties;
    }

    public final int getWidth() {

        return this.styleTableColumnProperties.getColumnWidth();
    }

    /**
     * Sets the value of the styleMasterPageName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMasterPageName(final String value) {
        this.styleMasterPageName = value;
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

    public void setParagraphProperties(final StyleParagraphProperties props) {
        this.paragraphProperties = props;

    }

    /**
     * Sets the value of the styleAutoUpdate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleAutoUpdate(final String value) {
        this.styleAutoUpdate = value;
    }

    /**
     * Sets the value of the styleClass property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleClass(final String value) {
        this.styleClass = value;
    }

    /**
     * Sets the value of the styleDataStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDataStyleName(final String value) {
        this.styleDataStyleName = value;
    }

    /**
     * Sets the value of the styleFamily property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleFamily(final String value) {
        this.styleFamily = value;
    }

    /**
     * Sets the value of the styleListStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleListStyleName(final String value) {
        this.styleListStyleName = value;
    }

    /**
     * Sets the value of the styleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleName(final String value) {
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

    /**
     * Sets the value of the styleParentStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleParentStyleName(final String value) {
        this.styleParentStyleName = value;
    }

    /**
     * Sets the value of the styleProperties property.
     * 
     * @param value allowed object is {@link StyleProperties }
     * 
     */
    public void setStyleProperties(final StyleProperties value) {
        this.styleProperties = value;
    }

    public void setTableCellProperties(final StyleTableCellProperties props) {
        this.styleTableCellProperties = props;

    }

    public void setTableColumnProperties(final StyleTableColumnProperties props) {
        this.styleTableColumnProperties = props;
    }

    public void setTableProperties(final StyleTableProperties props) {
        this.styleTableProperties = props;

    }

    public void setTableRowProperties(final StyleTableRowProperties props) {
        this.styleTableRowProperties = props;
    }

    public void setTextProperties(final StyleTextProperties props) {
        this.styleTextProperties = props;

    }

    public void setGraphicProperties(StyleGraphicProperties props) {
        this.styleGraphicProperties = props;

    }

    public void setSectionProperties(StyleSectionProperties props) {
        this.styleSectionProperties = props;

    }

    @Override
    public String toString() {
        return "StyleStyle: name:" + this.getStyleName() + " family:" + this.getStyleFamily() + " cellProps:" + this.styleTableCellProperties + " masterTableName:" + this.styleMasterPageName;
    }

}
