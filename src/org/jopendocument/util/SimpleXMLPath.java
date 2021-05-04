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

package org.jopendocument.util;

import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.Step.Axis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.xpath.XPath;

/**
 * Like an {@link XPath} with less features but a greater speed. Thread-safe if its {@link Step
 * steps} are.
 * 
 * @author Sylvain CUAZ
 * 
 * @param <T> type of result.
 */
public final class SimpleXMLPath<T> {

    private static final SimpleXMLPath<Attribute> ALL_ATTRIBUTES = allAttributes(null, null);
    private static final SimpleXMLPath<Element> ALL_ELEMENTS = allElements(null, null);

    public static <T> SimpleXMLPath<T> create(final List<Step<?>> steps, final Step<T> lastStep) {
        return new SimpleXMLPath<T>(Collections.unmodifiableList(new ArrayList<Step<?>>(steps)), lastStep);
    }

    public static <T> SimpleXMLPath<T> create(final Step<T> lastStep) {
        return new SimpleXMLPath<T>(Collections.<Step<?>> emptyList(), lastStep);
    }

    public static <T> SimpleXMLPath<T> create(final Step<?> first, final Step<T> lastStep) {
        return new SimpleXMLPath<T>(Collections.<Step<?>> singletonList(first), lastStep);
    }

    public static <T> SimpleXMLPath<T> create(final Step<?> first, final Step<?> second, final Step<T> lastStep) {
        return new SimpleXMLPath<T>(Arrays.<Step<?>> asList(first, second), lastStep);
    }

    /**
     * Create a path searching for all descendant attributes. The returned instance is immutable.
     * 
     * @return a path searching attributes in all {@link Axis#descendantOrSelf} elements.
     */
    public static SimpleXMLPath<Attribute> allAttributes() {
        return ALL_ATTRIBUTES;
    }

    /**
     * Create a path searching for all descendant attributes with the passed name and namespace.
     * I.e. in XPath this would be ".//@ns:name". The returned instance is immutable.
     * 
     * @param name the name of attributes.
     * @param ns the namespace of attributes.
     * @return a path searching attributes in all {@link Axis#descendantOrSelf} elements.
     */
    public static SimpleXMLPath<Attribute> allAttributes(final String name, final String ns) {
        return create(Step.createElementStep(Axis.descendantOrSelf, null), Step.createAttributeStep(name, ns));
    }

    /**
     * Create a path searching for all descendant elements. The returned instance is immutable.
     * 
     * @return a path searching all {@link Axis#descendantOrSelf} elements.
     */
    public static SimpleXMLPath<Element> allElements() {
        return ALL_ELEMENTS;
    }

    /**
     * Create a path searching for all descendant elements with the passed name and namespace. I.e.
     * in XPath this would be ".//ns:name". The returned instance is immutable.
     * 
     * @param name the name of elements.
     * @param ns the namespace of elements.
     * @return a path searching all {@link Axis#descendantOrSelf} elements.
     */
    public static SimpleXMLPath<Element> allElements(final String name, final String ns) {
        return create(Step.createElementStep(Axis.descendantOrSelf, name, ns));
    }

    private final List<Step<?>> items;
    private final Step<T> lastItem;

    // private since we don't copy steps
    private SimpleXMLPath(final List<Step<?>> steps, Step<T> lastStep) {
        this.lastItem = lastStep;
        this.items = steps;
    }

    // return Element or Attribute
    public final T selectSingleNode(final Object n) {
        return selectSingleNode(n, this.items);
    }

    private final T selectSingleNode(final Object currentNode, List<Step<?>> steps) {
        final int size = steps.size();
        if (size > 0) {
            final Step<?> currentStep = steps.get(0);

            final List<?> nextNodes = currentStep.nextNodes(Node.get(currentNode), currentNode);
            // MAYBE add an index argument instead of creating a sublist
            final List<Step<?>> nextSteps = steps.subList(1, size);
            final int stop = nextNodes.size();
            for (int i = 0; i < stop; i++) {
                final T finalNode = this.selectSingleNode(nextNodes.get(i), nextSteps);
                if (finalNode != null)
                    return finalNode;
            }
            return null;
        } else {
            return CollectionUtils.getFirst(this.lastItem.nextNodes(Node.get(currentNode), currentNode));
        }
    }

