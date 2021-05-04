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

import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.SimpleXMLPath.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * A step in {@link SimpleXMLPath}. There's only 2 types of step, those which go to {@link Element}
 * and those which go to {@link Attribute}. Thread-safe if its {@link #getPredicate() predicate} is.
 * 
 * @author Sylvain CUAZ
 * 
 * @param <T> type of items after the step.
 */
public final class Step<T> {

    public enum Axis {
        attribute, child, ancestor, descendantOrSelf
    }

    private static final Step<Attribute> ANY_ATTRIBUTE = createAttributeStep(null, null);
    private static final Step<Element> ANY_CHILD_ELEMENT = createElementStep(Axis.child, null, null);

    /**
     * Return a step that match any attribute.
     * 
     * @return the equivalent of <code>@*</code>.
     */
    public static Step<Attribute> getAnyAttributeStep() {
        return ANY_ATTRIBUTE;
    }

    public static Step<Attribute> createAttributeStep(final String name, final String ns) {
        return createAttributeStep(name, ns, null);
    }

    public static Step<Attribute> createAttributeStep(final String name, final String ns, final IPredicate<Attribute> pred) {
        return new Step<Attribute>(Axis.attribute, name, ns, Attribute.class, pred);
    }

    /**
     * Return a step that match any child element.
     * 
     * @return the equivalent of <code>*</code>.
     */
    public static Step<Element> getAnyChildElementStep() {
        return ANY_CHILD_ELEMENT;
    }

    public static Step<Element> createElementStep(final String name, final String ns) {
        return createElementStep(name, ns, null);
    }

    public static Step<Element> createElementStep(final String name, final String ns, final IPredicate<Element> pred) {
        return createElementStep(Axis.child, name, ns, pred);
    }

    public static Step<Element> createElementStep(final Axis axis, final String name) {
        return createElementStep(axis, name, null);
    }

    public static Step<Element> createElementStep(final Axis axis, final String name, final String ns) {
        return createElementStep(axis, name, ns, null);
    }

    public static Step<Element> createElementStep(final Axis axis, final String name, final String ns, final IPredicate<Element> pred) {
        return new Step<Element>(axis, name, ns, Element.class, pred);
    }

    private final Axis axis;
    private final String name;
    private final String ns;
    private final Class<T> clazz;
    private final Node<T> node;
    private final IPredicate<T> pred;

    private Step(Axis axis, final String name, final String ns, final Class<T> clazz, IPredicate<T> pred) {
        super();
        this.axis = axis;
        this.name = name;
        this.ns = ns;
        this.clazz = clazz;
        this.node = Node.get(this.clazz);
        this.pred = pred;
    }

    public final Axis getAxis() {
        return this.axis;
    }

    public final String getName() {
        return this.name;
    }

    public final IPredicate<T> getPredicate() {
        return this.pred;
    }

    protected final Namespace getNS(final Element elem) {
        return this.ns == null ? Namespace.NO_NAMESPACE : elem.getNamespace(this.ns);
    }

    protected final T evaluate(final Object node) {
        if (node == null)
            return null;
        final T n = this.clazz.cast(node);
        final T filtered = this.node.filter(n, this.getName(), this.ns);
        if (filtered == null)
            return null;
        return this.pred == null || this.pred.evaluateChecked(filtered) ? filtered : null;
    }

    protected final void add(final Object node, final List<T> l) {
        final T filtered = evaluate(node);
        if (filtered != null)
            l.add(filtered);
    }

    final <U> List<T> nextNodes(final Node<U> n, final U jdom) {
        return this.nextNodes(new ArrayList<T>(), n, jdom);
    }

    final <U> List<T> nextNodes(final List<T> res, final Node<U> n, final U jdom) {
        n.nextNodes(res, jdom, this);
        return res;
    }

    final <U> List<T> nextNodes(List<U> jdom) {
        final int stop = jdom.size();
        if (stop == 0)
            return Collections.emptyList();

        final Node<U> n = Node.get(jdom.get(0));
        // nextNodes adding to res is far speedier than creating a list each call
        final List<T> res = new ArrayList<T>();
        // since we're using ArrayList it is faster to use for than to use iterator
        for (int i = 0; i < stop; i++) {
            nextNodes(res, n, jdom.get(i));
        }
        return res;
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + " " + this.getAxis() + " " + this.ns + ":" + this.name;
    }
}