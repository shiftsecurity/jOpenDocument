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

import java.util.ArrayList;
import java.util.List;

import org.jdom.Content;
import org.jdom.Element;

/**
 * If tag: conditionally displays a document element. <br>
 * Attributes:
 * <ul>
 * <li><b>element </b> (required): the document element to which this tag applies, e.g. "table-row"
 * </li>
 * <li><b>test </b> (required): expression that must be true for the element to be displayed</li>
 * </ul>
 */
public class If extends BaseStatement {

    public If() {
        super("if");
    }

    @SuppressWarnings("unchecked")
    public void prepare(Element script, Element command) throws TemplateException {
        String elementName = command.getAttributeValue("element");
        if (elementName == null)
            throw new TemplateException("missing required attribute for if tag: element");
        String testExpression = command.getAttributeValue("test");
        if (testExpression == null)
            throw new TemplateException("missing required attribute for if tag: test");
        Element target = getAncestorByName(script, elementName);
        if (target == null) {
            throw new TemplateException("no such element enclosing if tag: " + elementName + " for expression: " + testExpression);
        }
        Element parent = target.getParentElement();
        // Element ifTag = parent.addElement(getQName("if")).addAttribute("test",
        // testExpression);
        // parent.remove(ifTag);
        final Element ifTag = getElement(this.getName()).setAttribute("test", testExpression);
        int index = parent.indexOf(target);
        target.detach();
        parent.getContent().add(index, ifTag);
        ifTag.addContent(target);

        script.detach();
    }

    @SuppressWarnings("unchecked")
    public void execute(Processor processor, Element tag, DataModel model) throws TemplateException {
        String testExpression = tag.getAttributeValue("test");
        if (testExpression != null) {
            Object value = model.eval(testExpression);
            if (Boolean.TRUE.equals(value)) {
                final List<Content> parentContent = tag.getParentElement().getContent();
                final int index = parentContent.indexOf(tag);

                // copy cause the list is live, and we're about to remove its content
                final List<Element> children = new ArrayList<Element>(tag.getChildren());
                // change the parent of the children
                parentContent.addAll(index, tag.removeContent());
                // then transform, see ForEach
                for (final Element target : children) {
                    processor.transform(target);
                    removeSection(target);
                }
            }
        }
        tag.detach();
    }

}