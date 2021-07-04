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

import org.jopendocument.model.style.StyleStyle;

/**
 * 
 */
public class TableTableRow {
    static int count = 0;
    ArrayList<TableTableCell> allCells;
    TableTableCell[] cachedCellsInRange;

    int cachedFromCol, cachedToCol = -1;

    Vector<TableTableCell> cells = new Vector<TableTableCell>();

    int id = 0;

    private TableTable table;

    protected String tableDefaultCellStyleName;

    protected int tableNumberRowsRepeated = 1;

    protected String tableStyleName;

    protected List<Object> tableTableCellOrTableCoveredTableCell;

    protected String tableVisibility;

    public TableTableRow() {
        this.id = count;
        count++;
    }

    public void addCell(final TableTableCell c) {
        this.cells.add(c);

    }

    /**
     * Compute AllCell except the last one
     */
    void computeAllCells() {
        this.allCells = new ArrayList<TableTableCell>();
        final int size = this.cells.size();
        for (int index = 0; index < size; index++) {
            final TableTableCell c = this.cells.get(index);
            // for (TableTableCell c : cells) {
            final int colPosition = this.allCells.size();
            int repeated = c.getTableNumberColumnsRepeated();
            // la derniere colonne n'est repétée que dans la limite de la zone d'impression
            // sinon, on s'en coltine des milliers
            if (index == size - 1) {
                repeated = this.getTable().getPrintStopCol() - this.allCells.size() + 1;
            }
            for (int i = 0; i < repeated; i++) {
                final TableTableColumn col = this.table.getColumnAtPosition(colPosition + i);
                TableTableCell cc = c;
                if (i > 0) {
                    cc = c.cloneCell();
                }
                cc.setRowAndColumn(this, col);
                this.allCells.add(cc);
            }

        }

    }

    public TableTableCell[] getCellsInRange(final int fromCol, final int toCol) {

        // System.err.println(this + " " + fromCol + " >" + toCol);
        if (this.allCells == null) {
            this.computeAllCells();
        }

        if (this.cachedFromCol == fromCol && this.cachedToCol == toCol) {
            return this.cachedCellsInRange;
        }

        final TableTableCell[] result = new TableTableCell[toCol - fromCol + 1];

        int index = 0;
        try {
            for (int i = fromCol; i <= toCol; i++) {
                final TableTableCell e = this.allCells.get(i);
                result[index] = e;
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cachedCellsInRange = result;
        this.cachedFromCol = fromCol;
        this.cachedToCol = toCol;
        this.cells = null;
        return result;
    }

    public int getHeight() {
        // FIXME: need to be implemented !!!!!!!!
        if (this.getStyle() == null) {
            return 4200; // 4.2mm
        }
        return this.getStyle().getStyleTableRowProperties().getHeight();
    }

    public StyleStyle getStyle() {
        if (this.tableStyleName == null) {
            return null;
        }
        return this.table.getRowStyle(this.tableStyleName);
    }

    public TableTable getTable() {
        return this.table;
    }

    /**
     * Gets the value of the tableDefaultCellStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableDefaultCellStyleName() {
        return this.tableDefaultCellStyleName;
    }

    /**
     * Gets the value of the tableNumberRowsRepeated property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public int getTableNumberRowsRepeated() {

        return this.tableNumberRowsRepeated;

    }

    /**
     * Gets the value of the tableStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableStyleName() {
        return this.tableStyleName;
    }

    /**
     * Gets the value of the tableTableCellOrTableCoveredTableCell property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the tableTableCellOrTableCoveredTableCell
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTableTableCellOrTableCoveredTableCell().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TableTableCell }
     * {@link TableCoveredTableCell }
     * 
     * 
     */
    public List<Object> getTableTableCellOrTableCoveredTableCell() {
        if (this.tableTableCellOrTableCoveredTableCell == null) {
            this.tableTableCellOrTableCoveredTableCell = new ArrayList<Object>();
        }
        return this.tableTableCellOrTableCoveredTableCell;
    }

    // public List<TableTableCell> getCells() {
    // return cells;
    // }

    /**
     * Gets the value of the tableVisibility property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTableVisibility() {
        if (this.tableVisibility == null) {
            return "visible";
        }
        return this.tableVisibility;

    }

    public String getText() {
        if (this.allCells == null) {
            this.computeAllCells();
        }
        String t = "";
        final int size = this.allCells.size();
        for (int index = 0; index < size; index++) {
            final TableTableCell c = this.allCells.get(index);
            t += c.getTextP();
        }
        return t;
    }

    public void setTable(final TableTable t) {
        this.table = t;

    }

    /**
     * Sets the value of the tableDefaultCellStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableDefaultCellStyleName(final String value) {
        this.tableDefaultCellStyleName = value;
    }

    /**
     * Sets the value of the tableNumberRowsRepeated property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableNumberRowsRepeated(final String value) {
        if (value != null) {
            this.tableNumberRowsRepeated = Integer.valueOf(value).intValue();

        }
    }

    /**
     * Sets the value of the tableStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableStyleName(final String value) {
        this.tableStyleName = value;
    }

    /**
     * Sets the value of the tableVisibility property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTableVisibility(final String value) {
        this.tableVisibility = value;
    }

    @Override
    public String toString() {
        return "TableRow" + this.id;
    }
}
