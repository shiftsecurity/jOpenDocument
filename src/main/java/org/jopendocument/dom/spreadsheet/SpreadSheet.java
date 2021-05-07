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

import org.jopendocument.dom.ContentType;
import org.jopendocument.dom.ContentTypeVersioned;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.SimpleXMLPath;
import org.jopendocument.util.Step;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.TableModel;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

/**
 * A calc document.
 * 
 * @author Sylvain
 */
public class SpreadSheet extends ODDocument {

    public static SpreadSheet createFromFile(File f) throws IOException {
        return new ODPackage(f).getSpreadSheet();
    }

    /**
     * This method should be avoided, use {@link ODPackage#getSpreadSheet()}.
     * 
     * @param fd a package.
     * @return the spreadsheet.
     */
    public static SpreadSheet get(final ODPackage fd) {
        return fd.hasODDocument() ? fd.getSpreadSheet() : new SpreadSheet(fd);
    }

    public static SpreadSheet createEmpty(TableModel t) {
        return createEmpty(t, XMLFormatVersion.getDefault());
    }

    public static SpreadSheet createEmpty(TableModel t, XMLFormatVersion ns) {
        final SpreadSheet spreadSheet = create(ns, 1, 1, 1);
        spreadSheet.getFirstSheet().merge(t, 0, 0, true);
        return spreadSheet;
    }

    public static SpreadSheet create(final int sheetCount, final int colCount, final int rowCount) {
        return create(XMLFormatVersion.getDefault(), sheetCount, colCount, rowCount);
    }

    public static SpreadSheet create(final XMLFormatVersion ns, final int sheetCount, final int colCount, final int rowCount) {
        final ContentTypeVersioned ct = ContentType.SPREADSHEET.getVersioned(ns.getXMLVersion());
        final SpreadSheet spreadSheet = ct.createPackage(ns).getSpreadSheet();
        spreadSheet.addSheets(0, Collections.<String> nCopies(sheetCount, null), colCount, rowCount);
        return spreadSheet;
    }

    /**
     * Export the passed data to file.
     * 
     * @param t the data to export.
     * @param f where to export, if the extension is missing (or wrong) the correct one will be
     *        added, eg "dir/data".
     * @param ns the version of XML.
     * @return the saved file, eg "dir/data.ods".
     * @throws IOException if the file can't be saved.
     */
    public static File export(TableModel t, File f, XMLFormatVersion ns) throws IOException {
        return SpreadSheet.createEmpty(t, ns).saveAs(f);
    }

    static final Set<String> getRangesNames(final Element parentElement, final Namespace tableNS) {
        final List<Element> ranges = getRangePath(tableNS, null).selectNodes(parentElement);
        final Set<String> res = new HashSet<String>(ranges.size());
        for (final Element elem : ranges) {
            res.add(elem.getAttributeValue("name", tableNS));
        }
        return res;
    }

    static private final SimpleXMLPath<Element> getRangePath(final Namespace tableNS, final String name) {
        final IPredicate<Element> pred = name == null ? null : new IPredicate<Element>() {
            @Override
            public boolean evaluateChecked(Element input) {
                return input.getAttributeValue("name", tableNS).equals(name);
            }
        };
        return SimpleXMLPath.create(Step.createElementStep("named-expressions", tableNS.getPrefix()), Step.createElementStep("named-range", tableNS.getPrefix(), pred));
    }

    static final Range getRange(final Element parentElement, final Namespace tableNS, final String name) {
        final Element range = getRangePath(tableNS, name).selectSingleNode(parentElement);
        if (range == null)
            return null;

        return Range.parse(range.getAttributeValue("cell-range-address", tableNS));
    }

    private final Map<Element, Sheet> sheets;

    private SpreadSheet(final ODPackage orig) {
        super(orig);
        // map Sheet by XML elements so has not to depend on ordering or name
        this.sheets = new HashMap<Element, Sheet>();
    }

    // ** from 8.3.1 Referencing Table Cells (just double the backslash for . and escape the $)
    private static final String minCell = "\\$?([A-Z]+)\\$?([0-9]+)";
    // added parens to capture cell address
    // \1 is sheet name, \4 cell address
    static final Pattern cellPattern = Pattern.compile("(\\$?([^\\. ']+|'([^']|'')+'))?\\.(" + minCell + ")");
    static final Pattern minCellPattern = Pattern.compile(minCell);
    // added illegal characters to unquoted form from LibreOffice UI, especially ':' to avoid
    // mistakenly using it in the table name instead of as a separator
    private static String tableNamePattern = "\\$?([^\\Q. '[]*?:/\\\\E]+|'([^']|'')+')";
    // added parens to capture cell addresses
    // \1 is sheet name, \4 cell address, \6 second sheet name, \9 second cell address
    static final Pattern cellRangePattern = java.util.regex.Pattern.compile("(" + tableNamePattern + ")?\\.(\\$?[A-Z]+\\$?[0-9]+)(:(" + tableNamePattern + ")?\\.(\\$?[A-Z]+\\$?[0-9]+))?");

