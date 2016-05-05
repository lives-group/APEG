/* $Id: RSAPrivateCrtKeySpec.java,v 1.1 2000/07/26 21:05:31 gelderen Exp $
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
 * Specification of key material for an RSA private key with CRT values as
 * defined in RSA Lab's PKCS#1 Standard.
 * 
 * <p>Immutable.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class RSAPrivateCrtKeySpec extends RSAPrivateKeySpec {

    private final BigInteger
        publicExponent,
        primeP,
        primeQ,
        primeExponentP,
        primeExponentQ,
        crtCoefficient;


    public RSAPrivateCrtKeySpec(BigInteger modulus,
                                BigInteger publicExponent,
                                BigInteger privateExponent,
                                BigInteger primeP,
                                BigInteger primeQ,
                                BigInteger primeExponentP,
                                BigInteger primeExponentQ,
                                BigInteger crtCoefficient)
    {
        super(modulus, privateExponent);
        this.publicExponent = publicExponent;
        this.primeP = primeP;
        this.primeQ = primeQ;
        this.primeExponentP = primeExponentP;
        this.primeExponentQ = primeExponentQ;
        this.crtCoefficient = crtCoefficient;
    }


    public BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }


    public BigInteger getPrimeExponentP() {
        return this.primeExponentP;
    }


    public BigInteger getPrimeExponentQ() {
        return this.primeExponentQ;
    }


    public BigInteger getPrimeP() {
        return this.primeP;
    }


    public BigInteger getPrimeQ() {
        return this.primeQ;
    }


    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}
