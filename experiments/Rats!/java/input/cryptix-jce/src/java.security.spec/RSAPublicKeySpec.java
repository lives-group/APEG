/* $Id: RSAPublicKeySpec.java,v 1.1 2000/07/26 20:44:11 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.spec;


import java.math.BigInteger;


/**
 * Specification of key material (modulus and public exponent) for an
 * RSA key.
 * 
 * <p>Immutable.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class RSAPublicKeySpec implements KeySpec {

    private final BigInteger modulus, publicExponent;


    public RSAPublicKeySpec(BigInteger modulus, BigInteger publicExponent) {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
    }


    public BigInteger getModulus() {
        return this.modulus;
    }


    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}
