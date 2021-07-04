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

package org.jopendocument.dom;

import org.jopendocument.util.FormatGroup;
import org.jopendocument.util.TimeUtils;
import org.jopendocument.util.XMLCalendarFormat;
import org.jopendocument.util.XMLDateFormat;

import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.Duration;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;

/**
 * A type of value, as per 16.1 "Data Types" and 6.7.1 "Variable Value Types and Values"
 */
@Immutable
public enum ODValueType {

    /**
     * Parses to {@link BigDecimal} to return the exact number.
     */
    FLOAT("value", Number.class) {

        @Override
        public String format(Object o) {
            // avoid 1.23E+3
            if (o instanceof BigDecimal)
                return ((BigDecimal) o).toPlainString();
            else
                return ((Number) o).toString();
        }

        @Override
        public BigDecimal parse(String s) {
            return new BigDecimal(s);
        }

    },
    PERCENTAGE("value", Number.class) {

        @Override
        public String format(Object o) {
            return FLOAT.format(o);
        }

        @Override
        public Object parse(String s) {
            return FLOAT.parse(s);
        }

    },
    CURRENCY("value", Number.class) {

        @Override
        public String format(Object o) {
            return FLOAT.format(o);
        }

        @Override
        public Object parse(String s) {
            return FLOAT.parse(s);
        }
    },
    DATE("date-value", Date.class, Calendar.class) {

        @Override
        public String format(Object o) {
            return formatDate(o);
        }

        @Override
        public Date parse(String date) {
            if (date.length() == 0)
                return null;
            else {
                try {
                    return parseDateValue(date).getTime();
                } catch (ParseException e) {
                    throw new IllegalStateException("wrong date: " + date, e);
                }
            }
        }

    },
    TIME("time-value", Duration.class, Calendar.class) {

        @Override
        public String format(Object o) {
            if (o instanceof Duration) {
                return o.toString();
            } else {
                final Calendar cal = (Calendar) o;
                return TimeUtils.timePartToDuration(cal).toString();
            }
        }

        @Override
        public Duration parse(String date) {
            if (date.length() == 0)
                return null;
            else {
                return TimeUtils.getTypeFactory().newDuration(date);
            }
        }

    },
    BOOLEAN("boolean-value", Boolean.class) {

        @Override
        public String format(Object o) {
            return ((Boolean) o).toString().toLowerCase();
        }

        @Override
        public Boolean parse(String s) {
            return Boolean.valueOf(s);
        }

    },
    STRING("string-value", String.class) {

        @Override
        public String format(Object o) {
            return o.toString();
        }

        @Override
        public String parse(String s) {
            return s;
        }
    };

    private final String attr;
    private final List<Class<?>> acceptedClasses;

    private ODValueType(String attr, Class<?>... classes) {
        this.attr = attr;
        this.acceptedClasses = Arrays.asList(classes);
    }

    /**
     * The name of the value attribute for this value type.
     * 
     * @return the value attribute, e.g. "boolean-value".
     */
    public final String getValueAttribute() {
        return this.attr;
    }

    public boolean canFormat(Class<?> toFormat) {
        for (final Class<?> c : this.acceptedClasses)
            if (c.isAssignableFrom(toFormat))
                return true;
        return false;
    }

    public abstract String format(Object o);

    public abstract Object parse(String s);

    /**
     * The value for the value-type attribute.
     * 
     * @return the value for the value-type attribute, e.g. "float".
     */
    public final String getName() {
        return this.name().toLowerCase();
    }

    /**
     * The instance for the passed value type.
     * 
     * @param name the value of the value-type attribute, e.g. "date".
     * @return the corresponding instance, never <code>null</code>, e.g. {@link #DATE}.
     * @throws IllegalArgumentException if <code>name</code> isn't a valid type.
     */
    public static ODValueType get(String name) {
        return ODValueType.valueOf(name.toUpperCase());
    }

