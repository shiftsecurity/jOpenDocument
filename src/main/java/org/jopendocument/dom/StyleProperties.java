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

import java.awt.Color;

import org.jdom.Element;
import org.jdom.Namespace;

public abstract class StyleProperties {

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final String TRANSPARENT_NAME = "transparent";

    static public final boolean parseBoolean(final String s, boolean def) {
        return s == null ? def : Boolean.parseBoolean(s);
    }

    static public final int parseInt(final String s, int def) {
        return s == null ? def : Integer.parseInt(s);
    }

    static public final Integer parseInteger(final String s) {
        return s == null ? null : Integer.valueOf(s);
    }

    private final Style parentStyle;
    private final String propPrefix;
    // only styles with family have custom search or conditions (for getParentStyle())
    private final StyledNode<? extends StyleStyle, ?> styledNode;

    public StyleProperties(Style style, final String propPrefix) {
        this(style, propPrefix, null);
    }

    public <S extends StyleStyle> StyleProperties(S style, String propPrefix, StyledNode<S, ?> styledNode) {
        this((Style) style, propPrefix, styledNode);
    }

    private StyleProperties(Style style, String propPrefix, StyledNode<? extends StyleStyle, ?> styledNode) {
        if (style == null)
            throw new NullPointerException("Null enclosing style");
        if (propPrefix == null)
            throw new NullPointerException("Null propPrefix");
        this.parentStyle = style;
        this.propPrefix = propPrefix;
        this.styledNode = styledNode;
        // guaranteed by the public constructor
        assert this.styledNode == null || this.styledNode.getStyleDesc() == this.parentStyle.getDesc();
    }

    public final Style getEnclosingStyle() {
        return this.parentStyle;
    }

    public final StyledNode<? extends StyleStyle, ?> getStyledNode() {
        return this.styledNode;
    }

    public final Element getElement() {
        return getElement(this.getEnclosingStyle(), true);
    }

    protected final Element getElement(final Style s, final boolean create) {
        return s.getFormattingProperties(this.propPrefix, create);
    }

    private final String getAttributeValue(final Style s, final String attrName, final Namespace attrNS) {
        // e.g. no default style defined
        if (s == null)
            return null;
        final Element elem = this.getElement(s, false);
        if (elem == null)
            return null;
        return elem.getAttributeValue(attrName, attrNS);
    }

    protected final String getAttributeValueInAncestors(final StyleStyle s, final boolean includeFirst, final String attrName, final Namespace attrNS) {
        if (s == null)
            throw new NullPointerException("null style for " + attrName);
        String res = null;
        StyleStyle ancestorStyle = s;
        while (ancestorStyle != null && res == null) {
            if (includeFirst || ancestorStyle != s)
                res = getAttributeValue(ancestorStyle, attrName, attrNS);
            ancestorStyle = this.getStyledNode() == null ? ancestorStyle.getParentStyle() : ancestorStyle.getParentStyle(this.getStyledNode());
        }
        return res;
    }

    /**
     * Search the value of a formatting property. See §16.2 of OpenDocument v1.2.
     * 
     * @param attrName the attribute name, e.g. "rotation-angle".
     * @param attrNS the name space, e.g. "style".
     * @return the value or <code>null</code> if not found.
     */
    public final String getAttributeValue(final String attrName, final Namespace attrNS) {
        final String localRes = getAttributeValue(this.getEnclosingStyle(), attrName, attrNS);
        if (localRes != null)
            return localRes;
        if (this.getEnclosingStyle() instanceof StyleStyle) {
            final StyleStyle styleStyle = (StyleStyle) this.getEnclosingStyle();

            String attrValue = this.getAttributeValueInAncestors(styleStyle, false, attrName, attrNS);
            if (attrValue != null)
                return attrValue;

            if (this.getStyledNode() != null) {
                attrValue = this.getAttributeValueNotInAncestors(attrName, attrNS);
                if (attrValue != null)
                    return attrValue;
            }

            if (Style.isStandardStyleResolution() || this.fallbackToDefaultStyle(attrName, attrNS)) {
                final StyleStyle defaultStyle = styleStyle.getDefaultStyle();
                return getAttributeValue(defaultStyle, attrName, attrNS);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // some formatting properties have more complex search path (e.g. try with
    // default-cell-style-name of the column)
    protected String getAttributeValueNotInAncestors(final String attrName, final Namespace attrNS) {
        return null;
    }

    // LO sometime ignores the default style (e.g. cell borders)
    // only called if !Style.isStandardStyleResolution()
    protected boolean fallbackToDefaultStyle(final String attrName, final Namespace attrNS) {
        return true;
    }

    protected final Namespace getNS(final String prefix) {
        return this.getEnclosingStyle().getNS().getNS(prefix);
    }

    public final void setAttributeValue(final Object o, final String attrName) {
        this.setAttributeValue(o, attrName, getNS("style"));
    }

    public final void setAttributeValue(final Object o, final String attrName, final Namespace attrNS) {
        if (o == null)
            getElement().removeAttribute(attrName, attrNS);
        else
            getElement().setAttribute(attrName, o.toString(), attrNS);
    }

    // * 20.173 fo:background-color of OpenDocument-v1.2-part1, usable with all our subclasses :
    // <style:graphic-properties>, <style:header-footer-properties>, <style:page-layout-properties>,
    // <style:paragraph-properties>, <style:section-properties>, <style:table-cell-properties>,
    // <style:table-properties>, <style:table-row-properties> and <style:text-properties>

    public final String getRawBackgroundColor() {
        return this.getAttributeValue("background-color", this.getNS("fo"));
    }

    public final Color getBackgroundColor() {
        final String res = this.getRawBackgroundColor();
        if (res == null) {
            return null;
        } else if (TRANSPARENT_NAME.equals(res)) {
            return TRANSPARENT;
        } else
            return OOUtils.decodeRGB(res);
    }

    public final void setBackgroundColor(Color color) {
        this.setBackgroundColor(color.getAlpha() == TRANSPARENT.getAlpha() ? TRANSPARENT_NAME : OOUtils.encodeRGB(color));
    }

    public final void setBackgroundColor(String color) {
        this.setAttributeValue(color, "background-color", this.getNS("fo"));
    }
}