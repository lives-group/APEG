/* $Id: NoSuchElementException.java,v 1.1 2000/08/07 01:32:57 gelderen Exp $
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
public class NoSuchElementException extends RuntimeException {

    public NoSuchElementException() {
        super();
    }


    public NoSuchElementException(String s) {
        super(s);
    }
}
