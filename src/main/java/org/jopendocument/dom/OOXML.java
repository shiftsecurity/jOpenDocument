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

import org.jopendocument.dom.text.Span;
import org.jopendocument.dom.text.TextNode;
import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import org.jdom.Content;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Parent;
import org.jdom.Text;
import org.jdom.xpath.XPath;
import org.xml.sax.SAXException;

/**
 * Various bits of OpenDocument XML.
 * 
 * @author Sylvain CUAZ
 * @see #get(XMLFormatVersion)
 */
public abstract class OOXML implements Comparable<OOXML> {

    /**
     * If this system property is set to <code>true</code> then {@link #get(XMLFormatVersion)} will
     * never return <code>null</code>, allowing to support unknown versions.
     */
    public static final String LAST_FOR_UNKNOWN_PROP = OOXML.class.getPackage().getName() + ".lastOOXMLForUnknownVersion";
    private static final XML_OO instanceOO = new XML_OO();
    @GuardedBy("OOXML")
    private static final SortedMap<String, XML_OD> instancesODByDate = new TreeMap<String, XML_OD>();
    @GuardedBy("OOXML")
    private static final Map<String, XML_OD> instancesODByVersion = new HashMap<String, XML_OD>();
    private static final List<OOXML> values;
    @GuardedBy("OOXML")
    private static OOXML defaultInstance;
    private static final Pattern WHITE_SPACE_TO_ENCODE = Pattern.compile("\n|" + TextNode.VERTICAL_TAB_CHAR + "|\t| {2,}");

    static {
        register(new XML_OD_1_0());
        register(new XML_OD_1_1());
        register(new XML_OD_1_2());

        final List<OOXML> tmp = new ArrayList<OOXML>(instancesODByDate.size() + 1);
        tmp.add(instanceOO);
        tmp.addAll(instancesODByDate.values());
        values = Collections.unmodifiableList(tmp);

        setDefault(getLast());
    }

    private static synchronized void register(XML_OD xml) {
        assert xml.getVersion() == XMLVersion.OD;
        instancesODByDate.put(xml.getDateString(), xml);
        instancesODByVersion.put(xml.getFormatVersion().getOfficeVersion(), xml);
    }

    /**
     * Returns the instance that match the requested version.
     * 
     * @param version the version.
     * @return the corresponding instance, <code>null</code> for unsupported versions.
     * @see #LAST_FOR_UNKNOWN_PROP
     */
    public static OOXML get(XMLFormatVersion version) {
        return get(version, Boolean.getBoolean(LAST_FOR_UNKNOWN_PROP));
    }

    public static synchronized OOXML get(XMLFormatVersion version, final boolean lastForUnknown) {
        if (version.getXMLVersion() == XMLVersion.OOo) {
            return instanceOO;
        } else {
            final XML_OD res = instancesODByVersion.get(version.getOfficeVersion());
            if (res == null && lastForUnknown)
                return getLast(version.getXMLVersion());
            else
                return res;
        }
    }

    public static OOXML get(Element root) {
        return XMLFormatVersion.get(root).getXML();
    }

    /**
     * Return all known instances in the order they were published.
     * 
     * @return all known instances ordered.
     * @see #compareTo(OOXML)
     */
    static public final List<OOXML> values() {
        return values;
    }

    static public final OOXML getLast() {
        return CollectionUtils.getLast(values);
    }

    static public synchronized final OOXML getLast(XMLVersion version) {
        if (version == XMLVersion.OOo)
            return instanceOO;
        else
            return instancesODByDate.get(instancesODByDate.lastKey());
    }

    public static synchronized void setDefault(OOXML ns) {
        defaultInstance = ns;
    }

    public static synchronized OOXML getDefault() {
        return defaultInstance;
    }

    // from OpenDocument-v1.2-schema.rng : a coordinate is a length
    static private final BigDecimal parseCoordinate(final Element elem, final String attrName, final Namespace ns, LengthUnit unit) {
        return parseLength(elem, attrName, ns, unit);
    }

