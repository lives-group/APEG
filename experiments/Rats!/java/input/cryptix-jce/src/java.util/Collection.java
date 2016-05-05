/* $Id: Collection.java,v 1.1 2000/08/07 01:32:57 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.util;


/**
 * Refer to Sun's JDK 1.2+ documentation.
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public interface Collection {

    boolean add(Object o);

    boolean addAll(Collection c);

    void clear();

    boolean contains(Object o);

    boolean containsAll(Collection c);

    boolean equals(Object o);

    int hashCode();

    boolean isEmpty();

    Iterator iterator();

    boolean remove(Object o);

    boolean removeAll(Collection c);

    boolean retainAll(Collection c);

    int size();

    Object[] toArray();

    Object[] toArray(Object[] a);
}
