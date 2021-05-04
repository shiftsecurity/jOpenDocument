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

package org.jopendocument.util;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Format a {@link Calendar} local time according to <a
 * href="http://www.w3.org/TR/xmlschema-2/#dateTime">W3C XML Schema 1.0 Part 2, Section
 * 3.2.7-14</a>. I.e. {@link #format(Object)} never write a time zone part and
 * {@link #parseObject(String)} will always use this {@link #getTimeZone() time zone}.
 * 
 * @author Sylvain CUAZ
 * @see XMLGregorianCalendar
 * @see XMLDateFormat
 */
public class XMLCalendarFormat extends AbstractXMLDateFormat {

    private final boolean alwaysParseGregorian;

    public XMLCalendarFormat() {
        this(null, null);
    }

    public XMLCalendarFormat(final TimeZone timezone, final Locale aLocale) {
        this(timezone, aLocale, false);
    }

    public XMLCalendarFormat(final TimeZone timezone, final Locale aLocale, final boolean alwaysParseGregorian) {
        super(timezone, aLocale);
        this.alwaysParseGregorian = alwaysParseGregorian;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        final GregorianCalendar cal;
        if (obj instanceof Date) {
            cal = new GregorianCalendar(getTimeZone(), getLocale());
            cal.setTime((Date) obj);
        } else if (obj instanceof GregorianCalendar) {
            cal = (GregorianCalendar) obj;
        } else {
            final Calendar nonGregCal = (Calendar) obj;
            cal = new GregorianCalendar(nonGregCal.getTimeZone());
            cal.setTime(nonGregCal.getTime());
        }
        final XMLGregorianCalendar xmlCal = factory.newXMLGregorianCalendar(cal);
        xmlCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        return toAppendTo.append(xmlCal.toXMLFormat());
    }

    @Override
    public Calendar parseObject(String source, ParsePosition pos) {
        try {
            final XMLGregorianCalendar xmlCal = factory.newXMLGregorianCalendar(source.substring(pos.getIndex()));
            pos.setIndex(source.length());
            final GregorianCalendar gregorianCalendar = xmlCal.toGregorianCalendar(getTimeZone(), getLocale(), null);
            final Calendar res;
            if (this.alwaysParseGregorian) {
                res = gregorianCalendar;
            } else {
                res = Calendar.getInstance(getTimeZone(), getLocale());
                res.setTime(gregorianCalendar.getTime());
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            pos.setErrorIndex(pos.getIndex());
            return null;
        }
    }
}