    static private final BigDecimal parseLength(final Element elem, final String attrName, final Namespace ns, LengthUnit unit) {
        final String attr = elem.getAttributeValue(attrName, ns);
        if (attr == null)
            return null;
        return LengthUnit.parseLength(attr, unit);
    }

    // *** instances

    private final XMLFormatVersion version;
    private final String dateString;

    private OOXML(XMLFormatVersion version, final String dateString) {
        this.version = version;
        this.dateString = dateString;
    }

    /**
     * The date the specification was published.
     * 
     * @return the date in "yyyyMMdd" format.
     */
    public final String getDateString() {
        return this.dateString;
    }

    /**
     * Compare the date the specification was published.
     * 
     * @param o the object to be compared.
     * @see #getDateString()
     */
    @Override
    public int compareTo(OOXML o) {
        return this.dateString.compareTo(o.dateString);
    }

    public final XMLVersion getVersion() {
        return this.getFormatVersion().getXMLVersion();
    }

    public final XMLFormatVersion getFormatVersion() {
        return this.version;
    }

    public abstract boolean canValidate();

    public Validator getValidator(final Document doc) {
        // true since by default LibreOffice generates foreign content
        return this.getValidator(doc, true);
    }

    /**
     * Verify that the passed document is a valid OpenOffice.org 1 or ODF document.
     * 
     * @param doc the XML to test.
     * @param ignoreForeign <code>true</code> to ignore unknown mark up, e.g. "extended document" in
     *        OpenDocument v1.2 §2.2.2 and in OpenDocument v1.1 §1.5.
     * @return a validator on <code>doc</code>.
     */
    public abstract Validator getValidator(final Document doc, final boolean ignoreForeign);

    public abstract Document createManifestDoc();

    /**
     * Return the names of font face declarations.
     * 
     * @return at index 0 the name of the container element, at 1 the qualified name of its
     *         children.
     */
    public abstract String[] getFontDecls();

    /**
     * Return the top-level script element in the content.
     * 
     * @return the top-level script element name.
     */
    public abstract String getOfficeScripts();

    /**
     * The name of the elements where scripts are defined.
     * 
     * @return the name of the children of {@link #getOfficeScripts()} defining scripts.
     */
    public abstract String getOfficeScript();

    /**
     * The name of the element where event listeners are defined.
     * 
     * @return the name of the child of {@link #getOfficeScripts()} defining event listeners.
     */
    public abstract String getOfficeEventListeners();

    public abstract String getEventListener();

    public final Element getLineBreak() {
        return new Element("line-break", getVersion().getTEXT());
    }

    public abstract Element getTab();

    public abstract String getFrameQName();

    public abstract Element createFormattingProperties(final String family);

    protected final Element encodeRT_L(final Element root, final String s, final Map<String, String> styles) {
        final List<String> quotedCodes = new ArrayList<String>(styles.size());
        for (final String code : styles.keySet()) {
            if (code.length() == 0 || code.indexOf('/') >= 0 || code.indexOf('[') >= 0 || code.indexOf(']') >= 0)
                throw new IllegalArgumentException("Invalid code : " + code);
            quotedCodes.add(Pattern.quote(code));
        }
        final Pattern p = Pattern.compile("\\[/?(" + CollectionUtils.join(quotedCodes, "|") + ")\\]");
        final Matcher m = p.matcher(s);

        final Deque<Element> elements = new LinkedList<Element>();
        final Deque<String> codes = new LinkedList<String>();
        elements.addFirst(root);
        codes.addFirst(null);
        final Namespace testNS = getVersion().getTEXT();
        int last = 0;
        while (m.find()) {
            assert elements.size() == codes.size();
            final Element current = elements.getFirst();
            // null if root
            final String currentCode = codes.getFirst();
            current.addContent(new Text(s.substring(last, m.start())));
            assert m.group().charAt(0) == '[';
            final boolean closing = m.group().charAt(1) == '/';
            final String code = m.group(1);
            if (closing) {
                if (!code.equals(currentCode))
                    throw new IllegalArgumentException("Mismatch current " + currentCode + " but closing " + code + " at " + m.start() + "\n" + s);
                elements.removeFirst();
                codes.removeFirst();
            } else {
                final Element newElem = new Element("span", testNS).setAttribute("style-name", styles.get(code), testNS);
                current.addContent(newElem);
                elements.addFirst(newElem);
                codes.addFirst(code);
            }
            last = m.end();
        }
        if (elements.size() != 1)
            throw new IllegalArgumentException("Some tags weren't closed : " + elements + "\n" + s);
        assert elements.peekFirst() == root;
        root.addContent(new Text(s.substring(last)));
        return root;
    }

