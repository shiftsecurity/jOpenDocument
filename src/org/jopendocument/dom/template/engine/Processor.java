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

import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.template.statements.Statement;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.OOXML;
import org.jopendocument.util.ExceptionUtils;
import org.jopendocument.util.JDOMUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.xpath.XPath;

/**
 * Generates the final document content from the preprocessed template content.
 * <p>
 * The behaviour of the substitution of fields can be controlled with prefixes :
 * <dl>
 * <dt>{@link Processor#AS_STR}</dt>
 * <dd>the following expression will be substitued as a String. For an XML element its tree will be
 * outputed.</dd>
 * <dt>{@link Processor#ENCODE}</dt>
 * <dd>the following expression will be converted to a String with toString() and then encoded using
 * {@link OOXML#encodeWS(String)}.</dd>
 * <dt>{@link Processor#OO_XML}</dt>
 * <dd>the following expression will be converted to a String with toString() and then parsed as OO
 * XML.</dd>
 * </dl>
 * If none of these is specified, an XML element will be treated as OO XML to be grafted (only its
 * children), else the value will simply be set as text of the field.
 * </p>
 * 
 * @param <E> type of material this processor uses.
 */
public class Processor<E> {

    public static final String OO_XML = "OOXML:";
    public static final String ENCODE = "%enc:";
    public static final String AS_STR = "%str:";

    private static Logger logger = Logger.getLogger(Processor.class.getName());

    private final Parsed<E> parsed;
    private final OOXML ns;
    // to workaround OO, see getChildren()
    private final XPath xp;
    private final DataModel model;

    private Material<E> material;

    Processor(Parsed<E> parsed, DataModel model) throws TemplateException {
        this.parsed = parsed;
        this.model = model;
        this.ns = this.parsed.getSource().getNS();
        try {
            this.xp = OOUtils.getXPath(".//text:span", this.ns.getVersion());
        } catch (JDOMException e) {
            throw ExceptionUtils.createExn(TemplateException.class, "xpath error", e);
        }
        this.material = null;
    }

    public final Parsed<E> getParsed() {
        return this.parsed;
    }

    public Material<E> getMaterial() {
        return this.material;
    }

    public Material<E> process() throws TemplateException {
        this.material = this.parsed.getSource().clone();
        transform(this.material.getRoot());
        final Material<E> res = this.material;
        this.material = null;
        return res;
    }

    /**
     * Transforms recursively the passed element. That means :
     * <ul>
     * <li>evaluating text-input and replacing them with the result.</li>
     * <li>executing the tags (if, for, set).</li>
     * </ul>
     * 
     * @param element the element to be processed.
     * @throws TemplateException
     */
    @SuppressWarnings("unchecked")
    public void transform(Element element) throws TemplateException {
        final String tagName = element.getName();
        if (tagName.equals("text-input")) {
            try {
                replaceField(element);
            } catch (Exception e) {
                throw new TemplateException("pb while replacing " + JDOMUtils.output(element), e);
            }
        } else if (element.getNamespace().equals(Statement.stmtNS)) {
            final Statement tag = this.parsed.getStatement(tagName);
            if (tag != null) {
                try {
                    if (logger.isLoggable(Level.FINE))
                        logger.fine("executing tag " + tagName);
                    tag.execute(this, element, this.model);
                } catch (Exception e) {
                    throw new TemplateException("pb while executing " + tag + " at " + JDOMUtils.output(element), e);
                }
            } else {
                logger.warning("unknown tag: " + tagName);
            }
        } else {
            // to continue the recursion is the responsability of the tag
            Iterator children = new ArrayList(element.getChildren()).iterator();
            while (children.hasNext()) {
                Element child = (Element) children.next();
                this.transform(child);
            }
        }
    }

    /**
     * Replace the given field by its value.
     * 
     * @param field a text-input.
     * @throws TemplateException if the field cannot be evaluated.
     */
    private void replaceField(Element field) throws TemplateException {
        String expression = field.getAttributeValue("description", this.ns.getVersion().getTEXT());

        final boolean asString = expression.startsWith(AS_STR);
        if (asString)
            expression = expression.substring(AS_STR.length());
        final boolean isOOXML = expression.startsWith(OO_XML);
        if (isOOXML)
            expression = expression.substring(OO_XML.length());
        final boolean encode = expression.startsWith(ENCODE);
        if (encode)
            expression = expression.substring(ENCODE.length());

        field.setName("span");
        field.setNamespace(this.ns.getVersion().getTEXT());
        Object value = this.model.eval(expression);
        if (value != null) {
            if (encode)
                value = this.ns.encodeWS(value.toString());
            else if (isOOXML) {
                try {
                    value = this.parse(value.toString());
                } catch (JDOMException e) {
                    throw ExceptionUtils.createExn(IllegalArgumentException.class, "invalid xml for : " + value, e);
                }
            }
            if (logger.isLoggable(Level.FINE))
                logger.fine("replacing field \"" + expression + "\" with \"" + value + "\"");
            if (!asString && value instanceof Element) {
                field.setContent(this.getChildren((Element) value));
            } else
                field.setText(display(value));
        } else {
            logger.warning("missing value for field: " + expression);
            field.setText("");
        }
    }

    private String display(Object value) {
        final String res;
        if (value instanceof org.jdom.Element) {
            res = JDOMUtils.output((org.jdom.Element) value);
        } else {
            res = value.toString();
        }
        return res;
    }

    /**
     * Get the children of the passed XML element.
     * 
     * @param xmlElem OpenOffice XML.
     * @return a list of Nodes.
     */
    @SuppressWarnings("unchecked")
    private List getChildren(Element xmlElem) {
        // WORKAROUND must NOT have a newline before text:span but MUST after, otherwise
        // text:span is ignored.
        ListIterator iter;
        try {
            iter = this.xp.selectNodes(xmlElem).listIterator();
        } catch (JDOMException e1) {
            throw ExceptionUtils.createExn(RuntimeException.class, "xpath error", e1);
        }
        while (iter.hasNext()) {
            final Element e = (Element) iter.next();
            final List<Content> parentContent = e.getParent().getContent();
            parentContent.add(parentContent.indexOf(e) + 1, new Text("\n"));
        }
        return xmlElem.removeContent();
    }

    private Element parse(String ooxml) throws JDOMException {
        return JDOMUtils.parseElementString("<dummy>" + ooxml + "</dummy>", this.ns.getVersion().getALL());
    }

}