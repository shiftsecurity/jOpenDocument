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

package org.jopendocument.dom;

import static org.jopendocument.dom.ODPackage.RootElement.CONTENT;
import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.dom.style.data.DataStyle;
import org.jopendocument.util.Base64;
import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.FileUtils;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.SimpleXMLPath;
import org.jopendocument.util.Step;
import org.jopendocument.util.Step.Axis;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.Transformer;
import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

/**
 * An XML document containing all of an office document, see section 2.1 of OpenDocument 1.1.
 * 
 * @author Sylvain CUAZ 24 nov. 2004
 */
public class ODSingleXMLDocument extends ODXMLDocument implements Cloneable {

    static private enum ContentPart {
        PROLOGUE, MAIN, EPILOGUE
    }

    private static final String BASIC_LANG_NAME = "ooo:Basic";
    private static final SimpleXMLPath<Attribute> ALL_HREF_ATTRIBUTES = SimpleXMLPath.allAttributes("href", "xlink");
    private static final SimpleXMLPath<Element> ALL_BINARY_DATA_ELEMENTS = SimpleXMLPath.allElements("binary-data", "office");
    // see 10.4.5 <office:binary-data> of OpenDocument-v1.2-os
    private static final Set<String> BINARY_DATA_PARENTS = CollectionUtils.createSet("draw:image", "draw:object-ole", "style:background-image", "text:list-level-style-image");

    final static Set<String> DONT_PREFIX;
    static private final Map<XMLVersion, List<Set<Element>>> ELEMS_ORDER;
    static private final Map<XMLVersion, Map<Tuple2<Namespace, String>, ContentPart>> ELEMS_PARTS;
    static {
        DONT_PREFIX = new HashSet<String>();
        // don't touch to user fields and variables
        // we want them to be the same across the document
        DONT_PREFIX.add("user-field-decl");
        DONT_PREFIX.add("user-field-get");
        DONT_PREFIX.add("variable-get");
        DONT_PREFIX.add("variable-decl");
        DONT_PREFIX.add("variable-set");

        final XMLVersion[] versions = XMLVersion.values();
        ELEMS_ORDER = new HashMap<XMLVersion, List<Set<Element>>>(versions.length);
        ELEMS_PARTS = new HashMap<XMLVersion, Map<Tuple2<Namespace, String>, ContentPart>>(versions.length);
        for (final XMLVersion v : versions) {
            // some elements can only appear in some types but since we have the null wild card,
            // they'd match that. So always include all elements.
            final List<Set<Element>> children = createChildren(v, null);

            ELEMS_ORDER.put(v, children);

            final Map<Tuple2<Namespace, String>, ContentPart> m = new HashMap<Tuple2<Namespace, String>, ODSingleXMLDocument.ContentPart>(ContentPart.values().length);
            boolean beforeNull = true;
            for (final Set<Element> s : children) {
                if (s == null) {
                    m.put(null, ContentPart.MAIN);
                    assert beforeNull : "more than one null";
                    beforeNull = false;
                } else {
                    for (final Element elem : s)
                        m.put(Tuple2.create(elem.getNamespace(), elem.getName()), beforeNull ? ContentPart.PROLOGUE : ContentPart.EPILOGUE);
                }
            }
            ELEMS_PARTS.put(v, m);
        }
    }

    private static final List<Set<Element>> createChildren(XMLVersion v, ContentType t) {
        final Namespace textNS = v.getTEXT();
        final Namespace tableNS = v.getTABLE();
        final List<Set<Element>> res = new ArrayList<Set<Element>>(24);

        if (t == null || t == ContentType.TEXT)
            res.add(Collections.singleton(new Element("forms", v.getOFFICE())));
        // first only for text, second only for spreadsheet
        final Element textTrackedChanges = new Element("tracked-changes", textNS);
        final Element tableTrackedChanges = new Element("tracked-changes", tableNS);
        if (t == null)
            res.add(CollectionUtils.createSet(textTrackedChanges, tableTrackedChanges));
        else if (t == ContentType.TEXT)
            res.add(Collections.singleton(textTrackedChanges));
        else if (t == ContentType.SPREADSHEET)
            res.add(Collections.singleton(tableTrackedChanges));

        // text-decls
        res.add(Collections.singleton(new Element("variable-decls", textNS)));
        res.add(Collections.singleton(new Element("sequence-decls", textNS)));
        res.add(Collections.singleton(new Element("user-field-decls", textNS)));
        res.add(Collections.singleton(new Element("dde-connection-decls", textNS)));
        res.add(Collections.singleton(new Element("alphabetical-index-auto-mark-file", textNS)));

        // table-decls
        res.add(Collections.singleton(new Element("calculation-settings", tableNS)));
        res.add(Collections.singleton(new Element("content-validations", tableNS)));
        res.add(Collections.singleton(new Element("label-ranges", tableNS)));

        if (v == XMLVersion.OD && (t == null || t == ContentType.PRESENTATION)) {
            // perhaps add presentation-decls (needs new namespace in XMLVersion)
        }

        // main content
        res.add(null);

        if (v == XMLVersion.OD && (t == null || t == ContentType.PRESENTATION)) {
            // perhaps add presentation:settings
        }

        // table-functions
        res.add(Collections.singleton(new Element("named-expressions", tableNS)));
        res.add(Collections.singleton(new Element("database-ranges", tableNS)));
        res.add(Collections.singleton(new Element("data-pilot-tables", tableNS)));
        res.add(Collections.singleton(new Element("consolidation", tableNS)));
        res.add(Collections.singleton(new Element("dde-links", tableNS)));

        if (v == XMLVersion.OOo && (t == null || t == ContentType.PRESENTATION)) {
            // perhaps add presentation:settings
        }

        return res;
    }

    // return null if not an element
    // return the part the element is in (assume MAIN for unknown elements)
    static private ContentPart getPart(final Map<Tuple2<Namespace, String>, ContentPart> parts, final Content bodyContent) {
        if (!(bodyContent instanceof Element))
            return null;
        final Element elem = (Element) bodyContent;
        ContentPart res = parts.get(Tuple2.create(elem.getNamespace(), elem.getName()));
        if (res == null)
            res = parts.get(null);
        assert res != null;
        return res;
    }

    static private int[] getLastNulls(final Map<Tuple2<Namespace, String>, ContentPart> parts, final Element body) {
        @SuppressWarnings("unchecked")
        final List<Content> content = body.getContent();
        return getLastNulls(parts, content, content.size());
    }

    // return the start of the EPILOGUE, at 0 with non-elements (i.e. having a null ContentPart), at
    // 1 the first EPILOGUE element
    static private int[] getLastNulls(final Map<Tuple2<Namespace, String>, ContentPart> parts, final List<Content> content, final int contentSize) {
        // start from the end until we leave the epilogue (quicker than traversing the main part as
        // prologue and epilogue sizes are bounded and small)
        ContentPart contentPart = null;
        final ListIterator<Content> thisChildrenIter = content.listIterator(contentSize);
        int nullsStartIndex = -1;
        while ((contentPart == null || contentPart == ContentPart.EPILOGUE) && thisChildrenIter.hasPrevious()) {
            contentPart = getPart(parts, thisChildrenIter.previous());
            if (contentPart != null) {
                nullsStartIndex = -1;
            } else if (nullsStartIndex < 0) {
                nullsStartIndex = thisChildrenIter.nextIndex();
            }
        }
        final int lastNullsStart = contentPart == null || contentPart == ContentPart.EPILOGUE ? thisChildrenIter.nextIndex() : thisChildrenIter.nextIndex() + 1;
        final int lastNullsEnd = nullsStartIndex < 0 ? lastNullsStart : nullsStartIndex + 1;
        return new int[] { lastNullsStart, lastNullsEnd };
    }

