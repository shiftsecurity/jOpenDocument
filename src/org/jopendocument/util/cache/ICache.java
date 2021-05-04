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

import org.jopendocument.util.ExceptionUtils;
import org.jopendocument.util.Log;
import org.jopendocument.util.SetMap;
import org.jopendocument.util.cache.CacheResult.State;
import org.jopendocument.util.cc.Transformer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;

import org.apache.commons.collections.map.LazyMap;

/**
 * To keep results computed from some data. The results will be automatically invalidated after some
 * period of time or when the data is modified.
 * 
 * @author Sylvain CUAZ
 * @param <K> key type, eg String.
 * @param <V> value type, eg List of SQLRow.
 * @param <D> source data type, eg SQLTable.
 */
public class ICache<K, V, D> {

    private static final Level LEVEL = Level.FINEST;

    // linked to fifo
    private final LinkedHashMap<K, V> cache;
    private final Set<K> running;
    private final int delay;
    private final int size;
    // lazy initialization to avoid creating unnecessary threads
    private Timer timer;
    private final String name;
    private final Map<K, CacheTimeOut<K>> timeoutTasks;
    private Map<D, CacheWatcher<K, D>> watchers;
    private final SetMap<K, CacheWatcher<K, D>> watchersByKey;

    private ICache<K, V, D> parent;

    public ICache() {
        this(60);
    }

    public ICache(int delay) {
        this(delay, -1);
    }

    public ICache(int delay, int size) {
        this(delay, size, null);
    }

    /**
     * Creates a cache with the given parameters.
     * 
     * @param delay the delay in seconds before a key is cleared.
     * @param size the maximum size of the cache, negative means no limit.
     * @param name name of this cache and associated thread.
     * @throws IllegalArgumentException if size is 0.
     */
    public ICache(int delay, int size, String name) {
        this.running = new HashSet<K>();
        this.delay = delay;
        if (size == 0)
            throw new IllegalArgumentException("0 size");
        this.size = size;
        this.cache = new LinkedHashMap<K, V>(size < 0 ? 64 : size);
        this.timer = null;
        this.name = name;
        this.timeoutTasks = new HashMap<K, CacheTimeOut<K>>();

        this.watchers = null;
        this.watchersByKey = new SetMap<K, CacheWatcher<K, D>>();

        this.parent = null;
    }

    private final Timer getTimer() {
        if (this.timer == null)
            this.timer = this.name == null ? new Timer(true) : new Timer("cache for " + this.name, true);
        return this.timer;
    }

    @SuppressWarnings("unchecked")
    public final void setWatcherFactory(final CacheWatcherFactory<K, D> f) {
        this.watchers = LazyMap.decorate(new HashMap(), new Transformer<D, CacheWatcher<K, D>>() {

            @Override
            public CacheWatcher<K, D> transformChecked(D input) {
                try {
                    return f.createWatcher(ICache.this, input);
                } catch (Exception e) {
                    throw ExceptionUtils.createExn(IllegalStateException.class, "could not create watcher for " + input, e);
                }
            }
        });

    }

    /**
     * Allow to continue the search for a key in another instance.
     * 
     * @param parent the cache to search when a key isn't found in this.
     */
    public final synchronized void setParent(final ICache<K, V, D> parent) {
        ICache<K, V, D> current = parent;
        while (current != null) {
            if (current == this)
                throw new IllegalArgumentException("Cycle detected, cannot set parent to " + parent);
            current = current.getParent();
        }
        this.parent = parent;
    }

    public final synchronized ICache<K, V, D> getParent() {
        return this.parent;
    }

    /**
     * If <code>sel</code> is in cache returns its value, else if key is running block until the key
     * is put (or the current thread is interrupted). Then if a {@link #setParent(ICache) parent}
     * has been set, use it. Otherwise the key is not in cache so return a CacheResult of state
     * {@link State#NOT_IN_CACHE}.
     * 
     * @param sel the key we're getting the value for.
     * @return a CacheResult with the appropriate state.
     */
    public final CacheResult<V> get(K sel) {
        return this.get(sel, true);
    }

