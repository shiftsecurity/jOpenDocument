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

package org.jopendocument.dom;

import org.jopendocument.dom.text.ParagraphStyle.StyleParagraphProperties;
import org.jopendocument.dom.text.TextStyle.StyleTextProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.jdom.Element;

/**
 * See 14.13.1 Graphic and Presentation Styles.
 * 
 * @author Sylvain CUAZ
 */
public class GraphicStyle extends StyleStyle {

    // from section 18.222 in v1.2-part1
    private static final StyleStyleDesc<GraphicStyle> DESC = new StyleStyleDesc<GraphicStyle>(GraphicStyle.class, XMLVersion.OD, "graphic", "fr", "draw", Arrays.asList("dr3d:cube", "dr3d:extrude",
            "dr3d:rotate", "dr3d:scene", "dr3d:sphere", "draw:caption", "draw:circle", "draw:connector", "draw:control", "draw:custom-shape", "draw:ellipse", "draw:frame", "draw:g", "draw:line",
            "draw:measure", "draw:page-thumbnail", "draw:path", "draw:polygon", "draw:polyline", "draw:rect", "draw:regular-polygon", "office:annotation")) {

        @Override
        public GraphicStyle create(ODPackage pkg, Element e) {
            return new GraphicStyle(pkg, e);
        }
    };

    // from §2.6 Frames and §5.3 Drawing shapes in OpenOffice.org XML File Format 1.0
    private static final StyleStyleDesc<GraphicStyle> DESC_OO = new StyleStyleDesc<GraphicStyle>(GraphicStyle.class, XMLVersion.OOo, "graphics", "fr", "draw", Arrays.asList("draw:text-box",
            "draw:image", "draw:caption", "draw:circle", "draw:connector", "draw:control", "draw:ellipse", "draw:line", "draw:measure", "draw:page-thumbnail", "draw:path", "draw:polygon",
            "draw:polyline", "draw:rect")) {

        @Override
        public GraphicStyle create(ODPackage pkg, Element e) {
            return new GraphicStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.register(DESC);
        Style.register(DESC_OO);
    }

    private StyleTextProperties textProps;
    private StyleParagraphProperties pProps;
    private StyleGraphicProperties graphProps;

    public GraphicStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
        this.textProps = null;
        this.pProps = null;
        this.graphProps = null;
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

    public final StyleGraphicProperties getGraphicProperties() {
        if (this.graphProps == null)
            this.graphProps = new StyleGraphicProperties(this);
        return this.graphProps;
    }

    /**
     * Cf section 15.13 to 15.28.
     * 
     * @author Sylvain CUAZ
     */
    public static class StyleGraphicProperties extends StyleProperties {

        private static Pattern split = Pattern.compile(" ");

        public StyleGraphicProperties(StyleStyle style) {
            super(style, DESC.getFamily());
        }

        public final String getHorizontalPosition() {
            return this.getAttributeValue("horizontal-pos", this.getElement().getNamespace("style"));
        }

        public final String getHorizontalRelation() {
            return this.getAttributeValue("horizontal-rel", this.getElement().getNamespace("style"));
        }

        public final String getVerticalPosition() {
            return this.getAttributeValue("vertical-pos", this.getElement().getNamespace("style"));
        }

        public final String getVerticalRelation() {
            return this.getAttributeValue("vertical-rel", this.getElement().getNamespace("style"));
        }

        /**
         * The protected position, see 15.27.8.
         * 
         * @return a list that consists of any of the values content, position, or size.
         */
        public final List<String> getProtected() {
            final String val = this.getAttributeValue("protect", this.getElement().getNamespace("style"));
            if (val == null || "none".equals(val))
                return Collections.emptyList();
            else
                return Arrays.asList(split.split(val));
        }

        public final boolean isContentPrinted() {
            return parseBoolean(this.getAttributeValue("print-content", this.getElement().getNamespace("style")), true);
        }
    }
}
