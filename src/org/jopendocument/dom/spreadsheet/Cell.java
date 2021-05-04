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
 * Cell created on 10 décembre 2005
 */
package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODNodeDesc.Children;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.text.Paragraph;
import org.jopendocument.dom.text.TextNode;
import org.jopendocument.dom.text.TextNodeDesc;
import org.jopendocument.util.Tuple2.List2;
import org.jopendocument.util.Tuple3;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Verifier;

/**
 * A cell in a calc document. If you want to change a cell value you must obtain a MutableCell.
 * 
 * @author Sylvain
 * @param <D> type of document
 */
public class Cell<D extends ODDocument> extends TableCalcNode<CellStyle, D> {

    private static boolean OO_MODE = true;

    // from §5.12 of OpenDocument-v1.2-cs01-part2
    // Error ::= '#' [A-Z0-9]+ ([!?] | ('/' ([A-Z] | ([0-9] [!?]))))
    // we added an optional space before the marks to support OpenOffice/LibreOffice (at least until
    // 3.4)
    private static final Pattern ErrorPattern = Pattern.compile("#[A-Z0-9]+( ?[!?]|(/([A-Z]|([0-9] ?[!?]))))");

    /**
     * Set whether {@link #getTextValue()} parses strings using the standard way or using the
     * OpenOffice.org way.
     * 
     * @param ooMode <code>true</code> if strings should be parsed the OO way.
     * @see #getTextValue(boolean)
     */
    public static void setTextValueMode(boolean ooMode) {
        OO_MODE = ooMode;
    }

    public static boolean getTextValueMode() {
        return OO_MODE;
    }

    static final Element createEmpty(XMLVersion ns) {
        return createEmpty(ns, 1);
    }

    static final Element createEmpty(XMLVersion ns, int count) {
        final Element e = new Element("table-cell", ns.getTABLE());
        if (count > 1)
            e.setAttribute("number-columns-repeated", count + "", ns.getTABLE());
        return e;
    }

    static public final List2<String> getFormulaNSPrefix(final String formula) {
        final int colonIndex = formula.indexOf(':');
        if (colonIndex > 0) {
            final String ncName = formula.substring(0, colonIndex);
            if (Verifier.checkXMLName(ncName) == null) {
                // +1 : remove colon
                return new List2<String>(ncName, formula.substring(colonIndex + 1));
            }
        }
        return new List2<String>(null, formula);
    }

    private final Row<D> row;

    Cell(Row<D> parent, Element elem, StyleDesc<CellStyle> styleDesc) {
        super(parent.getODDocument(), elem, styleDesc);
        this.row = parent;
    }

    protected final Row<D> getRow() {
        return this.row;
    }

    protected final XMLVersion getNS() {
        return this.getODDocument().getVersion();
    }

    protected final Namespace getValueNS() {
        final XMLVersion ns = this.getNS();
        return ns == XMLVersion.OD ? ns.getOFFICE() : ns.getTABLE();
    }

    protected final String getType() {
        return this.getElement().getAttributeValue("value-type", getValueNS());
    }

    /**
     * The value type of this cell.
     * 
     * @return the value type of this cell, <code>null</code> if not specified.
     */
    public final ODValueType getValueType() {
        final String type = this.getType();
        return type == null ? null : ODValueType.get(type);
    }

    // cannot resolve our style since a single instance of Cell is used for all
    // repeated and thus if we need to check table-column table:default-cell-style-name
    // we wouldn't know which column to check.
    @Override
    protected String getStyleName() {
        throw new UnsupportedOperationException("cannot resolve our style, use MutableCell");
    }

    String getStyleAttr() {
        return this.getElement().getAttributeValue("style-name", getNS().getTABLE());
    }

    private final String getValue(String attrName) {
        return this.getElement().getAttributeValue(attrName, getValueNS());
    }

    public final String getRawValue(final ODValueType requiredType) {
        final ODValueType vt = this.getValueType();
        if (requiredType != null && vt != requiredType)
            throw new IllegalStateException("Wrong type : " + vt + "!= " + requiredType);
        return this.getValue(vt.getValueAttribute());
    }

    /**
     * The value of this cell. If the value type is <code>null</code> this method assumes
     * {@link ODValueType#STRING}. If the {@link ODValueType#getValueAttribute() string value} is
     * <code>null</code> returns {@link #getTextValue()}.
     * 
     * @return the value of this cell, never <code>null</code>.
     * @see #getValueType()
     */
    public Object getValue() {
        final ODValueType vt = this.getValueType();
        if (vt == null || vt == ODValueType.STRING) {
            // ATTN oo generates string value-types w/o any @string-value
            final String attr = vt == null ? null : this.getValue(vt.getValueAttribute());
            if (attr != null)
                return attr;
            else {
                return getTextValue();
            }
        } else {
            return vt.parse(this.getValue(vt.getValueAttribute()));
        }
    }

    /**
     * Get the date as a Calendar.
     * 
     * @return a calendar with the local time of this cell.
     * @throws ParseException if the value couldn't be parsed.
     * @see ODValueType#getCalendar()
     */
    public final Calendar getDateValue() throws ParseException {
        return this.getDateValue(null, null);
    }

    /**
     * Get the date as a Calendar with the passed parameters.
     * 
     * @param tz the time zone of the returned calendar, <code>null</code> meaning
     *        {@link ODValueType#getTimeZone(boolean)}.
     * @param locale the locale of the returned calendar, <code>null</code> meaning
     *        {@link ODValueType#getLocale(boolean)}.
     * @return a calendar with the local time of this cell.
     * @throws ParseException if the value couldn't be parsed.
     * @see ODValueType#parseDateValue(String, TimeZone, Locale, Boolean)
     */
    public final Calendar getDateValue(final TimeZone tz, final Locale locale) throws ParseException {
        return ODValueType.parseDateValue(getRawValue(ODValueType.DATE), tz, locale, null);
    }

