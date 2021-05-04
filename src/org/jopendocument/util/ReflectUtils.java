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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectUtils {

    static private Map<Type, Type> resolveTypes(Class<?> c, Class<?> raw) {
        final Map<Type, Type> res = new HashMap<Type, Type>();
        if (!raw.isAssignableFrom(c))
            return res;

        // c : ListDeString implements List<String>
        final List<Type> types = new ArrayList<Type>(Arrays.asList(c.getGenericInterfaces()));
        types.add(c.getGenericSuperclass());
        for (final Type t : types) {
            if (t instanceof ParameterizedType) {
                // eg List<String>
                final ParameterizedType pt = (ParameterizedType) t;
                if (raw.isAssignableFrom((Class) pt.getRawType())) {
                    // eg List.class (List<E>)
                    final Class<?> rawType = (Class) pt.getRawType();
                    // eg [String.class]
                    final Type[] actualTypeArguments = pt.getActualTypeArguments();
                    // eg [E]
                    final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                    for (int i = 0; i < actualTypeArguments.length; i++) {
                        res.put(typeParameters[i], actualTypeArguments[i]);
                    }
                }
            }
            final Class<?> tc = getClass(t);
            if (tc != null) {
                res.putAll(resolveTypes(tc, raw));
            }
        }
        return res;
    }

    /**
     * The map of type arguments of baseClass to actual type for childClass.
     * 
     * @param <T> the type of the baseClass.
     * @param childClass the class to test, eg Props.class with Props<V> extends Map<String, V>.
     * @param baseClass the generic superclass, eg Map.class.
     * @return a the map, eg {K => String.class, V => null}.
     */
    public static <T> Map<TypeVariable<Class<T>>, Class<?>> getTypeArgumentsMap(Class<? extends T> childClass, Class<T> baseClass) {
        final TypeVariable<Class<T>>[] actualTypeArguments = baseClass.getTypeParameters();
        if (actualTypeArguments.length == 0)
            throw new IllegalArgumentException(baseClass + " is not generic");

        final Map<TypeVariable<Class<T>>, Class<?>> res = new LinkedHashMap<TypeVariable<Class<T>>, Class<?>>();
        final Map<Type, Type> resolvedTypes = resolveTypes(childClass, baseClass);
        // for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        // resolve types by chasing down type variables.
        for (final TypeVariable<Class<T>> baseType : actualTypeArguments) {
            Type currentType = baseType;
            while (resolvedTypes.containsKey(currentType)) {
                currentType = resolvedTypes.get(currentType);
            }
            res.put(baseType, getClass(currentType));
        }

        return res;
    }

    /**
     * Search for the list of class used to extend/implement a generic class/interface.
     * 
     * @param <T> the type of the baseClass.
     * @param childClass the class to test, eg Props.class with Props<V> extends Map<String, V>.
     * @param baseClass the generic superclass, eg Map.class.
     * @return the list of actual classes w/o the possible nulls (if childClass is generic), never
     *         <code>null</code>, eg [Boolean.class].
     */
    public static <T> List<Class<?>> getTypeArguments(Class<? extends T> childClass, Class<T> baseClass) {
        final ArrayList<Class<?>> res = new ArrayList<Class<?>>();
        // ok since getTypeArgumentsMap returns a LinkedHashMap
        for (final Class<?> c : getTypeArgumentsMap(childClass, baseClass).values()) {
            if (c != null)
                res.add(c);
        }
        return res;
    }

    static public <U> List<Class<?>> getTypeArguments(U o, Class<U> raw) {
        return getTypeArguments(o.getClass().asSubclass(raw), raw);
    }

    /**
     * Whether o can be casted to raw&lt;typeArgs&gt;.
     * 
     * @param <U> type of the superclass.
     * @param o the instance to check, eg new MapOfInt2Boolean().
     * @param raw the generic superclass, eg Map.class.
     * @param typeArgs arguments to <code>raw</code>, eg Integer.class, Boolean.class.
     * @return whether o is a raw&lt;typeArgs&gt;, eg <code>true</code> : new MapOfInt2Boolean()
     *         is a Map&lt;Integer, Boolean&gt;.
     */
    static public <U> boolean isCastable(U o, Class<U> raw, Class... typeArgs) {
        return getTypeArguments(o, raw).equals(Arrays.asList(typeArgs));
    }

    // *** pasted from http://www.artima.com/weblogs/viewpost.jsp?thread=208860

    /**
     * Get the underlying class for a type, or null if the type is a variable type.
     * 
     * @param type the type
     * @return the underlying class
     */
    private static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
