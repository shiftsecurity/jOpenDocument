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

import org.jopendocument.util.cc.IClosure;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.cc.ITransformer;
import org.jopendocument.util.cc.IdentityHashSet;
import org.jopendocument.util.cc.IdentitySet;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Une classe regroupant des méthodes utilitaires pour les collections.
 * 
 * @author ILM Informatique 30 sept. 2004
 */
public class CollectionUtils {

    /**
     * Concatene une collection. Cette méthode va appliquer un transformation sur chaque élément
     * avant d'appeler toString(). join([-1, 3, 0], " ,", doubleTransformer) == "-2, 6, 0"
     * 
     * @param <E> type of items
     * @param c la collection a concaténer.
     * @param sep le séparateur entre chaque élément.
     * @param tf la transformation à appliquer à chaque élément.
     * @return la chaine composée de chacun des éléments séparés par <code>sep</code>.
     */
    static public final <E> String join(final Collection<E> c, final String sep, final ITransformer<? super E, ?> tf) {
        final int size = c.size();
        if (size == 0)
            return "";

        final StringBuffer res = new StringBuffer(size * 4);
        if (c instanceof RandomAccess && c instanceof List) {
            final List<E> list = (List<E>) c;
            for (int i = 0; i < size; i++) {
                res.append(tf.transformChecked(list.get(i)));
                if (i < size - 1)
                    res.append(sep);
            }
        } else {
            final Iterator<E> iter = c.iterator();
            while (iter.hasNext()) {
                final E elem = iter.next();
                res.append(tf.transformChecked(elem));
                if (iter.hasNext())
                    res.append(sep);
            }
        }
        return res.toString();
    }

    /**
     * Concatene une collection en appelant simplement toString() sur chaque élément.
     * 
     * @param <T> type of collection
     * @param c la collection a concaténer.
     * @param sep le séparateur entre chaque élément.
     * @return la chaine composée de chacun des éléments séparés par <code>sep</code>.
     * @see #join(Collection, String, ITransformer)
     */
    static public <T> String join(Collection<T> c, String sep) {
        return join(c, sep, org.jopendocument.util.cc.Transformer.<T> nopTransformer());
    }

    static public <T, U, C extends Collection<? super U>> C transform(final Collection<T> c, final ITransformer<? super T, U> transf, final C res) {
        return transformAndFilter(c, transf, IPredicate.truePredicate(), res);
    }

    static public <T, U, C extends Collection<? super U>> C transformAndFilter(final Collection<T> c, final ITransformer<? super T, U> transf, final IPredicate<? super U> filterAfter, final C res) {
        return filterTransformAndFilter(c, IPredicate.truePredicate(), transf, filterAfter, res);
    }

    static public <T, U, C extends Collection<? super U>> C filterAndTransform(final Collection<T> c, final IPredicate<? super T> filterBefore, final ITransformer<? super T, U> transf, final C res) {
        return filterTransformAndFilter(c, filterBefore, transf, IPredicate.truePredicate(), res);
    }

    static public <T, U, C extends Collection<? super U>> C filterTransformAndFilter(final Collection<T> c, final IPredicate<? super T> filterBefore, final ITransformer<? super T, U> transf,
            final IPredicate<? super U> filterAfter, final C res) {
        iterate(c, filterBefore, new IClosure<T>() {
            @Override
            public void executeChecked(T input) {
                final U item = transf.transformChecked(input);
                if (filterAfter.evaluateChecked(item))
                    res.add(item);
            }
        });
        return res;
    }

    static public <T> void iterate(final Collection<T> c, final IClosure<T> cl) {
        iterate(c, IPredicate.truePredicate(), cl);
    }

    static public <T> void iterate(final Collection<T> c, final IPredicate<? super T> filterBefore, final IClosure<T> cl) {
        if (c instanceof RandomAccess && c instanceof List) {
            final List<T> list = (List<T>) c;
            final int size = c.size();
            for (int i = 0; i < size; i++) {
                final T item = list.get(i);
                if (filterBefore.evaluateChecked(item))
                    cl.executeChecked(item);
            }
        } else {
            final Iterator<T> iter = c.iterator();
            while (iter.hasNext()) {
                final T item = iter.next();
                if (filterBefore.evaluateChecked(item))
                    cl.executeChecked(item);
            }
        }
    }

