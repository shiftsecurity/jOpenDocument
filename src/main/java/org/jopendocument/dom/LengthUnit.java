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

import org.jopendocument.util.DecimalUtils;
import org.jopendocument.util.convertor.NumberConvertor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Units of length.
 * 
 * @author Sylvain CUAZ
 * @see <a href="http://www.w3.org/TR/xsl/#d0e5752">W3C Definitions</a>
 */
public enum LengthUnit {
    /**
     * The millimetre.
     */
    MM("mm", BigDecimal.ONE),
    /**
     * The centimetre.
     */
    CM("cm", BigDecimal.TEN),
    /**
     * The inch.
     */
    INCH("in", new BigDecimal("25.4"), BigDecimal.valueOf(72)),
    /**
     * The pica (12pt i.e. 1/6in).
     */
    PICA("pc", INCH.multiplier.divide(BigDecimal.valueOf(6), DecimalUtils.HIGH_PRECISION), BigDecimal.valueOf(12)),
    /**
     * The point (1/72in).
     */
    POINT("pt", INCH.multiplier.divide(INCH.multiplierImp, DecimalUtils.HIGH_PRECISION), BigDecimal.ONE);

    private final String symbol;
    // millimetre multiplier
    private final BigDecimal multiplier;
    // point multiplier
    private final BigDecimal multiplierImp;

    private LengthUnit(final String abbr, BigDecimal multiplier) {
        this(abbr, multiplier, null);
    }

    private LengthUnit(final String abbr, BigDecimal multiplier, final BigDecimal multiplierImp) {
        this.symbol = abbr;
        if (multiplier == null)
            throw new NullPointerException("Null millimetre multiplier");
        this.multiplier = multiplier;
        this.multiplierImp = multiplierImp;
    }

    /**
     * The symbol for this unit of length.
     * 
     * @return the symbol, eg "cm".
     */
    public final String getSymbol() {
        return this.symbol;
    }

    private final boolean isMetric() {
        return this.multiplierImp == null;
    }

    /**
     * Convert from this unit to another.
     * 
     * @param d a length, eg 1.
     * @param other another unit, eg {@link #CM}.
     * @return the {@link RoundingMode#HALF_UP rounded} result, eg 2.54 if this is {@link #INCH}
     */
    public final BigDecimal convertTo(final BigDecimal d, LengthUnit other) {
        return this.convertTo(d, other, DecimalUtils.HIGH_PRECISION);
    }

    public final BigDecimal convertTo(final BigDecimal d, LengthUnit other, final MathContext mc) {
        if (this == other) {
            return d;
        } else if (!this.isMetric() && !other.isMetric()) {
            return d.multiply(this.multiplierImp).divide(other.multiplierImp, mc);
        } else {
            return d.multiply(this.multiplier).divide(other.multiplier, mc);
        }
    }

    /**
     * Convert from this unit to another. If <code>d</code> is a Float or Double then native
     * operators are used, otherwise it is converted to {@link BigDecimal} and converted using
     * <code>mc</code>. NOTE : this method doesn't always return the same type that it was passed,
     * e.g. passing <code>(int)1</code> can return <code>new BigDecimal("2.54")</code>.
     * 
     * @param d a length, e.g. 1.
     * @param other another unit, e.g. {@link #CM}.
     * @param mc only used if <code>d</code> is not a {@link Float} or {@link Double}.
     * @return the result, e.g. 2.54 if this is {@link #INCH}.
     */
    public final Number convertTo(final Number d, LengthUnit other, final MathContext mc) {
        // convert floats as it prevents spurious decimal part
        if (d.getClass() == Double.class) {
            return this.convertTo(d.doubleValue(), other);
        } else if (d.getClass() == Float.class) {
            return this.convertTo(d.floatValue(), other);
        } else {
            // don't convert back to d.getClass() as it could be useless (e.g. 1cm in inch would
            // return 0)
            return this.convertTo(NumberConvertor.toBigDecimal(d), other, mc);
        }
    }

