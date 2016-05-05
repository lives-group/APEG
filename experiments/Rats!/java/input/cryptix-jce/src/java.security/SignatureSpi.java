/* $Id: SignatureSpi.java,v 1.5 2000/02/09 20:28:13 gelderen Exp $
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
 * JDK 1.1 compatibility layer: our Signatures derive from the JDK 1.2
 * class SignatureSpi which is not present in JDK 1.1.
 *
 * @version $Revision: 1.5 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public abstract class SignatureSpi
extends Signature
{
    public SignatureSpi() 
    {
        super("");
    }
}
