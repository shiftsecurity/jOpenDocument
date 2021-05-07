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

import org.jopendocument.util.JDOMUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

/**
 * An OpenDocument manifest included in the package format.
 * 
 * @author Sylvain CUAZ
 */
final class Manifest {

    public static final String ENTRY_NAME = "META-INF/manifest.xml";

    /**
     * Parse an OpenDocument manifest and return a map of path to media type.
     * 
     * @param ins the manifest.
     * @return the mapping between path and types.
     * @throws JDOMException if the manifest is not valid XML.
     * @throws IOException if the stream can't be read.
     */
    public static final Map<String, String> parse(final InputStream ins) throws JDOMException, IOException {
        final Map<String, String> res = new HashMap<String, String>();
        // OOo 1 use the line below, don't load that
        // <!DOCTYPE manifest:manifest PUBLIC "-//OpenOffice.org//DTD Manifest 1.0//EN"
        // "Manifest.dtd">
        final Document manifestDoc = OOUtils.getBuilder().build(ins);
        for (final Object child : manifestDoc.getRootElement().getChildren()) {
            final Element fileEntry = (Element) child;
            final String path = fileEntry.getAttributeValue("full-path", fileEntry.getNamespace("manifest"));
            // main mimetype taken from somewhere else see ODPackage#getContentType()
            if (!path.equals("/")) {
                final String type = fileEntry.getAttributeValue("media-type", fileEntry.getNamespace("manifest"));
                res.put(path, type);
            }
        }
        return res;
    }

    private final XMLFormatVersion version;
    private final Document doc;

    /**
     * Creates a new Manifest.
     * 
     * @param version the version.
     * @param mainType the mime type of the document, eg "application/vnd.sun.xml.writer".
     */
    public Manifest(XMLFormatVersion version, String mainType) {
        this.version = version;
        this.doc = version.getXML().createManifestDoc();
        this.addEntry("/", mainType);
    }

    private Namespace getNS() {
        return this.version.getXMLVersion().getManifest();
    }

    final Document getDocument() {
        return this.doc;
    }

    /**
     * Adds an entry.
     * 
     * @param path the path, eg "Pictures/a.png".
     * @param type the mime type, eg "image/png"
     */
    public void addEntry(String path, String type) {
        final Element elem = new Element("file-entry", this.getNS());
        elem.setAttribute("media-type", type, this.getNS());
        elem.setAttribute("full-path", path, this.getNS());
        this.doc.getRootElement().addContent(elem);
    }

    public String asString() {
        return JDOMUtils.output(this.doc);
    }
}