    /**
     * Slice the body into parts. Since some content have no part (e.g. comment), they can be added
     * to the previous or next range. If <code>overlapping</code> is <code>true</code> they will be
     * added to both, else only to the next range.
     * 
     * @param parts parts definition.
     * @param body the element to slice.
     * @param overlapping <code>true</code> if ranges can overlap.
     * @return the start (inclusive, {@link Point#x}) and end (exclusive, {@link Point#y}) for each
     *         {@link ContentPart}.
     */
    static private Point[] getBounds(final Map<Tuple2<Namespace, String>, ContentPart> parts, final Element body, final boolean overlapping) {
        @SuppressWarnings("unchecked")
        final List<Content> content = body.getContent();
        final int contentSize = content.size();
        if (contentSize == 0)
            return new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0) };

        // start from the beginning until we leave the prologue
        ContentPart contentPart = null;
        ListIterator<Content> thisChildrenIter = content.listIterator(0);
        final int prologueStart = 0;
        int nullsStartIndex = -1;
        while ((contentPart == null || contentPart == ContentPart.PROLOGUE) && thisChildrenIter.hasNext()) {
            contentPart = getPart(parts, thisChildrenIter.next());
            if (contentPart != null) {
                nullsStartIndex = -1;
            } else if (nullsStartIndex < 0) {
                nullsStartIndex = thisChildrenIter.previousIndex();
            }
        }
        final int nullsEnd = contentPart == null || contentPart == ContentPart.PROLOGUE ? thisChildrenIter.nextIndex() : thisChildrenIter.previousIndex();
        final int nullsStart = nullsStartIndex < 0 ? nullsEnd : nullsStartIndex;
        assert nullsStart >= 0 && nullsStart <= nullsEnd;
        final int mainStart = nullsStart;
        final int prologueStop = overlapping ? nullsEnd : nullsStart;

        final int epilogueEnd = contentSize;
        final int[] lastNulls = getLastNulls(parts, content, contentSize);
        final int lastNullsStart = lastNulls[0];
        final int lastNullsEnd = lastNulls[1];
        assert lastNullsStart >= mainStart && lastNullsStart <= lastNullsEnd;
        final int epilogueStart = lastNullsStart;
        final int mainEnd = overlapping ? lastNullsEnd : lastNullsStart;

        final Point[] res = new Point[] { new Point(prologueStart, prologueStop), new Point(mainStart, mainEnd), new Point(epilogueStart, epilogueEnd) };
        assert res.length == ContentPart.values().length;
        return res;
    }

    static private int getValidIndex(final Map<Tuple2<Namespace, String>, ContentPart> parts, final Element body, final int index) {
        // overlapping ranges to have longest main part possible and thus avoid changing index
        final Point[] bounds = getBounds(parts, body, true);
        final Point mainBounds = bounds[ContentPart.MAIN.ordinal()];

        final int mainEnd = mainBounds.y;
        if (index < 0 || index > mainEnd)
            return mainEnd;

        final int mainStart = mainBounds.x;
        if (index < mainStart)
            return mainStart;

        return index;
    }

    // Voir le TODO du ctor
    // public static OOSingleXMLDocument createEmpty() {
    // }

    /**
     * Create a document from a collection of subdocuments.
     * 
     * @param content the content.
     * @param style the styles, can be <code>null</code>.
     * @return the merged document.
     */
    public static ODSingleXMLDocument createFromDocument(Document content, Document style) {
        return ODPackage.createFromDocuments(content, style).toSingle();
    }

    static ODSingleXMLDocument create(ODPackage files) {
        final Document content = files.getContent().getDocument();
        final Document style = files.getDocument(RootElement.STYLES.getZipEntry());
        // signal that the xml is a complete document (was document-content)
        final Document singleContent = RootElement.createSingle(content);
        copyNS(content, singleContent);
        files.getContentType().setType(singleContent);
        final Element root = singleContent.getRootElement();
        root.addContent(content.getRootElement().removeContent());
        // see section 2.1.1 first meta, then settings, then the rest
        createScriptsElement(root, files);
        prependToRoot(files.getDocument(RootElement.SETTINGS.getZipEntry()), root);
        prependToRoot(files.getDocument(RootElement.META.getZipEntry()), root);
        final ODSingleXMLDocument single = new ODSingleXMLDocument(singleContent, files);
        if (single.getChild("body") == null)
            throw new IllegalArgumentException("no body in " + single);
        if (style != null) {
            // section 2.1 : Styles used in the document content and automatic styles used in the
            // styles themselves.
            // more precisely in section 2.1.1 : office:document-styles contains style, master
            // style, auto style, font decls ; the last two being also in content.xml but are *not*
            // related : eg P1 of styles.xml is *not* the P1 of content.xml
            try {
                single.mergeAllStyles(new ODXMLDocument(style), true);
            } catch (JDOMException e) {
                throw new IllegalArgumentException("style is not valid", e);
            }
        }
        return single;
    }

    private static void createScriptsElement(final Element root, final ODPackage pkg) {
        final Map<String, Library> basicLibraries = pkg.readBasicLibraries();
        if (basicLibraries.size() > 0) {
            final XMLFormatVersion formatVersion = pkg.getFormatVersion();
            final XMLVersion version = formatVersion.getXMLVersion();
            final Namespace officeNS = version.getOFFICE();

            // scripts must be before the body and automatic styles
            final Element scriptsElem = JDOMUtils.getOrCreateChild(root, formatVersion.getXML().getOfficeScripts(), officeNS, 0);
            final Element scriptElem = new Element(formatVersion.getXML().getOfficeScript(), officeNS);
            scriptElem.setAttribute("language", BASIC_LANG_NAME, version.getNS("script"));
            // script must be before events
            scriptsElem.addContent(0, scriptElem);

            final Element libsElem = new Element("libraries", version.getLibrariesNS());
            for (final Library lib : basicLibraries.values()) {
                libsElem.addContent(lib.toFlatXML(formatVersion));
            }
            scriptElem.addContent(libsElem);
        }
    }

    private static void prependToRoot(Document settings, final Element root) {
        if (settings != null) {
            copyNS(settings, root.getDocument());
            final Element officeSettings = (Element) settings.getRootElement().getChildren().get(0);
            root.addContent(0, (Element) officeSettings.clone());
        }
    }

    // some namespaces are needed even if not directly used, see § 18.3.19 namespacedToken
    // of v1.2-part1-cd04 (e.g. 19.31 config:name or 19.260 form:control-implementation)
    @SuppressWarnings("unchecked")
    private static void copyNS(final Document src, final Document dest) {
        JDOMUtils.addNamespaces(dest.getRootElement(), src.getRootElement().getAdditionalNamespaces());
    }

    /**
     * Create a document from a package.
     * 
     * @param f an OpenDocument package file.
     * @return the merged file.
     * @throws JDOMException if the file is not a valid OpenDocument file.
     * @throws IOException if the file can't be read.
     */
    public static ODSingleXMLDocument createFromPackage(File f) throws JDOMException, IOException {
        // this loads all linked files
        return new ODPackage(f).toSingle();
    }

    public static ODSingleXMLDocument createFromPackage(InputStream in) throws IOException {
        // this loads all linked files
        return new ODPackage(in).toSingle();
    }

    /**
     * Create a document from a flat XML.
     * 
     * @param f an OpenDocument XML file.
     * @return the created file.
     * @throws JDOMException if the file is not a valid OpenDocument file.
     * @throws IOException if the file can't be read.
     */
    public static ODSingleXMLDocument createFromFile(File f) throws JDOMException, IOException {
        final ODSingleXMLDocument res = new ODSingleXMLDocument(OOUtils.getBuilder().build(f));
        res.getPackage().setFile(f);
        return res;
    }

    public static ODSingleXMLDocument createFromStream(InputStream ins) throws JDOMException, IOException {
        return new ODSingleXMLDocument(OOUtils.getBuilder().build(ins));
    }

    /**
     * fix bug when a SingleXMLDoc is used to create a document (for example with P2 and 1_P2), and
     * then create another instance s2 with the previous document and add a second file (also with
     * P2 and 1_P2) => s2 will contain P2, 1_P2, 1_P2, 1_1_P2.
     */
    private static final String COUNT = "SingleXMLDocument_count";

    /** Le nombre de fichiers concat */
    private int numero;
    /** Les styles présent dans ce document */
    private final Set<String> stylesNames;
    /** Les styles de liste présent dans ce document */
    private final Set<String> listStylesNames;
    /** Les fichiers référencés par ce document */
    private ODPackage pkg;
    private final ODMeta meta;
    // the element between each page
    private Element pageBreak;

    public ODSingleXMLDocument(Document content) {
        this(content, null);
    }

    /**
     * A new single document. NOTE: this document will put himself in <code>pkg</code>, replacing
     * any previous content.
     * 
     * @param content the XML.
     * @param pkg the package this document belongs to.
     */
    private ODSingleXMLDocument(Document content, final ODPackage pkg) {
        super(content);

        // inited in getPageBreak()
        this.pageBreak = null;

        final boolean contentIsFlat = pkg == null;
        this.pkg = contentIsFlat ? new ODPackage() : pkg;
        if (!contentIsFlat) {
            final Set<String> toRm = new HashSet<String>();
            for (final RootElement e : RootElement.getPackageElements())
                toRm.add(e.getZipEntry());
            for (final String e : this.pkg.getEntries()) {
                if (e.startsWith(Library.DIR_NAME))
                    toRm.add(e);
            }
            this.pkg.rmFiles(toRm);
        }
        this.pkg.putFile(CONTENT.getZipEntry(), this, "text/xml");

        // update href
        if (contentIsFlat) {
            // OD thinks of the ZIP archive as an additional folder
            for (final Attribute hrefAttr : ALL_HREF_ATTRIBUTES.selectNodes(getDocument().getRootElement())) {
                final String href = hrefAttr.getValue();
                if (!URI.create(href).isAbsolute())
                    hrefAttr.setValue("../" + href);
            }
        }
        // decode Base64 binaries
        for (final Element binaryDataElem : ALL_BINARY_DATA_ELEMENTS.selectNodes(getDocument().getRootElement())) {
            final String name;
            int i = 1;
            final Set<String> entries = getPackage().getEntries();
            final Element binaryParentElement = binaryDataElem.getParentElement();
            while (entries.contains(binaryParentElement.getName() + "/" + i))
                i++;
            name = binaryParentElement.getName() + "/" + i;
            getPackage().putFile(name, Base64.decode(binaryDataElem.getText()));
            binaryParentElement.setAttribute("href", name, binaryDataElem.getNamespace("xlink"));
            binaryDataElem.detach();
        }

        this.meta = this.getPackage().getMeta(true);

        final ODUserDefinedMeta userMeta = this.meta.getUserMeta(COUNT);
        if (userMeta != null) {
            final Object countValue = userMeta.getValue();
            if (countValue instanceof Number) {
                this.numero = ((Number) countValue).intValue();
            } else {
                this.numero = new BigDecimal(countValue.toString()).intValue();
            }
        } else {
            // if not hasCount(), it's not us that created content
            // so there should not be any 1_
            this.setNumero(0);
        }

        this.stylesNames = new HashSet<String>(64);
        this.listStylesNames = new HashSet<String>(16);

        // little trick to find the common styles names (not to be prefixed so they remain
        // consistent across the added documents)
        final Element styles = this.getChild("styles");
        if (styles != null) {
            // create a second document with our styles to collect names
            final Element root = this.getDocument().getRootElement();
            final Document clonedDoc = new Document(new Element(root.getName(), root.getNamespace()));
            clonedDoc.getRootElement().addContent(styles.detach());
            try {
                this.mergeStyles(new ODXMLDocument(clonedDoc), true);
            } catch (JDOMException e) {
                throw new IllegalArgumentException("can't find common styles names.");
            }
            // reattach our styles
            styles.detach();
            this.setChild(styles);
        }
    }

    ODSingleXMLDocument(ODSingleXMLDocument doc, ODPackage p) {
        super(doc);
        if (p == null)
            throw new NullPointerException("Null package");
        this.stylesNames = new HashSet<String>(doc.stylesNames);
        this.listStylesNames = new HashSet<String>(doc.listStylesNames);
        this.pkg = p;
        this.meta = ODMeta.create(this);
        this.setNumero(doc.numero);
    }

    @Override
    public ODSingleXMLDocument clone() {
        final ODPackage copy = new ODPackage(this.pkg);
        return (ODSingleXMLDocument) copy.getContent();
    }

    private void setNumero(int numero) {
        this.numero = numero;
        this.meta.getUserMeta(COUNT, true).setValue(this.numero);
    }

    /**
     * The number of files concatenated with {@link #add(ODSingleXMLDocument)}.
     * 
     * @return number of files concatenated.
     */
    public final int getNumero() {
        return this.numero;
    }

    public ODPackage getPackage() {
        return this.pkg;
    }

    final Element getBasicScriptElem() {
        return this.getBasicScriptElem(false);
    }

    private final Element getBasicScriptElem(final boolean create) {
        final OOXML xml = getXML();
        final String officeScripts = xml.getOfficeScripts();
        final Element scriptsElem = this.getChild(officeScripts, create);
        if (scriptsElem == null)
            return null;
        final Namespace scriptNS = this.getVersion().getNS("script");
        final Namespace officeNS = this.getVersion().getOFFICE();
        @SuppressWarnings("unchecked")
        final List<Element> scriptElems = scriptsElem.getChildren(xml.getOfficeScript(), officeNS);
        for (final Element scriptElem : scriptElems) {
            if (scriptElem.getAttributeValue("language", scriptNS).equals(BASIC_LANG_NAME))
                return scriptElem;
        }
        if (create) {
            final Element res = new Element(xml.getOfficeScript(), officeNS);
            res.setAttribute("language", BASIC_LANG_NAME, scriptNS);
            scriptsElem.addContent(res);
            return res;
        } else {
            return null;
        }
    }

    /**
     * Parse BASIC libraries in this flat XML.
     * 
     * @return the BASIC libraries by name.
     */
    public final Map<String, Library> readBasicLibraries() {
        return this.readBasicLibraries(this.getBasicScriptElem()).get0();
    }

    private final Tuple2<Map<String, Library>, Map<String, Element>> readBasicLibraries(final Element scriptElem) {
        if (scriptElem == null)
            return Tuple2.create(Collections.<String, Library> emptyMap(), Collections.<String, Element> emptyMap());

        final Namespace libNS = this.getVersion().getLibrariesNS();
        final Namespace linkNS = this.getVersion().getNS("xlink");
        final Map<String, Library> res = new HashMap<String, Library>();
        final Map<String, Element> resElems = new HashMap<String, Element>();
        @SuppressWarnings("unchecked")
        final List<Element> libsElems = scriptElem.getChildren("libraries", libNS);
        for (final Element libsElem : libsElems) {
            @SuppressWarnings("unchecked")
            final List<Element> libElems = libsElem.getChildren();
            for (final Element libElem : libElems) {
                final Library library = Library.fromFlatXML(libElem, this.getPackage(), linkNS);
                if (library != null) {
                    if (res.put(library.getName(), library) != null)
                        throw new IllegalStateException("Duplicate library named " + library.getName());
                    resElems.put(library.getName(), libElem);
                }
            }
        }

        return Tuple2.create(res, resElems);
    }

    /**
     * Append a document.
     * 
     * @param doc the document to add.
     */
    public synchronized void add(ODSingleXMLDocument doc) {
        // ajoute un saut de page entre chaque document
        this.add(doc, true);
    }

    /**
     * Append a document.
     * 
     * @param doc the document to add, <code>null</code> means no-op.
     * @param pageBreak whether a page break should be inserted before <code>doc</code>.
     */
    public synchronized void add(ODSingleXMLDocument doc, boolean pageBreak) {
        if (doc != null && pageBreak) {
            // only add a page break, if a page was really added
            final Element thisBody = this.getBody();
            thisBody.addContent(getLastNulls(ELEMS_PARTS.get(getVersion()), thisBody)[0], this.getPageBreak());
        }
        this.add(null, -1, doc);
    }

    public synchronized void replace(Element elem, ODSingleXMLDocument doc) {
        final Element parent = elem.getParentElement();
        this.add(parent, parent.indexOf(elem), doc);
        elem.detach();
    }

    // use content index and not children (element) index, since it's more accurate (we can add
    // after or before a comment) and faster (no filter and adjusted index)
    /**
     * Add the passed document at the specified place.
     * 
     * @param where a descendant of the body, <code>null</code> meaning the body itself.
     * @param index the content index inside <code>where</code>, -1 meaning the end.
     * @param doc the document to add, <code>null</code> means no-op.
     */
    public synchronized void add(Element where, int index, ODSingleXMLDocument doc) {
        if (doc == null)
            return;
        if (!this.getVersion().equals(doc.getVersion()))
            throw new IllegalArgumentException("version mismatch");

        this.setNumero(this.numero + 1);
        try {
            copyNS(doc.getDocument(), this.getDocument());
            this.mergeEmbedded(doc);
            this.mergeSettings(doc);
            this.mergeScripts(doc);
            this.mergeAllStyles(doc, false);
            this.mergeBody(where, index, doc);
        } catch (JDOMException exn) {
            throw new IllegalArgumentException("XML error", exn);
        }
    }

    /**
     * Merge the four elements of style.
     * 
     * @param doc the xml document to merge.
     * @param sameDoc whether <code>doc</code> is the same OpenDocument than this, eg
     *        <code>true</code> when merging content.xml and styles.xml.
     * @throws JDOMException if an error occurs.
     */
    private void mergeAllStyles(ODXMLDocument doc, boolean sameDoc) throws JDOMException {
        // no reference
        this.mergeFontDecls(doc);
        // section 14.1
        // § Parent Style only refer to other common styles
        // § Next Style cannot refer to an autostyle (only available in common styles)
        // § List Style can refer to an autostyle
        // § Master Page Name cannot (auto master pages does not exist)
        // § Data Style Name (for cells) can
        // but since the UI for common styles doesn't allow to customize List Style
        // and there is no common styles for tables : office:styles doesn't reference any automatic
        // styles
        this.mergeStyles(doc, sameDoc);
        // on the contrary autostyles do refer to other autostyles :
        // choosing "activate bullets" will create an automatic paragraph style:style
        // referencing an automatic text:list-style.
        this.mergeAutoStyles(doc, !sameDoc);
        // section 14.4
        // § Page Layout can refer to an autostyle
        // § Next Style Name refer to another masterPage
        this.mergeMasterStyles(doc, !sameDoc);
    }

    private void mergeEmbedded(ODSingleXMLDocument doc) {
        // since we are adding another document our existing thumbnail is obsolete
        this.pkg.rmFile("Thumbnails/thumbnail.png");
        this.pkg.rmFile("layout-cache");
        // copy the files (only non generated files, e.g. content.xml will be merged later)
        for (final String name : doc.pkg.getEntries()) {
            final ODPackageEntry e = doc.pkg.getEntry(name);
            if (!ODPackage.isStandardFile(e.getName())) {
                this.pkg.putCopy(e, this.prefix(e.getName()));
            }
        }
    }

    private void mergeSettings(ODSingleXMLDocument doc) throws JDOMException {
        // used to call addIfNotPresent(), but it cannot create the element at the correct position
        final String elemName = "settings";
        if (this.getChild(elemName, false) == null) {
            final Element other = doc.getChild(elemName, false);
            if (other != null) {
                this.getChild(elemName, true).addContent(other.cloneContent());
            }
        }
    }

    private void mergeScripts(ODSingleXMLDocument doc) {
        // <office:script>*
        this.addBasicLibraries(doc.readBasicLibraries());

        // <office:event-listeners>?
        final Map<String, EventListener> oEvents = doc.getPackage().readEventListeners();
        if (oEvents.size() > 0) {
            // check if they can be merged
            final Map<String, EventListener> thisEvents = this.getPackage().readEventListeners();
            final Set<String> duplicateEvents = CollectionUtils.inter(thisEvents.keySet(), oEvents.keySet());
            for (final String eventName : duplicateEvents) {
                final Element thisEvent = thisEvents.get(eventName).getElement();
                final Element oEvent = oEvents.get(eventName).getElement();
                if (!JDOMUtils.equalsDeep(oEvent, thisEvent)) {
                    throw new IllegalArgumentException("Incompatible elements for " + eventName);
                }
            }

            final OOXML xml = getXML();
            final Element thisScripts = this.getChild(xml.getOfficeScripts(), true);
            final Element thisEventListeners = JDOMUtils.getOrCreateChild(thisScripts, xml.getOfficeEventListeners(), this.getVersion().getOFFICE());
            for (final Entry<String, EventListener> e : oEvents.entrySet()) {
                if (!thisEvents.containsKey(e.getKey())) {
                    // we can just clone since libraries aren't renamed when merged
                    thisEventListeners.addContent((Element) e.getValue().getElement().clone());
                }
            }
        }
    }

    /**
     * Add the passed libraries to this document. Passed libraries with the same content as existing
     * ones are ignored.
     * 
     * @param libraries what to add.
     * @return the actually added libraries.
     * @throws IllegalArgumentException if <code>libraries</code> contains duplicates or if it
     *         cannot be merged into this.
     * @see Library#canBeMerged(Library)
     */
    public final Set<String> addBasicLibraries(final Collection<? extends Library> libraries) {
        return this.addBasicLibraries(Library.toMap(libraries));
    }

    public final Set<String> addBasicLibraries(final ODPackage pkg) {
        if (pkg == this.pkg)
            return Collections.emptySet();
        return this.addBasicLibraries(pkg.readBasicLibraries());
    }

    final Set<String> addBasicLibraries(final Map<String, Library> oLibraries) {
        if (oLibraries.size() == 0)
            return Collections.emptySet();

        final Tuple2<Map<String, Library>, Map<String, Element>> thisLibrariesAndElements = this.readBasicLibraries(this.getBasicScriptElem(false));
        final Map<String, Library> thisLibraries = thisLibrariesAndElements.get0();
        final Map<String, Element> thisLibrariesElements = thisLibrariesAndElements.get1();
        // check that the libraries to add which are already in us can be merged (no elements
        // conflict)
        final Set<String> duplicateLibs = Library.canBeMerged(thisLibraries, oLibraries);
        final Set<String> newLibs = new HashSet<String>(oLibraries.keySet());
        newLibs.removeAll(thisLibraries.keySet());

        // merge modules
        for (final String duplicateLib : duplicateLibs) {
            final Library thisLib = thisLibraries.get(duplicateLib);
            final Library oLib = oLibraries.get(duplicateLib);
            assert thisLib != null && oLib != null : "Not duplicate " + duplicateLib;
            oLib.mergeToFlatXML(this.getFormatVersion(), thisLib, thisLibrariesElements.get(duplicateLib));
        }
        if (newLibs.size() > 0) {
            final Element thisScriptElem = this.getBasicScriptElem(true);
            final Element librariesElem = JDOMUtils.getOrCreateChild(thisScriptElem, "libraries", this.getVersion().getLibrariesNS());
            for (final String newLib : newLibs)
                librariesElem.addContent(oLibraries.get(newLib).toFlatXML(this.getFormatVersion()));
        }

        // merge dialogs
        for (final Library oLib : oLibraries.values()) {
            final String libName = oLib.getName();
            // can be null
            final Library thisLib = thisLibraries.get(libName);
            oLib.mergeDialogs(this.getPackage(), thisLib);
        }

        return newLibs;
    }

    /**
     * Remove the passed libraries.
     * 
     * @param libraries which libraries to remove.
     * @return the actually removed libraries.
     */
    public final Set<String> removeBasicLibraries(final Collection<String> libraries) {
        final Element basicScriptElem = this.getBasicScriptElem(false);
        final Map<String, Element> thisLibrariesElements = this.readBasicLibraries(basicScriptElem).get1();
        // don't return if thisLibrariesElements is empty as we also check the package content

        final Set<String> res = new HashSet<String>();
        for (final String libToRm : libraries) {
            final Element elemToRm = thisLibrariesElements.get(libToRm);
            if (elemToRm != null) {
                // also detach empty <libraries>
                JDOMUtils.detachEmptyParent(elemToRm);
                res.add(libToRm);
            }
            if (Library.removeFromPackage(this.getPackage(), libToRm))
                res.add(libToRm);
        }
        // Detach empty <script>, works because we already detached empty <libraries>
        if (basicScriptElem != null && basicScriptElem.getChildren().isEmpty())
            basicScriptElem.detach();

        return res;
    }

    /**
     * Fusionne les office:font-decls/style:font-decl. On ne préfixe jamais, on ajoute seulement si
     * l'attribut style:name est différent.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @throws JDOMException
     */
    private void mergeFontDecls(ODXMLDocument doc) throws JDOMException {
        final String[] fontDecls = this.getFontDecls();
        this.mergeUnique(doc, fontDecls[0], fontDecls[1]);
    }

    private String[] getFontDecls() {
        return getXML().getFontDecls();
    }

    // merge everything under office:styles
    private void mergeStyles(ODXMLDocument doc, boolean sameDoc) throws JDOMException {
        // les default-style (notamment tab-stop-distance)
        this.mergeUnique(doc, "styles", "style:default-style", "style:family", NOP_ElementTransformer);
        // data styles
        // we have to prefix data styles since they're automatically generated by LO
        // (e.g. user created cellStyle1, LO generated dataStyleN0 in a document, then in another
        // document created cellStyle2, LO also generated dataStyleN0)
        // MAYBE search for orphans that discarded (same name) styles might leave
        // Don't prefix if we're merging styles into content (content contains no styles, so there
        // can be no collision ; also we don't prefix the body)
        final String dsNS = "number";
        final boolean prefixDataStyles = !sameDoc;
        final List<Element> addedDataStyles = this.addStyles(doc, "styles", Step.createElementStep(null, dsNS, new IPredicate<Element>() {
            private final Set<String> names;
            {
                this.names = new HashSet<String>(DataStyle.DATA_STYLES.size());
                for (final Class<? extends DataStyle> cl : DataStyle.DATA_STYLES) {
                    final StyleDesc<? extends DataStyle> styleDesc = Style.getStyleDesc(cl, getVersion());
                    this.names.add(styleDesc.getElementName());
                    assert styleDesc.getElementNS().getPrefix().equals(dsNS) : styleDesc;
                }
            }

            @Override
            public boolean evaluateChecked(Element elem) {
                return this.names.contains(elem.getName());
            }
        }), prefixDataStyles);
        if (prefixDataStyles) {
            // data styles reference each other (e.g. style:map)
            final SimpleXMLPath<Attribute> simplePath = SimpleXMLPath.allAttributes("apply-style-name", "style");
            for (final Attribute attr : simplePath.selectNodes(addedDataStyles)) {
                attr.setValue(prefix(attr.getValue()));
            }
        }
        // les styles
        // if we prefixed data styles, we must prefix references
        this.stylesNames.addAll(this.mergeUnique(doc, "styles", "style:style", !prefixDataStyles ? NOP_ElementTransformer : new ElementTransformer() {
            @Override
            public Element transform(Element elem) throws JDOMException {
                final Attribute attr = elem.getAttribute("data-style-name", elem.getNamespace());
                if (attr != null)
                    attr.setValue(prefix(attr.getValue()));
                return elem;
            }
        }));
        // on ajoute outline-style si non présent
        this.addStylesIfNotPresent(doc, "outline-style");
        // les list-style
        this.listStylesNames.addAll(this.mergeUnique(doc, "styles", "text:list-style"));
        // les *notes-configuration
        if (getVersion() == XMLVersion.OOo) {
            this.addStylesIfNotPresent(doc, "footnotes-configuration");
            this.addStylesIfNotPresent(doc, "endnotes-configuration");
        } else {
            // 16.29.3 : specifies values for each note class used in a document
            this.mergeUnique(doc, "styles", "text:notes-configuration", "text:note-class", NOP_ElementTransformer);
        }
        this.addStylesIfNotPresent(doc, "bibliography-configuration");
        this.addStylesIfNotPresent(doc, "linenumbering-configuration");
    }

    /**
     * Fusionne les office:automatic-styles, on préfixe tout.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @param ref whether to prefix hrefs.
     * @throws JDOMException
     */
    private void mergeAutoStyles(ODXMLDocument doc, boolean ref) throws JDOMException {
        final List<Element> addedStyles = this.prefixAndAddAutoStyles(doc);
        for (final Element addedStyle : addedStyles) {
            this.prefix(addedStyle, ref);
        }
    }

    /**
     * Fusionne les office:master-styles. On ne préfixe jamais, on ajoute seulement si l'attribut
     * style:name est différent.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @param ref whether to prefix hrefs.
     * @throws JDOMException if an error occurs.
     */
    private void mergeMasterStyles(ODXMLDocument doc, boolean ref) throws JDOMException {
        // est référencé dans les styles avec "style:master-page-name"
        this.mergeUnique(doc, "master-styles", "style:master-page", ref ? this.prefixTransf : this.prefixTransfNoRef);
    }

    /**
     * Fusionne les corps.
     * 
     * @param where the element where to add the main content, <code>null</code> meaning at the root
     *        of the body.
     * @param index the content index inside <code>where</code>, -1 meaning at the end.
     * @param doc le document à fusionner avec celui-ci.
     * @throws JDOMException
     */
    private void mergeBody(Element where, int index, ODSingleXMLDocument doc) throws JDOMException {
        final Element thisBody = this.getBody();
        final Map<Tuple2<Namespace, String>, ContentPart> parts = ELEMS_PARTS.get(getVersion());

        // find where to add
        final Element nonNullWhere = where != null ? where : thisBody;
        if (nonNullWhere == thisBody) {
            // don't add in prologue or epilogue (ATTN the caller passed the index in reference to
            // the existing body but it might change and thus the index might need correction)
            index = getValidIndex(parts, thisBody, index);
        } else {
            // check that the element is rooted in the main part
            final Element movedChild = JDOMUtils.getAncestor(nonNullWhere, new IPredicate<Element>() {
                @Override
                public boolean evaluateChecked(Element input) {
                    return input.getParent() == thisBody;
                }
            });
            if (movedChild == null)
                throw new IllegalStateException("not adding in body : " + nonNullWhere);
            final ContentPart contentPart = getPart(parts, movedChild);
            if (contentPart != ContentPart.MAIN)
                throw new IllegalStateException("not adding in main : " + contentPart + " ; " + nonNullWhere);
        }

        final ChildCreator childCreator = ChildCreator.createFromSets(thisBody, ELEMS_ORDER.get(getVersion()));
        int prologueAddedCount = 0;
        final Element otherBody = doc.getBody();
        // split doc body in to three parts to keep non-elements
        final Point[] bounds = getBounds(parts, otherBody, false);
        @SuppressWarnings("unchecked")
        final List<Content> otherContent = otherBody.getContent();
        // prologue and epilogue have small and bounded size
        final List<Content> mainElements = new ArrayList<Content>(otherContent.size());
        final ListIterator<Content> listIter = otherContent.listIterator();
        for (final ContentPart part : ContentPart.values()) {
            final Point partBounds = bounds[part.ordinal()];
            final int partEnd = partBounds.y;
            while (listIter.nextIndex() < partEnd) {
                assert listIter.hasNext() : "wrong bounds";
                final Content c = listIter.next();
                if (c instanceof Element) {
                    final Element bodyChild = (Element) c;
                    if (part == ContentPart.PROLOGUE) {
                        final int preSize = thisBody.getContentSize();
                        final String childName = bodyChild.getName();
                        if (childName.equals("forms")) {
                            final Element elem = (Element) bodyChild.clone();
                            // TODO prefix xml:id and their draw:control references
                            this.prefix(elem, true);
                            childCreator.getChild(bodyChild, true).addContent(elem.removeContent());
                        } else if (childName.equals("variable-decls") || childName.equals("sequence-decls") || childName.equals("user-field-decls")) {
                            final Element elem = (Element) bodyChild.clone();
                            // * user fields are global to a document, they do not vary across it.
                            // Hence they are initialized at declaration
                            // * variables are not initialized at declaration
                            detachDuplicate(elem);
                            this.prefix(elem, true);
                            childCreator.getChild(bodyChild, true).addContent(elem.removeContent());
                        } else {
                            Log.get().fine("Ignoring in " + part + " : " + bodyChild);
                        }
                        final int postSize = thisBody.getContentSize();
                        prologueAddedCount += postSize - preSize;
                    } else if (part == ContentPart.MAIN) {
                        mainElements.add(this.prefix((Element) bodyChild.clone(), true));
                    } else if (part == ContentPart.EPILOGUE) {
                        Log.get().fine("Ignoring in " + part + " : " + bodyChild);
                    }
                } else if (part == ContentPart.MAIN) {
                    mainElements.add((Content) c.clone());
                } else {
                    Log.get().finer("Ignoring non-element in " + part);
                }
            }
        }

        if (nonNullWhere == thisBody) {
            assert index >= 0;
            index += prologueAddedCount;
            assert index >= 0 && index <= thisBody.getContentSize();
        }
        if (index < 0)
            nonNullWhere.addContent(mainElements);
        else
            nonNullWhere.addContent(index, mainElements);
    }

    /**
     * Detach the children of elem whose names already exist in the body.
     * 
     * @param elem the elem to be trimmed.
     * @throws JDOMException if an error occurs.
     */
    protected final void detachDuplicate(Element elem) throws JDOMException {
        final String singularName = elem.getName().substring(0, elem.getName().length() - 1);
        final List thisNames = getXPath("./text:" + singularName + "s/text:" + singularName + "/@text:name").selectNodes(getChild("body"));
        org.apache.commons.collections.CollectionUtils.transform(thisNames, new Transformer() {
            public Object transform(Object obj) {
                return ((Attribute) obj).getValue();
            }
        });

        final Iterator iter = elem.getChildren().iterator();
        while (iter.hasNext()) {
            final Element decl = (Element) iter.next();
            if (thisNames.contains(decl.getAttributeValue("name", getVersion().getTEXT()))) {
                // on retire les déjà existant
                iter.remove();
            }
        }
    }

    // *** Utils

    public final Element getBody() {
        return this.getContentTypeVersioned().getBody(getDocument());
    }

    private ContentTypeVersioned getContentTypeVersioned() {
        return getPackage().getContentType();
    }

    /**
     * Préfixe les attributs en ayant besoin.
     * 
     * @param elem l'élément à préfixer.
     * @param references whether to prefix hrefs.
     * @return <code>elem</code>.
     * @throws JDOMException if an error occurs.
     */
    Element prefix(Element elem, boolean references) throws JDOMException {
        Iterator attrs = this.getXPath(".//@text:style-name | .//@table:style-name | .//@draw:style-name | .//@style:data-style-name | .//@style:apply-style-name").selectNodes(elem).iterator();
        while (attrs.hasNext()) {
            Attribute attr = (Attribute) attrs.next();
            // text:list/@text:style-name references text:list-style
            if (!this.listStylesNames.contains(attr.getValue()) && !this.stylesNames.contains(attr.getValue())) {
                attr.setValue(this.prefix(attr.getValue()));
            }
        }

        attrs = this.getXPath(".//@style:list-style-name").selectNodes(elem).iterator();
        while (attrs.hasNext()) {
            Attribute attr = (Attribute) attrs.next();
            if (!this.listStylesNames.contains(attr.getValue())) {
                attr.setValue(this.prefix(attr.getValue()));
            }
        }

        attrs = this.getXPath(".//@style:page-master-name | .//@style:page-layout-name | .//@text:name | .//@form:name | .//@form:property-name").selectNodes(elem).iterator();
        while (attrs.hasNext()) {
            final Attribute attr = (Attribute) attrs.next();
            final String parentName = attr.getParent().getName();
            if (!DONT_PREFIX.contains(parentName))
                attr.setValue(this.prefix(attr.getValue()));
        }

        // prefix references
        if (references) {
            attrs = this.getXPath(".//@xlink:href[../@xlink:show='embed']").selectNodes(elem).iterator();
            while (attrs.hasNext()) {
                final Attribute attr = (Attribute) attrs.next();
                final String prefixedPath = this.prefixPath(attr.getValue());
                if (prefixedPath != null)
                    attr.setValue(prefixedPath);
            }
        }
        return elem;
    }

    /**
     * Prefix a path.
     * 
     * @param href a path inside the pkg, eg "./Object 1/content.xml".
     * @return the prefixed path or <code>null</code> if href is external, eg "./3_Object
     *         1/content.xml".
     */
    private String prefixPath(final String href) {
        if (this.getVersion().equals(XMLVersion.OOo)) {
            // in OOo 1.x inPKG is denoted by a #
            final boolean sharp = href.startsWith("#");
            if (sharp)
                // eg #Pictures/100000000000006C000000ABCC02339E.png
                return "#" + this.prefix(href.substring(1));
            else
                // eg ../../../../Program%20Files/OpenOffice.org1.1.5/share/gallery/apples.gif
                return null;
        } else {
            URI uri;
            try {
                uri = new URI(href);
            } catch (URISyntaxException e) {
                // OO doesn't escape characters for files
                uri = null;
            }
            // section 17.5
            final boolean inPKGFile = uri == null || uri.getScheme() == null && uri.getAuthority() == null && uri.getPath().charAt(0) != '/';
            if (inPKGFile) {
                final String dotSlash = "./";
                if (href.startsWith(dotSlash))
                    return dotSlash + this.prefix(href.substring(dotSlash.length()));
                else
                    return this.prefix(href);
            } else
                return null;
        }
    }

    private String prefix(String value) {
        return "_" + this.numero + value;
    }

    private final ElementTransformer prefixTransf = new ElementTransformer() {
        public Element transform(Element elem) throws JDOMException {
            return ODSingleXMLDocument.this.prefix(elem, true);
        }
    };

    private final ElementTransformer prefixTransfNoRef = new ElementTransformer() {
        public Element transform(Element elem) throws JDOMException {
            return ODSingleXMLDocument.this.prefix(elem, false);
        }
    };

    /**
     * Ajoute dans ce document seulement les éléments de doc correspondant au XPath spécifié et dont
     * la valeur de l'attribut style:name n'existe pas déjà.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @param topElem eg "office:font-decls".
     * @param elemToMerge les éléments à fusionner (par rapport à topElem), eg "style:font-decl".
     * @return les noms des éléments ajoutés.
     * @throws JDOMException
     * @see #mergeUnique(ODSingleXMLDocument, String, String, ElementTransformer)
     */
    private List<String> mergeUnique(ODXMLDocument doc, String topElem, String elemToMerge) throws JDOMException {
        return this.mergeUnique(doc, topElem, elemToMerge, NOP_ElementTransformer);
    }

    /**
     * Ajoute dans ce document seulement les éléments de doc correspondant au XPath spécifié et dont
     * la valeur de l'attribut style:name n'existe pas déjà. En conséquence n'ajoute que les
     * éléments possédant un attribut style:name.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @param topElem eg "office:font-decls".
     * @param elemToMerge les éléments à fusionner (par rapport à topElem), eg "style:font-decl".
     * @param addTransf la transformation à appliquer avant d'ajouter.
     * @return les noms des éléments ajoutés.
     * @throws JDOMException
     */
    private List<String> mergeUnique(ODXMLDocument doc, String topElem, String elemToMerge, ElementTransformer addTransf) throws JDOMException {
        return this.mergeUnique(doc, topElem, elemToMerge, "style:name", addTransf);
    }

    private List<String> mergeUnique(ODXMLDocument doc, String topElem, String elemToMerge, String attrFQName, ElementTransformer addTransf) throws JDOMException {
        List<String> added = new ArrayList<String>();
        Element thisParent = this.getChild(topElem, true);

        XPath xp = this.getXPath("./" + elemToMerge + "/@" + attrFQName);

        // les styles de ce document
        List thisElemNames = xp.selectNodes(thisParent);
        // on transforme la liste d'attributs en liste de String
        org.apache.commons.collections.CollectionUtils.transform(thisElemNames, new Transformer() {
            public Object transform(Object obj) {
                return ((Attribute) obj).getValue();
            }
        });

        // pour chaque style de l'autre document
        Iterator otherElemNames = xp.selectNodes(doc.getChild(topElem)).iterator();
        while (otherElemNames.hasNext()) {
            Attribute attr = (Attribute) otherElemNames.next();
            // on l'ajoute si non déjà dedans
            if (!thisElemNames.contains(attr.getValue())) {
                thisParent.addContent(addTransf.transform((Element) attr.getParent().clone()));
                added.add(attr.getValue());
            }
        }

        return added;
    }

    /**
     * Ajoute l'élément elemName de doc, s'il n'est pas dans ce document.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @param elemName l'élément à ajouter, eg "outline-style".
     * @throws JDOMException if elemName is not valid.
     */
    private void addStylesIfNotPresent(ODXMLDocument doc, String elemName) throws JDOMException {
        this.addIfNotPresent(doc, "./office:styles/text:" + elemName);
    }

    /**
     * Prefixe les fils de auto-styles possédant un attribut "name" avant de les ajouter.
     * 
     * @param doc le document à fusionner avec celui-ci.
     * @return les élément ayant été ajoutés.
     * @throws JDOMException
     */
    private List<Element> prefixAndAddAutoStyles(ODXMLDocument doc) throws JDOMException {
        return addStyles(doc, "automatic-styles", Step.getAnyChildElementStep(), true);
    }

    // add styles from doc/rootElem/styleElemStep/@style:name, optionally prefixing
    private List<Element> addStyles(ODXMLDocument doc, final String rootElem, final Step<Element> styleElemStep, boolean prefix) throws JDOMException {
        // needed since we add to us directly under rootElem
        if (styleElemStep.getAxis() != Axis.child)
            throw new IllegalArgumentException("Not child axis : " + styleElemStep.getAxis());
        final List<Element> result = new ArrayList<Element>(128);
        final Element thisChild = this.getChild(rootElem);
        // find all elements with a style:name in doc
        final SimpleXMLPath<Attribute> simplePath = SimpleXMLPath.create(styleElemStep, Step.createAttributeStep("name", "style"));
        for (final Attribute attr : simplePath.selectNodes(doc.getChild(rootElem))) {
            final Element parent = (Element) attr.getParent().clone();
            // prefix their name
            if (prefix)
                parent.setAttribute(attr.getName(), this.prefix(attr.getValue()), attr.getNamespace());
            // and add to us
            thisChild.addContent(parent);
            result.add(parent);
        }
        return result;
    }

    /**
     * Return <code>true</code> if this document was split.
     * 
     * @return <code>true</code> if this has no package anymore.
     * @see ODPackage#split()
     */
    public final boolean isDead() {
        return this.getPackage() == null;
    }

    final Map<RootElement, Document> split() {
        final Map<RootElement, Document> res = new HashMap<RootElement, Document>();
        final XMLVersion version = getVersion();
        final Element root = this.getDocument().getRootElement();
        final XMLFormatVersion officeVersion = getFormatVersion();

        // meta
        {
            final Element thisMeta = root.getChild("meta", version.getOFFICE());
            if (thisMeta != null) {
                final Document meta = createDocument(res, RootElement.META, officeVersion);
                meta.getRootElement().addContent(thisMeta.detach());
            }
        }
        // settings
        {
            final Element thisSettings = root.getChild("settings", version.getOFFICE());
            if (thisSettings != null) {
                final Document settings = createDocument(res, RootElement.SETTINGS, officeVersion);
                settings.getRootElement().addContent(thisSettings.detach());
            }
        }
        // scripts
        {
            final Element thisScript = this.getBasicScriptElem();
            if (thisScript != null) {
                final Map<String, Library> basicLibraries = this.readBasicLibraries(thisScript).get0();
                final Element lcRootElem = new Element("libraries", XMLVersion.LIBRARY_NS);
                for (final Library lib : basicLibraries.values()) {
                    lcRootElem.addContent(lib.toPackageLibrariesElement(officeVersion));
                    for (final Entry<String, Document> e : lib.toPackageDocuments(officeVersion).entrySet()) {
                        this.pkg.putFile(Library.DIR_NAME + "/" + lib.getName() + "/" + e.getKey(), e.getValue(), FileUtils.XML_TYPE);
                    }
                }
                final Document lc = new Document(lcRootElem, new DocType("library:libraries", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "libraries.dtd"));
                this.pkg.putFile(Library.DIR_NAME + "/" + Library.LIBRARY_LIST_FILENAME, lc, FileUtils.XML_TYPE);
                thisScript.detach();
                // nothing to do for dialogs, since they cannot be in our Document
            }
        }
        // styles
        // we must move office:styles, office:master-styles and referenced office:automatic-styles
        {
            final Document styles = createDocument(res, RootElement.STYLES, officeVersion);
            // don't bother finding out which font is used where since there isn't that many of them
            styles.getRootElement().addContent((Element) root.getChild(getFontDecls()[0], version.getOFFICE()).clone());
            // extract common styles
            styles.getRootElement().addContent(root.getChild("styles", version.getOFFICE()).detach());
            // only automatic styles used in the styles themselves.
            final Element contentAutoStyles = root.getChild("automatic-styles", version.getOFFICE());
            final Element stylesAutoStyles = new Element(contentAutoStyles.getName(), contentAutoStyles.getNamespace());
            final Element masterStyles = root.getChild("master-styles", version.getOFFICE());

            // style elements referenced, e.g. <style:page-layout style:name="pm1">
            final Set<Element> referenced = new HashSet<Element>();

            final SimpleXMLPath<Attribute> descAttrs = SimpleXMLPath.create(Step.createElementStep(Axis.descendantOrSelf, null), Step.createAttributeStep(null, null));
            for (final Attribute attr : descAttrs.selectNodes(masterStyles)) {
                final Element referencedStyleElement = Style.getReferencedStyleElement(this.pkg, attr);
                if (referencedStyleElement != null)
                    referenced.add(referencedStyleElement);
            }
            for (final Element r : referenced) {
                // since we already removed common styles
                assert r.getParentElement() == contentAutoStyles;
                stylesAutoStyles.addContent(r.detach());
            }

            styles.getRootElement().addContent(stylesAutoStyles);
            styles.getRootElement().addContent(masterStyles.detach());
        }
        // content
        {
            // store before emptying package
            final ContentTypeVersioned contentTypeVersioned = getContentTypeVersioned();
            // needed since the content will be emptied (which can cause methods of ODPackage to
            // fail, e.g. setTypeAndVersion())
            this.pkg.rmFile(RootElement.CONTENT.getZipEntry());
            this.pkg = null;
            final Document content = createDocument(res, RootElement.CONTENT, officeVersion);
            contentTypeVersioned.setType(content);
            content.getRootElement().addContent(root.removeContent());
        }
        return res;
    }

    private Document createDocument(final Map<RootElement, Document> res, RootElement rootElement, final XMLFormatVersion version) {
        final Document doc = rootElement.createDocument(version);
        copyNS(this.getDocument(), doc);
        res.put(rootElement, doc);
        return doc;
    }

    /**
     * Saves this OO document to a file.
     * 
     * @param f the file where this document will be saved, without extension, eg "dir/myfile".
     * @return the actual file where it has been saved (with extension), eg "dir/myfile.odt".
     * @throws IOException if an error occurs.
     */
    public File saveToPackageAs(File f) throws IOException {
        return this.pkg.saveAs(f);
    }

    public void saveToPackage(OutputStream f) throws IOException {
        this.pkg.save(f);
    }

    public File save() throws IOException {
        return this.saveAs(this.getPackage().getFile());
    }

    public File saveAs(File fNoExt) throws IOException {
        final Document doc = (Document) getDocument().clone();
        for (final Attribute hrefAttr : ALL_HREF_ATTRIBUTES.selectNodes(doc.getRootElement())) {
            final String href = hrefAttr.getValue();
            if (href.startsWith("../")) {
                // update href
                hrefAttr.setValue(href.substring(3));
            } else if (!URI.create(href).isAbsolute()) {
                // encode binaries
                final Element hrefParent = hrefAttr.getParent();
                if (!BINARY_DATA_PARENTS.contains(hrefParent.getQualifiedName()))
                    throw new IllegalStateException("Cannot convert to binary data element : " + hrefParent);
                final Element binaryData = new Element("binary-data", getPackage().getVersion().getOFFICE());

                binaryData.setText(Base64.encodeBytes(getPackage().getBinaryFile(href)));
                hrefParent.addContent(binaryData);
                // If this element is present, an xlink:href attribute in its parent element
                // shall be ignored. But LO doesn't respect that
                hrefAttr.detach();
            }
        }

        final File f = this.getPackage().getContentType().addExt(fNoExt, true);
        FileUtils.write(ODPackage.createOutputter().outputString(doc), f);
        return f;
    }

    private Element getPageBreak() {
        if (this.pageBreak == null) {
            final String styleName = "PageBreak";
            try {
                final XPath xp = this.getXPath("./style:style[@style:name='" + styleName + "']");
                final Element styles = this.getChild("styles", true);
                if (xp.selectSingleNode(styles) == null) {
                    final Element pageBreakStyle = new Element("style", this.getVersion().getSTYLE());
                    pageBreakStyle.setAttribute("name", styleName, this.getVersion().getSTYLE());
                    pageBreakStyle.setAttribute("family", "paragraph", this.getVersion().getSTYLE());
                    pageBreakStyle.setContent(getPProps().setAttribute("break-after", "page", this.getVersion().getNS("fo")));
                    // <element name="office:styles"> <interleave>...
                    // so just append the new style
                    styles.addContent(pageBreakStyle);
                    this.stylesNames.add(styleName);
                }
            } catch (JDOMException e) {
                // static path, shouldn't happen
                throw new IllegalStateException("pb while searching for " + styleName, e);
            }
            this.pageBreak = new Element("p", this.getVersion().getTEXT()).setAttribute("style-name", styleName, this.getVersion().getTEXT());
        }
        return (Element) this.pageBreak.clone();
    }

    private final Element getPProps() {
        return this.getXML().createFormattingProperties("paragraph");
    }
}
