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
import org.jopendocument.util.ExceptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 * ForEach tag: repeats a document element for each item in a collection. <br>
 * Attributes:
 * <ul>
 * <li><b>element </b> (required): the document element to which this tag applies, e.g. "table-row"</li>
 * <li><b>items </b> (required): expression evaluating to a Collection to be iterated</li>
 * <li><b>var </b> (required): variable name where the current iteration item is stored</li>
 * <li><b>alternate </b> (optional): number of document elements to be alternated during iteration,
 * typically used for alternating row background colors with a value of "2"</li>
 * <li><b>multiple </b> (optional): number of document elements to be copied during iteration, not
 * compatible with "alternate".</li>
 * </ul>
 */
public class ForEach extends BaseStatement {
    private static Logger logger = Logger.getLogger(ForEach.class.getName());

    public ForEach() {
        super("forEach");
    }

    @SuppressWarnings("unchecked")
    public void prepare(Element script, Element command) throws TemplateException {
        String elementName = command.getAttributeValue("element");
        if (elementName == null)
            throw new TemplateException("missing required attribute for forEach tag: element");
        String itemsExpression = command.getAttributeValue("items");
        if (itemsExpression == null)
            throw new TemplateException("missing required attribute for forEach tag: items");
        String varName = command.getAttributeValue("var");
        if (varName == null)
            throw new TemplateException("missing required attribute for forEach tag: var");
        // alternate
        final int alternate;
        String alternateString = command.getAttributeValue("alternate");
        if (alternateString == null) {
            alternate = 1;
        } else {
            try {
                alternate = Integer.parseInt(alternateString);
            } catch (Throwable t) {
                throw new TemplateException("invalid alternate attribute for forEach tag: " + alternateString);
            }
        }
        // multiple
        final int multiple;
        final String multipleString = command.getAttributeValue("multiple");
        if (multipleString == null) {
            multiple = 0;
        } else {
            try {
                multiple = Integer.parseInt(multipleString);
            } catch (Throwable t) {
                throw new TemplateException("invalid alternate attribute for forEach tag: " + multipleString);
            }
        }
        if (alternate > 1 && multiple > 0) {
            throw new TemplateException("both alternate and multiple have been specified");
        }
        Element element = getAncestorByName(script, elementName);
        if (element == null) {
            throw new TemplateException("no such element enclosing forEach: " + elementName + " for expression: " + itemsExpression);
        }
        Element parent = element.getParentElement();
        // Element forEachTag = parent.addElement(getQName("forEach")).addAttribute("items",
        // itemsExpression).addAttribute("var", varName);
        final Element forEachTag = getElement("forEach").setAttribute("items", itemsExpression).setAttribute("var", varName);
        final int siblingsToAdd;
        if (multiple > 0) {
            forEachTag.setAttribute("multiple", "yes");
            siblingsToAdd = multiple - 1;
        } else {
            siblingsToAdd = alternate - 1;
        }
        int index = parent.indexOf(element);
        List<Element> targets = new ArrayList<Element>();
        targets.add(element);
        if (siblingsToAdd > 0) {
            List siblings;
            try {
                final XPath xpath = XPath.newInstance("following-sibling::*");
                siblings = xpath.selectNodes(element);
            } catch (JDOMException e) {
                throw ExceptionUtils.createExn(TemplateException.class, "xpath error", e);
            }
            if (siblingsToAdd > siblings.size())
                throw new TemplateException("alternate or multiple is greater (" + (siblingsToAdd + 1) + ") than actual table size (" + siblings.size() + ").");
            for (int i = 0; i < siblingsToAdd; i++) {
                Element sibling = (Element) siblings.get(i);
                targets.add(sibling);
                sibling.detach();
            }
        }
        element.detach();
        parent.getContent().add(index, forEachTag);
        for (int i = 0; i < targets.size(); i++) {
            forEachTag.addContent(targets.get(i));
        }

        script.detach();
    }

    @SuppressWarnings("unchecked")
    public void execute(Processor processor, Element tag, DataModel model) throws TemplateException {
        String varName = tag.getAttributeValue("var");
        String itemsExpression = tag.getAttributeValue("items");
        final boolean multiple = "yes".equals(tag.getAttributeValue("multiple"));
        Object value = model.eval(itemsExpression);
        if (value == null) {
            logger.info("forEach items: null expression: " + itemsExpression);
            return;
        }
        final Collection items;
        if (value instanceof Collection)
            items = (Collection) value;
        else if (value instanceof Object[])
            items = Arrays.asList((Object[]) value);
        else
            items = null;
        if (items == null && !(value instanceof Iterator))
            throw new TemplateException("forEach items neither a Collection nor an Iterator: " + itemsExpression + " => " + value.getClass() + ":" + value);
        List targets = tag.getChildren();
        List<Element> parentContent = tag.getParentElement().getContent();
        int iterationCount = 0;
        int index = parentContent.indexOf(tag);
        // ne peut utiliser de Map si plusieurs fois le même éléments dans 'items'
        final List<Object> itemsList = items == null ? new ArrayList<Object>() : new ArrayList<Object>(items.size());
        final List<List<Element>> createdElems = items == null ? new ArrayList<List<Element>>() : new ArrayList<List<Element>>(items.size());
        final Iterator itemsIter = (Iterator) (items != null ? items.iterator() : value);
        // first we create all elements
        while (itemsIter.hasNext()) {
            final Object item = itemsIter.next();
            final List toAdd;
            if (multiple) {
                toAdd = targets;
            } else {
                toAdd = Collections.singletonList(targets.get(iterationCount % targets.size()));
                ++iterationCount;
            }
            final List<Element> added = new ArrayList<Element>(toAdd.size());
            final Iterator targetIter = toAdd.iterator();
            while (targetIter.hasNext()) {
                final Element target = (Element) targetIter.next();
                Element newElement = (Element) target.clone();
                parentContent.add(index++, newElement);
                added.add(newElement);
            }
            // store the new elements and their associated item
            itemsList.add(item);
            createdElems.add(added);
        }
        // then transform them
        // forced to do that otherwise if we transform right after adding, the newly added element
        // might be removed and this will mess with index++
        final ListIterator<Object> i = itemsList.listIterator();
        while (i.hasNext()) {
            final Object item = i.next();
            model.put(varName, item);
            final Iterator addedIter = createdElems.get(i.previousIndex()).iterator();
            while (addedIter.hasNext()) {
                final Element newElement = (Element) addedIter.next();
                processor.transform(newElement);
                removeSection(newElement);
            }
        }

        tag.detach();
    }

}