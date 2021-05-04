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

import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.CompareUtils;
import org.jopendocument.util.FileUtils;
import org.jopendocument.util.StringUtils;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.SimpleXMLPath;
import org.jopendocument.util.Step;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A BASIC script library.
 * 
 * @author Sylvain
 */
public abstract class Library {

    static public final String DIR_NAME = "Basic";
    static public final String DIALOG_DIR_NAME = "Dialogs";
    static public final String LIBRARY_LIST_FILENAME = "script-lc.xml";
    static public final String MODULE_LIST_FILENAME = "script-lb.xml";
    static public final String DIALOG_LIBRARY_LIST_FILENAME = "dialog-lc.xml";
    static public final String DIALOG_LIST_FILENAME = "dialog-lb.xml";

    static final Map<String, Library> toMap(final Collection<? extends Library> libraries) {
        final Map<String, Library> res = new HashMap<String, Library>(libraries.size());
        for (final Library l : libraries) {
            if (res.put(l.getName(), l) != null)
                throw new IllegalArgumentException("Duplicate library named " + l.getName());
        }
        return res;
    }

    static final Set<String> canBeMerged(final Map<String, Library> libraries1, final Map<String, Library> libraries2) {
        final Set<String> duplicateLibs = CollectionUtils.inter(libraries1.keySet(), libraries2.keySet());
        for (final String duplicateLib : duplicateLibs) {
            final Library thisLib = libraries1.get(duplicateLib);
            final Library oLib = libraries2.get(duplicateLib);
            if (!thisLib.canBeMerged(oLib))
                throw new IllegalArgumentException("Incompatible library : " + duplicateLib);
        }
        return duplicateLibs;
    }

    // dialogs aren't included in flat XML, but if it was created from a package we kept the dialogs
    static final Library fromFlatXML(final Element libElem, final ODPackage pkg, final Namespace linkNS) {
        final Namespace libNS = libElem.getNamespace();
        final String libName = libElem.getAttributeValue("name", libNS);
        final Library res;
        if (libElem.getName().equals(LinkedLibrary.FLAT_XML_ELEMENT_NAME)) {
            res = new LinkedLibrary(libName, libElem.getAttributeValue("href", linkNS), libElem.getAttributeValue("type", linkNS));
        } else if (libElem.getName().equals(EmbeddedLibrary.FLAT_XML_ELEMENT_NAME)) {
            @SuppressWarnings("unchecked")
            final List<Element> moduleElems = libElem.getChildren("module", libNS);
            final Map<String, String> modules = new HashMap<String, String>();
            for (final Element moduleElem : moduleElems) {
                final String moduleName = moduleElem.getAttributeValue("name", libNS);
                if (modules.containsKey(moduleName))
                    throw new IllegalStateException("Duplicate module named " + moduleName);
                modules.put(moduleName, moduleElem.getChild("source-code", libNS).getText());
            }
            final boolean readonly = StyleProperties.parseBoolean(libElem.getAttributeValue("readonly", libNS), false);
            final boolean passwordprotected = StyleProperties.parseBoolean(libElem.getAttributeValue("passwordprotected", libNS), false);

            // dialogs aren't included in flat XML
            final LB_Content<Element> dialogs = DIALOGS_CODEC.decode(pkg, libName);
            final Map<String, Element> dialogsMap = dialogs == null ? Collections.<String, Element> emptyMap() : dialogs.getMap();

            res = new EmbeddedLibrary(libName, readonly, passwordprotected, modules, dialogsMap);
        } else {
            res = null;
        }
        assert res == null || libName.equals(res.getName());
        return res;
    }

