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

package org.jopendocument.dom.text;

import static java.util.Arrays.asList;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;

import org.jdom.Element;

public class ParagraphStyle extends TextStyle {

    private static final StyleStyleDesc<ParagraphStyle> DESC = new StyleStyleDesc<ParagraphStyle>(ParagraphStyle.class, XMLVersion.OD, "paragraph", "P") {

        {
            // from section 18.876 in v1.2-part1
            this.getRefElementsMap().addAll(
                    "text:style-name",
                    asList("text:alphabetical-index-entry-template", "text:bibliography-entry-template", "text:h", "text:p", "text:illustration-index-entry-template", "text:index-source-style",
                            "text:object-index-entry-template", "text:table-index-entry-template", "text:table-of-content-entry-template", "text:user-index-entry-template"));
            // 19.229
            this.getRefElementsMap().addAll(
                    "draw:text-style-name",
                    asList("draw:caption", "draw:circle", "draw:connector", "draw:control", "draw:custom-shape", "draw:ellipse", "draw:frame", "draw:line", "draw:measure", "draw:path",
                            "draw:polygon", "draw:polyline", "draw:rect", "draw:regular-polygon", "office:annotation"));
            // 19.314
            this.getRefElementsMap().addAll("form:text-style-name", asList("form:column"));
            // 19.690 (apparently not implemented by OpenOffice 3.2 - or any older version)
            this.getRefElementsMap()
                    .addAll("table:paragraph-style-name",
                            asList("table:body", "table:even-columns", "table:even-rows", "table:first-column", "table:first-row", "table:last-column", "table:last-row", "table:odd-columns",
                                    "table:odd-rows"));
        }

        @Override
        public ParagraphStyle create(ODPackage pkg, Element e) {
            return new ParagraphStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private StyleParagraphProperties pProps;

    public ParagraphStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
        this.pProps = null;
    }

    public final StyleParagraphProperties getParagraphProperties() {
        if (this.pProps == null)
            this.pProps = new StyleParagraphProperties(this);
        return this.pProps;
    }

    public final String getAlignment() {
        return getParagraphProperties().getAlignment();
    }

    // cf style-text-properties-content-strict in the relaxNG
    public static class StyleParagraphProperties extends StyleProperties {

        public StyleParagraphProperties(StyleStyle style) {
            super(style, DESC.getFamily());
        }

        public final String getAlignment() {
            return getAttributeValue("text-align", this.getNS("fo"));
        }
    }
}
