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

import org.jopendocument.dom.Log;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODFrame;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.BytesProducer.ByteArrayProducer;
import org.jopendocument.dom.spreadsheet.BytesProducer.ImageProducer;
import org.jopendocument.dom.spreadsheet.CellStyle.StyleTableCellProperties;
import org.jopendocument.dom.style.data.BooleanStyle;
import org.jopendocument.dom.style.data.DataStyle;
import org.jopendocument.dom.style.data.DateStyle;
import org.jopendocument.util.FileUtils;
import org.jopendocument.util.TimeUtils;
import org.jopendocument.util.TimeUtils.DurationNullsChanger;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.Tuple3;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import javax.xml.datatype.Duration;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;

/**
 * A cell whose value can be changed.
 * 
 * @author Sylvain
 * @param <D> type of document
 */
public class MutableCell<D extends ODDocument> extends Cell<D> {

    static private final DateFormat TextPDateFormat = DateFormat.getDateInstance();
    static private final DateFormat TextPTimeFormat = DateFormat.getTimeInstance();
    static private final NumberFormat TextPMinuteSecondFormat = new DecimalFormat("00.###");
    static private final NumberFormat TextPFloatFormat = DecimalFormat.getNumberInstance();
    static private final NumberFormat TextPPercentFormat = DecimalFormat.getPercentInstance();
    static private final NumberFormat TextPCurrencyFormat = DecimalFormat.getCurrencyInstance();

    static public String formatNumber(Number n, final CellStyle defaultStyle) {
        return formatNumber(TextPFloatFormat, n, defaultStyle);
    }

    static public String formatPercent(Number n, final CellStyle defaultStyle) {
        return formatNumber(TextPPercentFormat, n, defaultStyle);
    }

    static public String formatCurrency(Number n, final CellStyle defaultStyle) {
        return formatNumber(TextPCurrencyFormat, n, defaultStyle);
    }

    static private String formatNumber(NumberFormat format, Number n, final CellStyle defaultStyle) {
        synchronized (format) {
            final int decPlaces = DataStyle.getDecimalPlaces(defaultStyle);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(decPlaces);
            return format.format(n);
        }
    }

    static private boolean LO_MODE = true;
    // no date part, all time part to zero
    static private final DurationNullsChanger TIME_NULLS = new TimeUtils.DurationNullsBuilder(TimeUtils.EmptyFieldPolicy.SET_TO_ZERO).setToNull(TimeUtils.getDateFields()).build();

    public static void setTimeValueMode(boolean loMode) {
        LO_MODE = loMode;
    }

    /**
     * Whether {@link #setValue(Object)} formats {@link Duration} using the standard way or using
     * the LibreOffice way. LibreOffice does not support years, months and days in cells ; even set
     * to 0 (it does in user meta fields). The initial value is <code>true</code>.
     * 
     * @return <code>true</code> if durations should be formatted the LibO way.
     */
    public static boolean getTimeValueMode() {
        return LO_MODE;
    }

    MutableCell(Row<D> parent, Element elem, StyleDesc<CellStyle> styleDesc) {
        super(parent, elem, styleDesc);
    }

    // ask our column to our row so we don't have to update anything when columns are removed/added
    public final int getX() {
        return this.getRow().getX(this);
    }

    public final int getY() {
        return this.getRow().getY();
    }

    public final Point getPoint() {
        return new Point(getX(), getY());
    }

    final void setRowsSpanned(final int rowsSpanned) {
        if (rowsSpanned <= 1)
            this.getElement().removeAttribute("number-rows-spanned", getNS().getTABLE());
        else
            this.getElement().setAttribute("number-rows-spanned", String.valueOf(rowsSpanned), getNS().getTABLE());
    }

    // *** setValue

    private void setValueAttributes(ODValueType type, Object val) {
        final Namespace valueNS = getValueNS();
        final Attribute valueTypeAttr = this.getElement().getAttribute("value-type", valueNS);
        // e.g. DATE
        final ODValueType currentType = valueTypeAttr == null ? null : ODValueType.get(valueTypeAttr.getValue());

        if (type == null) {
            if (valueTypeAttr != null) {
                valueTypeAttr.detach();
            }
        } else {
            if (!type.equals(currentType)) {
                if (valueTypeAttr != null) {
                    valueTypeAttr.setValue(type.getName());
                } else {
                    // create an instance of Attribute to avoid a getAttribute() in the simpler
                    // setAttribute()
                    this.getElement().setAttribute(new Attribute("value-type", type.getName(), valueNS));
                }
            }
        }

        // remove old value attribute (assume Element is valid, otherwise we would need to remove
        // all possible value attributes)
        if (currentType != null && (!currentType.equals(type) || type == ODValueType.STRING)) {
            // e.g. @date-value
            this.getElement().removeAttribute(currentType.getValueAttribute(), valueNS);
        }
        // Like LO, do not generate string-value
        if (type != null && type != ODValueType.STRING) {
            // LO cells don't support the full syntax of a duration (user meta fields do)
            // instead it support only values without nYnMnD
            if (type == ODValueType.TIME && getTimeValueMode()) {
                final Duration d = val instanceof Duration ? (Duration) val : TimeUtils.timePartToDuration((Calendar) val);
                val = TIME_NULLS.apply(getODDocument().getEpoch().normalizeToHours(d));
            }
            this.getElement().setAttribute(type.getValueAttribute(), type.format(val), valueNS);
        }
    }

