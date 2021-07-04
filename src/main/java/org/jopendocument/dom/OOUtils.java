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

import org.jopendocument.util.FileUtils;
import org.jopendocument.util.JDOMUtils;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class OOUtils {

    // MAYBE configurable
    static private final String[] executables = { "ooffice2", "ooffice", "soffice" };

    /**
     * Open the passed file in OpenOffice.
     * 
     * @param f the file to open.
     * @throws IOException if openoffice could not be opened
     */
    static public void open(File f) throws IOException {
        FileUtils.open(f, executables);
    }

    /**
     * Return a builder who doesn't load DTD.
     * 
     * @return a builder who doesn't load DTD.
     */
    static public SAXBuilder getBuilder() {
        SAXBuilder builder = new SAXBuilder();
        builder.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(String publicId, String systemId) {
                // les dtd sont "vidées"
                if (systemId.endsWith(".dtd")) {
                    InputSource in = new InputSource();
                    in.setCharacterStream(new StringReader(""));
                    return in;
                } else
                    return null;
            }
        });
        return builder;
    }

    /**
     * Return a builder who loads DTD.
     * 
     * @return a builder who loads DTD.
     */
    static public SAXBuilder getBuilderLoadDTD() {
        SAXBuilder builder = new SAXBuilder() {
            public Document build(InputSource in) throws JDOMException, IOException {
                in.setSystemId(OOUtils.class.getResource("oofficeDTDs/").toExternalForm());
                return super.build(in);
            }
        };
        return builder;
    }

    static public Document parseDocument(String doc) throws JDOMException {
        return JDOMUtils.parseStringDocument(doc, getBuilder());
    }

    /**
     * Create an XPath with OO namespaces.
     * 
     * @param path the xpath to create.
     * @param version XML version.
     * @return the specified XPath.
     * @throws JDOMException if the path is malformed.
     */
    public static final XPath getXPath(String path, XMLVersion version) throws JDOMException {
        final XPath xp = XPath.newInstance(path);
        for (final Namespace ns : version.getALL()) {
            xp.addNamespace(ns);
        }
        return xp;
    }

    /**
     * Encode to the color data type, see section 18.3.8 of OpenDocument-v1.2-part1.
     * 
     * @param color a color
     * @return the string encoded version.
     */
    public static String encodeRGB(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }

    /**
     * Decode a color.
     * 
     * @param color a RGB color in notation "#rrggbb", can be <code>null</code>.
     * @return the corresponding color.
     * @see #encodeRGB(Color)
     */
    public static Color decodeRGB(String color) {
        return color == null ? null : Color.decode(color.trim());
    }
}