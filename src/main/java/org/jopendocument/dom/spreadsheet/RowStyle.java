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

import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;

import java.math.BigDecimal;

import org.jdom.Element;

public class RowStyle extends StyleStyle {

    // from section 18.728 in v1.2-part1
    private static final StyleStyleDesc<RowStyle> DESC = new StyleStyleDesc<RowStyle>(RowStyle.class, XMLVersion.OD, "table-row", "ro", "table") {
        @Override
        public RowStyle create(ODPackage pkg, Element e) {
            return new RowStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private StyleTableRowProperties rowProps;

    public RowStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
    }

    public final StyleTableRowProperties getTableRowProperties() {
        if (this.rowProps == null)
            this.rowProps = new StyleTableRowProperties(this);
        return this.rowProps;
    }

    // see 17.17 of v1.2-cs01-part1
    public static class StyleTableRowProperties extends StyleProperties {

        public StyleTableRowProperties(StyleStyle style) {
            super(style, style.getFamily());
        }

        public final BigDecimal getHeight(final LengthUnit in) {
            return LengthUnit.parseLength(getAttributeValue("row-height", this.getNS("style")), in);
        }

        public final String getBreakBefore() {
            return getAttributeValue("break-before", this.getNS("fo"));
        }

        public final String getBreakAfter() {
            return getAttributeValue("break-after", this.getNS("fo"));
        }
    }
}
