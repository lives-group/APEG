/* $Id: RSAPrivateKeySpec.java,v 1.1 2000/07/26 20:46:50 gelderen Exp $
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
 * Specification of key material (modulus and private exponent) for an
 * RSA private key.
 * 
 * <p>Immutable.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class RSAPrivateKeySpec implements KeySpec {

    private final BigInteger modulus, privateExponent;


    public RSAPrivateKeySpec(BigInteger modulus, BigInteger privateExponent) {
        this.modulus = modulus;
        this.privateExponent = privateExponent;
    }


    public BigInteger getModulus() {
        return this.modulus;
    }


    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }
}
