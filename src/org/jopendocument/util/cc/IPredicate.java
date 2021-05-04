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

import org.apache.commons.collections.Predicate;

public abstract class IPredicate<E> implements Predicate {

    private static final IPredicate<Object> truePred = new IPredicate<Object>() {
        @Override
        public boolean evaluateChecked(Object input) {
            return true;
        }
    };
    private static final IPredicate<Object> falsePred = new IPredicate<Object>() {
        @Override
        public boolean evaluateChecked(Object input) {
            return false;
        }
    };
    private static final IPredicate<Object> NotNullPred = new IPredicate<Object>() {
        @Override
        public boolean evaluateChecked(Object input) {
            return input != null;
        }
    };

    @SuppressWarnings("unchecked")
    public static final <N> IPredicate<N> truePredicate() {
        return (IPredicate<N>) truePred;
    }

    @SuppressWarnings("unchecked")
    public static final <N> IPredicate<N> falsePredicate() {
        return (IPredicate<N>) falsePred;
    }

    @SuppressWarnings("unchecked")
    public static final <N> IPredicate<N> notNullPredicate() {
        return (IPredicate<N>) NotNullPred;
    }

    @SuppressWarnings("unchecked")
    public boolean evaluate(Object object) {
        return this.evaluateChecked((E) object);
    }

    public abstract boolean evaluateChecked(E input);

    public final <F extends E> IPredicate<F> cast() {
        // this class never returns E, only takes it as argument
        // but we cannot return this instance :
        // class Sub<E> extends IPredicate<E> {
        // E someMethod();
        // }
        // Sub<Number> n;
        // IPredicate<Integer> cast = n.<Integer> cast();
        // Sub<Integer> n2 = (Sub<Integer>) cast;
        // the cast in the line above will succeed but n2.someMethod()
        // will fail if n.someMethod() returns a BigDecimal

        // since we're returning a new IPredicate instance, the n2 line would correctly fail
        return new IPredicate<F>() {
            @Override
            public boolean evaluateChecked(F input) {
                return IPredicate.this.evaluateChecked(input);
            }
        };
    }

    public final IPredicate<E> not() {
        if (this == truePred)
            return falsePredicate();
        else if (this == falsePred)
            return truePredicate();
        else
            return new IPredicate<E>() {
                @Override
                public boolean evaluateChecked(E input) {
                    return !IPredicate.this.evaluateChecked(input);
                }
            };
    }

    public final IPredicate<E> and(final IPredicate<? super E> o) {
        if (o == this || o == truePred)
            return this;
        else if (this == truePred)
            return o.cast();
        else if (o == falsePred || this == falsePred)
            return falsePredicate();
        else
            return new IPredicate<E>() {
                @Override
                public boolean evaluateChecked(E input) {
                    return IPredicate.this.evaluateChecked(input) && o.evaluateChecked(input);
                }
            };
    }

    public final IPredicate<E> or(final IPredicate<? super E> o) {
        if (o == this || o == falsePred)
            return this;
        else if (this == falsePred)
            return o.cast();
        else if (o == truePred || this == truePred)
            return truePredicate();
        else
            return new IPredicate<E>() {
                @Override
                public boolean evaluateChecked(E input) {
                    return IPredicate.this.evaluateChecked(input) || o.evaluateChecked(input);
                }
            };
    }
}
