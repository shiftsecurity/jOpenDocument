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
import static org.jopendocument.dom.ODPackage.RootElement.META;
import static org.jopendocument.dom.ODPackage.RootElement.STYLES;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.text.TextDocument;
import org.jopendocument.util.CopyUtils;
import org.jopendocument.util.ExceptionUtils;
import org.jopendocument.util.FileUtils;
import org.jopendocument.util.ProductInfo;
import org.jopendocument.util.PropertiesUtils;
import org.jopendocument.util.SetMap;
import org.jopendocument.util.StreamUtils;
import org.jopendocument.util.StringInputStream;
import org.jopendocument.util.StringUtils;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.Tuple3;
import org.jopendocument.util.Zip;
import org.jopendocument.util.ZippedFilesProcessor;
import org.jopendocument.util.cc.ITransformer;
import org.jopendocument.util.io.DataInputStream;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.Validator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;

import net.jcip.annotations.GuardedBy;

import org.jdom.Attribute;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * An OpenDocument package, ie a zip containing XML documents and their associated files.
 * 
 * @author ILM Informatique 2 août 2004
 */
public class ODPackage {

    static final String MIMETYPE_ENTRY = "mimetype";
    /** Normally mimetype contains only ASCII characters */
    static final Charset MIMETYPE_ENC = Charset.forName("UTF-8");

    @GuardedBy("ODPackage")
    private static String PAGE_COUNT = null;

    /**
     * Allow to specify a fixed number of pages for all text documents. This provides a workaround
     * for LibreOffice 4.0.x on Ubuntu which takes a long time to open documents without statistics.
     * E.g. 40s for 35 pages but only 4s if the page count is 500 pages.
     * 
     * @param count page count, negative means remove.
     */
    public static final synchronized void setPageCount(final int count) {
        if (count < 0)
            PAGE_COUNT = null;
        else
            PAGE_COUNT = String.valueOf(count);
    }

    public static final synchronized String getPageCount() {
        return PAGE_COUNT;
    }

    // not a constant since XMLOutputter isn't thread-safe
    static final XMLOutputter createOutputter() {
        // use raw format, otherwise spaces are added to every spreadsheet cell
        return new XMLOutputter(Format.getRawFormat());
    }

    /**
     * Root element of an OpenDocument document. See section 22.2.1 of v1.2-part1-cd04.
     * 
     * @author Sylvain CUAZ
     */
    // full name needed for javac 1.6.0_45
    @net.jcip.annotations.Immutable
    public static enum RootElement {
        /** Contains the entire document, see 3.1.2 of OpenDocument-v1.2-part1-cd04 */
        SINGLE_CONTENT("office", "document", null),
        /** Document content and automatic styles used in the content, see 3.1.3.2 */
        CONTENT("office", "document-content", "content.xml"),
        // TODO uncomment and create ContentTypeVersioned for .odf and .otf, see 22.2.9 Conforming
        // OpenDocument Formula Document
        // MATH("math", "math", "content.xml"),
        /** Styles used in document content and automatic styles used in styles, see 3.1.3.3 */
        STYLES("office", "document-styles", "styles.xml"),
        /** Document metadata elements, see 3.1.3.4 */
        META("office", "document-meta", "meta.xml"),
        /** Implementation-specific settings, see 3.1.3.5 */
        SETTINGS("office", "document-settings", "settings.xml");

        public final static EnumSet<RootElement> getPackageElements() {
            return EnumSet.of(CONTENT, STYLES, META, SETTINGS);
        }

        public final static RootElement fromDocument(final Document doc) {
            return fromElementName(doc.getRootElement().getName());
        }

        public final static RootElement fromElementName(final String name) {
            for (final RootElement e : values()) {
                if (e.getElementName().equals(name))
                    return e;
            }
            return null;
        }

        static final Document createSingle(final Document from) {
            return SINGLE_CONTENT.createDocument(XMLFormatVersion.get(from));
        }

        private final String nsPrefix;
        private final String name;
        private final String zipEntry;

        private RootElement(String prefix, String rootName, String zipEntry) {
            this.nsPrefix = prefix;
            this.name = rootName;
            this.zipEntry = zipEntry;
        }

        public final String getElementNSPrefix() {
            return this.nsPrefix;
        }

        public final String getElementName() {
            return this.name;
        }

        public final Document createDocument(final XMLFormatVersion fv) {
            final XMLVersion version = fv.getXMLVersion();
            final Element root = new Element(getElementName(), version.getNS(getElementNSPrefix()));
            // 19.388 office:version identifies the version of ODF specification
            if (fv.getOfficeVersion() != null)
                root.setAttribute("version", fv.getOfficeVersion(), version.getOFFICE());
            // avoid declaring namespaces in each child
            for (final Namespace ns : version.getALL())
                root.addNamespaceDeclaration(ns);

            return new Document(root, createDocType(version));
        }

        public final DocType createDocType(final XMLVersion version) {
            // OpenDocument use relaxNG
            if (version == XMLVersion.OOo)
                return new DocType(getElementNSPrefix() + ":" + getElementName(), "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "office.dtd");
            else
                return null;
        }

        /**
         * The name of the zip entry in the package.
         * 
         * @return the path of the file, <code>null</code> if this element shouldn't be in a
         *         package.
         */
        public final String getZipEntry() {
            return this.zipEntry;
        }
    }

