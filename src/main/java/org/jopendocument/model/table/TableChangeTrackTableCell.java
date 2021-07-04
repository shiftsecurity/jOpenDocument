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

import org.jopendocument.model.text.TextP;

/**
 * 
 */
public class TableChangeTrackTableCell {

    protected String tableCellAddress;
    protected String tableDateValue;
    protected String tableFormula;
    protected String tableMatrixCovered;
    protected String tableNumberMatrixColumnsSpanned;
    protected String tableNumberMatrixRowsSpanned;
    protected String tableStringValue;
    protected String tableTimeValue;
    protected String tableValue;
    protected String tableValueType;
    protected List<TextP> textP;

    /**
     * Gets the value of the tableCellAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableCellAddress() {
        return this.tableCellAddress;
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
     * Gets the value of the tableFormula property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableFormula() {
        return this.tableFormula;
    }

    /**
     * Gets the value of the tableMatrixCovered property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableMatrixCovered() {
        if (this.tableMatrixCovered == null) {
            return "false";
        } else {
            return this.tableMatrixCovered;
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
     * Gets the value of the tableTimeValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableTimeValue() {
        return this.tableTimeValue;
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
     * Gets the value of the textP property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textP property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextP }
     * 
     * 
     */
    public List<TextP> getTextP() {
        if (this.textP == null) {
            this.textP = new ArrayList<TextP>();
        }
        return this.textP;
    }

    /**
     * Sets the value of the tableCellAddress property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableCellAddress(final String value) {
        this.tableCellAddress = value;
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
     * Sets the value of the tableFormula property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableFormula(final String value) {
        this.tableFormula = value;
    }

    /**
     * Sets the value of the tableMatrixCovered property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableMatrixCovered(final String value) {
        this.tableMatrixCovered = value;
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
     * Sets the value of the tableTimeValue property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableTimeValue(final String value) {
        this.tableTimeValue = value;
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