    /**
     * Try to guess the value type for the passed object.
     * 
     * @param o the object.
     * @return a value type capable of formatting <code>o</code> or <code>null</code>.
     * @throws NullPointerException if <code>o</code> is <code>null</code>.
     */
    public static ODValueType forObject(Object o) throws NullPointerException {
        if (o == null)
            throw new NullPointerException();
        if (o instanceof Number)
            return FLOAT;
        else if (o instanceof Boolean)
            return BOOLEAN;
        else if (o instanceof String)
            return STRING;
        else if (o instanceof Duration)
            return TIME;
        else if (DATE.canFormat(o.getClass()))
            return DATE;
        else
            return null;
    }

    static private final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
    // use nulls and not (TimeZone|Locale).getDefault() so as to not need to listen to changes
    // LibreOffice behavior as of 4.1 is to no longer ignore explicit time zone when reading dates.
    @GuardedBy("ODValueType")
    static private DateConfig DATE_CONFIG = new DateConfig(null, null, null, Boolean.FALSE);

    // see http://www.w3.org/TR/2004/REC-xmlschema-2-20041028/#isoformats

    @GuardedBy("ODValueType")
    static private Format DATE_FORMAT, DATE_PARSER;
    static {
        updateFormat();
    }

    static private synchronized String formatDate(Object obj) {
        return DATE_FORMAT.format(obj);
    }

    static private synchronized void updateFormat() {
        // always remove time zone on write
        DATE_FORMAT = new XMLCalendarFormat(getTimeZone(false), getLocale(false));
        DATE_PARSER = getFormatParser(DATE_CONFIG, true);
    }

    static private synchronized SimpleDateFormat createDateFormat(final String pattern, final DateConfig dateConf) {
        final SimpleDateFormat res = new SimpleDateFormat(pattern, dateConf.getLocale(true));
        res.setTimeZone(!dateConf.isTimeZoneIgnored() ? UTC_TZ : dateConf.getTimeZone(true));
        return res;
    }

    static private synchronized Format getFormatParser(final DateConfig dateConf, final boolean forceCreate) {
        if (!forceCreate && dateConf.equals(DATE_CONFIG)) {
            return DATE_PARSER;
        } else {
            final Format xmlDF;
            if (dateConf.isTimeZoneIgnored()) {
                xmlDF = new XMLCalendarFormat(dateConf.getTimeZone(false), dateConf.getLocale(false));
            } else {
                xmlDF = new XMLDateFormat(UTC_TZ, null);
            }
            // first date and time so we don't loose time information on format() or parse()
            // MAYBE add HH':'mm':'ss,SSS for OOo 1
            return new FormatGroup(xmlDF, createDateFormat("yyyy-MM-dd'T'HH':'mm':'ss", dateConf), createDateFormat("yyyy-MM-dd", dateConf));
        }
    }

    static private synchronized final void setDateConfig(final DateConfig newVal) {
        if (!newVal.equals(DATE_CONFIG)) {
            DATE_CONFIG = newVal;
            updateFormat();
        }
    }

    /**
     * Set the framework default time zone. Pass <code>null</code> to always use the VM default
     * (passing {@link TimeZone#getDefault()} would set the value once and for all and wouldn't be
     * changed by {@link TimeZone#setDefault(TimeZone)}).
     * 
     * @param tz the new default time zone, <code>null</code> to use the VM default.
     */
    static public synchronized final void setTimeZone(final TimeZone tz) {
        setDateConfig(DATE_CONFIG.setTimeZone(tz));
    }

    /**
     * The framework default time zone.
     * 
     * @param notNull <code>true</code> if <code>null</code> should be replaced by
     *        {@link TimeZone#getDefault()}.
     * @return the default time zone, can only be <code>null</code> if <code>notNull</code> is
     *         <code>false</code>.
     */
    static public synchronized final TimeZone getTimeZone(final boolean notNull) {
        return DATE_CONFIG.getTimeZone(notNull);
    }

