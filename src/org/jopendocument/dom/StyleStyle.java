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

import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.dom.spreadsheet.Row;
import net.jcip.annotations.ThreadSafe;

import org.jdom.Element;

/**
 * A style:style, see section 14.1. Maintains a map of family to classes.
 * 
 * @author Sylvain
 */
@ThreadSafe
public class StyleStyle extends Style {

    private final String family;

    public StyleStyle(final ODPackage pkg, final Element styleElem) {
        super(pkg, styleElem);
        this.family = StyleStyleDesc.getFamily(this.getElement());
        if (!this.getDesc().getFamily().equals(this.getFamily()))
            throw new IllegalArgumentException("expected " + this.getDesc().getFamily() + " but got " + this.getFamily() + " for " + styleElem);
    }

    @Override
    protected void checkElemName() {
        // allow use of default styles
        if (!StyleStyleDesc.ELEMENT_DEFAULT_NAME.equals(this.getElement().getName()) && !this.getDesc().getElementName().equals(this.getElement().getName()))
            throw new IllegalArgumentException("expected a default style (" + StyleStyleDesc.ELEMENT_DEFAULT_NAME + ") or " + this.getDesc().getElementName() + " but got " + getElement());
    }

    @Override
    protected StyleStyleDesc<?> getDesc() {
        return (StyleStyleDesc<?>) super.getDesc();
    }

    public final String getFamily() {
        return this.family;
    }

    public final Element getFormattingProperties() {
        return this.getFormattingProperties(this.getFamily());
    }

    /**
     * Get the parent style. Note: if this style {@link StyleDesc#supportConditions() supports
     * conditions} they will be ignored.
     * 
     * @return the parent style or <code>null</code> if none exists.
     * @see StyleDesc#findStyleWithName(ODPackage, org.jdom.Document, String)
     */
    public final StyleStyle getParentStyle() {
        final String parentName = getParentStyleName();
        if (parentName == null)
            return null;
        return this.getDesc().findStyleWithName(this.getPackage(), this.getElement().getDocument(), parentName);
    }

    private final String getParentStyleName() {
        return this.getElement().getAttributeValue("parent-style-name", getSTYLE());
    }

    /**
     * Get the parent style, evaluating conditions.
     * 
     * @param <S> style of the node, must be equal to this class.
     * @param styledNode the node to use for evaluations, not <code>null</code>.
     * @return the parent style or <code>null</code> if none exists.
     * @see StyleDesc#findStyleForNode(StyledNode, String)
     * @throws IllegalArgumentException if <code>styledNode</code> cannot have this as its style,
     *         e.g. {@link CellStyle cellStyle}.getParentStyle({@link Row row}).
     */
    public final <S extends StyleStyle> S getParentStyle(StyledNode<S, ?> styledNode) {
        if (!this.getDesc().equals(styledNode.getStyleDesc()))
            throw new IllegalArgumentException("Different style desc");
        final String parentName = getParentStyleName();
        if (parentName == null)
            return null;
        return styledNode.getStyleDesc().findStyleForNode(this.getPackage(), this.getElement().getDocument(), styledNode, parentName);
    }

    public final StyleStyle getDefaultStyle() {
        return this.getDesc().findDefaultStyle(getPackage());
    }

    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof StyleStyle) || !super.equals(obj))
            return false;
        final StyleStyle o = (StyleStyle) obj;
        return this.getFamily().equals(o.getFamily());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.getFamily().hashCode();
    }
}