    // see 9.2.1 of OpenDocument-v1.2-cs01-part1
    static final Pattern tableNameQuoteQuotePattern = Pattern.compile("''", Pattern.LITERAL);
    static final Pattern tableNameQuotePattern = Pattern.compile("'", Pattern.LITERAL);
    static final Pattern tableNameQuoteNeededPattern = Pattern.compile("[ \t.']");

    static protected final String parseSheetName(final String n) {
        if (n == null)
            return null;

        String res = n.charAt(0) == '$' ? n.substring(1) : n;
        if (res.charAt(0) == '\'') {
            if (res.charAt(res.length() - 1) != '\'')
                throw new IllegalArgumentException("Missing closing quote");
            res = tableNameQuoteQuotePattern.matcher(res.substring(1, res.length() - 1)).replaceAll(tableNameQuotePattern.pattern());
        }
        return res;
    }

    static protected final String formatSheetName(final String n) {
        if (n == null)
            return null;
        if (tableNameQuoteNeededPattern.matcher(n).find()) {
            return "'" + tableNameQuotePattern.matcher(n).replaceAll(tableNameQuoteQuotePattern.pattern()) + "'";
        } else {
            return n;
        }
    }

    /**
     * All global ranges defined in this document.
     * 
     * @return the global names.
     * @see Table#getRangesNames()
     */
    public final Set<String> getRangesNames() {
        return getRangesNames(getBody(), getVersion().getTABLE());
    }

    /**
     * Get a global named range.
     * 
     * @param name the name of the range.
     * @return a named range, or <code>null</code> if the passed name doesn't exist.
     * @see #getRangesNames()
     * @see Table#getRange(String)
     */
    public final Range getRange(String name) {
        return getRange(getBody(), getVersion().getTABLE(), name);
    }

    /**
     * Return a view of the passed range.
     * 
     * @param name a global named range.
     * @return the matching TableModel, <code>null</code> if it doesn't exist.
     * @see #getRange(String)
     * @see Table#getMutableTableModel(Point, Point)
     */
    public final MutableTableModel<SpreadSheet> getTableModel(String name) {
        final Range points = getRange(name);
        if (points == null)
            return null;
        if (points.getStartSheet() == null)
            throw new IllegalStateException("Missing table name");
        if (points.spanSheets())
            throw new UnsupportedOperationException("different sheet names: " + points.getStartSheet() + " != " + points.getEndSheet());
        final Sheet sheet = this.getSheet(points.getStartSheet(), true);

        return sheet.getMutableTableModel(points.getStartPoint(), points.getEndPoint());
    }

    /**
     * Return the cell at the passed address.
     * 
     * @param ref the full address, eg "$sheet.A12".
     * @return the cell at the passed address.
     */
    public final Cell<SpreadSheet> getCellAt(String ref) {
        final Tuple2<Sheet, Point> res = this.resolve(ref);
        return res.get0().getCellAt(res.get1());
    }

    /**
     * Resolve a cell address.
     * 
     * @param ref an OpenDocument cell address (see 9.2.1 of OpenDocument-v1.2-cs01-part1), e.g.
     *        "table.B2".
     * @return the table and the cell coordinates.
     */
    public final Tuple2<Sheet, Point> resolve(String ref) {
        final Matcher m = cellPattern.matcher(ref);
        if (!m.matches())
            throw new IllegalArgumentException(ref + " is not a valid cell address: " + m.pattern().pattern());
        final String sheetName = parseSheetName(m.group(1));
        if (sheetName == null)
            throw new IllegalArgumentException("no sheet specified: " + ref);
        return Tuple2.create(this.getSheet(sheetName, true), Sheet.resolve(m.group(5), m.group(6)));
    }

    public XPath getXPath(String p) throws JDOMException {
        return OOUtils.getXPath(p, this.getVersion());
    }

    // query directly the DOM, that way don't need to listen to it (eg for name, size or order
    // change)
    @SuppressWarnings("unchecked")
    private final List<Element> getTables() {
        return this.getBody().getChildren("table", this.getVersion().getTABLE());
    }

    public int getSheetCount() {
        return this.getTables().size();
    }

    public final Sheet getFirstSheet() {
        return this.getSheet(0);
    }

    public Sheet getSheet(int i) {
        return this.getSheet(getTables().get(i));
    }

    public Sheet getSheet(String name) {
        return this.getSheet(name, false);
    }