    /**
     * Convert rich text (with [] tags) into XML.
     * 
     * @param content the string to convert, eg "texte [b]gras[/b]".
     * @param styles the mapping from tagname (eg "b") to the name of the character style (eg
     *        "Gras").
     * @return the corresponding element.
     */
    public final Element encodeRT(String content, Map<String, String> styles) {
        return encodeRT_L(Span.createEmpty(getFormatVersion()), content, styles);
    }

    // create the necessary <text:s c="n"/>
    public final Element createSpaces(int count) {
        return new Element("s", getVersion().getTEXT()).setAttribute("c", count + "", getVersion().getTEXT());
    }

    /**
     * Encode a String to OO XML. Handles substition of whitespaces to their OO equivalent.
     * 
     * @param s a plain ole String, eg "term\tdefinition".
     * @return an Element suitable to be inserted in an OO XML document, eg
     * 
     *         <pre>
     *     &lt;text:span&gt;term&lt;text:tab-stop/&gt;definition&lt;/text:span&gt;
     * </pre>
     * 
     *         .
     */
    public final Element encodeWS(final String s) {
        return Span.createEmpty(getFormatVersion()).setContent(encodeWSasList(s));
    }

    public final List<Content> encodeWSasList(final String s) {
        final List<Content> res = new ArrayList<Content>();
        final Matcher m = WHITE_SPACE_TO_ENCODE.matcher(s);
        int last = 0;
        while (m.find()) {
            res.add(new Text(s.substring(last, m.start())));
            switch (m.group().charAt(0)) {
            case '\n':
                // Vertical Tab, see TextNode#VERTICAL_TAB_CHAR
            case '\u000B':
                res.add(getLineBreak());
                break;
            case '\t':
                res.add(getTab());
                break;
            case ' ':
                res.add(createSpaces(m.group().length()));
                break;

            default:
                throw new IllegalStateException("unknown item: " + m.group());
            }
            last = m.end();
        }
        res.add(new Text(s.substring(last)));
        return res;
    }

    @SuppressWarnings("unchecked")
    public final void encodeWS(final Text t) {
        final Parent parent = t.getParent();
        final int ind = parent.indexOf(t);
        t.detach();
        parent.getContent().addAll(ind, encodeWSasList(t.getText()));
    }

    @SuppressWarnings("unchecked")
    public final Element encodeWS(final Element elem) {
        final XPath path;
        try {
            path = OOUtils.getXPath(".//text()", getVersion());
        } catch (JDOMException e) {
            // static path, hence always valid
            throw new IllegalStateException("cannot create XPath", e);
        }
        try {
            final Iterator iter = new ArrayList(path.selectNodes(elem)).iterator();
            while (iter.hasNext()) {
                final Text t = (Text) iter.next();
                encodeWS(t);
            }
        } catch (JDOMException e) {
            throw new IllegalArgumentException("cannot find text nodes of " + elem, e);
        }
        return elem;
    }

    /**
     * Return the coordinates of the top-left and bottom-right of the passed shape.
     * 
     * @param elem an XML element.
     * @param unit the unit of the returned numbers.
     * @return an array of 4 numbers, <code>null</code> if <code>elem</code> is not a shape, numbers
     *         themselves are never <code>null</code>.
     */
    public final BigDecimal[] getCoordinates(Element elem, LengthUnit unit) {
        return this.getCoordinates(elem, unit, true, true);
    }

