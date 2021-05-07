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

import org.jopendocument.dom.ODNodeDesc;
import org.jopendocument.dom.XMLFormatVersion;

import org.jdom.Element;
import org.jdom.Parent;

public abstract class TextNodeDesc<N extends TextNode<?>> extends ODNodeDesc<N> {

    static public <N extends TextNode<?>> TextNodeDesc<N> get(Class<N> clazz) {
        return (TextNodeDesc<N>) ODNodeDesc.getRegistry().getDesc(clazz);
    }

    public TextNodeDesc(final Class<N> nodeClass) {
        super(nodeClass);
    }

    @Override
    public Children<N> getChildren(final XMLFormatVersion vers, final Parent p) {
        return new Children<N>(this, null, vers, p) {
            @Override
            protected N getDocumentLess(int i) {
                return ((TextNodeDesc<N>) this.getDesc()).wrapNode(this.getVersion(), this.getList().get(i));
            }
        };
    }

    public final Element createEmptyElement(final XMLFormatVersion vers) {
        final Element res = this.createProto(vers);
        this.fillEmptyElement(res, vers);
        return res;
    }

    protected void fillEmptyElement(final Element elem, final XMLFormatVersion vers) {
    }

    public final N createEmptyNode(final XMLFormatVersion vers) {
        return this.wrapNode(vers, this.createEmptyElement(vers));
    }

    protected abstract N wrapNode(final XMLFormatVersion vers, final Element elem);
}
