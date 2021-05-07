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

import org.jopendocument.util.CollectionMap2Itf.SetMapItf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetMap<K, V> extends CollectionMap2<K, Set<V>, V> implements SetMapItf<K, V> {

    static private class Unmodifiable<K, V> extends UnmodifiableCollectionMap<K, Set<V>, V> implements SetMapItf<K, V> {
        Unmodifiable(CollectionMap2Itf<K, Set<V>, V> delegate) {
            super(delegate, new UnmodifiableCollectionMap.UnmodifiableMap<K, Set<V>>(delegate) {
                @Override
                protected Set<V> toUnmodifiable(Set<V> coll) {
                    return Collections.unmodifiableSet(coll);
                }
            });
        }
    }

    static public <K, V> SetMapItf<K, V> unmodifiableMap(SetMapItf<K, V> map) {
        return new Unmodifiable<K, V>(map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static SetMap EMPTY = new SetMap(Collections.emptyMap(), Mode.NULL_FORBIDDEN) {
        @Override
        public SetMap clone() {
            // this instance is immutable and Collections classes might be cloneable
            return this;
        }
    };

    @SuppressWarnings("unchecked")
    public static <K, V> SetMap<K, V> empty() {
        return EMPTY;
    }

    public static <K, V> SetMap<K, V> singleton(K key, V... values) {
        return singleton(key, Arrays.asList(values));
    }

    public static <K, V> SetMap<K, V> singleton(K key, Collection<? extends V> values) {
        return singleton(key, new HashSet<V>(values), false);
    }

    private static <K, V> SetMap<K, V> singleton(K key, Set<V> values, final boolean immutable) {
        final Set<V> coll = immutable ? values : Collections.unmodifiableSet(values);
        return new SetMap<K, V>(Collections.singletonMap(key, coll), DEFAULT_MODE) {
            @Override
            public SetMap<K, V> clone() {
                // this instance is immutable and Collections classes might be cloneable
                return this;
            }
        };
    }

    // to avoid
    // "Type safety : A generic array of Tuple2<String,Boolean> is created for a varargs parameter"
    public static <K, V> SetMap<K, V> singleton(K key, V value) {
        return singleton(key, Collections.singleton(value), true);
    }

    // static method since one-argument is for copying (see CopyUtils)
    static public <K, V> SetMap<K, V> decorate(final Map<K, Set<V>> m) {
        return new SetMap<K, V>(m, DEFAULT_MODE);
    }

    public SetMap() {
        super();
    }

    public SetMap(int initialCapacity, Mode mode, boolean emptyCollSameAsNoColl) {
        super(initialCapacity, mode, emptyCollSameAsNoColl);
    }

    public SetMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SetMap(Mode mode, boolean emptyCollSameAsNoColl) {
        super(mode, emptyCollSameAsNoColl);
    }

    public SetMap(Mode mode) {
        super(mode);
    }

    public SetMap(Map<K, Set<V>> delegate, Mode mode) {
        super(delegate, mode);
    }

    // ** copy constructors

    public SetMap(CollectionMap2<K, Set<V>, ? extends V> m) {
        super(m);
    }

    public SetMap(Map<? extends K, ? extends Collection<? extends V>> m) {
        super(m);
    }

    @Override
    public Set<V> createCollection(Collection<? extends V> v) {
        return new HashSet<V>(v);
    }

    @Override
    public SetMap<K, V> clone() throws CloneNotSupportedException {
        return (SetMap<K, V>) super.clone();
    }
}
