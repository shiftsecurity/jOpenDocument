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

/*
 * Row created on 10 décembre 2005
 */
package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;

import java.util.Arrays;
import java.util.List;

import org.jdom.Element;

/**
 * A row in a Calc document. This class will only break "repeated" attributes on demand (eg for
 * setting a value).
 * 
 * @author Sylvain
 * @param <D> type of document
 */
public class Row<D extends ODDocument> extends TableCalcNode<RowStyle, D> {

    static Element createEmpty(XMLVersion ns) {
        return new Element("table-row", ns.getTABLE());
    }

    private final Table<D> parent;
    private final int index;
    private int repeated;
    // the same immutable cell instance is repeated, but each MutableCell is only once
    // ATTN MutableCell have their index as attribute
    // array is faster than List
    private Cell<D>[] cells;
    private int cellCount;

    Row(Table<D> parent, Element tableRowElem, int index, StyleDesc<RowStyle> styleDesc, StyleDesc<CellStyle> cellStyleDesc) {
        super(parent.getODDocument(), tableRowElem, styleDesc);
        this.parent = parent;
        this.index = index;
        this.repeated = Axis.ROW.getRepeated(getElement());
        @SuppressWarnings("unchecked")
        final Cell<D>[] unsafe = new Cell[parent.getColumnCount()];
        this.cells = unsafe;
        this.cellCount = 0;
        for (final Element cellElem : this.getCellElements()) {
            addCellElem(cellElem, cellStyleDesc);
        }
    }

    private final void ensureRoom(int additionalItems) {
        final int requiredSize = this.getCellCount() + additionalItems;
        if (requiredSize > this.cells.length) {
            this.cells = Arrays.copyOf(this.cells, requiredSize);
        }
    }

    protected final Table<D> getSheet() {
        return this.parent;
    }

    // ATTN index of the first row
    final int getY() {
        return this.index;
    }

    // inclusive
    final int getLastY() {
        return this.getY() + this.getRepeated() - 1;
    }

    final int getRepeated() {
        return this.repeated;
    }

    final void setRepeated(int newRepeated) {
        Axis.ROW.setRepeated(getElement(), newRepeated);
        this.repeated = newRepeated;
    }

    // plain Cell instances have multiple indexes (if repeated) but MutableCell are unique
    final int getX(MutableCell<D> c) {
        final int stop = this.getCellCount();
        for (int i = 0; i < stop; i++) {
            final Cell<D> item = this.cells[i];
            if (c.equals(item))
                return i;
        }
        return -1;
    }

    private void addCellElem(final Element cellElem, StyleDesc<CellStyle> cellStyleDesc) {
        final Cell<D> cell = new Cell<D>(this, cellElem, cellStyleDesc);
        final String repeatedS = cellElem.getAttributeValue("number-columns-repeated", this.getTABLE());
        final int toRepeat = StyleProperties.parseInt(repeatedS, 1);
        final int stop = this.cellCount + toRepeat;
        for (int i = this.cellCount; i < stop; i++) {
            this.cells[i] = cell;
        }
        this.cellCount = stop;
    }

    /**
     * All cells of this row.
     * 
     * @return cells of this row, only "table-cell" and "covered-table-cell".
     */
    @SuppressWarnings("unchecked")
    private List<Element> getCellElements() {
        // seuls table-cell et covered-table-cell sont légaux
        return this.getElement().getChildren();
    }

    protected final int getCellCount() {
        return this.cellCount;
    }

    private final List<Cell<D>> getCellsAsList() {
        return Arrays.asList(this.cells);
    }

    protected final Cell<D> getCellAt(int col) {
        return this.cells[col];
    }

    protected final Cell<D> getValidCellAt(int col) {
        final Cell<D> c = this.getCellAt(col);
        if (!c.isValid())
            throw new IllegalArgumentException("invalid cell " + c);
        return c;
    }

    public final MutableCell<D> getMutableCellAt(final int col) {
        final Cell<D> c = this.getValidCellAt(col);
        if (!(c instanceof MutableCell)) {
            RepeatedBreaker.<D> getCellBreaker().breakRepeated(this, getCellsAsList(), col);
        }
        return (MutableCell<D>) this.getValidCellAt(col);
    }

    // rempli cette ligne avec autant de cellules vides qu'il faut
    void columnCountChanged(StyleStyleDesc<CellStyle> cellStyleDesc) {
        final int diff = this.getSheet().getColumnCount() - getCellCount();
        if (diff < 0) {
            throw new IllegalStateException("should have used Table.removeColumn()");
        } else if (diff > 0) {
            final Element e = Cell.createEmpty(this.getSheet().getODDocument().getVersion(), diff);
            this.getElement().addContent(e);
            this.ensureRoom(diff);
            addCellElem(e, cellStyleDesc);
        }
        assert this.getCellCount() == this.getSheet().getColumnCount();
    }

    void checkRemove(int firstIndex, int lastIndexExcl) {
        if (lastIndexExcl > getCellCount()) {
            throw new IndexOutOfBoundsException(lastIndexExcl + " > " + getCellCount());
        }
        if (!this.getCellAt(firstIndex).isValid())
            throw new IllegalArgumentException("unable to remove covered cell at " + firstIndex);
    }

    // ATTN unsafe, must call checkRemove() first
    void removeCells(int firstIndex, int lastIndexExcl) {
        this.getMutableCellAt(firstIndex).unmerge();

        // if lastIndex == size, nothing to do
        if (lastIndexExcl < getCellCount()) {
            if (!this.getCellAt(lastIndexExcl - 1).isValid()) {
                int currentCol = lastIndexExcl - 2;
                // the covering cell is on this row since last cells of previous rows have been
                // unmerged (see *)
                // we've just unmerged firstIndex so there must be a non covered cell before it
                while (!this.getCellAt(currentCol).isValid())
                    currentCol--;
                this.getMutableCellAt(currentCol).unmerge();
            }
            // * lastIndex-1 is now uncovered, we can now unmerge it to assure our following rows
            // that any covered cell they encounter is on them and not above
            // plus of course if the last cell removed is covering following columns
            this.getMutableCellAt(lastIndexExcl - 1).unmerge();
        }

        for (int i = firstIndex; i < lastIndexExcl; i++) {
            // ok to detach multiple times the same element (since repeated cells share the same XML
            // element)
            this.cells[i].getElement().detach();
        }
        final int movedCount = getCellCount() - lastIndexExcl;
        System.arraycopy(this.cells, lastIndexExcl, this.cells, firstIndex, movedCount);
        this.cells = Arrays.copyOfRange(this.cells, 0, firstIndex + movedCount);
        this.cellCount = this.cells.length;
        assert this.getCellCount() == this.getSheet().getColumnCount();
    }

}
