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
import java.util.Vector;

import org.jopendocument.model.draw.DrawFrame;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.office.OfficeAnnotation;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.text.TextP;

public class TableTableCell {

    private StyleStyle cellStyle;

    private TableTableColumn column;

    private boolean covered;

    private List<DrawFrame> drawFrames;

    private DrawImage drawImage;

    protected OfficeAnnotation officeAnnotation;

    protected TableTableRow row;

    protected String tableBooleanValue;

    protected TableCellRangeSource tableCellRangeSource;

    protected String tableCurrency;

    protected String tableDateValue;

    protected TableDetective tableDetective;

    protected String tableFormula;

    protected int tableNumberColumnsRepeated = 1;

    protected int tableNumberColumnsSpanned = 1;

    protected String tableNumberMatrixColumnsSpanned;

    protected String tableNumberMatrixRowsSpanned;

    protected int tableNumberRowsSpanned = 1;

    protected String tableProtected;

    protected String tableStringValue;

    protected String tableStyleName;
    protected String tableTimeValue;
    protected String tableValidationName;

    protected String tableValue;

    protected String tableValueType;

    private List<TextP> textP;

    public void addDrawFrame(final DrawFrame p) {
        if (this.drawFrames == null) {
            this.drawFrames = new Vector<DrawFrame>();
        }
        this.drawFrames.add(p);
    }

    protected TableTableCell cloneCell() {
        final TableTableCell c = new TableTableCell();

        c.tableNumberColumnsRepeated = this.tableNumberColumnsRepeated;
        c.tableNumberRowsSpanned = this.tableNumberRowsSpanned;
        c.tableNumberColumnsSpanned = this.tableNumberColumnsSpanned;
        c.tableStyleName = this.tableStyleName;
        c.tableValidationName = this.tableValidationName;
        c.tableFormula = this.tableFormula;
        c.tableNumberMatrixRowsSpanned = this.tableNumberMatrixRowsSpanned;
        c.tableNumberMatrixColumnsSpanned = this.tableNumberMatrixColumnsSpanned;
        c.tableValueType = this.tableValueType;
        c.tableValue = this.tableValue;
        c.tableDateValue = this.tableDateValue;
        c.tableTimeValue = this.tableTimeValue;
        c.tableBooleanValue = this.tableBooleanValue;
        c.tableStringValue = this.tableStringValue;
        c.tableCurrency = this.tableCurrency;
        c.tableProtected = this.tableProtected;
        c.tableCellRangeSource = this.tableCellRangeSource;
        c.tableDetective = this.tableDetective;
        c.officeAnnotation = this.officeAnnotation;
        c.textP = this.textP;
        c.covered = this.covered;
        c.drawImage = this.drawImage;
        c.drawFrames = this.drawFrames;
        return c;
    }

    private void computeStyle() {
        if (this.column == null) {
            return;
        }
        String styleName = this.getStyleName();

        if (styleName == null) {
            styleName = this.column.getTableDefaultCellStyleName();
        }
        this.cellStyle = this.row.getTable().getCellStyle(styleName);
        if (this.cellStyle == null /* && styleName != null */) {
            System.err.println(styleName + " not found");
            Thread.dumpStack();
        }

    }

    public List<DrawFrame> getDrawFrames() {
        return this.drawFrames;
    }

    /**
     * Gets the value of the officeAnnotation property.
     * 
     * @return possible object is {@link OfficeAnnotation }
     * 
     */
    public OfficeAnnotation getOfficeAnnotation() {
        return this.officeAnnotation;
    }

    public TableTableRow getRow() {
        return this.row;
    }

    public StyleStyle getStyle() {
        return this.cellStyle;
    }

    /**
     * Gets the value of the tableStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleName() {
        return this.tableStyleName;
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
     */
    public int getTableNumberColumnsRepeated() {
        return this.tableNumberColumnsRepeated;
    }

    /**
     * Gets the value of the tableNumberColumnsSpanned property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public int getTableNumberColumnsSpanned() {

        return this.tableNumberColumnsSpanned;

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
     * Gets the value of the tableNumberRowsSpanned property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public int getTableNumberRowsSpanned() {

        return this.tableNumberRowsSpanned;

    }

    /**
     * Gets the value of the tableProtected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableProtected() {
        if (this.tableProtected == null) {
            return "false";
        } else {
            return this.tableProtected;
        }
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

    public List<TextP> getTextP() {
        return this.textP;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(final boolean b) {
        this.covered = b;

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

    public void setRowAndColumn(final TableTableRow r, final TableTableColumn c) {
        this.row = r;
        this.column = c;
        this.computeStyle();
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
        if (value != null) {
            this.tableNumberColumnsRepeated = Integer.valueOf(value);
        }
    }

    /**
     * Sets the value of the tableNumberColumnsSpanned property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberColumnsSpanned(final String value) {
        if (value != null) {
            this.tableNumberColumnsSpanned = Integer.valueOf(value);
        }
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
     * Sets the value of the tableNumberRowsSpanned property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberRowsSpanned(final String value) {
        if (value != null) {
            this.tableNumberRowsSpanned = Integer.valueOf(value);
        }
    }

    /**
     * Sets the value of the tableProtected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableProtected(final String value) {
        this.tableProtected = value;
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
        this.computeStyle();

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

    public void addTextP(final TextP p) {
        if (this.textP == null) {
            this.textP = new ArrayList<TextP>(3);
        }
        this.textP.add(p);
    }

    @Override
    public String toString() {
        return "Cell: style:" + this.getStyleName() + " TestP:" + this.getTextP();
    }

    public String getFullText() {
        String result = "";
        if (this.getTextP() != null) {
            for (TextP t : this.textP) {
                result += t.getFullText();
            }
        }

        return result;
    }
}