    static final Library fromPackage(final Element libraryElem, final ODPackage pkg) {
        final String libName = libraryElem.getAttributeValue("name", libraryElem.getNamespace());
        final boolean link = Boolean.parseBoolean(libraryElem.getAttributeValue("link", libraryElem.getNamespace()));
        final Library res;
        if (link) {
            final Namespace linkNS = pkg.getVersion().getNS("xlink");
            res = new LinkedLibrary(libName, libraryElem.getAttributeValue("href", linkNS), libraryElem.getAttributeValue("type", linkNS));
        } else {
            final LB_Content<String> modules = MODULES_CODEC.decode(pkg, libName);
            final LB_Content<Element> dialogs = DIALOGS_CODEC.decode(pkg, libName);
            final Map<String, Element> dialogsMap;
            if (dialogs == null) {
                dialogsMap = Collections.<String, Element> emptyMap();
            } else {
                if (modules.isReadonly() != dialogs.isReadonly() || modules.isPasswordProtected() != dialogs.isPasswordProtected())
                    throw new IllegalStateException("Properties mismatch between modules and dialogs");
                dialogsMap = dialogs.getMap();
            }
            res = new EmbeddedLibrary(libName, modules.isReadonly(), modules.isPasswordProtected(), modules.getMap(), dialogsMap);
        }
        assert libName.equals(res.getName());
        return res;
    }

    static final public boolean removeFromPackage(final ODPackage pkg, final String libName) {
        return MODULES_CODEC.removeFromPackage(pkg, libName) || DIALOGS_CODEC.removeFromPackage(pkg, libName);
    }

    static private final class LB_Content<V> {
        private final boolean readonly;
        private final boolean passwordProtected;
        private final Map<String, V> map;

        private LB_Content(boolean readonly, boolean passwordProtected, Map<String, V> map) {
            super();
            this.readonly = readonly;
            this.passwordProtected = passwordProtected;
            this.map = map;
        }

        public final boolean isReadonly() {
            return this.readonly;
        }

        public final boolean isPasswordProtected() {
            return this.passwordProtected;
        }

        public final Map<String, V> getMap() {
            return this.map;
        }
    }

    static private abstract class LB_Codec<V> {

        private LB_Content<V> decode(final ODPackage pkg, final String libName) {
            final Document libDoc = (Document) pkg.getData(getDirName() + "/" + libName + "/" + getTOCName());
            if (libDoc == null)
                return null;
            final Element libraryElemDetail = libDoc.getRootElement();
            final String libDetailName = libraryElemDetail.getAttributeValue("name", libraryElemDetail.getNamespace());
            if (!libName.equals(libDetailName))
                throw new IllegalStateException("name mismatch : " + libName + " != " + libDetailName);
            final boolean readonly = Boolean.parseBoolean(libraryElemDetail.getAttributeValue("readonly", libraryElemDetail.getNamespace()));
            final boolean passwordProtected = Boolean.parseBoolean(libraryElemDetail.getAttributeValue("passwordprotected", libraryElemDetail.getNamespace()));

            @SuppressWarnings("unchecked")
            final List<Element> modulesElems = libraryElemDetail.getChildren();
            final Map<String, V> modules = new HashMap<String, V>();
            for (final Element moduleElem : modulesElems) {
                final String moduleName = moduleElem.getAttributeValue("name", moduleElem.getNamespace());
                if (modules.containsKey(moduleName))
                    throw new IllegalStateException("Duplicate module named " + moduleName);
                final Document moduleData = (Document) pkg.getData(getDirName() + "/" + libName + "/" + moduleName + ".xml");
                modules.put(moduleName, getValue(moduleData));
            }
            return new LB_Content<V>(readonly, passwordProtected, modules);
        }

        private void encode(final EmbeddedLibrary l, final ODPackage destPkg, final EmbeddedLibrary destLibrary) {
            final Set<String> destElements;
            if (destLibrary == null) {
                destElements = Collections.emptySet();
            } else {
                destElements = this.getElements(destLibrary).keySet();
            }
            if (destElements.size() == 0) {
                // library list might not exist
                getLibraryInList(destPkg, l.getName(), l);
            }
            // add all elements not present
            final Document libDoc = (Document) destPkg.getData(getDirName() + '/' + l.getName() + '/' + getTOCName());
            // if libDoc is null, docsToAdd will contain one
            final Map<String, Document> docsToAdd = l.toPackageDocuments(destPkg.getFormatVersion(), this, libDoc, destElements);
            for (final Entry<String, Document> e : docsToAdd.entrySet()) {
                destPkg.putFile(getDirName() + '/' + l.getName() + '/' + e.getKey(), e.getValue(), FileUtils.XML_TYPE);
            }
        }

