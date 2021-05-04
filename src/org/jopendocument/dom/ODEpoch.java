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

import org.jopendocument.util.TimeUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;

/**
 * The null date of an OpenDocument.
 */
@Immutable
public final class ODEpoch {

    static private final BigDecimal MS_PER_DAY = BigDecimal.valueOf(24l * 60l * 60l * 1000l);
    @GuardedBy("DATE_FORMAT")
    static private final DateFormat DATE_FORMAT;
    static private final ODEpoch DEFAULT_EPOCH;
    @GuardedBy("cache")
    static private final Map<String, ODEpoch> cache = new LinkedHashMap<String, ODEpoch>(4, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, ODEpoch> eldest) {
            return this.size() > 16;
        }
    };

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        DATE_FORMAT.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        try {
            DEFAULT_EPOCH = new ODEpoch("1899-12-30");
        } catch (ParseException e) {
            // shouldn't happen since string are constants
            throw new IllegalStateException(e);
        }
    }

    static public final ODEpoch getDefaultEpoch() {
        return DEFAULT_EPOCH;
    }

    static public final ODEpoch getInstance(String date) throws ParseException {
        if (date == null || date.equals(DEFAULT_EPOCH.getDateString())) {
            return DEFAULT_EPOCH;
        } else {
            synchronized (cache) {
                ODEpoch res = cache.get(date);
                if (res == null) {
                    res = new ODEpoch(date);
                    cache.put(date, res);
                }
                return res;
            }
        }
    }

    static private final Calendar parse(final String date) throws ParseException {
        synchronized (DATE_FORMAT) {
            final Calendar cal = (Calendar) DATE_FORMAT.getCalendar().clone();
            cal.setTime(DATE_FORMAT.parse(date));
            return cal;
        }
    }

    private final String dateString;
    private final Calendar epochUTC;

    private ODEpoch(final String date) throws ParseException {
        this.dateString = date;
        this.epochUTC = parse(date);
        assert this.epochUTC.getTimeZone().equals(DATE_FORMAT.getTimeZone());
    }

    public final String getDateString() {
        return this.dateString;
    }

    public final Calendar getCalendar() {
        return (Calendar) this.epochUTC.clone();
    }

    private final Calendar getDate(final Duration duration) {
        // If we don't use the UTC calendar, we go from 0:00 (the epoch), we add n days, we get
        // to the last Sunday of March at 0:00, so far so good, but then we add say 10 hours, thus
        // going through the change of offset, and arriving at 11:00.
        final Calendar res = getCalendar();
        duration.addTo(res);
        return res;
    }

    public final Duration normalizeToDays(final Duration dur) {
        // we have to convert to a date to know the duration of years and months
        final Duration res = dur.getYears() == 0 && dur.getMonths() == 0 ? dur : getDuration(getDays(dur));
        assert res.getYears() == 0 && res.getMonths() == 0;
        return res;
    }

    public final Duration normalizeToHours(final Duration dur) {
        final Duration durationInDays = normalizeToDays(dur);
        final BigInteger days = (BigInteger) durationInDays.getField(DatatypeConstants.DAYS);
        if (days == null || days.equals(BigInteger.ZERO))
            return durationInDays;
        final BigInteger hours = ((BigInteger) durationInDays.getField(DatatypeConstants.HOURS)).add(days.multiply(BigInteger.valueOf(24)));
        return TimeUtils.getTypeFactory().newDuration(days.signum() >= 0, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, hours, (BigInteger) durationInDays.getField(DatatypeConstants.MINUTES),
                (BigDecimal) durationInDays.getField(DatatypeConstants.SECONDS));
    }

    public final BigDecimal getDays(final Duration duration) {
        return getDays(getDate(duration));
    }

    public final BigDecimal getDays(final Calendar cal) {
        // can't use Duration.normalizeWith() since it doesn't handle DST, i.e. going from winter to
        // summer at midnight will miss a day
        final long diff = TimeUtils.normalizeLocalTime(cal) - this.epochUTC.getTimeInMillis();
        return BigDecimal.valueOf(diff).divide(MS_PER_DAY, MathContext.DECIMAL128);
    }

    public final Calendar getDate(final BigDecimal days) {
        return getDate(days, ODValueType.getCalendar());
    }

    public final Calendar getDate(final BigDecimal days, final Calendar res) {
        final Calendar utcCal = getDate(getDuration(days));
        // can't use getTimeZone().getOffset() since we have no idea for the UTC time
        return TimeUtils.copyLocalTime(utcCal, res);
    }

    private final static Duration getDuration(final BigDecimal days) {
        final BigDecimal posDays = days.abs();
        final BigInteger wholeDays = posDays.toBigInteger().abs();
        final BigDecimal hours = posDays.subtract(new BigDecimal(wholeDays)).multiply(BigDecimal.valueOf(24));
        final BigInteger wholeHours = hours.toBigInteger();
        final BigDecimal minutes = hours.subtract(new BigDecimal(wholeHours)).multiply(BigDecimal.valueOf(60));
        final BigInteger wholeMinutes = minutes.toBigInteger();
        // round to 16 digits, i.e. 10^-14 seconds is more than enough
        // it is required since the number coming from getDays() might have been rounded
        final BigDecimal seconds = minutes.subtract(new BigDecimal(wholeMinutes)).multiply(BigDecimal.valueOf(60)).round(MathContext.DECIMAL64);
        return TimeUtils.getTypeFactory().newDuration(days.signum() >= 0, BigInteger.ZERO, BigInteger.ZERO, wholeDays, wholeHours, wholeMinutes, seconds);
    }
}