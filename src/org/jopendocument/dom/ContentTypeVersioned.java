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

import static java.util.Arrays.asList;
import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.util.Tuple2;

import java.io.File;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A {@link ContentType} of a certain version.
 * 
 * @author Sylvain
 */
public enum ContentTypeVersioned {
    TEXT_V1(ContentType.TEXT, XMLVersion.OOo, "application/vnd.sun.xml.writer", "text", "sxw") {
    },
    GRAPHICS_V1(ContentType.GRAPHICS, XMLVersion.OOo, "application/vnd.sun.xml.draw", "drawing", "sxd") {
    },
    PRESENTATION_V1(ContentType.PRESENTATION, XMLVersion.OOo, "application/vnd.sun.xml.impress", "presentation", "sxi") {
    },
    SPREADSHEET_V1(ContentType.SPREADSHEET, XMLVersion.OOo, "application/vnd.sun.xml.calc", "spreadsheet", "sxc") {
    },
    TEXT(ContentType.TEXT, XMLVersion.OD, "application/vnd.oasis.opendocument.text", "text", "odt") {
    },
    TEXT_TEMPLATE(ContentType.TEXT, XMLVersion.OD, "application/vnd.oasis.opendocument.text-template", "text", "ott") {
    },
    GRAPHICS(ContentType.GRAPHICS, XMLVersion.OD, "application/vnd.oasis.opendocument.graphics", "drawing", "odg") {
    },
    GRAPHICS_TEMPLATE(ContentType.GRAPHICS, XMLVersion.OD, "application/vnd.oasis.opendocument.graphics-template", "drawing", "otg") {
    },
    PRESENTATION(ContentType.PRESENTATION, XMLVersion.OD, "application/vnd.oasis.opendocument.presentation", "presentation", "odp") {
    },
    PRESENTATION_TEMPLATE(ContentType.PRESENTATION, XMLVersion.OD, "application/vnd.oasis.opendocument.presentation-template", "presentation", "otp") {
    },
    SPREADSHEET(ContentType.SPREADSHEET, XMLVersion.OD, "application/vnd.oasis.opendocument.spreadsheet", "spreadsheet", "ods") {
    },
    SPREADSHEET_TEMPLATE(ContentType.SPREADSHEET, XMLVersion.OD, "application/vnd.oasis.opendocument.spreadsheet-template", "spreadsheet", "ots") {
    };

    private final ContentType type;
    private final XMLVersion version;
    private final String mimeType;
    // either office:class of the root element for V1
    // or the name of the child of office:body for V2
    private final String shortName;
    private final String extension;

    private ContentTypeVersioned(ContentType type, XMLVersion version, String mimeType, String bodyChildName, String extension) {
        this.type = type;
        this.mimeType = mimeType;
        this.version = version;
        this.shortName = bodyChildName;
        this.extension = extension;
    }

    public final XMLVersion getVersion() {
        return this.version;
    }

    public final String getShortName() {
        return this.shortName;
    }

    /**
     * Returns the xpath to the body (from below the root element).
     * 
     * @return the xpath to the body, e.g. "./office:body".
     */
    public final String getBodyPath() {
        return this.getVersion() == XMLVersion.OOo ? "./office:body" : "./office:body/office:" + getShortName();
    }

