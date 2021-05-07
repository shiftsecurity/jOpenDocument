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

import static java.util.Collections.singleton;
import org.jopendocument.dom.spreadsheet.CellStyle;
import org.jopendocument.dom.spreadsheet.ColumnStyle;
import org.jopendocument.dom.spreadsheet.RowStyle;
import org.jopendocument.dom.spreadsheet.TableStyle;
import org.jopendocument.dom.style.PageLayoutStyle;
import org.jopendocument.dom.style.data.DataStyle;
import org.jopendocument.dom.text.ParagraphStyle;
import org.jopendocument.dom.text.TextStyle;
import org.jopendocument.util.SetMap;
import org.jopendocument.util.Tuple2;
import org.jopendocument.util.JDOMUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A style, see section 16 of v1.2-part1. Maintains a map of family to classes.
 * 
 * @author Sylvain
 */
@ThreadSafe
public class Style extends ODNode {

    @GuardedBy("Style")
    private static boolean STANDARD_STYLE_RESOLUTION = false;
    @GuardedBy("class2Desc")
    private static final Map<XMLVersion, Map<String, StyleDesc<?>>> family2Desc;
    @GuardedBy("class2Desc")
    private static final Map<XMLVersion, Map<String, StyleDesc<?>>> elemName2Desc;
    @GuardedBy("class2Desc")
    private static final Map<XMLVersion, Map<Class<? extends Style>, StyleDesc<?>>> class2Desc;
    @GuardedBy("class2Desc")
    private static boolean descsLoaded = false;
    // need a CollectionMap e.g. [ "style:style", "style:data-style-name" ] ->
    // DataStyle.DATA_STYLES_DESCS
    @GuardedBy("class2Desc")
    private static final Map<XMLVersion, SetMap<Tuple2<String, String>, StyleDesc<?>>> attribute2Desc;
    static {
        final int versionsCount = XMLVersion.values().length;
        family2Desc = new HashMap<XMLVersion, Map<String, StyleDesc<?>>>(versionsCount);
        elemName2Desc = new HashMap<XMLVersion, Map<String, StyleDesc<?>>>(versionsCount);
        class2Desc = new HashMap<XMLVersion, Map<Class<? extends Style>, StyleDesc<?>>>(versionsCount);
        attribute2Desc = new HashMap<XMLVersion, SetMap<Tuple2<String, String>, StyleDesc<?>>>(versionsCount);
        for (final XMLVersion v : XMLVersion.values()) {
            family2Desc.put(v, new HashMap<String, StyleDesc<?>>());
            elemName2Desc.put(v, new HashMap<String, StyleDesc<?>>());
            class2Desc.put(v, new HashMap<Class<? extends Style>, StyleDesc<?>>());
            attribute2Desc.put(v, new SetMap<Tuple2<String, String>, StyleDesc<?>>(128));
        }
    }

    // lazy initialization to avoid circular dependency (i.e. ClassLoader loads PStyle.DESC which
    // loads StyleStyle which needs PStyle.DESC)
    private static void loadDescs() {
        synchronized (class2Desc) {
            if (!descsLoaded) {
                CellStyle.registerDesc();
                RowStyle.registerDesc();
                ColumnStyle.registerDesc();
                TableStyle.registerDesc();
                TextStyle.registerDesc();
                ParagraphStyle.registerDesc();
                DataStyle.registerDesc();
                GraphicStyle.registerDesc();
                PageLayoutStyle.registerDesc();
                descsLoaded = true;
            }
        }
    }

    /**
     * Whether to search styles according to the standard or to LibreOffice.
     * 
     * @param std <code>true</code> to search like the standard, <code>false</code> to search like
     *        LibreOffice.
     */
    public static synchronized void setStandardStyleResolution(boolean std) {
        STANDARD_STYLE_RESOLUTION = std;
    }

    public static synchronized boolean isStandardStyleResolution() {
        return STANDARD_STYLE_RESOLUTION;
    }

