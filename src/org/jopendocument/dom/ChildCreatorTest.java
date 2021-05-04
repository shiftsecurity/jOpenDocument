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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.jdom.Element;

public class ChildCreatorTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreate() throws Exception {
        final XMLVersion ns = XMLVersion.getOD();
        final Element parent = new Element("p", ns.getTEXT());
        final ChildCreator childCreator = new ChildCreator(parent, new Element("un", ns.getTEXT()), new Element("deux", ns.getTEXT()), new Element("trois", ns.getSTYLE()), new Element("quatre",
                ns.getOFFICE()));
        try {
            childCreator.getChild(ns.getOFFICE(), "un", true);
            fail("un is not in office namespace");
        } catch (IllegalArgumentException e) {
            // ok
        }
        final Element newChild = childCreator.getChild(ns.getSTYLE(), "trois", true);
        assertEquals(ns.getSTYLE(), newChild.getNamespace());
        assertEquals("trois", newChild.getName());
        // parent was empty
        assertSame(newChild, parent.getChildren().get(0));
        // getChild() only creates once
        assertSame(newChild, childCreator.getChild(ns.getSTYLE(), "trois", true));
        assertEquals(1, parent.getContentSize());
        // except if we use addChild()
        final Element added = childCreator.addChild(ns.getSTYLE(), "trois");
        assertNotSame(newChild, added);
        // added after the existing "trois"
        assertSame(added, parent.getChildren().get(1));
        assertEquals(2, parent.getContentSize());
        parent.getChildren().remove(1);

        childCreator.getChild(ns.getTEXT(), "deux", true);
        // deux was prepended
        assertSame(newChild, parent.getChildren().get(1));
        assertEquals(2, parent.getContentSize());
        // quatre was appended
        assertSame(childCreator.getChild(ns.getOFFICE(), "quatre", true), parent.getChildren().get(2));
        assertEquals(3, parent.getContentSize());
        // deux remains
        assertSame(newChild, parent.getChildren().get(1));
        // un becomes the first
        assertSame(childCreator.getChild(ns.getTEXT(), "un", true), parent.getChildren().get(0));
        assertEquals(4, parent.getContentSize());
    }

    public void testNull() throws Exception {
        final XMLVersion ns = XMLVersion.getOD();
        final Element parent = new Element("body", ns.getOFFICE());
        final Element pElem = new Element("p", ns.getTEXT());
        parent.addContent(pElem);
        final List<Set<Element>> children = new ArrayList<Set<Element>>();
        children.add(Collections.singleton(new Element("forms", ns.getOFFICE())));
        children.add(CollectionUtils.createSet(new Element("tracked-changes", ns.getTEXT()), new Element("tracked-changes", ns.getTABLE())));
        children.add(Collections.singleton(new Element("user-field-decls", ns.getTEXT())));
        children.add(null);
        children.add(Collections.singleton(new Element("named-expressions", ns.getTABLE())));

        final ChildCreator childCreator = ChildCreator.createFromSets(parent, children);
        // find non explicitly defined (use null)
        assertSame(pElem, childCreator.getChild(ns.getTEXT(), "p"));
        // don't create
        assertSame(pElem, childCreator.getChild(ns.getTEXT(), "p", true));

        // find
        assertEquals(null, childCreator.getChild(ns.getTEXT(), "tracked-changes"));
        // create before p
        final Element changesElem = childCreator.getChild(ns.getTEXT(), "tracked-changes", true);
        assertEquals(0, parent.indexOf(changesElem));
        // create between changes and p
        final Element userFieldsElem = childCreator.getChild(ns.getTEXT(), "user-field-decls", true);
        assertEquals(1, parent.indexOf(userFieldsElem));
        // create after p
        final Element namedExpressionsElem = childCreator.getChild(ns.getTABLE(), "named-expressions", true);
        assertEquals(3, parent.indexOf(namedExpressionsElem));

        // find
        assertEquals(null, childCreator.getChild(ns.getTEXT(), "h", false));
        // add after
        final Element hElem = childCreator.getChild(ns.getTEXT(), "h", true);
        assertEquals(Arrays.asList(changesElem, userFieldsElem, pElem, hElem, namedExpressionsElem), parent.getChildren());
        // there's already on text:h
        assertSame(hElem, childCreator.getChild(ns.getTEXT(), "h", true));
        // add a new text:p after the whole (not after the first matching)
        final Element p2Elem = childCreator.addChild(ns.getTEXT(), "p");
        assertEquals(Arrays.asList(changesElem, userFieldsElem, pElem, hElem, p2Elem, namedExpressionsElem), parent.getChildren());
    }
}
