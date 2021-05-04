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

package org.jopendocument.io;

import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.draw.DrawFrame;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.draw.DrawTextBox;
import org.jopendocument.model.number.NumberDateStyle;
import org.jopendocument.model.number.NumberDay;
import org.jopendocument.model.number.NumberMonth;
import org.jopendocument.model.number.NumberNumberStyle;
import org.jopendocument.model.number.NumberText;
import org.jopendocument.model.number.NumberYear;
import org.jopendocument.model.office.OfficeAutomaticStyles;
import org.jopendocument.model.office.OfficeBody;
import org.jopendocument.model.office.OfficeScripts;
import org.jopendocument.model.office.OfficeSpreadsheet;
import org.jopendocument.model.office.OfficeText;
import org.jopendocument.model.script.StyleGraphicProperties;
import org.jopendocument.model.style.StyleColumns;
import org.jopendocument.model.style.StyleFontFace;
import org.jopendocument.model.style.StyleListLevelProperties;
import org.jopendocument.model.style.StyleParagraphProperties;
import org.jopendocument.model.style.StyleSectionProperties;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTabStop;
import org.jopendocument.model.style.StyleTabStops;
import org.jopendocument.model.style.StyleTableCellProperties;
import org.jopendocument.model.style.StyleTableColumnProperties;
import org.jopendocument.model.style.StyleTableRowProperties;
import org.jopendocument.model.style.StyleTextProperties;
import org.jopendocument.model.table.TableShapes;
import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.table.TableTableCell;
import org.jopendocument.model.table.TableTableColumn;
import org.jopendocument.model.table.TableTableHeaderColumns;
import org.jopendocument.model.table.TableTableRow;
import org.jopendocument.model.text.TextA;
import org.jopendocument.model.text.TextBookmark;
import org.jopendocument.model.text.TextBookmarkEnd;
import org.jopendocument.model.text.TextDate;
import org.jopendocument.model.text.TextH;
import org.jopendocument.model.text.TextIndexBody;
import org.jopendocument.model.text.TextLineBreak;
import org.jopendocument.model.text.TextList;
import org.jopendocument.model.text.TextListItem;
import org.jopendocument.model.text.TextListLevelStyleBullet;
import org.jopendocument.model.text.TextListLevelStyleNumber;
import org.jopendocument.model.text.TextListStyle;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextS;
import org.jopendocument.model.text.TextSoftPageBreak;
import org.jopendocument.model.text.TextSpan;
import org.jopendocument.model.text.TextTab;
import org.jopendocument.model.text.TextTableOfContent;
import org.jopendocument.util.Log;
import org.jopendocument.util.ValueHelper;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SaxContentUnmarshaller extends DefaultHandler {
    static void log(final String s) {
        log(s, true);
    }

    static void log(final String s, final boolean printStack) {
        Log.get().fine(s);
        if (printStack)
            Log.get().log(Level.FINE, null, new Exception("dump stack"));
    }

    private OfficeAutomaticStyles autostyles;

    private OfficeBody body;

    private Object current;

    private final OpenDocument document;

    private FontFaceDecls fontDeclarations;

    private OfficeScripts scripts;

    private final Stack<Object> stack;

    // -----

    public SaxContentUnmarshaller(final OpenDocument openDocument) {
        this.document = openDocument;
        this.stack = new Stack<Object>();
    }

    // ----- callbacks: -----

    private void assertParsed(final Attributes attribs, final int l) {
        if (attribs.getLength() > l) {
            for (int i = 0; i < attribs.getLength(); i++) {
                log(attribs.getQName(i) + "  -> " + attribs.getValue(i), false);
            }
            throw new IllegalStateException("Somme attributes are not parsed");
        }
    }

    public void characters(final char[] data, final int start, final int length) {
        final StringBuffer s = new StringBuffer();
        s.append(data, start, length);
        if (this.current instanceof TextP) {
            ((TextP) this.current).addToLastTextSpan(s.toString());
        } else if (this.current instanceof TextSpan) {
            ((TextSpan) this.current).concatValue(s.toString());
        } else if (this.current instanceof NumberText) {
            ((NumberText) this.current).concatValue(s.toString());
        } else if (this.current instanceof TextA) {
            ((TextA) this.current).concatValue(s.toString());
        } else if (this.current instanceof TextH) {
            ((TextH) this.current).concatValue(s.toString());
        } else if (this.current instanceof TextDate) {
            ((TextDate) this.current).concatValue(s.toString());
        } else {
            if (s.length() > 0) {
                log("Error : " + this.current + ":" + this.current.getClass() + " ?'" + s.toString() + "'");
            }
        }
    }

    // -----

    private static void dumpAttributes(final Attributes attribs) {
        log("Dump attributes:", false);
        for (int i = 0; i < attribs.getLength(); i++) {
            log("'" + attribs.getQName(i) + "'  -> '" + attribs.getValue(i) + "'", false);
        }

    }

    // -----

    public void dumpAutoStyles() {
        final List<StyleStyle> l = this.autostyles.getStyles();
        for (final StyleStyle style : l) {
            System.out.println(style);
        }
    }

    public void dumpSpreadSheets() {
        final List<OfficeSpreadsheet> l = this.body.getOfficeSpreadsheets();
        for (final OfficeSpreadsheet sheet : l) {
            System.out.println(sheet);
            final List<TableTable> tables = sheet.getTables();
            for (final TableTable table : tables) {
                dumpRows(table);

                final List<TableTableColumn> cols = table.getColumnsInRange(table.getPrintStartCol(), table.getPrintStopCol());
                for (final TableTableColumn col : cols) {
                    System.out.println(col.getWidth());
                }

            }
        }

    }

    private void dumpRows(final TableTable table) {
        for (final TableTableRow r : table.getRows()) {

            final TableTableCell[] cells = r.getCellsInRange(table.getPrintStartCol(), table.getPrintStopCol());
            for (final TableTableCell cell : cells) {

                if (cell.getTextP() != null) {
                    final List<TextP> text = cell.getTextP();
                    for (TextP textP : text) {
                        final List<TextSpan> lt = textP.getTextSpans();
                        System.out.println("TextP:" + lt);
                    }

                }
            }
        }
    }

    public void dumpSpreadSheetsRows() {
        final List<OfficeSpreadsheet> l = this.body.getOfficeSpreadsheets();
        for (final OfficeSpreadsheet sheet : l) {
            System.out.println(sheet);
            final List<TableTable> tables = sheet.getTables();
            for (final TableTable table : tables) {
                System.out.println("Table ===================");
                for (final TableTableRow r : table.getRows()) {
                    System.out.println(r.getText() + " repeated:" + r.getTableNumberRowsRepeated());

                }

            }
        }

    }

    public void endElement(final String uri, final String localName, final String qName) {
        if (this.current instanceof TableTable) {
            ((TableTable) this.current).completed();
        } else if (this.current instanceof TextSpan) {
            ((TextSpan) this.current).completed();
        }
        this.pop();
    }

    public OfficeAutomaticStyles getAutomaticstyles() {
        return this.autostyles;
    }

    // -----

    public OfficeBody getBody() {
        return this.body;
    }

    // -----

    private void pop() {

        if (!this.stack.isEmpty()) {
            Object o = this.stack.pop();
            // log("POP :" + o);
        }
        if (!this.stack.isEmpty()) {
            this.current = this.stack.peek();
        }
    }

    private void push(final Object o) {
        this.stack.push(o);
        this.current = o;

    }

    public void startElement(final String uri, final String localName, final String qName, final Attributes attribs) {
        if (qName.equals("table:table-cell") || qName.equals("table:covered-table-cell")) {
            final TableTableCell cell = new TableTableCell();
            cell.setTableStyleName(attribs.getValue("table:style-name"));
            cell.setTableNumberColumnsRepeated(attribs.getValue("table:number-columns-repeated"));
            cell.setTableNumberColumnsSpanned(attribs.getValue("table:number-columns-spanned"));
            cell.setTableNumberRowsSpanned(attribs.getValue("table:number-rows-spanned"));
            cell.setTableValueType(attribs.getValue("office:value-type"));
            if (qName.equals("table:covered-table-cell")) {
                cell.setCovered(true);
            }

            if (this.current instanceof TableTableRow) {
                ((TableTableRow) this.current).addCell(cell);
            } else {
                log("Not TableTableRow:" + this.current);
            }
            this.push(cell);

        } else if (qName.equals("text:p")) {
            final TextP p = new TextP();
            if (this.current instanceof TableTableCell) {
                ((TableTableCell) this.current).addTextP(p);
            } else if (this.current instanceof DrawImage) {
                ((DrawImage) this.current).setTextP(p);
            } else if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(p);
            } else if (this.current instanceof DrawTextBox) {
                ((DrawTextBox) this.current).addTextElement(p);
            } else if (this.current instanceof TextIndexBody) {
                ((TextIndexBody) this.current).addTextElement(p);
            } else if (this.current instanceof TextListItem) {
                ((TextListItem) this.current).addTextElement(p);
            } else {
                log("Not TableTableCell:" + this.current + " classe:" + this.current.getClass());
            }
            this.push(p);

        } else if (qName.equals("text:span")) {
            final TextSpan textspan = new TextSpan();
            textspan.setTextStyle(this.document.getStyle(attribs.getValue("text:style-name"), "text"));

            if (this.current instanceof TextP) {
                ((TextP) this.current).addTextSpan(textspan);
            } else if (this.current instanceof TextSpan) {
                ((TextSpan) this.current).addTextSpan(textspan);
            } else if (this.current instanceof TextA) {
                ((TextA) this.current).addTextSpan(textspan);
            } else {
                log("Not TextP:" + this.current + " " + textspan.getTextStyle());
            }
            this.push(textspan);

        } else if (qName.equals("office:automatic-styles")) {
            this.autostyles = new OfficeAutomaticStyles();
            this.document.setAutomaticStyles(this.autostyles);
            this.push(this.autostyles);
        } else if (qName.equals("style:style")) {
            final StyleStyle style = new StyleStyle();
            style.setStyleName(attribs.getValue("style:name"));
            style.setStyleFamily(attribs.getValue("style:family"));
            style.setStyleParentStyleName(attribs.getValue("style:parent-style-name"));
            style.setMasterPageName(attribs.getValue("style:master-page-name"));

            // style:data-style-name="N108"
            if (this.current instanceof OfficeAutomaticStyles) {
                this.autostyles.addStyle(style);
                this.document.getStyleResolver().add(style);
            } else {
                log("Not OfficeAutomaticStyles: " + this.current + " style:" + style.getStyleName());
                dumpStack();
            }
            this.push(style);
        } else if (qName.equals("number:number-style")) {

            final NumberNumberStyle style = new NumberNumberStyle();
            style.setStyleName(attribs.getValue("style:name"));
            style.setStyleFamily(attribs.getValue("style:family"));

            // style:data-style-name="N108"
            if (this.current instanceof OfficeAutomaticStyles) {
                this.autostyles.addStyle(style);
            } else {
                log("Not OfficeAutomaticStyles:" + this.current);
            }
            this.push(style);
        } else if (qName.equals("style:table-row-properties")) {
            final StyleTableRowProperties props = new StyleTableRowProperties();
            props.setFoBreakBefore(attribs.getValue("fo:break-before"));
            props.setRowHeight(attribs.getValue("style:row-height"));
            props.setUseOptimalRowHeight(attribs.getValue("style:use-optimal-row-height"));
            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableRowProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:table-properties")) {
            final StyleTableProperties props = new StyleTableProperties();
            if (attribs.getValue("table:display") != null) {
                props.setDisplay(ValueHelper.getBoolean(attribs.getValue("table:display")));
            }
            props.setWritingMode(attribs.getValue("style:writing-mode"));

            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:table-cell-properties")) {
            final StyleTableCellProperties props = new StyleTableCellProperties();
            props.setVerticalAlign(attribs.getValue("style:vertical-align"));
            props.setBackgroundColor(attribs.getValue("fo:background-color"));

            props.setPadding(attribs.getValue("fo:padding"));

            props.setTextAlignSource(attribs.getValue("style:text-align-source"));
            props.setRepeatContent(attribs.getValue("style:repeat-content"));

            props.setBorderLeft(attribs.getValue("fo:border-left"));
            props.setBorderRight(attribs.getValue("fo:border-right"));

            props.setBorderTop(attribs.getValue("fo:border-top"));
            props.setBorderBottom(attribs.getValue("fo:border-bottom"));
            // doit etre apres pour overrider le border!
            props.setBorder(attribs.getValue("fo:border"));
            props.setWrapOption(attribs.getValue("fo:wrap-option"));
            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableCellProperties(props);

            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:text-properties")) {
            final StyleTextProperties props = StyleTextProperties.getStyleTextProperties(attribs.getValue("style:font-name"), attribs.getValue("fo:font-size"), attribs.getValue("fo:font-weight"),
                    attribs.getValue("fo:font-style"), attribs.getValue("fo:color"));

            // fo:hyphenate="true"
            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTextProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);

        } else if (qName.equals("style:background-image")) {

            if (this.current instanceof StyleTableCellProperties) {
                ((StyleTableCellProperties) this.current).setBackgroundImage(true);
            } else if (this.current instanceof StyleSectionProperties) {
                ((StyleSectionProperties) this.current).setBackgroundImage(true);
            } else {
                log("Not StyleTableCellProperties:" + this.current);
            }
            this.push(new UnusedElement("style:background-image"));
        } else if (qName.equals("style:table-column-properties")) {
            final StyleTableColumnProperties props = new StyleTableColumnProperties();
            props.setFoBreakBefore(attribs.getValue("fo:break-before"));
            props.setStyleColumnWidth(attribs.getValue("style:column-width"));

            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableColumnProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:paragraph-properties")) {
            final StyleParagraphProperties props = new StyleParagraphProperties();
            props.setTextAlign(attribs.getValue("fo:text-align"));
            props.setMarginLeft(attribs.getValue("fo:margin-left"));

            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setParagraphProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("office:body")) {
            this.body = new OfficeBody();
            this.push(this.body);
        } else if (qName.equals("office:spreadsheet")) {
            final OfficeSpreadsheet spread = new OfficeSpreadsheet();
            if (this.current instanceof OfficeBody) {
                ((OfficeBody) this.current).addOfficeSpreadsheet(spread);
            } else {
                log("Not StyleStyle:" + this.current);
            }

            this.push(spread);

        } else if (qName.equals("table:table")) {
            final TableTable table = new TableTable();
            dumpAttributes(attribs);

            table.setTableName(attribs.getValue("table:name"));
            table.setTableStyleName(attribs.getValue("table:style-name"));
            final String printranges = attribs.getValue("table:print-ranges");
            if (printranges != null) {
                table.setTablePrintRanges(printranges);
            }
            this.assertParsed(attribs, 3);
            if (this.current instanceof OfficeSpreadsheet) {
                ((OfficeSpreadsheet) this.current).addTable(table);
            } else if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(table);
            } else {
                log("Not OfficeSpreadsheet:" + this.current);
            }
            this.push(table);

        } else if (qName.equals("table:table-column")) {
            final TableTableColumn col = new TableTableColumn();
            col.setTableStyleName(attribs.getValue("table:style-name"));
            col.setTableDefaultCellStyleName(attribs.getValue("table:default-cell-style-name"));
            col.setTableNumberColumnsRepeated(attribs.getValue("table:number-columns-repeated"));

            this.assertParsed(attribs, 3);
            if (this.current instanceof TableTable) {
                ((TableTable) this.current).addColumn(col);
            } else if (this.current instanceof TableTableHeaderColumns) {
                // FIXME Doit fixer le pb de ColumnHeader
                ((TableTable) this.stack.get(this.stack.size() - 2)).addColumn(col);
            } else {
                log("Not TableTable:" + this.current + " class:" + this.current.getClass().getName());
                dumpStack();
            }
            this.push(col);

        } else if (qName.equals("table:table-row")) {

            final TableTableRow row = new TableTableRow();
            row.setTableStyleName(attribs.getValue("table:style-name"));
            row.setTableNumberRowsRepeated(attribs.getValue("table:number-rows-repeated"));

            if (this.current instanceof TableTable) {
                ((TableTable) this.current).addRow(row);
            } else {
                log("Not TableTable:" + this.current);
                dumpStack();
            }
            this.push(row);

        } else if (qName.equals("draw:frame")) {
            final DrawFrame p = new DrawFrame();
            p.setSvgWidth(attribs.getValue("svg:width"));
            p.setSvgHeight(attribs.getValue("svg:height"));
            p.setSvgX(attribs.getValue("svg:x"));
            p.setSvgY(attribs.getValue("svg:y"));

            if (this.current instanceof TableTableCell) {
                ((TableTableCell) this.current).addDrawFrame(p);
            } else if (this.current instanceof TableShapes) {
                ((TableShapes) this.current).addDrawFrame(p);
            } else if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(p);
            } else if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(p);
            } else {
                log("Not TableTableCell:" + this.current + " " + this.current.getClass());
            }
            this.push(p);

        } else if (qName.equals("draw:image")) {
            final DrawImage p = new DrawImage();
            final String link = attribs.getValue("xlink:href");
            p.setXlinkHref(link);
            this.document.preloadImage(link);
            if (this.current instanceof DrawFrame) {
                ((DrawFrame) this.current).setDrawImage(p);
            } else {
                log("Not DrawFrame:" + this.current);
            }
            this.push(p);

        } else if (qName.equals("table:shapes")) {
            final TableShapes p = new TableShapes();

            if (this.current instanceof TableTable) {
                ((TableTable) this.current).setTableShapes(p);
            } else {
                log("Not TableTable:" + this.current);
            }
            this.push(p);

        } else if (qName.equals("office:scripts")) {
            this.scripts = new OfficeScripts();

            this.push(this.scripts);

        } else if (qName.equals("table:table-header-columns")) {
            TableTableHeaderColumns h = new TableTableHeaderColumns();
            this.push(h);
        } else if (qName.equals("office:font-face-decls")) {
            this.fontDeclarations = new FontFaceDecls();

            this.push(this.fontDeclarations);

        } else if (qName.equals("style:font-face")) {
            final StyleFontFace p = new StyleFontFace();
            p.setStyleName(attribs.getValue("style:name"));
            p.setFontFamily(attribs.getValue("svg:font-family"));
            p.setFontFamilyGeneric(attribs.getValue("style:font-family-generic"));
            p.setFontPitch(attribs.getValue("style:font-pitch"));

            if (this.current instanceof FontFaceDecls) {
                ((FontFaceDecls) this.current).addFontFace(p);
            } else {
                log("Not FontFaceDecls:" + this.current);
            }
            this.push(p);

        } else if (qName.equals("style:tab-stops")) {
            final StyleTabStops p = new StyleTabStops();

            if (this.current instanceof StyleParagraphProperties) {
                ((StyleParagraphProperties) this.current).addTabStops(p);
            } else {
                log("StyleParagraphProperties:" + this.current);
            }
            this.push(p);

        } else if (qName.equals("style:tab-stop")) {
            final StyleTabStop ts = new StyleTabStop();
            ts.setStylePosition(attribs.getValue("style:position"));
            if (this.current instanceof StyleTabStops) {
                ((StyleTabStops) this.current).add(ts);
            } else {
                log("StyleTabStops:" + this.current);
            }
            this.push(ts);

        } else if (qName.equals("style:graphic-properties")) {
            final StyleGraphicProperties props = new StyleGraphicProperties();
            props.setMarginLeft(attribs.getValue("fo:margin-left"));
            props.setMarginRight(attribs.getValue("fo:margin-right"));
            props.setMarginTop(attribs.getValue("fo:margin-top"));
            props.setMarginBottom(attribs.getValue("fo:margin-bottom"));
            props.setProtection(attribs.getValue("style:protect"));
            props.setWrap(attribs.getValue("style:wrap"));
            props.setNumberWrappedParagraphs(attribs.getValue("style:number-wrapped-paragraphs"));
            props.setVerticalPosition(attribs.getValue("style:vertical-pos"));
            props.setVerticalRelative(attribs.getValue("style:vertical-rel"));
            props.setHorizontalPosition(attribs.getValue("style:horizontal-pos"));
            props.setHorizontalRelative(attribs.getValue("style:horizontal-rel"));
            props.setPadding(attribs.getValue("fo:padding"));
            props.setBorder(attribs.getValue("fo:border"));
            props.setShadow(attribs.getValue("style:shadow"));

            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setGraphicProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);

        } else if (qName.equals("style:section-properties")) {

            final StyleSectionProperties sp = new StyleSectionProperties();
            sp.setBackGroundColor(ValueHelper.getColor(attribs.getValue("fo:background-color")));
            sp.setEditable(ValueHelper.getBoolean(attribs.getValue("style:editable")));

            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setSectionProperties(sp);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(sp);

        } else if (qName.equals("style:columns")) {
            final StyleColumns sc = new StyleColumns();
            sc.setFoColumnGap("fo:column-gap");
            sc.setFoColumnCount("fo:column-count");
            if (this.current instanceof StyleGraphicProperties) {
                ((StyleGraphicProperties) this.current).setColums(sc);
            } else if (this.current instanceof StyleSectionProperties) {
                ((StyleSectionProperties) this.current).setColums(sc);
            } else {
                log("Not StyleSectionProperties: " + this.current);
            }
            this.push(sc);

        } else if (qName.equals("text:list-style")) {
            // /////////
            final TextListStyle tls = new TextListStyle();
            tls.setStyleName(attribs.getValue("style:name"));
            if (this.current instanceof OfficeAutomaticStyles) {
                ((OfficeAutomaticStyles) this.current).addStyle(tls);
            } else {
                log("Not OfficeAutomaticStyles: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("text:list-level-style-bullet")) {
            // /////////
            final TextListLevelStyleBullet tls = new TextListLevelStyleBullet();
            tls.setTextLevel(attribs.getValue("text:level"));
            tls.setTextStyleName(attribs.getValue("text:style-name"));
            tls.setStyleNumSuffix(attribs.getValue("style:num-suffix"));
            tls.setStyleNumPrefix(attribs.getValue("style:num-prefix"));
            tls.setTextBulletChar(attribs.getValue("text:bullet-char"));
            if (this.current instanceof TextListStyle) {
                ((TextListStyle) this.current).addListLevelStyleBullet(tls);
            } else {
                log("Not TextListStyle: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("text:list-level-style-number")) {
            // /////////
            final TextListLevelStyleNumber tls = new TextListLevelStyleNumber();
            tls.setTextLevel(attribs.getValue("text:level"));
            tls.setTextStyleName(attribs.getValue("text:style-name"));
            tls.setStyleNumSuffix(attribs.getValue("style:num-suffix"));
            tls.setStyleNumPrefix(attribs.getValue("style:num-prefix"));
            tls.setStyleNumFormat(attribs.getValue("style:num-format"));
            tls.setTextStartValue(attribs.getValue("text:start-value"));

            if (this.current instanceof TextListStyle) {
                ((TextListStyle) this.current).addListLevelStyleNumber(tls);
            } else {
                log("Not TextListStyle: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("style:list-level-properties")) {
            final StyleListLevelProperties tls = new StyleListLevelProperties();
            tls.setSpaceBefore(attribs.getValue("text:space-before"));
            tls.setMinLabelWidth(attribs.getValue("text:min-label-width"));
            if (this.current instanceof TextListLevelStyleBullet) {
                ((TextListLevelStyleBullet) this.current).setStyleListLevelProperties(tls);
            } else if (this.current instanceof TextListLevelStyleNumber) {
                ((TextListLevelStyleNumber) this.current).setStyleListLevelProperties(tls);
            } else {
                log("Not TextListLevelStyleBullet: " + this.current);
            }
            this.push(tls);
        } else if (qName.equals("office:text")) {

            final OfficeText tls = new OfficeText();
            tls.setUseSoftPageBreaks(ValueHelper.getBoolean(attribs.getValue("text:use-soft-page-breaks")));

            if (this.current instanceof OfficeBody) {
                ((OfficeBody) this.current).setOfficeText(tls);
            } else {
                log("Not OfficeBody: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("draw:text-box")) {

            final DrawTextBox textBox = new DrawTextBox();

            if (this.current instanceof DrawFrame) {
                ((DrawFrame) this.current).addTextBox(textBox);
            } else {
                log("Not DrawFrame: " + this.current);
            }
            this.push(textBox);

        } else if (qName.equals("text:table-of-content")) {
            TextTableOfContent ttoc = new TextTableOfContent();
            ttoc.setTextStyleName(attribs.getValue("text:style-name"));
            ttoc.setTextProtected(attribs.getValue("text:protected"));
            ttoc.setTextName(attribs.getValue("text:name"));
            if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(ttoc);
            } else {
                log("Not OfficeText: " + this.current);
            }
            this.push(ttoc);

        }

        else if (qName.equals("text:index-body")) {

            final TextIndexBody textBox = new TextIndexBody();

            if (this.current instanceof TextTableOfContent) {
                ((TextTableOfContent) this.current).setTextIndexBody(textBox);
            } else {
                log("Not TextTableOfContent: " + this.current);
            }
            this.push(textBox);

        } else if (qName.equals("text:tab")) {
            final TextTab tab = new TextTab();

            if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(tab);
            } else {
                log("Not TextP: " + this.current);
            }
            this.push(tab);
        } else if (qName.equals("text:a")) {
            final TextA link = new TextA();
            link.setXlinkType(attribs.getValue("xlink:type"));
            link.setXlinkHref(attribs.getValue("xlink:href"));
            link.setTextStyleName(attribs.getValue("text:style-name"));
            link.setTextVisitedStyleName(attribs.getValue("text:visited-style-name"));
            if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(link);
            } else {
                log("Not TextP: " + this.current);
            }
            this.push(link);

        } else if (qName.equals("text:h")) {
            final TextH link = new TextH();

            link.setTextStyleName(attribs.getValue("text:style-name"));
            link.setTextLevel(attribs.getValue("text:outline-level"));
            if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(link);
            } else if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(link);
            } else {
                log("Not TextP: " + this.current);
            }
            this.push(link);

        } else if (qName.equals("text:date")) {
            final TextDate date = new TextDate();
            date.setStyleDataStyleName(attribs.getValue("style:data-style-name"));
            date.setTextDateValue(attribs.getValue("text:date-value"));
            date.setTextFixed(attribs.getValue("text:fixed"));

            if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(date);
            } else {
                log("Not TextP: " + this.current);
            }
            this.push(date);

        } else if (qName.equals("text:list")) {
            final TextList list = new TextList();
            list.setId(attribs.getValue("xml:id"));
            list.setTextStyleName(attribs.getValue("text:style-name"));
            if (this.current instanceof OfficeText) {
                ((OfficeText) this.current).addTextElement(list);
            } else {
                log("Not OfficeText: " + this.current);
            }
            this.push(list);

        } else if (qName.equals("text:list-item")) {
            final TextListItem item = new TextListItem();

            if (this.current instanceof TextList) {
                ((TextList) this.current).addListItem(item);
            } else {
                log("Not TextList: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("text:line-break")) {
            final TextLineBreak item = new TextLineBreak();

            if (this.current instanceof TextSpan) {
                ((TextSpan) this.current).addTextElement(item);
            } else if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(item);
            } else {
                log("Not TextSpan: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("text:soft-page-break")) {
            final TextSoftPageBreak item = new TextSoftPageBreak();

            if (this.current instanceof TextSpan) {
                ((TextSpan) this.current).addTextElement(item);
            } else if (this.current instanceof TextP) {
                ((TextP) this.current).addElement(item);
            } else if (this.current instanceof TextH) {
                ((TextH) this.current).addElement(item);
            } else {
                log("Not TextSpan: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("text:bookmark")) {
            final TextBookmark item = new TextBookmark();
            item.setTextName(attribs.getValue("text:name"));

            if (this.current instanceof TextH) {
                ((TextH) this.current).addElement(item);
            } else {
                log("Not TextH: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("text:bookmark-end")) {
            final TextBookmarkEnd item = new TextBookmarkEnd();
            item.setTextName(attribs.getValue("text:name"));

            if (this.current instanceof TextH) {
                ((TextH) this.current).addElement(item);
            } else {
                log("Not TextH: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("number:date-style")) {
            final NumberDateStyle item = new NumberDateStyle();
            item.setStyleName(attribs.getValue("style:name"));
            item.setNumberAutomaticOrder(attribs.getValue("number:automatic-order"));
            if (this.current instanceof OfficeAutomaticStyles) {
                ((OfficeAutomaticStyles) this.current).addStyle(item);
            } else {
                log("Not OfficeAutomaticStyles: " + this.current);
            }
            this.push(item);

        } else if (qName.equals("number:day")) {
            final NumberDay item = new NumberDay();
            if (this.current instanceof NumberDateStyle) {
                ((NumberDateStyle) this.current).addElement(item);
            } else {
                log("Not NumberDateStyle: " + this.current);
            }
            this.push(item);
        } else if (qName.equals("number:text")) {
            final NumberText item = new NumberText();
            if (this.current instanceof NumberDateStyle) {
                ((NumberDateStyle) this.current).addElement(item);
            } else {
                log("Not NumberDateStyle: " + this.current);
            }
            this.push(item);
        } else if (qName.equals("number:month")) {
            final NumberMonth item = new NumberMonth();
            item.setNumberTextual(attribs.getValue("number:textual"));
            if (this.current instanceof NumberDateStyle) {
                ((NumberDateStyle) this.current).addElement(item);
            } else {
                log("Not NumberDateStyle: " + this.current);
            }
            this.push(item);
        } else if (qName.equals("number:year")) {
            final NumberYear item = new NumberYear();
            if (this.current instanceof NumberDateStyle) {
                ((NumberDateStyle) this.current).addElement(item);
            } else {
                log("Not NumberDateStyle: " + this.current);
            }
            this.push(item);
        } else if (qName.equals("text:s")) {
            final TextS item = new TextS();
            item.setTextC(attribs.getValue("text:c"));

            final int spaceCount = item.getSpaceCount();
            final StringBuffer b = new StringBuffer(spaceCount);
            for (int i = 0; i < spaceCount; i++) {
                b.append(' ');
            }
            if (this.current instanceof TextP) {
                ((TextP) this.current).addToLastTextSpan(b.toString());
            } else {
                log("Not TextP: " + this.current);
            }
            this.push(item);
        }

        else {

            log("content.xml : ignoring :" + qName + " current:" + current, false);
            this.push(qName + "/" + uri);

        }
    }

    private void dumpStack() {
        log("Stack:", false);
        for (int i = 0; i < this.stack.size(); i++) {
            log("Item " + i + " : " + this.stack.get(i).getClass().getCanonicalName() + " : " + this.stack.get(i), false);
        }
        System.err.flush();
    }

}
