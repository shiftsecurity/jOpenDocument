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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Redirect streams of a process to System.out and System.err.
 * 
 * @author Sylvain
 */
public class ProcessStreams {

    static public enum Action {
        /**
         * Redirect process streams to ours.
         */
        REDIRECT,
        /**
         * Close process streams.
         */
        CLOSE,
        /**
         * Do nothing, which is dangerous as the process will hang until its output is read.
         */
        DO_NOTHING
    }

    static public final Process handle(final Process p, final Action action) throws IOException {
        if (action == Action.CLOSE) {
            p.getInputStream().close();
            p.getErrorStream().close();
        } else if (action == Action.REDIRECT) {
            new ProcessStreams(p);
        }
        return p;
    }

    private final ExecutorService exec = Executors.newFixedThreadPool(2);
    private final CountDownLatch latch;
    private final Future<?> out;
    private final Future<?> err;

    public ProcessStreams(final Process p) {
        this.latch = new CountDownLatch(2);
        this.out = writeToAsync(p.getInputStream(), System.out);
        this.err = writeToAsync(p.getErrorStream(), System.err);
        this.exec.submit(new Runnable() {
            public void run() {
                try {
                    ProcessStreams.this.latch.await();
                } catch (InterruptedException e) {
                    // ne rien faire
                    e.printStackTrace();
                } finally {
                    ProcessStreams.this.exec.shutdown();
                }
            }
        });
    }

    protected final void stopOut() {
        this.stop(this.out);
    }

    protected final void stopErr() {
        this.stop(this.err);
    }

    private final void stop(Future<?> f) {
        // TODO
        // ATTN don't interrupt, hangs in readLine()
        f.cancel(false);
    }

    private final Future<?> writeToAsync(final InputStream ins, final PrintStream outs) {
        return this.exec.submit(new Callable<Object>() {
            public Object call() throws InterruptedException, IOException {
                try {
                    writeTo(ins, outs);
                    return null;
                } finally {
                    ProcessStreams.this.latch.countDown();
                }
            }
        });
    }

    /**
     * Copy ins to outs, line by line.
     * 
     * @param ins the source.
     * @param outs the destination.
     * @throws InterruptedException if current thread is interrupted.
     * @throws IOException if io error.
     */
    public static final void writeTo(InputStream ins, PrintStream outs) throws InterruptedException, IOException {
        final BufferedReader r = new BufferedReader(new InputStreamReader(ins));
        String encodedName;
        while ((encodedName = r.readLine()) != null) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            outs.println(encodedName);
        }
    }

}
