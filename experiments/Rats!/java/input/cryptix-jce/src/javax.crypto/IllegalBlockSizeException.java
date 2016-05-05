/* $Id: IllegalBlockSizeException.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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
 * Thrown when the length of the input data is not a multiple of the Cipher's
 * block size and no padding was specified.
 *
 * @version $revision: $ 
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class IllegalBlockSizeException
extends GeneralSecurityException {
    
    /**
     * Construct a IllegalBlockSizeException.
     */
    public IllegalBlockSizeException() {
        super();
    }
    

    /**
     * Construct a IllegalBlockSizeException with detail message.
     *
     * @param msg  detail message
     */
    public IllegalBlockSizeException(String msg) {
        super(msg);
    }
}
