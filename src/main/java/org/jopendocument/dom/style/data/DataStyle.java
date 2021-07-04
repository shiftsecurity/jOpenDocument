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

package org.jopendocument.dom.style.data;

import org.jopendocument.dom.Log;
import org.jopendocument.dom.ODEpoch;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.dom.text.TextStyle.StyleTextProperties;
import org.jopendocument.util.NumberUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27 in v1.2-cs01-part1
public abstract class DataStyle extends Style {
    private static final int DEFAULT_GROUPING_SIZE = new DecimalFormat().getGroupingSize();
    public static final int DEFAULT_DECIMAL_PLACES = 10;
    private static final Pattern QUOTE_PATRN = Pattern.compile("'", Pattern.LITERAL);
    private static final Pattern EXP_PATTERN = Pattern.compile("E(\\d+)$");

    public static int getDecimalPlaces(final CellStyle defaultStyle) {
        if (defaultStyle != null) {
            return defaultStyle.getTableCellProperties(null).getDecimalPlaces();
        } else {
            return DEFAULT_DECIMAL_PLACES;
        }
    }

    public static void addStringLiteral(final StringBuilder formatSB, final String s) {
        formatSB.append('\'');
        formatSB.append(QUOTE_PATRN.matcher(s).replaceAll("''"));
        formatSB.append('\'');
    }

    public static final Set<Class<? extends DataStyle>> DATA_STYLES;
    private static final DataStyleDesc<?>[] DATA_STYLES_DESCS = new DataStyleDesc<?>[] { NumberStyle.DESC, PercentStyle.DESC, TextStyle.DESC, CurrencyStyle.DESC, DateStyle.DESC, TimeStyle.DESC,
            BooleanStyle.DESC };
    static {
        final Set<Class<? extends DataStyle>> l = new HashSet<Class<? extends DataStyle>>(DATA_STYLES_DESCS.length);
        l.add(NumberStyle.class);
        l.add(PercentStyle.class);
        l.add(TextStyle.class);
        l.add(CurrencyStyle.class);
        l.add(DateStyle.class);
        l.add(TimeStyle.class);
        l.add(BooleanStyle.class);
        DATA_STYLES = Collections.unmodifiableSet(l);
        assert DATA_STYLES_DESCS.length == DATA_STYLES.size() : "Discrepancy between classes and descs";
    }

    public static abstract class DataStyleDesc<S extends DataStyle> extends StyleDesc<S> {

        protected DataStyleDesc(Class<S> clazz, XMLVersion version, String elemName, String baseName) {
            super(clazz, version, elemName, baseName);
            this.setElementNS(getVersion().getNS("number"));
            // from 19.469 in v1.2-cs01-part1
            this.getRefElementsMap().addAll(
                    "style:data-style-name",
                    Arrays.asList("presentation:date-time-decl", "style:style", "text:creation-date", "text:creation-time", "text:database-display", "text:date", "text:editing-duration",
                            "text:expression", "text:meta-field", "text:modification-date", "text:modification-time", "text:print-date", "text:print-time", "text:table-formula", "text:time",
                            "text:user-defined", "text:user-field-get", "text:user-field-input", "text:variable-get", "text:variable-input", "text:variable-set"));
            this.getRefElementsMap().add("style:apply-style-name", "style:map");
        }
    }

    static public void registerDesc() {
        for (final StyleDesc<?> d : DATA_STYLES_DESCS)
            Style.registerAllVersions(d);
    }

    static public <S extends DataStyle> DataStyleDesc<S> getDesc(final Class<S> clazz, final XMLVersion version) {
        return (DataStyleDesc<S>) Style.getStyleDesc(clazz, version);
    }

    private final ODValueType type;
    private StyleTextProperties textProps;

    protected DataStyle(final ODPackage pkg, Element elem, final ODValueType type) {
        super(pkg, elem);
        this.type = type;
    }

    public final ODValueType getDataType() {
        return this.type;
    }

    public final ODEpoch getEpoch() {
        return this.getPackage().getODDocument().getEpoch();
    }

    /**
     * Convert the passed object to something that {@link #format(Object, CellStyle, boolean)} can
     * accept.
     * 
     * @param o the object to convert.
     * @return an object that can be formatted, <code>null</code> if <code>o</code> cannot be
     *         converted.
     * @throws NullPointerException if <code>o</code> is <code>null</code>.
     * @see #canFormat(Class)
     */
    public final Object convert(final Object o) throws NullPointerException {
        if (o == null)
            throw new NullPointerException();

        final Object res;
        if (this.canFormat(o.getClass()))
            res = o;
        else
            res = this.convertNonNull(o);
        assert res == null || this.canFormat(res.getClass());
        return res;
    }

    // o is not null and canFormat(o.getClass()) is false
    // return null if o cannot be converted
    protected abstract Object convertNonNull(Object o);

    /**
     * Whether instances of the passed class can be {@link #format(Object, CellStyle, boolean)
     * formatted}.
     * 
     * @param toFormat the class.
     * @return <code>true</code> if instances of <code>toFormat</code> can be formatted.
     */
    public final boolean canFormat(Class<?> toFormat) {
        return this.getDataType().canFormat(toFormat);
    }

