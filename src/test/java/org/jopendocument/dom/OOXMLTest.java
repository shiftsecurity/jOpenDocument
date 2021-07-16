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

import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.text.TextNode;
import org.jopendocument.util.JDOMUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.jdom.Element;
import org.jdom.Namespace;
import org.junit.Test;

public class OOXMLTest extends TestCase {

    private static final OOXML xml = OOXML.getLast(XMLVersion.OD);

    public void testEncodeRT() {
        final Map<String, String> styles = new HashMap<String, String>();
        styles.put("b", "Gras");
        styles.put("gris", "Gris");
        final String expected = "<text:span xmlns:text=\"urn:oasis:names:tc:opendocument:xmlns:text:1.0\">4 &lt; <text:span text:style-name=\"Gras\">5</text:span></text:span>";
        assertEquals(expected, JDOMUtils.output(xml.encodeRT("4 < [b]5[/b]", styles)));

        try {
            JDOMUtils.output(xml.encodeRT("[gris]4 < [b][/gris]5[/b]", styles));
            fail("Mismatch");
        } catch (Exception e) {
            // OK
        }
        try {
            JDOMUtils.output(xml.encodeRT("[gris]4 < [b]5[/b]", styles));
            fail("Missing closing tags");
        } catch (Exception e) {
            // OK
        }

        // test 2 styles, trailing text, embedded
        assertEquals(
                "<text:span xmlns:text=\"urn:oasis:names:tc:opendocument:xmlns:text:1.0\">4 <text:span text:style-name=\"Gris\">&lt; <text:span text:style-name=\"Gras\">5</text:span></text:span> !</text:span>",
                JDOMUtils.output(xml.encodeRT("4 [gris]< [b]5[/b][/gris] !", styles)));
    }

    public void testEncodeWS() {
        final String s = "hi\thow are   you ?\n[That] was >= 3" + TextNode.VERTICAL_TAB_CHAR + "and <=3 spaces";
        final String expected = "<text:span xmlns:text=\"urn:oasis:names:tc:opendocument:xmlns:text:1.0\">hi<text:tab />how are<text:s text:c=\"3\" />you ?<text:line-break />[That] was &gt;= 3<text:line-break />and &lt;=3 spaces</text:span>";
        assertEquals(expected, JDOMUtils.output(xml.encodeWS(s)));
    }

    @Test
    public void testValidation() throws Exception {
        for (final OOXML xml : OOXML.values()) {
            if (!xml.canValidate())
                continue;
            final SpreadSheet empty = SpreadSheet.create(xml.getFormatVersion(), 1, 5, 5);
            final ODPackage pkg = empty.getPackage();

            // the framework doesn't generate foreign content
            assertTrue(pkg.validateSubDocuments(true, false).isEmpty());
            assertTrue(pkg.validateSubDocuments(true, true).isEmpty());

            // add some foreign content
            empty.getBody().addContent(new Element("sansNS"));
            empty.getBody().addContent(new Element("foreignNS", Namespace.getNamespace("foo", "urn:org:documentfoundation:names:experimental:foo")));
            // the content is no longer strictly valid
            assertEquals(Collections.singleton(RootElement.CONTENT.getZipEntry()), pkg.validateSubDocuments(true, false).keySet());
            // but is a valid extended document
            assertTrue(pkg.validateSubDocuments(true, true).isEmpty());

            // add some invalid office content
            empty.getBody().addContent(empty.getSheet(0).getStyleDesc().createElement("foobar"));
            assertEquals(Collections.singleton(RootElement.CONTENT.getZipEntry()), pkg.validateSubDocuments(true, false).keySet());
            assertEquals(Collections.singleton(RootElement.CONTENT.getZipEntry()), pkg.validateSubDocuments(true, true).keySet());
        }
    }

}
