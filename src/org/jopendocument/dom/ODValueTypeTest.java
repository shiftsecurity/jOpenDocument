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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.xml.datatype.Duration;

import junit.framework.TestCase;

public class ODValueTypeTest extends TestCase {
    public void testAll() throws Exception {
        for (final ODValueType vt : ODValueType.values()) {
            if (vt == ODValueType.FLOAT || vt == ODValueType.PERCENTAGE || vt == ODValueType.CURRENCY)
                testNumber(vt);
            else if (vt == ODValueType.BOOLEAN)
                testBoolean(vt);
            else if (vt == ODValueType.STRING)
                testString(vt);
            else if (vt == ODValueType.TIME)
                testTime(vt);
            else if (vt == ODValueType.DATE)
                testDate(vt);
            else
                fail("No test for " + vt);
        }
    }

    private void testNumber(final ODValueType vt) throws Exception {
        assertTrue(vt.canFormat(Float.class));
        assertTrue(vt.canFormat(Integer.class));
        assertFalse(vt.canFormat(Boolean.class));
        assertFalse(vt.canFormat(String.class));
        assertFalse(vt.canFormat(Date.class));
        assertFalse(vt.canFormat(Calendar.class));
        assertFalse(vt.canFormat(Duration.class));

        assertEquals(new BigDecimal("3.5"), vt.parse(vt.format(3.5)));
    }

    private void testBoolean(final ODValueType vt) throws Exception {
        assertFalse(vt.canFormat(Float.class));
        assertFalse(vt.canFormat(Integer.class));
        assertTrue(vt.canFormat(Boolean.class));
        assertFalse(vt.canFormat(String.class));
        assertFalse(vt.canFormat(Date.class));
        assertFalse(vt.canFormat(Calendar.class));
        assertFalse(vt.canFormat(Duration.class));

        assertEquals(true, vt.parse(vt.format(true)));
        assertEquals(false, vt.parse(vt.format(false)));
        try {
            vt.format("non bool");
            fail("Shouldn't format a String");
        } catch (Exception e) {
            // OK
        }
    }

    private void testString(final ODValueType vt) throws Exception {
        assertFalse(vt.canFormat(Float.class));
        assertFalse(vt.canFormat(Integer.class));
        assertFalse(vt.canFormat(Boolean.class));
        assertTrue(vt.canFormat(String.class));
        assertFalse(vt.canFormat(Date.class));
        assertFalse(vt.canFormat(Calendar.class));
        assertFalse(vt.canFormat(Duration.class));

        assertEquals("tést\t", vt.parse(vt.format("tést\t")));
    }

    private void testTime(final ODValueType vt) throws Exception {
        assertFalse(vt.canFormat(Float.class));
        assertFalse(vt.canFormat(Integer.class));
        assertFalse(vt.canFormat(Boolean.class));
        assertFalse(vt.canFormat(String.class));
        assertFalse(vt.canFormat(Date.class));
        assertTrue(vt.canFormat(Calendar.class));
        assertTrue(vt.canFormat(Duration.class));

        final String s = "P1DT23H";
        final Duration duration = TimeUtils.getTypeFactory().newDuration(s);
        assertEquals(duration, vt.parse(s));
        try {
            vt.parse(s + "foobar");
            fail("Wrong format");
        } catch (Exception e) {
            // OK
        }

        // round trip
        assertEquals(duration, vt.parse(vt.format(duration)));
    }

    private void testDate(final ODValueType vt) throws Exception {
        assertFalse(vt.canFormat(Float.class));
        assertFalse(vt.canFormat(Integer.class));
        assertFalse(vt.canFormat(Boolean.class));
        assertFalse(vt.canFormat(String.class));
        assertTrue(vt.canFormat(Date.class));
        assertTrue(vt.canFormat(Calendar.class));
        assertFalse(vt.canFormat(Duration.class));

        final Calendar cal = Calendar.getInstance();
        final String formatted = vt.format(cal);
        final String formattedWithDate = vt.format(cal.getTime());
        // same result with Date or Calendar
        assertEquals(formatted, formattedWithDate);
        // round trip
        assertEquals(cal.getTime(), vt.parse(formatted));

        // pick an offset that isn't likely to be used for the DST
        final SimpleTimeZone nonDefaultTZ = new SimpleTimeZone(TimeZone.getDefault().getRawOffset() + 7250 * 1000, "customTZ");
        ODValueType.setTimeZone(nonDefaultTZ);
        try {
            final Date nonDefaultTZparse = (Date) vt.parse(formatted);
            // the absolute time is different
            assertFalse(cal.getTime().equals(nonDefaultTZparse));
            // but the local time is the same
            final Calendar nonDefaultCal = ODValueType.getCalendar();
            assertEquals(nonDefaultTZ, nonDefaultCal.getTimeZone());
            nonDefaultCal.setTime(nonDefaultTZparse);
            assertEquals(TimeUtils.normalizeLocalTime(cal), TimeUtils.normalizeLocalTime(nonDefaultCal));
            // parseDateValue() is coherent with vt
            assertEquals(nonDefaultCal, ODValueType.parseDateValue(formatted));
        } finally {
            ODValueType.setTimeZone(null);
        }

        final String woTZ = "2013-11-15T12:00:00.000";
        final String withTZ = woTZ + "+01:00";
        // if there's no time zone part, the boolean is useless
        assertEquals(ODValueType.parseDateValue(woTZ, null, null, true), ODValueType.parseDateValue(woTZ, null, null, false));
        // if ignoreTZ=true, both strings parse the same
        assertEquals(ODValueType.parseDateValue(woTZ, null, null, true), ODValueType.parseDateValue(withTZ, null, null, true));
        // if ignoreTZ=false, one string parses different than the other
        assertEquals(ODValueType.parseDateValue(woTZ, null, null, false).getTimeInMillis(), ODValueType.parseDateValue(withTZ, null, null, false).getTimeInMillis() + 3600 * 1000);

        // test DateConfig
        {
            final TimeZone parisTZ = TimeZone.getTimeZone("Europe/Paris");
            final TimeZone secondTZ = TimeZone.getTimeZone(parisTZ.getID());
            assertNotSame(secondTZ, parisTZ);
            assertTrue(parisTZ.equals(secondTZ));

            secondTZ.setID("Europe/Grenoble");
            // different ID
            assertFalse(parisTZ.equals(secondTZ));
            // but same rules
            assertEquals(new DateConfig(null, parisTZ, null, true), new DateConfig(null, secondTZ, null, true));

            // test getter
            assertEquals(new DateConfig(null, null, null, true), new DateConfig(null, TimeZone.getDefault(), Locale.getDefault(), true));
        }
    }
}
