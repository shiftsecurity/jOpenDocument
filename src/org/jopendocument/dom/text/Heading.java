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
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.dom.XMLFormatVersion;

import org.jdom.Element;

/**
 * A text heading, defining the chapter structure. See §4.1 of the OpenDocument specification.
 */
public class Heading extends Paragraph {

    public static final TextNodeDesc<?> NODE_DESC = new TextNodeDesc<Heading>(Heading.class) {

        @Override
        public Element createProto(XMLFormatVersion vers) {
            return new Element("h", vers.getXMLVersion().getTEXT());
        }

        @Override
        protected void fillEmptyElement(Element elem, XMLFormatVersion vers) {
            super.fillEmptyElement(elem, vers);
            // have to add level since it's required by OpenDocument-strict-schema-v1.1.rng
            elem.setAttribute("outline-level", "1", elem.getNamespace());
        }

        @Override
        public Heading wrapNode(ODDocument doc, Element e) {
            return new Heading(e, doc);
        }

        @Override
        protected Heading wrapNode(XMLFormatVersion vers, Element elem) {
            return new Heading(elem, vers);
        }
    };

    static public Element createEmpty(XMLFormatVersion ns) {
        return NODE_DESC.createEmptyElement(ns);
    }

    public Heading(String text) {
        this();
        addContent(text);
    }

    public Heading() {
        this(XMLFormatVersion.getDefault());
    }

    public Heading(final XMLFormatVersion ns) {
        this(createEmpty(ns), ns);
    }

    // not public since local element cannot be checked against vers
    protected Heading(Element elem, XMLFormatVersion vers) {
        super(elem, vers);
    }

    public Heading(final Element elem, final ODDocument parent) {
        super(elem, parent);
    }

    public final int getLevel() {
        final String attr = this.getElement().getAttributeValue("outline-level", this.getElement().getNamespace());
        // see 4.1.1
        return StyleProperties.parseInt(attr, 1);
    }

    public final void setLevel(int level) {
        if (level < 1)
            throw new IllegalArgumentException(level + " < 1");
        this.getElement().setAttribute("outline-level", level + "", this.getElement().getNamespace());
    }
}