    private static final Set<String> subdocNames;
    static {
        final Set<String> tmp = new HashSet<String>();
        for (final RootElement r : RootElement.getPackageElements())
            if (r.getZipEntry() != null)
                tmp.add(r.getZipEntry());
        subdocNames = Collections.unmodifiableSet(tmp);
    }

    /**
     * Whether the passed entry is specific to a package.
     * 
     * @param name a entry name, eg "mimetype"
     * @return <code>true</code> if <code>name</code> is a standard file, eg <code>true</code>.
     */
    public static final boolean isStandardFile(final String name) {
        return name.equals(MIMETYPE_ENTRY) || subdocNames.contains(name) || name.startsWith("Thumbnails") || name.startsWith("META-INF") || name.startsWith("Configurations")
                || name.equals("layout-cache") || name.equals("manifest.rdf") || name.startsWith(Library.DIR_NAME) || name.startsWith(Library.DIALOG_DIR_NAME);
    }

    /**
     * Create a package from a collection of sub-documents.
     * 
     * @param content the content.
     * @param style the styles, can be <code>null</code>.
     * @return a package containing the XML documents.
     */
    public static ODPackage createFromDocuments(Document content, Document style) {
        return createFromDocuments(null, content, style, null, null);
    }

    public static ODPackage createFromDocuments(final ContentTypeVersioned type, Document content, Document style, Document meta, Document settings) {
        final ODPackage pkg = new ODPackage();
        if (type != null)
            pkg.setContentType(type);
        pkg.putFile(RootElement.CONTENT.getZipEntry(), content);
        pkg.putFile(RootElement.STYLES.getZipEntry(), style);
        pkg.putFile(RootElement.META.getZipEntry(), meta);
        pkg.putFile(RootElement.SETTINGS.getZipEntry(), settings);
        return pkg;
    }

    /**
     * Read from the input stream into memory and close it.
     * 
     * @param ins the package or flat XML.
     * @param name the name, can be <code>null</code>.
     * @return a package containing the document.
     * @throws IOException if an error occurs.
     */
    public static ODPackage createFromStream(final InputStream ins, final String name) throws IOException {
        try {
            return create(null, ins, name);
        } finally {
            ins.close();
        }
    }

    public static ODPackage createFromFile(final File f) throws IOException {
        final FileInputStream ins = new FileInputStream(f);
        try {
            return create(f, ins, f.getName());
        } finally {
            ins.close();
        }
    }

    private static final int mimetypeZipEndOffset = 250;

    // ATTN ins is *not* closed if f != null
    private static ODPackage create(final File f, InputStream ins, final String name) throws IOException {
        // first use extension
        final Tuple2<ContentTypeVersioned, Boolean> fromExt = name != null ? ContentTypeVersioned.fromExtension(FileUtils.getExtension(name)) : Tuple2.<ContentTypeVersioned, Boolean> nullInstance();
        ContentTypeVersioned contentType = fromExt.get0();
        Boolean flat = fromExt.get1();
        // then content
        if (flat == null) {
            ins = new BufferedInputStream(ins);
            final String xmlStart = "<?xml";
            if (ins.markSupported())
                ins.mark(Math.max(xmlStart.length(), mimetypeZipEndOffset));
            else
                throw new IllegalStateException("Mark unsupported on " + ins);
            final byte[] buffer = new byte[xmlStart.length()];
            ins.read(buffer);
            if (xmlStart.equals(new String(buffer, StringUtils.ASCII))) {
                // would have to parse the whole document
                contentType = null;
                flat = true;
            } else {
                ins.reset();
                contentType = getType(ins);
                if (contentType != null)
                    flat = false;
            }
            ins.reset();
        }
        final ODPackage res;
        if (flat == null) {
            res = null;
        } else if (flat) {
            try {
                res = (f != null ? ODSingleXMLDocument.createFromFile(f) : ODSingleXMLDocument.createFromStream(ins)).getPackage();
            } catch (JDOMException e) {
                throw new IOException(e);
            }
        } else {
            res = f != null ? new ODPackage(f) : new ODPackage(ins);
        }
        assert contentType == null || contentType == res.getContentType();
        return res;
    }

    private static ContentTypeVersioned getType(final InputStream in) throws IOException {
        // don't want to close underlying stream
        @SuppressWarnings("resource")
        final DataInputStream ins = new DataInputStream(in, true);
        if (ins.read() != 'P' || ins.read() != 'K')
            return null;
        if (ins.skip(16) != 16)
            return null;
        final int compressedSize = ins.readInt();
        final int uncompressedSize = ins.readInt();
        // not a valid package and beyond that we would need to actually inflate the data
        if (compressedSize != uncompressedSize)
            return null;
        final short fnameLength = ins.readShort();
        if (fnameLength != MIMETYPE_ENTRY.length())
            return null;
        final short extraLength = ins.readShort();
        final byte[] array = new byte[Math.max(fnameLength, compressedSize)];
        ins.read(array, 0, fnameLength);
        if (!new String(array, 0, fnameLength, StringUtils.ASCII).equals(MIMETYPE_ENTRY))
            return null;
        if (ins.skip(extraLength) != extraLength)
            return null;
        ins.read(array, 0, compressedSize);
        final String data = new String(array, 0, compressedSize, MIMETYPE_ENC);
        return ContentTypeVersioned.fromMime(data);
    }

