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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.Log;
import org.jopendocument.dom.ODEpoch;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.StyledNode;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.style.RelationalOperator;
import org.jopendocument.dom.style.SideStyleProperties;
import org.jopendocument.dom.style.data.BooleanStyle;
import org.jopendocument.dom.style.data.DataStyle;
import org.jopendocument.dom.style.data.NumberStyle;
import org.jopendocument.dom.text.ParagraphStyle.StyleParagraphProperties;
import org.jopendocument.dom.text.TextStyle.StyleTextProperties;
import org.jopendocument.util.CompareUtils;
import org.jopendocument.util.Tuple3;
import org.jopendocument.util.JDOMUtils;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class CellStyle extends StyleStyle {

    private static final Pattern numberPatrn = Pattern.compile("-?\\d+(?:\\.\\d+)?");
    private static final Pattern escapedQuotePatrn = Pattern.compile("\"\"", Pattern.LITERAL);
    private static final Pattern stringPatrn = Pattern.compile("\"(?:[^\\p{Cntrl}\"]|\\p{Space}|" + escapedQuotePatrn.pattern() + ")*\"");
    private static final String valuePatrn = "(" + numberPatrn.pattern() + "|" + stringPatrn.pattern() + ")";
    private static final Pattern cellContentPatrn = Pattern.compile("cell-content\\(\\) *(" + RelationalOperator.OR_PATTERN + ") *" + valuePatrn + "");
    private static final Pattern cellContentBetweenPatrn = Pattern.compile("cell-content-is(?:-not)?-between\\(" + valuePatrn + ", *" + valuePatrn + "\\)");

    // from section 18.728 in v1.2-part1
    private static final StyleStyleDesc<CellStyle> DESC = new StyleStyleDesc<CellStyle>(CellStyle.class, XMLVersion.OD, "table-cell", "ce", "table", Arrays.asList("table:body",
            "table:covered-table-cell", "table:even-rows", "table:first-column", "table:first-row", "table:last-column", "table:last-row", "table:odd-columns", "table:odd-rows", "table:table-cell")) {

        {
            this.getMultiRefElementsMap().addAll("table:default-cell-style-name", "table:table-column", "table:table-row");
        }

        @Override
        public CellStyle create(ODPackage pkg, Element e) {
            return new CellStyle(pkg, e);
        }

        @Override
        protected boolean supportConditions() {
            return true;
        }

        @Override
        protected Element evaluateConditions(final StyledNode<CellStyle, ?> styledNode, final List<Element> styleMaps) {
            final Cell<?> cell = (Cell<?>) styledNode;
            final ODEpoch epoch = cell.getODDocument().getEpoch();
            final Object cellValue = cell.getValue();
            final boolean cellIsEmpty = cell.isEmpty();
            for (final Element styleMap : styleMaps) {
                final String condition = styleMap.getAttributeValue("condition", getVersion().getSTYLE()).trim();
                Matcher matcher = cellContentPatrn.matcher(condition);
                if (matcher.matches()) {
                    final Object parsed = parse(matcher.group(2));
                    final Object usedCellValue = getValue(cellIsEmpty, epoch, cellValue, parsed);
                    if (usedCellValue != null && RelationalOperator.getInstance(matcher.group(1)).compare(usedCellValue, parsed))
                        return styleMap;
                } else if ((matcher = cellContentBetweenPatrn.matcher(condition)).matches()) {
                    final boolean wantBetween = condition.startsWith("cell-content-is-between");
                    assert wantBetween ^ condition.startsWith("cell-content-is-not-between");
                    final Object o1 = parse(matcher.group(1));
                    final Object o2 = parse(matcher.group(2));
                    assert o1.getClass() == o2.getClass();
                    final Object usedCellValue = getValue(cellIsEmpty, epoch, cellValue, o1);
                    if (usedCellValue != null) {
                        final boolean isBetween = CompareUtils.compare(usedCellValue, o1) >= 0 && CompareUtils.compare(usedCellValue, o2) <= 0;
                        if (isBetween == wantBetween)
                            return styleMap;
                    }
                } else {
                    // If a consumer does not recognize a condition, it shall ignore the <style:map>
                    // element containing the condition.
                    Log.get().fine("Ignoring " + JDOMUtils.output(styleMap));
                }
            }
            return null;
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private static final Pattern conditionPatrn = Pattern.compile("value\\(\\) *(" + RelationalOperator.OR_PATTERN + ") *(true|false|" + numberPatrn.pattern() + ")");

    // from style:condition :
    // "n is a number for non-Boolean data styles and true or false for Boolean data styles"
    private static final Object convertForCondition(final Object value, final DataStyle style) {
        final Object castedValue;
        if (style instanceof BooleanStyle) {
            castedValue = BooleanStyle.toBoolean(value);
        } else {
            castedValue = NumberStyle.toNumber(value, style.getEpoch());
        }
        return castedValue;
    }

    private StyleTextProperties textProps;
    private StyleParagraphProperties pProps;

    public CellStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
    }

    private final DataStyle getDataStyle(final Attribute name) {
        return (DataStyle) Style.getReferencedStyle(getPackage(), name);
    }

    final DataStyle getDataStyle() {
        return getDataStyle(this.getElement().getAttribute("data-style-name", this.getSTYLE()));
    }

    // return value since it can be changed depending on the data style.
    // e.g. in OO if we input 12:30 in an empty cell, it will have value-type="time"
    // but if we had previously set a number style (like 0,00) it would have been converted to 0,52
    // value-type="float"
    final Tuple3<DataStyle, ODValueType, Object> getDataStyle(final Object cellValue, final ODValueType valueType, final boolean onlyCast) {
        DataStyle res = getDataStyle();
        ODValueType returnValueType = valueType;
        Object returnCellValue = cellValue;
        // if the type is null, then the cell is empty so don't try to convert the cell value or
        // evaluate conditions
        if (res != null && valueType != null) {
            if (!onlyCast) {
                final Object convertedForStyle = res.convert(cellValue);
                // if conversion is successful
                if (convertedForStyle != null) {
                    returnCellValue = convertedForStyle;
                    returnValueType = res.getDataType();
                }
            }

            final List<?> styleMaps = res.getElement().getChildren("map", getSTYLE());
            if (styleMaps.size() > 0) {
                final Object converted = convertForCondition(returnCellValue, res);
                // we can't compare() so don't try
                if (converted != null) {
                    for (Object child : styleMaps) {
                        final Element styleMap = (Element) child;
                        final Matcher matcher = conditionPatrn.matcher(styleMap.getAttributeValue("condition", getSTYLE()).trim());
                        if (!matcher.matches())
                            throw new IllegalStateException("Cannot parse " + JDOMUtils.output(styleMap));
                        if (RelationalOperator.getInstance(matcher.group(1)).compare(converted, parse(matcher.group(2)))) {
                            res = getDataStyle(styleMap.getAttribute("apply-style-name", getSTYLE()));
                            break;
                        }
                    }
                }
            }
        }
        // if the type is null, then the cell is empty, we cannot make up some value, otherwise
        // don't change it to null
        assert (valueType == null) == (returnValueType == null) : "don't change type to null";
        assert !onlyCast || (returnValueType == valueType && returnCellValue == cellValue) : "Requested to only cast, but different object";
        // if res is null, the document is incoherent (non existing style name)
        return res == null ? null : Tuple3.create(res, returnValueType, returnCellValue);
    }

    static private Object parse(String val) {
        if (val.equalsIgnoreCase("true"))
            return Boolean.TRUE;
        else if (val.equalsIgnoreCase("false"))
            return Boolean.FALSE;
        else if (val.charAt(0) == '"')
            return escapedQuotePatrn.matcher(val.substring(1, val.length() - 1)).replaceAll("\"");
        else
            return new BigDecimal(val);
    }

    static private Object getDefault(Class<?> clazz) {
        if (clazz == Boolean.class)
            return Boolean.FALSE;
        else if (clazz == String.class)
            return "";
        else if (clazz == BigDecimal.class)
            return BigDecimal.ZERO;
        else
            throw new IllegalStateException("Unknown default for " + clazz);
    }

    // convert cellValue to class of parsed, return null if not possible
    static private Object getValue(boolean cellIsEmpty, ODEpoch epoch, Object cellValue, Object parsed) {
        final Class<?> conditionClass = parsed.getClass();
        if (cellIsEmpty) {
            // LO uses the default value for the type when the cell is empty
            return getDefault(conditionClass);
        } else if (cellValue.getClass() == conditionClass) {
            return cellValue;
        } else {
            // LO doesn't convert between String and Number, but Boolean are Numbers
            if (conditionClass == String.class) {
                return null;
            } else if (conditionClass == Boolean.class) {
                return BooleanStyle.toBoolean(cellValue);
            } else if (Number.class.isAssignableFrom(conditionClass)) {
                return NumberStyle.toNumber(cellValue, epoch);
            } else {
                throw new IllegalStateException("Invalid class value for condition : " + conditionClass);
            }
        }
    }

    @Deprecated
    public final Color getBackgroundColor() {
        return getTableCellProperties().getBackgroundColor();
    }

    public final Color getBackgroundColor(final Cell<?> styledNode) {
        return getTableCellProperties(styledNode).getBackgroundColor();
    }

    @Deprecated
    public final StyleTableCellProperties getTableCellProperties() {
        return this.getTableCellProperties(null);
    }

    // MAYBE add getWriteOnlyTableCellProperties() and enforce it in
    // StyleProperties.getAttributeValue(). That way getTableCellProperties() can check for non null
    // parameter.
    public final StyleTableCellProperties getTableCellProperties(final Cell<?> styledNode) {
        // no longer cache since the result depends on the passed node (a simple ICache is slower)
        return new StyleTableCellProperties(this, styledNode);
    }

    public final StyleTextProperties getTextProperties() {
        if (this.textProps == null)
            this.textProps = new StyleTextProperties(this);
        return this.textProps;
    }

    public final StyleParagraphProperties getParagraphProperties() {
        if (this.pProps == null)
            this.pProps = new StyleParagraphProperties(this);
        return this.pProps;
    }

    /**
     * See section 15.11 of OpenDocument v1.1 : Table Cell Formatting Properties.
     * 
     * @author Sylvain CUAZ
     */
    public static class StyleTableCellProperties extends SideStyleProperties {

        public <S extends StyleStyle> StyleTableCellProperties(S style, StyledNode<S, ?> styledNode) {
            super(style, DESC.getFamily(), styledNode);
        }

        protected String getAttributeValueInAncestors(final Cell<?> cell, final TableCalcNode<?, ?> calcNode, String attrName, Namespace attrNS) {
            final Element elem = calcNode.getElement();
            final String cellStyleName = elem.getAttributeValue("default-cell-style-name", elem.getNamespace("table"));
            if (cellStyleName == null) {
                return null;
            } else {
                final CellStyle style = cell.getStyleDesc().findStyleForNode(calcNode.getODDocument().getPackage(), elem.getDocument(), cell, cellStyleName);
                return this.getAttributeValueInAncestors(style, true, attrName, attrNS);
            }
        }

        @Override
        protected String getAttributeValueNotInAncestors(String attrName, Namespace attrNS) {
            String res = null;
            // from §16.2 of OpenDocument v1.2 (LO ignores it)
            if (Style.isStandardStyleResolution() && this.getEnclosingStyle() instanceof CellStyle && this.getStyledNode() instanceof Cell) {
                if (!(this.getStyledNode() instanceof MutableCell))
                    // MAYBE pass the column alongside the cell in the constructor (from
                    // Table.getTableCellPropertiesAt())
                    throw new UnsupportedOperationException("Missing column for " + getStyledNode());
                final Cell<?> cell = (Cell<?>) this.getStyledNode();
                res = this.getAttributeValueInAncestors(cell, cell.getRow(), attrName, attrNS);
                if (res != null)
                    return res;
                res = this.getAttributeValueInAncestors(cell, cell.getRow().getSheet().getColumn(((MutableCell<?>) cell).getX()), attrName, attrNS);
            }
            return res;
        }

        @Override
        protected boolean fallbackToDefaultStyle(String attrName, Namespace attrNS) {
            // all properties that I've test are ignored by LO
            return false;
        }

        public final int getRotationAngle() {
            final String s = this.getAttributeValue("rotation-angle", this.getElement().getNamespace("style"));
            return parseInt(s, 0);
        }

        public final void setRotationAngle(final Integer angle) {
            this.setAttributeValue(angle, "rotation-angle");
        }

        public final boolean isContentPrinted() {
            return parseBoolean(this.getAttributeValue("print-content", this.getElement().getNamespace("style")), true);
        }

        public final boolean isContentRepeated() {
            return parseBoolean(this.getAttributeValue("repeat-content", this.getElement().getNamespace("style")), false);
        }

        public final boolean isShrinkToFit() {
            return parseBoolean(this.getAttributeValue("shrink-to-fit", this.getElement().getNamespace("style")), false);
        }

        // *maximum* number of decimal places to display (number:decimal-places is the *exact*
        // number to display)
        public final int getDecimalPlaces() {
            // see §20.250 style:decimal-places of OpenDocument v1.2
            if (!getEnclosingStyle().getElement().getName().equals(StyleStyleDesc.ELEMENT_DEFAULT_NAME))
                throw new IllegalStateException("Not on a default style : " + this.getEnclosingStyle());
            return parseInt(this.getRawDecimalPlaces(), DataStyle.DEFAULT_DECIMAL_PLACES);
        }

        public final String getRawDecimalPlaces() {
            return this.getAttributeValue("decimal-places", this.getElement().getNamespace("style"));
        }

        public final boolean isWrapping() {
            final String val = this.getAttributeValue("wrap-option", this.getNS("fo"));
            if (val == null || val.equals("no-wrap"))
                return false;
            else if (val.equals("wrap"))
                return true;
            else
                throw new IllegalStateException("Unknown value : " + val);
        }

        public final void setWrapping(final boolean b) {
            this.setAttributeValue(b ? "wrap" : "no-wrap", "wrap-option", getNS("fo"));
        }
    }

}