    private static final Pattern COMMA = Pattern.compile("\\p{Space}*,\\p{Space}*");

    static public List<String> split(String s) {
        return split(s, COMMA);
    }

    static public List<String> split(String s, String sep) {
        return split(s, Pattern.compile(sep));
    }

    /**
     * Split a string into a list based on a pattern.
     * 
     * @param s the string to split.
     * @param pattern the pattern where to cut the string.
     * @return the splitted string, empty list if <code>s</code> is "".
     */
    static public List<String> split(String s, Pattern pattern) {
        return s.length() == 0 ? Collections.<String> emptyList() : Arrays.asList(pattern.split(s));
    }

    /**
     * Return an index between <code>0</code> and <code>l.size()</code> inclusive. If <code>i</code>
     * is negative, it is added to <code>l.size()</code> (but bounded to 0), ie for a list of 3
     * items, -1 is the index of the last item ; -3 and -4 are both the first. If <code>i</code> is
     * greater than <code>l.size()</code> then <code>l.size()</code> is returned.
     * 
     * <pre>
     *    a  b  c  a  b  c
     *   -3 -2 -1  0  1  2  3
     * </pre>
     * 
     * @param l the list, eg a list of 3 items.
     * @param i the virtual index, eg -1.
     * @return the real index, eg 2.
     */
    static public int getValidIndex(final List<?> l, final int i) {
        return getValidIndex(l, i, false);
    }

    static public int getValidIndex(final List<?> l, final int i, final boolean strict) {
        final int size = l.size();
        if (i > size) {
            if (strict)
                throw new IndexOutOfBoundsException("Too high : " + i + " > " + size);
            return size;
        } else if (i < -size) {
            if (strict)
                throw new IndexOutOfBoundsException("Too low : " + i + " < " + -size);
            return 0;
        } else if (i >= 0) {
            return i;
        } else {
            return size + i;
        }
    }

    /**
     * Deletes a slice of a list. Pass indexes to {@link #getValidIndex(List, int)} to allow
     * delete(l, 0, -1) to clear l or delete(l, -2, -2) to remove the penultimate item.
     * 
     * @param l the list to delete from.
     * @param from the first index to be removed (inclusive).
     * @param to the last index to be removed (inclusive).
     */
    static public void delete(List<?> l, int from, int to) {
        if (!l.isEmpty())
            l.subList(getValidIndex(l, from), getValidIndex(l, to) + 1).clear();
    }

    /**
     * Deletes the tail of a list. The resulting list will have a size of <code>from</code>.
     * 
     * @param l the list to delete from.
     * @param from the first index to be removed (inclusive).
     */
    static public void delete(List<?> l, int from) {
        delete(l, from, -1);
    }

    public static <T> void filter(Collection<T> collection, IPredicate<? super T> predicate) {
        org.apache.commons.collections.CollectionUtils.filter(collection, predicate);
    }

    public static <T> boolean exists(Collection<T> collection, IPredicate<? super T> predicate) {
        return org.apache.commons.collections.CollectionUtils.exists(collection, predicate);
    }

    /**
     * Convertit une map en 2 listes, une pour les clefs, une pour les valeurs.
     * 
     * @param map la Map à convertir.
     * @return un tuple de 2 List, en 0 les clefs, en 1 les valeurs.
     * @param <K> type of key
     * @param <V> type of value
     */
    static public <K, V> Tuple2<List<K>, List<V>> mapToLists(Map<K, V> map) {
        final List<K> keys = new ArrayList<K>(map.size());
        final List<V> vals = new ArrayList<V>(map.size());
        for (final Map.Entry<K, V> e : map.entrySet()) {
            keys.add(e.getKey());
            vals.add(e.getValue());
        }

        return Tuple2.create(keys, vals);
    }

