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

import org.jopendocument.util.ReflectUtils;

import java.util.ArrayList;
import java.util.List;

public final class ValueConvertorFactory {

    @SuppressWarnings("rawtypes")
    private static final ValueConvertor IdentityConvertor = new ValueConvertor() {
        @Override
        public Object convert(Object o) {
            return o;
        }

        @Override
        public Object unconvert(Object o) {
            return o;
        }
    };

    @SuppressWarnings("unchecked")
    public static final <T> ValueConvertor<T, T> getIdentityConvertor() {
        return (ValueConvertor<T, T>) IdentityConvertor;
    }

    private static final List<ValueConvertor<?, ?>> convs;
    static {
        convs = new ArrayList<ValueConvertor<?, ?>>();
        convs.add(new DateTSConvertor());
        convs.add(new DateToTimeConvertor());
        convs.add(StringClobConvertor.INSTANCE);
        convs.add(NumberConvertor.INT_TO_LONG);
        convs.add(NumberConvertor.SHORT_TO_INT);
    }

    @SuppressWarnings("unchecked")
    public static final <T, U> ValueConvertor<T, U> find(Class<T> c1, Class<U> c2) {
        if (c1 == c2)
            return (ValueConvertor<T, U>) getIdentityConvertor();
        for (final ValueConvertor<?, ?> vc : convs) {
            final List<Class<?>> args = ReflectUtils.getTypeArguments(vc, ValueConvertor.class);
            if (args.size() != 2)
                throw new IllegalStateException(vc + " don't specify type arguments");
            if (args.get(0).equals(c1) && args.get(1).equals(c2)) {
                return (ValueConvertor<T, U>) vc;
            } else if (args.get(0).equals(c2) && args.get(1).equals(c1)) {
                return new ReverseConvertor<T, U>((ValueConvertor<U, T>) vc);
            }
        }
        if (Number.class.isAssignableFrom(c1) && Number.class.isAssignableFrom(c2))
            return (ValueConvertor<T, U>) NumberConvertor.create(c1.asSubclass(Number.class), c2.asSubclass(Number.class), true);

        return null;
    }
}
