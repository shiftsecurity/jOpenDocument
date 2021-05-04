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

import org.jopendocument.dom.Length;
import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;

import org.jdom.Element;

public class ColumnStyle extends StyleStyle {

    // from section 18.728 in v1.2-part1
    private static final StyleStyleDesc<ColumnStyle> DESC = new StyleStyleDesc<ColumnStyle>(ColumnStyle.class, XMLVersion.OD, "table-column", "co", "table") {
        @Override
        public ColumnStyle create(ODPackage pkg, Element e) {
            return new ColumnStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private StyleTableColumnProperties colProps;

    public ColumnStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
    }

    public final StyleTableColumnProperties getTableColumnProperties() {
        if (this.colProps == null)
            this.colProps = new StyleTableColumnProperties(this);
        return this.colProps;
    }

    public final Length getWidth() {
        return this.getTableColumnProperties().getWidth();
    }

    // not public, use Table.setColumnCount(), Table.createColumnStyle() or Column.setWidth()
    void setWidth(final Length l) {
        this.getTableColumnProperties().setAttributeValue(l.format(TableStyle.getWriteUnit()), "column-width");
        // optional, so no need to recompute it
        rmRelWidth();
    }

    void rmRelWidth() {
        getFormattingProperties().removeAttribute("rel-column-width", this.getSTYLE());
    }

    // see 17.16 of v1.2-cs01-part1
    public static class StyleTableColumnProperties extends StyleProperties {

        public StyleTableColumnProperties(StyleStyle style) {
            super(style, style.getFamily());
        }

        public final Length getWidth() {
            return LengthUnit.parseLength(getAttributeValue("column-width", this.getNS("style")));
        }

        public final String getBreakBefore() {
            return getAttributeValue("break-before", this.getNS("fo"));
        }

        public final String getBreakAfter() {
            return getAttributeValue("break-after", this.getNS("fo"));
        }
    }
}