    /**
     * Set the framework default locale. Pass <code>null</code> to always use the VM default
     * (passing {@link Locale#getDefault()} would set the value once and for all and wouldn't be
     * changed by {@link Locale#setDefault(Locale)}).
     * 
     * @param locale the new default locale, <code>null</code> to use the VM default.
     */
    static public synchronized final void setLocale(final Locale locale) {
        setDateConfig(DATE_CONFIG.setLocale(locale));
    }

    /**
     * The framework default locale.
     * 
     * @param notNull <code>true</code> if <code>null</code> should be replaced by
     *        {@link Locale#getDefault()}.
     * @return the default locale, can only be <code>null</code> if <code>notNull</code> is
     *         <code>false</code>.
     */
    static public synchronized final Locale getLocale(final boolean notNull) {
        return DATE_CONFIG.getLocale(notNull);
    }

    /**
     * Get the framework default calendar.
     * 
     * @return the default calendar.
     * @see #getTimeZone(boolean)
     * @see #getLocale(boolean)
     */
    static public synchronized final Calendar getCalendar() {
        return DATE_CONFIG.getCalendar();
    }

    static public synchronized final void setTimeZoneIgnored(final boolean b) {
        setDateConfig(DATE_CONFIG.setTimeZoneIgnored(b));
    }

    /**
     * Whether to ignore explicit time zone in dates. Prior to 4.1 LibreOffice would ignore explicit
     * time zones, i.e. "2013-11-15T12:00:00.000" and "2013-11-15T12:00:00.000+01:00" would both
     * parse to noon. As of 4.1 the first one parse to noon, the second one to 11 AM.
     * 
     * @return <code>true</code> if the time zone part should be ignored.
     */
    static public synchronized final boolean isTimeZoneIgnored() {
        return DATE_CONFIG.isTimeZoneIgnored();
    }

    /**
     * Parse an OpenDocument date value with the framework defaults.
     * 
     * @param date the string formatted value.
     * @return a calendar with the local time of the passed date.
     * @throws ParseException if the value couldn't be parsed.
     * @see #parseDateValue(String, TimeZone, Locale, Boolean)
     */
    static public synchronized Calendar parseDateValue(final String date) throws ParseException {
        return parseDateValue(date, null, null, null);
    }

    /**
     * Parse an OpenDocument date value with the passed parameters.
     * 
     * @param date the string formatted value.
     * @param tz the time zone of the returned calendar, <code>null</code> meaning
     *        {@link #getTimeZone(boolean)}.
     * @param locale the locale of the returned calendar, <code>null</code> meaning
     *        {@link #getLocale(boolean)}.
     * @param ignoreTZ whether to ignore the time zone part of <code>part</code>, <code>null</code>
     *        meaning {@link #isTimeZoneIgnored()}.
     * @return a calendar with the local time of the passed date.
     * @throws ParseException if the value couldn't be parsed.
     */
    static public synchronized Calendar parseDateValue(final String date, final TimeZone tz, final Locale locale, final Boolean ignoreTZ) throws ParseException {
        final DateConfig conf = new DateConfig(DATE_CONFIG, tz, locale, ignoreTZ);
        final Object parsed = getFormatParser(conf, false).parseObject(date);
        if (parsed instanceof Calendar) {
            // XMLCalendarFormat
            return (Calendar) parsed;
        } else {
            final Calendar res = conf.getCalendar();
            if (conf.isTimeZoneIgnored()) {
                // SimpleDateFormat
                res.setTime((Date) parsed);
                return res;
            } else {
                // XMLDateFormat or SimpleDateFormat
                final Calendar cal = Calendar.getInstance(UTC_TZ);
                cal.setTime((Date) parsed);
                return TimeUtils.copyLocalTime(cal, res);
            }
        }
    }
}