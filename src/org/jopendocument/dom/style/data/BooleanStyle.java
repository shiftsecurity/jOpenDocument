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
import org.jopendocument.util.NumberUtils;
import org.jopendocument.util.i18n.I18nUtils;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jdom.Element;
import org.jdom.Namespace;

// from section 16.27.23 in v1.2-cs01-part1
public class BooleanStyle extends DataStyle {

    static final DataStyleDesc<BooleanStyle> DESC = new DataStyleDesc<BooleanStyle>(BooleanStyle.class, XMLVersion.OD, "boolean-style", "N") {
        @Override
        public BooleanStyle create(ODPackage pkg, Element e) {
            return new BooleanStyle(pkg, e);
        }
    };

    public static final Boolean toBoolean(Object o) {
        if (o instanceof Boolean)
            return (Boolean) o;
        else if (o instanceof Number)
            return Boolean.valueOf(!NumberUtils.areNumericallyEqual(0, (Number) o));
        else
            return null;
    }

    public static final String toString(final boolean b, final Locale locale, final boolean lenient) {
        final ResourceBundle bundle = ResourceBundle.getBundle(I18nUtils.RSRC_BASENAME, locale);
        if (!bundle.getLocale().getLanguage().equals(locale.getLanguage()))
            reportError("Boolean not localized", lenient);
        return bundle.getString(I18nUtils.getBooleanKey(b)).toUpperCase(locale);
    }

    public BooleanStyle(final ODPackage pkg, Element elem) {
        super(pkg, elem, ODValueType.BOOLEAN);
    }

    @Override
    protected Boolean convertNonNull(Object o) {
        return toBoolean(o);
    }

    @Override
    public String format(Object o, CellStyle defaultStyle, boolean lenient) {
        final Boolean b = (Boolean) o;
        final Namespace numberNS = this.getElement().getNamespace();
        final Locale styleLocale = DateStyle.getLocale(getElement());
        final StringBuilder sb = new StringBuilder();
        @SuppressWarnings("unchecked")
        final List<Element> children = this.getElement().getChildren();
        for (final Element elem : children) {
            if (elem.getNamespace().equals(numberNS)) {
                if (elem.getName().equals("text")) {
                    sb.append(elem.getText());
                } else if (elem.getName().equals("boolean")) {
                    sb.append(toString(b, styleLocale, lenient));
                }
            }
        }
        return sb.toString();
    }
}
