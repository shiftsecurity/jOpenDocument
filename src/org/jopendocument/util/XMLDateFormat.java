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
 * Format an absolute {@link Date} according to <a
 * href="http://www.w3.org/TR/xmlschema-2/#dateTime">W3C XML Schema 1.0 Part 2, Section
 * 3.2.7-14</a>. I.e. {@link #format(Object)} always write a time zone part and
 * {@link #parseObject(String)} will only use the {@link #getTimeZone() default time zone} if none
 * is specified.
 * 
 * @author Sylvain CUAZ
 * @see XMLGregorianCalendar
 * @see XMLCalendarFormat
 */
public class XMLDateFormat extends AbstractXMLDateFormat {

    public XMLDateFormat() {
        this(null, null);
    }

    public XMLDateFormat(final TimeZone timezone, final Locale aLocale) {
        super(timezone, aLocale);
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        final GregorianCalendar cal;
        if (obj instanceof GregorianCalendar) {
            cal = (GregorianCalendar) obj;
        } else {
            final Date d;
            final TimeZone tz;
            if (obj instanceof Calendar) {
                d = ((Calendar) obj).getTime();
                tz = ((Calendar) obj).getTimeZone();
            } else {
                d = (Date) obj;
                tz = this.getTimeZone();
            }
            cal = new GregorianCalendar(tz, this.getLocale());
            cal.setTime(d);
        }
        return toAppendTo.append(factory.newXMLGregorianCalendar(cal).toXMLFormat());
    }

    @Override
    public Date parseObject(String source, ParsePosition pos) {
        try {
            final XMLGregorianCalendar res = factory.newXMLGregorianCalendar(source.substring(pos.getIndex()));
            pos.setIndex(source.length());
            // pass time zone if source lacks it
            final TimeZone tz = res.getTimezone() == DatatypeConstants.FIELD_UNDEFINED ? this.tz : null;
            return res.toGregorianCalendar(tz, this.locale, null).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            pos.setErrorIndex(pos.getIndex());
            return null;
        }
    }
}
