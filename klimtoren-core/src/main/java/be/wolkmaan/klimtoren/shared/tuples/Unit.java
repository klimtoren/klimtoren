/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.shared.tuples;

import be.wolkmaan.klimtoren.shared.tuples.valueintf.IValue0;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author karl
 * @param <A>
 */
public final class Unit<A> extends Tuple implements IValue0<A> {

    private static final int SIZE = 1;

    private final A val0;

    public static <A> Unit<A> with(final A value0) {
        return new Unit<>(value0);
    }

    public static <X> Unit<X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (array.length != 1) {
            throw new IllegalArgumentException("Array must have exactly 1 element in order to create a Unit. Size is " + array.length);
        }
        return new Unit<>(array[0]);
    }

    public static <X> Unit<X> fromCollection(final Collection<X> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection cannot be null.");
        }
        if (collection.size() != 1) {
            throw new IllegalArgumentException("Collection must have exactly 1 element in order to create a Unit. Size is " + collection.size());
        }
        final Iterator<X> iter = collection.iterator();
        return new Unit<>(iter.next());
    }

    public static <X> Unit<X> fromIterable(final Iterable<X> iterable) {
        return fromIterable(iterable, 0, true);
    }

    public static <X> Unit<X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    private static <X> Unit<X> fromIterable(Iterable<X> iterable, int index, boolean exactSize) {
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }
        boolean tooFewElements = false;
        X element0 = null;

        final Iterator<X> iter = iterable.iterator();

        int i = 0;
        while (i < index) {
            if (iter.hasNext()) {
                iter.next();
            } else {
                tooFewElements = true;
            }
            i++;
        }
        if (iter.hasNext()) {
            element0 = iter.next();
        } else {
            tooFewElements = true;
        }

        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Unit (1 needed)");
        }
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 1 available element in order to create a Unit.");
        }
        return new Unit<>(element0);
    }

    public Unit(final A value0) {
        super(value0);
        this.val0 = value0;
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public A getValue0() {
        return this.val0;
    }

}