    public final Element getBody(final Document doc) {
        final Namespace officeNS = this.getVersion().getOFFICE();
        final Element body = doc.getRootElement().getChild("body", officeNS);
        if (this.getVersion().equals(XMLVersion.OOo))
            return body;
        else
            return body.getChild(getShortName(), officeNS);
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final ContentType getType() {
        return this.type;
    }

    public final String getExtension() {
        return this.extension;
    }

    public final String getFlatExtension() {
        if (this.getVersion() == XMLVersion.OOo)
            return null;
        return 'f' + this.getExtension();
    }

    final File addExt(File f, final boolean flat) {
        final String ext = '.' + (flat ? this.getFlatExtension() : this.getExtension());
        if (!f.getName().endsWith(ext))
            f = new File(f.getParentFile(), f.getName() + ext);
        return f;
    }

    public final boolean isTemplate() {
        return this.getMimeType().endsWith(TEMPLATE_SUFFIX);
    }

    public final ContentTypeVersioned getTemplate() {
        if (this.isTemplate())
            return this;
        else
            return fromMime(getMimeType() + TEMPLATE_SUFFIX);
    }

    public final ContentTypeVersioned getNonTemplate() {
        if (this.isTemplate())
            return fromMime(getMimeType().substring(0, getMimeType().length() - TEMPLATE_SUFFIX.length()));
        else
            return this;
    }

    /**
     * Create a new minimal document using {@link RootElement#CONTENT}.
     * 
     * @param version the version.
     * @return the body of the created document.
     * @see #createContent(XMLFormatVersion, boolean)
     */
    public final Element createContent(final XMLFormatVersion version) {
        return this.createContent(version, false);
    }

    /**
     * Create a new minimal document.
     * 
     * @param version the version.
     * @param singleXML <code>true</code> for {@link RootElement#SINGLE_CONTENT}, <code>false</code>
     *        for {@link RootElement#CONTENT}.
     * @return the body of the created document.
     * @see #createPackage(XMLFormatVersion)
     */
    public Element createContent(final XMLFormatVersion version, final boolean singleXML) {
        checkVersion(version);
        final RootElement rootElement = singleXML ? RootElement.SINGLE_CONTENT : RootElement.CONTENT;
        final Document doc = rootElement.createDocument(version);
        final Namespace officeNS = getVersion().getOFFICE();
        setType(doc, rootElement, officeNS);
        // don't forget that, otherwise OO crash
        doc.getRootElement().addContent(new Element("automatic-styles", officeNS));

        final Element topBody = new Element("body", officeNS);
        final Element body;
        if (this.getVersion().equals(XMLVersion.OD)) {
            body = new Element(this.getShortName(), officeNS);
            topBody.addContent(body);
        } else
            body = topBody;
        doc.getRootElement().addContent(topBody);

        return body;
    }

    private final void checkVersion(final XMLFormatVersion version) {
        if (version.getXMLVersion() != getVersion())
            throw new IllegalArgumentException("Version mismatch : " + version.getXMLVersion());
    }

    public void setType(final Document doc) {
        this.setType(doc, RootElement.fromDocument(doc), getVersion().getOFFICE());
    }

    // not safe
    private void setType(final Document doc, final RootElement rootElem, final Namespace officeNS) {
        final Element root = doc.getRootElement();
        assert root.getName().equals(rootElem.getElementName());
        if (rootElem != RootElement.CONTENT && rootElem != RootElement.SINGLE_CONTENT)
            throw new IllegalArgumentException("the document is not content : " + rootElem);
        if (this.getVersion().equals(XMLVersion.OOo)) {
            root.setAttribute("class", this.getShortName(), officeNS);
        } else if (this.getVersion().equals(XMLVersion.OD)) {
            if (rootElem == RootElement.SINGLE_CONTENT) {
                root.setAttribute("mimetype", this.getMimeType(), officeNS);
            }
            // else ODPackage can use the body to infer the type (office:mimetype is only for single
            // xml document)
        }
    }

    /**
     * Create a new minimal document using {@link RootElement#STYLES}.
     * 
     * @param version the version.
     * @return the created document.
     */
    public Document createStyles(final XMLFormatVersion version) {
        checkVersion(version);
        final Namespace officeNS = getVersion().getOFFICE();
        final Document styles = RootElement.STYLES.createDocument(version);
        // some consumers demand empty children
        styles.getRootElement().addContent(asList(new Element("styles", officeNS), new Element("automatic-styles", officeNS), new Element("master-styles", officeNS)));
        return styles;
    }

    /**
     * Creates an empty package.
     * 
     * @param version the version of the package.
     * @return a new package with minimal {@link RootElement#CONTENT} and {@link RootElement#STYLES}
     */
    public ODPackage createPackage(final XMLFormatVersion version) {
        return ODPackage.createFromDocuments(this, this.createContent(version, false).getDocument(), this.createStyles(version), null, null);
    }

    // *** static

    private static final String TEMPLATE_SUFFIX = "-template";

    static ContentTypeVersioned fromType(ContentType type, XMLVersion version, final boolean template) {
        for (final ContentTypeVersioned t : ContentTypeVersioned.values())
            if (t.getType().equals(type) && t.getVersion() == version && t.isTemplate() == template)
                return t;
        return null;
    }

    static public ContentTypeVersioned fromMime(String mime) {
        for (final ContentTypeVersioned t : ContentTypeVersioned.values())
            if (t.getMimeType().equals(mime))
                return t;
        return null;
    }

    static public ContentTypeVersioned fromMime(byte[] mime) {
        return fromMime(new String(mime, ODPackage.MIMETYPE_ENC));
    }

    static ContentTypeVersioned fromContent(final ODXMLDocument content) {
        final ContentTypeVersioned res;
        final XMLVersion vers = content.getVersion();
        if (vers.equals(XMLVersion.OOo)) {
            final Element contentRoot = content.getDocument().getRootElement();
            final String docClass = contentRoot.getAttributeValue("class", contentRoot.getNamespace("office"));
            res = ContentTypeVersioned.fromClass(docClass);
        } else if (vers.equals(XMLVersion.OD)) {
            final Element bodyChild = (Element) content.getChild("body").getChildren().get(0);
            res = ContentTypeVersioned.fromBody(bodyChild.getName());
        } else {
            throw new IllegalStateException("Unknown content version : " + vers);
        }
        assert !res.isTemplate() : "template status cannot be inferred from content";
        return res;
    }

    static ContentTypeVersioned fromClass(String name) {
        return fromShortName(XMLVersion.OOo, name);
    }

    static ContentTypeVersioned fromBody(String name) {
        return fromShortName(XMLVersion.OD, name);
    }

    static private ContentTypeVersioned fromShortName(XMLVersion version, String name) {
        if (name == null)
            throw new NullPointerException();

        for (final ContentTypeVersioned t : ContentTypeVersioned.values())
            if (t.shortName.equals(name) && t.getVersion() == version)
                return t;
        return null;
    }

    // Boolean : is flat
    static Tuple2<ContentTypeVersioned, Boolean> fromExtension(final String extension) {
        if (extension != null && extension.length() != 0) {
            for (final ContentTypeVersioned t : ContentTypeVersioned.values()) {
                if (extension.equals(t.getExtension()))
                    return Tuple2.create(t, false);
                if (extension.equals(t.getFlatExtension()))
                    return Tuple2.create(t, true);
            }
        }
        return Tuple2.nullInstance();
    }
}
