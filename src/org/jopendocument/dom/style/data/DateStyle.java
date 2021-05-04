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

package org.jopendocument.dom.style.data;

import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.util.convertor.NumberConvertor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27.10 in v1.2-cs01-part1
public class DateStyle extends DataStyle {

    // see http://download.oracle.com/javase/6/docs/technotes/guides/intl/calendar.doc.html
    private static final Calendar BUDDHIST_CAL = Calendar.getInstance(new Locale("th", "TH"));
    private static final Calendar JAPANESE_CAL = Calendar.getInstance(new Locale("ja", "JP", "JP"));
    private static final Calendar GREGORIAN_CAL = new GregorianCalendar();

    static final DataStyleDesc<DateStyle> DESC = new DataStyleDesc<DateStyle>(DateStyle.class, XMLVersion.OD, "date-style", "N") {
        @Override
        public DateStyle create(ODPackage pkg, Element e) {
            return new DateStyle(pkg, e);
        }
    };

    static final boolean isShort(final Element elem) {
        // in OOo the default is short
        return !"long".equals(elem.getAttributeValue("style", elem.getNamespace("number")));
    }

    public static final Locale getLocale(final Element elem) {
        final Locale res;
        final String country = elem.getAttributeValue("country", elem.getNamespace());
        final String lang = elem.getAttributeValue("language", elem.getNamespace());
        if (lang != null) {
            res = new Locale(lang, country == null ? "" : country);
        } else {
            res = Locale.getDefault();
        }
        return res;
    }

    private static final Calendar getCalendar(final Element elem, Calendar defaultCal) {
        final Calendar res;
        final String cal = elem.getAttributeValue("calendar", elem.getNamespace());
        if (cal == null) {
            res = defaultCal;
        } else if ("buddhist".equals(cal)) {
            res = BUDDHIST_CAL;
        } else if ("gengou".equals(cal)) {
            res = JAPANESE_CAL;
        } else if ("gregorian".equals(cal)) {
            res = GREGORIAN_CAL;
        } else {
            throw new IllegalArgumentException("Unsupported calendar : " + cal);
        }
        return res;
    }

    static String formatSecondFraction(final Locale styleLocale, final BigDecimal seconds, final int decPlaces) {
        if (decPlaces > 0) {
            final DecimalFormat decFormat = new DecimalFormat();
            decFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(styleLocale));
            decFormat.setMinimumIntegerDigits(0);
            decFormat.setMaximumIntegerDigits(0);
            decFormat.setMinimumFractionDigits(decPlaces);
            decFormat.setMaximumFractionDigits(decPlaces);
            // .12 or .578
            return decFormat.format(seconds);
        } else {
            return "";
        }
    }

    public DateStyle(final ODPackage pkg, Element elem) {
        super(pkg, elem, ODValueType.DATE);
    }

    @Override
    protected Object convertNonNull(Object o) {
        if (o instanceof Number)
            return getEpoch().getDate(NumberConvertor.toBigDecimal((Number) o));
        else
            return null;
    }

    private final void format(final StringBuilder res, final StringBuilder pattern, final Locale styleLocale, final Calendar currentCalendar, final Date d) {
        if (pattern.length() > 0) {
            final SimpleDateFormat fmt = new SimpleDateFormat(pattern.toString(), styleLocale);
            pattern.setLength(0);
            fmt.setCalendar((Calendar) currentCalendar.clone());
            res.append(fmt.format(d));
        }
    }

    @Override
    public String format(Object o, CellStyle defaultStyle, boolean lenient) {
        final Date d = o instanceof Calendar ? ((Calendar) o).getTime() : (Date) o;
        final Namespace numberNS = this.getElement().getNamespace();
        final Locale styleLocale = getLocale(getElement());
        final Calendar styleCalendar = Calendar.getInstance(styleLocale);
        final StringBuilder res = new StringBuilder();

        Calendar currentCalendar = styleCalendar;
        final StringBuilder sb = new StringBuilder();

        @SuppressWarnings("unchecked")
        final List<Element> children = this.getElement().getChildren();
        for (final Element elem : children) {
            if (elem.getNamespace().equals(numberNS)) {
                final Calendar calendarLocaleElem = getCalendar(elem, styleCalendar);
                if (!calendarLocaleElem.equals(currentCalendar)) {
                    format(res, sb, styleLocale, currentCalendar, d);
                    currentCalendar = calendarLocaleElem;
                }

                if (elem.getName().equals("text")) {
                    DataStyle.addStringLiteral(sb, elem.getText());
                } else if (elem.getName().equals("era")) {
                    sb.append(isShort(elem) ? "G" : "GGGG");
                } else if (elem.getName().equals("year")) {
                    sb.append(isShort(elem) ? "yy" : "yyyy");
                } else if (elem.getName().equals("quarter")) {
                    final Calendar cal = (Calendar) currentCalendar.clone();
                    cal.setTime(d);
                    final double quarterLength = cal.getActualMaximum(Calendar.MONTH) / 4.0;
                    final int quarter = (int) (cal.get(Calendar.MONTH) / quarterLength + 1);
                    assert quarter >= 1 && quarter <= 4;
                    // TODO localize and honor short/long style
                    reportError("Quarters are not localized", lenient);
                    DataStyle.addStringLiteral(sb, isShort(elem) ? "Q" + quarter : "Q" + quarter);
                } else if (elem.getName().equals("month")) {
                    final Attribute possessive = elem.getAttribute("possessive-form", numberNS);
                    if (possessive != null)
                        reportError("Ignoring " + possessive, lenient);
                    if (!StyleProperties.parseBoolean(elem.getAttributeValue("textual", numberNS), false))
                        sb.append(isShort(elem) ? "M" : "MM");
                    else
                        sb.append(isShort(elem) ? "MMM" : "MMMM");
                } else if (elem.getName().equals("week-of-year")) {
                    sb.append("w");
                } else if (elem.getName().equals("day")) {
                    sb.append(isShort(elem) ? "d" : "dd");
                } else if (elem.getName().equals("day-of-week")) {
                    sb.append(isShort(elem) ? "E" : "EEEE");
                } else if (elem.getName().equals("am-pm")) {
                    sb.append("a");
                } else if (elem.getName().equals("hours")) {
                    // see 16.27.22 : If a <number:am-pm> element is contained in a date or time
                    // style, hours are displayed using values from 1 to 12 only.
                    if (getElement().getChild("am-pm", numberNS) == null)
                        sb.append(isShort(elem) ? "H" : "HH");
                    else
                        sb.append(isShort(elem) ? "h" : "hh");
                } else if (elem.getName().equals("minutes")) {
                    sb.append(isShort(elem) ? "m" : "mm");
                } else if (elem.getName().equals("seconds")) {
                    sb.append(isShort(elem) ? "s" : "ss");
                    final int decPlaces = StyleProperties.parseInt(elem.getAttributeValue("decimal-places", numberNS), 0);
                    if (decPlaces > 0) {
                        // use styleLocale since <seconds> hasn't @calendar
                        final Calendar cal = Calendar.getInstance(styleLocale);
                        cal.setTime(d);
                        final BigDecimal secondFractions = new BigDecimal(cal.get(Calendar.MILLISECOND)).movePointLeft(3);
                        assert secondFractions.compareTo(BigDecimal.ONE) < 0;
                        final String fractionPart = formatSecondFraction(styleLocale, secondFractions, decPlaces);
                        DataStyle.addStringLiteral(sb, fractionPart);
                    }
                }
            }
        }
        format(res, sb, styleLocale, currentCalendar, d);
        return res.toString();
    }
}
