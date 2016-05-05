/* $Id: KeyPairGeneratorSpi.java,v 1.1 2000/02/09 20:29:26 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security;


/**
 * JDK 1.1 compatibility layer: our KeyPairGenerators derive from the JDK 1.2
 * class KeyPairGeneratorSpi which is not present in JDK 1.1.
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public abstract class KeyPairGeneratorSpi
extends KeyPairGenerator
{
    public KeyPairGeneratorSpi() 
    {
        super(""); // empty string (see MessageDigestSpi for explanation)
    }
}
