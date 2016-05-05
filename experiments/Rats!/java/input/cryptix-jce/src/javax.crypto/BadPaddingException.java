/* $Id: BadPaddingException.java,v 1.5 2000/02/12 15:54:24 gelderen Exp $
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
 * Thrown when padding is expect but not found or when padding is corrupted.
 *
 * @version $revision: $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class BadPaddingException
extends GeneralSecurityException
{
    /**
     * Construct a BadPaddingException.
     */
    public BadPaddingException()
    {
        super();
    }


    /**
     * Construct a BadPaddingException with detail message.
     *
     * @param msg  detail message
     */
    public BadPaddingException(String msg)
    {
        super(msg);
    }
}