    public final List<T> selectNodes(final Object n) {
        return this.selectNodes(Collections.singletonList(n));
    }

    public final List<T> selectNodes(final List<?> nodes) {
        List<?> currentNodes = nodes;
        final int stop = this.items.size();
        for (int i = 0; i < stop; i++) {
            final Step<?> currentStep = this.items.get(i);

            final List<?> nextNodes = currentStep.nextNodes(currentNodes);
            if (nextNodes.isEmpty())
                return Collections.emptyList();
            else
                currentNodes = nextNodes;

        }
        return this.lastItem.nextNodes(currentNodes);
    }

    // encapsulate differences about JDOM nodes
    static abstract class Node<T> {

        static final Node<Element> elem = new ElementNode();
        static final Node<Attribute> attr = new AttributeNode();

        @SuppressWarnings("unchecked")
        public static <TT> Node<TT> get(TT o) {
            if (o instanceof Attribute)
                return (Node<TT>) attr;
            else if (o instanceof Element)
                return (Node<TT>) elem;
            else
                throw new IllegalArgumentException("unknown Node: " + o);
        }

        @SuppressWarnings("unchecked")
        public static <TT> Node<TT> get(Class<TT> clazz) {
            if (clazz == Attribute.class)
                return (Node<TT>) attr;
            else if (clazz == Element.class)
                return (Node<TT>) elem;
            else
                throw new IllegalArgumentException("unknown Node: " + clazz);
        }

        public abstract <S> void nextNodes(final List<S> res, final T node, final Step<S> step);

        // viva jdom who doesn't have a common interface for getName() and getNS()
        abstract T filter(final T elem, final String name, final String ns);

        @Override
        public final String toString() {
            return this.getClass().getSimpleName();
        }
    }

    static class AttributeNode extends Node<Attribute> {

        @Override
        public <S> void nextNodes(final List<S> res, final Attribute node, final Step<S> step) {
            if (step.getAxis() == Axis.ancestor) {
                step.add(node.getParent(), res);
            } else
                throw new IllegalArgumentException(this + " cannot take the passed step: " + step);
        }

        @Override
        Attribute filter(Attribute elem, String name, String ns) {
            if (elem == null)
                return null;
            if (name != null && !name.equals(elem.getName()))
                return null;
            if (ns != null && !ns.equals(elem.getNamespacePrefix()))
                return null;
            return elem;
        }
    }

    static class ElementNode extends Node<Element> {

        @SuppressWarnings("unchecked")
        @Override
        public <S> void nextNodes(final List<S> res, final Element node, final Step<S> step) {
            final Axis axis = step.getAxis();
            if (axis == Axis.ancestor) {
                step.add(node.getParent(), res);
            } else if (axis == Axis.attribute) {
                final List<?> attributes = node.getAttributes();
                final int stop = attributes.size();
                for (int i = 0; i < stop; i++) {
                    step.add(attributes.get(i), res);
                }
            } else if (axis == Axis.child) {
                // jdom : traversal through the List is best done with a Iterator
                for (final Object o : node.getChildren()) {
                    step.add(o, res);
                }
            } else if (axis == Axis.descendantOrSelf) {
                step.add(node, res);
                final Iterator<S> iter = node.getDescendants(new Filter() {
                    @Override
                    public boolean matches(Object obj) {
                        if (!(obj instanceof Element))
                            return false;
                        return step.evaluate(obj) != null;
                    }
                });
                while (iter.hasNext()) {
                    res.add(iter.next());
                }
            } else
                throw new IllegalArgumentException(this + " cannot take the passed step: " + axis);
        }

        @Override
        Element filter(Element elem, String name, String ns) {
            if (elem == null)
                return null;
            if (name != null && !name.equals(elem.getName()))
                return null;
            if (ns != null && !ns.equals(elem.getNamespacePrefix()))
                return null;
            return elem;
        }
    }
}