    // until now a majority of styles have remained constant through versions
    public static void registerAllVersions(StyleDesc<? extends Style> desc) {
        for (final XMLVersion v : XMLVersion.values()) {
            if (v == desc.getVersion())
                register(desc);
            else
                register(StyleDesc.copy(desc, v));
        }
    }

    public static void register(StyleDesc<?> desc) {
        synchronized (class2Desc) {
            if (desc instanceof StyleStyleDesc<?>) {
                final StyleStyleDesc<?> styleStyleDesc = (StyleStyleDesc<?>) desc;
                if (family2Desc.get(desc.getVersion()).put(styleStyleDesc.getFamily(), styleStyleDesc) != null)
                    throw new IllegalStateException(styleStyleDesc.getFamily() + " duplicate family");
            } else {
                if (elemName2Desc.get(desc.getVersion()).put(desc.getElementName(), desc) != null)
                    throw new IllegalStateException(desc.getElementName() + " duplicate element name");
            }
            assert desc != null : "Will need containsKey() in getStyleDesc()";
            if (class2Desc.get(desc.getVersion()).put(desc.getStyleClass(), desc) != null)
                throw new IllegalStateException(desc.getStyleClass() + " duplicate");
        }
    }

    /**
     * Get all registered {@link StyleDesc}.
     * 
     * @param version the version.
     * @return all known descriptions.
     */
    private static Collection<StyleDesc<?>> getDesc(final XMLVersion version) {
        assert Thread.holdsLock(class2Desc);
        loadDescs();
        return class2Desc.get(version).values();
    }

    // return the qualified names of attributes referencing styles
    static final Set<String> getAttr(final XMLVersion version) {
        final Set<String> res = new HashSet<String>();
        synchronized (class2Desc) {
            for (final StyleDesc<?> desc : getDesc(version)) {
                res.addAll(desc.getRefElementsMap().keySet());
                res.addAll(desc.getMultiRefElementsMap().keySet());
            }
        }
        return res;
    }

    /**
     * Return a mapping from element/attribute to its {@link StyleDesc}.
     * 
     * <pre>
     * [ element qualified name, attribute qualified name ] -> StyleDesc ; e.g. :
     * [ "text:p", "text:style-name" ] -> <code>StyleStyleDesc&lt;ParagraphStyle&gt;</code>
     * [ "text:h", "text:style-name" ] -> <code>StyleStyleDesc&lt;ParagraphStyle&gt;</code>
     * [ "text:span", "text:style-name" ] -> <code>StyleStyleDesc&lt;TextStyle&gt;</code>
     * </pre>
     * 
     * @param version the version.
     * @return the mapping from attribute to description.
     */
    private static SetMap<Tuple2<String, String>, StyleDesc<?>> getAttr2Desc(final XMLVersion version) {
        synchronized (class2Desc) {
            final SetMap<Tuple2<String, String>, StyleDesc<?>> map = attribute2Desc.get(version);
            if (map.isEmpty()) {
                for (final StyleDesc<?> desc : getDesc(version)) {
                    fillMap(map, desc, desc.getRefElementsMap());
                    fillMap(map, desc, desc.getMultiRefElementsMap());
                }
                assert !map.isEmpty();
            }
            return map;
        }
    }

    private static void fillMap(final SetMap<Tuple2<String, String>, StyleDesc<?>> map, final StyleDesc<?> desc, final SetMap<String, String> elemsByAttrs) {
        for (final Entry<String, Set<String>> e : elemsByAttrs.entrySet()) {
            for (final String elementName : e.getValue()) {
                final Tuple2<String, String> key = Tuple2.create(elementName, e.getKey());
                final boolean added = map.add(key, desc);
                if (!added)
                    throw new IllegalArgumentException("Already added " + desc + " for " + key);
            }
        }
    }