    /**
     * Return the coordinates of the top-left and bottom-right of the passed shape.
     * 
     * @param elem an XML element.
     * @param unit the unit of the returned numbers.
     * @param horizontal <code>true</code> if the x coordinates should be computed,
     *        <code>false</code> meaning items 0 and 2 of the result are <code>null</code>.
     * @param vertical <code>true</code> if the y coordinates should be computed, <code>false</code>
     *        meaning items 1 and 3 of the result are <code>null</code>.
     * @return an array of 4 numbers, <code>null</code> if <code>elem</code> is not a shape, numbers
     *         themselves are only <code>null</code> if requested with <code>horizontal</code> or
     *         <code>vertical</code>.
     */
    public final BigDecimal[] getCoordinates(Element elem, LengthUnit unit, final boolean horizontal, final boolean vertical) {
        return getCoordinates(elem, getVersion().getNS("svg"), unit, horizontal, vertical);
    }

    static private final BigDecimal[] getCoordinates(Element elem, final Namespace svgNS, LengthUnit unit, final boolean horizontal, final boolean vertical) {
        if (elem.getName().equals("g") && elem.getNamespacePrefix().equals("draw")) {
            // put below if to allow null to be returned by getLocalCoordinates() if elem isn't a
            // shape
            if (!horizontal && !vertical)
                return new BigDecimal[] { null, null, null, null };

            // an OpenDocument group (of shapes) doesn't have any coordinates nor any width and
            // height so iterate through its components to find its coordinates
            BigDecimal minX = null, minY = null;
            BigDecimal maxX = null, maxY = null;
            for (final Object c : elem.getChildren()) {
                final Element child = (Element) c;
                final BigDecimal[] childCoord = getCoordinates(child, svgNS, unit, horizontal, vertical);
                // e.g. <office:event-listeners>, <svg:desc>, <svg:title>
                if (childCoord != null) {
                    {
                        final BigDecimal x = childCoord[0];
                        final BigDecimal x2 = childCoord[2];
                        if (x != null) {
                            assert x2 != null;
                            if (minX == null || x.compareTo(minX) < 0)
                                minX = x;
                            if (maxX == null || x2.compareTo(maxX) > 0)
                                maxX = x2;
                        }
                    }
                    {
                        final BigDecimal y = childCoord[1];
                        final BigDecimal y2 = childCoord[3];
                        if (y != null) {
                            assert y2 != null;
                            if (minY == null || y.compareTo(minY) < 0)
                                minY = y;
                            if (maxY == null || y2.compareTo(maxY) > 0)
                                maxY = y2;
                        }
                    }
                }
            }
            // works because we check above if both horizontal and vertical are false
            if (minX == null && minY == null)
                throw new IllegalArgumentException("Empty group : " + JDOMUtils.output(elem));
            return new BigDecimal[] { minX, minY, maxX, maxY };
        } else {
            return getLocalCoordinates(elem, svgNS, unit, horizontal, vertical);
        }
    }

