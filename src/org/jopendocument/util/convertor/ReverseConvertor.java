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

package org.jopendocument.util.convertor;

public class ReverseConvertor<T, U> implements ValueConvertor<T, U> {

    private final ValueConvertor<U, T> delegate;

    public ReverseConvertor(ValueConvertor<U, T> delegate) {
        super();
        this.delegate = delegate;
    }

    public U convert(T o) {
        return this.delegate.unconvert(o);
    }

    public T unconvert(U o) {
        return this.delegate.convert(o);
    }

}