    /**
     * Add entries from <code>toAdd</code> into <code>map</code> only if the key is not already
     * present.
     * 
     * @param <K> type of keys.
     * @param <V> type of values.
     * @param map the map to fill.
     * @param toAdd the entries to add.
     * @return <code>map</code>.
     */
    static public <K, V> Map<K, V> addIfNotPresent(Map<K, V> map, Map<? extends K, ? extends V> toAdd) {
        for (final Map.Entry<? extends K, ? extends V> e : toAdd.entrySet()) {
            if (!map.containsKey(e.getKey()))
                map.put(e.getKey(), e.getValue());
        }
        return map;
    }

    /**
     * Compute the index that have changed (added or removed) between 2 lists. One of the lists MUST
     * be a sublist of the other, ie the to go from one to the other we just add or remove items but
     * we don't do both.
     * 
     * @param oldList the first list.
     * @param newList the second list.
     * @return a list of Integer.
     * @param <E> type of item
     * @throws IllegalStateException if one list is not a sublist of the other.
     */
    static public <E> List<Integer> getIndexesChanged(List<E> oldList, List<E> newList) {
        final List<E> longer;
        final List<E> shorter;
        if (newList.size() > oldList.size()) {
            longer = new ArrayList<E>(newList);
            shorter = new ArrayList<E>(oldList);
        } else {
            longer = new ArrayList<E>(oldList);
            shorter = new ArrayList<E>(newList);
        }

        final List<Integer> res = new ArrayList<Integer>();
        int offset = 0;
        while (shorter.size() > 0) {
            if (longer.size() < shorter.size())
                throw new IllegalStateException(shorter + " is not a sublist of " + longer);
            // compare nulls
            if (CompareUtils.equals(shorter.get(0), longer.get(0))) {
                shorter.remove(0);
                longer.remove(0);
            } else {
                longer.remove(0);
                res.add(offset);
            }
            offset++;
        }

        for (int i = 0; i < longer.size(); i++) {
            res.add(i + offset);
        }

        return res;
    }

    /**
     * Aggregate a list of ints into a list of intervals. Eg aggregate([-1,0,1,2,5]) returns
     * [[-1,2], [5,5]].
     * 
     * @param ints a list of Integer strictly increasing.
     * @return a list of int[2].
     */
    static public List<int[]> aggregate(Collection<? extends Number> ints) {
        final List<int[]> res = new ArrayList<int[]>();
        int[] currentInterval = null;
        for (final Number n : ints) {
            final int index = n.intValue();
            if (currentInterval == null || index != currentInterval[1] + 1) {
                currentInterval = new int[2];
                currentInterval[0] = index;
                currentInterval[1] = currentInterval[0];
                res.add(currentInterval);
            } else {
                currentInterval[1] = index;
            }
        }
        return res;
    }

    /**
     * Test whether col2 is contained in col1.
     * 
     * @param <T> type of collection
     * @param col1 the first collection
     * @param col2 the second collection
     * @return <code>null</code> if col1 contains all of col2, else return the extra items that col2
     *         have.
     */
    static public <T> Set<T> contains(final Set<T> col1, final Set<T> col2) {
        if (col1.containsAll(col2))
            return null;
        else {
            final Set<T> names = new HashSet<T>(col2);
            names.removeAll(col1);
            return names;
        }
    }

    static public <C extends Collection<?>> boolean containsAny(final C coll1, final C coll2) {
        return org.apache.commons.collections.CollectionUtils.containsAny(coll1, coll2);
    }

    /**
     * Convert an array to a list of a different type.
     * 
     * @param <U> type of array
     * @param <T> type of list
     * @param array the array to convert, eg new Object[]{"a", "b"}.
     * @param clazz the class of the list items, eg String.class.
     * @return all items of <code>array</code> into a list, eg ["a", "b"].
     * @throws ClassCastException if some item of <code>array</code> is not a <code>T</code>.
     */
    static public <U, T extends U> List<T> castToList(U[] array, Class<T> clazz) throws ClassCastException {
        final List<T> res = new ArrayList<T>(array.length);
        for (final U item : array) {
            res.add(clazz.cast(item));
        }
        return res;
    }

