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

import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle;

import java.text.DecimalFormatSymbols;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27.7 in v1.2-cs01-part1
public class CurrencyStyle extends DataStyle {

    static final DataStyleDesc<CurrencyStyle> DESC = new DataStyleDesc<CurrencyStyle>(CurrencyStyle.class, XMLVersion.OD, "currency-style", "N") {
        @Override
        public CurrencyStyle create(ODPackage pkg, Element e) {
            return new CurrencyStyle(pkg, e);
        }
    };

    public CurrencyStyle(final ODPackage pkg, Element elem) {
        super(pkg, elem, ODValueType.CURRENCY);
    }

    @Override
    protected Object convertNonNull(Object o) {
        return NumberStyle.toNumber(o, getEpoch());
    }

    @Override
    public String format(Object o, CellStyle defaultStyle, boolean lenient) {
        final Number n = (Number) o;
        final Namespace numberNS = this.getElement().getNamespace();
        final StringBuilder sb = new StringBuilder();
        @SuppressWarnings("unchecked")
        final List<Element> children = this.getElement().getChildren();
        for (final Element elem : children) {
            if (elem.getNamespace().equals(numberNS)) {
                if (elem.getName().equals("text")) {
                    sb.append(elem.getText());
                } else if (elem.getName().equals("number")) {
                    // ATTN OpenOffice Fix (it generates <text>-</text>, so we have to use the
                    // absolute value)
                    final int multiplier = n.doubleValue() > 0 ? 1 : -1;
                    sb.append(formatNumberOrScientificNumber(elem, n, multiplier, defaultStyle));
                } else if (elem.getName().equals("currency-symbol")) {
                    if (elem.getTextTrim().length() > 0) {
                        sb.append(elem.getText());
                    } else {
                        sb.append(new DecimalFormatSymbols(DateStyle.getLocale(elem)).getCurrencySymbol());
                    }
                }
            }
        }
        return sb.toString();
    }
}