    /**
     * Create the most specific instance for the passed element.
     * 
     * @param pkg the package where the style is defined.
     * @param styleElem a style:style XML element.
     * @return the most specific instance, e.g. a new ColumnStyle.
     */
    public static Style warp(final ODPackage pkg, final Element styleElem) {
        loadDescs();
        final String name = styleElem.getName();
        if (name.equals(StyleStyleDesc.ELEMENT_NAME)) {
            final String family = StyleStyleDesc.getFamily(styleElem);
            final StyleDesc<?> styleClass;
            synchronized (class2Desc) {
                styleClass = family2Desc.get(pkg.getVersion()).get(family);
            }
            if (styleClass != null) {
                return styleClass.create(pkg, styleElem);
            } else {
                return new StyleStyle(pkg, styleElem);
            }
        } else {
            final StyleDesc<?> styleClass;
            synchronized (class2Desc) {
                styleClass = elemName2Desc.get(pkg.getVersion()).get(name);
            }
            if (styleClass != null) {
                return styleClass.create(pkg, styleElem);
            } else {
                return new Style(pkg, styleElem);
            }
        }
    }

    public static <S extends Style> S getStyle(final ODPackage pkg, final Class<S> clazz, final String name) {
        final StyleDesc<S> styleDesc = getStyleDesc(clazz, pkg.getVersion());
        return styleDesc.findStyleWithName(pkg, pkg.getContent().getDocument(), name);
    }

    /**
     * Return the style element referenced by the passed attribute.
     * 
     * @param pkg the package where <code>styleAttr</code> is defined.
     * @param styleAttr any attribute in <code>pkg</code>, e.g.
     *        <code>style:page-layout-name="pm1"</code> of a style:master-page.
     * @return the referenced element if <code>styleAttr</code> is an attribute pointing to a style
     *         in the passed package, <code>null</code> otherwise, e.g.
     *         <code><style:page-layout style:name="pm1"></code>.
     */
    public static Element getReferencedStyleElement(final ODPackage pkg, final Attribute styleAttr) {
        return (Element) getReferencedStyle(pkg, null, styleAttr, ResolveMode.RESOLVE_ELEMENT);
    }

    public static Style getReferencedStyle(final ODPackage pkg, final Attribute styleAttr) {
        return (Style) getReferencedStyle(pkg, null, styleAttr, ResolveMode.RESOLVE_STYLE);
    }

    /**
     * Resolve the style referenced by the passed attribute. The attribute is not necessarily in the
     * passed package, i.e. this method can be used to test whether the referenced style is defined
     * in a package (e.g. before actually adding the attribute).
     * 
     * @param pkg the package where <code>styleAttr</code> should be resolved.
     * @param styleAttrDoc the document from where to resolve <code>styleAttr</code>, can be
     *        <code>null</code> if <code>styleAttr</code> has a document. The document must be in
     *        <code>pkg</code>.
     * @param styleAttr any attribute, e.g. <code>style:page-layout-name="pm1"</code> of a
     *        style:master-page.
     * @return <code>null</code> if <code>styleAttr</code> is <code>null</code> of if it isn't a
     *         style attribute, {@link ReferenceStatus#RESOLVED} if <code>pkg</code> contains the
     *         referenced style, {@link ReferenceStatus#NOT_RESOLVED} otherwise.
     */
    public static ResolveResult resolveReference(final ODPackage pkg, final Document styleAttrDoc, final Attribute styleAttr) {
        return (ResolveResult) getReferencedStyle(pkg, styleAttrDoc, styleAttr, ResolveMode.RESOLVE_REFERENCE);
    }

    public static enum ResolveResult {
        RESOLVED, NOT_RESOLVED
    }

    private static enum ResolveMode {
        RESOLVE_REFERENCE, RESOLVE_ELEMENT, RESOLVE_STYLE
    }