    static private XMLVersion getVersion(final XMLFormatVersion fv, final ContentTypeVersioned ct) {
        final XMLVersion v;
        if (ct == null && fv == null)
            v = null;
        else if (ct != null)
            v = ct.getVersion();
        else
            v = fv.getXMLVersion();
        assert fv == null || ct == null || fv.getXMLVersion() == ct.getVersion();
        return v;
    }

    static private <T> void checkVersion(final Class<T> clazz, final String s, final String entry, final T actual, final T required) {
        if (actual != null && required != null) {
            final boolean ok;
            if (actual instanceof ContentTypeVersioned) {
                // we can change our template status since it doesn't affect our content
                ok = ((ContentTypeVersioned) actual).getNonTemplate().equals(((ContentTypeVersioned) required).getNonTemplate());
            } else {
                ok = actual.equals(required);
            }
            if (!ok)
                throw new IllegalArgumentException(entry + " would change " + s + " from " + required + " to " + actual);
        }
    }

    private final Map<String, ODPackageEntry> files;
    private ContentTypeVersioned type;
    private XMLFormatVersion version;
    private ODMeta meta;
    private File file;
    private ODDocument doc;

    public ODPackage() {
        this.files = new HashMap<String, ODPackageEntry>();
        this.type = null;
        this.version = null;
        this.meta = null;
        this.file = null;
        this.doc = null;
    }

    /**
     * Read from the input stream into memory and close it.
     * 
     * @param ins the package.
     * @throws IOException if <code>ins</code> couldn't be read.
     */
    public ODPackage(InputStream ins) throws IOException {
        this();

        final ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        new ZippedFilesProcessor() {
            @Override
            protected void processEntry(ZipEntry entry, InputStream in) throws IOException {
                final String name = entry.getName();
                final Object res;
                if (subdocNames.contains(name)) {
                    try {
                        res = OOUtils.getBuilder().build(in);
                    } catch (JDOMException e) {
                        // always correct
                        throw new IllegalStateException("parse error", e);
                    }
                } else {
                    out.reset();
                    StreamUtils.copy(in, out);
                    res = out.toByteArray();
                }
                // we don't know yet the types
                putFile(name, res, null, entry.getMethod() == ZipEntry.DEFLATED);
            }
        }.process(ins);
        // fill in the missing types from the manifest, if any
        final ODPackageEntry me = this.files.remove(Manifest.ENTRY_NAME);
        if (me != null) {
            final byte[] m = (byte[]) me.getData();
            try {
                final Map<String, String> manifestEntries = Manifest.parse(new ByteArrayInputStream(m));
                for (final Map.Entry<String, String> e : manifestEntries.entrySet()) {
                    final String path = e.getKey();
                    final String type = e.getValue();
                    final ODPackageEntry entry = this.files.get(path);
                    // eg directory
                    if (entry == null) {
                        this.files.put(path, new ODPackageEntry(path, type, null));
                        // subdocs are already parsed to ODXMLDocument
                    } else if (type.equals(FileUtils.XML_TYPE) && entry.getData() instanceof byte[]) {
                        final Document doc = OOUtils.getBuilder().build(new ByteArrayInputStream((byte[]) entry.getData()));
                        this.putFile(path, doc, type, entry.isCompressed());
                    } else {
                        entry.setType(type);
                    }
                }
            } catch (JDOMException e) {
                throw new IllegalArgumentException("bad manifest " + new String(m), e);
            }
        }
    }

    public ODPackage(File f) throws IOException {
        this(new BufferedInputStream(new FileInputStream(f), 512 * 1024));
        this.file = f;
    }

    public ODPackage(ODPackage o) {
        this();
        for (final String name : o.getEntries()) {
            final ODPackageEntry entry = o.getEntry(name);
            this.putCopy(entry);
        }
        this.type = o.type;
        this.version = o.version;
        this.meta = null;
        this.file = o.file;
        this.doc = null;
    }

    public final File getFile() {
        return this.file;
    }

    public final void setFile(File f) {
        this.file = this.addExt(f);
    }

    private final File addExt(File f) {
        return this.getContentType().addExt(f, false);
    }

    /**
     * The version of this package, <code>null</code> if it cannot be found (eg this package is
     * empty, or contains no xml).
     * 
     * @return the version of this package, can be <code>null</code>.
     */
    public final XMLVersion getVersion() {
        return getVersion(this.version, this.type);
    }

    public final XMLFormatVersion getFormatVersion() {
        return this.version;
    }

    /**
     * The type of this package, <code>null</code> if it cannot be found (eg this package is empty).
     * 
     * @return the type of this package, can be <code>null</code>.
     */
    public final ContentTypeVersioned getContentType() {
        return this.type;
    }

    public final void setContentType(final ContentTypeVersioned newType) {
        this.putFile(MIMETYPE_ENTRY, newType.getMimeType().getBytes(MIMETYPE_ENC));
    }

    private void updateTypeAndVersion(final String entry, ODXMLDocument xml) {
        this.setTypeAndVersion(entry.equals(CONTENT.getZipEntry()) ? ContentTypeVersioned.fromContent(xml) : null, xml.getFormatVersion(), entry);
    }

