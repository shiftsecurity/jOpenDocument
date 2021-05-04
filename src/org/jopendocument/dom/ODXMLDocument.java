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

/*
 * Créé le 28 oct. 2004
 */
package org.jopendocument.dom;

import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.util.cc.IFactory;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.Validator;
import org.jopendocument.util.XPathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

/**
 * An OpenDocument XML document, like content.xml ou styles.xml.
 * 
 * @author Sylvain CUAZ
 */
public class ODXMLDocument {

    /**
     * All top-level elements that an office document may contain. Note that only the single xml
     * representation (office:document) contains all of them.
     */
    private static final Map<XMLVersion, List<Element>> ELEMS_ORDER;
    static {
        ELEMS_ORDER = new HashMap<XMLVersion, List<Element>>(2);
        ELEMS_ORDER.put(XMLVersion.getOOo(), createChildren(XMLVersion.getOOo()));
        ELEMS_ORDER.put(XMLVersion.getOD(), createChildren(XMLVersion.getOD()));
    }

    private static final List<Element> createChildren(XMLVersion ins) {
        final Namespace ns = ins.getOFFICE();
        final List<Element> res = new ArrayList<Element>(8);
        res.add(new Element("meta", ns));
        res.add(new Element("settings", ns));
        final OOXML xml = OOXML.getLast(ins);
        res.add(new Element(xml.getOfficeScripts(), ns));
        res.add(new Element(xml.getFontDecls()[0], ns));
        res.add(new Element("styles", ns));
        res.add(new Element("automatic-styles", ns));
        res.add(new Element("master-styles", ns));
        res.add(new Element("body", ns));
        return res;
    }

    static private final int ITERATIONS_WARNING_COUNT = 2000;
    // namespaces for the name attributes
    static private final Map<String, String> namePrefixes;
    static {
        namePrefixes = new HashMap<String, String>();
        namePrefixes.put("table:table", "table");
        namePrefixes.put("text:a", "office");
        namePrefixes.put("draw:text-box", "draw");
        namePrefixes.put("draw:image", "draw");
        namePrefixes.put("draw:frame", "draw");
    }

    /**
     * The XML elements posessing a name.
     * 
     * @return the qualified names of named elements.
     * @see #getDescendantByName(String, String)
     */
    public static Set<String> getNamedElements() {
        return Collections.unmodifiableSet(namePrefixes.keySet());
    }

    public static final ODXMLDocument create(final Document doc) {
        if (RootElement.fromDocument(doc) == RootElement.SINGLE_CONTENT)
            return new ODSingleXMLDocument(doc);
        else
            return new ODXMLDocument(doc);
    }

    private final Document content;
    private final XMLFormatVersion version;
    private final ChildCreator childCreator;
    private final Map<String, Integer> styleNamesLast;

