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
import org.jopendocument.util.NumberUtils;
import org.jopendocument.util.Tuple2.List2;
import org.jopendocument.util.convertor.NumberConvertor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import net.jcip.annotations.Immutable;

/**
 * An OpenDocument length.
 * 
 * <p>
 * Note: for performance reason, this class has a {@link #compareTo(Length) natural ordering} that
 * is inconsistent with {@link #equals(Object)}.
 * </p>
 * 
 * @author Sylvain
 */
@Immutable
public class Length implements Comparable<Length> {

    static private final Length NONE = new Length(null, null, true);

    /**
     * The empty length.
     */
    static public final Length ZERO = new Length(0, LengthUnit.MM);

    /**
     * The lack of length, e.g. sheets in spreadsheets have no length of their own.
     * 
     * @return the nothing length, not <code>null</code>.
     * @see #isNone()
     */
    static public final Length getNone() {
        return NONE;
    }

    // for roundDecimalAmount()
    static private final MathContext STD_MC = MathContext.DECIMAL32;
    // for everything else, e.g. compute with more accuracy and then round
    static private final MathContext PRECISION_MC = MathContext.DECIMAL64;

    static {
        assert PRECISION_MC.getPrecision() > STD_MC.getPrecision();
    }

    /**
     * The context used in {@link #roundDecimalAmount()}.
     * 
     * @return the standard context.
     * @see #getPreciseContext()
     */
    public static final MathContext getStandardContext() {
        return STD_MC;
    }

    /**
     * The default context, always more accurate than {@link #getStandardContext()}.
     * 
     * @return the default context.
     */
    public static final MathContext getPreciseContext() {
        return PRECISION_MC;
    }

    public static final Length create(final String amount, final LengthUnit unit) {
        return create(new BigDecimal(amount), unit);
    }

    public static final Length create(final Number amount, final LengthUnit unit) {
        // TODO cache
        return new Length(amount, unit);
    }

    public static final Length MM(final Number amount) {
        return create(amount, LengthUnit.MM);
    }

    public static final Length MM(final String amount) {
        return create(amount, LengthUnit.MM);
    }

    public static final Length CM(final Number amount) {
        return create(amount, LengthUnit.CM);
    }

    public static final Length CM(final String amount) {
        return create(amount, LengthUnit.CM);
    }

    public static final Length INCH(final Number amount) {
        return create(amount, LengthUnit.INCH);
    }

    private final Number amount;
    private final LengthUnit unit;

    public Length(Number amount, LengthUnit unit) {
        this(amount, unit, false);
    }

    private Length(Number amount, LengthUnit unit, final boolean isNone) {
        super();
        if (!isNone) {
            if (amount == null)
                throw new NullPointerException("Null amount");
            if (unit == null)
                throw new NullPointerException("Null unit");
        }
        if (amount instanceof AtomicInteger)
            amount = amount.intValue();
        else if (amount instanceof AtomicLong)
            amount = amount.longValue();
        this.amount = amount;
        this.unit = unit;
    }

    public final boolean isNone() {
        return this.amount == null && this.unit == null;
    }

    public final boolean isDefined() {
        return !this.isNone();
    }

    /**
     * Whether this is an empty length.
     * 
     * @return <code>true</code> if the amount is 0.
     * @see #ZERO
     */
    public final boolean isZero() {
        return this.signum() == 0;
    }

    public final Number getAmount() {
        return this.amount;
    }

    public final BigDecimal getDecimalAmount() {
        return NumberConvertor.toBigDecimal(getAmount());
    }

    /**
     * The unit.
     * 
     * @return the unit, <code>null</code> if and only if this is {@link #getNone()}.
     */
    public final LengthUnit getUnit() {
        return this.unit;
    }

    public final Length convertToDecimal() {
        final Number amount = getAmount();
        final BigDecimal bd = NumberConvertor.toBigDecimal(amount);
        // null or already BigDecimal
        if (bd == amount)
            return this;
        else
            return new Length(bd, getUnit());
    }

    /**
     * Convert this into an instance with the same length but with the passed unit.
     * 
     * @param destUnit the unit of the result.
     * @return the same length in another unit.
     */
    public final Length convertTo(final LengthUnit destUnit) {
        if (destUnit == null)
            throw new NullPointerException("Null unit");
        if (isNone() || this.unit.equals(destUnit))
            return this;
        else
            return new Length(this.unit.convertTo(this.amount, destUnit, getPreciseContext()), destUnit);
    }

    /**
     * Optionally convert and then format this.
     * 
     * @param unit the unit to format to, <code>null</code> meaning don't convert.
     * @return the string value.
     * @see #format()
     */
    public final String format(final LengthUnit unit) {
        final Length l = unit == null ? this : this.convertTo(unit);
        return l.format();
    }

    /**
     * Format this length.
     * 
     * @return the string value, <code>null</code> if and only if this is {@link #getNone()}.
     */
    public final String format() {
        return isNone() ? null : this.unit.format(this.amount);
    }

    public final Length roundDecimalAmount() {
        return this.roundDecimalAmount(getStandardContext());
    }

    public final Length roundDecimalAmount(MathContext mc) {
        return roundDecimalAmount(mc, 0);
    }

    private final Length roundDecimalAmount(MathContext mc, final int precision) {
        if (this.getAmount() instanceof BigDecimal) {
            if (precision < 0) {
                mc = new MathContext(mc.getPrecision() + precision, mc.getRoundingMode());
            } else if (precision > 0) {
                mc = new MathContext(precision, mc.getRoundingMode());
            }
            return new Length(((BigDecimal) this.getAmount()).round(mc), this.getUnit());
        } else {
            return this;
        }
    }

