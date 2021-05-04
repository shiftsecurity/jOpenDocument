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

package org.jopendocument.util.cc;

import org.jopendocument.util.ExceptionUtils;

import org.apache.commons.collections.Transformer;

/**
 * Transformer able to throw an exception.
 * 
 * @author Sylvain
 * 
 * @param <E> input type
 * @param <T> return type
 * @param <X> exception type
 */
public abstract class ExnTransformer<E, T, X extends Exception> implements Transformer, ITransformerExn<E, T, X> {

    @SuppressWarnings("unchecked")
    public final Object transform(Object input) {
        return this.transformCheckedWithExn((E) input, IllegalStateException.class);
    }

    /**
     * Execute this transformer, making sure that an exception of type <code>exnClass</code> is
     * thrown.
     * 
     * @param <Y> type of exception to throw.
     * @param input the input.
     * @param exnClass class exception to throw.
     * @return the result of this transformer.
     * @throws Y if {@link #transformChecked(Object)} throws an exception, it will be wrapped (if
     *         necessary) in an exception of class <code>exnClass</code>.
     */
    public final <Y extends Exception> T transformCheckedWithExn(E input, Class<Y> exnClass) throws Y {
        try {
            return this.transformChecked(input);
        } catch (Exception e) {
            if (exnClass.isInstance(e))
                throw exnClass.cast(e);
            else
                throw ExceptionUtils.createExn(exnClass, "executeChecked failed", e);
        }
    }

    /**
     * Execute this transformer, wrapping exceptions thrown by {@link #transformChecked(Object)}
     * into one of the passed exception classes.
     * 
     * @param <Y> type of exception to throw.
     * @param <Z> second type of exception to throw.
     * @param <A> third type of exception to throw.
     * @param input the input.
     * @param wrapRT <code>true</code> so that even {@link RuntimeException} are wrapped into
     *        <code>Y</code>, <code>false</code> if this method should throw them as they are.
     * @param exnClass class exception to throw.
     * @param exnClass2 class exception to throw, can be <code>null</code>.
     * @param exnClass3 class exception to throw, can be <code>null</code>.
     * @return the result of this transformer.
     * @throws Y if {@link #transformChecked(Object)} throws an exception, it will be wrapped (if
     *         necessary) in an exception of class <code>exnClass</code>.
     * @throws Z if {@link #transformChecked(Object)} throws an exception of class Z.
     * @throws A if {@link #transformChecked(Object)} throws an exception of class A.
     */
    public final <Y extends Exception, Z extends Exception, A extends Exception> T transformCheckedWithExn(E input, final boolean wrapRT, Class<Y> exnClass, Class<Z> exnClass2, Class<A> exnClass3)
            throws Y, Z, A {
        try {
            return this.transformChecked(input);
        } catch (Exception e) {
            if (!wrapRT && e instanceof RuntimeException)
                throw (RuntimeException) e;
            else if (exnClass.isInstance(e))
                throw exnClass.cast(e);
            else if (exnClass2 != null && exnClass2.isInstance(e))
                throw exnClass2.cast(e);
            else if (exnClass3 != null && exnClass3.isInstance(e))
                throw exnClass3.cast(e);
            else
                throw ExceptionUtils.createExn(exnClass, "executeChecked failed", e);
        }
    }

    @Override
    public abstract T transformChecked(E input) throws X;

}