        private Element getLibraryInList(final ODPackage pkg, final String libName, final EmbeddedLibrary lib) {
            assert lib == null || libName.equals(lib.getName());
            final boolean create = lib != null;
            final String path = getDirName() + '/' + getLibraryListName();
            final Document doc = (Document) pkg.getData(path);
            final Element rootElement;
            Element res;
            if (doc == null) {
                if (!create)
                    return null;
                rootElement = new Element("libraries", XMLVersion.LIBRARY_NS);
                pkg.putFile(path, new Document(rootElement, new DocType("library:libraries", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "libraries.dtd")), FileUtils.XML_TYPE);
                res = null;
            } else {
                rootElement = doc.getRootElement();
                res = SimpleXMLPath.create(Step.createElementStep("library", null, new IPredicate<Element>() {
                    @Override
                    public boolean evaluateChecked(Element input) {
                        return input.getAttributeValue("name", input.getNamespace()).equals(libName);
                    }
                })).selectSingleNode(rootElement);
            }
            if (res == null && create) {
                res = lib.toPackageLibrariesElement(pkg.getFormatVersion());
                rootElement.addContent(res);
            }
            return res;
        }

        private boolean removeFromPackage(final ODPackage pkg, final String libName) {
            final Set<String> entriesToRm = new HashSet<String>();
            final Element libraryInList = getLibraryInList(pkg, libName, null);
            boolean res = false;
            if (libraryInList != null) {
                // if <libraries> is empty remove the file
                if (JDOMUtils.detachEmptyParent(libraryInList).getParent() == null) {
                    entriesToRm.add(getDirName() + '/' + getLibraryListName());
                }
                res = true;
            }
            final String libPath = getDirName() + '/' + libName;
            for (final String entry : pkg.getEntries()) {
                if (entry.equals(libPath) || entry.startsWith(libPath + '/'))
                    entriesToRm.add(entry);
            }
            if (entriesToRm.size() > 0) {
                pkg.rmFiles(entriesToRm);
                res = true;
            }
            return res;
        }

        protected abstract String getDirName();

        protected abstract String getLibraryListName();

        protected abstract String getTOCName();

        protected abstract DocType createElementDTD();

        protected abstract Map<String, V> getElements(final EmbeddedLibrary l);

        protected abstract V getValue(final Document moduleData);
    }

    static private final LB_Codec<String> MODULES_CODEC = new LB_Codec<String>() {
        @Override
        protected String getDirName() {
            return DIR_NAME;
        }

        @Override
        protected String getLibraryListName() {
            return LIBRARY_LIST_FILENAME;
        }

        @Override
        protected String getTOCName() {
            return MODULE_LIST_FILENAME;
        }

        @Override
        protected DocType createElementDTD() {
            return new DocType("script:module", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "module.dtd");
        }

        @Override
        protected Map<String, String> getElements(EmbeddedLibrary l) {
            return l.getModules();
        }

        @Override
        protected String getValue(final Document moduleData) {
            return moduleData.getRootElement().getText();
        }
    };

    static private final LB_Codec<Element> DIALOGS_CODEC = new LB_Codec<Element>() {
        @Override
        protected String getDirName() {
            return DIALOG_DIR_NAME;
        }

        @Override
        protected String getLibraryListName() {
            return DIALOG_LIBRARY_LIST_FILENAME;
        }

        @Override
        protected String getTOCName() {
            return DIALOG_LIST_FILENAME;
        }

        @Override
        protected DocType createElementDTD() {
            return new DocType("dlg:window", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "dialog.dtd");
        }

        @Override
        protected Map<String, Element> getElements(EmbeddedLibrary l) {
            return l.getDialogs();
        }

        @Override
        protected Element getValue(final Document moduleData) {
            return moduleData.getRootElement();
        }
    };

    private final String name;
    private final boolean readonly;