    private void updateTypeAndVersion(byte[] mimetype) {
        this.setTypeAndVersion(ContentTypeVersioned.fromMime(mimetype), null, MIMETYPE_ENTRY);
    }

    private final void setTypeAndVersion(final ContentTypeVersioned ct, final XMLFormatVersion fv, final String entry) {
        final Tuple3<XMLVersion, ContentTypeVersioned, XMLFormatVersion> requiredByPkg = this.getRequired(entry);
        if (requiredByPkg != null) {
            checkVersion(XMLVersion.class, "version", entry, getVersion(fv, ct), requiredByPkg.get0());
            checkVersion(ContentTypeVersioned.class, "type", entry, ct, requiredByPkg.get1());
            checkVersion(XMLFormatVersion.class, "format version", entry, fv, requiredByPkg.get2());
        }

        // since we're adding "entry" never set attributes to null
        if (fv != null && !fv.equals(this.version))
            this.version = fv;
        // don't let non-template from content overwrite the correct one
        if (ct != null && !ct.equals(this.type) && (this.type == null || entry.equals(MIMETYPE_ENTRY)))
            this.type = ct;
    }

    // find the versions required by the package without the passed entry
    private final Tuple3<XMLVersion, ContentTypeVersioned, XMLFormatVersion> getRequired(final String entryToIgnore) {
        if (this.files.size() == 0 || (this.files.size() == 1 && this.files.containsKey(entryToIgnore)))
            return null;

        final byte[] mimetype;
        if (this.files.containsKey(MIMETYPE_ENTRY) && !MIMETYPE_ENTRY.equals(entryToIgnore)) {
            mimetype = this.getBinaryFile(MIMETYPE_ENTRY);
        } else {
            mimetype = null;
        }
        XMLFormatVersion fv = null;
        final Map<String, Object> versionFiles = new HashMap<String, Object>();
        for (final String e : subdocNames) {
            if (this.files.containsKey(e) && !e.equals(entryToIgnore)) {
                final ODXMLDocument xmlFile = this.getXMLFile(e);
                versionFiles.put(e, xmlFile);
                if (fv == null)
                    fv = xmlFile.getFormatVersion();
                else
                    assert fv.equals(xmlFile.getFormatVersion()) : "Incoherence";
            }
        }
        final ODXMLDocument content = (ODXMLDocument) versionFiles.get(CONTENT.getZipEntry());

        final ContentTypeVersioned ct;
        if (mimetype != null)
            ct = ContentTypeVersioned.fromMime(mimetype);
        else if (content != null)
            ct = ContentTypeVersioned.fromContent(content);
        else
            ct = null;

        return Tuple3.create(getVersion(fv, ct), ct, fv);
    }

    public final String getMimeType() {
        return this.getContentType().getMimeType();
    }

    public final boolean isTemplate() {
        return this.getContentType().isTemplate();
    }

    public final void setTemplate(boolean b) {
        if (this.type == null)
            throw new IllegalStateException("No type");
        final ContentTypeVersioned newType = b ? this.type.getTemplate() : this.type.getNonTemplate();
        if (newType == null)
            throw new IllegalStateException("Missing " + (b ? "" : "non-") + "template for " + this.type);
        this.setContentType(newType);
    }

    /**
     * Call {@link Validator#isValid()} on each XML subdocuments.
     * 
     * @return all problems indexed by package entry names, i.e. empty if all OK, <code>null</code>
     *         if validation couldn't occur.
     */
    public final Map<String, String> validateSubDocuments() {
        return this.validateSubDocuments(true, true);
    }

    public final Map<String, String> validateSubDocuments(final boolean allowChangeToValidate, final boolean ignoreForeign) {
        final OOXML ooxml = this.getFormatVersion().getXML();
        if (!ooxml.canValidate())
            return null;
        final Map<String, String> res = new HashMap<String, String>();
        for (final String s : subdocNames) {
            final Document doc = this.getDocument(s);
            if (doc != null) {
                if (allowChangeToValidate) {
                    // OpenOffice do not generate DocType declaration
                    final DocType docType = RootElement.fromDocument(doc).createDocType(ooxml.getVersion());
                    if (docType != null && doc.getDocType() == null)
                        doc.setDocType(docType);
                }
                final String valid = ooxml.getValidator(doc, ignoreForeign).isValid();
                if (valid != null)
                    res.put(s, valid);
            }
        }
        final String valid = ooxml.getValidator(this.createManifest().getDocument(), ignoreForeign).isValid();
        if (valid != null)
            res.put(Manifest.ENTRY_NAME, valid);
        return res;
    }

    public final ODDocument getODDocument() {
        // cache ODDocument otherwise a second one can modify the XML (e.g. remove rows) without the
        // first one knowing
        if (this.doc == null) {
            final ContentType ct = this.getContentType().getType();
            if (ct.equals(ContentType.SPREADSHEET))
                this.doc = SpreadSheet.get(this);
            else if (ct.equals(ContentType.TEXT))
                this.doc = TextDocument.get(this);
        }
        return this.doc;
    }

    public final boolean hasODDocument() {
        return this.doc != null;
    }

    public final SpreadSheet getSpreadSheet() {
        return (SpreadSheet) this.getODDocument();
    }

    public final TextDocument getTextDocument() {
        return (TextDocument) this.getODDocument();
    }

