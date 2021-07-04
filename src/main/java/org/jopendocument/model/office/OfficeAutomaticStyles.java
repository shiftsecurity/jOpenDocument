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

package org.jopendocument.model.office;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.jopendocument.model.number.NumberNumberStyle;
import org.jopendocument.model.style.StylePageLayout;
import org.jopendocument.model.style.StyleStyle;

public class OfficeAutomaticStyles {

    private final Map<String, StyleStyle> cellStyles = new HashMap<String, StyleStyle>();
    private final Map<String, NumberNumberStyle> numberStyles = new HashMap<String, NumberNumberStyle>();

    private final Map<String, StylePageLayout> pagesLayouts = new HashMap<String, StylePageLayout>();
    private final List<StyleStyle> styles = new Vector<StyleStyle>();
    private final Map<String, StyleStyle> stylesMap = new HashMap<String, StyleStyle>();

    private final Map<String, StyleStyle> tableStyles = new HashMap<String, StyleStyle>();

    public void addPageLayout(final StylePageLayout l) {
        this.pagesLayouts.put(l.getStyleName(), l);
    }

    public void addStyle(final StyleStyle style) {
        this.stylesMap.put(style.getStyleName(), style);
        this.styles.add(style);
    }

    public StyleStyle getCellStyle(final String s) {
        StyleStyle result = this.cellStyles.get(s);
        if (result == null) {
            result = this.getStyle(s, "table-cell");
            this.cellStyles.put(s, result);
        }
        return result;
    }

    public StyleStyle getColumnStyle(final String s) {
        return this.getStyle(s, "table-column");
    }

    public StyleStyle getRowStyle(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("The style name cannot be null");
        }
        return this.getStyle(s, "table-row");
    }

    private StyleStyle getStyle(final String s, final String type) {
        final StyleStyle styleStyle = this.stylesMap.get(s);
        if (styleStyle == null) {
            final Set<String> t = this.stylesMap.keySet();
            System.err.println(this.styles);
            for (final String string : t) {
                System.err.println("Key:" + string);
            }
            throw new IllegalArgumentException("Unable to find Style name:" + s + " type:" + type);
        }

        return styleStyle;

    }

    public StylePageLayout getStylePageLayoutFromStyleName(final String pageLayoutStyleName) {
        return this.pagesLayouts.get(pageLayoutStyleName);
    }

    public List<StyleStyle> getStyles() {
        return this.styles;
    }

    public StyleStyle getTableStyle(final String tableStyleName) {
        if (tableStyleName == null) {
            throw new IllegalArgumentException("null style name");
        }

        StyleStyle result = this.tableStyles.get(tableStyleName);
        if (result == null) {
            result = this.getStyle(tableStyleName, "table");
            this.tableStyles.put(tableStyleName, result);
        }
        return result;

    }

    public StyleStyle getTextStyle(final String s) {
        return this.getStyle(s, "text");
    }

    @Override
    public String toString() {
        return "OfficeAutomaticStyles";
    }

}
