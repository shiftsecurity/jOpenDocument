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

import org.jopendocument.dom.Length;
import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.StyledNode;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle.StyleTableCellProperties;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;
import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.SimpleXMLPath;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.swing.table.TableModel;

import org.jdom.Attribute;
import org.jdom.Element;

/**
 * A single sheet in a spreadsheet.
 * 
 * @author Sylvain
 * @param <D> type of table parent
 */
public class Table<D extends ODDocument> extends TableCalcNode<TableStyle, D> {

    static Element createEmpty(XMLVersion ns) {
        return createEmpty(ns, 1, 1);
    }

    static Element createEmpty(final XMLVersion ns, final int colCount, final int rowCount) {
        return createEmpty(ns, colCount, rowCount, null);
    }

    // pass styleName to avoid creating possibly expensive Table instance (readCols/readRows) in
    // order to use setStyleName()
    static Element createEmpty(final XMLVersion ns, final int colCount, final int rowCount, final String styleName) {
        // from the relaxNG
        if (colCount < 1 || rowCount < 1)
            throw new IllegalArgumentException("a table must have at least one cell");
        final Element col = Column.createEmpty(ns, null);
        Axis.COLUMN.setRepeated(col, colCount);
        final Element row = Row.createEmpty(ns).addContent(Cell.createEmpty(ns, colCount));
        Axis.ROW.setRepeated(row, rowCount);
        final Element res = new Element("table", ns.getTABLE()).addContent(col).addContent(row);
        StyledNode.setStyleName(res, styleName);
        return res;
    }

    static final String getName(final Element elem) {
        return elem.getAttributeValue("name", elem.getNamespace("table"));
    }

    // ATTN Row have their index as attribute
    private final ArrayList<Row<D>> rows;
    private TableGroup rowGroup;
    private final ArrayList<Column<D>> cols;
    private TableGroup columnGroup;

    public Table(D parent, Element local) {
        super(parent, local, TableStyle.class);

        this.rows = new ArrayList<Row<D>>(64);
        this.cols = new ArrayList<Column<D>>(32);

        // read columns first since Row constructor needs it
        this.readColumns();
        this.readRows();
    }

    private void readColumns() {
        this.read(Axis.COLUMN);
    }

    private final void readRows() {
        this.read(Axis.ROW);
    }

    private final void read(final Axis axis) {
        final boolean col = axis == Axis.COLUMN;
        final Tuple2<TableGroup, List<Element>> r = TableGroup.createRoot(this, axis);
        final ArrayList<?> l = col ? this.cols : this.rows;
        final int oldSize = l.size();
        l.clear();
        final int newSize = r.get0().getSize();
        l.ensureCapacity(newSize);
        if (col) {
            final StyleStyleDesc<ColumnStyle> colStyleDesc = getColumnStyleDesc();
            for (final Element clone : r.get1())
                this.addCol(clone, colStyleDesc);
            this.columnGroup = r.get0();
        } else {
            final StyleStyleDesc<RowStyle> rowStyleDesc = getRowStyleDesc();
            final StyleStyleDesc<CellStyle> cellStyleDesc = getCellStyleDesc();
            for (final Element clone : r.get1())
                this.addRow(clone, rowStyleDesc, cellStyleDesc);
            this.rowGroup = r.get0();
        }
        // this always copy the array, so make sure we reclaim enough memory (~ 64k)
        if (oldSize - newSize > 8192) {
            l.trimToSize();
        }
        assert newSize == (col ? this.getColumnCount() : this.getRowCount());
    }

    private final void addCol(Element clone, StyleStyleDesc<ColumnStyle> colStyleDesc) {
        this.cols.add(new Column<D>(this, clone, colStyleDesc));
    }

    static final int flattenChildren(final List<Element> res, final Element elem, final Axis axis) {
        int count = 0;
        // array so that flatten1() can modify an int
        int[] index = new int[] { 0 };
        // copy since we will change our children (don't use List.listIterator(int) since it
        // re-filters all content)
        @SuppressWarnings("unchecked")
        final List<Element> children = new ArrayList<Element>(elem.getChildren(axis.getElemName(), elem.getNamespace()));
        final int stop = children.size();
        for (int i = 0; i < stop; i++) {
            final Element row = children.get(i);
            count += flatten1(res, row, axis, index);
        }
        return count;
    }

    static int flatten1(final List<Element> res, final Element row, final Axis axis) {
        return flatten1(res, row, axis, null);
    }

    // add XML elements to res and return the logical count
    private static int flatten1(final List<Element> res, final Element row, final Axis axis, final int[] parentIndex) {
        final int resSize = res.size();
        final Attribute repeatedAttr = axis.getRepeatedAttr(row);
        final int repeated = repeatedAttr == null ? 1 : Integer.parseInt(repeatedAttr.getValue());
        if (axis == Axis.COLUMN && repeated > 1) {
            row.removeAttribute(repeatedAttr);
            final Element parent = row.getParentElement();
            final int index = (parentIndex == null ? parent.indexOf(row) : parentIndex[0]) + 1;
            res.add(row);
            // -1 : we keep the original row
            for (int i = 0; i < repeated - 1; i++) {
                final Element clone = (Element) row.clone();
                res.add(clone);
                parent.addContent(index + i, clone);
            }
        } else {
            res.add(row);
        }
        if (parentIndex != null)
            parentIndex[0] += res.size() - resSize;
        return repeated;
    }

    public final String getName() {
        return getName(this.getElement());
    }

    public final void setName(String name) {
        this.getElement().setAttribute("name", name, this.getODDocument().getVersion().getTABLE());
    }

    public void detach() {
        this.getElement().detach();
    }

    public final Object getPrintRanges() {
        return this.getElement().getAttributeValue("print-ranges", this.getTABLE());
    }

    public final void setPrintRanges(String s) {
        this.getElement().setAttribute("print-ranges", s, this.getTABLE());
    }

    public final void removePrintRanges() {
        this.getElement().removeAttribute("print-ranges", this.getTABLE());
    }

    public final synchronized void duplicateFirstRows(int nbFirstRows, int nbDuplicate) {
        this.duplicateRows(0, nbFirstRows, nbDuplicate);
    }

