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

import org.jopendocument.dom.Style;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyledNode;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdom.Namespace;

public class SideStyleProperties extends StyleProperties {

    static public enum Side {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private static final Pattern spacePattern = Pattern.compile(" +");

    public SideStyleProperties(Style style, final String propPrefix) {
        super(style, propPrefix);
    }

    public <S extends StyleStyle> SideStyleProperties(S style, final String propPrefix, StyledNode<S, ?> styledNode) {
        super(style, propPrefix, styledNode);
    }

    public final String getBorder(final Side s) {
        return getSideAttribute(s, "border", this.getNS("fo"));
    }

    /**
     * Return all defined borders.
     * 
     * @return all defined borders, i.e. without any <code>null</code> value and thus an empty map
     *         when there are no borders.
     * @see #getBorder(Side)
     */
    public final Map<Side, String> getBorders() {
        final Map<Side, String> res = new HashMap<SideStyleProperties.Side, String>();
        for (final Side s : Side.values()) {
            final String b = this.getBorder(s);
            if (b != null)
                res.put(s, b);
        }
        return res;
    }

    /**
     * If the line style for the border is double, specify the width of the inner and outer lines
     * and the distance between them. See section 15.5.26.
     * 
     * @param s which side.
     * @return the width of the inner line, the distance between the two lines, the width of the
     *         outer line, <code>null</code> if the line style of the border is not double.
     */
    public final String[] getBorderLineWidth(final Side s) {
        final String res = getSideAttribute(s, "border-line-width", this.getElement().getNamespace("style"));
        return res == null ? null : spacePattern.split(res);
    }

    protected final String getSideAttribute(final Side s, final String attrName, final Namespace ns) {
        final String allBorder = getAttributeValue(attrName, ns);
        final String res;
        if (allBorder != null)
            res = allBorder;
        else
            res = getAttributeValue(attrName + "-" + s.name().toLowerCase(), ns);
        return res;
    }
}