    // return null if elem isn't a shape (no x/y or no width/height)
    // BigDecimal null if and only if horizontal/vertical is false
    static private final BigDecimal[] getLocalCoordinates(Element elem, final Namespace svgNS, LengthUnit unit, final boolean horizontal, final boolean vertical) {
        final BigDecimal x = parseCoordinate(elem, "x", svgNS, unit);
        final BigDecimal x1 = parseCoordinate(elem, "x1", svgNS, unit);
        if (x == null && x1 == null)
            return null;

        final BigDecimal y = parseCoordinate(elem, "y", svgNS, unit);
        final BigDecimal y1 = parseCoordinate(elem, "y1", svgNS, unit);
        if (y == null && y1 == null)
            throw new IllegalArgumentException("Have x but missing y in " + JDOMUtils.output(elem));

        final BigDecimal startX;
        final BigDecimal endX;
        if (horizontal) {
            if (x == null) {
                startX = x1;
                endX = parseCoordinate(elem, "x2", svgNS, unit);
            } else {
                startX = x;
                final BigDecimal width = parseLength(elem, "width", svgNS, unit);
                endX = width == null ? null : startX.add(width);
            }
            // return null if there's no second coordinate (it's a point)
            if (endX == null)
                return null;
        } else {
            startX = null;
            endX = null;
        }

        final BigDecimal startY;
        final BigDecimal endY;
        if (vertical) {
            if (y == null) {
                startY = y1;
                endY = parseCoordinate(elem, "y2", svgNS, unit);
            } else {
                startY = y;
                final BigDecimal height = parseLength(elem, "height", svgNS, unit);
                endY = height == null ? null : startY.add(height);
            }
            // return null if there's no second coordinate (it's a point)
            if (endY == null)
                return null;
        } else {
            startY = null;
            endY = null;
        }

        return new BigDecimal[] { startX, startY, endX, endY };
    }

    @Immutable
    private static final class XML_OO extends OOXML {
        private static final Set<Namespace> NS = XMLVersion.OOo.getNamespaceSet();

        private static final IPredicate<Object> UNKNOWN_PRED = new IPredicate<Object>() {
            @Override
            public boolean evaluateChecked(Object input) {
                final Namespace ns = JDOMUtils.getNamespace(input);
                return ns != null && !NS.contains(ns);
            }
        };
        private static final IPredicate<Object> MANIFEST_UNKNOWN_PRED = new IPredicate<Object>() {
            @Override
            public boolean evaluateChecked(Object input) {
                final Namespace ns = JDOMUtils.getNamespace(input);
                return ns != null && !ns.equals(XMLVersion.OOo.getManifest());
            }
        };

        private static final DocType createManifestDocType() {
            return new DocType("manifest:manifest", "-//OpenOffice.org//DTD Manifest 1.0//EN", "Manifest.dtd");
        }

        public XML_OO() {
            super(XMLFormatVersion.getOOo(), "20020501");
        }

        @Override
        public boolean canValidate() {
            return true;
        }

        @Override
        public Validator getValidator(final Document doc, final boolean ignoreForeign) {
            // DTDs are stubborn, xmlns have to be exactly where they want
            // in this case the root element
            final boolean isManifest = doc.getRootElement().getQualifiedName().equals("manifest:manifest");
            if (!isManifest) {
                for (final Namespace n : getVersion().getALL())
                    doc.getRootElement().addNamespaceDeclaration(n);
            }
            return new Validator.DTDValidator(doc, !ignoreForeign ? null : (isManifest ? MANIFEST_UNKNOWN_PRED : UNKNOWN_PRED), OOUtils.getBuilderLoadDTD());
        }

        @Override
        public Document createManifestDoc() {
            return new Document(new Element("manifest", this.getVersion().getManifest()), createManifestDocType());
        }

        @Override
        public String getOfficeScripts() {
            return "script";
        }

        @Override
        public String getOfficeScript() {
            return "script-data";
        }

        @Override
        public String getOfficeEventListeners() {
            return "events";
        }

        @Override
        public String getEventListener() {
            return "event";
        }

        @Override
        public String[] getFontDecls() {
            return new String[] { "font-decls", "style:font-decl" };
        }

        @Override
        public final Element getTab() {
            return new Element("tab-stop", getVersion().getTEXT());
        }

        @Override
        public String getFrameQName() {
            return "draw:text-box";
        }

        @Override
        public Element createFormattingProperties(String family) {
            return new Element("properties", this.getVersion().getSTYLE());
        }
    }

    @ThreadSafe
    private static class XML_OD extends OOXML {

