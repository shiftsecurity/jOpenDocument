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

package org.jopendocument.dom.style;

import static java.util.Arrays.asList;
import org.jopendocument.dom.Length;
import org.jopendocument.dom.LengthUnit;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleDesc;
import org.jopendocument.dom.XMLVersion;

import java.awt.Color;

import org.jdom.Element;

// from section 16.5 in v1.2-part1-cd04
public class PageLayoutStyle extends Style {

    private static final StyleDesc<PageLayoutStyle> DESC = new StyleDesc<PageLayoutStyle>(PageLayoutStyle.class, XMLVersion.OD, "page-layout", "pm") {
        {
            // from section 19.506 in v1.2-part1-cd04
            this.getRefElementsMap().addAll("style:page-layout-name", asList("presentation:notes", "style:handout-master", "style:master-page"));
        }

        @Override
        public PageLayoutStyle create(ODPackage pkg, Element e) {
            return new PageLayoutStyle(pkg, e);
        }
    };
    private static final StyleDesc<PageLayoutStyle> DESC_OO = new StyleDesc<PageLayoutStyle>(PageLayoutStyle.class, XMLVersion.OOo, "page-master", "pm") {
        {
            // from DTD
            this.getRefElementsMap().addAll("style:page-master-name", asList("presentation:notes", "style:handout-master", "style:master-page"));
        }

        @Override
        public PageLayoutStyle create(ODPackage pkg, Element e) {
            return new PageLayoutStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.register(DESC);
        Style.register(DESC_OO);
    }

    private PageLayoutProperties props;

    public PageLayoutStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
        this.props = null;
    }

    public final PageLayoutProperties getPageLayoutProperties() {
        if (this.props == null)
            this.props = new PageLayoutProperties(this);
        return this.props;
    }

    public final Color getBackgroundColor() {
        return getPageLayoutProperties().getBackgroundColor();
    }

    // see 17.2 of v1.2-part1-cd04
    public static class PageLayoutProperties extends SideStyleProperties {

        public PageLayoutProperties(Style style) {
            super(style, DESC.getElementName());
        }

        public final String getRawMargin(final Side s) {
            return getSideAttribute(s, "margin", this.getNS("fo"));
        }

        /**
         * Get the margin of one of the side.
         * 
         * @param s which side.
         * @return the margin.
         */
        public final Length getMargin(final Side s) {
            return LengthUnit.parseLength(getRawMargin(s));
        }

        public final Length getPageWidth() {
            return getLengthAttr("page-width", "fo");
        }

        public final Length getPageHeight() {
            return getLengthAttr("page-height", "fo");
        }

        private final Length getLengthAttr(final String attrName, final String attrNS) {
            return LengthUnit.parseLength(getAttributeValue(attrName, this.getNS(attrNS)));
        }
    }
}