    private static Object getReferencedStyle(final ODPackage pkg, Document styleAttrDoc, final Attribute styleAttr, final ResolveMode mode) {
        if (styleAttr == null)
            return null;
        final Collection<StyleDesc<?>> descs = getAttr2Desc(pkg.getVersion()).get(Tuple2.create(styleAttr.getParent().getQualifiedName(), styleAttr.getQualifiedName()));
        if (descs == null)
            return null;
        if (styleAttrDoc == null)
            styleAttrDoc = styleAttr.getDocument();
        for (final StyleDesc<?> desc : descs) {
            final Element res = pkg.getStyle(styleAttrDoc, desc, styleAttr.getValue());
            if (res != null)
                if (mode == ResolveMode.RESOLVE_STYLE)
                    return desc.create(pkg, res);
                else if (mode == ResolveMode.RESOLVE_ELEMENT)
                    return res;
                else
                    return ResolveResult.RESOLVED;
        }
        return mode == ResolveMode.RESOLVE_REFERENCE ? ResolveResult.NOT_RESOLVED : null;
    }

    public static <S extends Style> StyleDesc<S> getStyleDesc(Class<S> clazz, final XMLVersion version) {
        return getStyleDesc(clazz, version, true);
    }

    public static <S extends StyleStyle> StyleStyleDesc<S> getStyleStyleDesc(Class<S> clazz, final XMLVersion version) {
        return (StyleStyleDesc<S>) getStyleDesc(clazz, version);
    }

    private static <S extends Style> StyleDesc<S> getStyleDesc(Class<S> clazz, final XMLVersion version, final boolean mustExist) {
        loadDescs();
        synchronized (class2Desc) {
            final Map<Class<? extends Style>, StyleDesc<?>> map = class2Desc.get(version);
            @SuppressWarnings("unchecked")
            final StyleDesc<S> res = (StyleDesc<S>) map.get(clazz);
            if (res == null && mustExist)
                throw new IllegalArgumentException("unregistered " + clazz + " for version " + version);
            return res;
        }
    }

    protected static <S extends Style> StyleDesc<S> getNonNullStyleDesc(final Class<S> clazz, final XMLVersion version, final Element styleElem, final String styleName) {
        final StyleDesc<S> registered = getStyleDesc(clazz, version, false);
        if (registered != null)
            return registered;
        else
        // generic desc, use styleName as baseName
        if (clazz == StyleStyle.class) {
            @SuppressWarnings("unchecked")
            final StyleDesc<S> res = (StyleDesc<S>) new StyleStyleDesc<StyleStyle>(StyleStyle.class, version, StyleStyleDesc.getFamily(styleElem), styleName) {
                @Override
                public StyleStyle create(ODPackage pkg, Element e) {
                    return new StyleStyle(pkg, styleElem);
                }
            };
            return res;
        } else if (clazz == Style.class) {
            return new StyleDesc<S>(clazz, version, styleElem.getName(), styleName) {
                @Override
                public S create(ODPackage pkg, Element e) {
                    return clazz.cast(new Style(pkg, styleElem));
                }
            };
        } else
            throw new IllegalStateException("Unregistered class which is neither Style not StyleStyle :" + clazz);
    }

    private final StyleDesc<?> desc;
    private final ODPackage pkg;
    private final String name;
    private final XMLFormatVersion ns;

    public Style(final ODPackage pkg, final Element styleElem) {
        super(styleElem);
        this.pkg = pkg;
        this.name = this.getElement().getAttributeValue("name", this.getSTYLE());
        this.ns = this.pkg.getFormatVersion();
        this.desc = getNonNullStyleDesc(this.getClass(), this.ns.getXMLVersion(), styleElem, getName());
        checkElemName();
        // assert that styleElem is in pkg (and thus have the same version)
        assert this.pkg.getXMLFile(getElement().getDocument()) != null;
        assert this.pkg.getFormatVersion().equals(XMLFormatVersion.get(getElement().getDocument()));
    }

