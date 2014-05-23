/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.shared.tuples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author karl
 */
public abstract class Tuple implements Serializable, Comparable<Tuple>, Iterable<Object> {

    private final Object[] valueArray;
    private final List<Object> valueList;
    /*
     | C'tor
     */

    protected Tuple(final Object... values) {
        super();
        this.valueArray = values;
        this.valueList = Arrays.asList(values);
    }

    /*
     | Properties
     */
    public abstract int getSize();

    public final Object getValue(final int pos) {
        if (pos >= getSize()) {
            throw new IllegalArgumentException("Cannot retrieve position " + pos + " in " + this.getClass().getSimpleName()
                    + ". Positions for this class start with 0 and end with " + (this.getSize() - 1));
        }
        return this.valueArray[pos];
    }

    @Override
    public final String toString() {
        return this.valueList.toString();
    }

    public final boolean contains(final Object value) {
        for (final Object val : this.valueList) {
            if (val == null) {
                if (value == null) {
                    return true;
                }
            } else {
                if (val.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean containsAll(final Collection<?> collection) {
        return collection.stream().noneMatch((value) -> (!contains(value)));
    }

    public final boolean containsAll(final Object... values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array cannot be null.");
        }
        for (final Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    public final int indexOf(final Object value) {
        int i = 0;
        for (final Object val : this.valueList) {
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else {
                if (val.equals(value)) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public final int lastIndexOf(final Object value) {
        for (int i = getSize() - 1; i >= 0; i--) {
            final Object val = this.valueList.get(i);
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else {
                if (val.equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public final List<Object> toList() {
        return Collections.unmodifiableList(new ArrayList<Object>(this.valueList));
    }
    public final Object[] toArray() {
        return this.valueArray.clone();
    }
    
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result 
                + ((this.valueList == null) ? 0 : this.valueList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple other = (Tuple) obj;
        return Objects.equals(this.valueList, other.valueList);
    }

    @Override
    public int compareTo(Tuple o) {
        final int tLen = this.valueArray.length;
        final Object[] oValues = o.valueArray;
        final int oLen = oValues.length;
        
        for(int i = 0; i<tLen && i<oLen; i++) {
            final Comparable tElement = (Comparable)this.valueArray[i];
            final Comparable oElement = (Comparable)oValues[i];
            
            final int comparison = tElement.compareTo(oElement);
            if(comparison != 0) {
                return comparison;
            }
        }
        return (Integer.valueOf(tLen)).compareTo(oLen);
    }

    @Override
    public Iterator<Object> iterator() {
        return this.valueList.iterator();
    }

}