    /**
     * The number of equals item between a and b, starting from the end.
     * 
     * @param <T> type of items.
     * @param a the first list, eg [a, b, c].
     * @param b the second list, eg [a, null, z, c].
     * @return the number of common items, eg 1.
     */
    public static <T> int equalsFromEnd(final List<T> a, final List<T> b) {
        return equals(a, b, true, null);
    }

    public static <T> int equalsFromStart(final List<T> a, final List<T> b) {
        return equals(a, b, false, null);
    }

    /**
     * The number of equals item between a and b, starting from the choosen end.
     * 
     * @param <A> type of the first list.
     * @param <B> type of the second list.
     * @param a the first list, eg [a, b, c].
     * @param b the second list, eg [a, null, z, c].
     * @param fromEnd whether search from the start or the end, <code>true</code>.
     * @param transf how items of <code>a</code> should be transformed before being compared, can be
     *        <code>null</code>.
     * @return the number of common items, eg 1.
     */
    public final static <A, B> int equals(final List<A> a, final List<B> b, boolean fromEnd, ITransformer<A, B> transf) {
        final int sizeA = a.size();
        final int sizeB = b.size();
        final int lastI = Math.min(sizeA, sizeB);
        for (int i = 0; i < lastI; i++) {
            final A itemA = a.get(fromEnd ? sizeA - 1 - i : i);
            final B itemB = b.get(fromEnd ? sizeB - 1 - i : i);
            if (!CompareUtils.equals(transf == null ? itemA : transf.transformChecked(itemA), itemB))
                return i;
        }
        return lastI;
    }

    public final static <T> int getEqualsCount(final Iterator<? extends T> a, final Iterator<? extends T> b) {
        return getEqualsCount(a, b, null);
    }

    public final static <A, B> int getEqualsCount(final Iterator<A> a, final Iterator<B> b, final ITransformer<A, B> transf) {
        int res = 0;
        while (a.hasNext() && b.hasNext()) {
            final A itemA = a.next();
            final B itemB = b.next();
            if (!CompareUtils.equals(transf == null ? itemA : transf.transformChecked(itemA), itemB))
                break;
            res++;
        }
        return res;
    }

    public static <T> Collection<T> select(final Collection<T> a, final IPredicate<? super T> pred) {
        return select(a, pred, new ArrayList<T>());
    }

    public static <T, C extends Collection<? super T>> C select(final Collection<T> a, final IPredicate<? super T> pred, final C b) {
        for (final T item : a)
            if (pred.evaluateChecked(item))
                b.add(item);
        return b;
    }

    // avoid name collision causing eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=319603
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> intersection(final Collection<T> a, final Collection<T> b) {
        return org.apache.commons.collections.CollectionUtils.intersection(a, b);
    }