    public final double convertTo(final double d, LengthUnit other) {
        if (this == other) {
            return d;
        } else if (!this.isMetric() && !other.isMetric()) {
            return d * this.multiplierImp.doubleValue() / other.multiplierImp.doubleValue();
        } else {
            return d * this.multiplier.doubleValue() / other.multiplier.doubleValue();
        }
    }

    public final float convertTo(final float d, LengthUnit other) {
        if (this == other) {
            return d;
        } else {
            return (float) convertTo((double) d, other);
        }
    }

    public final String format(final Number n) {
        if (n == null)
            throw new NullPointerException();
        // don't use exponents
        final String s;
        if (n instanceof BigDecimal) {
            s = ((BigDecimal) n).toPlainString();
        } else if ((n instanceof Float || n instanceof Double) && (n.doubleValue() < 0.001d || n.doubleValue() >= 10000000d)) {
            // test value (see Float.toString()) since the DecimalFormat should be slower and it
            // only deals with double, e.g. DF.format(2.357f) yields "2.3570001125335693"
            synchronized (LengthUnit.class) {
                s = DF.format(n);
            }
        } else {
            s = n.toString();
        }
        return s + getSymbol();
    }

    public final LengthUnit getCommonUnit(final LengthUnit o) {
        if (this.equals(o))
            return this;

        final boolean thisMetric = this.isMetric();
        final boolean oMetric = o.isMetric();
        final boolean returnThis;
        if (thisMetric && oMetric) {
            returnThis = this.compareTo(o) < 0;
        } else if (!thisMetric && !oMetric) {
            returnThis = this.compareTo(o) > 0;
        } else {
            returnThis = thisMetric;
        }
        return returnThis ? this : o;
    }

    public static final LengthUnit fromSymbol(final String s) {
        for (final LengthUnit lu : values())
            if (lu.symbol.equals(s))
                return lu;
        return null;
    }

    // match all lengths in relaxNG : length, nonNegativeLength and positiveLength ; eg 15.2cm
    // if you want to tell them apart do it in java on the BigDecimal.
    private static final Pattern lenghPattern = Pattern.compile("(-?\\d+(\\.\\d+)?)(\\p{Alpha}+)?");
    // some Number use exponents
    private static final Format DF = new DecimalFormat("0.############", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    // 0: value, eg 15 ; 1: unit, eg "cm" or null
    private static final String[] parseLength2String(String l) {
        final Matcher m = lenghPattern.matcher(l);
        if (!m.matches())
            throw new IllegalStateException("unable to parse " + l);
        return new String[] { m.group(1), m.group(3) };
    }

    public static final Length parseLength(final String l) {
        if (l == null)
            return Length.getNone();
        final String[] valAndUnit = parseLength2String(l);
        final LengthUnit unit = LengthUnit.fromSymbol(valAndUnit[1]);
        if (unit == null)
            throw new IllegalStateException("unknown unit " + unit);
        return Length.create(new BigDecimal(valAndUnit[0]), unit);
    }

    /**
     * Parse a length.
     * 
     * @param l the length, can be <code>null</code>, e.g. "2.0cm".
     * @param to the result unit, e.g. {@link LengthUnit#MM}.
     * @return the parsed length, <code>null</code> if <code>l</code> is, e.g. 20.
     */
    public static final BigDecimal parseLength(final String l, final LengthUnit to) {
        return parseLength(l).convertTo(to).getDecimalAmount();
    }

    public static final BigDecimal parsePositiveLength(final String l, final LengthUnit to, boolean strict) {
        final BigDecimal res = parseLength(l, to);
        if (res.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException(res + " < 0");
        if (strict && res.compareTo(BigDecimal.ZERO) == 0)
            throw new IllegalArgumentException(res + " == 0");
        return res;
    }
}
