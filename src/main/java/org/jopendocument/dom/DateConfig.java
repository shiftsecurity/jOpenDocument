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

import org.jopendocument.util.CompareUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import net.jcip.annotations.Immutable;

// configuration necessary to parse an OpenDocument date into a java.util.Calendar.
// all fields can be null (meaning ask this.defaults).
@Immutable
final class DateConfig {
    private final DateConfig defaults;
    private final TimeZone timeZone;
    private final Locale locale;
    private final Boolean ignoreTZ;

    DateConfig(final DateConfig defaults, TimeZone timeZone, Locale locale, Boolean ignoreTZ) {
        super();
        this.defaults = defaults;
        this.timeZone = timeZone;
        this.locale = locale;
        this.ignoreTZ = ignoreTZ;
    }

    /**
     * The default time zone.
     * 
     * @param vmDefaults <code>true</code> if <code>null</code> should be replaced by
     *        {@link TimeZone#getDefault()}.
     * @return the default time zone, can only be <code>null</code> if <code>vmDefaults</code> is
     *         <code>false</code>.
     */
    public final TimeZone getTimeZone(final boolean vmDefaults) {
        if (this.timeZone != null)
            return this.timeZone;
        else if (this.defaults != null)
            return this.defaults.getTimeZone(vmDefaults);
        else if (vmDefaults)
            return TimeZone.getDefault();
        else
            return null;
    }

    public final DateConfig setTimeZone(final TimeZone tz) {
        if (!CompareUtils.equals(this.timeZone, tz)) {
            return new DateConfig(this.defaults, tz, this.locale, this.ignoreTZ);
        } else {
            return this;
        }
    }

    /**
     * The default locale.
     * 
     * @param vmDefaults <code>true</code> if <code>null</code> should be replaced by
     *        {@link Locale#getDefault()}.
     * @return the default locale, can only be <code>null</code> if <code>vmDefaults</code> is
     *         <code>false</code>.
     */
    public final Locale getLocale(final boolean vmDefaults) {
        if (this.locale != null)
            return this.locale;
        else if (this.defaults != null)
            return this.defaults.getLocale(vmDefaults);
        else if (vmDefaults)
            return Locale.getDefault();
        else
            return null;
    }

    public final DateConfig setLocale(final Locale locale) {
        if (!CompareUtils.equals(this.locale, locale)) {
            return new DateConfig(this.defaults, this.timeZone, locale, this.ignoreTZ);
        } else {
            return this;
        }
    }

    /**
     * Get the default calendar.
     * 
     * @return the default calendar.
     * @see #getTimeZone(boolean)
     * @see #getLocale(boolean)
     */
    public final Calendar getCalendar() {
        return Calendar.getInstance(getTimeZone(true), getLocale(true));
    }

    public final boolean isTimeZoneIgnored() {
        if (this.ignoreTZ != null)
            return this.ignoreTZ;
        else if (this.defaults != null)
            return this.defaults.isTimeZoneIgnored();
        else
            throw new IllegalStateException("Null boolean");
    }

    public final DateConfig setTimeZoneIgnored(final Boolean b) {
        if (!CompareUtils.equals(this.ignoreTZ, b)) {
            return new DateConfig(this.defaults, this.timeZone, this.locale, b);
        } else {
            return this;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.isTimeZoneIgnored() ? 1231 : 1237);
        final Locale locale = this.getLocale(false);
        result = prime * result + (locale == null ? 0 : locale.hashCode());
        final TimeZone tz = this.getTimeZone(false);
        result = prime * result + (tz == null ? 0 : tz.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DateConfig other = (DateConfig) obj;
        return this.isTimeZoneIgnored() == other.isTimeZoneIgnored() && CompareUtils.equals(this.getLocale(true), other.getLocale(true))
                && this.getTimeZone(true).hasSameRules(other.getTimeZone(true));
    }
}