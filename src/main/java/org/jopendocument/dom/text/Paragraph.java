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
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.XMLFormatVersion;

import org.jdom.Element;

/**
 * A text paragraph, the basic unit of text. See §4.1 of the OpenDocument specification.
 */
public class Paragraph extends TextNode<ParagraphStyle> {

    public static final TextNodeDesc<?> NODE_DESC = new TextNodeDesc<Paragraph>(Paragraph.class) {

        @Override
        public Element createProto(XMLFormatVersion vers) {
            return new Element("p", vers.getXMLVersion().getTEXT());
        }

        @Override
        public Paragraph wrapNode(ODDocument doc, Element e) {
            return new Paragraph(e, doc);
        }

        @Override
        protected Paragraph wrapNode(XMLFormatVersion vers, Element elem) {
            return new Paragraph(elem, vers);
        }
    };

    static public Element createEmpty(XMLFormatVersion ns) {
        return NODE_DESC.createEmptyElement(ns);
    }

    // not public since, local element cannot be checked against vers
    protected Paragraph(Element local, final XMLFormatVersion vers) {
        super(local, ParagraphStyle.class, vers);
    }

    public Paragraph(Element elem, ODDocument parent) {
        super(elem, ParagraphStyle.class, parent);
    }

    public Paragraph(Element elem, ODPackage pkg) {
        this(elem, pkg.getFormatVersion());
        if (pkg.getXMLFile(elem.getDocument()) == null)
            throw new IllegalArgumentException("Element not in package");
    }

    public Paragraph(XMLFormatVersion ns) {
        this(createEmpty(ns), ns);
    }

    public Paragraph() {
        this(XMLFormatVersion.getDefault());
    }

    public Paragraph(String text) {
        this();
        addContent(text);
    }

    // MAYBE add updateStyle() which evaluates the conditions in style:map of the conditional style
    // to update style-name
    /**
     * A style containing conditions and maps to other styles.
     * 
     * @return the conditional style or <code>null</code> if none or if this isn't in a document.
     */
    public final ParagraphStyle getConditionalStyle() {
        final String condName = this.getElement().getAttributeValue("cond-style-name", this.getElement().getNamespace());
        if (condName == null)
            return null;
        else
            return getStyle(condName);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getElement().getText();
    }
}