    Library(String name, boolean readonly) {
        super();
        if (name == null)
            throw new NullPointerException();
        this.name = name;
        this.readonly = readonly;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean isReadonly() {
        return this.readonly;
    }

    /**
     * The element to define this in a flat XML.
     * 
     * @param vers the version of the format.
     * @return an element totally defining this.
     */
    public abstract Element toFlatXML(final XMLFormatVersion vers);

    /**
     * The element to include in the {@value #LIBRARY_LIST_FILENAME} entry in a package.
     * 
     * @param vers the version of the format.
     * @return an element defining the properties of this.
     * @see #toPackageDocuments(XMLFormatVersion)
     */
    public abstract Element toPackageLibrariesElement(final XMLFormatVersion vers);

    /**
     * The documents to include in a package.
     * 
     * @param vers the version of the format.
     * @return documents totally defining this, by file name.
     */
    public abstract Map<String, Document> toPackageDocuments(final XMLFormatVersion vers);

    public abstract Map<String, Document> toPackageDialogDocuments(final XMLFormatVersion vers);

    /**
     * Whether this library can be merged with the passed one. The merge can happen if there's no
     * overlap between the two, or if the content of the overlap is equal (e.g. both define the
     * module 'foo' with the same content).
     * 
     * @param other another library.
     * @return <code>true</code> if this library and the other can be merged.
     */
    public abstract boolean canBeMerged(Library other);

    void mergeToFlatXML(final XMLFormatVersion vers, final Library other, final Element libElement) {
        assert this.canBeMerged(other);
    }

    void mergeDialogs(final ODPackage pkg, final Library obj) {
        assert this.canBeMerged(obj);
    }

    void mergeModules(final ODPackage pkg, final Library obj) {
        assert this.canBeMerged(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.name.hashCode();
        result = prime * result + (this.readonly ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Library other = (Library) obj;
        return this.name.equals(other.name) && this.readonly == other.readonly;
    }

    @Immutable
    static public final class LinkedLibrary extends Library {

        private static final String FLAT_XML_ELEMENT_NAME = "library-linked";
        private final String href, type;

        LinkedLibrary(String name, String href, String type) {
            super(name, true);
            this.href = href;
            this.type = type;
        }

        public final String getHref() {
            return this.href;
        }

        public final String getType() {
            return this.type;
        }

        @Override
        public Element toFlatXML(XMLFormatVersion vers) {
            final Namespace ns = vers.getXMLVersion().getLibrariesNS();
            final Namespace linkNS = vers.getXMLVersion().getNS("xlink");
            return setAttributes(new Element(FLAT_XML_ELEMENT_NAME, ns), linkNS);
        }

        private final Element setAttributes(final Element res, final Namespace linkNS) {
            final Namespace ns = res.getNamespace();
            res.setAttribute("name", getName(), ns);
            res.setAttribute("href", getHref(), linkNS);
            res.setAttribute("type", getType(), linkNS);
            res.setAttribute("readonly", String.valueOf(isReadonly()), ns);
            return res;
        }

        @Override
        public Element toPackageLibrariesElement(XMLFormatVersion vers) {
            final Namespace ns = XMLVersion.LIBRARY_NS;
            final Namespace linkNS = vers.getXMLVersion().getNS("xlink");
            final Element res = setAttributes(new Element("library", ns), linkNS);
            res.setAttribute("link", "true", ns);
            return res;
        }

        @Override
        public Map<String, Document> toPackageDocuments(XMLFormatVersion vers) {
            return Collections.emptyMap();
        }

        @Override
        public Map<String, Document> toPackageDialogDocuments(XMLFormatVersion vers) {
            return Collections.emptyMap();
        }

        @Override
        public boolean canBeMerged(final Library other) {
            // since a linked library is only a reference, it can only be merged with an equal
            // library
            return other == null || this.equals(other);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((this.href == null) ? 0 : this.href.hashCode());
            result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            final LinkedLibrary other = (LinkedLibrary) obj;
            return CompareUtils.equals(this.href, other.href) && CompareUtils.equals(this.type, other.type);
        }
    }

    // not immutable because of dialogs' elements
    @ThreadSafe
    static public final class EmbeddedLibrary extends Library {

        private static final String FLAT_XML_ELEMENT_NAME = "library-embedded";
        private final boolean passwordProtected;
        private final Map<String, String> modules;
        private final Map<String, Element> dialogs;

        EmbeddedLibrary(String name, boolean readonly, boolean passwordProtected, Map<String, String> modules, Map<String, Element> dialogs) {
            super(name, readonly);
            this.passwordProtected = passwordProtected;
            // LibreOffice always blank lines at the end when saving flat XML
            final Map<String, String> normalized = new HashMap<String, String>(modules.size());
            for (final Entry<String, String> e : modules.entrySet()) {
                normalized.put(e.getKey(), StringUtils.trim(e.getValue(), false) + "\n");
            }
            this.modules = Collections.unmodifiableMap(normalized);
            // perhaps clone elements (we would be immutable but would loose equals() optimization)
            this.dialogs = Collections.unmodifiableMap(new HashMap<String, Element>(dialogs));
        }

        public final boolean isPasswordProtected() {
            return this.passwordProtected;
        }

        public final Map<String, String> getModules() {
            return this.modules;
        }

        public final Map<String, Element> getDialogs() {
            return this.dialogs;
        }

        @Override
        public Element toFlatXML(XMLFormatVersion vers) {
            return this.toFlatXML(vers, Collections.<String> emptySet());
        }

        private final Element toFlatXML(final XMLFormatVersion vers, final Set<String> modulesToIgnore) {
            final Namespace ns = vers.getXMLVersion().getLibrariesNS();
            final Element res = setAttributes(vers, new Element(FLAT_XML_ELEMENT_NAME, ns));
            for (final Entry<String, String> e : this.getModules().entrySet()) {
                final String moduleName = e.getKey();
                if (!modulesToIgnore.contains(moduleName)) {
                    final Element moduleElem = new Element("module", ns);
                    moduleElem.setAttribute("name", moduleName, ns);
                    moduleElem.addContent(new Element("source-code", ns).setText(e.getValue()));
                    res.addContent(moduleElem);
                }
            }
            // dialogs aren't included in flat XML
            return res;
        }

        private final Element setAttributes(final XMLFormatVersion vers, final Element res) {
            final Namespace ns = res.getNamespace();
            res.setAttribute("name", getName(), ns);
            res.setAttribute("readonly", String.valueOf(isReadonly()), ns);
            if (vers.getXMLVersion().compareTo(XMLVersion.OD) >= 0)
                res.setAttribute("passwordprotected", String.valueOf(isPasswordProtected()), ns);
            return res;
        }

        @Override
        public Element toPackageLibrariesElement(XMLFormatVersion vers) {
            final Namespace ns = XMLVersion.LIBRARY_NS;
            final Element res = new Element("library", ns);
            res.setAttribute("name", getName(), ns);
            res.setAttribute("link", "false", ns);
            return res;
        }

        @Override
        public final Map<String, Document> toPackageDocuments(XMLFormatVersion vers) {
            return toPackageDocuments(vers, MODULES_CODEC);
        }

        private final Map<String, Document> toPackageDocuments(XMLFormatVersion vers, final LB_Codec<?> codec) {
            return this.toPackageDocuments(vers, codec, null, Collections.<String> emptySet());
        }

        private final Map<String, Document> toPackageDocuments(final XMLFormatVersion vers, final LB_Codec<?> codec, Document lb, final Set<String> elementsToIgnore) {
            final Map<String, ?> map = codec.getElements(this);
            final Map<String, Document> res = new HashMap<String, Document>(map.size() + 1);

            final Namespace ns = XMLVersion.LIBRARY_NS;
            // old script namespace is still used
            final Namespace scriptNS = XMLVersion.OOo.getNS("script");
            final Element rootElem;
            if (lb == null) {
                rootElem = setAttributes(vers, new Element("library", ns));
                lb = new Document(rootElem, new DocType("library:library", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "library.dtd"));
                res.put(codec.getTOCName(), lb);
            } else {
                rootElem = lb.getRootElement();
            }
            for (final Entry<String, ?> e : map.entrySet()) {
                final String moduleName = e.getKey();
                if (elementsToIgnore.contains(moduleName))
                    continue;

                // add to TOC
                final Element moduleElem = new Element("element", ns);
                moduleElem.setAttribute("name", moduleName, ns);
                rootElem.addContent(moduleElem);

                // add Document
                final Element moduleRootElem;
                if (codec == DIALOGS_CODEC) {
                    moduleRootElem = (Element) ((Element) e.getValue()).clone();
                } else {
                    moduleRootElem = new Element("module", scriptNS);
                    moduleRootElem.setAttribute("name", moduleName, scriptNS);
                    moduleRootElem.setAttribute("language", "StarBasic", scriptNS);
                    moduleRootElem.setText((String) e.getValue());
                }
                // create a new DTD for each Document
                final DocType moduleDTD = codec.createElementDTD();
                assert moduleDTD.getElementName().equals(moduleRootElem.getQualifiedName());
                res.put(moduleName + ".xml", new Document(moduleRootElem, moduleDTD));
            }

            return res;
        }

        @Override
        public Map<String, Document> toPackageDialogDocuments(XMLFormatVersion vers) {
            return toPackageDocuments(vers, DIALOGS_CODEC);
        }

        @Override
        public boolean canBeMerged(final Library obj) {
            if (obj == null || this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            final EmbeddedLibrary other = (EmbeddedLibrary) obj;
            if (this.passwordProtected != other.passwordProtected)
                return false;
            return mapCanBeMerged(this.modules, other.modules) && mapCanBeMerged(this.dialogs, other.dialogs);
        }

        // Java6 let us call it canBeMerged but not Java7
        static private final <V> boolean mapCanBeMerged(final Map<String, V> m1, final Map<String, V> m2) {
            final Set<String> duplicateKeys = CollectionUtils.inter(m1.keySet(), m2.keySet());
            for (final String key : duplicateKeys) {
                final V v1 = m1.get(key);
                final V v2 = m2.get(key);
                if (v1 instanceof Element) {
                    if (!JDOMUtils.equalsDeep((Element) v1, (Element) v2))
                        return false;
                } else {
                    if (!v1.equals(v2))
                        return false;
                }
            }
            return true;
        }

        @Override
        void mergeToFlatXML(final XMLFormatVersion vers, final Library obj, final Element libElement) {
            super.mergeToFlatXML(vers, obj, libElement);
            final EmbeddedLibrary other = (EmbeddedLibrary) obj;
            // add all modules not present in other :
            // since canBeMerged() is true, the duplicate ones are equal
            libElement.addContent(this.toFlatXML(vers, other.getModules().keySet()).removeContent());
        }

        @Override
        void mergeDialogs(final ODPackage pkg, final Library obj) {
            super.mergeDialogs(pkg, obj);
            DIALOGS_CODEC.encode(this, pkg, (EmbeddedLibrary) obj);
        }

        @Override
        void mergeModules(ODPackage pkg, Library obj) {
            super.mergeModules(pkg, obj);
            MODULES_CODEC.encode(this, pkg, (EmbeddedLibrary) obj);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + (this.passwordProtected ? 1231 : 1237);
            result = prime * result + this.modules.keySet().hashCode();
            result = prime * result + this.dialogs.keySet().hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            final EmbeddedLibrary other = (EmbeddedLibrary) obj;
            if (!(this.passwordProtected == other.passwordProtected && this.modules.equals(other.modules) && this.dialogs.keySet().equals(other.dialogs.keySet())))
                return false;
            for (final Entry<String, Element> e : this.dialogs.entrySet()) {
                final Element otherElement = other.dialogs.get(e.getKey());
                // since keys are equal
                assert otherElement != null;
                if (!JDOMUtils.equalsDeep(e.getValue(), otherElement))
                    return false;
            }
            return true;
        }
    }
}
