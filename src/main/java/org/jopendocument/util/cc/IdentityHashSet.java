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

package org.jopendocument.util.cc;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;

/**
 * This class implements the <tt>Set</tt> interface with a hash table, using reference-equality in
 * place of object-equality when comparing items. In other words, in an <tt>IdentityHashSet</tt>,
 * two keys <tt>k1</tt> and <tt>k2</tt> are considered equal if and only if <tt>(k1==k2)</tt>. (In
 * normal <tt>Set</tt> implementations (like <tt>HashSet</tt>) two keys <tt>k1</tt> and <tt>k2</tt>
 * are considered equal if and only if <tt>(k1==null ? k2==null : k1.equals(k2))</tt>.)
 * 
 * @param <E> the type of elements maintained by this set
 */
public final class IdentityHashSet<E> extends AbstractSet<E> implements IdentitySet<E>, Cloneable {

    private final IdentityHashMap<E, Object> map;

    public IdentityHashSet() {
        this.map = new IdentityHashMap<E, Object>();
    }

    public IdentityHashSet(int expectedMaxSize) {
        this.map = new IdentityHashMap<E, Object>(expectedMaxSize);
    }

    public IdentityHashSet(Collection<? extends E> c) {
        this.map = new IdentityHashMap<E, Object>(Math.max((int) (c.size() / .75f) + 1, 16));
        this.addAll(c);
    }

    @Override
    public boolean add(E e) {
        if (this.contains(e))
            return false;
        else {
            this.map.put(e, null);
            return true;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // let the super which calls add() since we don't want to build a map to use Map.addAll()
        return super.addAll(c);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public boolean contains(Object o) {
        return this.map.keySet().contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.map.keySet().containsAll(c);
    }

    @Override
    public boolean remove(Object o) {
        return this.map.keySet().remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.map.keySet().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.map.keySet().retainAll(c);
    }

    @Override
    public Object[] toArray() {
        return this.map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.map.keySet().toArray(a);
    }

    /**
     * Returns a shallow copy of this <tt>HashSet</tt> instance: the elements themselves are not
     * cloned.
     * 
     * @return a shallow copy of this set
     */
    public Object clone() {
        return new IdentityHashSet<E>(this);
    }

}
