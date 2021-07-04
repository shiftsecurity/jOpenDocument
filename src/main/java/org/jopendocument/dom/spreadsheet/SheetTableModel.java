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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.util.CompareUtils;

import javax.swing.table.AbstractTableModel;

public class SheetTableModel<D extends ODDocument> extends AbstractTableModel {

    protected final Table<D> table;
    protected final int row;
    protected final int column;
    protected final int lastRow;
    protected final int lastCol;

    SheetTableModel(final Table<D> table, final int row, final int column) {
        this(table, row, column, table.getRowCount(), table.getColumnCount());
    }

    /**
     * Creates a new instance.
     * 
     * @param table parent table.
     * @param row the first row, inclusive.
     * @param column the first column, inclusive.
     * @param lastRow the last row, exclusive.
     * @param lastCol the last column, exclusive.
     */
    SheetTableModel(final Table<D> table, final int row, final int column, final int lastRow, final int lastCol) {
        super();
        this.table = table;
        this.row = row;
        this.column = column;
        this.lastRow = lastRow;
        this.lastCol = lastCol;
    }

    @Override
    public int getColumnCount() {
        return this.lastCol - this.column;
    }

    @Override
    public int getRowCount() {
        return this.lastRow - this.row;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        check(rowIndex, columnIndex);
        return this.table.getValueAt(this.column + columnIndex, this.row + rowIndex);
    }

    public Cell<D> getImmutableCellAt(int rowIndex, int columnIndex) {
        check(rowIndex, columnIndex);
        return this.table.getImmutableCellAt(this.column + columnIndex, this.row + rowIndex);
    }

    // protect cells outside our range
    protected final void check(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= this.getRowCount())
            throw new IndexOutOfBoundsException("row :" + rowIndex + " not between 0 and " + (this.getRowCount() - 1));
        if (columnIndex < 0 || columnIndex >= this.getColumnCount())
            throw new IndexOutOfBoundsException("column: " + columnIndex + " not between 0 and " + (this.getColumnCount() - 1));
    }

    @Override
    public int hashCode() {
        final int rowCount = getRowCount();
        final int columnCount = getColumnCount();
        final int prime = 17;
        int result = 1;
        result = prime * result + rowCount;
        result = prime * result + columnCount;
        // use some of the values
        final int maxX = Math.min(4, columnCount);
        final int maxY = Math.min(8, rowCount);
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                final Object v = this.getValueAt(x, y);
                result = prime * result + (v == null ? 0 : v.hashCode());
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SheetTableModel))
            return false;
        final SheetTableModel<?> other = (SheetTableModel<?>) obj;

        final int rowCount = this.getRowCount();
        final int columnCount = this.getColumnCount();
        if (other.getRowCount() != rowCount || other.getColumnCount() != columnCount)
            return false;

        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                if (!CompareUtils.equals(this.getValueAt(x, y), other.getValueAt(x, y)))
                    return false;
            }
        }
        return true;
    }

    static public final class MutableTableModel<D extends ODDocument> extends SheetTableModel<D> {

        MutableTableModel(final Table<D> table, final int row, final int column) {
            super(table, row, column);
        }

        MutableTableModel(final Table<D> table, final int row, final int column, final int lastRow, final int lastCol) {
            super(table, row, column, lastRow, lastCol);
        }

        @Override
        public void setValueAt(final Object obj, final int rowIndex, final int columnIndex) {
            check(rowIndex, columnIndex);
            this.table.setValueAt(obj, this.column + columnIndex, this.row + rowIndex);
        }

        public MutableCell<D> getCellAt(int rowIndex, int columnIndex) {
            check(rowIndex, columnIndex);
            return this.table.getCellAt(this.column + columnIndex, this.row + rowIndex);
        }
    }
}