    public final Length add(Length other) {
        if (isNone() || other.isNone())
            throw new IllegalArgumentException("Cannot add " + this + " and " + other);
        // only convert if necessary
        if (this.isZero())
            return other;
        else if (other.isZero())
            return this;
        final List2<Number> amounts = this.getSameUnitAmounts(other);
        final Number thisAmount = amounts.get0();
        final Number oAmount = amounts.get1();
        final Class<? extends Number> widerClass = NumberUtils.getWiderClass(thisAmount, oAmount);
        // MAYBE check for overflow
        final Number amount;
        if (widerClass == Double.class) {
            amount = thisAmount.doubleValue() + oAmount.doubleValue();
        } else if (widerClass == Float.class) {
            amount = thisAmount.floatValue() + oAmount.floatValue();
        } else if (widerClass == Byte.class || widerClass == Short.class || widerClass == Integer.class) {
            amount = thisAmount.intValue() + oAmount.intValue();
        } else if (widerClass == Long.class) {
            amount = thisAmount.longValue() + oAmount.longValue();
        } else {
            amount = NumberConvertor.toBigDecimal(thisAmount).add(NumberConvertor.toBigDecimal(oAmount));
        }
        return Length.create(amount, this.getUnit());
    }

    public final Length subtract(Length other) {
        return this.add(other.negate());
    }

    public final Length negate() {
        return this.isZero() || this.isNone() ? this : new Length(NumberUtils.negate(this.getAmount()), this.getUnit());
    }

    public final Length multiply(Number m) {
        if (isNone())
            throw new IllegalArgumentException("Cannot multiply " + this);
        if (this.isZero())
            return this;
        final Class<? extends Number> widerClass = NumberUtils.getWiderClass(this.getAmount(), m);
        // MAYBE check for overflow
        final Number amount;
        if (widerClass == Double.class) {
            amount = this.getAmount().doubleValue() * m.doubleValue();
        } else if (widerClass == Float.class) {
            amount = this.getAmount().floatValue() * m.floatValue();
        } else if (widerClass == Byte.class || widerClass == Short.class || widerClass == Integer.class) {
            amount = this.getAmount().intValue() * m.intValue();
        } else if (widerClass == Long.class) {
            amount = this.getAmount().longValue() * m.longValue();
        } else {
            // limit the number of digits
            amount = NumberConvertor.toBigDecimal(this.getAmount()).multiply(NumberConvertor.toBigDecimal(m), getPreciseContext());
        }
        return Length.create(amount, this.getUnit());
    }

    public final Number divide(Length other) {
        return this.divide(other, getPreciseContext());
    }

    // MathContext parameter needed since a Length isn't returned (otherwise we could call
    // roundDecimalAmount() afterwards)
    public final Number divide(Length other, final MathContext mc) {
        if (isNone() || other.isNone())
            throw new IllegalArgumentException("Cannot divide " + this + " and " + other);
        if (this == other)
            return 1;
        final List2<Number> amounts = this.getSameUnitAmounts(other);
        final Number thisAmount = amounts.get0();
        final Number oAmount = amounts.get1();
        final Class<? extends Number> widerClass = NumberUtils.getWiderClass(thisAmount, oAmount);
        final Number res;
        if (widerClass == Double.class) {
            res = thisAmount.doubleValue() / oAmount.doubleValue();
        } else if (widerClass == Float.class) {
            res = thisAmount.floatValue() / oAmount.floatValue();
        } else {
            // MathContext needed to avoid ArithmeticException
            res = NumberConvertor.toBigDecimal(thisAmount).divide(NumberConvertor.toBigDecimal(oAmount), mc);
        }
        return res;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.amount == null ? 0 : this.amount.hashCode());
        result = prime * result + (this.unit == null ? 0 : this.unit.hashCode());
        return result;
    }

    /**
     * Indicates whether some other object has the exact same amount and unit as this. If you want
     * to compare the value use {@link #compareTo(Length)}. E.g. <code>CM((float)0)</code> is not
     * equal to <code>CM(0)</code> and <code>MM(0)</code> is not <code>CM(0)</code>.
     * 
     * @param obj another length.
     * @return <code>true</code> if <code>obj</code> is equal to this.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Length other = (Length) obj;
        return CompareUtils.equals(this.getAmount(), other.getAmount()) && CompareUtils.equals(this.getUnit(), other.getUnit());
    }

    public final int signum() {
        return NumberUtils.signum(getAmount());
    }

    /**
     * Compare two lengths.
     * 
     * @param o the other length.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     * @throws IllegalArgumentException if this or <code>o</code> {@link #isNone()}.
     */
    @Override
    public int compareTo(Length o) throws IllegalArgumentException {
        // consistency with equals()
        if (isNone() && o.isNone())
            return 0;
        if (isNone() || o.isNone())
            throw new IllegalArgumentException("Cannot compare " + this + " and " + o);
        final int thisSignum = this.signum();
        final int oSignum = o.signum();
        if (thisSignum > oSignum) {
            return 1;
        } else if (thisSignum < oSignum) {
            return -1;
        } else if (thisSignum == 0) {
            return 0;
        } else {
            // same non-zero sign
            final List2<Number> amounts = getSameUnitAmounts(o);
            return NumberUtils.compare(amounts.get0(), amounts.get1());
        }
    }

    // ATTN doesn't check for isNone()
    private final List2<Number> getSameUnitAmounts(Length o) {
        final LengthUnit commonUnit = this.getUnit().getCommonUnit(o.getUnit());
        final Number thisN = this.convertTo(commonUnit).getAmount();
        final Number oN = o.convertTo(commonUnit).getAmount();
        return new List2<Number>(thisN, oN);
    }

    @Override
    public String toString() {
        return this.isNone() ? "<no length>" : this.format();
    }
}
