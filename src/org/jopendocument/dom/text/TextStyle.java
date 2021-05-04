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
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.XMLVersion;

import java.awt.Color;

import org.jdom.Element;

public class TextStyle extends StyleStyle {

    private static final StyleStyleDesc<TextStyle> DESC = new StyleStyleDesc<TextStyle>(TextStyle.class, XMLVersion.OD, "text", "T") {

        {
            // from section 19.876 in v1.2-part1-cd04
            this.getRefElementsMap().addAll("text:style-name", asList("text:linenumbering-configuration", "text:list-level-style-number", "text:ruby-text", "text:span"));
        }

        @Override
        public TextStyle create(ODPackage pkg, Element e) {
            return new TextStyle(pkg, e);
        }
    };

    static public void registerDesc() {
        Style.registerAllVersions(DESC);
    }

    private StyleTextProperties textProps;

    public TextStyle(final ODPackage pkg, Element tableColElem) {
        super(pkg, tableColElem);
        this.textProps = null;
    }

    public final StyleTextProperties getTextProperties() {
        if (this.textProps == null)
            this.textProps = new StyleTextProperties(this);
        return this.textProps;
    }

    public final Color getColor() {
        return getTextProperties().getColor();
    }

    public final Color getBackgroundColor() {
        return getTextProperties().getBackgroundColor();
    }

    // cf style-text-properties-content-strict in the relaxNG
    public static class StyleTextProperties extends StyleProperties {

        public StyleTextProperties(Style style) {
            super(style, DESC.getFamily());
        }

        public final Color getColor() {
            final String attrValue = this.getAttributeValue("color", this.getNS("fo"));
            if (attrValue == null)
                return Color.BLACK;
            else
                return OOUtils.decodeRGB(attrValue);
        }

        public final void setColor(Color color) {
            this.setAttributeValue(color == null ? null : OOUtils.encodeRGB(color), "color", this.getNS("fo"));
        }

        public final String getFontName() {
            return this.getAttributeValue("font-name", this.getElement().getNamespace("style"));
        }

        public final String getLanguage() {
            return this.getAttributeValue("language", this.getNS("fo"));
        }

        public final String getCountry() {
            return this.getAttributeValue("country", this.getNS("fo"));
        }

        public final String getWeight() {
            return this.getAttributeValue("font-weight", this.getNS("fo"));
        }
    }
}
