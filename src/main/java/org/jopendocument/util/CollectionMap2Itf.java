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

import org.jopendocument.util.CollectionMap2.Mode;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CollectionMap2Itf<K, C extends Collection<V>, V> extends Map<K, C> {

    public static interface ListMapItf<K, V> extends CollectionMap2Itf<K, List<V>, V> {
    }

    public static interface SetMapItf<K, V> extends CollectionMap2Itf<K, Set<V>, V> {
    }

    public Mode getMode();

    public boolean isEmptyCollSameAsNoColl();

    public C getNonNull(K key);

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
    public C get(Object key, final boolean nullIfMissing, final boolean nullIfPresent);

    public C getCollection(Object key);

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
    public Collection<V> allValues();

    // copy passed collection
    public C putCollection(final K key, final Collection<? extends V> value);

    public boolean add(final K k, final V v);

    public boolean addAll(final K k, final Collection<? extends V> v);

    /**
     * Call {@link #addAll(Object, Collection)} for each entry.
     * 
     * @param mm the collection map to merge.
     */
    public void merge(final Map<? extends K, ? extends Collection<? extends V>> mm);

    /**
     * Call {@link #add(Object, Object)} for each entry.
     * 
     * @param scalarMap the map to merge.
     */
    public void mergeScalarMap(final Map<? extends K, ? extends V> scalarMap);

    public boolean remove(final K k, final V v);

    public boolean removeAll(final K k, final Collection<? extends V> v);

    public boolean removeAll(final Map<? extends K, ? extends Collection<? extends V>> mm);

    public boolean removeAllScalar(final Map<? extends K, ? extends V> m);

    public Set<K> removeAllEmptyCollections();

    public Set<K> removeAllNullCollections();
}
