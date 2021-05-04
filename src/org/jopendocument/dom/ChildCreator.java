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

import org.jopendocument.util.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A helper to create children in the schema order.
 * 
 * @author Sylvain CUAZ
 */
public class ChildCreator {

    /**
     * Create a new instance to add children to <code>content</code>. This method accepts a list of
     * set to be able to specify more than one element for the same index.
     * 
     * @param content the parent element where to create children.
     * @param children the elements in the order they must appear inside <code>content</code>. One
     *        <code>null</code> can be included, it matches any element.
     * @return a new instance.
     * @throws IllegalArgumentException if a child is specified more than once in
     *         <code>children</code> or if a set is empty.
     * @see #ChildCreator(Element, List)
     */
    static public ChildCreator createFromSets(final Element content, final List<Set<Element>> children) {
        return new ChildCreator(content, indexListOfSet(children));
    }

    static private void put(final Map<Tuple2<Namespace, String>, Integer> res, final Tuple2<Namespace, String> t, final int index) {
        if (res.put(t, index) != null)
            throw new IllegalArgumentException("More than one " + t);
    }

    static private Map<Tuple2<Namespace, String>, Integer> indexListOfSet(final List<Set<Element>> children) {
        final Map<Tuple2<Namespace, String>, Integer> res = new HashMap<Tuple2<Namespace, String>, Integer>();
        final ListIterator<Set<Element>> iter = children.listIterator();
        while (iter.hasNext()) {
            final Set<Element> atIndex = iter.next();
            if (atIndex == null) {
                put(res, null, iter.previousIndex());
            } else if (atIndex.size() == 0) {
                throw new IllegalArgumentException("Empty set");
            } else {
                for (final Element child : atIndex)
                    put(res, Tuple2.create(child.getNamespace(), child.getName()), iter.previousIndex());
            }
        }
        return res;
    }

    static private Map<Tuple2<Namespace, String>, Integer> indexList(final List<Element> children) {
        final Map<Tuple2<Namespace, String>, Integer> res = new HashMap<Tuple2<Namespace, String>, Integer>();
        final ListIterator<Element> iter = children.listIterator();
        while (iter.hasNext()) {
            final Element child = iter.next();
            if (child == null) {
                put(res, null, iter.previousIndex());
            } else {
                put(res, Tuple2.create(child.getNamespace(), child.getName()), iter.previousIndex());
            }
        }
        return res;
    }

    private final Element content;
    private final Map<Tuple2<Namespace, String>, Integer> elemsOrder;

    // private since we want to be sure that there's no gap in indexes, no null
    // (also avoids us to copy the map)
    private ChildCreator(final Element content, final Map<Tuple2<Namespace, String>, Integer> elemsOrder) {
        if (content == null)
            throw new NullPointerException("null content");
        this.content = content;
        this.elemsOrder = elemsOrder;
        if (this.elemsOrder.get(null) == null)
            this.elemsOrder.put(null, -1);
    }

    /**
     * Create a new instance to add children to <code>content</code>.
     * 
     * @param content the parent element where to create children.
     * @param children the elements in the order they must appear inside <code>content</code>. One
     *        <code>null</code> can be included, it matches any element.
     * @throws IllegalArgumentException if a child is specified more than once in
     *         <code>children</code>.
     * @see #createFromSets(Element, List)
     */
    public ChildCreator(Element content, final List<Element> children) {
        this(content, indexList(children));
    }

    public ChildCreator(Element content, final Element... children) {
        this(content, Arrays.asList(children));
    }

    public final Element getElement() {
        return this.content;
    }

    // *** children

    public final Element getChild(Namespace childNS, String childName) {
        return this.getChild(childNS, childName, false);
    }

    private final int indexOf(Namespace childNS, String childName) {
        return indexOf(Tuple2.create(childNS, childName));
    }

    private final int indexOf(Tuple2<Namespace, String> child) {
        final Integer res = this.elemsOrder.get(child);
        if (res != null)
            return res.intValue();
        else
            // null matches anything
            return this.elemsOrder.get(null).intValue();
    }

    private final int indexOf(final Element elem) {
        return this.indexOf(elem.getNamespace(), elem.getName());
    }

    /**
     * Trouve l'index ou il faut insérer le fils dans ce document.
     * 
     * @param childName le nom du fils que l'on veut insérer.
     * @return l'index ou il faut l'insérer (s'il est déjà présent son index actuel +1).
     * @throws IllegalArgumentException if childName is not in {@link #getChildren()}.
     */
    private final int findInsertIndex(Namespace childNS, String childName) {
        // eg 6, for "master-styles"
        final int idealIndex = indexOf(childNS, childName);
        if (idealIndex == -1)
            throw new IllegalArgumentException(childName + " is unknown.");
        // eg [scripts, font-decls, styles, font-face-decls, automatic-styles, body]
        @SuppressWarnings("unchecked")
        final List<Element> children = this.getElement().getChildren();
        final ListIterator<Element> iter = children.listIterator();
        while (iter.hasNext()) {
            final Element elem = iter.next();
            if (indexOf(elem) > idealIndex)
                // eg indexOf("body") == 7 > 6
                // eg return 5
                return iter.previousIndex();
        }
        return iter.nextIndex();
    }

    /**
     * Insère cet élément à la bonne place. The child should not be already present.
     * 
     * @param child l'élément à insérer, doit être dans TOP_ELEMENTS.
     */
    @SuppressWarnings("unchecked")
    private final void insertChild(Element child) {
        // on ajoute au bon endroit
        this.getElement().getChildren().add(this.findInsertIndex(child.getNamespace(), child.getName()), child);
    }

    public final Element getChild(Element child, boolean create) {
        return this.getChild(child.getNamespace(), child.getName(), create);
    }

    /**
     * Return the asked child, optionally creating it.
     * 
     * @param childNS the namespace of the child.
     * @param childName the name of the child.
     * @param create whether it should be created in case it doesn't exist.
     * @return the asked child or <code>null</code> if it doesn't exist and create is
     *         <code>false</code>
     */
    public final Element getChild(Namespace childNS, String childName, boolean create) {
        Element child = this.getElement().getChild(childName, childNS);
        if (create && child == null) {
            child = new Element(childName, childNS);
            this.insertChild(child);
        }
        return child;
    }

    public final Element addChild(Namespace childNS, String childName) {
        final Element child = new Element(childName, childNS);
        this.insertChild(child);
        return child;
    }

    public final void setChild(Element elem) {
        this.getElement().removeChildren(elem.getName(), elem.getNamespace());
        this.insertChild(elem);
    }

}