        private static final IPredicate<Object> UNKNOWN_PRED = new IPredicate<Object>() {
            @Override
            public boolean evaluateChecked(Object input) {
                final Namespace ns = JDOMUtils.getNamespace(input);
                // leave non-elements alone
                if (ns == null)
                    return false;
                // XMLVersion doesn't include all namespaces of the standard, so only exclude known
                // extended namespaces.
                return ns.equals(Namespace.NO_NAMESPACE) || ns.getURI().startsWith("urn:org:documentfoundation:names:experimental") || ns.getURI().startsWith("urn:openoffice:names:experimental");
            }
        };

        private final String schemaFile, manifestSchemaFile;
        @GuardedBy("this")
        private Schema schema, manifestSchema;

        public XML_OD(final String dateString, final String versionString, final String schemaFile, final String manifestSchemaFile) {
            super(XMLFormatVersion.get(XMLVersion.OD, versionString), dateString);
            this.schemaFile = schemaFile;
            this.manifestSchemaFile = manifestSchemaFile;
            this.schema = this.manifestSchema = null;
        }

        @Override
        public boolean canValidate() {
            return this.schemaFile != null && this.manifestSchemaFile != null;
        }

        private Schema createSchema(final String name) throws SAXException {
            return SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI).newSchema(getClass().getResource("oofficeDTDs/" + name));
        }

        private synchronized Schema getSchema() throws SAXException {
            if (this.schema == null && this.schemaFile != null) {
                this.schema = this.createSchema(this.schemaFile);
            }
            return this.schema;
        }

        private synchronized Schema getManifestSchema() throws SAXException {
            if (this.manifestSchema == null && this.manifestSchemaFile != null) {
                this.manifestSchema = this.createSchema(this.manifestSchemaFile);
            }
            return this.manifestSchema;
        }

        @Override
        public Validator getValidator(final Document doc, final boolean ignoreForeign) {
            final Schema schema;
            try {
                if (doc.getRootElement().getQualifiedName().equals("manifest:manifest"))
                    schema = this.getManifestSchema();
                else
                    schema = this.getSchema();
            } catch (SAXException e) {
                throw new IllegalStateException("relaxNG schemas pb", e);
            }
            return schema == null ? null : new Validator.JAXPValidator(doc, ignoreForeign ? UNKNOWN_PRED : null, schema);
        }

        @Override
        public Document createManifestDoc() {
            return new Document(new Element("manifest", this.getVersion().getManifest()), null);
        }

        @Override
        public String getOfficeScripts() {
            return "scripts";
        }

        @Override
        public String getOfficeScript() {
            return "script";
        }

        @Override
        public String getOfficeEventListeners() {
            return "event-listeners";
        }

        @Override
        public String getEventListener() {
            return "event-listener";
        }

        @Override
        public final String[] getFontDecls() {
            return new String[] { "font-face-decls", "style:font-face" };
        }

        @Override
        public final Element getTab() {
            return new Element("tab", getVersion().getTEXT());
        }

        @Override
        public String getFrameQName() {
            return "draw:frame";
        }

        @Override
        public Element createFormattingProperties(String family) {
            return new Element(family + "-properties", this.getVersion().getSTYLE());
        }
    }

    private static final class XML_OD_1_0 extends XML_OD {
        public XML_OD_1_0() {
            super("20061130", "1.0", null, null);
        }
    }

    private static final class XML_OD_1_1 extends XML_OD {
        public XML_OD_1_1() {
            super("20070201", "1.1", "OpenDocument-strict-schema-v1.1.rng", "OpenDocument-manifest-schema-v1.1.rng");
        }
    }

    private static final class XML_OD_1_2 extends XML_OD {
        public XML_OD_1_2() {
            super("20110317", "1.2", "OpenDocument-v1.2-schema.rng", "OpenDocument-v1.2-manifest-schema.rng");
        }

        @Override
        public Document createManifestDoc() {
            final Document res = super.createManifestDoc();
            res.getRootElement().setAttribute("version", getFormatVersion().getOfficeVersion(), res.getRootElement().getNamespace());
            return res;
        }
    }
}