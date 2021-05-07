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

public interface CacheWatcherFactory<K, D> {

    /**
     * Creates the appropriate watcher for listening to <code>data</code>.
     * 
     * @param cache the cache.
     * @param data the data to watch.
     * @return the corresponding watcher.
     * @throws Exception if the watcher could not be created.
     */
    CacheWatcher<K, D> createWatcher(ICache<K, ?, D> cache, D data) throws Exception;

}
