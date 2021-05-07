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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class NumberConvertor<T extends Number, U extends Number> implements ValueConvertor<T, U> {

    public static final NumberConvertor<Integer, Long> INT_TO_LONG = new NumberConvertor<Integer, Long>() {
        @Override
        public Long convert(Integer o) {
            return o.longValue();
        }

        @Override
        public Integer unconvert(Long o) {
            return o.intValue();
        }
    };

    public static final NumberConvertor<Short, Integer> SHORT_TO_INT = new NumberConvertor<Short, Integer>() {
        @Override
        public Integer convert(Short o) {
            return o.intValue();
        }

        @Override
        public Short unconvert(Integer o) {
            return o.shortValue();
        }
    };

    public static final <N1 extends Number, N2 extends Number> NumberConvertor<N1, N2> create(final Class<N1> c1, final Class<N2> c2, final boolean exact) {
        return new NumberConvertor<N1, N2>() {
            @Override
            public N2 convert(N1 o) {
                return exact ? convertExact(o, c2) : convert(o, c2);
            }

            @Override
            public N1 unconvert(N2 o) {
                return exact ? convertExact(o, c1) : convert(o, c1);
            }
        };
    }

    /**
     * Convert from one class of {@link Number} to another. Necessary since new Integer(123) isn't
     * equal to new Long(123).
     * 
     * @param <N> type of desired Number.
     * @param n the instance to convert, e.g. new Integer(123).
     * @param clazz desired class of Number, e.g. Long.class.
     * @return <code>n</code> as an instance of <code>clazz</code>, e.g. new Long(123).
     */
    public static <N extends Number> N convert(Number n, Class<N> clazz) {
        final Number res;
        if (n == null || n.getClass() == clazz || clazz == Number.class) {
            res = n;
        } else if (clazz == Short.class) {
            res = n.shortValue();
        } else if (clazz == Integer.class) {
            res = n.intValue();
        } else if (clazz == Long.class) {
            res = n.longValue();
        } else if (clazz == Byte.class) {
            res = n.byteValue();
        } else if (clazz == BigInteger.class) {
            if (n instanceof BigDecimal)
                res = ((BigDecimal) n).toBigInteger();
            else if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong)
                res = BigInteger.valueOf(n.longValue());
            else if (n instanceof Float || n instanceof Double)
                res = BigDecimal.valueOf(n.doubleValue()).toBigInteger();
            else
                res = new BigInteger(n.toString());
        } else if (clazz == AtomicInteger.class) {
            res = new AtomicInteger(n.intValue());
        } else if (clazz == AtomicLong.class) {
            res = new AtomicLong(n.longValue());
        } else if (clazz == BigDecimal.class) {
            res = toBigDecimal(n);
        } else if (clazz == Double.class) {
            res = n.doubleValue();
        } else if (clazz == Float.class) {
            res = n.floatValue();
        } else {
            throw new IllegalArgumentException("unknown class: " + clazz);
        }
        return clazz.cast(res);
    }

    public static BigDecimal toBigDecimal(final Number n) {
        final BigDecimal res;
        if (n == null || n instanceof BigDecimal) {
            res = (BigDecimal) n;
        } else if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
            res = BigDecimal.valueOf(n.longValue());
        } else if (n instanceof Float || n instanceof Double) {
            res = BigDecimal.valueOf(n.doubleValue());
        } else if (n instanceof BigInteger) {
            res = new BigDecimal((BigInteger) n);
        } else {
            res = new BigDecimal(n.toString());
        }
        return res;
    }

    public static class OverflowException extends ArithmeticException {
        public OverflowException(Number n, Class<? extends Number> clazz) {
            super("Cannot convert to " + clazz + " : " + n);
        }

        @Override
        public OverflowException initCause(Throwable cause) {
            super.initCause(cause);
            return this;
        }
    }

    public static class RoundingException extends ArithmeticException {
        public RoundingException(Number n, Class<? extends Number> clazz) {
            super("Rounding necessary for " + clazz + " : " + n + " (" + n.getClass() + ")");
        }
    }

    public static final BigDecimal MAX_FLOAT = BigDecimal.valueOf(Float.MAX_VALUE);
    public static final BigDecimal MIN_FLOAT = MAX_FLOAT.negate();
    public static final BigDecimal MAX_DOUBLE = BigDecimal.valueOf(Double.MAX_VALUE);
    public static final BigDecimal MIN_DOUBLE = MAX_DOUBLE.negate();

    public static <N extends Number> N convertExact(Number n, Class<N> clazz) throws OverflowException, RoundingException {
        final Number res;
        if (n == null || n.getClass() == clazz || clazz == Number.class) {
            res = n;
        } else if (n instanceof BigDecimal) {
            // cannot use *ValueExact() since we want to differentiate between overflow and rounding
            final BigDecimal bd = (BigDecimal) n;
            if (clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == AtomicInteger.class || clazz == Long.class || clazz == AtomicLong.class || clazz == BigInteger.class) {
                final BigInteger bi;
                try {
                    bi = bd.toBigIntegerExact();
                } catch (ArithmeticException e) {
                    throw new RoundingException(n, clazz);
                }
                if (clazz == BigInteger.class) {
                    res = bi;
                } else {
                    try {
                        res = convertExact(bi, clazz);
                    } catch (RoundingException e) {
                        // cannot be a rounding error since we pass a BigInteger
                        throw new IllegalStateException(e);
                    } catch (OverflowException e) {
                        throw new OverflowException(n, clazz).initCause(e);
                    }
                }
            } else if (clazz == Float.class) {
                if (bd.compareTo(MAX_FLOAT) > 0 || bd.compareTo(MIN_FLOAT) < 0)
                    throw new OverflowException(n, clazz);
                res = n.floatValue();
            } else if (clazz == Double.class) {
                if (bd.compareTo(MAX_DOUBLE) > 0 || bd.compareTo(MIN_DOUBLE) < 0)
                    throw new OverflowException(n, clazz);
                res = n.doubleValue();
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
        } else if (clazz == Byte.class) {
            final byte value = n.byteValue();
            if (n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
                if (value != n.longValue())
                    throw new OverflowException(n, clazz);
            } else if (n instanceof BigInteger) {
                if (((BigInteger) n).bitLength() >= Byte.SIZE)
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Float || n instanceof Double) {
                if (n.doubleValue() > Byte.MAX_VALUE || n.doubleValue() < Byte.MIN_VALUE)
                    throw new OverflowException(n, clazz);
                else
                    throw new RoundingException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = value;
        } else if (clazz == Short.class) {
            final short value = n.shortValue();
            if (n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
                if (value != n.longValue())
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Byte) {
            } else if (n instanceof BigInteger) {
                if (((BigInteger) n).bitLength() >= Short.SIZE)
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Float || n instanceof Double) {
                if (n.doubleValue() > Short.MAX_VALUE || n.doubleValue() < Short.MIN_VALUE)
                    throw new OverflowException(n, clazz);
                else
                    throw new RoundingException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = value;
        } else if (clazz == Integer.class || clazz == AtomicInteger.class) {
            final int value = n.intValue();
            if (n instanceof Long || n instanceof AtomicLong) {
                if (value != n.longValue())
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger) {
            } else if (n instanceof BigInteger) {
                if (((BigInteger) n).bitLength() >= Integer.SIZE)
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Float || n instanceof Double) {
                if (n.doubleValue() > Integer.MAX_VALUE || n.doubleValue() < Integer.MIN_VALUE)
                    throw new OverflowException(n, clazz);
                else
                    throw new RoundingException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = clazz == Integer.class ? Integer.valueOf(value) : new AtomicInteger(value);
        } else if (clazz == Long.class || clazz == AtomicLong.class) {
            final long value = n.longValue();
            if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
            } else if (n instanceof BigInteger) {
                if (((BigInteger) n).bitLength() >= Long.SIZE)
                    throw new OverflowException(n, clazz);
            } else if (n instanceof Float || n instanceof Double) {
                // at the limits of Long, Double have less than one unit of precision, so since both
                // case of this if throw exceptions, be lenient to allow testing the limits
                if (n.doubleValue() >= Long.MAX_VALUE || n.doubleValue() <= Long.MIN_VALUE)
                    throw new OverflowException(n, clazz);
                else
                    throw new RoundingException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = clazz == Long.class ? Long.valueOf(value) : new AtomicLong(value);
        } else if (clazz == BigInteger.class) {
            if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
            } else if (n instanceof Float || n instanceof Double) {
                throw new RoundingException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = BigInteger.valueOf(n.longValue());
        } else if (clazz == BigDecimal.class) {
            res = toBigDecimal(n);
        } else if (clazz == Double.class) {
            if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong || n instanceof Float) {
            } else if (n instanceof BigInteger) {
                final BigInteger bi = (BigInteger) n;
                // can use toBigIntegerExact() since the precision is low at the limits
                if (bi.compareTo(MAX_DOUBLE.toBigIntegerExact()) > 0 || bi.compareTo(MIN_DOUBLE.toBigIntegerExact()) < 0)
                    throw new OverflowException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = n.doubleValue();
        } else if (clazz == Float.class) {
            if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof AtomicInteger || n instanceof Long || n instanceof AtomicLong) {
            } else if (n instanceof Double) {
                if (Math.abs(n.doubleValue()) > Float.MAX_VALUE)
                    throw new OverflowException(n, clazz);
            } else if (n instanceof BigInteger) {
                final BigInteger bi = (BigInteger) n;
                // can use toBigIntegerExact() since the precision is low at the limits
                if (bi.compareTo(MAX_FLOAT.toBigIntegerExact()) > 0 || bi.compareTo(MIN_FLOAT.toBigIntegerExact()) < 0)
                    throw new OverflowException(n, clazz);
            } else {
                throw new IllegalStateException("Unknown class " + n.getClass());
            }
            res = n.floatValue();
        } else {
            throw new IllegalArgumentException("Unknown class: " + clazz);
        }
        return clazz.cast(res);
    }
}
