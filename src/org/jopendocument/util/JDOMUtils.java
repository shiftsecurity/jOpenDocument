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

package org.jopendocument.util;

import org.jopendocument.util.StringUtils;
import org.jopendocument.util.cc.IPredicate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * @author ILM Informatique 26 juil. 2004
 */
public final class JDOMUtils {

    public static final XMLOutputter OUTPUTTER;
    private static final SAXBuilder BUILDER;
    static {
        final Format rawFormat = Format.getRawFormat();
        // JDOM has \r\n hardcoded
        rawFormat.setLineSeparator("\n");
        OUTPUTTER = new XMLOutputter(rawFormat);

        BUILDER = new SAXBuilder();
        BUILDER.setValidation(false);
    }

    /**
     * Analyse la chaine passée et retourne l'Element correspondant.
     * 
     * @param xml une chaine contenant un élément XML.
     * @param namespaces les namespaces utilisés dans la chaine.
     * @return l'Element correspondant à la chaine passée.
     * @throws JDOMException si l'xml n'est pas bien formé.
     */
    public static Element parseElementString(String xml, Namespace[] namespaces) throws JDOMException {
        // l'element passé est le seul enfant de dummy
        // to be sure that the 0th can be cast use trim(), otherwise we might get a org.jdom.Text
        return (Element) parseString(xml.trim(), namespaces).get(0);
    }

    /**
     * Analyse la chaine passée et retourne la liste correspondante.
     * 
     * @param xml une chaine contenant de l'XML.
     * @param namespaces les namespaces utilisés dans la chaine.
     * @return la liste correspondant à la chaine passée.
     * @throws JDOMException si l'xml n'est pas bien formé.
     */
    public static List parseString(String xml, Namespace[] namespaces) throws JDOMException {
        // construit le dummy pour déclarer les namespaces
        String dummy = "<dummy";
        for (int i = 0; i < namespaces.length; i++) {
            Namespace ns = namespaces[i];
            dummy += " xmlns:" + ns.getPrefix() + "=\"" + ns.getURI() + "\"";
        }
        xml = dummy + ">" + xml + "</dummy>";

        return parseStringDocument(xml).getRootElement().removeContent();
    }

    /**
     * Analyse la chaine passée et retourne l'Element correspondant.
     * 
     * @param xml une chaine contenant de l'XML.
     * @return l'Element correspondant à la chaine passée.
     * @throws JDOMException si l'xml n'est pas bien formé.
     * @see #parseElementString(String, Namespace[])
     */
    public static Element parseString(String xml) throws JDOMException {
        return parseElementString(xml, new Namespace[0]);
    }

    /**
     * Analyse la chaine passée avec un builder par défaut et retourne le Document correspondant.
     * 
     * @param xml une chaine représentant un document XML.
     * @return le document correspondant.
     * @throws JDOMException si l'xml n'est pas bien formé.
     * @see #parseStringDocument(String, SAXBuilder)
     */
    public static synchronized Document parseStringDocument(String xml) throws JDOMException {
        // BUILDER is not thread safe
        return parseStringDocument(xml, BUILDER);
    }