    /**
     * Compute the intersection of a and b. <code>null</code>s are ignored : x ∩ null = x.
     * 
     * @param <T> type of collection.
     * @param a the first set, can be <code>null</code>.
     * @param b the second set, can be <code>null</code>.
     * @return the intersection.
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> inter(final Set<T> a, final Set<T> b) {
        return (Set<T>) interSubtype(a, b);
    }

    public static <T> Set<? extends T> interSubtype(final Set<? extends T> a, final Set<? extends T> b) {
        if (a == b)
            return a;
        else if (a == null)
            return b;
        else if (b == null)
            return a;
        else if (a.size() > b.size()) {
            return interSubtype(b, a);
        }

        final Set<T> res = new HashSet<T>();
        for (final T item : a) {
            if (b.contains(item))
                res.add(item);
        }
        return res;
    }

    public static <T> Set<T> inter(final Set<T>... sets) {
        return inter(Arrays.asList(sets));
    }

    public static <T> Set<T> inter(final List<Set<T>> sets) {
        final List<Set<T>> mutable = new ArrayList<Set<T>>(sets.size());
        for (final Set<T> s : sets) {
            // ignore nulls
            if (s != null)
                mutable.add(s);
        }

        if (mutable.isEmpty())
            return null;
        else if (mutable.size() == 1)
            return mutable.get(0);

        final int indexMin = indexOfMinSize(mutable);
        if (indexMin != 0) {
            mutable.add(0, mutable.remove(indexMin));
            return inter(mutable);
        }

        if (mutable.get(0).isEmpty())
            return Collections.emptySet();

        // replace the first 2 by their intersection
        // (inter will swap as appropriate if java doesn't evalute args in source order)
        mutable.add(0, inter(mutable.remove(0), mutable.remove(0)));
        return inter(mutable);
    }

    private static final <T> int indexOfMinSize(final List<Set<T>> sets) {
        if (sets.isEmpty())
            throw new IllegalArgumentException("empty sets");

        int res = 0;
        for (int i = 1; i < sets.size(); i++) {
            if (sets.get(i).size() < sets.get(res).size())
                res = i;
        }
        return res;
    }

    /**
     * Returns a {@link Set} containing the union of the given {@link Set}s.
     * 
     * @param <T> type of items.
     * @param a the first set, must not be <code>null</code>
     * @param b the second set, must not be <code>null</code>
     * @return the union of the two.
     */
    public static <T> Set<T> union(final Set<? extends T> a, final Set<? extends T> b) {
        final Set<T> res = new HashSet<T>(a);
        if (a != b)
            res.addAll(b);
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> subtract(final Collection<T> a, final Collection<? extends T> b) {
        return org.apache.commons.collections.CollectionUtils.subtract(a, b);
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> substract(final Collection<T> a, final Collection<? extends T> b) {
        return org.apache.commons.collections.CollectionUtils.subtract(a, b);
    }

    public static final <T> T coalesce(T o1, T o2) {
        return o1 != null ? o1 : o2;
    }

    public static final <T> T coalesce(T... objects) {
        for (T o : objects) {
            if (o != null)
                return o;
        }
        return null;
    }

    /**
     * Return the first item of <code>l</code> if it's the only one, otherwise <code>null</code>.
     * 
     * @param <T> type of list.
     * @param l the list.
     * @return the first item of <code>l</code> or <code>null</code>.
     */
    public static <T> T getSole(List<T> l) {
        return l.size() == 1 ? l.get(0) : null;
    }

    public static <T> T getSole(Collection<T> l) {
        return l.size() == 1 ? l.iterator().next() : null;
    }

    public static <T> T getFirst(Collection<T> l) {
        return l.size() > 0 ? l.iterator().next() : null;
    }

    /**
     * Return the first item of <code>l</code> if it isn't empty, otherwise <code>null</code>.
     * 
     * @param <T> type of list.
     * @param l the list.
     * @return the first item of <code>l</code> or <code>null</code>.
     */
    public static <T> T getFirst(List<T> l) {
        return getNoExn(l, 0);
    }

    /**
     * Return the last item of <code>l</code> if it isn't empty, otherwise <code>null</code>.
     * 
     * @param <T> type of list.
     * @param l the list.
     * @return the last item of <code>l</code> or <code>null</code>.
     */
    public static <T> T getLast(List<T> l) {
        return getNoExn(l, l.size() - 1);
    }

    /**
     * Return the item no <code>index</code> of <code>l</code> if it exists, otherwise
     * <code>null</code>.
     * 
     * @param <T> type of list.
     * @param l the list.
     * @param index the wanted index.
     * @return the corresponding item of <code>l</code> or <code>null</code>.
     */
    public static <T> T getNoExn(List<T> l, int index) {
        return index >= 0 && index < l.size() ? l.get(index) : null;
    }

    @SuppressWarnings("rawtypes")
    private static final Iterator EMPTY_ITERATOR = new Iterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    public static <T> LinkedList<T> toLinkedList(final Iterator<T> iter) {
        return addTo(iter, new LinkedList<T>());
    }

    public static <T> ArrayList<T> toArrayList(final Iterator<T> iter, final int estimatedSize) {
        return addTo(iter, new ArrayList<T>(estimatedSize));
    }

    public static <T, C extends Collection<? super T>> C addTo(final Iterator<T> iter, final C c) {
        while (iter.hasNext())
            c.add(iter.next());
        return c;
    }

    public static <T> ListIterator<T> getListIterator(final List<T> l, final boolean reversed) {
        if (!reversed)
            return l.listIterator();

        return reverseListIterator(l.listIterator(l.size()));
    }

    public static <T> ListIterator<T> reverseListIterator(final ListIterator<T> listIter) {
        if (listIter instanceof ReverseListIter)
            return ((ReverseListIter<T>) listIter).listIter;
        else
            return new ReverseListIter<T>(listIter);
    }

    private static final class ReverseListIter<T> implements ListIterator<T> {
        private final ListIterator<T> listIter;

        private ReverseListIter(ListIterator<T> listIter) {
            this.listIter = listIter;
        }

        @Override
        public boolean hasNext() {
            return this.listIter.hasPrevious();
        }

        @Override
        public T next() {
            return this.listIter.previous();
        }

        @Override
        public boolean hasPrevious() {
            return this.listIter.hasNext();
        }

        @Override
        public T previous() {
            return this.listIter.next();
        }

        @Override
        public int nextIndex() {
            return this.listIter.previousIndex();
        }

        @Override
        public int previousIndex() {
            return this.listIter.nextIndex();
        }

        @Override
        public void remove() {
            this.listIter.remove();
        }

        @Override
        public void set(T e) {
            this.listIter.set(e);
        }

        @Override
        public void add(T e) {
            throw new UnsupportedOperationException();
        }
    }

    public static <T> Set<T> createSet(T... items) {
        return new HashSet<T>(Arrays.asList(items));
    }

    public static <T> IdentitySet<T> createIdentitySet(T... items) {
        return new IdentityHashSet<T>(Arrays.asList(items));
    }

    /**
     * Return an {@link IdentitySet} consisting of <code>items</code>.
     * 
     * @param items the collection whose elements are to be in the result.
     * @return a set, possibly <code>items</code> if it's already an identity set.
     */
    public static <T> Set<T> toIdentitySet(Collection<T> items) {
        if (items instanceof IdentitySet)
            return (Set<T>) items;
        else
            return new IdentityHashSet<T>(items);
    }

    @SuppressWarnings("rawtypes")
    private static final IdentitySet EMPTY_SET = new EmptyIdentitySet();

    @SuppressWarnings("unchecked")
    public static <T> IdentitySet<T> emptyIdentitySet() {
        return EMPTY_SET;
    }

    private static final class EmptyIdentitySet extends AbstractSet<Object> implements IdentitySet<Object>, Serializable {
        @Override
        public Iterator<Object> iterator() {
            return emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean contains(Object obj) {
            return false;
        }

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    public static <K, V> Map<K, V> createMap(K key, V val, K key2, V val2) {
        // arguments are ordered, so should the result
        final Map<K, V> res = new LinkedHashMap<K, V>();
        res.put(key, val);
        res.put(key2, val2);
        return res;
    }

    public static <K, V> Map<K, V> createMap(K key, V val, K key2, V val2, K key3, V val3) {
        final Map<K, V> res = createMap(key, val, key2, val2);
        res.put(key3, val3);
        return res;
    }

    /**
     * Creates a map with null values.
     * 
     * @param <K> type of key.
     * @param <V> type of value.
     * @param keys the keys of the map.
     * @return a new map, if <code>keys</code> is a {@link List} it will be ordered.
     */
    public static <K, V> Map<K, V> createMap(Collection<? extends K> keys) {
        return fillMap(keys instanceof List ? new LinkedHashMap<K, V>(keys.size()) : new HashMap<K, V>(keys.size()), keys);
    }

    /**
     * Fills a map with null values.
     * 
     * @param <K> type of key.
     * @param <V> type of value.
     * @param <M> type of map.
     * @param m the map to fill.
     * @param keys the keys to add.
     * @return the passed map.
     */
    public static <K, V, M extends Map<K, V>> M fillMap(final M m, Collection<? extends K> keys) {
        return fillMap(m, keys, null);
    }

    /**
     * Fills a map with the same value.
     * 
     * @param <K> type of key.
     * @param <V> type of value.
     * @param <M> type of map.
     * @param m the map to fill.
     * @param keys the keys to add.
     * @param val the value to put.
     * @return the passed map.
     */
    public static <K, V, M extends Map<K, V>> M fillMap(final M m, final Collection<? extends K> keys, final V val) {
        for (final K key : keys)
            m.put(key, val);
        return m;
    }
}