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

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

/**
 * A node with a style.
 * 
 * @author Sylvain CUAZ
 * 
 * @param <S> type of style.
 * @param <D> type of document.
 */
public abstract class StyledNode<S extends Style, D extends ODDocument> extends ODNode {

    static protected final <S extends Style> StyleDesc<S> getStyleDesc(final Element local, final Class<S> styleClass) {
        return Style.getStyleDesc(styleClass, XMLVersion.getVersion(local));
    }

    static public final Attribute setStyleName(final Element elem, final String name) {
        if (name == null) {
            elem.removeAttribute("style-name", elem.getNamespace());
            return null;
        } else {
            Attribute res = elem.getAttribute("style-name", elem.getNamespace());
            if (res == null) {
                res = new Attribute("style-name", name, elem.getNamespace());
                elem.setAttribute(res);
            } else {
                res.setValue(name);
            }
            return res;
        }
    }

    private final StyleDesc<S> styleClass;

    /**
     * Create a new instance. We used to find the {@link Style} class with reflection but this was
     * slow.
     * 
     * @param local our XML model.
     * @param styleClass our class of style, cannot be <code>null</code>.
     */
    public StyledNode(Element local, final Class<S> styleClass) {
        this(local, getStyleDesc(local, styleClass));
    }

    // allow to pass StyleDesc since Style.getStyleDesc() was the longest operation of this
    // constructor, and this constructor is called for every Table, Column, Row and Cell, i.e.
    // up to millions of times.
    protected StyledNode(Element local, final StyleDesc<S> styleDesc) {
        super(local);
        if (styleDesc == null)
            throw new NullPointerException("null style desc");
        this.styleClass = styleDesc;
        assert styleDesc.getVersion().equals(XMLVersion.getVersion(local));
        assert this.styleClass.getRefElements().contains(this.getElement().getQualifiedName()) : this.getElement().getQualifiedName() + " not in " + this.styleClass;
    }

    // can be null if this node wasn't created from a document (eg new Paragraph())
    public abstract D getODDocument();

    public final StyleDesc<S> getStyleDesc() {
        return this.styleClass;
    }

    public final <S2 extends Style> StyleDesc<S2> getStyleDesc(Class<S2> clazz) {
        return Style.getStyleDesc(clazz, getODDocument().getVersion());
    }

    public final <S2 extends StyleStyle> StyleStyleDesc<S2> getStyleStyleDesc(Class<S2> clazz) {
        return Style.getStyleStyleDesc(clazz, getODDocument().getVersion());
    }

    public final S getStyle() {
        // null avoid getting styleName if we haven't any Document
        return this.getStyle(null);
    }

    protected final S getStyle(final String styleName) {
        final D doc = this.getODDocument();
        return doc == null ? null : this.getStyle(doc.getPackage(), getElement().getDocument(), styleName == null ? getStyleName() : styleName);
    }

    protected final S getStyle(final ODPackage pkg, final Document doc) {
        return this.getStyle(pkg, doc, getStyleName());
    }

    protected final S getStyle(final ODPackage pkg, final Document doc, final String styleName) {
        return this.styleClass.findStyleForNode(pkg, doc, this, styleName);
    }

    /**
     * Assure that this node's style is only referenced by this. I.e. after this method returns the
     * style of this node can be safely modified without affecting other nodes.
     * 
     * @return this node's style, never <code>null</code>.
     */
    public final S getPrivateStyle() {
        final S currentStyle = this.getStyle();
        if (currentStyle != null && currentStyle.isReferencedAtMostOnce())
            return currentStyle;

        final S newStyle;
        if (currentStyle == null)
            newStyle = this.styleClass.createAutoStyle(getODDocument().getPackage());
        else
            newStyle = this.styleClass.getStyleClass().cast(currentStyle.dup());
        this.setStyleName(newStyle.getName());
        // return newStyle to avoid the costly getStyle()
        assert this.getStyle().equals(newStyle);
        return newStyle;
    }

    // some nodes have more complicated ways of finding their style (eg Cell)
    protected String getStyleName() {
        return this.getElement().getAttributeValue("style-name", this.getElement().getNamespace());
    }

    public final Attribute setStyleName(final String name) {
        return setStyleName(this.getElement(), name);
    }
}