    private void setTextP(String value) {
        if (value == null) {
            this.getElement().removeContent();
        } else {
            new Lines(this.getODDocument(), value).setText(getElement(), getTextValueMode());
        }
    }

    private void setValue(ODValueType type, Object value, String textP) {
        this.setValueAttributes(type, value);
        this.setTextP(textP);
    }

    public void clearValue() {
        this.setValue(null, null, null);
    }

    public void setValue(Object obj) {
        this.setValue(obj, true);
    }

    public void setValue(Object obj, final boolean allowTypeChange) throws UnsupportedOperationException {
        final ODValueType type;
        final ODValueType currentType = getValueType();
        // try to keep current type, since for example a Number can work with FLOAT, PERCENTAGE
        // and CURRENCY
        if (currentType != null && currentType.canFormat(obj.getClass())) {
            type = currentType;
        } else {
            final ODValueType tmp = ODValueType.forObject(obj);
            // allow any Object
            if (allowTypeChange && tmp == null) {
                type = ODValueType.STRING;
                obj = String.valueOf(obj);
            } else {
                type = tmp;
            }
        }
        if (type == null) {
            throw new IllegalArgumentException("Couldn't infer type of " + obj);
        }
        this.setValue(obj, type, allowTypeChange, true);
    }

    /**
     * Change the value of this cell.
     * 
     * @param obj the new cell value.
     * @param vt the value type.
     * @param allowTypeChange if <code>true</code> <code>obj</code> and <code>vt</code> might be
     *        changed to allow the data style to format, e.g. from Boolean.FALSE to 0.
     * @param lenient <code>false</code> to throw an exception if we can't format according to the
     *        ODF, <code>true</code> to try best-effort.
     * @throws UnsupportedOperationException if <code>obj</code> couldn't be formatted.
     */
    public void setValue(Object obj, ODValueType vt, final boolean allowTypeChange, final boolean lenient) throws UnsupportedOperationException {
        final String text;
        final Tuple3<String, ODValueType, Object> formatted = format(obj, vt, !allowTypeChange, lenient);
        vt = formatted.get1();
        obj = formatted.get2();

        if (formatted.get0() != null) {
            text = formatted.get0();
        } else {
            // either there were no format or formatting failed
            if (vt == ODValueType.FLOAT) {
                text = formatNumber((Number) obj, getDefaultStyle());
            } else if (vt == ODValueType.PERCENTAGE) {
                text = formatPercent((Number) obj, getDefaultStyle());
            } else if (vt == ODValueType.CURRENCY) {
                text = formatCurrency((Number) obj, getDefaultStyle());
            } else if (vt == ODValueType.DATE) {
                final Date d;
                if (obj instanceof Calendar) {
                    d = ((Calendar) obj).getTime();
                } else {
                    d = (Date) obj;
                }
                text = TextPDateFormat.format(d);
            } else if (vt == ODValueType.TIME) {
                if (obj instanceof Duration) {
                    final Duration normalized = getODDocument().getEpoch().normalizeToHours((Duration) obj);
                    text = "" + normalized.getHours() + ':' + TextPMinuteSecondFormat.format(normalized.getMinutes()) + ':' + TextPMinuteSecondFormat.format(TimeUtils.getSeconds(normalized));
                } else {
                    text = TextPTimeFormat.format(((Calendar) obj).getTime());
                }
            } else if (vt == ODValueType.BOOLEAN) {
                // LO do not use the the document language but the system language
                // http://help.libreoffice.org/Common/Selecting_the_Document_Language
                Locale l = Locale.getDefault();
                // except of course if there's a data style
                final CellStyle s = getStyle();
                if (s != null) {
                    final DataStyle ds = s.getDataStyle();
                    if (ds != null)
                        l = DateStyle.getLocale(ds.getElement());
                }
                text = BooleanStyle.toString((Boolean) obj, l, lenient);
            } else if (vt == ODValueType.STRING) {
                text = obj.toString();
            } else {
                throw new IllegalStateException(vt + " unknown");
            }
        }
        this.setValue(vt, obj, text);
    }