    private final CacheResult<V> get(K sel, final boolean checkRunning) {
        synchronized (this) {
            if (this.cache.containsKey(sel)) {
                log("IN cache", sel);
                return new CacheResult<V>(this.cache.get(sel));
            } else if (checkRunning && isRunning(sel)) {
                log("RUNNING", sel);
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // return sinon thread ne peut sortir que lorsque sel sera fini
                    return CacheResult.getInterrupted();
                }
                return this.get(sel);
            } else if (this.parent != null) {
                log("CALLING parent", sel);
                return this.parent.get(sel, false);
            } else {
                log("NOT in cache", sel);
                return CacheResult.getNotInCache();
            }
        }
    }

    /**
     * Tell this cache that we're in process of getting the value for key, so if someone else ask
     * have them wait. ATTN after calling this method you MUST call put(), otherwise get() will
     * always block for key.
     * 
     * @param key the key we're getting the value for.
     * @see #put(Object, Object, Set)
     */
    public final synchronized void addRunning(K key) {
        this.running.add(key);
    }

    public final synchronized void removeRunning(K key) {
        this.running.remove(key);
        this.notifyAll();
    }

    public final synchronized boolean isRunning(K sel) {
        return this.running.contains(sel);
    }

    /**
     * Check if key is in cache, in that case returns the value otherwise adds key to running and
     * returns <code>NOT_IN_CACHE</code>.
     * 
     * @param key the key to be checked.
     * @return the associated value, or <code>null</code>.
     * @see #addRunning(Object)
     */
    public final synchronized CacheResult<V> check(K key) {
        final CacheResult<V> l = this.get(key);
        if (l.getState() == State.NOT_IN_CACHE)
            this.addRunning(key);
        return l;
    }

    /**
     * Put a result which doesn't depend on variable data in this cache.
     * 
     * @param sel the key.
     * @param res the result associated with <code>sel</code>.
     */
    public final synchronized void put(K sel, V res) {
        this.put(sel, res, Collections.<D> emptySet());
    }

    /**
     * Put a result in this cache.
     * 
     * @param sel the key.
     * @param res the result associated with <code>sel</code>.
     * @param data the data from which <code>res</code> is computed.
     * @return the watchers monitoring the passed key.
     */
    public final synchronized Set<? extends CacheWatcher<K, D>> put(K sel, V res, Set<? extends D> data) {
        if (this.size > 0 && this.cache.size() == this.size)
            this.clear(this.cache.keySet().iterator().next());
        this.cache.put(sel, res);
        this.removeRunning(sel);

        for (final D datum : data) {
            if (this.watchers != null) {
                final CacheWatcher<K, D> watcher = this.watchers.get(datum);
                watcher.add(sel);
                this.watchersByKey.add(sel, watcher);
            }
        }

        final CacheTimeOut<K> timeout = new CacheTimeOut<K>(this, sel);
        this.timeoutTasks.put(sel, timeout);
        this.getTimer().schedule(timeout, this.delay * 1000);

        return this.watchersByKey.getNonNull(sel);
    }

    public final synchronized void clear(K select) {
        log("clear", select);
        if (this.cache.containsKey(select)) {
            this.cache.remove(select);
            this.timeoutTasks.remove(select).cancel();
            final Set<CacheWatcher<K, D>> keyWatchers = this.watchersByKey.remove(select);
            // a key can specify no watchers at all
            if (keyWatchers != null) {
                for (final CacheWatcher<K, D> w : keyWatchers) {
                    w.remove(select);
                    if (w.isEmpty()) {
                        w.die();
                        this.watchers.remove(w.getData());
                    }
                }
            }
        }
    }

    public final synchronized void clear() {
        if (this.size() > 0) {
            this.cache.clear();
            if (this.timer != null) {
                this.timer.cancel();
                this.timer = null;
                this.timeoutTasks.clear();
            }
            if (this.watchers != null) {
                this.watchersByKey.clear();
                for (final CacheWatcher<K, D> w : this.watchers.values()) {
                    // die() will call clear() but since this.cache is now empty it won't do
                    // anything
                    w.die();
                }
                this.watchers.clear();
            }
        }
    }

    final synchronized boolean dependsOn(D data) {
        return this.watchers.containsKey(data);
    }

    private final void log(String msg, Object subject) {
        // do the toString() on subject only if necessary
        if (Log.get().isLoggable(LEVEL))
            Log.get().log(LEVEL, msg + ": " + subject);
    }

    public final synchronized int size() {
        return this.cache.size();
    }

    public final String toString() {
        return this.getClass().getName() + ", keys cached: " + this.timeoutTasks.keySet();
    }
}