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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ICacheTest {

    private final ThreadGroup thg = new ThreadGroup("testjunit") {
        public void uncaughtException(Thread t, Throwable e) {
            ICacheTest.this.threadException = e;
        }
    };

    private Thread launch(String name, Runnable runnable) {
        final Thread res = new Thread(this.thg, runnable, name);
        res.start();
        // really let res start...
        Thread.yield();
        return res;
    }

    private void join(final Thread t) throws Throwable {
        t.join();
        if (this.threadException != null) {
            final Throwable thw = this.threadException;
            this.threadException = null;
            throw thw;
        }
    }

    private static final int delay = 1;

    private ICache<String, Object, Object> cache;
    protected Throwable threadException;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        this.cache = new ICache<String, Object, Object>(delay);
    }

    @After
    public void tearDown() throws Exception {
        this.cache = null;
    }

    @Test
    public void testWatcher() throws NoSuchMethodException {
        class BogusCacheWatcher<K, D> extends CacheWatcher<K, D> {

            public BogusCacheWatcher(ICache<K, ?, D> c, D d) {
                super(c, d);
            }

            public void clear() {
                this.clearCache();
            }

        }

        final ICache<String, List<Object>, String> cache = new ICache<String, List<Object>, String>(delay);
        cache.setWatcherFactory(new CacheWatcherFactory<String, String>() {
            public CacheWatcher<String, String> createWatcher(ICache<String, ?, String> cache, String obj) throws Exception {
                return new BogusCacheWatcher<String, String>(cache, obj);
            }
        });

        final String sel = "SELECT * FROM CONTACT";
        final String sel2 = "SELECT NOM FROM CONTACT";
        final List<Object> r = new ArrayList<Object>();
        final String data = "CONTACT_TABLE";
        assertFalse(cache.dependsOn(data));
        // put both in cache
        final Set<? extends CacheWatcher<String, String>> put = cache.put(sel, r, Collections.singleton(data));
        final BogusCacheWatcher<String, String> cw = (BogusCacheWatcher<String, String>) put.iterator().next();
        cache.put(sel2, r, Collections.singleton(data));
        // now cache depends on data
        assertTrue(cache.dependsOn(data));
        cache.clear(sel2);
        // still depends on data because of sel
        assertTrue(cache.dependsOn(data));
        assertSame(r, cache.get(sel).getRes());
        // apres une modif, le cache est invalidé
        cw.clear();
        assertSame(CacheResult.State.NOT_IN_CACHE, cache.get(sel).getState());
        assertFalse(cache.dependsOn(data));
    }

    @Test
    public void testInterrupt() throws Throwable {
        // test that we can interrupt a waiting thread
        this.getCache().addRunning("SELECT *");
        final Thread tInt = launch("test interrupt", new Runnable() {
            public void run() {
                final CacheResult<Object> res = getCache().get("SELECT *");
                assertSame(CacheResult.State.INTERRUPTED, res.getState());
            }
        });
        tInt.interrupt();
        join(tInt);
        this.getCache().removeRunning("SELECT *");
    }

    @Test
    public void testGet() throws Throwable {
        // cache vide
        assertEquals(0, this.getCache().size());
        assertSame(CacheResult.State.NOT_IN_CACHE, this.getCache().get("SELECT 1").getState());

        this.getCache().put("SELECT 1", new Integer(1), Collections.singleton(1));
        // test timeout
        final Thread t = launch("test timeout", new Runnable() {
            public void run() {
                try {
                    Thread.sleep((long) (delay * 1000 * 1.1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    fail(e.getMessage());
                }
                assertSame(CacheResult.State.NOT_IN_CACHE, getCache().get("SELECT 1").getState());
            }
        });
        assertEquals(new Integer(1), this.getCache().get("SELECT 1").getRes());
        join(t);

        // cache <- cache2 <- cache3
        final ICache<String, Object, Object> cache2 = new ICache<String, Object, Object>();
        final ICache<String, Object, Object> cache3 = new ICache<String, Object, Object>();
        cache3.setParent(cache2);
        cache2.setParent(getCache());

        try {
            getCache().setParent(cache3);
            fail("Would create a cycle");
        } catch (Exception e) {
            // OK
        }
        assertNull(getCache().getParent());

        getCache().put("SELECT 1", 1);
        cache2.put("SELECT 2", 2);
        cache3.put("SELECT 3", 3);

        assertEquals(Integer.valueOf(1), this.getCache().get("SELECT 1").getRes());
        assertEquals(CacheResult.State.NOT_IN_CACHE, this.getCache().get("SELECT 2").getState());
        assertEquals(CacheResult.State.NOT_IN_CACHE, this.getCache().get("SELECT 3").getState());

        assertEquals(Integer.valueOf(1), cache2.get("SELECT 1").getRes());
        assertEquals(Integer.valueOf(2), cache2.get("SELECT 2").getRes());
        assertEquals(CacheResult.State.NOT_IN_CACHE, cache2.get("SELECT 3").getState());

        assertEquals(Integer.valueOf(1), cache3.get("SELECT 1").getRes());
        assertEquals(Integer.valueOf(2), cache3.get("SELECT 2").getRes());
        assertEquals(Integer.valueOf(3), cache3.get("SELECT 3").getRes());
    }

    @Test
    public void testRunning() throws Throwable {
        final String sel = "SELECT 2";
        assertFalse(this.getCache().isRunning(sel));
        this.getCache().addRunning(sel);
        assertTrue(this.getCache().isRunning(sel));

        final Vector<Object> t2Res = new Vector<Object>();
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread t2 = launch("test running", new Runnable() {
            public void run() {
                t2Res.add("begun");
                latch.countDown();
                t2Res.add(getCache().get(sel).getRes());
            }
        });

        // be sure that t2 started
        latch.await();
        assertEquals(1, t2Res.size());
        // and that it is waiting (give t2 some time to get to "get(sel)")
        Thread.sleep(20);
        assertEquals(State.WAITING, t2.getState());

        final Object two = new Object();
        assertTrue(getCache().isRunning(sel));
        this.getCache().put(sel, two, Collections.emptySet());
        assertFalse(getCache().isRunning(sel));

        join(t2);
        // assert that t2 has been given our two
        assertSame(two, t2Res.get(1));
    }

    @Test
    public void testCheck() {
        final Object nul = new Object();
        this.getCache().put("SELECT NULL", nul, Collections.emptySet());
        assertSame(nul, this.getCache().check("SELECT NULL").getRes());
        // it should be in cache
        assertFalse(this.getCache().isRunning("SELECT NULL"));
    }

    @Test
    public void testSizeAndClear() {
        final ICache<String, String, Object> cache = new ICache<String, String, Object>(600, 1);
        cache.put("a", "A", Collections.emptySet());
        assertEquals(1, cache.size());
        cache.put("b", "B", Collections.emptySet());
        assertEquals(1, cache.size());
        assertSame(CacheResult.State.NOT_IN_CACHE, cache.get("a").getState());
    }

    @Test
    public void testClear() {
        this.getCache().put("a", "A", Collections.emptySet());
        assertEquals(1, this.getCache().size());
        this.getCache().clear("a");
        assertEquals(0, this.getCache().size());
        // we can remove something that isn't there
        this.getCache().clear("b");
        assertEquals(0, this.getCache().size());

        this.getCache().put("a", "A", Collections.emptySet());
        this.getCache().put("b", "B", Collections.emptySet());
        this.getCache().put("c", "C", Collections.emptySet());
        assertEquals(3, this.getCache().size());
        this.getCache().clear();
        assertEquals(0, this.getCache().size());

        // clear() is not final
        this.getCache().put("a", "A", Collections.emptySet());
        assertEquals(1, this.getCache().size());
    }

    private ICache<String, Object, Object> getCache() {
        return this.cache;
    }

}
