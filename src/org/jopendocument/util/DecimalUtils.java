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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DecimalUtils {

    /**
     * Useful to pass to {@link BigDecimal#divide(BigDecimal, MathContext)} since :
     * <ul>
     * <li>1234,567.divide(33000) throws java.lang.ArithmeticException: Non-terminating decimal
     * expansion; no exact representable decimal result</li>
     * <li>1234,567.divide(33000, RoundingMode.HALF_UP) = 0,037 : since it isn't exact uses the
     * dividend scale, thus loosing a lot of precision</li>
     * <li>1234,567.divide(33000, HIGH_PRECISION) = 0.03741112121212121212121212121212121</li>
     * </ul>
     * You also can't just use the precision of the dividend :
     * 
     * <pre>
     * lowPrecision = new BigDecimal("1E+3");
     * lowPrecision.divide(new BigDecimal("7E+5"), new MathContext(lowPrecision.precision()))) == 0.001;
     * lowPrecision.divide(new BigDecimal("7E+5"), HIGH_PRECISION) == 0.001428571428571428571428571428571429;
     * </pre>
     * <p>
     * Note: {@link MathContext#DECIMAL128} is different since it uses
     * {@link RoundingMode#HALF_EVEN}
     * </p>
     */
    static public final MathContext HIGH_PRECISION = new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_UP);

    /**
     * The number of int digits.
     * 
     * @param d the decimal to use, eg "123.45".
     * @return number of int digits, never less than 1, eg 3.
     */
    static public int intDigits(BigDecimal d) {
        return Math.max(1, d.precision() - d.scale());
    }

    /**
     * The number of decimal digits.
     * 
     * @param d the decimal to use, eg "123.45" or "120".
     * @return number of decimal digits, never less than 0, eg 2 or 0.
     */
    static public int decimalDigits(BigDecimal d) {
        return Math.max(0, d.stripTrailingZeros().scale());
    }

    static public BigDecimal round(BigDecimal d, int decimalDigits) {
        return round(d, decimalDigits, RoundingMode.HALF_UP);
    }

    /**
     * Round the passed BigDecimal so that only <code>decimalDigits</code> remain after the point.
     * 
     * @param d the decimal to use, eg "123.4567".
     * @param decimalDigits the number of digits after the point, eg 1.
     * @param rmode how to round.
     * @return d rounded so that only <code>decimalDigits</code> remain, eg "123.5".
     */
    static public BigDecimal round(BigDecimal d, int decimalDigits, RoundingMode rmode) {
        return d.round(new MathContext(intDigits(d) + decimalDigits, rmode));
    }

}
