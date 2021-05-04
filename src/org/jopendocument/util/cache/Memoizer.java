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

import org.jopendocument.util.RTInterruptedException;
import org.jopendocument.util.cc.ITransformerExn;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import net.jcip.annotations.ThreadSafe;

// from "Java Concurrency in Practice"
@ThreadSafe
public class Memoizer<A, V, X extends Exception> implements ITransformerExn<A, V, X> {
    private final ConcurrentMap<A, Future<V>> cache;
    private final ITransformerExn<A, V, X> c;

    public Memoizer(ITransformerExn<A, V, X> c) {
        this(c, new ConcurrentHashMap<A, Future<V>>());
    }

    public Memoizer(final ITransformerExn<A, V, X> c, final ConcurrentMap<A, Future<V>> cache) {
        this.c = c;
        this.cache = cache;
    }

    public final Set<A> getCachedItems() {
        return this.cache.keySet();
    }

    @Override
    public final V transformChecked(final A arg) throws X {
        while (true) {
            Future<V> res = this.cache.get(arg);
            if (res == null) {
                final FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {
                    public V call() throws X {
                        return Memoizer.this.c.transformChecked(arg);
                    }
                });
                res = this.cache.putIfAbsent(arg, ft);
                if (res == null) {
                    res = ft;
                    ft.run();
                }
            }
            try {
                return res.get();
            } catch (InterruptedException e) {
                throw new RTInterruptedException(e);
            } catch (CancellationException e) {
                this.cache.remove(arg, res);
            } catch (ExecutionException e) {
                @SuppressWarnings("unchecked")
                final X cause = (X) e.getCause();
                throw cause;
            }
        }
    }
}