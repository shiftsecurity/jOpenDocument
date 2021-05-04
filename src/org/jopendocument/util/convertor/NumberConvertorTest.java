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

package org.jopendocument.util.convertor;

import org.jopendocument.util.NumberUtils;
import org.jopendocument.util.convertor.NumberConvertor.OverflowException;
import org.jopendocument.util.convertor.NumberConvertor.RoundingException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

public class NumberConvertorTest extends TestCase {

    private static final List<Class<? extends Number>> numberClasses;
    private static final Map<Class<? extends Number>, Number> fives = new HashMap<Class<? extends Number>, Number>();
    private static final Map<Class<? extends Number>, Number> minValues = new HashMap<Class<? extends Number>, Number>();
    private static final Map<Class<? extends Number>, Number> maxValues = new HashMap<Class<? extends Number>, Number>();
    static {
        numberClasses = new ArrayList<Class<? extends Number>>();
        numberClasses.add(Byte.class);
        numberClasses.add(Short.class);
        numberClasses.add(AtomicInteger.class);
        numberClasses.add(Integer.class);
        numberClasses.add(AtomicLong.class);
        numberClasses.add(Long.class);
        numberClasses.add(BigInteger.class);
        numberClasses.add(Float.class);
        numberClasses.add(Double.class);
        numberClasses.add(BigDecimal.class);

        fives.put(Byte.class, Byte.valueOf((byte) 5));
        fives.put(Short.class, Short.valueOf((short) 5));
        fives.put(Integer.class, Integer.valueOf(5));
        fives.put(AtomicInteger.class, new AtomicInteger(5));
        fives.put(Long.class, Long.valueOf(5));
        fives.put(AtomicLong.class, new AtomicLong(5));
        fives.put(BigInteger.class, BigInteger.valueOf(5));
        fives.put(BigDecimal.class, BigDecimal.valueOf(5));
        fives.put(Float.class, 5f);
        fives.put(Double.class, 5d);
    }

    private static void fillValues() throws IllegalAccessException, NoSuchFieldException {
        for (final Class<?> c : new Class<?>[] { Byte.class, Short.class, Integer.class, Long.class }) {
            minValues.put(c.asSubclass(Number.class), (Number) c.getField("MIN_VALUE").get(null));
            maxValues.put(c.asSubclass(Number.class), (Number) c.getField("MAX_VALUE").get(null));
        }
        minValues.put(AtomicInteger.class, new AtomicInteger((Integer) minValues.get(Integer.class)));
        minValues.put(AtomicLong.class, new AtomicLong((Long) minValues.get(Long.class)));
        maxValues.put(AtomicInteger.class, new AtomicInteger((Integer) maxValues.get(Integer.class)));
        maxValues.put(AtomicLong.class, new AtomicLong((Long) maxValues.get(Long.class)));

        maxValues.put(Float.class, Float.MAX_VALUE);
        minValues.put(Float.class, -Float.MAX_VALUE);
        maxValues.put(Double.class, Double.MAX_VALUE);
        minValues.put(Double.class, -Double.MAX_VALUE);

        // BigInteger and BigDecimal have no limits
    }

    public static Map<Class<? extends Number>, Number> getMinValues() throws IllegalAccessException, NoSuchFieldException {
        if (minValues.isEmpty()) {
            fillValues();
        }
        return minValues;
    }

    public static Map<Class<? extends Number>, Number> getMaxValues() throws IllegalAccessException, NoSuchFieldException {
        if (maxValues.isEmpty()) {
            fillValues();
        }
        return maxValues;
    }

    public static boolean isFloat(Number n) {
        return n instanceof Float || n instanceof Double;
    }

    public void testConvert() throws Exception {
        for (final Class<? extends Number> nClass : numberClasses) {
            final Number ourFive = fives.get(nClass);
            assert ourFive.getClass() == nClass;
            for (final Number five : fives.values()) {
                // inexact
                {
                    final Number converted = NumberConvertor.convert(five, nClass);
                    assertTrue(NumberUtils.areEqual(ourFive, converted));
                    // and back
                    assertTrue(NumberUtils.areEqual(five, NumberConvertor.convert(converted, five.getClass())));
                }
                // exact
                {
                    assertConvertExactAndBack(five, nClass, ourFive);
                }
            }
            assertConvertExactAtLimits(getMinValues().get(nClass), nClass);
            assertConvertExactAtLimits(getMaxValues().get(nClass), nClass);
        }
    }

    private void assertConvertExactAndBack(final Number n, final Class<? extends Number> destClass, final Number expected) {
        final Number converted = assertConvertExact(n, destClass, expected, false);
        // if we could convert it one way, try the opposite
        if (converted != null)
            assertConvertExact(converted, n.getClass(), n, false);
    }

    private Number assertConvertExact(final Number n, final Class<? extends Number> destClass, final Number expected, final boolean offLimits) {
        // convert() never tries to round floats to integers
        // BigDecimal is arbitrary-precision so it never has to round
        if (isFloat(n) && !isFloat(expected) && !(expected instanceof BigDecimal)) {
            try {
                NumberConvertor.convertExact(n, destClass);
                // overflow is a bigger problem than rounding
                if (offLimits)
                    fail("Should have thrown a OverflowException from " + n.getClass() + " to " + destClass);
                else
                    fail("Should have thrown a RoundingException from " + n.getClass() + " to " + destClass);
            } catch (RuntimeException e) {
                // OK
                if (!(offLimits && e instanceof OverflowException) && !(!offLimits && e instanceof RoundingException))
                    throw e;
            }
            return null;
        } else if (offLimits) {
            try {
                NumberConvertor.convertExact(n, destClass);
                fail("Should have thrown an OverflowException");
            } catch (OverflowException e) {
                // OK
            }
            return null;
        } else {
            final Number converted = NumberConvertor.convertExact(n, destClass);
            assertEquals(destClass, converted.getClass());
            assertTrue(NumberUtils.areEqual(expected, converted));
            return converted;
        }
    }

    private void assertConvertExactAtLimits(final Number limitValue, final Class<? extends Number> nClass) {
        if (limitValue != null) {
            assertEquals(nClass, limitValue.getClass());
            // BigDecimal has no limits
            final BigDecimal bd = NumberConvertor.toBigDecimal(limitValue);
            // the limit is OK
            assertConvertExactAndBack(bd, nClass, limitValue);
            // beyond is not
            final BigDecimal beyondLimit;
            if (isFloat(limitValue))
                beyondLimit = bd.multiply(BigDecimal.valueOf(2));
            else
                beyondLimit = bd.signum() > 0 ? bd.add(BigDecimal.ONE) : bd.subtract(BigDecimal.ONE);
            assertBeyondLimit(bd, beyondLimit);
            assertConvertExact(beyondLimit, nClass, limitValue, true);
            // test that when trying to convert a float it first checks for overflow, except for
            // Double since beyondLimit.doubleValue() would be brought back inside the limits
            if (nClass != Double.class) {
                assertBeyondLimit(bd, BigDecimal.valueOf(beyondLimit.doubleValue()));
                assertConvertExact(beyondLimit.doubleValue(), nClass, limitValue, true);
            }
        }
    }

    private void assertBeyondLimit(final BigDecimal limit, final BigDecimal beyondLimit) {
        assertEquals(limit.signum(), beyondLimit.signum());
        assertTrue(beyondLimit.abs().compareTo(limit.abs()) > 0);
    }
}
