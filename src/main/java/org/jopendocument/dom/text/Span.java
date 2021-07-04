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

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.XMLFormatVersion;

import org.jdom.Element;

/**
 * A portion of text that can be nested. See §6.1.7 of OpenDocument v1.2.
 */
public class Span extends TextNode<TextStyle> {

    public static final TextNodeDesc<?> NODE_DESC = new TextNodeDesc<Span>(Span.class) {

        @Override
        public Element createProto(XMLFormatVersion vers) {
            return new Element("span", vers.getXMLVersion().getTEXT());
        }

        @Override
        public Span wrapNode(ODDocument doc, Element e) {
            return new Span(e, doc);
        }

        @Override
        protected Span wrapNode(XMLFormatVersion vers, Element elem) {
            return new Span(elem, vers);
        }
    };

    static public Element createEmpty(XMLFormatVersion ns) {
        return NODE_DESC.createEmptyElement(ns);
    }

    // not public since, local element cannot be checked against vers
    protected Span(Element local, final XMLFormatVersion vers) {
        super(local, TextStyle.class, vers);
    }

    public Span(Element elem, ODDocument parent) {
        super(elem, TextStyle.class, parent);
    }

    public Span(XMLFormatVersion ns) {
        this(createEmpty(ns), ns);
    }

    public Span() {
        this(XMLFormatVersion.getDefault());
    }

    public Span(String text) {
        this();
        addContent(text);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getElement().getText();
    }
}
