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

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class ListAbstractMap<K, L extends List<V>, V> extends CollectionMap2<K, L, V> {

    public ListAbstractMap() {
        super();
    }

    public ListAbstractMap(Map<K, L> delegate, Mode mode) {
        super(delegate, mode);
    }

    public ListAbstractMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ListAbstractMap(int initialCapacity, Mode mode, Boolean emptyCollSameAsNoColl) {
        super(initialCapacity, mode, emptyCollSameAsNoColl);
    }

    // ** copy constructors

    public ListAbstractMap(CollectionMap2<K, L, ? extends V> m) {
        super(m);
    }

    public ListAbstractMap(Map<? extends K, ? extends Collection<? extends V>> m) {
        super(m);
    }

    public ListAbstractMap(Map<K, L> delegate, Map<? extends K, ? extends Collection<? extends V>> m) {
        super(delegate, m);
    }
}