    // return null String if no data style exists, or if one exists but we couldn't use it
    private Tuple3<String, ODValueType, Object> format(Object obj, ODValueType valueType, boolean onlyCast, boolean lenient) {
        String res = null;
        try {
            final Tuple3<DataStyle, ODValueType, Object> ds = getDataStyleAndValue(obj, valueType, onlyCast);
            if (ds != null) {
                obj = ds.get2();
                valueType = ds.get1();
                // act like OO, that is if we set a String to a Date cell, change the value and
                // value-type but leave the data-style untouched
                if (ds.get0().canFormat(obj.getClass()))
                    res = ds.get0().format(obj, getDefaultStyle(), lenient);
            }
        } catch (UnsupportedOperationException e) {
            if (lenient)
                Log.get().log(Level.WARNING, "Couldn't format", e);
            else
                throw e;
        }
        return Tuple3.create(res, valueType, obj);
    }

    public final DataStyle getDataStyle() {
        final Tuple3<DataStyle, ODValueType, Object> s = this.getDataStyleAndValue(this.getValue(), this.getValueType(), true);
        return s != null ? s.get0() : null;
    }

    private final Tuple3<DataStyle, ODValueType, Object> getDataStyleAndValue(Object obj, ODValueType valueType, boolean onlyCast) {
        final CellStyle s = this.getStyle();
        return s != null ? s.getDataStyle(obj, valueType, onlyCast) : null;
    }

    protected final CellStyle getDefaultStyle() {
        return this.getRow().getSheet().getDefaultCellStyle();
    }

    public void replaceBy(String oldValue, String newValue) {
        replaceContentBy(this.getElement(), oldValue, newValue);
    }

    private void replaceContentBy(Element l, String oldValue, String newValue) {
        final List<?> content = l.getContent();
        for (int i = 0; i < content.size(); i++) {
            final Object obj = content.get(i);
            if (obj instanceof Text) {
                // System.err.println(" Text --> " + obj.toString());
                final Text t = (Text) obj;
                t.setText(t.getText().replaceAll(oldValue, newValue));
            } else if (obj instanceof Element) {
                replaceContentBy((Element) obj, oldValue, newValue);
            }
        }
    }

    /**
     * Set the raw value of the formula attribute.
     * 
     * @param formula the raw value, e.g. "of:sum(A1:A2)".
     */
    public final void setRawFormula(final String formula) {
        // from 19.642 table:formula of OpenDocument-v1.2 : Whenever the initial text of a formula
        // has the appearance of an NCName followed by ":", an OpenDocument producer shall provide a
        // valid namespace prefix in order to eliminate any ambiguity.
        final String nsPrefix = getFormulaNSPrefix(formula).get0();
        if (nsPrefix != null && this.getElement().getNamespace(nsPrefix) == null) {
            throw new IllegalArgumentException("Unknown namespace prefix : " + nsPrefix);
        }
        this.setFormulaNoCheck(formula);
    }

    private final void setFormulaNoCheck(final String formula) {
        this.getElement().setAttribute("formula", formula, getTABLE());
    }

    public final void setFormulaAndNamespace(final Tuple2<Namespace, String> formula) {
        this.setFormulaAndNamespace(formula.get0(), formula.get1());
    }

    public final void setFormula(final String formula) {
        this.setFormulaAndNamespace(null, formula);
    }

    public final void setFormulaAndNamespace(final Namespace ns, final String formula) {
        if (getODDocument().getVersion() == XMLVersion.OOo) {
            if (ns != null)
                throw new IllegalArgumentException("Namespaces not supported by this version : " + ns);
            this.setFormulaNoCheck(formula);
        } else if (ns == null) {
            final String nsPrefix = getFormulaNSPrefix(formula).get0();
            if (nsPrefix != null) {
                // eliminate ambiguity
                final Namespace defaultNS = getDefaultFormulaNS();
                // prevent infinite recursion
                if (defaultNS == null)
                    throw new IllegalStateException("Cannot resolve ambiguity, formula appears to begin with " + nsPrefix + " and no default namespace found");
                this.setFormulaAndNamespace(defaultNS, formula);
            } else {
                this.setFormulaNoCheck(formula);
            }
        } else {
            final Namespace existingNS = this.getElement().getNamespace(ns.getPrefix());
            if (existingNS == null)
                this.getElement().getDocument().getRootElement().addNamespaceDeclaration(ns);
            else if (!existingNS.equals(ns))
                throw new IllegalStateException("Namespace conflict : " + existingNS + " != " + ns);
            this.setFormulaNoCheck(ns.getPrefix() + ':' + formula);
        }
    }

