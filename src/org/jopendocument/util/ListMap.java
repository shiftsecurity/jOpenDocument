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

import org.jopendocument.util.CollectionMap2Itf.ListMapItf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListMap<K, V> extends ListAbstractMap<K, List<V>, V> implements ListMapItf<K, V> {

    static private class Unmodifiable<K, V> extends UnmodifiableCollectionMap<K, List<V>, V> implements ListMapItf<K, V> {
        Unmodifiable(CollectionMap2Itf<K, List<V>, V> delegate) {
            super(delegate, new UnmodifiableCollectionMap.UnmodifiableMap<K, List<V>>(delegate) {
                @Override
                protected List<V> toUnmodifiable(List<V> coll) {
                    return Collections.unmodifiableList(coll);
                }
            });
        }
    }

    static public <K, V> ListMapItf<K, V> unmodifiableMap(ListMapItf<K, V> map) {
        return new Unmodifiable<K, V>(map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static ListMap EMPTY = new ListMap(Collections.emptyMap(), Mode.NULL_FORBIDDEN) {
        @Override
        public ListMap clone() {
            // this instance is immutable and Collections classes might be cloneable
            return this;
        }
    };

    @SuppressWarnings("unchecked")
    public static <K, V> ListMap<K, V> empty() {
        return EMPTY;
    }

    public static <K, V> ListMap<K, V> singleton(K key, V... values) {
        return singleton(key, Arrays.asList(values), true);
    }

    public static <K, V> ListMap<K, V> singleton(K key, Collection<? extends V> values) {
        return singleton(key, new ArrayList<V>(values), false);
    }

    private static <K, V> ListMap<K, V> singleton(K key, List<V> values, final boolean immutable) {
        final List<V> coll = immutable ? values : Collections.unmodifiableList(values);
        return new ListMap<K, V>(Collections.singletonMap(key, coll), DEFAULT_MODE) {
            @Override
            public ListMap<K, V> clone() {
                // this instance is immutable and Collections classes might be cloneable
                return this;
            }
        };
    }

    // to avoid
    // "Type safety : A generic array of Tuple2<String,Boolean> is created for a varargs parameter"
    public static <K, V> ListMap<K, V> singleton(K key, V value) {
        return singleton(key, Collections.singletonList(value), true);
    }

    // static method since one-argument is for copying (see CopyUtils)
    static public <K, V> ListMap<K, V> decorate(final Map<K, List<V>> m) {
        return new ListMap<K, V>(m, DEFAULT_MODE);
    }

    public ListMap() {
        super();
    }

    public ListMap(Map<K, List<V>> delegate, Mode mode) {
        super(delegate, mode);
    }

    public ListMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ListMap(int initialCapacity, Mode mode, Boolean emptyCollSameAsNoColl) {
        super(initialCapacity, mode, emptyCollSameAsNoColl);
    }

    // ** copy constructors

    public ListMap(CollectionMap2<K, List<V>, ? extends V> m) {
        super(m);
    }

    public ListMap(Map<? extends K, ? extends Collection<? extends V>> m) {
        super(m);
    }

    public ListMap(Map<K, List<V>> delegate, Map<? extends K, ? extends Collection<? extends V>> m) {
        super(delegate, m);
    }

    @Override
    public List<V> createCollection(Collection<? extends V> v) {
        return new ArrayList<V>(v);
    }

    @Override
    public ListMap<K, V> clone() throws CloneNotSupportedException {
        return (ListMap<K, V>) super.clone();
    }
}