    /**
     * Return the first sheet with the passed name.
     * 
     * @param name the name of a sheet.
     * @param mustExist what to do when no match is found : <code>true</code> to throw an exception,
     *        <code>false</code> to return null.
     * @return the first matching sheet, <code>null</code> if <code>mustExist</code> is
     *         <code>false</code> and no match is found.
     * @throws NoSuchElementException if <code>mustExist</code> is <code>true</code> and no match is
     *         found.
     */
    public Sheet getSheet(String name, final boolean mustExist) throws NoSuchElementException {
        for (final Element table : getTables()) {
            if (name.equals(Table.getName(table)))
                return getSheet(table);
        }
        if (mustExist)
            throw new NoSuchElementException("no such sheet: " + name);
        else
            return null;
    }

    private final Sheet getSheet(Element table) {
        Sheet res = this.sheets.get(table);
        if (res == null) {
            res = new Sheet(this, table);
            this.sheets.put(table, res);
        }
        return res;
    }

    void invalidate(Element element) {
        this.sheets.remove(element);
    }

    /**
     * Adds an empty sheet.
     * 
     * @param index where to add the new sheet.
     * @param name the name of the new sheet.
     * @return the newly created sheet.
     */
    public final Sheet addSheet(final int index, String name) {
        return this.addSheets(index, Collections.singletonList(name)).get(0);
    }

    public final List<Sheet> addSheets(final int index, final List<String> names) {
        return this.addSheets(index, names, 1, 1);
    }

    public final List<Sheet> addSheets(final int index, final List<String> names, final int colCount, final int rowCount) {
        // create auto style with display=true (LO always generates one, and Google Docs expects it)
        final TableStyle tableStyle = Style.getStyleStyleDesc(TableStyle.class, this.getVersion()).createAutoStyle(this.getPackage());
        tableStyle.getTableProperties().setDisplayed(true);
        final int sheetCount = names.size();
        final List<Element> newElems = new ArrayList<Element>(sheetCount);
        for (int i = 0; i < sheetCount; i++) {
            newElems.add(Sheet.createEmpty(this.getVersion(), colCount, rowCount, tableStyle.getName()));
        }
        return this.addSheets(index, newElems, names);
    }

    final Sheet addSheet(final int index, final Element newElem, final String name) {
        return addSheets(index, Collections.singletonList(newElem), Collections.singletonList(name)).get(0);
    }

    final List<Sheet> addSheets(final int index, final List<Element> newElems, final List<String> names) {
        final int size = newElems.size();
        if (size != names.size())
            throw new IllegalArgumentException("Size mismatch " + newElems + " / " + names);
        this.getBody().addContent(getContentIndex(index), newElems);

        final List<Sheet> res = new ArrayList<Sheet>(size);
        final Iterator<String> iter = names.iterator();
        for (final Element newElem : newElems) {
            final Sheet newSheet = this.getSheet(newElem);
            res.add(newSheet);
            final String name = iter.next();
            // name is optional
            if (name != null)
                newSheet.setName(name);
        }
        assert !iter.hasNext();
        return res;
    }

    // convert between an index between 0 and getSheetCount(), to a content index (between 0 and
    // getBody().getContentSize())
    private final int getContentIndex(final int tableIndex) {
        if (tableIndex < 0)
            throw new IndexOutOfBoundsException("Negative index: " + tableIndex);
        // copy since we will modify it (plus JDOM uses an iterator)
        final List<Element> tables = new ArrayList<Element>(this.getTables());
        final int tablesCount = tables.size();
        if (tableIndex > tablesCount)
            throw new IndexOutOfBoundsException("index (" + tableIndex + ") > count (" + tablesCount + ")");
        // the following statement fails when adding after the last table:table :
        // this.getTables().add(index, newElem);
        // it add at the end of its parent element (e.g. after table:named-expressions).
        // so use the fact that there's always at least one sheet (all sheets aren't grouped there
        // can be Text or Comment in between them)
        final int contentIndex;
        if (tablesCount == 0) {
            contentIndex = 0;
        } else if (tableIndex == tablesCount) {
            // after last table
            contentIndex = this.getBody().indexOf(tables.get(tableIndex - 1)) + 1;
        } else {
            contentIndex = this.getBody().indexOf(tables.get(tableIndex));
        }
        return contentIndex;
    }

    public final Sheet addSheet(String name) {
        return this.addSheet(getSheetCount(), name);
    }

    void move(Sheet sheet, int toIndex) {
        final Element parentElement = sheet.getElement().getParentElement();
        sheet.getElement().detach();
        parentElement.addContent(getContentIndex(toIndex), sheet.getElement());
        // no need to update this.sheets since it doesn't depend on order
    }
}