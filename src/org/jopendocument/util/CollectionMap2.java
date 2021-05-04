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

import org.jopendocument.util.cc.AbstractMapDecorator;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Allow to map keys to collections. This map always allow <code>null</code> items for mapped
 * collections, but it may restrict null collections.
 * 
 * @author Sylvain
 * 
 * @param <K> the type of keys maintained by this map
 * @param <C> the type of mapped collections
 * @param <V> the type of elements of the collections
 */
public abstract class CollectionMap2<K, C extends Collection<V>, V> extends AbstractMapDecorator<K, C> implements Cloneable, CollectionMap2Itf<K, C, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;

    private static final String toStr(final Object o) {
        return o == null ? "null" : "'" + o + "'";
    }

    public static enum Mode {
        /**
         * Mapped collections cannot be <code>null</code>.
         */
        NULL_FORBIDDEN,
        /**
         * Mapped collections can be <code>null</code>, but some methods may throw
         * {@link NullPointerException}.
         * 
         * @see CollectionMap2#addAll(Object, Collection)
         * @see CollectionMap2#removeAll(Object, Collection)
         */
        NULL_ALLOWED,
        /**
         * Mapped collections can be <code>null</code>, meaning every possible item. Thus no method
         * throws {@link NullPointerException}.
         */
        NULL_MEANS_ALL
    }

    static protected final Mode DEFAULT_MODE = Mode.NULL_FORBIDDEN;
    static private final Boolean DEFAULT_emptyCollSameAsNoColl = null;

    private final boolean emptyCollSameAsNoColl;
    private final Mode mode;
    private transient Collection<V> allValues = null;

    public CollectionMap2() {
        this(DEFAULT_MODE);
    }

    public CollectionMap2(final Mode mode) {
        this(mode, DEFAULT_emptyCollSameAsNoColl);
    }

    public CollectionMap2(final Map<K, C> delegate, final Mode mode) {
        this(delegate, mode, DEFAULT_emptyCollSameAsNoColl);
    }

    public CollectionMap2(final Mode mode, final Boolean emptyCollSameAsNoColl) {
        this(DEFAULT_INITIAL_CAPACITY, mode, emptyCollSameAsNoColl);
    }

    public CollectionMap2(final int initialCapacity) {
        this(initialCapacity, DEFAULT_MODE, DEFAULT_emptyCollSameAsNoColl);
    }

    public CollectionMap2(final int initialCapacity, final Mode mode, final Boolean emptyCollSameAsNoColl) {
        this(new HashMap<K, C>(initialCapacity), mode, emptyCollSameAsNoColl);
    }

    /**
     * Create a new instance with the passed delegate. The delegate is *not* cleared, this allows to
     * decorate an existing Map but it also means that the existing collections might not be the
     * exact same type as those returned by {@link #createCollection(Collection)}.
     * 
     * @param delegate the map to use, it must not be modified afterwards.
     * @param mode how to handle null values.
     * @param emptyCollSameAsNoColl for {@link #getCollection(Object)} : whether the lack of an
     *        entry is the same as an entry with an empty collection, can be <code>null</code>.
     */
    public CollectionMap2(final Map<K, C> delegate, final Mode mode, final Boolean emptyCollSameAsNoColl) {
        super(delegate);
        if (mode == null)
            throw new NullPointerException("Null mode");
        this.mode = mode;
        this.emptyCollSameAsNoColl = emptyCollSameAsNoColl == null ? mode == Mode.NULL_MEANS_ALL : emptyCollSameAsNoColl;
        checkMode();
    }

    private final void checkMode() {
        assert this.mode != null : "Called too early";
        if (this.mode == Mode.NULL_FORBIDDEN && this.containsValue(null))
            throw new IllegalArgumentException("Null collection");
    }

    // ** copy constructors

    public CollectionMap2(final CollectionMap2<K, C, ? extends V> m) {
        this(CopyUtils.copy(m.getDelegate()), m);
    }

    public CollectionMap2(final Map<? extends K, ? extends Collection<? extends V>> m) {
        this(new HashMap<K, C>(m.size()), m);
    }

    /**
     * Create a new instance with the passed delegate and filling it with the passed map.
     * 
     * @param delegate the map to use, it will be cleared and must not be modified afterwards.
     * @param m the values to put in this, if it's an instance of {@link CollectionMap2} the
     *        {@link #getMode() mode} and {@link #isEmptyCollSameAsNoColl()} will be copied as well.
     */
    public CollectionMap2(final Map<K, C> delegate, final Map<? extends K, ? extends Collection<? extends V>> m) {
        // don't use super(Map) since it doesn't copy the collections
        // also its type is more restrictive
        super(delegate);
        if (m instanceof CollectionMap2) {
            final CollectionMap2<?, ?, ?> collM = (CollectionMap2<?, ?, ?>) m;
            this.mode = collM.getMode();
            this.emptyCollSameAsNoColl = collM.isEmptyCollSameAsNoColl();
        } else {
            this.mode = DEFAULT_MODE;
            this.emptyCollSameAsNoColl = this.mode == Mode.NULL_MEANS_ALL;
        }
        // delegate might not contain the same instances of collections (i.e. LinkedList vs
        // ArrayList)
        this.clear();
        this.putAllCollections(m);
    }

    @Override
    public final Mode getMode() {
        return this.mode;
    }

    @Override
    public final boolean isEmptyCollSameAsNoColl() {
        return this.emptyCollSameAsNoColl;
    }

    public final C getNonNullIfMissing(final Object key) {
        return this.get(key, false, true);
    }

    @Override
    public final C getNonNull(final K key) {
        return this.get(key, false, false);
    }

    private final C getNonNullColl(final C res) {
        return res == null ? this.createCollection(Collections.<V> emptySet()) : res;
    }

    /**
     * Get the collection mapped to the passed key. Note : <code>get(key, true, true)</code> is
     * equivalent to <code>get(key)</code>.
     * 
     * @param key the key whose associated value is to be returned.
     * @param nullIfMissing only relevant if the key isn't contained : if <code>true</code>
     *        <code>null</code> will be returned, otherwise an empty collection.
     * @param nullIfPresent only relevant if the key is mapped to <code>null</code> : if
     *        <code>true</code> <code>null</code> will be returned, otherwise an empty collection.
     * @return the non {@code null} value to which the specified key is mapped, otherwise
     *         {@code null} or empty collection depending on the other parameters.
     */
    @Override
    public final C get(final Object key, final boolean nullIfMissing, final boolean nullIfPresent) {
        if (nullIfMissing == nullIfPresent) {
            final C res = super.get(key);
            if (res != null || nullIfMissing && nullIfPresent) {
                return res;
            } else {
                assert !nullIfMissing && !nullIfPresent;
                return getNonNullColl(null);
            }
        } else if (nullIfMissing) {
            assert !nullIfPresent;
            if (!this.containsKey(key))
                return null;
            else
                return getNonNullColl(super.get(key));
        } else {
            assert !nullIfMissing && nullIfPresent;
            if (this.containsKey(key))
                return super.get(key);
            else
                return getNonNullColl(null);
        }
    }

    @Override
    public final C getCollection(final Object key) {
        return this.get(key, !this.isEmptyCollSameAsNoColl(), true);
    }

    /**
     * Returns a {@link Collection} view of all the values contained in this map. The collection is
     * backed by the map, so changes to the map are reflected in the collection, and vice-versa. If
     * the map is modified while an iteration over the collection is in progress (except through the
     * iterator's own <tt>remove</tt> operation), the results of the iteration are undefined. The
     * collection supports element removal, which removes the corresponding values from the map, via
     * the <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations. Note that it doesn't remove entries only
     * values : keySet() doesn't change, use {@link #removeAllEmptyCollections()} and
     * {@link #removeAllNullCollections()} afterwards. It does not support the <tt>add</tt> or
     * <tt>addAll</tt> operations.
     * 
     * @return a view all values in all entries, <code>null</code> collections are ignored.
     */
    @Override
    public Collection<V> allValues() {
        if (this.allValues == null)
            this.allValues = new AllValues();
        return this.allValues;
    }

    private final class AllValues extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new AllValuesIterator();
        }

        @Override
        public boolean isEmpty() {
            return !iterator().hasNext();
        }

        @Override
        public int size() {
            int compt = 0;
            final Iterator<V> it = iterator();
            while (it.hasNext()) {
                it.next();
                compt++;
            }
            return compt;
        }

        // don't overload clear() to call Map.clear() as this would be incoherent with removeAll() :
        // this last method only removes values, resulting in empty and null collections
    }

    private final class AllValuesIterator implements Iterator<V> {
        private final Iterator<C> mapIterator;
        private Iterator<V> tempIterator;

        private AllValuesIterator() {
            this.mapIterator = values().iterator();
            this.tempIterator = null;
        }

        private boolean searchNextIterator() {
            // tempIterator == null initially and when a collection is null
            while (this.tempIterator == null || !this.tempIterator.hasNext()) {
                if (!this.mapIterator.hasNext()) {
                    return false;
                }
                final C nextCol = this.mapIterator.next();
                this.tempIterator = nextCol == null ? null : nextCol.iterator();
            }
            return true;
        }

        @Override
        public boolean hasNext() {
            return searchNextIterator();
        }

        @Override
        public V next() {
            // search next iterator if necessary
            if (!hasNext())
                throw new NoSuchElementException();
            return this.tempIterator.next();
        }

        @Override
        public void remove() {
            if (this.tempIterator == null)
                throw new IllegalStateException();
            this.tempIterator.remove();
        }
    }

    @Override
    public Set<Map.Entry<K, C>> entrySet() {
        if (getMode() == Mode.NULL_FORBIDDEN) {
            // prevent null insertion
            // MAYBE cache
            return new EntrySet(super.entrySet());
        } else {
            return super.entrySet();
        }
    }

    private final class EntrySet extends AbstractCollection<Map.Entry<K, C>> implements Set<Map.Entry<K, C>> {

        private final Set<Map.Entry<K, C>> delegate;

        public EntrySet(final Set<java.util.Map.Entry<K, C>> delegate) {
            super();
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public boolean contains(final Object o) {
            return this.delegate.contains(o);
        }

        @Override
        public boolean remove(final Object o) {
            return this.delegate.remove(o);
        }

        @Override
        public void clear() {
            this.delegate.clear();
        }

        @Override
        public Iterator<Map.Entry<K, C>> iterator() {
            return new Iterator<Map.Entry<K, C>>() {

                private final Iterator<Map.Entry<K, C>> delegateIter = EntrySet.this.delegate.iterator();

                @Override
                public boolean hasNext() {
                    return this.delegateIter.hasNext();
                }

                @Override
                public Map.Entry<K, C> next() {
                    final Map.Entry<K, C> delegate = this.delegateIter.next();
                    return new Map.Entry<K, C>() {
                        @Override
                        public K getKey() {
                            return delegate.getKey();
                        }

                        @Override
                        public C getValue() {
                            return delegate.getValue();
                        }

                        @Override
                        public C setValue(final C value) {
                            if (value == null)
                                throw new NullPointerException("Putting null collection for " + toStr(getKey()));
                            return delegate.setValue(value);
                        }
                    };
                }

                @Override
                public void remove() {
                    this.delegateIter.remove();
                }
            };
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
        public boolean removeAll(final Collection<?> c) {
            return this.delegate.removeAll(c);
        }
    }

    @Override
    public final C put(final K key, final C value) {
        return this.putCollection(key, value);
    }

    // copy passed collection
    @Override
    public final C putCollection(final K key, final Collection<? extends V> value) {
        if (value == null && this.getMode() == Mode.NULL_FORBIDDEN)
            throw new NullPointerException("Putting null collection for " + toStr(key));
        return super.put(key, value == null ? null : createCollection(value));
    }

    public void putAllCollections(final Map<? extends K, ? extends Collection<? extends V>> m) {
        for (final Map.Entry<? extends K, ? extends Collection<? extends V>> e : m.entrySet()) {
            this.putCollection(e.getKey(), e.getValue());
        }
    }

    // ** add/remove collection

    @Override
    public final boolean add(final K k, final V v) {
        return this.addAll(k, Collections.singleton(v));
    }

    public final boolean addAll(final K k, final V... v) {
        return this.addAll(k, Arrays.asList(v));
    }

    @Override
    public final boolean addAll(final K k, final Collection<? extends V> v) {
        final boolean nullIsAll = getMode() == Mode.NULL_MEANS_ALL;
        if (v == null && !nullIsAll)
            throw new NullPointerException("Adding null collection for " + toStr(k));
        final boolean containsKey = this.containsKey(k);
        if (v == null) {
            return this.putCollection(k, v) != null;
        } else if (!containsKey) {
            this.putCollection(k, v);
            return true;
        } else {
            final C currentColl = this.get(k);
            if (nullIsAll && currentColl == null) {
                // ignore since we can't add something to everything
                return false;
            } else {
                // will throw if currentCol is null
                return currentColl.addAll(v);
            }
        }
    }

    @Override
    public final void merge(final Map<? extends K, ? extends Collection<? extends V>> mm) {
        for (final Map.Entry<? extends K, ? extends Collection<? extends V>> e : mm.entrySet()) {
            this.addAll(e.getKey(), e.getValue());
        }
    }

    @Override
    public final void mergeScalarMap(final Map<? extends K, ? extends V> scalarMap) {
        for (final Map.Entry<? extends K, ? extends V> e : scalarMap.entrySet()) {
            this.add(e.getKey(), e.getValue());
        }
    }

    @Override
    public final boolean remove(final K k, final V v) {
        return this.removeAll(k, Collections.singleton(v));
    }

    @Override
    public final boolean removeAll(final K k, final Collection<? extends V> v) {
        return this.removeAll(k, v, null);
    }

    private final boolean removeAll(final K k, final Collection<? extends V> v, final Iterator<Map.Entry<K, C>> iter) {
        boolean removeK = false;
        boolean modified = false;
        if (getMode() == Mode.NULL_MEANS_ALL) {
            if (v == null) {
                removeK = true;
            } else if (v.size() > 0) {
                final C currentColl = this.get(k);
                if (currentColl == null)
                    throw new IllegalStateException("Cannot remove from all for " + toStr(k));
                modified = currentColl.removeAll(v);
                if (currentColl.isEmpty())
                    removeK = true;
            }
        } else if (this.containsKey(k)) {
            final C currentColl = this.get(k);
            if (currentColl == null && v == null) {
                // since containsKey() and coll == null
                assert getMode() == Mode.NULL_ALLOWED;
                removeK = true;
                // since we just tested containsKey()
                modified = true;
            } else {
                if (v == null)
                    throw new NullPointerException("Removing null collection for " + toStr(k));
                modified = currentColl.removeAll(v);
                if (currentColl.isEmpty())
                    removeK = true;
            }
        }
        if (removeK) {
            if (iter == null) {
                modified |= this.containsKey(k);
                this.remove(k);
            } else {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public final boolean removeAll(final Map<? extends K, ? extends Collection<? extends V>> mm) {
        boolean modified = false;
        // iterate on this to allow mm.removeAll(mm)
        final Iterator<Map.Entry<K, C>> iter = this.entrySet().iterator();
        while (iter.hasNext()) {
            final Map.Entry<K, C> e = iter.next();
            final K key = e.getKey();
            if (mm.containsKey(key))
                modified |= this.removeAll(key, mm.get(key), iter);
        }
        return modified;
    }

    @Override
    public final boolean removeAllScalar(final Map<? extends K, ? extends V> m) {
        boolean modified = false;
        // incompatible types, allowing removal without ConcurrentModificationException
        assert m != this;
        for (final Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            modified |= this.remove(e.getKey(), e.getValue());
        }
        return modified;
    }

    // ** remove empty/null collections

    public final C removeIfEmpty(final K k) {
        final C v = this.get(k);
        if (v != null && v.isEmpty())
            return this.remove(k);
        else
            return null;
    }

    public final void removeIfNull(final K k) {
        if (this.get(k) == null)
            this.remove(k);
    }

    @Override
    public final Set<K> removeAllEmptyCollections() {
        return this.removeAll(true);
    }

    @Override
    public final Set<K> removeAllNullCollections() {
        return this.removeAll(false);
    }

    private final Set<K> removeAll(final boolean emptyOrNull) {
        final Set<K> removed = new HashSet<K>();
        final Iterator<Map.Entry<K, C>> iter = this.entrySet().iterator();
        while (iter.hasNext()) {
            final Map.Entry<K, C> e = iter.next();
            final C val = e.getValue();
            if ((emptyOrNull && val != null && val.isEmpty()) || (!emptyOrNull && val == null)) {
                iter.remove();
                removed.add(e.getKey());
            }
        }
        return removed;
    }

    public abstract C createCollection(Collection<? extends V> v);

    @Override
    public int hashCode() {
        if (this.mode == Mode.NULL_MEANS_ALL)
            return this.hashCodeExact();
        else
            return super.hashCode();
    }

    public int hashCodeExact() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.emptyCollSameAsNoColl ? 1231 : 1237);
        result = prime * result + this.mode.hashCode();
        return result;
    }

    /**
     * Compares the specified object with this map for equality. Except for
     * {@link Mode#NULL_MEANS_ALL}, returns <tt>true</tt> if the given object is also a map and the
     * two maps represent the same mappings (as required by {@link Map}).
     * <code>NULL_MEANS_ALL</code> maps are tested using {@link #equalsExact(Object)}, meaning they
     * don't conform to the Map interface.
     * 
     * @param obj object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     * @see #equalsExact(Object)
     */
    @Override
    public final boolean equals(final Object obj) {
        return this.equals(obj, false);
    }

    /**
     * Compares the specified object with this map for complete equality. This method not only
     * checks for equality of values (as required by {@link Map}) but also the class and attributes.
     * 
     * @param obj object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is exactly equal to this map.
     */
    public final boolean equalsExact(final Object obj) {
        return this.equals(obj, true);
    }

    private final boolean equals(final Object obj, final boolean forceExact) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        assert obj != null;
        final CollectionMap2<?, ?, ?> other = obj instanceof CollectionMap2 ? (CollectionMap2<?, ?, ?>) obj : null;
        if (forceExact || this.mode == Mode.NULL_MEANS_ALL || (other != null && other.mode == Mode.NULL_MEANS_ALL)) {
            if (getClass() != obj.getClass())
                return false;
            // no need to test createCollection(), since values are tested by super.equals()
            return this.emptyCollSameAsNoColl == other.emptyCollSameAsNoColl && this.mode == other.mode && this.getDelegate().getClass() == other.getDelegate().getClass();
        } else {
            return true;
        }
    }

    @Override
    public CollectionMap2<K, C, V> clone() throws CloneNotSupportedException {
        @SuppressWarnings("unchecked")
        final CollectionMap2<K, C, V> result = (CollectionMap2<K, C, V>) super.clone();
        // allValues has a reference to this
        result.allValues = null;
        // clone each collection value
        for (final Map.Entry<K, C> entry : result.entrySet()) {
            final C coll = entry.getValue();
            entry.setValue(createCollection(coll));
        }
        return result;
    }
}