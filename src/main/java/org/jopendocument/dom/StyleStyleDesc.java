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

import java.util.Collections;
import java.util.List;

import org.jdom.Element;

/**
 * Describe a family of style.
 * 
 * @author Sylvain CUAZ
 * 
 * @param <S> type of style
 */
public abstract class StyleStyleDesc<S extends StyleStyle> extends StyleDesc<S> {

    static final String ELEMENT_NAME = "style";
    public static final String ELEMENT_DEFAULT_NAME = "default-style";

    static String getFamily(final Element styleElem) {
        assert styleElem.getName().equals(ELEMENT_NAME) || styleElem.getName().equals(ELEMENT_DEFAULT_NAME);
        return styleElem.getAttributeValue("family", styleElem.getNamespace("style"));
    }

    public static <C extends StyleStyle> StyleStyleDesc<C> copy(final StyleStyleDesc<C> toClone, final XMLVersion version) {
        final StyleStyleDesc<C> res = new StyleStyleDesc<C>(toClone, version) {
            @Override
            public C create(ODPackage pkg, Element e) {
                return toClone.create(pkg, e);
            }
        };
        return res;
    }

    private final String family;

    protected StyleStyleDesc(final Class<S> clazz, final XMLVersion version, String family, String baseName, String ns) {
        this(clazz, version, family, baseName, ns, Collections.singletonList(ns + ":" + family));
    }

    protected StyleStyleDesc(final Class<S> clazz, final XMLVersion version, String family, String baseName, String ns, final List<String> refQNames) {
        this(clazz, version, family, baseName);
        this.getRefElementsMap().addAll(ns + ":style-name", refQNames);
    }

    protected StyleStyleDesc(final Class<S> clazz, final XMLVersion version, String family, String baseName) {
        super(clazz, version, ELEMENT_NAME, baseName);
        this.family = family;
    }

    protected StyleStyleDesc(final StyleStyleDesc<S> toClone, final XMLVersion version) {
        super(toClone, version);
        this.family = toClone.getFamily();
    }

    public final String getFamily() {
        return this.family;
    }

    @Override
    protected void initStyle(Element elem) {
        super.initStyle(elem);
        elem.setAttribute("family", this.getFamily(), elem.getNamespace());
    }

    public final S findDefaultStyle(final ODPackage pkg) {
        return this.getDefaultStyle(pkg, false);
    }

    public final S getDefaultStyle(final ODPackage pkg, final boolean create) {
        final Element styleElem = pkg.getDefaultStyle(this, create);
        return styleElem == null ? null : this.create(pkg, styleElem);
    }

    public final Element createDefaultElement() {
        final Element res = new Element(StyleStyleDesc.ELEMENT_DEFAULT_NAME, getElementNS());
        this.initStyle(res);
        return res;
    }

    @Override
    public String toString() {
        return super.toString() + " family = " + this.getFamily();
    }
}