    /**
     * Analyse la chaine passée et retourne le Document correspondant.
     * 
     * @param xml une chaine représentant un document XML.
     * @param builder le builder à utiliser.
     * @return le document correspondant.
     * @throws JDOMException si l'xml n'est pas bien formé.
     */
    public static Document parseStringDocument(String xml, SAXBuilder builder) throws JDOMException {
        Document doc = null;
        try {
            doc = builder.build(new StringReader(xml));
        } catch (IOException e) {
            // peut pas arriver, lis depuis une String
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * Ecrit l'XML en chaine, contrairement a toString().
     * 
     * @param xml l'élément à écrire.
     * @return l'XML en tant que chaine.
     */
    public static String output(Element xml) {
        return OUTPUTTER.outputString(xml);
    }

    /**
     * Ecrit l'XML en chaine, contrairement a toString().
     * 
     * @param xml l'élément à écrire.
     * @return l'XML en tant que chaine.
     */
    public static String output(Document xml) {
        return OUTPUTTER.outputString(xml);
    }

    public static Element getAncestor(Element element, final String name, final Namespace ns) {
        return getAncestor(element, new IPredicate<Element>() {
            @Override
            public boolean evaluateChecked(Element elem) {
                return elem.getName().equals(name) && elem.getNamespace().equals(ns);
            }
        });
    }

    public static Element getAncestor(Element element, final IPredicate<Element> pred) {
        Element current = element;
        while (current != null) {
            if (pred.evaluateChecked(current))
                return current;
            current = current.getParentElement();
        }
        return null;
    }

    /**
     * Add namespace declaration to <code>elem</code> if needed. Necessary since JDOM uses a simple
     * list.
     * 
     * @param elem the element where namespaces should be available.
     * @param c the namespaces to add.
     * @see Element#addNamespaceDeclaration(Namespace)
     */
    public static void addNamespaces(final Element elem, final Collection<Namespace> c) {
        if (c instanceof RandomAccess && c instanceof List) {
            final List<Namespace> list = (List<Namespace>) c;
            final int stop = c.size() - 1;
            for (int i = 0; i < stop; i++) {
                final Namespace ns = list.get(i);
                if (elem.getNamespace(ns.getPrefix()) == null)
                    elem.addNamespaceDeclaration(ns);
            }
        } else {
            for (final Namespace ns : c) {
                if (elem.getNamespace(ns.getPrefix()) == null)
                    elem.addNamespaceDeclaration(ns);
            }
        }
    }

    public static void addNamespaces(final Element elem, final Namespace... l) {
        for (final Namespace ns : l) {
            if (elem.getNamespace(ns.getPrefix()) == null)
                elem.addNamespaceDeclaration(ns);
        }
    }

    /**
     * Detach the passed element, and if its parent becomes empty, detach it too.
     * 
     * @param elem the element to detach.
     * @return the parent.
     */
    public static Element detachEmptyParent(final Element elem) {
        final Element parent = elem.getParentElement();
        elem.detach();
        if (parent != null && parent.getChildren().isEmpty())
            parent.detach();
        return parent;
    }

    /**
     * Get the requested child of <code>parent</code> or create one if necessary. The created child
     * is {@link Element#addContent(Content) added at the end}.
     * 
     * @param parent the parent.
     * @param name the name of the requested child.
     * @param ns the namespace of the requested child.
     * @return an existing or new child of <code>parent</code>.
     * @see Element#getChild(String, Namespace)
     */
    public static Element getOrCreateChild(final Element parent, final String name, final Namespace ns) {
        return getOrCreateChild(parent, name, ns, -1);
    }

    public static Element getOrCreateChild(final Element parent, final String name, final Namespace ns, final int index) {
        Element res = parent.getChild(name, ns);
        if (res == null) {
            res = new Element(name, ns);
            if (index < 0)
                parent.addContent(res);
            else
                parent.addContent(index, res);
        }
        assert res.getParent() == parent;
        return res;
    }

    /**
     * Aka mkdir -p.
     * 
     * @param current l'élément dans lequel créer la hierarchie.
     * @param path le chemin des éléments à créer, chaque niveau séparé par "/".
     * @return le dernier élément créé.
     */
    public Element mkElem(Element current, String path) {
        String[] items = path.split("/");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            String[] qname = item.split(":");
            final Element elem;
            if (qname.length == 1)
                elem = new Element(item);
            else
                // MAYBE check if getNS return null and throw exn
                elem = new Element(qname[1], current.getNamespace(qname[0]));
            current.addContent(elem);
            current = elem;
        }
        return current;
    }

    public static void insertAfter(final Element insertAfter, final Collection<? extends Content> toAdd) {
        insertSiblings(insertAfter, toAdd, true);
    }

    public static void insertBefore(final Element insertBefore, final Collection<? extends Content> toAdd) {
        insertSiblings(insertBefore, toAdd, false);
    }

    /**
     * Add content before or after an element.
     * 
     * @param sibling an element with a parent.
     * @param toAdd the content to add alongside <code>sibling</code>.
     * @param after <code>true</code> to add it after <code>sibling</code>.
     */
    public static void insertSiblings(final Element sibling, final Collection<? extends Content> toAdd, final boolean after) {
        final Element parentElement = sibling.getParentElement();
        final int index = parentElement.indexOf(sibling);
        parentElement.addContent(after ? index + 1 : index, toAdd);
    }

    /**
     * Test if two elements have the same namespace and name.
     * 
     * @param elem1 an element, can be <code>null</code>.
     * @param elem2 an element, can be <code>null</code>.
     * @return <code>true</code> if both elements have the same name and namespace, or if both are
     *         <code>null</code>.
     */
    public static boolean equals(Element elem1, Element elem2) {
        if (elem1 == elem2 || elem1 == null && elem2 == null)
            return true;
        else if (elem1 == null || elem2 == null)
            return false;
        else
            return elem1.getName().equals(elem2.getName()) && elem1.getNamespace().equals(elem2.getNamespace());
    }

    /**
     * Compare two elements and their descendants (only Element and Text). Texts are merged and
     * normalized.
     * 
     * @param elem1 first element.
     * @param elem2 second element.
     * @return <code>true</code> if both elements are equal.
     * @see #getContent(Element, IPredicate, boolean)
     */
    public static boolean equalsDeep(Element elem1, Element elem2) {
        return equalsDeep(elem1, elem2, true);
    }

    public static boolean equalsDeep(Element elem1, Element elem2, final boolean normalizeText) {
        return getDiff(elem1, elem2, normalizeText) == null;
    }

    static String getDiff(Element elem1, Element elem2, final boolean normalizeText) {
        if (elem1 == elem2)
            return null;
        if (!equals(elem1, elem2))
            return "element name or namespace";

        // ignore attributes order
        @SuppressWarnings("unchecked")
        final List<Attribute> attr1 = elem1.getAttributes();
        @SuppressWarnings("unchecked")
        final List<Attribute> attr2 = elem2.getAttributes();
        if (attr1.size() != attr2.size())
            return "attributes count";
        for (final Attribute attr : attr1) {
            if (!attr.getValue().equals(elem2.getAttributeValue(attr.getName(), attr.getNamespace())))
                return "attribute value";
        }

        // use content order
        final IPredicate<Content> filter = new IPredicate<Content>() {
            @Override
            public boolean evaluateChecked(Content input) {
                return input instanceof Text || input instanceof Element;
            }
        };
        // only check Element and Text (also merge them)
        final Iterator<Content> contents1 = getContent(elem1, filter, true);
        final Iterator<Content> contents2 = getContent(elem2, filter, true);
        while (contents1.hasNext() && contents2.hasNext()) {
            final Content content1 = contents1.next();
            final Content content2 = contents2.next();
            if (content1.getClass() != content2.getClass())
                return "content";
            if (content1 instanceof Text) {
                final String s1 = normalizeText ? ((Text) content1).getTextNormalize() : content1.getValue();
                final String s2 = normalizeText ? ((Text) content2).getTextNormalize() : content2.getValue();
                if (!s1.equals(s2))
                    return "text";
            } else {
                final String rec = getDiff((Element) content1, (Element) content2, normalizeText);
                if (rec != null)
                    return rec;
            }
        }
        if (contents1.hasNext() || contents2.hasNext())
            return "content size";

        return null;
    }

    /**
     * Get the filtered content of an element, optionnaly merging adjacent {@link Text}. Adjacent
     * text can only happen programmatically.
     * 
     * @param elem the parent.
     * @param pred which content to return.
     * @param mergeText <code>true</code> if adjacent Text should be merged into one,
     *        <code>false</code> to leave the list as it is.
     * @return the filtered content (not supportting {@link Iterator#remove()}).
     */
    public static Iterator<Content> getContent(final Element elem, final IPredicate<? super Content> pred, final boolean mergeText) {
        @SuppressWarnings("unchecked")
        final Iterator<Content> iter = (Iterator<Content>) elem.getContent(new Filter() {
            @Override
            public boolean matches(Object obj) {
                return pred.evaluateChecked((Content) obj);
            }
        }).iterator();
        if (!mergeText)
            return iter;

        return new Iterator<Content>() {

            private Content next = null;

            @Override
            public boolean hasNext() {
                return this.next != null || iter.hasNext();
            }

            @Override
            public Content next() {
                if (this.next != null) {
                    final Content res = this.next;
                    this.next = null;
                    return res;
                }

                Content res = iter.next();
                assert res != null;
                if (res instanceof Text && iter.hasNext()) {
                    this.next = iter.next();
                    Text concatText = null;
                    while (this.next instanceof Text) {
                        if (concatText == null) {
                            concatText = new Text(res.getValue());
                        }
                        concatText.append((Text) this.next);
                        this.next = iter.hasNext() ? iter.next() : null;
                    }
                    assert this.next != null;
                    if (concatText != null)
                        res = concatText;
                }

                return res;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * The namespace for the passed content.
     * 
     * @param input any JDOM document content.
     * @return <code>null</code> if the passed object cannot have a namespace,
     *         {@link Namespace#NO_NAMESPACE} if it could have one but has none.
     */
    static public final Namespace getNamespace(Object input) {
        final Namespace res;
        if (input instanceof Element) {
            res = ((Element) input).getNamespace();
        } else if (input instanceof Attribute) {
            res = ((Attribute) input).getNamespace();
        } else {
            res = null;
        }
        return res;
    }

    // @return SAXException If a SAX error occurs during parsing of doc.
    static SAXException validate(final Document doc, final Schema schema, final ErrorHandler errorHandler) {
        return validate(output(doc), schema, errorHandler);
    }

    static SAXException validate(final String doc, final Schema schema, final ErrorHandler errorHandler) {
        final ByteArrayInputStream ins = new ByteArrayInputStream(doc.getBytes(StringUtils.UTF8));
        final Validator validator = schema.newValidator();
        // ATTN workaround : contrary to documentation setting to null swallow exceptions
        if (errorHandler != null)
            validator.setErrorHandler(errorHandler);
        try {
            // don't use JDOMSource since it's as inefficient as this plus we can't control the
            // output.
            validator.validate(new StreamSource(ins));
            return null;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read the document", e);
        } catch (SAXException e) {
            return e;
        }
    }

    static void validateDTD(final Document doc, final SAXBuilder b, final ErrorHandler errorHandler) throws JDOMException {
        validateDTD(output(doc), b, errorHandler);
    }

    static void validateDTD(final String doc, final SAXBuilder b, final ErrorHandler errorHandler) throws JDOMException {
        final ErrorHandler origEH = b.getErrorHandler();
        final boolean origValidation = b.getValidation();
        try {
            b.setErrorHandler(errorHandler);
            b.setValidation(true);
            JDOMUtils.parseStringDocument(doc, b);
        } finally {
            b.setErrorHandler(origEH);
            b.setValidation(origValidation);
        }
    }

}