    // *** getter on files

    public final Set<String> getEntries() {
        return this.files.keySet();
    }

    public final ODPackageEntry getEntry(String entry) {
        return this.files.get(entry);
    }

    protected final Object getData(String entry) {
        final ODPackageEntry e = this.getEntry(entry);
        return e == null ? null : e.getData();
    }

    public final byte[] getBinaryFile(String entry) {
        return (byte[]) this.getData(entry);
    }

    public final ODXMLDocument getXMLFile(String xmlEntry) {
        return (ODXMLDocument) this.getData(xmlEntry);
    }

    public final ODXMLDocument getXMLFile(final Document doc) {
        for (final String s : subdocNames) {
            final ODXMLDocument xmlFile = getXMLFile(s);
            if (xmlFile != null && xmlFile.getDocument() == doc) {
                return xmlFile;
            }
        }
        return null;
    }

    /**
     * The XML document where are located the common styles.
     * 
     * @return the document where are located styles.
     */
    public final ODXMLDocument getStyles() {
        final ODXMLDocument res;
        if (this.isSingle())
            res = this.getContent();
        else {
            res = this.getXMLFile(STYLES.getZipEntry());
        }
        return res;
    }

    public final ODXMLDocument getContent() {
        return this.getXMLFile(CONTENT.getZipEntry());
    }

    public final ODMeta getMeta() {
        return this.getMeta(false);
    }

    public final ODMeta getMeta(final boolean create) {
        if (this.meta == null) {
            if (this.isSingle()) {
                this.meta = ODMeta.create(this.getContent(), create);
            } else {
                final String metaEntry = META.getZipEntry();
                ODXMLDocument xmlFile = this.getXMLFile(metaEntry);
                if (xmlFile == null && create) {
                    this.putFile(metaEntry, RootElement.META.createDocument(getFormatVersion()));
                    xmlFile = this.getXMLFile(metaEntry);
                }
                if (xmlFile != null) {
                    this.meta = ODMeta.create(xmlFile, create);
                }
            }
        }
        return this.meta;
    }

    /**
     * Parse BASIC libraries in this package.
     * 
     * @return the BASIC libraries by name.
     */
    public final Map<String, Library> readBasicLibraries() {
        if (this.isSingle())
            return ((ODSingleXMLDocument) this.getContent()).readBasicLibraries();

        // TODO read DIALOG_LIBRARY_LIST_FILENAME (to support Library with only dialogs)
        final Document doc = (Document) this.getData(Library.DIR_NAME + "/" + Library.LIBRARY_LIST_FILENAME);
        if (doc == null)
            return Collections.emptyMap();
        @SuppressWarnings("unchecked")
        final List<Element> librariesElems = doc.getRootElement().getChildren();
        final Map<String, Library> res = new HashMap<String, Library>(librariesElems.size());
        for (final Element libraryElem : librariesElems) {
            final Library lib = Library.fromPackage(libraryElem, this);
            if (res.put(lib.getName(), lib) != null)
                throw new IllegalStateException("Duplicate library named " + lib.getName());
        }
        return res;
    }

    /**
     * Add the passed libraries to this package. Passed libraries with the same content as existing
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
        if (pkg == this)
            return Collections.emptySet();
        return this.addBasicLibraries(pkg.readBasicLibraries());
    }

    private final Set<String> addBasicLibraries(final Map<String, Library> oLibraries) {
        if (oLibraries.size() == 0)
            return Collections.emptySet();
        if (this.isSingle())
            return ((ODSingleXMLDocument) this.getContent()).addBasicLibraries(oLibraries);

        final Map<String, Library> thisLibraries = this.readBasicLibraries();
        // check that the libraries to add which are already in us can be merged (no elements
        // conflict)
        Library.canBeMerged(thisLibraries, oLibraries);

        // merge
        for (final Library oLib : oLibraries.values()) {
            // can be null
            final Library thisLib = thisLibraries.get(oLib.getName());
            oLib.mergeModules(this, thisLib);
            oLib.mergeDialogs(this, thisLib);
        }

        final Set<String> newLibs = new HashSet<String>(oLibraries.keySet());
        newLibs.removeAll(thisLibraries.keySet());
        return newLibs;
    }

    /**
     * Remove the passed libraries.
     * 
     * @param libraries which libraries to remove.
     * @return the actually removed libraries.
     */
    public final Set<String> removeBasicLibraries(final Collection<String> libraries) {
        if (libraries.size() == 0)
            return Collections.emptySet();
        if (this.isSingle())
            return ((ODSingleXMLDocument) this.getContent()).removeBasicLibraries(libraries);

        final Set<String> res = new HashSet<String>();
        for (final String libToRm : libraries) {
            if (Library.removeFromPackage(this, libToRm))
                res.add(libToRm);
        }
        return res;
    }

    /**
     * Parse events for the whole document.
     * 
     * @return event listeners by event name.
     */
    public final Map<String, EventListener> readEventListeners() {
        final OOXML xml = getFormatVersion().getXML();
        final Element scriptsElem = this.getContent().getChild(xml.getOfficeScripts(), false);
        final Element eventListeners = scriptsElem == null ? null : scriptsElem.getChild(xml.getOfficeEventListeners(), getVersion().getOFFICE());
        if (eventListeners == null)
            return Collections.emptyMap();

        final Map<String, EventListener> res = new HashMap<String, EventListener>();
        final Namespace scriptNS = getVersion().getNS("script");
        @SuppressWarnings("unchecked")
        final List<Element> listeners = eventListeners.getChildren(xml.getEventListener(), scriptNS);
        for (final Element listener : listeners) {
            final EventListener l = new EventListener(listener);
            res.put(l.getName(), l);
        }
        return res;
    }

