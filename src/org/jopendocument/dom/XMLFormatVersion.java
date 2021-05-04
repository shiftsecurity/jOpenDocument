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

import org.jopendocument.util.CompareUtils;

import java.util.HashMap;
import java.util.Map;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.jdom.Document;
import org.jdom.Element;

/**
 * Encapsulate a {@link XMLVersion version of xml} and its office version.
 * 
 * @author Sylvain CUAZ
 */
@ThreadSafe
public final class XMLFormatVersion {
    // not an enum to support any version (be forward compatible)

    @GuardedBy("instances")
    static private Map<XMLFormatVersion, XMLFormatVersion> instances = new HashMap<XMLFormatVersion, XMLFormatVersion>();
    static private final XMLFormatVersion OOo = get(XMLVersion.OOo, "1.0");

    public static XMLFormatVersion getOOo() {
        return OOo;
    }

    /**
     * The default version.
     * 
     * @return the default version.
     * @see OOXML#setDefault(OOXML)
     */
    public static XMLFormatVersion getDefault() {
        return OOXML.getDefault().getFormatVersion();
    }

    public static XMLFormatVersion get(Document doc) {
        return get(doc.getRootElement());
    }

    public static XMLFormatVersion get(Element root) {
        final XMLVersion version = XMLVersion.getVersion(root);
        // only 1.0 and 1.1 have the attribute optional (e.g. Microsoft Office 2007 and 2010 support
        // 1.1 and omit it)
        return get(version, root.getAttributeValue("version", version.getOFFICE(), "1.1"));
    }

    public static XMLFormatVersion get(XMLVersion xmlVersion, String officeVersion) {
        final XMLFormatVersion key = new XMLFormatVersion(xmlVersion, officeVersion);
        synchronized (instances) {
            XMLFormatVersion res = instances.get(key);
            if (res == null) {
                res = key;
                instances.put(res, res);
            }
            return res;
        }
    }

    private final XMLVersion xmlVersion;
    private final String officeVersion;
    @GuardedBy("this")
    private OOXML xml;

    private XMLFormatVersion(XMLVersion xmlVersion, String officeVersion) {
        if (xmlVersion == null)
            throw new NullPointerException("Null XMLVersion");
        this.xmlVersion = xmlVersion;
        this.officeVersion = officeVersion;
        // ATTN cannot set this.xml now since OOXML depends on us and is not yet fully functional
        // ATTN cannot set this.xml in OOXML constructor since OOo generates faulty documents (even
        // for XMLVersion.OOo documents it sets the office:version to the ODF one)
        this.xml = null;
    }

    public final XMLVersion getXMLVersion() {
        return this.xmlVersion;
    }

    public final String getOfficeVersion() {
        return this.officeVersion;
    }

    public synchronized final OOXML getXML() {
        if (this.xml == null)
            this.xml = OOXML.get(this);
        return this.xml;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.officeVersion == null) ? 0 : this.officeVersion.hashCode());
        result = prime * result + this.xmlVersion.hashCode();
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
        final XMLFormatVersion other = (XMLFormatVersion) obj;
        return this.xmlVersion == other.xmlVersion && CompareUtils.equals(this.officeVersion, other.officeVersion);
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.getXMLVersion() + " version " + this.getOfficeVersion();
    }
}
