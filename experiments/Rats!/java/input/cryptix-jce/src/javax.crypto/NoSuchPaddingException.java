/* $Id: NoSuchPaddingException.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 * 
 * Use, modification, copying and distribution of this software is subject 
 * the terms and conditions of the Cryptix General Licence. You should have 
 * received a copy of the Cryptix General Licence along with this library; 
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto;


import java.security.GeneralSecurityException;


/**
 * Thrown when the requested padding scheme is not available.
 *
 * @version $revision: $ 
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class NoSuchPaddingException
extends GeneralSecurityException {

    /**
     * Construct a NoSuchPaddingException.
     */
    public NoSuchPaddingException() {
        super();
    }
    
    
    /**
     * Construct a NoSuchPaddingException with detail message.
     *
     * @param msg  detail message
     */
    public NoSuchPaddingException(String msg) {
        super(msg);
    }
}