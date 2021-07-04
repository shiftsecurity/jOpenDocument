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

package org.jopendocument.dom.template.statements;

import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.template.engine.DataModel;
import org.jopendocument.dom.template.engine.Processor;

import java.util.List;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A statement that will be executed to modify the parsed xml.
 * 
 * @author Sylvain
 */
public abstract class Statement {

    private final String name;

    public Statement(final String name) {
        super();
        this.name = name;
    }

    /**
     * The name of this statement. {@link #prepare(Element)} must create an Element with that name.
     * 
     * @return the name of this statement.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Find representations of this statement.
     * 
     * @param elem the element to test.
     * @return <code>true</code> if elem should prepared by this statement.
     */
    public abstract boolean matches(Element elem);

    /**
     * Creates an element named {@link #getName()}.
     * 
     * @param elem an element that returned <code>true</code> with {@link #matches(Element)}.
     * @throws TemplateException
     */
    public abstract void prepare(Element elem) throws TemplateException;

    /**
     * Should produce valid OpenDocument XML.
     * 
     * @param processor the processor.
     * @param elem an element that has been prepared.
     * @param model the data.
     * @throws TemplateException
     */
    public abstract void execute(Processor<?> processor, Element elem, DataModel model) throws TemplateException;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getName();
    }

    // *** static

    public static final Namespace stmtNS = Namespace.getNamespace("jod", "http://www.jopendocument.org");

    protected static Element getElement(String name) {
        return new Element(name, stmtNS);
    }

    protected static Element getAncestorByName(Element element, String name) {
        while (true) {
            final Element parent = element.getParentElement();
            if (parent == null)
                return null;
            if (parent.getName().equals(name))
                return parent;
            element = parent;
        }
    }

    /**
     * Replace elem by its content. Ie elem will be detached from the tree and its content will be
     * inserted at this former location.
     * 
     * @param elem the element to remove.
     */
    @SuppressWarnings("unchecked")
    protected static void pullUp(Element elem) {
        final List<Content> parentContent = elem.getParentElement().getContent();
        final int index = parentContent.indexOf(elem);
        elem.detach();
        parentContent.addAll(index, elem.removeContent());
    }
}
