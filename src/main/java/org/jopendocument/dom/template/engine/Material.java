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

package org.jopendocument.dom.template.engine;

import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODXMLDocument;
import org.jopendocument.dom.OOXML;
import org.jopendocument.util.CopyUtils;

import java.io.File;

import org.jdom.Document;
import org.jdom.Element;

/**
 * XML material that will be parsed and executed.
 * 
 * @param <W> the type of this material.
 * @author Sylvain
 */
public class Material<W> implements Cloneable {

    // for cloning
    @SuppressWarnings("unchecked")
    private static final <Whole> Material<Whole> from(Whole p) {
        if (p instanceof Document)
            return (Material<Whole>) from((Document) p);
        else if (p instanceof ODXMLDocument)
            return (Material<Whole>) from((ODXMLDocument) p);
        else if (p instanceof ODPackage)
            return (Material<Whole>) from((ODPackage) p);
        else
            return (Material<Whole>) from((Element) p);
    }

    public static final Material<Document> from(Document doc) {
        return new Material<Document>(doc, doc.hasRootElement() ? doc.getRootElement() : null);
    }

    public static final Material<Element> from(Element elem) {
        return new Material<Element>(elem, elem);
    }

    public static final <E extends ODXMLDocument> Material<E> from(E doc) {
        return new Material<E>(doc, doc.getDocument().getRootElement());
    }

    public static final <E extends ODPackage> Material<E> from(E doc) {
        final Material<E> res = new Material<E>(doc, doc.getContent().getDocument().getRootElement());
        res.setBase(doc.getFile());
        return res;
    }

    private final W whole;
    private final Element root;
    private File base;

    private Material(final W whole, final Element root) {
        this.whole = whole;
        this.root = root;
        this.base = null;
    }

    public Element getRoot() {
        return this.root;
    }

    public boolean hasRoot() {
        return this.getRoot() != null;
    }

    public W getWhole() {
        return this.whole;
    }

    public OOXML getNS() {
        return OOXML.get(this.getRoot());
    }

    @Override
    public Material<W> clone() {
        final Material<W> res = Material.from(CopyUtils.copy(this.getWhole()));
        res.setBase(this.getBase());
        return res;
    }

    public File getBase() {
        return this.base;
    }

    public void setBase(File base) {
        this.base = base;
    }
}
