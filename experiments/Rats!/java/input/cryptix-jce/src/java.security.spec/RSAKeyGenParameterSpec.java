/* $Id: RSAKeyGenParameterSpec.java,v 1.1 2000/07/26 22:09:58 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.spec;


import java.math.BigInteger;


/**
 * Parameters for RSA key generation.
 *
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @version $Revision: 1.1 $
 */
public class RSAKeyGenParameterSpec implements AlgorithmParameterSpec {

    private final int keysize;


    private final BigInteger publicExponent;


    public static final BigInteger F0 = BigInteger.valueOf(3);


    public static final BigInteger F4 = BigInteger.valueOf(65537);


    public RSAKeyGenParameterSpec(int keysize, BigInteger publicExponent) {
        this.keysize = keysize;
        this.publicExponent = publicExponent;
    }


    public int getKeysize() {
        return this.keysize;
    }


    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}