    public final String getTitle() {
        return this.getElement().getAttributeValue("title", getElement().getNamespace());
    }

    public final StyleTextProperties getTextProperties() {
        if (this.textProps == null)
            this.textProps = new StyleTextProperties(this);
        return this.textProps;
    }

    public abstract String format(final Object o, final CellStyle defaultStyle, boolean lenient) throws UnsupportedOperationException;

    static protected final void reportError(String msg, boolean lenient) throws UnsupportedOperationException {
        if (lenient)
            Log.get().warning(msg);
        else
            throw new UnsupportedOperationException(msg);
    }

    protected final String formatNumberOrScientificNumber(final Element elem, final Number n, CellStyle defaultStyle) {
        return this.formatNumberOrScientificNumber(elem, n, 1, defaultStyle);
    }

    protected final String formatNumberOrScientificNumber(final Element elem, final Number n, final int multiplier, CellStyle defaultStyle) {
        final Namespace numberNS = this.getElement().getNamespace();
        final StringBuilder numberSB = new StringBuilder();

        final List<?> embeddedTexts = elem.getChildren("embedded-text", numberNS);
        final SortedMap<Integer, String> embeddedTextByPosition = new TreeMap<Integer, String>(Collections.reverseOrder());
        for (final Object o : embeddedTexts) {
            final Element embeddedText = (Element) o;
            embeddedTextByPosition.put(Integer.valueOf(embeddedText.getAttributeValue("position", numberNS)), embeddedText.getText());
        }

        final Attribute factorAttr = elem.getAttribute("display-factor", numberNS);
        final double factor = (factorAttr != null ? Double.valueOf(factorAttr.getValue()) : 1) / multiplier;

        // default value from 19.348
        final boolean grouping = StyleProperties.parseBoolean(elem.getAttributeValue("grouping", numberNS), false);

        final String minIntDigitsAttr = elem.getAttributeValue("min-integer-digits", numberNS);
        final int minIntDig = minIntDigitsAttr == null ? 0 : Integer.parseInt(minIntDigitsAttr);
        if (minIntDig == 0) {
            numberSB.append('#');
        } else {
            for (int i = 0; i < minIntDig; i++)
                numberSB.append('0');
        }

        // e.g. if it's "--", 12,3 is displayed "12,3" and 12 is displayed "12,--"
        final String decReplacement = elem.getAttributeValue("decimal-replacement", numberNS);
        final boolean decSeparatorAlwaysShown;
        if (decReplacement != null && !NumberUtils.hasFractionalPart(n)) {
            decSeparatorAlwaysShown = true;
            numberSB.append('.');
            // escape quote in replacement
            addStringLiteral(numberSB, decReplacement);
        } else {
            decSeparatorAlwaysShown = false;
            // see 19.343.2
            final Attribute decPlacesAttr = elem.getAttribute("decimal-places", numberNS);
            final int decPlaces;
            final char decChar;
            if (decPlacesAttr != null) {
                decChar = '0';
                decPlaces = Integer.parseInt(decPlacesAttr.getValue());
            } else {
                // default style specifies the maximum
                decChar = '#';
                decPlaces = getDecimalPlaces(defaultStyle);
            }

            if (decPlaces > 0) {
                numberSB.append('.');
                for (int i = 0; i < decPlaces; i++)
                    numberSB.append(decChar);
            }
        }

        final Attribute minExpAttr = elem.getAttribute("min-exponent-digits", numberNS);
        if (minExpAttr != null) {
            numberSB.append('E');
            for (int i = 0; i < Integer.parseInt(minExpAttr.getValue()); i++)
                numberSB.append('0');
        }

        final DecimalFormat decFormat = new DecimalFormat(numberSB.toString());
        // Java always use HALF_EVEN
        decFormat.setRoundingMode(RoundingMode.HALF_UP);
        decFormat.setGroupingUsed(grouping);
        // needed since the default size is overwritten by the pattern
        decFormat.setGroupingSize(DEFAULT_GROUPING_SIZE);
        decFormat.setDecimalSeparatorAlwaysShown(decSeparatorAlwaysShown);
        String res = decFormat.format(NumberUtils.divide(n, factor));
        // java only puts the minus sign, OO also puts the plus sign
        if (minExpAttr != null) {
            final Matcher m = EXP_PATTERN.matcher(res);
            if (m.find())
                res = res.substring(0, m.start()) + "E+" + m.group(1);
        }
        if (embeddedTextByPosition.size() > 0) {
            final int intDigits = Math.max(minIntDig, NumberUtils.intDigits(n));
            // each time we insert text the decimal point moves
            int offset = 0;
            // sorted descending to avoid overwriting
            for (Entry<Integer, String> e : embeddedTextByPosition.entrySet()) {
                final String embeddedText = e.getValue();
                // the text will be before this index
                final int index = Math.max(0, offset + intDigits - e.getKey().intValue());
                res = res.substring(0, index) + embeddedText + res.substring(index);
                offset += embeddedText.length();
            }
        }
        return res;
    }
}
