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
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.style.SideStyleProperties;

import java.math.BigDecimal;
import java.util.Arrays;

import org.jdom.Element;

public class TableStyle extends StyleStyle {

    // LibreOffice default
    static private LengthUnit WRITE_UNIT = LengthUnit.CM;

    /**
     * Set the unit written to XML. Initially set to the LibreOffice value ({@link LengthUnit#CM}).
     * 
     * @param unit the unit to write, <code>null</code> meaning keep the passed unit.
     */
    public synchronized static void setWriteUnit(LengthUnit unit) {
        WRITE_UNIT = unit;
    }

    public synchronized static LengthUnit getWriteUnit() {
        return WRITE_UNIT;
    }

    // from section 18.728 in v1.2-part1
    private static final StyleStyleDesc<TableStyle> DESC = new StyleStyleDesc<TableStyle>(TableStyle.class, XMLVersion.OD, "table", "ta", "table", Arrays.asList("table:background", "table:table")) {
        @Override
        public TableStyle create(ODPackage pkg, Element e) {
            return new TableStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private StyleTableProperties tableProps;

    public TableStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
    }

    public final StyleTableProperties getTableProperties() {
        if (this.tableProps == null)
            this.tableProps = new StyleTableProperties(this);
        return this.tableProps;
    }

    public final Length getWidth() {
        return getTableProperties().getWidth();
    }

    void setWidth(final Length l) {
        getTableProperties().setAttributeValue(l.format(getWriteUnit()), "width", this.getSTYLE());
    }

    // see 17.15 of v1.2-cs01-part1
    public static class StyleTableProperties extends SideStyleProperties {

        public StyleTableProperties(StyleStyle style) {
            super(style, style.getFamily());
        }

        public final Boolean isDisplayed() {
            final String val = this.getAttributeValue("display", this.getEnclosingStyle().getNS().getTABLE());
            return val == null ? null : Boolean.valueOf(val);
        }

        public final void setDisplayed(Boolean b) {
            this.setAttributeValue(b, "display", this.getEnclosingStyle().getNS().getTABLE());
        }

        public final String getRawMargin(final Side s) {
            return getSideAttribute(s, "margin", this.getNS("fo"));
        }

        /**
         * Get the margin of one of the side.
         * 
         * @param s which side.
         * @param in the desired unit.
         * @return the margin.
         */
        public final BigDecimal getMargin(final Side s, final LengthUnit in) {
            return LengthUnit.parseLength(getRawMargin(s), in);
        }

        public final Length getWidth() {
            return LengthUnit.parseLength(getAttributeValue("width", this.getNS("style")));
        }
    }
}
