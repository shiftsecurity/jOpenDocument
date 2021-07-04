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

package org.jopendocument.util.cache;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A watcher invalidates cache results when its data is modified.
 * 
 * @param <K> cache key type.
 * @param <D> source data type, eg SQLTable.
 */
abstract public class CacheWatcher<K, D> {

    private final ICache<K, ?, D> c;
    private final Set<K> keys;
    private final D data;

    protected CacheWatcher(ICache<K, ?, D> c, D data) {
        this.c = c;
        this.keys = new HashSet<K>();
        this.data = data;
    }

    public final D getData() {
        return this.data;
    }

    synchronized boolean isEmpty() {
        return this.keys.isEmpty();
    }

    synchronized final void add(K key) {
        this.keys.add(key);
    }

    synchronized final void remove(K key) {
        this.keys.remove(key);
    }

    public final synchronized void die() {
        this.dying();
        this.clearCache();
    }

    protected void dying() {
    }

    protected final void clearCache() {
        // synch on the cache since otherwise we take and release its lock on each iteration
        // but keep ours all the time. Thus if another thread call a synchronized method of the
        // cache inbetween an iteration and tries to access us, a deadlock occurs.
        synchronized (this.c) {
            synchronized (this) {
                final Iterator<K> iter = this.keys.iterator();
                while (iter.hasNext()) {
                    final K key = iter.next();
                    iter.remove();
                    this.c.clear(key);
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " on " + getData();
    }
}