    public final void unmerge() {
        // from 8.1.3 Table Cell : table-cell are like covered-table-cell with some extra
        // optional attributes so it's safe to rename covered cells into normal ones
        final int x = this.getX();
        final int y = this.getY();
        final int columnsSpanned = getColumnsSpanned();
        final int rowsSpanned = getRowsSpanned();
        for (int i = 0; i < columnsSpanned; i++) {
            for (int j = 0; j < rowsSpanned; j++) {
                // don't mind if we change us at 0,0 we're already a table-cell
                this.getRow().getSheet().getImmutableCellAt(x + i, y + j).getElement().setName("table-cell");
            }
        }
        this.getElement().removeAttribute("number-columns-spanned", getNS().getTABLE());
        this.getElement().removeAttribute("number-rows-spanned", getNS().getTABLE());
    }

    /**
     * Merge this cell and the following ones. If this cell already spanned multiple columns/rows
     * this method un-merge any additional cells.
     * 
     * @param columnsSpanned number of columns to merge.
     * @param rowsSpanned number of rows to merge.
     */
    public final void merge(final int columnsSpanned, final int rowsSpanned) {
        final int currentCols = this.getColumnsSpanned();
        final int currentRows = this.getRowsSpanned();

        // nothing to do
        if (columnsSpanned == currentCols && rowsSpanned == currentRows)
            return;

        final int x = this.getX();
        final int y = this.getY();

        // check for problems before any modifications
        for (int i = 0; i < columnsSpanned; i++) {
            for (int j = 0; j < rowsSpanned; j++) {
                final boolean coveredByThis = i < currentCols && j < currentRows;
                if (!coveredByThis) {
                    final int x2 = x + i;
                    final int y2 = y + j;
                    final Cell<D> immutableCell = this.getRow().getSheet().getImmutableCellAt(x2, y2);
                    // check for overlapping range from inside
                    if (immutableCell.coversOtherCells())
                        throw new IllegalArgumentException("Cell at " + x2 + "," + y2 + " is a merged cell.");
                    // and outside
                    if (immutableCell.getElement().getName().equals("covered-table-cell"))
                        throw new IllegalArgumentException("Cell at " + x2 + "," + y2 + " is already covered.");
                }
            }
        }

        final boolean shrinks = columnsSpanned < currentCols || rowsSpanned < currentRows;
        if (shrinks)
            this.unmerge();

        // from 8.1.3 Table Cell : table-cell are like covered-table-cell with some extra
        // optional attributes so it's safe to rename
        for (int i = 0; i < columnsSpanned; i++) {
            for (int j = 0; j < rowsSpanned; j++) {
                final boolean coveredByThis = i < currentCols && j < currentRows;
                // don't cover this,
                // if we grow the current covered cells are invalid so don't try to access them
                if ((i != 0 || j != 0) && (shrinks || !coveredByThis))
                    // MutableCell is needed to break repeated
                    this.getRow().getSheet().getCellAt(x + i, y + j).getElement().setName("covered-table-cell");
            }
        }
        this.getElement().setAttribute("number-columns-spanned", columnsSpanned + "", getNS().getTABLE());
        this.getElement().setAttribute("number-rows-spanned", rowsSpanned + "", getNS().getTABLE());
    }

    @Override
    public final String getStyleName() {
        return this.getRow().getSheet().getStyleNameAt(this.getX(), this.getY());
    }

    public final StyleTableCellProperties getTableCellProperties() {
        return this.getRow().getSheet().getTableCellPropertiesAt(this.getX(), this.getY());
    }

    public void setImage(final File pic) throws IOException {
        this.setImage(pic, false);
    }

    public void setImage(final File pic, boolean keepRatio) throws IOException {
        this.setImage(pic.getName(), new ByteArrayProducer(FileUtils.readBytes(pic), keepRatio));
    }

    public void setImage(final String name, final Image img) throws IOException {
        this.setImage(name, img == null ? null : new ImageProducer(img, true));
    }

    private void setImage(final String name, final BytesProducer data) {
        final Namespace draw = this.getNS().getNS("draw");
        final Element frame = this.getElement().getChild("frame", draw);
        final Element imageElem = frame == null ? null : frame.getChild("image", draw);

        if (imageElem != null) {
            final Attribute refAttr = imageElem.getAttribute("href", this.getNS().getNS("xlink"));
            this.getODDocument().getPackage().putFile(refAttr.getValue(), null);

            if (data == null)
                frame.detach();
            else {
                refAttr.setValue("Pictures/" + name + (data.getFormat() != null ? "." + data.getFormat() : ""));
                this.getODDocument().getPackage().putFile(refAttr.getValue(), data.getBytes(new ODFrame<D>(getODDocument(), frame)));
            }
        } else if (data != null)
            throw new IllegalStateException("this cell doesn't contain an image: " + this);
    }

    public final void setBackgroundColor(final Color color) {
        this.getPrivateStyle().getTableCellProperties(this).setBackgroundColor(color);
    }
}