    public final synchronized void insertDuplicatedRows(int rowDuplicated, int nbDuplicate) {
        this.duplicateRows(rowDuplicated, 1, nbDuplicate);
    }

    /**
     * Clone a range of rows. Eg if you want to copy once rows 2 through 5, you call
     * <code>duplicateRows(2, 4, 1)</code>.
     * 
     * @param start the first row to clone.
     * @param count the number of rows after <code>start</code> to clone, i.e. 1 means clone one
     *        row, must be greater or equal to 0.
     * @param copies the number of copies of the range to make, must be greater or equal to 0.
     * @throws IllegalArgumentException if arguments are not valid.
     */
    public final void duplicateRows(final int start, final int count, final int copies) throws IllegalArgumentException {
        this.duplicateRows(start, count, copies, true);
    }

    public final synchronized void duplicateRows(final int start, final int count, final int copies, final boolean updateCellAddresses) throws IllegalArgumentException {
        if (start < 0)
            throw new IllegalArgumentException("Negative start index : " + start);
        if (count < 0)
            throw new IllegalArgumentException("Negative count : " + count);
        else if (count == 0)
            return;
        if (copies < 0)
            throw new IllegalArgumentException("Negative copies : " + copies);
        else if (copies == 0)
            return;
        // stop is excluded
        final int stop = start + count;
        if (stop > this.getRowCount())
            throw new IllegalArgumentException("Last row to duplicate (" + (stop - 1) + ") doesn't exist, there's only : " + getRowCount());

        // should not change merged status
        final Map<Point, Integer> coverOrigins = new HashMap<Point, Integer>();
        final List<Point> coverOriginsToUpdate = new ArrayList<Point>();
        final int colCount = this.getColumnCount();
        for (int x = 0; x < colCount;) {
            int y = start;
            while (y < stop) {
                final Point coverOrigin = this.getCoverOrigin(x, y);
                if (coverOrigin == null) {
                    y++;
                } else {
                    final int lastCoveredCellRow;
                    // if we have already encountered this merged cell, skip it
                    if (coverOrigins.containsKey(coverOrigin)) {
                        lastCoveredCellRow = coverOrigins.get(coverOrigin);
                    } else {
                        final Cell<D> covering = this.getImmutableCellAt(coverOrigin.x, coverOrigin.y);
                        lastCoveredCellRow = coverOrigin.y + covering.getRowsSpanned() - 1;
                        if (coverOrigin.y < start) {
                            if (lastCoveredCellRow < stop - 1)
                                throw new IllegalArgumentException("Span starts before the duplicated rows and doesn't extend past the end of duplicated rows at " + getAddress(coverOrigin));
                        } else {
                            if (lastCoveredCellRow > stop - 1)
                                throw new IllegalArgumentException("Span starts in the duplicated rows and extend past the end of duplicated rows at " + getAddress(coverOrigin));
                        }

                        coverOrigins.put(coverOrigin, lastCoveredCellRow);
                        // merged cells inside the duplicated rows don't need to be updated
                        if (coverOrigin.y < start || lastCoveredCellRow > stop - 1)
                            coverOriginsToUpdate.add(coverOrigin);
                    }
                    y = lastCoveredCellRow + 1;
                }
            }
            x++;
        }

        // clone xml elements and add them to our tree
        final List<Element> clones = new ArrayList<Element>(count * copies);
        for (int l = start; l < stop;) {
            final Row<D> immutableRow = this.getRow(l);
            final Row<D> toClone;
            // MAYBE use something else than getMutableRow() since we don't need a single row.
            // the repeated row starts before the copied range, split it at the beginning
            if (immutableRow.getY() < l) {
                toClone = this.getMutableRow(l);
            } else {
                assert immutableRow.getY() == l;
                if (immutableRow.getLastY() >= stop) {
                    // the repeated row goes beyond the copied range, split it at the end
                    assert this.getRow(stop) == immutableRow;
                    this.getMutableRow(stop);
                    toClone = this.getRow(l);
                } else {
                    toClone = immutableRow;
                }
            }
            assert toClone.getY() == l;
            assert toClone.getLastY() < stop : "Row goes to far";
            l += toClone.getRepeated();
            clones.add((Element) toClone.getElement().clone());
        }
        final int clonesSize = clones.size();
        for (int i = 1; i < copies; i++) {
            for (int j = 0; j < clonesSize; j++) {
                clones.add((Element) clones.get(j).clone());
            }
        }
        // works anywhere its XML element is
        assert this.getRow(stop - 1).getLastY() == stop - 1 : "Adding XML element too far";
        JDOMUtils.insertAfter(this.getRow(stop - 1).getElement(), clones);

        for (final Point coverOrigin : coverOriginsToUpdate) {
            final MutableCell<D> coveringCell = getCellAt(coverOrigin);
            coveringCell.setRowsSpanned(coveringCell.getRowsSpanned() + count * copies);
        }

        // synchronize our rows with our new tree (rows' index have changed)
        this.readRows();

        // 19.627 in OpenDocument-v1.2-cs01-part1 : The table:end-cell-address attribute specifies
        // end position of the shape if it is included in a spreadsheet document.
        if (updateCellAddresses && getODDocument() instanceof SpreadSheet) {
            final SpreadSheet ssheet = (SpreadSheet) getODDocument();
            final SimpleXMLPath<Attribute> descAttrs = SimpleXMLPath.allAttributes("end-cell-address", "table");
            for (final Attribute endCellAttr : descAttrs.selectNodes(getElement())) {
                final Tuple2<Sheet, Point> resolved = ssheet.resolve(endCellAttr.getValue());
                final Sheet endCellSheet = resolved.get0();
                if (endCellSheet != this)
                    throw new UnsupportedOperationException("End sheet is not this : " + endCellSheet);
                final Point endCellPoint = resolved.get1();
                // if the end point is before the copied rows, nothing to do
                if (endCellPoint.y >= start) {
                    final Element endCellParentElem = endCellAttr.getParent();

                    // find row index of the shape
                    final Element rowElem = JDOMUtils.getAncestor(endCellParentElem, "table-row", getTABLE());
                    if (rowElem == null)
                        throw new IllegalStateException("Not in a row : " + JDOMUtils.output(endCellParentElem));
                    int startRowIndex = -1;
                    final int rowCount = getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        if (getRow(i).getElement() == rowElem) {
                            startRowIndex = i;
                            break;
                        }
                    }
                    if (startRowIndex < 0)
                        throw new IllegalStateException("Row not found for " + JDOMUtils.output(endCellParentElem));
                    final int newEndY;
                    if (startRowIndex >= start + (copies + 1) * count) {
                        // if the shape doesn't span over the copied rows, only need to offset
                        // end-cell-address
                        newEndY = endCellPoint.y + copies * count;
                    } else if (startRowIndex >= start + count && endCellPoint.y < start + count) {
                        // if the shape was copied and its end cell too, translate it
                        // find in which copy the shape is in, ATTN the truncation is important
                        // since the shape might not be in the first copied row
                        final int nth = (startRowIndex - start) / count;
                        newEndY = endCellPoint.y + nth * count;
                    } else {
                        // we must use height to compute new values for end-cell-address and end-y

                        // find the height of the shape
                        final LengthUnit unit = LengthUnit.MM;
                        final BigDecimal[] coordinates = getODDocument().getFormatVersion().getXML().getCoordinates(endCellParentElem, unit, false, true);
                        if (coordinates == null)
                            throw new IllegalStateException("Couldn't find the height of the shape : " + JDOMUtils.output(endCellParentElem));
                        final BigDecimal endYFromAnchor = coordinates[3];
                        assert endYFromAnchor != null : "getCoordinates() should never return null BigDecimal (unless requested by horizontal/vertical)";
                        // find the end row
                        int rowIndex = startRowIndex;
                        BigDecimal cellEndYFromAnchor = getRow(rowIndex).getStyle().getTableRowProperties().getHeight(unit);
                        while (endYFromAnchor.compareTo(cellEndYFromAnchor) > 0) {
                            rowIndex++;
                            cellEndYFromAnchor = cellEndYFromAnchor.add(getRow(rowIndex).getStyle().getTableRowProperties().getHeight(unit));
                        }
                        // find the end-y
                        final BigDecimal cellStartYFromAnchor = cellEndYFromAnchor.subtract(getRow(rowIndex).getStyle().getTableRowProperties().getHeight(unit));
                        final BigDecimal endY = endYFromAnchor.subtract(cellStartYFromAnchor);
                        assert endY.signum() >= 0;

                        newEndY = rowIndex;
                        endCellParentElem.setAttribute("end-y", unit.format(endY), getTABLE());
                    }
                    endCellAttr.setValue(SpreadSheet.formatSheetName(endCellSheet.getName()) + "." + Table.getAddress(new Point(endCellPoint.x, newEndY)));
                }
            }
        }
    }

    private synchronized void addRow(Element child, StyleDesc<RowStyle> styleDesc, StyleDesc<CellStyle> cellStyleDesc) {
        final Row<D> row = new Row<D>(this, child, this.rows.size(), styleDesc, cellStyleDesc);
        final int toRepeat = row.getRepeated();
        for (int i = 0; i < toRepeat; i++) {
            this.rows.add(row);
        }
    }

    public final Point resolveHint(String ref) {
        final Point res = resolve(ref);
        if (res != null) {
            return res;
        } else
            throw new IllegalArgumentException(ref + " is not a cell ref, if it's a named range, you must use it on a SpreadSheet.");
    }

    // *** set cell

    public final boolean isCellValid(int x, int y) {
        if (x > this.getColumnCount())
            return false;
        else if (y > this.getRowCount())
            return false;
        else
            return this.getImmutableCellAt(x, y).isValid();
    }

    /**
     * Return a modifiable cell at the passed coordinates. This is slower than
     * {@link #getImmutableCellAt(int, int)} since this method may modify the underlying XML (e.g.
     * break up repeated cells to allow for modification of only the returned cell).
     * 
     * @param x the column.
     * @param y the row.
     * @return the cell.
     * @see #getImmutableCellAt(int, int)
     */
    public final MutableCell<D> getCellAt(int x, int y) {
        return this.getMutableRow(y).getMutableCellAt(x);
    }

    public final MutableCell<D> getCellAt(String ref) {
        return this.getCellAt(resolveHint(ref));
    }

    final MutableCell<D> getCellAt(Point p) {
        return this.getCellAt(p.x, p.y);
    }

    /**
     * Sets the value at the specified coordinates.
     * 
     * @param val the new value, <code>null</code> will be treated as "".
     * @param x the column.
     * @param y the row.
     */
    public final void setValueAt(Object val, int x, int y) {
        if (val == null)
            val = "";
        // ne pas casser les repeated pour rien
        if (!val.equals(this.getValueAt(x, y)))
            this.getCellAt(x, y).setValue(val);
    }

    // *** get cell

    /**
     * Return a non modifiable cell at the passed coordinates. This is faster than
     * {@link #getCellAt(int, int)} since this method never modifies the underlying XML.
     * 
     * @param x the column.
     * @param y the row.
     * @return the cell.
     * @see #getCellAt(int, int)
     */
    public final Cell<D> getImmutableCellAt(int x, int y) {
        return this.getRow(y).getCellAt(x);
    }

    public final Cell<D> getImmutableCellAt(String ref) {
        final Point p = resolveHint(ref);
        return this.getImmutableCellAt(p.x, p.y);
    }

    /**
     * Return the origin of a merged cell.
     * 
     * @param x the column.
     * @param y the row.
     * @return the point of origin, <code>null</code> if there's no merged cell at the passed
     *         coordinates.
     */
    public final Point getCoverOrigin(final int x, final int y) {
        // can't return a Cell, since it has no x
        // don't return a MutableCell since it is costly

        final Cell<D> c = this.getImmutableCellAt(x, y);
        if (c.coversOtherCells()) {
            return new Point(x, y);
        } else if (!c.isCovered()) {
            return null;
        } else {
            final Row<D> row = this.getRow(y);
            Cell<D> currentCell = c;
            int currentX = x;
            while (currentX > 0 && currentCell.isCovered()) {
                currentX--;
                currentCell = row.getCellAt(currentX);
            }
            if (currentCell.coversOtherCells())
                return new Point(currentX, y);

            if (!currentCell.isCovered()) {
                currentX++;
                currentCell = row.getCellAt(currentX);
            }
            assert currentCell.isCovered();

            int currentY = y;
            while (!currentCell.coversOtherCells()) {
                currentY--;
                currentCell = this.getImmutableCellAt(currentX, currentY);
            }
            return new Point(currentX, currentY);
        }
    }

    /**
     * The value of the cell at the passed coordinates.
     * 
     * @param row row index from <code>0</code> to <code>{@link #getRowCount() row count}-1</code>
     * @param column column index from <code>0</code> to
     *        <code>{@link #getColumnCount() column count}-1</code>
     * @return the value at the passed coordinates.
     * @see #getValueAt(String)
     * @see Cell#getValue()
     */
    public final Object getValueAt(int column, int row) {
        return this.getImmutableCellAt(column, row).getValue();
    }

    /**
     * Find the style name for the specified cell.
     * 
     * @param column column index.
     * @param row row index.
     * @return the style name, can be <code>null</code>.
     */
    public final String getStyleNameAt(int column, int row) {
        // first the cell
        String cellStyle = this.getImmutableCellAt(column, row).getStyleAttr();
        if (cellStyle != null)
            return cellStyle;
        // then the row (as specified in §2 of section 8.1)
        // in reality LO never generates table-row/@default-cell-style-name and its behavior is
        // really unpredictable if it opens files with it
        if (Style.isStandardStyleResolution()) {
            cellStyle = this.getRow(row).getElement().getAttributeValue("default-cell-style-name", getTABLE());
            if (cellStyle != null)
                return cellStyle;
        }
        // and finally the column
        return this.getColumn(column).getElement().getAttributeValue("default-cell-style-name", getTABLE());
    }

    public final CellStyle getStyleAt(int column, int row) {
        return getCellStyleDesc().findStyleForNode(this.getImmutableCellAt(column, row), this.getStyleNameAt(column, row));
    }

    public final StyleTableCellProperties getTableCellPropertiesAt(int column, int row) {
        final CellStyle styleAt = this.getStyleAt(column, row);
        return styleAt == null ? null : styleAt.getTableCellProperties(this.getImmutableCellAt(column, row));
    }

    protected StyleStyleDesc<CellStyle> getCellStyleDesc() {
        return Style.getStyleStyleDesc(CellStyle.class, getODDocument().getVersion());
    }

    public final CellStyle getDefaultCellStyle() {
        return getCellStyleDesc().findDefaultStyle(this.getODDocument().getPackage());
    }

    /**
     * Return the coordinates of cells using the passed style.
     * 
     * @param cellStyleName a style name.
     * @return the cells using <code>cellStyleName</code>.
     */
    public final List<Tuple2<Integer, Integer>> getStyleReferences(final String cellStyleName) {
        final List<Tuple2<Integer, Integer>> res = new ArrayList<Tuple2<Integer, Integer>>();
        final Set<Integer> cols = new HashSet<Integer>();
        final int columnCount = getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            if (cellStyleName.equals(this.getColumn(i).getElement().getAttributeValue("default-cell-style-name", getTABLE())))
                cols.add(i);
        }
        final int rowCount = getRowCount();
        for (int y = 0; y < rowCount; y++) {
            final Row<D> row = this.getRow(y);
            final String rowStyle = row.getElement().getAttributeValue("default-cell-style-name", getTABLE());
            for (int x = 0; x < columnCount; x++) {
                final String cellStyle = row.getCellAt(x).getStyleAttr();
                final boolean match;
                // first the cell
                if (cellStyle != null)
                    match = cellStyleName.equals(cellStyle);
                // then the row (as specified in §2 of section 8.1)
                else if (rowStyle != null)
                    match = cellStyleName.equals(rowStyle);
                // and finally the column
                else
                    match = cols.contains(x);

                if (match)
                    res.add(Tuple2.create(x, y));
            }
        }
        return res;
    }

    protected final StyleStyleDesc<ColumnStyle> getColumnStyleDesc() {
        return Style.getStyleStyleDesc(ColumnStyle.class, XMLVersion.getVersion(getElement()));
    }

    protected final StyleStyleDesc<RowStyle> getRowStyleDesc() {
        return Style.getStyleStyleDesc(RowStyle.class, XMLVersion.getVersion(getElement()));
    }

    /**
     * The value of the cell at the passed coordinates.
     * 
     * @param ref a reference, e.g. "A3".
     * @return the value at the passed coordinates.
     * @see #getValueAt(int, int)
     * @see Cell#getValue()
     */
    public final Object getValueAt(String ref) {
        return this.getImmutableCellAt(ref).getValue();
    }

    // *** get count

    final Row<D> getRow(int index) {
        return this.rows.get(index);
    }

    final Row<D> getMutableRow(int y) {
        final Row<D> c = this.getRow(y);
        if (c.getRepeated() > 1) {
            RepeatedBreaker.<D> getRowBreaker().breakRepeated(this, this.rows, y);
            return this.getRow(y);
        } else {
            return c;
        }
    }

    public final Column<D> getColumn(int i) {
        return this.cols.get(i);
    }

    public final int getRowCount() {
        return this.rows.size();
    }

    public final TableGroup getRowGroup() {
        return this.rowGroup;
    }

    /**
     * Return the deepest group at the passed row.
     * 
     * @param y a row index.
     * @return the group at the index, never <code>null</code>.
     */
    public final TableGroup getRowGroupAt(final int y) {
        return this.getRowGroup().getDescendentOrSelfContaining(y);
    }

    public final int getHeaderRowCount() {
        return this.getRowGroup().getFollowingHeaderCount();
    }

    public final int getColumnCount() {
        return this.cols.size();
    }

    public final TableGroup getColumnGroup() {
        return this.columnGroup;
    }

    /**
     * Return the deepest group at the passed column.
     * 
     * @param x a column index.
     * @return the group at the index, never <code>null</code>.
     */
    public final TableGroup getColumnGroupAt(final int x) {
        return this.getColumnGroup().getDescendentOrSelfContaining(x);
    }

    public final int getHeaderColumnCount() {
        return this.getColumnGroup().getFollowingHeaderCount();
    }

    // *** set count

    /**
     * Changes the column count without keeping the table width.
     * 
     * @param newSize the new column count.
     * @see #setColumnCount(int, int, boolean)
     */
    public final void setColumnCount(int newSize) {
        this.setColumnCount(newSize, -1, false);
    }

    /**
     * Assure that this sheet has at least <code>newSize</code> columns.
     * 
     * @param newSize the minimum column count this table should have.
     */
    public final void ensureColumnCount(int newSize) {
        if (newSize > this.getColumnCount())
            this.setColumnCount(newSize);
    }

    /**
     * Changes the column count. If <code>newSize</code> is less than {@link #getColumnCount()}
     * extra cells will be chopped off. Otherwise empty cells will be created.
     * 
     * @param newSize the new column count.
     * @param colIndex the index of the column to be copied, -1 for empty column (and no style).
     * @param keepTableWidth <code>true</code> if the table should be same width after the column
     *        change.
     */
    public final void setColumnCount(int newSize, int colIndex, final boolean keepTableWidth) {
        this.setColumnCount(newSize, colIndex, null, keepTableWidth);
    }

    /**
     * Changes the column count. If <code>newSize</code> is less than {@link #getColumnCount()}
     * extra cells will be chopped off. Otherwise empty cells will be created.
     * 
     * @param newSize the new column count.
     * @param colStyle the style of the new columns, <code>null</code> for no style (throws
     *        exception if the table has a non-null width).
     * @param keepTableWidth <code>true</code> if the table should be same width after the column
     *        change.
     * @see #createColumnStyle(Number, LengthUnit)
     */
    public final void setColumnCount(int newSize, ColumnStyle colStyle, final boolean keepTableWidth) {
        this.setColumnCount(newSize, -1, colStyle, keepTableWidth);
    }

    private final void setColumnCount(int newSize, int colIndex, ColumnStyle colStyle, final boolean keepTableWidth) {
        final int toGrow = newSize - this.getColumnCount();
        if (toGrow < 0) {
            this.removeColumn(newSize, this.getColumnCount(), keepTableWidth);
        } else if (toGrow > 0) {
            // the list of columns cannot be mixed with other elements
            // so just keep adding after the last one
            final int indexOfLastCol;
            if (this.getColumnCount() == 0)
                // from section 8.1.1 the only possible elements after cols are rows
                // but there can't be rows w/o columns, so just add to the end
                indexOfLastCol = this.getElement().getContentSize() - 1;
            else
                indexOfLastCol = this.getElement().getContent().indexOf(this.getColumn(this.getColumnCount() - 1).getElement());

            final Element elemToClone;
            if (colIndex < 0) {
                elemToClone = Column.createEmpty(getODDocument().getVersion(), colStyle);
            } else {
                elemToClone = getColumn(colIndex).getElement();
            }
            final StyleStyleDesc<ColumnStyle> columnStyleDesc = getColumnStyleDesc();
            for (int i = 0; i < toGrow; i++) {
                final Element newElem = (Element) elemToClone.clone();
                this.getElement().addContent(indexOfLastCol + 1 + i, newElem);
                this.cols.add(new Column<D>(this, newElem, columnStyleDesc));
            }
            // now update widths
            updateWidth(keepTableWidth);

            // add needed cells
            final StyleStyleDesc<CellStyle> cellStyleDesc = this.getCellStyleDesc();
            final int rowCount = this.getRowCount();
            for (int i = 0; i < rowCount;) {
                final Row<D> r = this.getRow(i);
                r.columnCountChanged(cellStyleDesc);
                i += r.getRepeated();
            }
        }
    }

    public final void removeColumn(int colIndex, final boolean keepTableWidth) {
        this.removeColumn(colIndex, colIndex + 1, keepTableWidth);
    }

    /**
     * Remove columns from this. As with OpenOffice, no cell must be covered in the column to
     * remove. ATTN <code>keepTableWidth</code> only works for tables in text document that are not
     * aligned automatically (ie fill the entire page). ATTN spreadsheet applications may hide from
     * you the real width of sheets, eg display only columns A to AJ when in reality there's
     * hundreds of blank columns beyond. Thus if you pass <code>true</code> to
     * <code>keepTableWidth</code> you'll end up with huge widths.
     * 
     * @param firstIndex the first column to remove.
     * @param lastIndex the last column to remove, exclusive.
     * @param keepTableWidth <code>true</code> if the table should be same width after the column
     *        change.
     */
    public final void removeColumn(int firstIndex, int lastIndex, final boolean keepTableWidth) {
        // first check that removeCells() will succeed, so that we avoid an incoherent XML state
        final int rowCount = this.getRowCount();
        for (int i = 0; i < rowCount;) {
            final Row<D> r = this.getRow(i);
            r.checkRemove(firstIndex, lastIndex);
            i += r.getRepeated();
        }
        // rm column element
        remove(Axis.COLUMN, firstIndex, lastIndex - 1);
        // update widths
        updateWidth(keepTableWidth);
        // rm cells
        for (int i = 0; i < rowCount;) {
            final Row<D> r = this.getRow(i);
            r.removeCells(firstIndex, lastIndex);
            i += r.getRepeated();
        }
    }

    void updateWidth(final boolean keepTableWidth) {
        // tables widths must have a lower precision than columns since they can't be the exact sum
        // of them.
        final Length currentWidth = getWidth().roundDecimalAmount();
        Length newWidth = Length.ZERO;
        Column<?> nullWidthCol = null;
        // columns are flattened in ctor: no repeated
        for (final Column<?> col : this.cols) {
            final Length colWidth = col.getWidth();
            if (colWidth.isDefined()) {
                assert colWidth.signum() >= 0;
                newWidth = newWidth.add(colWidth);
            } else {
                // we cannot compute the newWidth
                newWidth = Length.getNone();
                nullWidthCol = col;
                break;
            }
        }
        // tables widths must have a lower precision than columns since they can't be the exact sum
        // of them.
        newWidth = newWidth.roundDecimalAmount();
        // remove all rel-column-width, simpler and Spreadsheet doesn't use them
        // SpreadSheets have no table width
        if (keepTableWidth && currentWidth.isDefined()) {
            if (nullWidthCol != null)
                throw new IllegalStateException("Cannot keep width since a column has no width : " + nullWidthCol);
            // compute column-width from table width
            final Number ratio = currentWidth.divide(newWidth);
            // once per style not once per col, otherwise if multiple columns with same styles they
            // all will be affected multiple times
            final Set<ColumnStyle> colStyles = new HashSet<ColumnStyle>();
            for (final Column<?> col : this.cols) {
                colStyles.add(col.getStyle());
            }
            for (final ColumnStyle colStyle : colStyles) {
                colStyle.setWidth(colStyle.getWidth().multiply(ratio));
            }
        } else {
            // compute table width from column-width
            final TableStyle style = this.getStyle();
            if (style != null) {
                if (nullWidthCol == null)
                    style.setWidth(newWidth);
                else if (currentWidth.isDefined())
                    throw new IllegalStateException("Cannot update table width since a column has no width : " + nullWidthCol);
                // else currentWidth undefined, no inconsistency, nothing to update
            }
            // either there's no table width or it's positive and equal to the sum of its columns
            assert style == null || style.getWidth().isNone() || (!newWidth.isNone() && newWidth.compareTo(style.getWidth()) == 0);
            for (final Column<?> col : this.cols) {
                final ColumnStyle colStyle = col.getStyle();
                // if no style, nothing to remove
                if (colStyle != null)
                    colStyle.rmRelWidth();
            }
        }
    }

    /**
     * Table width.
     * 
     * @return the table width, can be <code>null</code> (table has no style or style has no width,
     *         eg in SpreadSheet).
     */
    public final Length getWidth() {
        return this.getStyle().getWidth();
    }

    /**
     * Create and add an automatic style.
     * 
     * @param amount the column width.
     * @param unit the unit of <code>amount</code>.
     * @return the newly added style.
     */
    public final ColumnStyle createColumnStyle(final Number amount, final LengthUnit unit) {
        return createColumnStyle(amount == null ? Length.getNone() : Length.create(amount, unit));
    }

    public final ColumnStyle createColumnStyle(final Length l) {
        final ColumnStyle colStyle = this.getStyleDesc(ColumnStyle.class).createAutoStyle(this.getODDocument().getPackage());
        colStyle.setWidth(l);
        return colStyle;
    }

    private final void setCount(final Axis col, final int newSize) {
        this.remove(col, newSize, -1);
    }

    private final void remove(final Axis col, final int fromIndex, final int toIndexIncl) {
        this.remove(col, fromIndex, toIndexIncl, true);
    }

    // both inclusive
    private final void remove(final Axis col, final int fromIndex, final int toIndexIncl, final boolean updateList) {
        assert col == Axis.COLUMN || !updateList || toIndexIncl < 0 : "Row index will be wrong";
        final List<? extends TableCalcNode<?, ?>> l = col == Axis.COLUMN ? this.cols : this.rows;
        final int toIndexValid = CollectionUtils.getValidIndex(l, toIndexIncl);
        final int toRemoveCount = toIndexValid - fromIndex + 1;
        int removedCount = 0;
        while (removedCount < toRemoveCount) {
            // works backwards to keep y OK
            final int i = toIndexValid - removedCount;
            final TableCalcNode<?, ?> removed = l.get(i);
            if (removed instanceof Row) {
                final Row<?> r = (Row<?>) removed;
                final int removeFromRepeated = i - Math.max(fromIndex, r.getY()) + 1;
                // removedCount grows each iteration
                assert removeFromRepeated > 0;
                final int newRepeated = r.getRepeated() - removeFromRepeated;
                if (newRepeated == 0)
                    removed.getElement().detach();
                else
                    r.setRepeated(newRepeated);
                removedCount += removeFromRepeated;
            } else {
                // Columns are always flattened
                removed.getElement().detach();
                removedCount++;
            }
        }
        // one remove to be efficient
        if (updateList)
            l.subList(fromIndex, toIndexValid + 1).clear();
    }

    public final void ensureRowCount(int newSize) {
        if (newSize > this.getRowCount())
            this.setRowCount(newSize);
    }

    public final void setRowCount(int newSize) {
        this.setRowCount(newSize, -1);
    }

    /**
     * Changes the row count. If <code>newSize</code> is less than {@link #getRowCount()} extra rows
     * will be chopped off. Otherwise empty cells will be created.
     * 
     * @param newSize the new row count.
     * @param rowIndex the index of the row to be copied, -1 for empty row (i.e. default style).
     */
    public final void setRowCount(int newSize, int rowIndex) {
        final int toGrow = newSize - this.getRowCount();
        if (toGrow < 0) {
            setCount(Axis.ROW, newSize);
        } else if (toGrow > 0) {
            final Element elemToClone;
            if (rowIndex < 0) {
                elemToClone = Row.createEmpty(this.getODDocument().getVersion());
                // each row MUST have the same number of columns
                elemToClone.addContent(Cell.createEmpty(this.getODDocument().getVersion(), this.getColumnCount()));
            } else {
                elemToClone = (Element) getRow(rowIndex).getElement().clone();
            }
            Axis.ROW.setRepeated(elemToClone, toGrow);
            // as per section 8.1.1 rows are the last elements inside a table
            this.getElement().addContent(elemToClone);
            addRow(elemToClone, getRowStyleDesc(), getCellStyleDesc());
        }
    }

    public final void removeRow(int index) {
        this.removeRows(index, index + 1);
    }

    /**
     * Remove rows between the two passed indexes. If the origin of a merged cell is contained in
     * the rows to remove, then it will be unmerged, otherwise (the origin is before the rows) the
     * merged cell will just be shrunk.
     * 
     * @param firstIndex the start index, inclusive.
     * @param lastIndex the stop index, exclusive.
     */
    public final void removeRows(int firstIndex, int lastIndex) {
        if (firstIndex < 0)
            throw new IllegalArgumentException("Negative index " + firstIndex);
        if (firstIndex == lastIndex)
            return;
        if (firstIndex > lastIndex)
            throw new IllegalArgumentException("First index after last index " + firstIndex + " > " + lastIndex);

        // remove row elements, minding merged cells
        final int columnCount = getColumnCount();
        final Set<Point> coverOrigins = new HashSet<Point>();
        for (int y = firstIndex; y < lastIndex; y++) {
            for (int x = 0; x < columnCount; x++) {
                final Cell<D> cell = this.getImmutableCellAt(x, y);
                if (cell.isCovered()) {
                    coverOrigins.add(this.getCoverOrigin(x, y));
                    // cells spanning inside the removed rows don't need to be unmerged
                } else if (cell.coversOtherCells() && y + cell.getRowsSpanned() > lastIndex) {
                    this.getCellAt(x, y).unmerge();
                }
            }
        }

        // don't update this.rows as we need them below and indexes would be wrong anyway
        this.remove(Axis.ROW, firstIndex, lastIndex - 1, false);

        // adjust rows spanned
        // we haven't yet reset this.rows, so we can use getImmutableCellAt()
        for (final Point coverOrigin : coverOrigins) {
            // if the origin is inside the removed rows, then the whole merged cell was removed
            // (if the merged cell extended past lastIndex, it got unmerged above and wasn't added
            // to the set)
            if (coverOrigin.y >= firstIndex) {
                assert this.getImmutableCellAt(coverOrigin.x, coverOrigin.y).getElement().getDocument() == null;
                continue;
            }
            final Cell<D> coverOriginCell = this.getImmutableCellAt(coverOrigin.x, coverOrigin.y);
            final int rowsSpanned = coverOriginCell.getRowsSpanned();
            // otherwise the cover origin is between firstIndex and lastIndex and should have been
            // unmerged
            assert rowsSpanned > 1;
            final int stopY = coverOrigin.y + rowsSpanned;
            final int toRemove = Math.min(lastIndex, stopY) - firstIndex;
            assert toRemove > 0 && toRemove < rowsSpanned;
            coverOriginCell.getElement().setAttribute("number-rows-spanned", (rowsSpanned - toRemove) + "", getTABLE());
        }

        // remove empty table-row-group
        // since indexes are stored in TableGroup, we can do it after having removed rows
        final Set<TableGroup> groups = new HashSet<TableGroup>();
        for (int y = firstIndex; y < lastIndex; y++) {
            groups.add(this.getRowGroupAt(y));
        }
        for (final TableGroup group : groups) {
            TableGroup g = group;
            Element elem = g.getElement();
            while (g != null && elem.getParent() != null && elem.getChildren().size() == 0) {
                elem.detach();
                g = g.getParent();
                elem = g == null ? null : g.getElement();
            }
        }

        // recreate list from XML
        this.readRows();
    }

    // *** table models

    public final SheetTableModel<D> getTableModel(final int column, final int row) {
        return new SheetTableModel<D>(this, row, column);
    }

    public final SheetTableModel<D> getTableModel(final int column, final int row, final int lastCol, final int lastRow) {
        return new SheetTableModel<D>(this, row, column, lastRow, lastCol);
    }

    public final MutableTableModel<D> getMutableTableModel(final int column, final int row) {
        return new MutableTableModel<D>(this, row, column);
    }

    /**
     * Return the table from <code>start</code> to <code>end</code> inclusive.
     * 
     * @param start the first cell of the result.
     * @param end the last cell of the result.
     * @return the table.
     */
    public final MutableTableModel<D> getMutableTableModel(final Point start, final Point end) {
        // +1 since exclusive
        return new MutableTableModel<D>(this, start.y, start.x, end.y + 1, end.x + 1);
    }

    public final void merge(TableModel t, final int column, final int row) {
        this.merge(t, column, row, false);
    }

    /**
     * Merges t into this sheet at the specified point.
     * 
     * @param t the data to be merged.
     * @param column the column t will be merged at.
     * @param row the row t will be merged at.
     * @param includeColNames if <code>true</code> the column names of t will also be merged.
     */
    public final void merge(TableModel t, final int column, final int row, final boolean includeColNames) {
        final int offset = (includeColNames ? 1 : 0);
        // the columns must be first, see section 8.1.1 of v1.1
        this.ensureColumnCount(column + t.getColumnCount());
        this.ensureRowCount(row + t.getRowCount() + offset);
        final TableModel thisModel = this.getMutableTableModel(column, row);
        if (includeColNames) {
            for (int x = 0; x < t.getColumnCount(); x++) {
                thisModel.setValueAt(t.getColumnName(x), 0, x);
            }
        }
        for (int y = 0; y < t.getRowCount(); y++) {
            for (int x = 0; x < t.getColumnCount(); x++) {
                final Object value = t.getValueAt(y, x);
                thisModel.setValueAt(value, y + offset, x);
            }
        }
    }

    /**
     * All ranges defined in this table.
     * 
     * @return the names.
     * @see SpreadSheet#getRangesNames()
     */
    public final Set<String> getRangesNames() {
        return SpreadSheet.getRangesNames(getElement(), getTABLE());
    }

    /**
     * Get a named range in this table.
     * 
     * @param name the name of the range.
     * @return a named range, or <code>null</code> if the passed name doesn't exist.
     * @see #getRangesNames()
     * @see SpreadSheet#getRange(String)
     */
    public final Range getRange(String name) {
        return SpreadSheet.getRange(getElement(), getTABLE(), name);
    }

    public final MutableTableModel<D> getTableModel(String name) {
        final Range points = getRange(name);
        if (points == null)
            return null;
        if (points.spanSheets())
            throw new IllegalStateException("different sheet names: " + points.getStartSheet() + " != " + points.getEndSheet());
        return this.getMutableTableModel(points.getStartPoint(), points.getEndPoint());
    }

    // * UsedRange & CurrentRegion

    /**
     * The range that covers all used cells.
     * 
     * @return the range that covers all used cells, <code>null</code> if the table is completely
     *         empty.
     */
    public final Range getUsedRange() {
        return this.getUsedRange(false);
    }

    /**
     * The range that covers all used cells.
     * 
     * @param checkStyle <code>true</code> to check the background and borders in addition to the
     *        content.
     * @return the range that covers all used cells, <code>null</code> if the table is completely
     *         blank.
     */
    public final Range getUsedRange(boolean checkStyle) {
        int minX = -1, minY = -1, maxX = -1, maxY = -1;
        final int colCount = this.getColumnCount();
        final int rowCount = this.getRowCount();
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                if (!this.isCellBlank(x, y, checkStyle)) {
                    if (minX < 0 || x < minX)
                        minX = x;
                    if (minY < 0 || y < minY)
                        minY = y;

                    if (maxX < 0 || x > maxX)
                        maxX = x;
                    if (maxY < 0 || y > maxY)
                        maxY = y;
                }
            }
        }
        return minX < 0 ? null : new Range(getName(), new Point(minX, minY), new Point(maxX, maxY));
    }

    protected final boolean isCellBlank(final int x, int y, boolean checkStyle) {
        if (!getImmutableCellAt(x, y).isEmpty())
            return false;

        if (checkStyle) {
            final StyleTableCellProperties tableCellProperties = this.getTableCellPropertiesAt(x, y);
            return tableCellProperties == null || (tableCellProperties.getBackgroundColor() == null && tableCellProperties.getBorders().isEmpty());
        } else {
            return true;
        }
    }

    private class RegionExplorer {

        private final boolean checkStyle;
        private final int rowCount, colCount;
        protected int minX, minY, maxX, maxY;

        public RegionExplorer(final int startX, final int startY, final boolean checkStyle) {
            this.rowCount = getRowCount();
            this.colCount = getColumnCount();
            this.minX = this.maxX = startX;
            this.minY = this.maxY = startY;
            this.checkStyle = checkStyle;
        }

        public boolean canXDecrement() {
            return this.minX > 0;
        }

        public boolean canYDecrement() {
            return this.minY > 0;
        }

        public boolean canXIncrement() {
            return this.maxX < this.colCount - 1;
        }

        public boolean canYIncrement() {
            return this.maxY < this.rowCount - 1;
        }

        private boolean checkRow(final boolean upper) {
            if (upper && this.canYDecrement() || !upper && this.canYIncrement()) {
                final int y = upper ? this.minY - 1 : this.maxY + 1;
                final int start = this.canXDecrement() ? this.minX - 1 : this.minX;
                final int stop = this.canXIncrement() ? this.maxX + 1 : this.maxX;
                for (int x = start; x <= stop; x++) {
                    if (!isCellBlank(x, y, this.checkStyle)) {
                        if (upper)
                            this.minY = y;
                        else
                            this.maxY = y;
                        if (x < this.minX)
                            this.minX = x;
                        if (x > this.maxX)
                            this.maxX = x;
                        return true;
                    }
                }
            }
            return false;
        }

        // sans corners (checked by checkRow())
        private boolean checkCol(final boolean left) {
            if (left && this.canXDecrement() || !left && this.canXIncrement()) {
                final int x = left ? this.minX - 1 : this.maxX + 1;
                for (int y = this.minY; y <= this.maxY; y++) {
                    if (!isCellBlank(x, y, this.checkStyle)) {
                        if (left)
                            this.minX = x;
                        else
                            this.maxX = x;
                        return true;
                    }
                }
            }
            return false;
        }

        private final boolean checkFrame() {
            return this.checkRow(true) || this.checkRow(false) || this.checkCol(true) || this.checkCol(false);
        }

        public final Range getCurrentRegion() {
            while (this.checkFrame())
                ;// bounded by table size
            return new Range(getName(), new Point(this.minX, this.minY), new Point(this.maxX, this.maxY));
        }
    }

    public final Range getCurrentRegion(String ref) {
        return this.getCurrentRegion(ref, false);
    }

    public final Range getCurrentRegion(String ref, boolean checkStyle) {
        final Point p = resolveHint(ref);
        return this.getCurrentRegion(p.x, p.y, checkStyle);
    }

    /**
     * The smallest range containing the passed cell completely surrounded by empty rows and
     * columns.
     * 
     * @param startX x coordinate.
     * @param startY y coordinate.
     * @return the smallest range containing the passed cell.
     * @see <a href="http://msdn.microsoft.com/library/aa214248(v=office.11).aspx">CurrentRegion
     *      Property</a>
     */
    public final Range getCurrentRegion(final int startX, final int startY) {
        return this.getCurrentRegion(startX, startY, false);
    }

    public final Range getCurrentRegion(final int startX, final int startY, final boolean checkStyle) {
        return new RegionExplorer(startX, startY, checkStyle).getCurrentRegion();
    }

    // *** static

    /**
     * Convert string coordinates into numeric ones.
     * 
     * @param ref the string address, eg "$AA$34" or "AA34".
     * @return the numeric coordinates or <code>null</code> if <code>ref</code> is not valid, eg
     *         {26, 33}.
     */
    static final Point resolve(String ref) {
        final Matcher matcher = SpreadSheet.minCellPattern.matcher(ref);
        if (!matcher.matches())
            return null;
        return resolve(matcher.group(1), matcher.group(2));
    }

    /**
     * Convert string coordinates into numeric ones. ATTN this method does no checks.
     * 
     * @param letters the column, eg "AA".
     * @param digits the row, eg "34".
     * @return the numeric coordinates, eg {26, 33}.
     */
    static final Point resolve(final String letters, final String digits) {
        return new Point(toInt(letters), Integer.parseInt(digits) - 1);
    }

    // "AA" => 26
    static final int toInt(String col) {
        if (col.length() < 1)
            throw new IllegalArgumentException("x cannot be empty");
        col = col.toUpperCase();

        int x = 0;
        for (int i = 0; i < col.length(); i++) {
            x = x * 26 + (col.charAt(i) - 'A' + 1);
        }

        // zero based
        return x - 1;
    }

    public static final String toStr(int col) {
        if (col < 0)
            throw new IllegalArgumentException("negative column : " + col);
        // one based (i.e. 0 is A)
        col++;

        final int radix = 26;
        final StringBuilder chars = new StringBuilder(4);
        while (col > 0) {
            chars.append((char) ('A' + ((col - 1) % radix)));
            col = (col - 1) / radix;
        }

        return chars.reverse().toString();
    }

    /**
     * Convert numeric coordinates into string ones.
     * 
     * @param p the numeric coordinates, e.g. {26, 33}.
     * @return the string address, e.g. "AA34".
     */
    static final String getAddress(Point p) {
        if (p.x < 0 || p.y < 0)
            throw new IllegalArgumentException("negative coordinates : " + p);
        return toStr(p.x) + (p.y + 1);
    }
}