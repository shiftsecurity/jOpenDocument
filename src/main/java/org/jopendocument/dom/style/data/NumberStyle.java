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

import org.jopendocument.dom.ODEpoch;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.dom.spreadsheet.MutableCell;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;

import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27.2 in v1.2-cs01-part1
public class NumberStyle extends DataStyle {

    static final DataStyleDesc<NumberStyle> DESC = new DataStyleDesc<NumberStyle>(NumberStyle.class, XMLVersion.OD, "number-style", "N") {
        @Override
        public NumberStyle create(ODPackage pkg, Element e) {
            return new NumberStyle(pkg, e);
        }
    };

    public static final Number toNumber(Object value, ODEpoch epoch) {
        final Number res;
        if (value instanceof Number) {
            res = (Number) value;
        } else if (value instanceof Boolean) {
            res = ((Boolean) value).booleanValue() ? 1 : 0;
        } else if ((value instanceof Duration || value instanceof Date || value instanceof Calendar)) {
            if (value instanceof Duration) {
                res = epoch.getDays((Duration) value);
            } else {
                final Calendar cal;
                if (value instanceof Calendar) {
                    cal = (Calendar) value;
                } else {
                    cal = ODValueType.getCalendar();
                    cal.setTime((Date) value);
                }
                res = epoch.getDays(cal);
            }
        } else {
            res = null;
        }
        return res;
    }

    public NumberStyle(final ODPackage pkg, Element elem) {
        super(pkg, elem, ODValueType.FLOAT);
    }

    @Override
    protected Number convertNonNull(Object value) {
        return toNumber(value, getEpoch());
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
                } else if (elem.getName().equals("number") || elem.getName().equals("scientific-number")) {
                    sb.append(formatNumberOrScientificNumber(elem, n, defaultStyle));
                } else if (elem.getName().equals("fraction")) {
                    // TODO fractions
                    reportError("Fractions not supported", lenient);
                    sb.append(MutableCell.formatNumber(n, defaultStyle));
                }
            }
        }
        return sb.toString();
    }
}