    /**
     * Return an XML document.
     * 
     * @param xmlEntry the filename, eg "styles.xml".
     * @return the matching document, or <code>null</code> if there's none.
     * @throws JDOMException if error about the XML.
     * @throws IOException if an error occurs while reading the file.
     */
    public Document getDocument(String xmlEntry) {
        final ODXMLDocument xml = this.getXMLFile(xmlEntry);
        return xml == null ? null : xml.getDocument();
    }

    /**
     * Find the passed automatic or common style referenced from the content.
     * 
     * @param desc the family, eg <code>StyleStyleDesc&lt;ParagraphStyle&gt;</code>.
     * @param name the name, eg "P1".
     * @return the corresponding XML element.
     */
    public final Element getStyle(final StyleDesc<?> desc, final String name) {
        return this.getStyle(this.getContent().getDocument(), desc, name);
    }

    /**
     * Find the passed automatic or common style. NOTE : <code>referent</code> is needed because
     * there can exist automatic styles with the same name in both "content.xml" and "styles.xml".
     * 
     * @param referent the document referencing the style.
     * @param desc the family, eg <code>StyleStyleDesc&lt;ParagraphStyle&gt;</code>.
     * @param name the name, eg "P1".
     * @return the corresponding XML element.
     * @see ODXMLDocument#getStyle(StyleDesc, String, Document)
     */
    public final Element getStyle(final Document referent, final StyleDesc<?> desc, final String name) {
        // avoid searching in content then styles if it cannot be found
        if (name == null)
            return null;

        String refSubDoc = null;
        ODXMLDocument refXMLFile = null;
        final String[] stylesContainer = new String[] { CONTENT.getZipEntry(), STYLES.getZipEntry() };
        for (final String subDoc : stylesContainer) {
            final ODXMLDocument xmlFile = this.getXMLFile(subDoc);
            if (xmlFile != null && xmlFile.getDocument() == referent) {
                refSubDoc = subDoc;
                refXMLFile = xmlFile;
                break;
            }
        }
        if (refSubDoc == null)
            throw new IllegalArgumentException("neither in content nor styles : " + referent);

        Element res = refXMLFile.getStyle(desc, name, referent);
        // if it isn't in content.xml it might be in styles.xml
        if (res == null && refSubDoc.equals(stylesContainer[0])) {
            final ODXMLDocument stylesXMLFile = this.getXMLFile(stylesContainer[1]);
            if (stylesXMLFile != null)
                res = stylesXMLFile.getStyle(desc, name, referent);
        }
        return res;
    }

    public final Element getDefaultStyle(final StyleStyleDesc<?> desc, final boolean create) {
        // from 16.4 of OpenDocument-v1.2-cs01-part1, default-style only usable in office:styles
        return getStyles().getDefaultStyle(desc, create);
    }

    /**
     * Verify that styles referenced by this document are indeed defined. NOTE this method is not
     * perfect : not all problems are detected.
     * 
     * @return <code>null</code> if no problem has been found, else a String describing it.
     */
    public final String checkStyles() {
        final ODXMLDocument stylesDoc = this.getStyles();
        final ODXMLDocument contentDoc = this.getContent();
        final Element styles;
        if (stylesDoc != null) {
            styles = stylesDoc.getChild("styles");
            // check styles.xml
            final String res = checkStyles(stylesDoc, styles);
            if (res != null)
                return res;
        } else {
            styles = contentDoc.getChild("styles");
        }

        // check content.xml
        return checkStyles(contentDoc, styles);
    }

    static private final String checkStyles(ODXMLDocument doc, Element styles) {
        try {
            final SetMap<String, String> stylesNames = getStylesNames(doc, styles, doc.getChild("automatic-styles"));
            // text:style-name : text:p, text:span
            // table:style-name : table:table, table:row, table:column, table:cell
            // draw:style-name : draw:text-box
            // style:data-style-name : <style:style style:family="table-cell">
            // TODO check by family
            final Set<String> names = new HashSet<String>(stylesNames.allValues());
            final Iterator attrs = doc.getXPath(".//@text:style-name | .//@table:style-name | .//@draw:style-name | .//@style:data-style-name | .//@style:list-style-name")
                    .selectNodes(doc.getDocument()).iterator();
            while (attrs.hasNext()) {
                final Attribute attr = (Attribute) attrs.next();
                if (!names.contains(attr.getValue()))
                    return "unknown style referenced by " + attr.getName() + " in " + JDOMUtils.output(attr.getParent());
            }
            // TODO check other references like page-*-name (§3 of #prefix())
        } catch (IllegalStateException e) {
            return ExceptionUtils.getStackTrace(e);
        } catch (JDOMException e) {
            return ExceptionUtils.getStackTrace(e);
        }
        return null;
    }

