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

import org.jopendocument.util.convertor.NumberConvertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NumberUtils {

    /**
     * Test class and numerical equality. E.g. {@link BigDecimal#equals(Object)} also tests the
     * scale.
     * 
     * @param <N> type of number.
     * @param n1 first number, can be <code>null</code>.
     * @param n2 second number, can be <code>null</code>.
     * @return <code>true</code> if <code>n1</code> and <code>n2</code> have the same class and are
     *         numerically equal.
     * @see #areNumericallyEqual(Number, Number)
     */
    static public final <N extends Number> boolean areEqual(final N n1, final N n2) {
        if (n1 == null && n2 == null)
            return true;
        if (n1 == null || n2 == null)
            return false;
        final Class<? extends Number> n1Class = n1.getClass();
        if (n1Class != n2.getClass())
            return false;
        // Atomic* don't implement equals()
        if (n1Class == AtomicInteger.class || n1Class == AtomicLong.class)
            return n1.longValue() == n2.longValue();
        else if (n1Class == BigDecimal.class)
            return ((BigDecimal) n1).compareTo((BigDecimal) n2) == 0;
        else
            return n1.equals(n2);
    }

    /**
     * Test numerical equality (but ignore class).
     * 
     * @param n1 first number, can be <code>null</code>.
     * @param n2 second number, can be <code>null</code>.
     * @return <code>true</code> if <code>n1</code> and <code>n2</code> are numerically equal.
     * @see #compare(Number, Number)
     */
    static public final boolean areNumericallyEqual(final Number n1, final Number n2) {
        if (n1 == null && n2 == null)
            return true;
        if (n1 == null || n2 == null)
            return false;
        return compare(n1, n2) == 0;
    }

    /**
     * Compare two arbitrary numbers.
     * 
     * @param n1 first number, not <code>null</code>.
     * @param n2 second number, not <code>null</code>.
     * @return a negative integer, zero, or a positive integer as n1 is less than, equal to, or
     *         greater than n2.
     * @see Comparable#compareTo(Object)
     */
    static public final int compare(final Number n1, final Number n2) {
        Class<? extends Number> biggerClass = getWiderClass(n1, n2);
        // Atomic* aren't Comparable
        if (biggerClass == AtomicInteger.class)
            biggerClass = Integer.class;
        else if (biggerClass == AtomicLong.class)
            biggerClass = Long.class;
        return compare(n1, n2, biggerClass);
    }

    static private final <N extends Number> int compare(final Number n1, final Number n2, Class<N> clazz) {
        final N n1Converted = NumberConvertor.convertExact(n1, clazz);
        final N n2Converted = NumberConvertor.convertExact(n2, clazz);
        @SuppressWarnings("unchecked")
        final Comparable<N> comparable = (Comparable<N>) n1Converted;
        return comparable.compareTo(n2Converted);
    }

    /**
     * Return a class wide enough for both numbers. E.g. for Integer and Short, Integer ; for
     * BigInteger and Float, BigDecimal.
     * 
     * @param n1 first number, not <code>null</code>.
     * @param n2 second number, not <code>null</code>.
     * @return a class wide enough for both numbers.
     * @see NumberConvertor#convertExact(Number, Class)
     */
    static public final Class<? extends Number> getWiderClass(final Number n1, final Number n2) {
        final Class<? extends Number> n1Class = n1.getClass();
        final Class<? extends Number> n2Class = n2.getClass();
        if (n1Class == n2Class)
            return n1Class;
        if (n1Class == BigDecimal.class || n2Class == BigDecimal.class)
            return BigDecimal.class;

        final boolean n1isFloat = n1Class == Float.class || n1Class == Double.class;
        final boolean n2isFloat = n2Class == Float.class || n2Class == Double.class;
        if (n1isFloat && n2isFloat) {
            // since classes are different, at least one is Double
            return Double.class;
        } else if (n1isFloat || n2isFloat) {
            // the only class (except the already handled BigDecimal) that can overflow in a Double
            // is BigInteger
            if (n1Class == BigInteger.class || n2Class == BigInteger.class)
                return BigDecimal.class;
            else
                return Double.class;
        }

        // integers or BigInteger
        if (n1Class == BigInteger.class || n2Class == BigInteger.class)
            return BigInteger.class;
        else if (n1Class == Long.class || n2Class == Long.class || n1Class == AtomicLong.class || n2Class == AtomicLong.class)
            return Long.class;
        else if (n1Class == Integer.class || n2Class == Integer.class || n1Class == AtomicInteger.class || n2Class == AtomicInteger.class)
            return Integer.class;
        else if (n1Class == Short.class || n2Class == Short.class)
            return Short.class;
        else if (n1Class == Byte.class || n2Class == Byte.class)
            return Byte.class;
        else
            throw new IllegalStateException("Unknown classes " + n1Class + " / " + n2Class);
    }

    /**
     * Whether <code>n</code> has a non-zero fractional part.
     * 
     * @param n a number.
     * @return <code>true</code> if there is a non-zero fractional part, e.g. <code>true</code> for
     *         1.3d and <code>false</code> for <code>new BigDecimal("1.00")</code>.
     */
    static public final boolean hasFractionalPart(Number n) {
        if (n instanceof Integer || n instanceof Long || n instanceof Short || n instanceof Byte || n instanceof BigInteger || n instanceof AtomicLong || n instanceof AtomicInteger)
            return false;

        final BigDecimal bd;
        if (n instanceof BigDecimal)
            bd = (BigDecimal) n;
        else if (n instanceof Double || n instanceof Float)
            bd = new BigDecimal(n.doubleValue());
        else
            bd = new BigDecimal(n.toString());
        return DecimalUtils.decimalDigits(bd) > 0;
    }

    static final int MAX_LONG_LENGTH = String.valueOf(Long.MAX_VALUE).length();

    static public final int intDigits(final long l) {
        final long x = Math.abs(l);
        long p = 10;
        int i = 1;
        while (x >= p && i < MAX_LONG_LENGTH) {
            p = 10 * p;
            i++;
        }
        return i;
    }

    /**
     * The number of digits of the integer part in decimal representation.
     * 
     * @param n a number, e.g. 123.45.
     * @return the number of digits of the integer part, e.g. 3.
     */
    static public final int intDigits(Number n) {
        if (n instanceof Integer || n instanceof Long || n instanceof Short || n instanceof Byte || n instanceof AtomicLong || n instanceof AtomicInteger)
            return intDigits(n.longValue());

        final BigDecimal bd;
        if (n instanceof BigDecimal)
            bd = (BigDecimal) n;
        else if (n instanceof BigInteger)
            bd = new BigDecimal((BigInteger) n);
        else if (n instanceof Double || n instanceof Float)
            bd = new BigDecimal(n.doubleValue());
        else
            bd = new BigDecimal(n.toString());
        return DecimalUtils.intDigits(bd);
    }

    /**
     * High precision divide.
     * 
     * @param n the dividend.
     * @param d the divisor.
     * @return <code>n / d</code>.
     * @see DecimalUtils#HIGH_PRECISION
     */
    static public Number divide(Number n, double d) {
        if (d == 1)
            return n;
        if (n instanceof BigDecimal) {
            return ((BigDecimal) n).divide(new BigDecimal(d), DecimalUtils.HIGH_PRECISION);
        } else if (n instanceof BigInteger) {
            return new BigDecimal((BigInteger) n).divide(new BigDecimal(d), DecimalUtils.HIGH_PRECISION);
        } else {
            return n.doubleValue() / d;
        }
    }

    static public Number negate(Number n) {
        if (n == null)
            return null;
        final Number res;
        final Class<? extends Number> clazz = n.getClass();
        if (n instanceof BigDecimal) {
            res = ((BigDecimal) n).negate();
        } else if (n instanceof BigInteger) {
            res = ((BigInteger) n).negate();
        } else if (clazz == Short.class) {
            // cast needed since '-' widens to int
            res = (short) -n.shortValue();
        } else if (clazz == Integer.class) {
            res = -n.intValue();
        } else if (clazz == Long.class) {
            res = -n.longValue();
        } else if (clazz == Byte.class) {
            // cast needed since '-' widens to int
            res = (byte) -n.byteValue();
        } else if (clazz == AtomicInteger.class) {
            res = new AtomicInteger(-n.intValue());
        } else if (clazz == AtomicLong.class) {
            res = new AtomicLong(-n.longValue());
        } else if (clazz == Double.class) {
            res = -n.doubleValue();
        } else if (clazz == Float.class) {
            res = -n.floatValue();
        } else {
            // fallback for unknown class
            res = new BigDecimal(n.toString()).negate();
        }
        return res;
    }

    static public int signum(Number n) {
        if (n == null)
            throw new NullPointerException();
        final int res;
        final Class<? extends Number> clazz = n.getClass();
        if (n instanceof BigDecimal) {
            res = ((BigDecimal) n).signum();
        } else if (n instanceof BigInteger) {
            res = ((BigInteger) n).signum();
        } else if (clazz == Double.class) {
            res = (int) Math.signum(n.doubleValue());
        } else if (clazz == Float.class) {
            res = (int) Math.signum(n.floatValue());
        } else if (clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == AtomicInteger.class || clazz == AtomicLong.class) {
            final long l = n.longValue();
            if (l == 0)
                res = 0;
            else if (l < 0)
                res = -1;
            else
                res = 1;
        } else {
            // limit overflow for unknown class
            res = (int) Math.signum(n.doubleValue());
        }
        return res;
    }
}
