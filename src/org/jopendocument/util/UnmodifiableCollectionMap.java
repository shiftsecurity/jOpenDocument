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
import org.jopendocument.util.cc.AbstractMapDecorator;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class UnmodifiableCollectionMap<K, C extends Collection<V>, V> extends AbstractMapDecorator<K, C> implements CollectionMap2Itf<K, C, V> {

    // protect Map methods and Collection values. The mutable CollectionMap2Itf methods are
    // protected by the enclosing class
    public static abstract class UnmodifiableMap<K, C extends Collection<?>> extends AbstractMapDecorator<K, C> {

        private final Map<K, C> del;
        private transient Collection<C> values;
        private transient Set<Map.Entry<K, C>> entrySet;

        protected UnmodifiableMap(Map<K, C> delegate) {
            super(Collections.unmodifiableMap(delegate));
            this.del = delegate;
        }

        @Override
        public final C get(final Object key) {
            return toUnmodifiable(super.get(key));
        }

        @Override
        public final Collection<C> values() {
            final Collection<C> vs = this.values;
            return vs != null ? vs : (this.values = new AbstractCollection<C>() {

                @Override
                public boolean contains(Object o) {
                    return UnmodifiableMap.this.containsValue(o);
                }

                @Override
                public Iterator<C> iterator() {
                    final Iterator<Map.Entry<K, C>> iter = entrySet().iterator();
                    return new Iterator<C>() {

                        @Override
                        public boolean hasNext() {
                            return iter.hasNext();
                        }

                        @Override
                        public C next() {
                            // our entrySet() is already unmodifiable
                            return iter.next().getValue();
                        }

                        @Override
                        public void remove() {
                            throw new UnsupportedOperationException();
                        }
                    };
                }

                @Override
                public int size() {
                    return UnmodifiableMap.this.size();
                }
            });
        }

        @Override
        public final Set<Map.Entry<K, C>> entrySet() {
            final Set<Map.Entry<K, C>> es = this.entrySet;
            return es != null ? es : (this.entrySet = new AbstractSet<Map.Entry<K, C>>() {
                private final Set<Map.Entry<K, C>> delegateES = UnmodifiableMap.super.entrySet();

                @Override
                public boolean contains(Object o) {
                    return this.delegateES.contains(o);
                }

                @Override
                public Iterator<Map.Entry<K, C>> iterator() {
                    final Iterator<Map.Entry<K, C>> iter = this.delegateES.iterator();
                    return new Iterator<Map.Entry<K, C>>() {

                        @Override
                        public boolean hasNext() {
                            return iter.hasNext();
                        }

                        @Override
                        public Map.Entry<K, C> next() {
                            final Map.Entry<K, C> orig = iter.next();
                            return new Map.Entry<K, C>() {

                                @Override
                                public K getKey() {
                                    return orig.getKey();
                                }

                                @Override
                                public C getValue() {
                                    return toUnmodifiable(orig.getValue());
                                }

                                @Override
                                public C setValue(C value) {
                                    throw new UnsupportedOperationException();
                                }
                            };
                        }

                        @Override
                        public void remove() {
                            throw new UnsupportedOperationException();
                        }
                    };
                }

                @Override
                public int size() {
                    return UnmodifiableMap.this.size();
                }

                @Override
                public boolean equals(Object o) {
                    return o == this || this.delegateES.equals(o);
                }

                @Override
                public int hashCode() {
                    return this.delegateES.hashCode();
                }
            });
        }

        protected abstract C toUnmodifiable(C coll);
    }

    private final CollectionMap2Itf<K, C, V> del;
    private transient Collection<V> values;

    UnmodifiableCollectionMap(final CollectionMap2Itf<K, C, V> delegate, final UnmodifiableMap<K, C> unmodif) {
        super(unmodif);
        if (unmodif.del != delegate)
            throw new IllegalArgumentException("Mismatched arguments");
        this.del = delegate;
    }

    private final CollectionMap2Itf<K, C, V> getDel() {
        return this.del;
    }

    @Override
    public Mode getMode() {
        return getDel().getMode();
    }

    @Override
    public boolean isEmptyCollSameAsNoColl() {
        return getDel().isEmptyCollSameAsNoColl();
    }

    @Override
    public C getNonNull(final K key) {
        return getDel().getNonNull(key);
    }

    @Override
    public C get(final Object key, final boolean nullIfMissing, final boolean nullIfPresent) {
        return getDel().get(key, nullIfMissing, nullIfPresent);
    }

    @Override
    public C getCollection(final Object key) {
        return getDel().getCollection(key);
    }

    @Override
    public Collection<V> allValues() {
        if (this.values == null) {
            this.values = Collections.unmodifiableCollection(getDel().allValues());
        }
        return this.values;
    }

    @Override
    public C putCollection(final K key, final Collection<? extends V> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(final K k, final V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final K k, final Collection<? extends V> v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void merge(final Map<? extends K, ? extends Collection<? extends V>> mm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void mergeScalarMap(final Map<? extends K, ? extends V> scalarMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final K k, final V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final K k, final Collection<? extends V> v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Map<? extends K, ? extends Collection<? extends V>> mm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAllScalar(final Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> removeAllEmptyCollections() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> removeAllNullCollections() {
        throw new UnsupportedOperationException();
    }
}
