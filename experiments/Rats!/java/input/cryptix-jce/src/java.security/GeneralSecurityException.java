/* $Id: GeneralSecurityException.java,v 1.4 2000/02/09 20:28:13 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security;


/**
 * This exception is superclass for most java.security.* related exceptions.
 *
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class GeneralSecurityException
extends Exception
{
    public GeneralSecurityException() 
    {
        super();
    }


    public GeneralSecurityException(String msg) 
    {
        super(msg);
    }
}