    /**
     * Calls {@link #getTextValue(boolean)} using {@link #getTextValueMode()}.
     * 
     * @return a string for the content of this cell.
     */
    public String getTextValue() {
        return this.getTextValue(getTextValueMode());
    }

    /**
     * Return the text value of this cell. This is often the formatted string of a value, e.g.
     * "11 novembre 2009" for a date. This method doesn't just return the text content it also
     * parses XML elements (like paragraphs, tabs and line-breaks). For the differences between the
     * OO way (as of 3.1) and the OpenDocument way see section 5.1.1 White-space Characters of
     * OpenDocument-v1.0-os and OpenDocument-v1.2-part1. In essence OpenOffice never trim strings.
     * 
     * @param ooMode whether to use the OO way or the standard way.
     * @return a string for the content of this cell.
     */
    public String getTextValue(final boolean ooMode) {
        return this.getTextValue(ooMode, false);
    }

    public String getTextValue(final boolean ooMode, final boolean useSeparator) {
        return TextNode.getChildrenCharacterContent(this.getElement(), getODDocument().getFormatVersion(), ooMode, useSeparator);
    }

    public final Children<Paragraph> getParagraphs() {
        return TextNodeDesc.get(Paragraph.class).getChildren(this.getODDocument(), getElement());
    }

    public final int getLinesCount() {
        return TextNode.getLinesCount(this.getElement(), this.getODDocument().getFormatVersion(), getTextValueMode());
    }

    /**
     * Get the raw value of the formula attribute.
     * 
     * @return the raw value, e.g. "of:sum(A1:A2)".
     * @see #getFormula()
     */
    public final String getRawFormula() {
        return this.getElement().getAttributeValue("formula", getTABLE());
    }

    protected final Namespace getDefaultFormulaNS() {
        return getNS().getNS("of");
    }

    /**
     * Get the formula text if it's from the default OpenFormula namespace.
     * 
     * @return the OpenFormula text, e.g. "sum(A1:A2)".
     * @throws IllegalStateException if there's no default namespace defined for this version of XML
     *         or if the formula is from a different namespace.
     * @see #getFormulaAndNamespace()
     */
    public final String getFormula() throws IllegalStateException {
        final Tuple3<Namespace, String, String> res = this.getFormulaAndNamespace();
        if (res.get2() != null) {
            final Namespace defaultNS = getDefaultFormulaNS();
            if (defaultNS == null)
                throw new IllegalStateException("No default namespace");
            if (!defaultNS.equals(res.get0()))
                throw new IllegalStateException("Not from default namespace");
        }
        return res.get1();
    }

    /**
     * Get the formula text and its namespace. The result can be passed to
     * {@link MutableCell#setFormulaAndNamespace(org.jopendocument.util.Tuple2)}. NOTE : {@link XMLVersion#OOo}
     * doesn't support namespaces.
     * 
     * @return the namespace (can be <code>null</code> if not included in this document), the text,
     *         the namespace prefix (can be <code>null</code>), e.g.
     *         [urn:oasis:names:tc:opendocument:xmlns:of:1.2, "sum(A1:A2)", null].
     * @see #getFormula()
     */
    public final Tuple3<Namespace, String, String> getFormulaAndNamespace() {
        final String raw = getRawFormula();
        if (raw == null || getODDocument().getVersion() == XMLVersion.OOo)
            return Tuple3.create(null, raw, null);
        final List2<String> tuple = getFormulaNSPrefix(raw);
        final String prefix = tuple.get0();
        final Namespace ns = prefix == null ? getDefaultFormulaNS() : getElement().getNamespace(prefix);
        return Tuple3.create(ns, tuple.get1(), prefix);
    }

    /**
     * Tries to find out if this cell computation resulted in an error. This method cannot be robust
     * since there's no error attribute in OpenDocument, we must match the value of the cell against
     * a pattern. E.g. whether a cell has '=A0' for formula or '= "#N" & "/A"', this method will
     * return a non-null error.
     * 
     * @return the error or <code>null</code>.
     */
    public String getError() {
        // to differentiate between the result of a computation and the user having typed '#N/A'
        // (this is because per §4.6 of OpenDocument-v1.2-cs01-part2 : if an error value is the
        // result of a cell computation it shall be stored as if it was a string.)
        if (getRawFormula() == null)
            return null;
        final String textValue = getTextValue();
        // OpenDocument 1.1 didn't specify errors
        return (XMLFormatVersion.get(XMLVersion.OD, "1.1").equals(getODDocument().getFormatVersion()) && textValue.equals("#NA")) || ErrorPattern.matcher(textValue).matches() ? textValue : null;
    }

    public boolean isValid() {
        return !this.isCovered();
    }

    protected final boolean isCovered() {
        return this.getElement().getName().equals("covered-table-cell");
    }

    public final boolean isEmpty() {
        return this.getValueType() == null && this.getElement().getContentSize() == 0;
    }

    public final int getColumnsSpanned() {
        // from 8.1.3 Table Cell
        return Integer.parseInt(this.getElement().getAttributeValue("number-columns-spanned", getNS().getTABLE(), "1"));
    }

    public final int getRowsSpanned() {
        // from 8.1.3 Table Cell
        return Integer.parseInt(this.getElement().getAttributeValue("number-rows-spanned", getNS().getTABLE(), "1"));
    }

    protected final boolean coversOtherCells() {
        return getColumnsSpanned() > 1 || getRowsSpanned() > 1;
    }
}