    static private final SetMap<String, String> getStylesNames(final ODXMLDocument doc, final Element styles, final Element autoStyles) throws IllegalStateException {
        // section 14.1 § Style Name : style:family + style:name is unique
        final SetMap<String, String> res = new SetMap<String, String>();

        final List<Element> nodes = new ArrayList<Element>();
        if (styles != null)
            nodes.add(styles);
        if (autoStyles != null)
            nodes.add(autoStyles);

        try {
            {
                final Iterator iter = doc.getXPath("./style:style/@style:name").selectNodes(nodes).iterator();
                while (iter.hasNext()) {
                    final Attribute attr = (Attribute) iter.next();
                    final String styleName = attr.getValue();
                    final String family = attr.getParent().getAttributeValue("family", attr.getNamespace());
                    if (res.getNonNull(family).contains(styleName))
                        throw new IllegalStateException("duplicate style in " + family + " :  " + styleName);
                    res.add(family, styleName);
                }
            }
            {
                final List<String> dataStyles = Arrays.asList("number-style", "currency-style", "percentage-style", "date-style", "time-style", "boolean-style", "text-style");
                final String xpDataStyles = org.jopendocument.util.CollectionUtils.join(dataStyles, " | ", new ITransformer<String, String>() {
                    @Override
                    public String transformChecked(String input) {
                        return "./number:" + input;
                    }
                });
                final Iterator listIter = doc.getXPath("./text:list-style | " + xpDataStyles).selectNodes(nodes).iterator();
                while (listIter.hasNext()) {
                    final Element elem = (Element) listIter.next();
                    res.add(elem.getQualifiedName(), elem.getAttributeValue("name", doc.getVersion().getSTYLE()));
                }
            }
        } catch (JDOMException e) {
            throw new IllegalStateException(e);
        }
        return res;
    }

    // *** setter

    public void putFile(String entry, Object data) {
        this.putFile(entry, data, null);
    }

    public void putFile(final String entry, final Object data, final String mediaType) {
        this.putFile(entry, data, mediaType, true);
    }

    public void putFile(final String entry, final Object data, final String mediaType, final boolean compress) {
        if (entry == null)
            throw new NullPointerException("null name");
        if (data == null) {
            this.rmFile(entry);
            return;
        }
        final Object myData;
        if (subdocNames.contains(entry)) {
            final ODXMLDocument oodoc;
            if (data instanceof Document)
                oodoc = ODXMLDocument.create((Document) data);
            else
                oodoc = (ODXMLDocument) data;
            checkEntryForDocument(entry);
            this.updateTypeAndVersion(entry, oodoc);
            myData = oodoc;
        } else if (data instanceof Document) {
            myData = data;
        } else if (!(data instanceof byte[])) {
            throw new IllegalArgumentException("should be byte[] for " + entry + ": " + data);
        } else {
            if (entry.equals(MIMETYPE_ENTRY))
                this.updateTypeAndVersion((byte[]) data);
            myData = data;
        }
        final String inferredType = mediaType != null ? mediaType : FileUtils.findMimeType(entry);
        this.files.put(entry, new ODPackageEntry(entry, inferredType, myData, compress));
    }

    // Perhaps add a clearODDocument() method to set doc to null and in ODDocument set pkg to null
    // (after having verified !hasDocument()). For now just copy the package.
    private void checkEntryForDocument(final String entry) {
        if (this.hasODDocument() && (entry.equals(RootElement.CONTENT.getZipEntry()) || entry.equals(RootElement.STYLES.getZipEntry())))
            throw new IllegalArgumentException("Cannot change content or styles with existing ODDocument");
    }

    public final void putCopy(final ODPackageEntry entry) {
        this.putCopy(entry, entry.getName());
    }

    public final void putCopy(final ODPackageEntry entry, final String entryName) {
        // ATTN this works because, all files are read upfront
        final Object data = entry.getData();
        final Object myData;
        if (data instanceof byte[]) {
            // assume byte[] are immutable
            myData = data;
        } else if (data instanceof ODSingleXMLDocument) {
            myData = new ODSingleXMLDocument((ODSingleXMLDocument) data, this);
        } else {
            myData = CopyUtils.copy(data);
        }
        this.putFile(entryName, myData, entry.getType(), entry.isCompressed());
    }

    public void rmFile(String entry) {
        this.checkEntryForDocument(entry);
        this.files.remove(entry);
        if (entry.equals(MIMETYPE_ENTRY) || subdocNames.contains(entry)) {
            final Tuple3<XMLVersion, ContentTypeVersioned, XMLFormatVersion> required = this.getRequired(entry);
            this.type = required == null ? null : required.get1();
            this.version = required == null ? null : required.get2();
        }
    }

    public final void rmFiles(Collection<String> entries) {
        for (final String entry : entries)
            this.rmFile(entry);
    }

    public void clear() {
        this.files.clear();
        this.type = null;
        this.version = null;
    }

    /**
     * Transform this to use a {@link ODSingleXMLDocument}. Ie after this method, only "content.xml"
     * remains and it's an instance of ODSingleXMLDocument.
     * 
     * @return the created ODSingleXMLDocument.
     */
    public ODSingleXMLDocument toSingle() {
        if (!this.isSingle()) {
            this.meta = null;
            return ODSingleXMLDocument.create(this);
        } else {
            return (ODSingleXMLDocument) this.getContent();
        }
    }

