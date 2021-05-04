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

import static java.util.Arrays.asList;
import org.jopendocument.dom.Length;
import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.LengthUnitTest;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.OOXML;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle.StyleTableCellProperties;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;
import org.jopendocument.dom.style.SideStyleProperties.Side;
import org.jopendocument.dom.text.Paragraph;
import org.jopendocument.dom.text.Span;
import org.jopendocument.dom.text.TextDocument;
import org.jopendocument.dom.text.TextNode;
import org.jopendocument.dom.text.TextStyle.StyleTextProperties;
import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.TimeUtils;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.Tuple3;
import org.jopendocument.util.SimpleXMLPath;
import org.jopendocument.util.Validator;

import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import junit.framework.TestCase;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class SheetTest extends TestCase {

    static final DefaultTableModel tm;
    static {
        tm = new DefaultTableModel();
        tm.addColumn("col1");
        tm.addColumn("col2");
        tm.addColumn("col3");

        // trailing 0 to test precision (i.e. not equals to -5.32)
        tm.addRow(new Object[] { "un1", new BigDecimal("-5.320"), new Integer(123) });
    }

    static public final void assertEquals(final Length o1, final Length o2) {
        LengthUnitTest.assertEqualsWithCompareTo(o1, o2);
    }

    private Sheet sheet;
    private Sheet realSheet;
    private SpreadSheet calc2;

    protected void setUp() throws Exception {
        this.sheet = SpreadSheet.createEmpty(tm, XMLFormatVersion.getOOo()).getSheet(0);
        this.realSheet = ODPackage.createFromStream(this.getClass().getResourceAsStream("test.fods"), null).getSpreadSheet().getSheet(0);
        this.calc2 = null;
    }

    protected void tearDown() throws Exception {
        this.sheet = null;
        this.realSheet = null;
        this.calc2 = null;
    }

    public SpreadSheet getCalc2() throws IOException {
        if (this.calc2 == null) {
            this.calc2 = ODPackage.createFromStream(this.getClass().getResourceAsStream("GroupsAndAddresses.fods"), null).getSpreadSheet();
        }
        return this.calc2;
    }

    public void testCreate() throws IOException {
        final DefaultTableModel empty = new DefaultTableModel();
        for (final OOXML vers : OOXML.values()) {
            if (vers.canValidate()) {
                final SpreadSheet emptySpread = SpreadSheet.createEmpty(empty, vers.getFormatVersion());
                final Validator validator = emptySpread.getPackage().getContent().getValidator();

                assertValid(emptySpread);
                assertValid(validator);
                assertEquals(0, validator.validateCompletely().size());

                // Should fail since we removed the body
                emptySpread.getSheet(0).getElement().removeContent();
                assertValid(emptySpread.getPackage(), false);
                assertValid(validator, false);
                assertTrue(validator.validateCompletely().size() > 0);
            }
        }

        // save
        final TableModel model = new DefaultTableModel(new Object[][] { { "Data", "DataCol2" } }, new Object[] { "Col1", "Col2" });
        final SpreadSheet created = SpreadSheet.createEmpty(model);
        assertValid(created);

        final File tempFile = File.createTempFile("testCreate", "." + created.getPackage().getContentType().getExtension());
        final File saved = created.saveAs(tempFile);
        saved.deleteOnExit();
        // assert that saveAs() didn't create a new file (otherwise the first one won't get deleted)
        if (!tempFile.equals(saved)) {
            tempFile.delete();
            fail("Wrong extension : " + tempFile);
        }
        // then read
        final Sheet sheetRead = SpreadSheet.createFromFile(saved).getSheet(0);
        // and check that the data is correct
        assertEquals(2, sheetRead.getRowCount());
        assertEquals("Col1", sheetRead.getValueAt("A1"));
        // data w/o column names
        final SheetTableModel<SpreadSheet> tmRead = sheetRead.getTableModel(0, 1);
        assertEquals(2, tmRead.getColumnCount());
        assertEquals(1, tmRead.getRowCount());
        assertEquals("DataCol2", tmRead.getValueAt(0, 1));

        // test toSingle()
        assertValid(new ODPackage(this.realSheet.getODDocument().getPackage()).toSingle().getPackage());

        final ODPackage realPkg = this.realSheet.getSpreadSheet().getPackage();
        try {
            realPkg.rmFile(RootElement.CONTENT.getZipEntry());
            fail("Cannot rm a file used by the ODDocument");
        } catch (Exception e) {
            // ok
        }
        // settings on the other hand is fine
        realPkg.putFile(RootElement.SETTINGS.getZipEntry(), RootElement.SETTINGS.createDocument(realPkg.getFormatVersion()));

        // OK since no ODDocument exists
        new ODPackage(realPkg).rmFile(RootElement.CONTENT.getZipEntry());

        // test repeated
        final SpreadSheet repeated = SpreadSheet.create(XMLFormatVersion.getDefault(), 2, 5, 25);
        assertEquals(2, repeated.getSheetCount());
        for (int i = 0; i < repeated.getSheetCount(); i++) {
            final Sheet sheetI = repeated.getSheet(i);
            assertEquals(5, sheetI.getColumnCount());
            assertEquals(25, sheetI.getRowCount());
            sheetI.setValueAt("foo", 4, 24);
            try {
                sheetI.setValueAt("bar", 5, 24);
                fail("Beyond bounds");
            } catch (Exception e) {
                // OK
            }
        }
        assertValid(repeated);
    }

    public void testName() throws Exception {
        final String n = "users";
        final SpreadSheet realSpread = this.realSheet.getSpreadSheet();
        assertEquals(n, realSpread.getSheet(n).getName());

        // the sheet can be accessed with a changed name
        final String newName = "newName";
        assertFalse(newName.equals(this.realSheet.getName()));
        this.realSheet.setName(newName);
        assertTrue(newName.equals(this.realSheet.getName()));
        assertSame(this.realSheet, realSpread.getSheet(newName));

        // test add
        final int startSheetCount = 2;
        assertEquals(startSheetCount, realSpread.getSheetCount());
        final Sheet sheet0 = realSpread.getSheet(0);
        final Sheet sheet1 = realSpread.getSheet(1);
        // add at the end since we used to have a bug that added table:table at the very end of
        // body, e.g. after table:named-expressions.
        final Sheet addSheet = realSpread.addSheet(2, "newSheet");
        assertEquals(startSheetCount + 1, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet("newSheet"));
        assertSame(sheet0, realSpread.getSheet(0));
        assertSame(sheet1, realSpread.getSheet(1));
        assertSame(addSheet, realSpread.getSheet(2));

        // assert that the new sheet is functional
        addSheet.ensureRowCount(8);
        // (was throwing a NPE in Table.updateWidth())
        addSheet.ensureColumnCount(10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                addSheet.setValueAt(i + "," + j, i, j);
            }
        }

        assertValid(this.realSheet.getSpreadSheet());

        // test move
        for (int i = 0; i < 3; i++) {
            addSheet.move(i);
            // test index
            assertSame(addSheet, realSpread.getSheet(i));
        }
        addSheet.move(0);
        assertEquals(startSheetCount + 1, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet("newSheet"));
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(sheet0, realSpread.getSheet(1));
        assertSame(sheet1, realSpread.getSheet(2));

        assertValid(this.realSheet.getSpreadSheet());

        // test copy
        final Sheet copiedSheet = addSheet.copy(2, "copiedSheet");
        assertEquals(startSheetCount + 2, realSpread.getSheetCount());
        assertSame(copiedSheet, realSpread.getSheet("copiedSheet"));
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(sheet0, realSpread.getSheet(1));
        assertSame(copiedSheet, realSpread.getSheet(2));
        assertSame(sheet1, realSpread.getSheet(3));

        assertValid(this.realSheet.getSpreadSheet());

        // test rm
        final int count = realSpread.getSheetCount();
        assertSame(sheet0, this.realSheet);
        this.realSheet.detach();
        assertNull(realSpread.getSheet(this.realSheet.getName()));
        assertEquals(count - 1, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(copiedSheet, realSpread.getSheet(1));
        assertSame(sheet1, realSpread.getSheet(2));

        assertValid(this.realSheet.getSpreadSheet());

        // test quoting
        {
            final String simple = "aposAndTab";
            assertTrue(simple.equals(SpreadSheet.formatSheetName(simple)));

            final String needQuote = "apos ' & tab \t";
            assertFalse(needQuote.equals(SpreadSheet.formatSheetName(needQuote)));
            assertEquals("'apos '' & tab \t'", SpreadSheet.formatSheetName(needQuote));
            assertEquals(needQuote, SpreadSheet.parseSheetName(SpreadSheet.formatSheetName(needQuote)));

            try {
                SpreadSheet.parseSheetName("'foo");
                fail("should have failed since quote is not closed");
            } catch (IllegalArgumentException e) {
                // ok
            }
        }
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.setValueAt(Object, int, int)'
     */
    public void testSetValueAt() throws Exception {
        assertEquals("col1", this.sheet.getValueAt(0, 0));
        assertEquals("col1", getFirstP(this.sheet.getImmutableCellAt(0, 0)).getCharacterContent());
        this.sheet.setValueAt("test", 0, 0);
        assertEquals("test", this.sheet.getValueAt(0, 0));

        this.sheet.setValueAt(60001399.1523, 0, 0);
        assertEquals(new BigDecimal("60001399.1523"), this.sheet.getValueAt(0, 0));

        final Calendar now = Calendar.getInstance();
        this.sheet.setValueAt(now.getTime(), 0, 0);
        final Object valueAt = this.sheet.getValueAt(0, 0);
        // OpenDocument can encode time up to the ms
        assertEquals(now.getTime(), valueAt);
        assertValid(this.sheet.getSpreadSheet());

        try {
            this.realSheet.getCellAt("C3");
            fail("should have failed since C3 is covered");
        } catch (IllegalArgumentException e) {
            // ok
        }
        this.realSheet.getCellAt("B3").unmerge();
        // should now succeed
        this.realSheet.getCellAt("C3").setValue(new Date());

        // preserve style
        {
            // test with odt (it has always worked in ods since text:p cannot have style, it is on
            // the cell)
            final ODPackage doc = new ODPackage(this.getClass().getResourceAsStream("../test.odt"));
            final Table<TextDocument> t = new Table<TextDocument>(doc.getTextDocument(), doc.getContent().getDescendantByName("table:table", "JODTestTable"));
            final MutableCell<TextDocument> cell = t.getCellAt(2, 1);
            assertEquals("end", getFirstP(cell).getStyle().getAlignment());
            cell.setValue("somethingElse");
            assertEquals("end", getFirstP(cell).getStyle().getAlignment());
        }

        // test white space encoding and decoding
        {
            final String spacesAndTab = "spaces   and tab\t.";
            this.sheet.setValueAt(spacesAndTab, 0, 0);
            assertEquals(spacesAndTab, this.sheet.getValueAt(0, 0));
        }

        // test separators
        {
            // two paragraphs
            final MutableCell<SpreadSheet> h3 = this.realSheet.getCellAt("H3");
            testCellContent(h3, "Conditional", "Format");
            h3.setValue("Cond\nFormats");
            testCellContent(h3, "Cond", "Formats");
            h3.setValue("Cond" + TextNode.LINE_SEPARATOR + "Formats");
            testCellContent(h3, "Cond", "Formats");
            h3.setValue("Cond" + TextNode.LINE_SEPARATOR + "Formats" + TextNode.PARAGRAPH_SEPARATOR + "line3");
            testCellContent(h3, "Cond", "Formats", "line3");
            // From Microsoft Word
            h3.setValue("Cond" + TextNode.VERTICAL_TAB_CHAR + "Formats" + TextNode.PARAGRAPH_SEPARATOR + "line3");
            testCellContent(h3, "Cond", "Formats", "line3");
            h3.setValue("");
            testCellContent(h3, "");
            // clear is stronger than setText("")
            h3.clearValue();
            assertEquals("", h3.getTextValue());
            assertEquals(0, h3.getLinesCount());
        }

        // test removing of different value attributes (we used to never remove attributes thus
        // having at the same time boolean-value, date-value, value, ...)
        this.realSheet.setValueAt(60001399.1523, 0, 0);
        this.realSheet.setValueAt(true, 0, 0);
        // only RelaxNG can detect the bug
        assertTrue(this.realSheet.getSpreadSheet().getVersion() == XMLVersion.OD);

        assertValid(this.realSheet.getSpreadSheet());
    }

    private void testCellContent(final MutableCell<SpreadSheet> h3, String... lines) {
        final String l1 = lines[0];
        // line breaks aren't supported by LibreOffice
        assertEquals(CollectionUtils.join(Arrays.asList(lines), TextNode.PARAGRAPH_SEPARATOR), h3.getTextValue(true, true));
        assertEquals(lines.length, h3.getParagraphs().getCount());
        assertEquals(lines.length, h3.getLinesCount());
        final Paragraph p0 = h3.getParagraphs().get(0);
        assertEquals(l1, p0.getCharacterContent());
        assertEquals(1, p0.getSpans().getCount());
        // span (and thus style) are kept
        final Span span0 = p0.getSpans().get(0);
        assertEquals(l1, span0.getCharacterContent());
        assertEquals("bold", span0.getStyle().getTextProperties().getWeight());
        assertEquals("GB", span0.getStyle().getTextProperties().getCountry());
    }

    private final Paragraph getFirstP(final Cell<?> cell) {
        return new Paragraph(cell.getElement().getChild("p", cell.getNS().getTEXT()), cell.getODDocument());
    }

    public void testFormulas() throws Exception {
        final Sheet sheet = this.realSheet.getSpreadSheet().getSheet(1);
        assertEquals("=a0", sheet.getCellAt("B3").getFormula());
        assertEquals(null, sheet.getCellAt("C3").getFormula());

        final Namespace defaultNS = sheet.getODDocument().getVersion().getNS("of");
        final MutableCell<SpreadSheet> a1Cell = sheet.getCellAt("A1");

        final String formula = "=SUM([.A2:.A3])";
        a1Cell.setFormula(formula);
        assertEquals(formula, a1Cell.getFormula());
        assertEquals(formula, a1Cell.getRawFormula());
        assertEquals(Tuple3.create(defaultNS, formula, null), a1Cell.getFormulaAndNamespace());

        a1Cell.setFormulaAndNamespace(defaultNS, formula);
        // getFormula() returns the same
        assertEquals(formula, a1Cell.getFormula());
        // but the raw value has the prefix
        assertEquals("of:" + formula, a1Cell.getRawFormula());
        assertEquals(Tuple3.create(defaultNS, formula, defaultNS.getPrefix()), a1Cell.getFormulaAndNamespace());

        // ambiguity
        final String ambuiguateFormula = "foo:bar";
        a1Cell.setFormulaAndNamespace(null, ambuiguateFormula);
        // the formula is correctly returned
        assertEquals(ambuiguateFormula, a1Cell.getFormula());
        // but the raw value has the prefix to disambiguate
        assertEquals("of:" + ambuiguateFormula, a1Cell.getRawFormula());

        // non default namespace
        final Namespace nonDefNS = Namespace.getNamespace("nonDef", "urn:myFormula");
        a1Cell.setFormulaAndNamespace(nonDefNS, ambuiguateFormula);
        assertEquals("nonDef:" + ambuiguateFormula, a1Cell.getRawFormula());
        assertEquals(Tuple3.create(nonDefNS, ambuiguateFormula, nonDefNS.getPrefix()), a1Cell.getFormulaAndNamespace());
        try {
            a1Cell.getFormula();
            fail("Not default namespace");
        } catch (Exception e) {
            // OK
        }
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getValueAt(int, int)'
     */
    public void testGetValueAt() {
        assertEquals(new BigDecimal(123), this.sheet.getValueAt(2, 1));
        // testGetValueAtString()
        assertEquals(new BigDecimal(123), this.sheet.getValueAt("C2"));

        final Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2008, 11, 25);
        final Cell<SpreadSheet> dateCell = this.realSheet.getImmutableCellAt("F3");
        assertEquals(c.getTime(), dateCell.getValue());
        assertEquals("25 déc. 2008", dateCell.getTextValue());

        final Cell<?> textCell = this.realSheet.getImmutableCellAt("B3");
        assertEquals("Gestion\tdes droits\nnouvelle  arborescence ", textCell.getValue());
        assertEquals("Gestion\tdes droits\nnouvelle  arborescence ", textCell.getTextValue(true));
        // test std mode
        // <cell>
        // <text:p>
        // Paragraph
        // </text:p>
        // </cell>
        textCell.getElement().removeContent();
        final Element p = new Element("p", textCell.getElement().getNamespace("text"));
        p.addContent("\n\t\tGestion \t des droits");
        textCell.getElement().addContent("\n\t").addContent(p).addContent("\n");
        assertEquals("Gestion des droits", textCell.getTextValue(false));

        assertSame(this.realSheet.getCellAt("F3"), this.realSheet.getSpreadSheet().getCellAt(this.realSheet.getName() + ".$F$3"));

        {
            final SheetTableModel<SpreadSheet> tm = this.realSheet.getSpreadSheet().getSheet(1).getTableModel(1, 1);
            // = NA()
            assertNotNull(tm.getImmutableCellAt(0, 0).getError());
            // = "#N" & "/D"
            // we can't be right
            // assertNull(tm.getImmutableCellAt(0, 1).getError());
            // =A0
            assertNotNull(tm.getImmutableCellAt(1, 0).getError());
            // #NOM ? : right since there's no formula
            assertNull(tm.getImmutableCellAt(1, 1).getError());
            // =3/0
            assertNotNull(tm.getImmutableCellAt(2, 0).getError());
            // empty cell
            assertTrue(tm.getImmutableCellAt(2, 1).isEmpty());
            assertNull(tm.getImmutableCellAt(2, 1).getError());
        }
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getRowCount()'
     */
    public void testGetCount() {
        // col labels + 1 row
        assertEquals(2, this.sheet.getRowCount());
        assertEquals(3, this.sheet.getColumnCount());
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.ensureColumnCount(int)'
     */
    public void testEnsureColumnCount() {
        final int before = this.sheet.getColumnCount();
        final int newSize = before - 2;
        this.sheet.ensureColumnCount(newSize);
        assertEquals(before, this.sheet.getColumnCount());
    }

    public void testSetColumnCount() throws Exception {
        // test in-memory
        final int newSize = this.sheet.getColumnCount() + 5;
        try {
            this.sheet.setValueAt("test", newSize - 1, 0);
            fail("should have thrown IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException exn) {
            // normal
        }
        this.sheet.setColumnCount(newSize);
        this.sheet.setValueAt("test", newSize - 1, 0);

        // test with real file
        final int origCount = this.realSheet.getColumnCount();
        try {
            this.realSheet.setValueAt("over", origCount + 10 - 1, 1);
            fail("should throw exn since we try to write past the limit");
        } catch (RuntimeException e) {
            // ok
        }
        this.realSheet.setColumnCount(origCount + 10);
        this.realSheet.setValueAt("over", origCount + 10 - 1, 1);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 15);
        this.realSheet.getSpreadSheet().getPackage().save(out);
        final SpreadSheet reRead = new ODPackage(new ByteArrayInputStream(out.toByteArray())).getSpreadSheet();
        assertEquals("over", reRead.getSheet(0).getValueAt(origCount + 10 - 1, 1));

        assertValid(this.realSheet.getSpreadSheet());
    }

    public void testRemoveCol() throws Exception {
        this.realSheet.setColumnCount(9, -1, true);
        assertEquals(9, this.realSheet.getColumnCount());
        assertFalse(this.realSheet.getImmutableCellAt("C3").isValid());
        this.realSheet.removeColumn(1, true);
        assertEquals(8, this.realSheet.getColumnCount());
        // B3 was covering C3, but since the B column is gone C3 is incovered
        assertTrue(this.realSheet.getImmutableCellAt("C3").isValid());

        final ODPackage doc = new ODPackage(this.getClass().getResourceAsStream("../test.odt"));
        final Table<ODDocument> t = new Table<ODDocument>(doc.getODDocument(), doc.getContent().getDescendantByName("table:table", "JODTestTable"));
        final Length colW = t.getColumn(1).getWidth();
        Length tableW = t.getWidth();
        t.removeColumn(1, false);
        assertEquals(tableW.subtract(colW), t.getWidth());

        tableW = t.getWidth();
        t.removeColumn(1, true);
        assertEquals(tableW, t.getWidth());
    }

    public void testRemoveRow() throws Exception {
        final Sheet sheet = this.getCalc2().getSheet("remove");

        // before
        {
            final Cell<SpreadSheet> b2Cell = sheet.getImmutableCellAt("B2");
            assertEquals("B2", b2Cell.getValue());
            assertEquals(2, b2Cell.getColumnsSpanned());
            assertEquals(3, b2Cell.getRowsSpanned());
            final Cell<SpreadSheet> h5Cell = sheet.getImmutableCellAt("H5");
            assertEquals("H5", h5Cell.getValue());
            assertEquals(1, h5Cell.getColumnsSpanned());
            assertEquals(3, h5Cell.getRowsSpanned());

            final List<TableGroup> level1Groups = sheet.getRowGroup().getChildren();
            assertEquals(1, level1Groups.size());
            final TableGroup level1Group = level1Groups.get(0);
            assertEquals(1, level1Group.getFirst());
            assertEquals(6, level1Group.getSize());

            assertEquals(1, level1Group.getChildren().size());
            final TableGroup level2Group = level1Group.getChildren().get(0);
            assertEquals(4, level2Group.getFirst());
            assertEquals(2, level2Group.getSize());
        }

        sheet.removeRows(2, 6);
        assertValid(sheet.getODDocument().getPackage());

        // after
        {
            final Cell<SpreadSheet> b2Cell = sheet.getImmutableCellAt("B2");
            assertEquals("B2", b2Cell.getValue());
            assertEquals(2, b2Cell.getColumnsSpanned());
            assertEquals(1, b2Cell.getRowsSpanned());
            final Cell<SpreadSheet> h3Cell = sheet.getImmutableCellAt("H3");
            // we removed 4 rows
            assertEquals("H7", h3Cell.getValue());
            assertEquals(1, h3Cell.getColumnsSpanned());
            assertEquals(1, h3Cell.getRowsSpanned());

            // level 1 group was shortened
            final List<TableGroup> level1Groups = sheet.getRowGroup().getChildren();
            assertEquals(1, level1Groups.size());
            final TableGroup level1Group = level1Groups.get(0);
            assertEquals(1, level1Group.getFirst());
            assertEquals(2, level1Group.getSize());

            // empty table group was removed
            assertEquals(0, level1Group.getChildren().size());
        }
    }

    public void testSetRowCount() throws IOException {
        this.realSheet.setRowCount(3);
        // java model OK
        assertEquals(3, this.realSheet.getRowCount());
        final SpreadSheet reParsed = new ODPackage(this.realSheet.getSpreadSheet().getPackage()).getSpreadSheet();
        // XML model OK
        assertEquals(3, reParsed.getSheet(0).getRowCount());
        assertValid(this.realSheet.getSpreadSheet());
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getTableModel(int, int)'
     */
    public void testGetTableModel() {
        SheetTableModel<SpreadSheet> t = this.sheet.getTableModel(1, 0);
        assertEquals(tm.getColumnCount() - 1, t.getColumnCount());
        // +1: col labels
        assertEquals(tm.getRowCount() + 1, t.getRowCount());
        assertEquals(new BigDecimal("-5.320"), t.getValueAt(1, 0));

        // empty hence no style
        assertNull(t.getImmutableCellAt(0, 0).getStyle());
        try {
            t.getValueAt(1, t.getColumnCount());
            fail("should have thrown exn");
        } catch (IndexOutOfBoundsException e) {
            // ok
        }
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getMutableTableModel(int, int)'
     */
    public void testGetMutableTableModel() {
        this.sheet.getMutableTableModel(1, 0).setValueAt("test", 0, 1);
        assertEquals("test", this.sheet.getCellAt("C1").getValue());

        final MutableTableModel<SpreadSheet> tm = this.realSheet.getSpreadSheet().getTableModel("rights");
        assertEquals("K1", tm.getValueAt(13, 6));

        assertEquals(Color.CYAN, tm.getCellAt(0, 0).getTableCellProperties().getBackgroundColor());
        assertEquals("#ffcc99", tm.getCellAt(1, 2).getTableCellProperties().getRawBackgroundColor());

        try {
            tm.setValueAt("foo", 0, tm.getColumnCount());
            fail("should have thrown exn");
        } catch (IndexOutOfBoundsException e) {
            // ok
        }
    }

    public void testMerge() throws IOException {
        {
            final Table<?> t = this.getCalc2().getSheet(0);
            assertNull(t.getCoverOrigin(0, 0));
            final Point orig = new Point(1, 11);
            for (int x = 0; x <= 5; x++) {
                for (int y = 10; y <= 15; y++) {
                    if (x < 1 || x > 4 || y < 11 || y > 14)
                        assertNull(t.getCoverOrigin(x, y));
                    else
                        assertEquals(orig, t.getCoverOrigin(x, y));
                }
            }
            // test bounds
            assertEquals(new Point(0, 16), t.getCoverOrigin(1, 16));
        }

        final MutableCell<SpreadSheet> b3 = this.realSheet.getCellAt("B3");
        assertEquals(2, b3.getColumnsSpanned());
        assertEquals(1, b3.getRowsSpanned());

        b3.unmerge();
        assertEquals(1, b3.getColumnsSpanned());
        assertEquals(1, b3.getRowsSpanned());
        assertFalse(this.realSheet.getImmutableCellAt("C3").isCovered());
        assertFalse(this.realSheet.getImmutableCellAt("B4").isCovered());
        assertNull(this.realSheet.getCoverOrigin(b3.getX(), b3.getY()));

        b3.merge(3, 2);
        assertEquals(3, b3.getColumnsSpanned());
        assertEquals(2, b3.getRowsSpanned());
        assertFalse(b3.isCovered());
        assertTrue(this.realSheet.getImmutableCellAt("C3").isCovered());
        assertTrue(this.realSheet.getImmutableCellAt("B4").isCovered());
        // we cannot have overlapping merge since we cannot get to a covered cell
        try {
            this.realSheet.getCellAt("C3");
            fail("should have thrown an exn");
        } catch (Exception e) {
            // ok : we can't modify a covered cell
        }
        assertEquals(b3.getPoint(), this.realSheet.getCoverOrigin(b3.getX(), b3.getY()));
        assertEquals(b3.getPoint(), this.realSheet.getCoverOrigin(b3.getX() + 3 - 1, b3.getY() + 2 - 1));

        // merge more
        b3.merge(3, 3);
        assertEquals(3, b3.getColumnsSpanned());
        assertEquals(3, b3.getRowsSpanned());

        // if we call merge() with less cells it un-merge the rest
        b3.merge(1, 3);
        assertEquals(1, b3.getColumnsSpanned());
        assertEquals(3, b3.getRowsSpanned());
        assertFalse(b3.isCovered());
        assertFalse(this.realSheet.getImmutableCellAt("C3").isCovered());
        assertTrue(this.realSheet.getImmutableCellAt("B4").isCovered());

        try {
            this.realSheet.getCellAt(0, 0).merge(2, 4);
            fail("Allowed overlapping merge");
        } catch (Exception e) {
            // ok
        }
        // the document was unchanged by the failed attempt
        assertFalse(this.realSheet.getImmutableCellAt(0, 0).coversOtherCells());
        assertFalse(this.realSheet.getImmutableCellAt(0, 1).isCovered());
        assertValid(this.realSheet.getSpreadSheet());
    }

    public void testResolve() {
        testResolve(new Point(0, 22), "A23");
        testResolve(new Point(26, 33), "AA34");
        testResolve(new Point(106, 11), "DC12");
        testResolve(new Point(25, 37), "Z38");
        // to verify just input 0 into A1 and drag to the right
        // you can also use COLONNE(ABC1) - 1
        testResolve(new Point(701, 0), "ZZ1");
        testResolve(new Point(702, 0), "AAA1");
        testResolve(new Point(730, 0), "ABC1");
        assertNull(Sheet.resolve("A23A"));
        assertNull(Sheet.resolve("test"));
        assertNull(Sheet.resolve("23"));

        assertEquals(77, Sheet.toInt("BZ"));

        final Range oneCellRange = Range.parse("Sheet1.A23");
        // OpenDocument requires the dots
        final Range sansSheetRange = Range.parse(".A23:.AA34");
        final Range oneSheetRange = Range.parse("Sheet1.A23:.AA34");
        final Range twoSheetsRange = Range.parse("Sheet1.A23:Sheet1.AA34");
        final Range spanSheetsRange = Range.parse("Sheet1.A23:Sheet2.AA34");
        // invalid range
        try {
            Range.parse("A23:Sheet1.AA34");
            fail("Invalid range");
        } catch (Exception e) {
            // OK
        }
        try {
            Range.parse(".A23:Sheet1.");
            fail("Invalid range");
        } catch (Exception e) {
            // OK
        }
        assertEquals(null, sansSheetRange.getStartSheet());
        assertEquals(null, sansSheetRange.getEndSheet());

        assertEquals("Sheet1", oneCellRange.getStartSheet());
        assertEquals("Sheet1", oneSheetRange.getStartSheet());
        assertEquals("Sheet1", oneSheetRange.getEndSheet());

        assertEquals("Sheet1", spanSheetsRange.getStartSheet());
        assertEquals("Sheet2", spanSheetsRange.getEndSheet());

        assertTrue(oneSheetRange.equals(twoSheetsRange));
        assertFalse(sansSheetRange.equals(twoSheetsRange));
        assertFalse(spanSheetsRange.equals(twoSheetsRange));

        for (final Range range : new Range[] { oneCellRange, sansSheetRange, oneSheetRange, twoSheetsRange, spanSheetsRange }) {
            assertTrue(range.spanSheets() == (range == spanSheetsRange));
            assertEquals(range, Range.parse(range.toString()));
            assertEquals(Sheet.resolve("A23"), range.getStartPoint());
            assertEquals((range == oneCellRange) ? range.getStartPoint() : Sheet.resolve("AA34"), range.getEndPoint());
        }

        assertEquals(Collections.singleton("rights"), this.realSheet.getSpreadSheet().getRangesNames());
        assertEquals(new Range(this.realSheet.getName(), new Point(2, 10), new Point(25, 26)), this.realSheet.getSpreadSheet().getRange("rights"));

        assertEquals(Collections.singleton("legend"), this.realSheet.getRangesNames());
        // no name
        assertEquals(new Range(null, new Point(1, 33), new Point(2, 37)), this.realSheet.getRange("legend"));

        final Sheet noRangesSheet = this.realSheet.getSpreadSheet().getSheet(1);
        assertEquals(Collections.emptySet(), noRangesSheet.getRangesNames());
        assertEquals(null, noRangesSheet.getRange("legend"));
    }

    private void testResolve(Point p, String addr) {
        assertEquals(p, Sheet.resolve(addr));
        assertEquals(addr, Sheet.getAddress(p));
    }

    public void testDuplicateRows() throws Exception {
        this.realSheet.duplicateRows(8, 3, 2);
        assertEquals(this.realSheet.getValueAt("B11"), this.realSheet.getValueAt("B14"));
        assertEquals("Comptabilité", this.realSheet.getValueAt("H9"));
        // two copies
        assertEquals(this.realSheet.getValueAt("H9"), this.realSheet.getValueAt("H12"));
        assertEquals(this.realSheet.getValueAt("H9"), this.realSheet.getValueAt("H15"));

        assertValid(this.realSheet.getSpreadSheet());

        assertEquals(2, this.sheet.getRowCount());
        // test cloning of last row
        this.sheet.duplicateRows(1, 1, 3);
        assertEquals(5, this.sheet.getRowCount());
        assertEquals(this.sheet.getValueAt("C2"), this.sheet.getValueAt("C4"));

        // test quoting of table name
        final Sheet sheet2 = this.getCalc2().getSheet("end'Cell & Address");

        try {
            sheet2.duplicateRows(2, 2, 1, false);
            fail("should have failed since a merged cell ends in the middle");
        } catch (IllegalArgumentException e) {
            // ok
        }
        try {
            sheet2.duplicateRows(10, 2, 1, false);
            fail("should have failed since a merged cell starts in the middle");
        } catch (IllegalArgumentException e) {
            // ok
        }

        // Nothing to do
        testShapeSpan(sheet2, "rect", "A1", "A3");
        // Just offset
        testShapeSpan(sheet2, "custom-shape", "A4", "B8");
        // Offset and copied
        testShapeSpan(sheet2, "frame", "E3", "E3");
        // use height but isn't copied
        testShapeSpan(sheet2, "line", "B1", "B6");
        // use height and copied
        testShapeSpan(sheet2, "g", "B2", "D5");
        assertEquals(3, sheet2.getCellAt("F1").getRowsSpanned());

        // repeat the end of a merged cell
        sheet2.duplicateRows(1, 2, 2, true);

        // Nothing to do
        testShapeSpan(sheet2, "rect", "A1", "A3");
        // Just offset
        testShapeSpan(sheet2, "custom-shape", "A8", "B12");
        // Offset and copied
        testShapeSpan(sheet2, "frame", "E3", "E3");
        testShapeSpan(sheet2, "frame", "E5", "E5");
        testShapeSpan(sheet2, "frame", "E7", "E7");
        // use height but isn't copied
        testShapeSpan(sheet2, "line", "B1", "B5");
        // use height and copied
        testShapeSpan(sheet2, "g", "B2", "D5");
        testShapeSpan(sheet2, "g", "B4", "D7");
        testShapeSpan(sheet2, "g", "B6", "D9");
        assertEquals(7, sheet2.getCellAt("F1").getRowsSpanned());

        // repeat a contained merged cell
        assertEquals(4, sheet2.getCellAt("B16").getRowsSpanned());
        sheet2.duplicateRows(14, 6, 1, false);
        assertEquals(4, sheet2.getCellAt("B16").getRowsSpanned());
        assertEquals(4, sheet2.getCellAt("B22").getRowsSpanned());
    }

    private void testShapeSpan(final Sheet sheet2, final String elemName, final String startCell, final String endCell) {
        final Element justOffset = SimpleXMLPath.allElements(elemName, "draw").selectSingleNode(sheet2.getImmutableCellAt(startCell).getElement());
        final Tuple2<Sheet, Point> end = sheet2.getSpreadSheet().resolve(justOffset.getAttributeValue("end-cell-address", sheet2.getTABLE()));
        assertSame(sheet2, end.get0());
        assertEquals(Table.resolve(endCell), end.get1());
    }

    public void testXY() throws Exception {
        final int y = 35;
        {
            final MutableCell<?> c = this.realSheet.getCellAt(2, y);
            assertEquals("LECTURE SEULE", c.getValue());
            assertEquals(2, c.getX());
            assertEquals(y, c.getY());
        }
        // offset by 6
        this.realSheet.duplicateRows(8, 3, 2);

        {
            final MutableCell<?> c = this.realSheet.getCellAt(2, y + 6);
            assertEquals("LECTURE SEULE", c.getValue());
            assertEquals(2, c.getX());
            assertEquals(y + 6, c.getY());
        }
    }

    public void testUsedRange() throws Exception {
        final Sheet emptySheet = this.sheet.getSpreadSheet().addSheet("empty");
        assertNull(emptySheet.getUsedRange());
        // test for content
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("B3"), Table.resolve("J38")), this.realSheet.getUsedRange());
        // also test for style
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("B3"), Table.resolve("Z38")), this.realSheet.getUsedRange(true));

        assertEquals(new Range("empty", Table.resolve("A1")), emptySheet.getCurrentRegion("A1"));
        // A1 is empty and bounded by empty cells
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("A1")), this.realSheet.getCurrentRegion("A1"));
        // C is covered
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("D3"), Table.resolve("F4")), this.realSheet.getCurrentRegion("D4"));
        // test for content
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("B9"), Table.resolve("I27")), this.realSheet.getCurrentRegion("C11"));
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("E18")), this.realSheet.getCurrentRegion("E18"));
        // also test for style
        assertEquals(new Range(this.realSheet.getName(), Table.resolve("B9"), Table.resolve("Z32")), this.realSheet.getCurrentRegion("C11", true));
        assertEquals(this.realSheet.getCurrentRegion("C11", true), this.realSheet.getCurrentRegion("E18", true));
    }

    public void testGroups() throws Exception {
        final Sheet sheet = this.getCalc2().getSheet("Groups");
        assertEquals(0, sheet.getColumnGroup().getChildren().size());
        final TableGroup rowsGroup = sheet.getRowGroup();
        assertNull(rowsGroup.getParent());
        assertSame(sheet, rowsGroup.getTable());
        assertEquals(sheet.getRowCount(), rowsGroup.getSize());
        assertEquals(1, rowsGroup.getChildren().size());
        final TableGroup childGroup = rowsGroup.getChildren().get(0);
        assertEquals(1, childGroup.getFirst());
        assertEquals(13, childGroup.getSize());

        // the table has 2 header rows in 2 different groups
        assertEquals(2, sheet.getHeaderRowCount());
        assertEquals(1, rowsGroup.getHeaderCount());
        assertEquals(1, childGroup.getHeaderCount());

        assertEquals(2, childGroup.getChildren().size());
        final TableGroup group1_1 = childGroup.getChildren().get(0);
        assertSame(childGroup, group1_1.getParent());
        assertEquals(0, group1_1.getChildren().size());
        assertEquals(2, group1_1.getFirst());
        assertEquals(2, group1_1.getSize());
        assertEquals("A3", sheet.getValueAt(0, group1_1.getFirst()));

        final TableGroup group1_2 = childGroup.getChildren().get(1);
        assertSame(childGroup, group1_2.getParent());
        assertEquals(0, group1_2.getChildren().size());
        assertEquals(6, group1_2.getFirst());
        assertEquals(4, group1_2.getSize());
        assertEquals("group1,2", sheet.getValueAt(0, group1_2.getFirst()));

        // groupAt
        assertSame(sheet.getRowGroup(), sheet.getRowGroupAt(0));
        assertSame(childGroup, sheet.getRowGroupAt(1));
        assertSame(group1_1, sheet.getRowGroupAt(2));
        assertSame(group1_1, sheet.getRowGroupAt(3));
        assertSame(childGroup, sheet.getRowGroupAt(4));
        assertSame(childGroup, sheet.getRowGroupAt(5));
        assertSame(group1_2, sheet.getRowGroupAt(6));

        // table-rows
        {
            // http://plugtest.opendocsociety.org/doku.php?id=scenarios:20110715:tabletablewrapper
            final Sheet plural = new ODPackage(getClass().getResourceAsStream("./rowscolumns-gnumeric.ods")).getSpreadSheet().getSheet(0);
            assertEquals(128, plural.getRowCount());
            assertEquals(128, plural.getColumnCount());
            final Integer[][] data = new Integer[][] { { 1, 2, 3 }, { 10, 20, 30 }, { 15, 5, 30 } };
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    assertEquals(BigDecimal.valueOf(data[i][j]), plural.getValueAt(i, j));
                }
            }
        }
    }

    public void testRepeated() throws Exception {
        final Row<SpreadSheet> row26 = this.realSheet.getRow(26);
        assertEquals(1, row26.getRepeated());
        assertEquals(26, row26.getY());
        assertEquals(26, row26.getLastY());

        final Row<SpreadSheet> row27 = this.realSheet.getRow(27);
        assertEquals(5, row27.getRepeated());
        assertEquals(27, row27.getY());
        assertEquals(31, row27.getLastY());
        assertSame(row27, this.realSheet.getRow(29));
        assertSame(row27, this.realSheet.getRow(31));
        assertNotSame(row27, this.realSheet.getRow(26));
        assertNotSame(row27, this.realSheet.getRow(32));

        // if we're just reading,
        assertEquals("", this.realSheet.getValueAt(0, 27));
        // the row is still repeated
        assertSame(row27, this.realSheet.getRow(27));
        assertEquals(5, row27.getRepeated());

        // if we're writing,
        this.realSheet.setValueAt("Break repeated", 0, 27);
        assertEquals("Break repeated", this.realSheet.getValueAt(0, 27));
        assertEquals("", this.realSheet.getValueAt(0, 28));
        // the row is no longer repeated
        assertNotSame(row27, this.realSheet.getRow(27));
        assertEquals(1, this.realSheet.getRow(27).getRepeated());
        assertEquals(4, this.realSheet.getRow(28).getRepeated());
    }

    public void testStyle() throws Exception {
        // even though the style-name is null
        assertNull(this.realSheet.getImmutableCellAt(2, 10).getStyleAttr());
        final String style = this.realSheet.getStyleNameAt(2, 10);
        assertNotNull(style);
        // the correct style can still be found
        assertEquals(Color.CYAN, this.realSheet.getTableCellPropertiesAt(2, 10).getBackgroundColor());
        // also from MutableCell
        assertEquals(style, this.realSheet.getCellAt(2, 10).getStyleName());

        // conditional style
        {
            final CellStyle headingStyle = Style.getStyle(this.realSheet.getSpreadSheet().getPackage(), CellStyle.class, "Heading");
            final CellStyle heading1Style = Style.getStyle(this.realSheet.getSpreadSheet().getPackage(), CellStyle.class, "Heading1");

            // ==5 : Titre ; >=15 Titre1
            final MutableCell<SpreadSheet> relationAndMultiple = this.realSheet.getCellAt("J3");
            // test default value
            assertTrue(relationAndMultiple.isEmpty());
            CellStyle elseStyle = relationAndMultiple.getStyle();
            assertFalse(elseStyle.equals(headingStyle) || elseStyle.equals(heading1Style));

            relationAndMultiple.setValue(5);
            // however we ask, we get the same result
            assertEquals(relationAndMultiple.getStyle(), this.realSheet.getStyleAt(relationAndMultiple.getX(), relationAndMultiple.getY()));
            // condition was evaluated
            assertEquals(headingStyle, relationAndMultiple.getStyle());
            // if we only use the name, the condition cannot be evaluated
            final String styleName = this.realSheet.getStyleNameAt(relationAndMultiple.getX(), relationAndMultiple.getY());
            assertEquals(elseStyle, Style.getStyle(this.realSheet.getSpreadSheet().getPackage(), CellStyle.class, styleName));

            relationAndMultiple.setValue(6);
            assertEquals(elseStyle, relationAndMultiple.getStyle());

            relationAndMultiple.setValue(15);
            assertEquals(heading1Style, relationAndMultiple.getStyle());
            relationAndMultiple.setValue(16);
            assertEquals(heading1Style, relationAndMultiple.getStyle());

            // test convertible value (values of different type of the relational operator)
            relationAndMultiple.setValue(TimeUtils.getTypeFactory().newDurationDayTime(true, 6, 0, 0, 0), ODValueType.TIME, true, true);
            assertEquals(elseStyle, relationAndMultiple.getStyle());
            relationAndMultiple.setValue(TimeUtils.getTypeFactory().newDurationDayTime(true, 16, 0, 0, 0), ODValueType.TIME, true, true);
            assertEquals(heading1Style, relationAndMultiple.getStyle());
            // test non convertible value
            relationAndMultiple.setValue("foo");
            assertEquals(elseStyle, relationAndMultiple.getStyle());

            // between c and f
            final char lowerChar = 'c', upperChar = 'f';
            final MutableCell<SpreadSheet> between = this.realSheet.getCellAt("J4");
            elseStyle = between.getStyle();
            assertFalse(elseStyle.equals(headingStyle) || elseStyle.equals(heading1Style));
            for (int i = lowerChar - 1; i <= upperChar + 1; i++) {
                between.setValue(String.valueOf((char) i));
                assertEquals(i >= lowerChar && i <= upperChar ? heading1Style : elseStyle, between.getStyle());
            }

            // not between 1 and 3
            final int lower = 1, upper = 3;
            final MutableCell<SpreadSheet> notBetween = this.realSheet.getCellAt("J5");
            elseStyle = notBetween.getStyle();
            assertFalse(elseStyle.equals(headingStyle) || elseStyle.equals(heading1Style));
            for (int i = lower - 1; i <= upper + 1; i++) {
                notBetween.setValue(i);
                assertEquals(i < lower || i > upper ? heading1Style : elseStyle, notBetween.getStyle());
            }

            // test convertible value (values of different type of the relational operator)
            notBetween.setValue(Boolean.FALSE);
            assertEquals(heading1Style, notBetween.getStyle());
            notBetween.setValue(Boolean.TRUE);
            assertEquals(elseStyle, notBetween.getStyle());
            // test non convertible value
            notBetween.setValue("foo");
            assertEquals(elseStyle, notBetween.getStyle());
        }

        // ATTN getStyle().getReferences() is low-level so if the style of a cell is not directly
        // referenced (i.e. it uses the default for its row/column) it won't get found
        final MutableCell<SpreadSheet> cellAt00 = this.realSheet.getCellAt(0, 0);
        assertNull(cellAt00.getStyleAttr());
        assertFalse(cellAt00.getStyle().getReferences().contains(cellAt00.getElement()));
        // unless getStyleReferences() is used
        assertTrue(this.realSheet.getStyleReferences(this.realSheet.getStyleNameAt(0, 0)).contains(Tuple2.create(0, 0)));

        {
            final MutableCell<SpreadSheet> b9Cell = this.realSheet.getCellAt("B9");
            final CellStyle centeredInBold = b9Cell.getStyle();
            assertTrue(centeredInBold.getReferences().contains(b9Cell.getElement()));

            assertEquals("center", centeredInBold.getParagraphProperties().getAlignment());
            assertEquals("bold", centeredInBold.getTextProperties().getWeight());
            final StyleTableCellProperties tableCellProperties = centeredInBold.getTableCellProperties(b9Cell);
            assertEquals("0.06pt solid #000000", tableCellProperties.getBorder(Side.LEFT));
            assertEquals(null, tableCellProperties.getBorderLineWidth(Side.LEFT));
            assertEquals(0, tableCellProperties.getRotationAngle());
            assertEquals(true, tableCellProperties.isContentPrinted());
            assertEquals(false, tableCellProperties.isContentRepeated());
            assertEquals(false, tableCellProperties.isShrinkToFit());
        }
        {
            final MutableCell<SpreadSheet> b6Cell = this.realSheet.getCellAt("B6");
            final CellStyle notPrinted = b6Cell.getStyle();
            assertTrue(notPrinted.getReferences().contains(b6Cell.getElement()));

            assertEquals("start", notPrinted.getParagraphProperties().getAlignment());
            assertEquals(null, notPrinted.getTextProperties().getWeight());
            final StyleTableCellProperties tableCellProperties = notPrinted.getTableCellProperties(b6Cell);
            assertEquals("9.01pt double #000080", tableCellProperties.getBorder(Side.RIGHT));
            assertEquals("none", tableCellProperties.getBorder(Side.TOP));
            assertEquals(asList("0.106cm", "0.106cm", "0.106cm"), asList(tableCellProperties.getBorderLineWidth(Side.RIGHT)));
            assertEquals(333, tableCellProperties.getRotationAngle());
            assertEquals(false, tableCellProperties.isContentPrinted());
            assertEquals(false, tableCellProperties.isContentRepeated());
            assertEquals(true, tableCellProperties.isShrinkToFit());
        }
        // assert that we don't crash if we ask for an attribute whose namespace is not in our
        // element (here "fo")
        final StyleStyleDesc<CellStyle> cellDesc = this.sheet.getStyleStyleDesc(CellStyle.class);
        assertNull(cellDesc.createAutoStyle(this.sheet.getSpreadSheet().getPackage()).getBackgroundColor(null));

        // column styles
        assertEquals(new Length(54.38, LengthUnit.MM), this.realSheet.getColumn(1).getWidth());
        final SpreadSheet sxc = new ODPackage(this.getClass().getResourceAsStream("test.sxc")).getSpreadSheet();
        assertEquals(new Length(33.85, LengthUnit.MM), sxc.getSheet(0).getColumn(1).getWidth());

        // change style
        {
            final Column<SpreadSheet> col1 = this.realSheet.getColumn(9);
            final Column<SpreadSheet> col2 = this.realSheet.getColumn(10);

            // even with shared style
            assertEquals(col1.getStyle().getName(), col2.getStyle().getName());
            checkStyleElements(col1, col2, true);
            assertNotNull(col1.getStyle().getTableColumnProperties().getBreakBefore());
            final Length initialWidth = col2.getWidth();
            final Length newWidth = initialWidth.add(Length.MM(15));
            // if we change the width of col1
            col1.setWidth(newWidth);
            // it only change that
            assertEquals(newWidth, col1.getWidth());
            assertEquals(initialWidth, col2.getWidth());
            // and nothing else
            assertEquals(col2.getStyle().getTableColumnProperties().getBreakBefore(), col1.getStyle().getTableColumnProperties().getBreakBefore());

            // new style is only created if needed
            final String newName = col1.getStyle().getName();
            assertFalse(newName.equals(col2.getStyle().getName()));
            checkStyleElements(col1, col2, false);
            col1.setWidth(newWidth.add(Length.MM(2)));
            assertEquals(newName, col1.getStyle().getName());

            // assert that getPrivateStyle() works even w/o a style
            final Table<?> addSheet = this.realSheet.getSpreadSheet().addSheet("testEmptyStyle");
            assertNull(addSheet.getColumn(0).getStyle());
            addSheet.getColumn(0).setWidth(Length.MM(20));
            final ColumnStyle createdStyle = addSheet.getColumn(0).getStyle();
            assertNotNull(createdStyle);
            assertEquals(createdStyle.getName(), addSheet.getColumn(0).getPrivateStyle().getName());
            assertEquals(Length.MM(20), addSheet.getColumn(0).getWidth());
        }
        // change shared style referenced by one XML element
        {
            // both cells have the same style
            assertEquals(null, this.sheet.getStyleNameAt(0, 0));
            assertEquals(null, this.sheet.getStyleNameAt(0, 1));

            // create a new cell style and set it as the default for the first column
            final CellStyle cellStyle = cellDesc.createAutoStyle(this.sheet.getSpreadSheet().getPackage(), "defaultCell");
            final Element col0Elem = this.sheet.getColumn(0).getElement();
            col0Elem.setAttribute("default-cell-style-name", cellStyle.getName(), col0Elem.getNamespace());
            // both cells have thus the new style
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 0));
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 1));
            // although the style is only referenced by the column
            assertEquals(Collections.singletonList(col0Elem), cellStyle.getReferences());

            // changing the background of the first cell from nothing to CYAN
            assertNull(cellStyle.getBackgroundColor(null));
            this.sheet.getCellAt(0, 0).setBackgroundColor(Color.CYAN);
            // imply that the first cell changes style
            final CellStyle newStyle = this.sheet.getStyleAt(0, 0);
            assertFalse(cellStyle.getName().equals(newStyle.getName()));
            // but not the second one
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 1));
            // the first cell has indeed changed colour
            assertEquals(Color.CYAN, this.sheet.getTableCellPropertiesAt(0, 0).getBackgroundColor());
            // but not the second one
            assertNull(this.sheet.getTableCellPropertiesAt(0, 1).getBackgroundColor());

            // changing a second time doesn't create a new style
            this.sheet.getCellAt(0, 0).setBackgroundColor(Color.YELLOW);
            assertEquals(newStyle.getName(), this.sheet.getStyleNameAt(0, 0));
        }

        // test default style
        {
            final CellStyle defaultCellStyle = Style.getStyleStyleDesc(CellStyle.class, this.realSheet.getODDocument().getVersion()).findDefaultStyle(this.realSheet.getODDocument().getPackage());
            assertEquals("Arial", defaultCellStyle.getTextProperties().getFontName());
            final CellStyle a1Style = this.realSheet.getCellAt(0, 0).getStyle();
            // even though the style doesn't contain the font name, it can find it in the default
            // style
            assertNull(a1Style.getTextProperties().getElement().getAttribute("font-name", a1Style.getElement().getNamespace("style")));
            assertEquals("Arial", a1Style.getTextProperties().getFontName());
        }

        // test creation
        {
            // no default style
            assertNull(cellDesc.getDefaultStyle(this.sheet.getODDocument().getPackage(), false));
            // the country is null
            final StyleTextProperties style00 = this.sheet.getImmutableCellAt(0, 0).getStyle().getTextProperties();
            assertNull(style00.getCountry());

            // create an empty default style, the country is still null
            final CellStyle defaultStyle = cellDesc.getDefaultStyle(this.sheet.getODDocument().getPackage(), true);
            assertNotNull(defaultStyle);
            assertNull(style00.getCountry());

            // now fill in the default style
            // ATTN LibreOffice ignores some default attributes (e.g. color, wrap, etc.)
            final Element element = defaultStyle.getTextProperties().getElement();
            element.setAttribute("country", "CH", element.getNamespace("fo"));
            element.setAttribute("language", "fr", element.getNamespace("fo"));
            // the cell uses it
            assertEquals("CH", style00.getCountry());
        }

        // test getParentStyle()
        {
            final CellStyle b3Style = this.realSheet.getStyleAt(1, 2);
            assertEquals("bold", b3Style.getTextProperties().getWeight());
            final CellStyle parentStyle = (CellStyle) b3Style.getParentStyle();
            assertEquals(b3Style.getFamily(), parentStyle.getFamily());
            assertEquals("Default", parentStyle.getName());
            try {
                b3Style.getParentStyle(null);
                fail("Null isn't valid");
            } catch (NullPointerException e) {
                // OK
            }
            // there's no condition on this style so passing the cell isn't needed
            assertEquals(parentStyle, b3Style.getParentStyle(this.realSheet.getCellAt(1, 2)));
            assertNull(parentStyle.getParentStyle());
        }

        assertValid(this.sheet.getSpreadSheet());
        assertValid(this.realSheet.getSpreadSheet());

        // test style resolution
        {
            final SpreadSheet calcDocument = SpreadSheet.create(1, 3, 3);
            final Sheet emptySheet = calcDocument.getSheet(0);
            final StyleStyleDesc<CellStyle> cellStyleDesc = emptySheet.getCellStyleDesc();

            final CellStyle defaultCellStyle = cellStyleDesc.getDefaultStyle(calcDocument.getPackage(), true);
            defaultCellStyle.getTableCellProperties(null).setRotationAngle(10);

            final CellStyle cellStyle1 = cellStyleDesc.createCommonStyle(calcDocument.getPackage(), "cellStyle1");
            cellStyle1.getTableCellProperties(null).setBackgroundColor(Color.RED);
            // keep the same background, but change the foreground
            final CellStyle subCellStyle1 = cellStyleDesc.createCommonStyle(calcDocument.getPackage(), "subCellStyle1");
            subCellStyle1.getElement().setAttribute("parent-style-name", "cellStyle1", subCellStyle1.getElement().getNamespace("style"));
            subCellStyle1.getTextProperties().setColor(Color.WHITE);

            final CellStyle cellStyle2 = cellStyleDesc.createCommonStyle(calcDocument.getPackage(), "cellStyle2");
            cellStyle2.getTableCellProperties(null).setBackgroundColor(Color.BLUE);
            cellStyle2.getTableCellProperties(null).setRotationAngle(90);

            final CellStyle cellStyle3 = cellStyleDesc.createCommonStyle(calcDocument.getPackage(), "cellStyle3");
            cellStyle3.getTableCellProperties(null).setBackgroundColor(Color.GREEN);
            cellStyle3.getTableCellProperties(null).setRotationAngle(45);

            // break repeated
            emptySheet.setValueAt("row&col", 0, 0);
            emptySheet.setValueAt("col", 0, 2);
            emptySheet.setValueAt("row", 2, 0);
            emptySheet.setValueAt("cell&row", 1, 0);
            emptySheet.setValueAt("cell", 1, 1);
            emptySheet.setValueAt("cell", 2, 2);
            emptySheet.setValueAt("Ø", 2, 1);
            emptySheet.setValueAt("Ø", 1, 2);

            emptySheet.getColumn(0).getElement().setAttribute("default-cell-style-name", "cellStyle3", emptySheet.getTABLE());
            emptySheet.getRow(0).getElement().setAttribute("default-cell-style-name", "cellStyle2", emptySheet.getTABLE());
            emptySheet.getCellAt(1, 0).setStyleName("cellStyle1");
            emptySheet.getCellAt(1, 1).setStyleName("cellStyle1");
            emptySheet.getCellAt(2, 2).setStyleName("subCellStyle1");

            assertValid(calcDocument);

            for (final boolean std : new boolean[] { true, false }) {
                Style.setStandardStyleResolution(std);

                // first available style is cell
                assertEquals("cellStyle1", emptySheet.getStyleNameAt(1, 1));
                assertEquals(Color.RED, emptySheet.getTableCellPropertiesAt(1, 1).getBackgroundColor());
                assertEquals(Color.BLACK, emptySheet.getStyleAt(1, 1).getTextProperties().getColor());
                // inherit from cellStyle1
                assertEquals("subCellStyle1", emptySheet.getStyleNameAt(2, 2));
                assertEquals(Color.RED, emptySheet.getTableCellPropertiesAt(2, 2).getBackgroundColor());
                assertEquals(Color.WHITE, emptySheet.getStyleAt(2, 2).getTextProperties().getColor());
                // use default-style
                assertEquals(std ? 10 : 0, emptySheet.getTableCellPropertiesAt(2, 2).getRotationAngle());

                // first available style is column
                assertEquals("cellStyle3", emptySheet.getStyleNameAt(0, 2));
                assertEquals(Color.GREEN, emptySheet.getTableCellPropertiesAt(0, 2).getBackgroundColor());
                assertEquals(45, emptySheet.getTableCellPropertiesAt(0, 2).getRotationAngle());

                if (std) {
                    // first available style is row
                    assertEquals("cellStyle2", emptySheet.getStyleNameAt(0, 0));
                    assertEquals(Color.BLUE, emptySheet.getTableCellPropertiesAt(0, 0).getBackgroundColor());

                    // since rotation isn't in the cell style (cellStyle1) search the next i.e. the
                    // row default cell style (cellStyle2)
                    assertEquals("cellStyle1", emptySheet.getStyleNameAt(1, 0));
                    assertEquals(Color.RED, emptySheet.getTableCellPropertiesAt(1, 0).getBackgroundColor());
                    assertEquals(90, emptySheet.getTableCellPropertiesAt(1, 0).getRotationAngle());

                    assertEquals("cellStyle2", emptySheet.getStyleNameAt(2, 0));
                    assertEquals(Color.BLUE, emptySheet.getTableCellPropertiesAt(2, 0).getBackgroundColor());
                } else {
                    // first available style is column
                    assertEquals("cellStyle3", emptySheet.getStyleNameAt(0, 0));
                    assertEquals(Color.GREEN, emptySheet.getTableCellPropertiesAt(0, 0).getBackgroundColor());

                    // since rotation isn't in the cell style (cellStyle1) LO uses the default value
                    // (*not* from the default style, which is 10 in that document)
                    assertEquals("cellStyle1", emptySheet.getStyleNameAt(1, 0));
                    assertEquals(Color.RED, emptySheet.getTableCellPropertiesAt(1, 0).getBackgroundColor());
                    assertEquals(0, emptySheet.getTableCellPropertiesAt(1, 0).getRotationAngle());

                    // nothing
                    assertEquals(null, emptySheet.getStyleNameAt(2, 0));
                    assertEquals(null, emptySheet.getTableCellPropertiesAt(2, 0));
                }
            }
        }
    }

    private void checkStyleElements(final Column<SpreadSheet> col1, final Column<SpreadSheet> col2, final boolean same) {
        final Attribute col1StyleAttr = col1.getElement().getAttribute("style-name", col1.getElement().getNamespace());
        assertSame(col1.getStyle().getElement(), Style.getReferencedStyleElement(this.realSheet.getSpreadSheet().getPackage(), col1StyleAttr));
        final Attribute col2StyleAttr = col2.getElement().getAttribute("style-name", col2.getElement().getNamespace());
        assertSame(col2.getStyle().getElement(), Style.getReferencedStyleElement(this.realSheet.getSpreadSheet().getPackage(), col2StyleAttr));

        if (same)
            assertSame(col1.getStyle().getElement(), col2.getStyle().getElement());
        else
            assertNotSame(col1.getStyle().getElement(), col2.getStyle().getElement());
    }

    private void assertValid(SpreadSheet ssheet) {
        assertValid(ssheet.getPackage());
    }

    static public void assertValid(final ODPackage pkg) {
        assertValid(pkg, true);
    }

    static public void assertValid(final ODPackage pkg, final boolean expected) {
        final Map<String, String> areSubDocumentsValid = pkg.validateSubDocuments();
        assertEquals(areSubDocumentsValid + "", expected, areSubDocumentsValid.isEmpty());

        final String checkStyles = pkg.checkStyles();
        assertNull(checkStyles, checkStyles);
    }

    private void assertValid(Validator val) {
        assertValid(val, true);
    }

    private void assertValid(Validator val, final boolean expected) {
        final String isValid = val.isValid();
        if (expected)
            assertNull(isValid, isValid);
        else
            assertNotNull(isValid, isValid);
    }
}
