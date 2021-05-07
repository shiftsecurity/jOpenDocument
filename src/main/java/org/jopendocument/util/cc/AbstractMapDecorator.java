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

import org.jopendocument.util.CopyUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapDecorator<K, V> implements Map<K, V>, Cloneable {

    // not final because of clone()
    private Map<K, V> delegate;

    public AbstractMapDecorator(final Map<K, V> delegate) {
        super();
        this.delegate = delegate;
    }

    protected final Map<K, V> getDelegate() {
        return this.delegate;
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public V get(final Object key) {
        return this.delegate.get(key);
    }

    @Override
    public V put(final K key, final V value) {
        return this.delegate.put(key, value);
    }

    @Override
    public V remove(final Object key) {
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        this.delegate.putAll(m);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.delegate.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return this.delegate.entrySet();
    }

    @Override
    public boolean equals(final Object o) {
        return this.delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.delegate.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            final Map<K, V> delegate = CopyUtils.copy(this.delegate);
            @SuppressWarnings("unchecked")
            final AbstractMapDecorator<K, V> res = (AbstractMapDecorator<K, V>) super.clone();
            res.delegate = delegate;
            return res;
        } catch (CloneNotSupportedException e) {
            throw e;
        } catch (Exception e) {
            final CloneNotSupportedException exn = new CloneNotSupportedException();
            exn.initCause(e);
            throw exn;
        }
    }
}
