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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class LengthUnitTest extends TestCase {

    static public final <T> void assertEqualsWithCompareTo(final Comparable<T> o1, final T o2) {
        assertTrue(o1 + " != " + o2, CompareUtils.equalsWithCompareTo(o1, o2));
    }

    public void testParseLength() throws Exception {
        assertEquals(Length.create(new BigDecimal("15.2"), LengthUnit.CM), LengthUnit.parseLength("15.2cm"));
        testPositiveLength("-15.2cm", true);
        testPositiveLength("-15.2cm", false);
        testPositiveLength("-0cm", true);
        testPositiveLength("0cm", true);
        // positive or zero
        assertEquals(BigDecimal.ZERO, LengthUnit.parsePositiveLength("0cm", LengthUnit.MM, false).stripTrailingZeros());
        // whatever the unit (use compareTo() to compare the value and not some other attribute)
        assertEquals(BigDecimal.ZERO.compareTo(LengthUnit.parsePositiveLength("0cm", LengthUnit.INCH, false)), 0);
        assertEquals(new BigDecimal("152").compareTo(LengthUnit.parsePositiveLength("15.2cm", LengthUnit.MM, true)), 0);

        assertEquals(new BigDecimal("152"), LengthUnit.parseLength("15.2cm", LengthUnit.MM).stripTrailingZeros());
        assertEquals(new BigDecimal("1.52"), LengthUnit.parseLength("15.2mm", LengthUnit.CM).stripTrailingZeros());
        assertEquals(new BigDecimal("2.54"), LengthUnit.parseLength("1in", LengthUnit.CM).stripTrailingZeros());
        assertEquals(new BigDecimal("1"), LengthUnit.parseLength("72pt", LengthUnit.INCH).stripTrailingZeros());
        assertEquals(new BigDecimal("2"), LengthUnit.parseLength("12pc", LengthUnit.INCH).stripTrailingZeros());
    }

    private void testPositiveLength(String l, boolean strict) {
        try {
            LengthUnit.parsePositiveLength(l, LengthUnit.CM, strict);
            fail("A length is positive");
        } catch (Exception e) {
            // ok
        }
    }

    public void testFormat() throws Exception {
        // assert that we use a plain string without exponent
        final BigDecimal bigDec = new BigDecimal("123.45678909E+10");
        assertTrue(bigDec.toString().endsWith("E+12"));
        assertEquals("1234567890900cm", LengthUnit.CM.format(bigDec));
        // assert that we don't use BigDecimal internally (otherwise we would have the exact
        // approximation of 2.357f, e.g. 2.3570001125335693)
        assertEquals("2.357cm", LengthUnit.CM.format(2.357f));

        // Double and Float.toString() can also return exponents
        {
            final String formattedDouble = LengthUnit.CM.format(123456789.09d);
            assertEquals("123456789.09cm", formattedDouble);
            assertEquals(123456789.09d, LengthUnit.parseLength(formattedDouble, LengthUnit.CM).doubleValue());
            final String formattedFloat = LengthUnit.CM.format(0.0005876f);
            // ATTN not equals since the float is first converted to double : 0.000587599992cm
            // assertEquals("0.0005876cm", formattedFloat);
            assertEquals(0.0005876f, LengthUnit.parseLength(formattedFloat, LengthUnit.CM).floatValue());
        }

        assertEquals("1cm", Length.MM(10).format(LengthUnit.CM));
    }

    public void testConvert() throws Exception {
        final MathContext mc = MathContext.DECIMAL64;
        final MathContext noRounding = new MathContext(mc.getPrecision(), RoundingMode.UNNECESSARY);
        // test rounding errors
        for (int i = 97; i <= 131; i++) {
            // test metric
            {
                final int expected = i * 10;
                final BigDecimal actual = LengthUnit.CM.convertTo(BigDecimal.valueOf(i), LengthUnit.MM);
                assertEquals(expected, actual.intValue());
                // there's should be no rounding necessary since a centimeter is exactly 10
                // millimeters
                assertEquals(expected, actual.intValueExact());
            }

            // test imperial (used to fail before LengthUnit.multiplierImp)
            {
                final int expected = i * 72;
                final BigDecimal actual = LengthUnit.INCH.convertTo(BigDecimal.valueOf(i), LengthUnit.POINT);
                assertEquals(expected, actual.intValue());
                // there's should be no rounding necessary since an inch is exactly 72 points
                assertEquals(expected, actual.intValueExact());
            }

            // test round trip with both
            {
                try {
                    LengthUnit.PICA.convertTo(BigDecimal.valueOf(i), LengthUnit.MM, noRounding);
                    fail("We want to test rounding errors");
                } catch (Exception e) {
                    // OK
                }
                final BigDecimal in_mm = LengthUnit.PICA.convertTo(BigDecimal.valueOf(i), LengthUnit.MM, mc);
                assertEquals(2 * i, LengthUnit.MM.convertTo(in_mm.multiply(BigDecimal.valueOf(2)), LengthUnit.PICA, mc).intValueExact());
            }
        }

        // test rounding errors due to type conversion
        {

            // conversion is coherent with native operators

            // the bigger the better since Double resolution decreases away from 0.
            final double d = 9876541000.321d;
            assertEquals(d * 72.0d, LengthUnit.INCH.convertTo(d, LengthUnit.POINT));

            // use float since it is converted to double by BigDecimal.valueOf() and some float
            // cannot be represented in double
            final double f = 987654.321f;
            assertEquals(f * 72.0f, LengthUnit.INCH.convertTo(f, LengthUnit.POINT));
        }
    }

    public void testLength() throws Exception {
        final Length ten_cm = Length.CM(10);
        assertEqualsWithCompareTo(Length.ZERO, ten_cm.subtract(ten_cm));
        assertEquals(1, ten_cm.divide(ten_cm).intValue());
        // test widen int to double and use native operator
        assertEquals(1d, ten_cm.divide(Length.CM(10d)));

        assertEqualsWithCompareTo(Length.MM(200), ten_cm.add(ten_cm));
        assertEqualsWithCompareTo(ten_cm.multiply(2), ten_cm.add(ten_cm));
    }
}