    // before making it public, assure that content is really of version "version"
    // eg by checking some namespace
    protected ODXMLDocument(final Document content, final XMLFormatVersion version) {
        if (content == null)
            throw new NullPointerException("null document");
        this.content = content;
        this.version = version;
        this.childCreator = new ChildCreator(this.content.getRootElement(), ELEMS_ORDER.get(this.getVersion()));
        this.styleNamesLast = new LinkedHashMap<String, Integer>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<String, Integer> eldest) {
                return this.size() > 15;
            }
        };
    }

    public ODXMLDocument(Document content) {
        this(content, XMLFormatVersion.get(content.getRootElement()));
    }

    public ODXMLDocument(ODXMLDocument doc) {
        this((Document) doc.content.clone(), doc.version);
    }

    public Document getDocument() {
        return this.content;
    }

    public Validator getValidator() {
        return getXML().getValidator(this.getDocument());
    }

    public final OOXML getXML() {
        return this.getFormatVersion().getXML();
    }

    public final XMLFormatVersion getFormatVersion() {
        return this.version;
    }

    public final XMLVersion getVersion() {
        return this.getFormatVersion().getXMLVersion();
    }

    // *** children

    public final Element getChild(String childName) {
        return this.getChild(childName, false);
    }

    /**
     * Return the asked child, optionally creating it.
     * 
     * @param childName the name of the child.
     * @param create whether it should be created in case it doesn't exist.
     * @return the asked child or <code>null</code> if it doesn't exist and create is
     *         <code>false</code>
     */
    public Element getChild(String childName, boolean create) {
        return this.childCreator.getChild(this.getVersion().getOFFICE(), childName, create);
    }

    public void setChild(Element elem) {
        if (!elem.getNamespace().equals(this.getVersion().getOFFICE()))
            throw new IllegalArgumentException("all children of a document belong to the office namespace.");
        this.childCreator.setChild(elem);
    }

    // *** descendants

    protected final Element getDescendant(String path) throws JDOMException {
        return this.getDescendant(path, false);
    }

    protected final Element getDescendant(String path, boolean create) throws JDOMException {
        Element res = (Element) this.getXPath(path).selectSingleNode(this.getDocument().getRootElement());
        if (res == null && create) {
            final Element parent = this.getDescendant(XPathUtils.parentOf(path), create);
            final String namespace = XPathUtils.namespace(path);
            final Namespace ns = namespace == null ? null : this.getVersion().getNS(namespace);
            res = new Element(XPathUtils.localName(path), ns);
            parent.addContent(res);
        }
        return res;
    }

    public final XPath getXPath(String string) throws JDOMException {
        return OOUtils.getXPath(string, this.getVersion());
    }

    /**
     * Search for a descendant with the passed name.
     * 
     * @param qName the XML element qualified name, eg "table:table".
     * @param name the value of the name, eg "MyTable".
     * @return the first element named <code>name</code> or <code>null</code> if none is found, eg
     *         &lt;table:table table:name="MyTable" &gt;
     * @throws IllegalArgumentException if <code>qName</code> is not in {@link #getNamedElements()}
     */
    public final Element getDescendantByName(String qName, String name) {
        return this.getDescendantByName(this.getDocument().getRootElement(), qName, name);
    }

    public final Element getDescendantByName(Element root, String qName, String name) {
        if (root.getDocument() != this.getDocument())
            throw new IllegalArgumentException("root is not part of this.");
        if (!namePrefixes.containsKey(qName))
            throw new IllegalArgumentException(qName + " not in " + getNamedElements());
        final String xp = ".//" + qName + "[@" + namePrefixes.get(qName) + ":name='" + name + "']";
        try {
            return (Element) this.getXPath(xp).selectSingleNode(root);
        } catch (JDOMException e) {
            // static xpath, should not happen
            throw new IllegalStateException("could not find " + xp, e);
        }
    }

    // *** styles
    public final Element getStyle(final StyleDesc<?> styleDesc, final String name) {
        return this.getStyle(styleDesc, name, this.getDocument());
    }

    public final Element getStyle(final StyleDesc<?> styleDesc, final String name, final Document referent) {
        final String family = styleDesc instanceof StyleStyleDesc<?> ? ((StyleStyleDesc<?>) styleDesc).getFamily() : null;
        // see section 14.1 § Style Name : "uniquely identifies a style"
        // was using an XPath but we had performance issues, we first rewrote it using variables
        // (and thus parsing it only once) and saw a 40% speedup, but by rewriting it in java we
        // we went from 70ms/instance + 1ms/call to 0.015ms/call :-)
        // final String stylePath = "style:style[@style:family=$family and @style:name=$name]";
        // this.styleXP = this.getXPath("./office:styles/" + stylePath +
        // " | ./office:automatic-styles/" + stylePath +
        // " | ./office:master-styles/style:master-page/" + stylePath);
        final Element root = this.getDocument().getRootElement();
        final Namespace office = getVersion().getOFFICE();
        Element res = this.findStyleChild(root.getChild("styles", office), styleDesc.getElementNS(), styleDesc.getElementName(), family, name);
        if (res != null) {
            return res;
        }

        // automatic-styles are only reachable from the same document
        if (referent == this.getDocument()) {
            res = this.findStyleChild(root.getChild("automatic-styles", office), styleDesc.getElementNS(), styleDesc.getElementName(), family, name);
            if (res != null) {
                return res;
            }
        }

        final Element masterStyles = root.getChild("master-styles", office);
        if (masterStyles != null) {
            res = this.findStyleChild(root.getChild("master-page", getVersion().getSTYLE()), styleDesc.getElementNS(), styleDesc.getElementName(), family, name);
            if (res != null) {
                return res;
            }
        }

        return null;
    }

    public final Element getDefaultStyle(final StyleStyleDesc<?> styleDesc, final boolean create) {
        final Element stylesElem = this.getChild("styles", create);
        final Element res = this.findStyleChild(stylesElem, styleDesc.getElementNS(), StyleStyleDesc.ELEMENT_DEFAULT_NAME, styleDesc.getFamily(), null);
        if (res != null || !create) {
            return res;
        } else {
            final Element created = styleDesc.createDefaultElement();
            // OK to add at the end, the relaxNG for office:styles is 'interleave'
            stylesElem.addContent(created);
            return created;
        }
    }

    private final Element findStyleChild(final Element styles, final Namespace elemNS, final String elemName, final String family, final String name) {
        if (styles == null)
            return null;

        final Namespace styleNS = getVersion().getSTYLE();
        // from JDOM : traversal through the List is best done with a Iterator
        for (final Object o : styles.getChildren(elemName, elemNS)) {
            final Element styleElem = (Element) o;
            // name first since it is more specific (and often includes family, eg "co2")
            if ((name == null || name.equals(styleElem.getAttributeValue("name", styleNS))) && (family == null || family.equals(StyleStyleDesc.getFamily(styleElem)))) {
                return styleElem;
            }
        }
        return null;
    }

    /**
     * Find an unused style name in this document.
     * 
     * @param desc the description of the style.
     * @param baseName the base name, e.g. "myColStyle".
     * @return an unused name, e.g. "myColStyle12".
     * @see Style#getStyleDesc(Class, XMLVersion)
     */
    public final String findUnusedName(final StyleDesc<?> desc, final String baseName) {
        final Integer lastI = this.styleNamesLast.get(baseName);
        final int offset = lastI == null ? 0 : lastI.intValue();
        int iterationCount = 0;
        int i = offset;
        String res = null;
        while (res == null) {
            final String name = baseName + i;
            final Element elem = this.getStyle(desc, name);
            iterationCount++;
            // don't increment i if we succeed since we don't know if the found name will indeed be
            // used
            if (elem == null) {
                res = name;
            } else {
                i++;
                // warn early before it takes too long
                if (iterationCount == ITERATIONS_WARNING_COUNT) {
                    Log.get().warning("After " + iterationCount + " iterations, no unused name found for " + baseName + " (" + desc + ")");
                }
            }
        }
        this.styleNamesLast.put(baseName, i);
        if (iterationCount >= ITERATIONS_WARNING_COUNT) {
            // MAYBE trigger an optimize pass that merges equal styles.
            Log.get().warning(iterationCount + " iterations were needed to find an unused name for " + baseName + " (" + desc + ")");
        }
        return res;
    }

    // Useful if many styles are removed (to avoid "ce12345")
    final void clearStyleNameCache() {
        this.styleNamesLast.clear();
    }

    public final void addAutoStyle(final Element styleElem) {
        this.getChild("automatic-styles", true).addContent(styleElem);
    }

    public String asString() {
        return JDOMUtils.output(this.content);
    }

    protected static interface ElementTransformer {
        Element transform(Element elem) throws JDOMException;
    }

    protected static final ElementTransformer NOP_ElementTransformer = new ElementTransformer() {
        public Element transform(Element elem) {
            return elem;
        }
    };

    protected void mergeAll(ODXMLDocument other, String path) throws JDOMException {
        this.mergeAll(other, path, null);
    }

    /**
     * Fusionne l'élément spécifié par topElem. Applique addTransf avant l'ajout. Attention seuls
     * les élément (et non les commentaires, text, etc.) de <code>other</code> sont ajoutés.
     * 
     * @param other le document à fusionner.
     * @param path le chemon de l'élément à fusionner, eg "./office:body".
     * @param addTransf la transformation à appliquer avant d'ajouter ou <code>null</code>.
     * @throws JDOMException
     */
    protected void mergeAll(ODXMLDocument other, String path, ElementTransformer addTransf) throws JDOMException {
        this.add(path, -1, other, path, addTransf);
    }

    /**
     * Add the part pointed by <code>rpath</code> of other in this document like child number
     * <code>lindex</code> of the part pointed by <code>lpath</code>.
     * 
     * @param lpath local xpath.
     * @param lindex local index beneath lpath, < 0 meaning the end.
     * @param other the document to add.
     * @param rpath the remote xpath, note: the content of that element will be added NOT the
     *        element itself.
     * @param addTransf the children of rpath will be transformed, can be <code>null</code>.
     * @throws JDOMException if an error occur.
     */
    protected void add(final String lpath, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        this.add(new IFactory<Element>() {
            public Element createChecked() {
                try {
                    return getDescendant(lpath, true);
                } catch (JDOMException e) {
                    throw new IllegalStateException("error", e);
                }
            }
        }, lindex, other, rpath, addTransf);
    }

    /**
     * Add the part pointed by <code>rpath</code> of other in this document like child number
     * <code>lindex</code> of <code>elem</code>.
     * 
     * @param elem local element, if <code>null</code> add to rpath see
     *        {@link #mergeAll(ODXMLDocument, String, org.jopendocument.dom.ODXMLDocument.ElementTransformer)}
     *        .
     * @param lindex local index beneath lpath, < 0 meaning the end, ignored if elem is
     *        <code>null</code>.
     * @param other the document to add.
     * @param rpath the remote xpath, note: the content of that element will be added NOT the
     *        element itself.
     * @param addTransf the children of rpath will be transformed, can be <code>null</code>.
     * @throws JDOMException if an error occur.
     */
    protected void add(final Element elem, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        if (elem == null) {
            this.mergeAll(other, rpath, addTransf);
        } else {
            if (!this.getDocument().getRootElement().isAncestor(elem))
                throw new IllegalArgumentException(elem + " not part of " + this);
            this.add(new IFactory<Element>() {
                public Element createChecked() {
                    return elem;
                }
            }, lindex, other, rpath, addTransf);
        }
    }

    protected final void add(IFactory<Element> elemF, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        final Element toAdd = other.getDescendant(rpath);
        // si on a qqchose à ajouter
        if (toAdd != null) {
            @SuppressWarnings("unchecked")
            final List<Content> cloned = toAdd.cloneContent();
            final List<Content> listToAdd;
            if (addTransf == null) {
                listToAdd = cloned;
            } else {
                listToAdd = new ArrayList<Content>(cloned.size());
                final Iterator<Content> iter = cloned.iterator();
                while (iter.hasNext()) {
                    final Content c = iter.next();
                    if (c instanceof Element) {
                        final Element transformedElem = addTransf.transform((Element) c);
                        if (transformedElem != null)
                            listToAdd.add(transformedElem);
                    } else {
                        // keep non element as when addTransf is null
                        // perhaps use a Transformer<Content> to allow to remove or modify
                        listToAdd.add(c);
                    }
                }
            }
            // on crée si besoin le "récepteur"
            final Element thisElem = elemF.createChecked();
            if (lindex < 0)
                thisElem.addContent(listToAdd);
            else
                thisElem.addContent(lindex, listToAdd);
        }
    }

    protected final void addIfNotPresent(ODXMLDocument doc, String path) throws JDOMException {
        this.addIfNotPresent(doc, path, -1);
    }

    /**
     * Adds an element from doc to this, if it's not already there.
     * 
     * @param doc the other document.
     * @param path an XPath denoting an element, and relative to the root element, eg
     *        ./office:settings.
     * @param index the index where to add the element, -1 means the end.
     * @throws JDOMException if a problem occurs with path.
     */
    protected final void addIfNotPresent(ODXMLDocument doc, String path, int index) throws JDOMException {
        final Element myElem = this.getDescendant(path);
        if (myElem == null) {
            final Element otherElem = doc.getDescendant(path);
            if (otherElem != null) {
                final Element myParent = this.getDescendant(XPathUtils.parentOf(path));
                if (index == -1)
                    myParent.addContent((Element) otherElem.clone());
                else
                    myParent.addContent(index, (Element) otherElem.clone());
            }
        }
    }

}