    protected void checkElemName() {
        if (!this.desc.getElementName().equals(this.getElement().getName()))
            throw new IllegalArgumentException("expected " + this.desc.getElementName() + " but got " + this.getElement().getName() + " for " + getElement());
    }

    protected final ODPackage getPackage() {
        return this.pkg;
    }

    protected final Namespace getSTYLE() {
        return this.getElement().getNamespace("style");
    }

    public final XMLVersion getNS() {
        return this.ns.getXMLVersion();
    }

    public final String getName() {
        return this.name;
    }

    protected StyleDesc<?> getDesc() {
        return this.desc;
    }

    public Element getFormattingProperties() {
        return this.getFormattingProperties(this.getName());
    }

    /**
     * Create if necessary and return the wanted properties.
     * 
     * @param family type of properties, eg "text".
     * @return the matching properties, eg &lt;text-properties&gt;.
     */
    public final Element getFormattingProperties(final String family) {
        return getFormattingProperties(family, true);
    }

    public final Element getFormattingProperties(final String family, final boolean create) {
        final Element elem = this.ns.getXML().createFormattingProperties(family);
        Element res = this.getElement().getChild(elem.getName(), elem.getNamespace());
        if (res == null && create) {
            res = elem;
            this.getElement().addContent(res);
        }
        return res;
    }

    /**
     * Return the elements referring to this style in the passed document.
     * 
     * @param doc an XML document.
     * @param wantSingle whether elements that affect only themselves should be included.
     * @param wantMulti whether elements that affect multiple others should be included.
     * @return the list of elements referring to this.
     */
    private final List<Element> getReferences(final Document doc, final boolean wantSingle, boolean wantMulti) {
        return this.desc.getReferences(doc, getName(), wantSingle, wantMulti);
    }

    /**
     * Return the elements referring to this style.
     * 
     * @return the list of elements referring to this.
     */
    public final List<Element> getReferences() {
        return this.getReferences(true, true);
    }

    private final List<Element> getReferences(final boolean wantSingle, final boolean wantMulti) {
        final Document myDoc = this.getElement().getDocument();
        final Document content = this.pkg.getContent().getDocument();
        // my document can always refer to us
        final List<Element> res = this.getReferences(myDoc, wantSingle, wantMulti);
        // but only common styles can be referenced from the content
        if (myDoc != content && !this.isAutomatic())
            res.addAll(this.getReferences(content, wantSingle, wantMulti));
        return res;
    }

    private final boolean isAutomatic() {
        return this.getElement().getParentElement().getQualifiedName().equals("office:automatic-styles");
    }

    public final boolean isReferencedAtMostOnce() {
        // i.e. no multi-references and at most one single reference
        return this.getReferences(false, true).size() == 0 && this.getReferences(true, false).size() <= 1;
    }

    /**
     * Make a copy of this style and add it to its document.
     * 
     * @return the new style with an unused name.
     */
    public final Style dup() {
        // don't use an ODXMLDocument attribute, search for our document in an ODPackage, that way
        // even if our element changes document (toSingle()) we will find the proper ODXMLDocument
        final ODXMLDocument xmlFile = this.pkg.getXMLFile(this.getElement().getDocument());
        final String unusedName = xmlFile.findUnusedName(this.desc, this.desc.getBaseName());
        final Element clone = (Element) this.getElement().clone();
        // needed if this is a default-style
        clone.setName(this.desc.getElementName());
        clone.setAttribute("name", unusedName, this.getSTYLE());
        JDOMUtils.insertAfter(this.getElement(), singleton(clone));
        return this.desc.create(this.pkg, clone);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Style))
            return false;
        final Style o = (Style) obj;
        return this.getName().equals(o.getName()) && this.getElement().getName().equals(o.getElement().getName()) && this.getElement().getNamespace().equals(o.getElement().getNamespace());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getElement().getName().hashCode() + this.getElement().getNamespace().hashCode();
    }
}