    public final boolean isSingle() {
        return this.getContent() instanceof ODSingleXMLDocument;
    }

    /**
     * Split the {@link RootElement#SINGLE_CONTENT}. If this was {@link #isSingle() single} the
     * former {@link #getContent() content} won't be useable anymore, you can check it with
     * {@link ODSingleXMLDocument#isDead()}.
     * 
     * @return <code>true</code> if this was modified.
     */
    public final boolean split() {
        final boolean res;
        if (this.isSingle()) {
            // store now, as split() empties us
            final XMLFormatVersion version = getFormatVersion();
            final Map<RootElement, Document> split = ((ODSingleXMLDocument) this.getContent()).split();
            // from 22.2.1 (D1.1.2) of OpenDocument-v1.2-part1-cd04
            assert (split.containsKey(RootElement.CONTENT) || split.containsKey(RootElement.STYLES)) && RootElement.getPackageElements().containsAll(split.keySet()) : "wrong elements " + split;
            for (final Entry<RootElement, Document> e : split.entrySet()) {
                this.putFile(e.getKey().getZipEntry(), new ODXMLDocument(e.getValue(), version));
            }
            this.meta = null;
            res = true;
        } else {
            res = false;
        }
        assert !this.isSingle();
        return res;
    }

    // *** save

    private final Manifest createManifest() {
        try {
            return this.createManifest(null);
        } catch (IOException e) {
            // shouldn't happen since we're not writing
            throw new IllegalStateException(e);
        }
    }

    private final Manifest createManifest(final Zip z) throws IOException {
        final Manifest manifest = new Manifest(this.getFormatVersion(), this.getMimeType());
        final XMLOutputter outputter = z == null ? null : createOutputter();
        for (final String name : this.files.keySet()) {
            // added at the end
            if (name.equals(MIMETYPE_ENTRY) || name.equals(Manifest.ENTRY_NAME))
                continue;

            final ODPackageEntry entry = this.files.get(name);
            if (z != null) {
                final Object val = entry.getData();
                if (val != null) {
                    if (val instanceof ODXMLDocument) {
                        final OutputStream o = z.createEntry(name);
                        outputter.output(((ODXMLDocument) val).getDocument(), o);
                        o.close();
                    } else if (val instanceof Document) {
                        final OutputStream o = z.createEntry(name);
                        outputter.output((Document) val, o);
                        o.close();
                    } else {
                        z.zip(name, (byte[]) val, entry.isCompressed());
                    }
                }
            }
            final String mediaType = entry.getType();
            manifest.addEntry(name, mediaType == null ? "" : mediaType);
        }

        return manifest;
    }

    /**
     * Save this package to the passed stream.
     * 
     * @param out the stream to write to, it will be closed.
     * @throws IOException if an error occurs.
     */
    public final void save(OutputStream out) throws IOException {
        // from 22.2.1 (D1.2)
        if (this.isSingle()) {
            // assert we can use this copy constructor (instead of the slower CopyUtils)
            assert this.getClass() == ODPackage.class;
            final ODPackage copy = new ODPackage(this);
            copy.split();
            copy.save(out);
            return;
        }

        // set the generator
        ProductInfo productInfo = ProductInfo.getInstance();
        if (productInfo == null) {
            // do *not* use "/product.properties" as it might interfere with products using this
            // framework
            final Properties props = PropertiesUtils.createFromResource(this.getClass(), "product.properties");
            props.put(ProductInfo.NAME, this.getClass().getName());
            productInfo = new ProductInfo(props);
        }
        final String generator;
        if (productInfo.getVersion() == null)
            generator = productInfo.getName();
        else
            generator = productInfo.getName() + "/" + productInfo.getVersion();
        this.getMeta(true).setGenerator(generator);
        // we could update almost all statistics (table count, paragraph count, ...) but the most
        // important one for opening times is page count
        this.getMeta().removeMetaChild("document-statistic");
        final String pageCount = getPageCount();
        if (pageCount != null && getContentType() != null && ContentType.TEXT.equals(getContentType().getType()))
            this.getMeta().getMetaChild("document-statistic").setAttribute("page-count", pageCount, getVersion().getMETA());

        final Zip z = new Zip(out);

        // magic number, see section 17.4
        z.zipNonCompressed(MIMETYPE_ENTRY, this.getMimeType().getBytes(MIMETYPE_ENC));

        final Manifest manifest = createManifest(z);

        z.zip(Manifest.ENTRY_NAME, new StringInputStream(manifest.asString()));
        z.close();
    }

    /**
     * Save the content of this package to our file, overwriting it if it exists.
     * 
     * @return the saved file.
     * @throws IOException if an error occurs while saving.
     */
    public File save() throws IOException {
        return this.saveAs(this.getFile());
    }

    public File saveAs(final File fNoExt) throws IOException {
        final File f = this.addExt(fNoExt);
        if (f.getParentFile() != null)
            f.getParentFile().mkdirs();
        // ATTN at this point, we must have read all the content of this file
        // otherwise we could save to File.createTempFile("oofd", null).deleteOnExit();
        final FileOutputStream out = new FileOutputStream(f);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out, 512 * 1024);
        try {
            this.save(bufferedOutputStream);
        } finally {
            bufferedOutputStream.close();
        }
        return f;
    }
}
