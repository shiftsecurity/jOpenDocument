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
import org.jopendocument.util.TimeUtils;
import org.jopendocument.util.convertor.NumberConvertor;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.Duration;

import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27.18 in v1.2-cs01-part1
public class TimeStyle extends DataStyle {

    static final DataStyleDesc<TimeStyle> DESC = new DataStyleDesc<TimeStyle>(TimeStyle.class, XMLVersion.OD, "time-style", "N") {
        @Override
        public TimeStyle create(ODPackage pkg, Element e) {
            return new TimeStyle(pkg, e);
        }
    };

    private static final int DAY_LENGTH_IN_HOURS = 24;
    private static final int AM_LENGTH = DAY_LENGTH_IN_HOURS / 2;

    private static final String formatInt(int i, Element elem) {
        final String res = String.valueOf(i);
        return !DateStyle.isShort(elem) && res.length() < 2 ? '0' + res : res;
    }

    public TimeStyle(final ODPackage pkg, Element elem) {
        super(pkg, elem, ODValueType.TIME);
    }

    @Override
    protected Duration convertNonNull(Object o) {
        if (o instanceof Number) {
            return TimeUtils.timePartToDuration(getEpoch().getDate(NumberConvertor.toBigDecimal((Number) o)));
        } else {
            return null;
        }
    }

    @Override
    public String format(Object o, CellStyle defaultStyle, boolean lenient) {
        final Duration d = o instanceof Calendar ? TimeUtils.timePartToDuration((Calendar) o) : (Duration) o;
        final Namespace numberNS = this.getElement().getNamespace();
        final StringBuilder sb = new StringBuilder();

        final Locale styleLocale = DateStyle.getLocale(getElement());
        final boolean truncate = StyleProperties.parseBoolean(getElement().getAttributeValue("truncate-on-overflow", numberNS), true);
        @SuppressWarnings("unchecked")
        final List<Element> children = this.getElement().getChildren();
        for (final Element elem : children) {
            if (elem.getNamespace().equals(numberNS)) {
                if (elem.getName().equals("text")) {
                    sb.append(elem.getText());
                } else if (elem.getName().equals("hours")) {
                    int hours = d.getHours();
                    if (truncate)
                        hours = hours % DAY_LENGTH_IN_HOURS;
                    if (elem.getChild("am-pm", numberNS) != null) {
                        // Duration fields are never negative
                        hours = d.getHours() == 0 ? AM_LENGTH : (d.getHours() - 1) % AM_LENGTH + 1;
                        assert hours >= 1 && hours <= AM_LENGTH;
                    }
                    sb.append(formatInt(hours, elem));
                } else if (elem.getName().equals("am-pm")) {
                    final boolean am = d.getHours() % DAY_LENGTH_IN_HOURS < AM_LENGTH;
                    sb.append(new DateFormatSymbols(styleLocale).getAmPmStrings()[am ? 0 : 1]);
                } else if (elem.getName().equals("minutes")) {
                    final int minutes;
                    if (truncate && getElement().getChild("hours", numberNS) == null)
                        minutes = d.getMinutes() % 60;
                    else
                        minutes = d.getMinutes();
                    sb.append(formatInt(minutes, elem));
                } else if (elem.getName().equals("seconds")) {
                    final BigDecimal seconds = TimeUtils.getSeconds(d);
                    final int secondsIntPart;
                    if (truncate && getElement().getChild("hours", numberNS) == null && getElement().getChild("minutes", numberNS) == null)
                        secondsIntPart = seconds.intValue() % 60;
                    else
                        secondsIntPart = seconds.intValue();
                    sb.append(formatInt(secondsIntPart, elem));

                    final int decPlaces = StyleProperties.parseInt(elem.getAttributeValue("decimal-places", numberNS), 0);
                    sb.append(DateStyle.formatSecondFraction(styleLocale, seconds, decPlaces));
                }
            }
        }

        return sb.toString();
    }
}
