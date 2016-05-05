/* $Id: DSAPublicKeySpec.java,v 1.1 2000/07/26 22:02:39 gelderen Exp $
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
 * DSA public key + parameters (y and g, p, q).
 *
 * <p>Immutable.</p>
 *
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @version $Revision: 1.1 $
 */
public class DSAPublicKeySpec implements KeySpec {

    private final BigInteger y, p, q, g;


    public DSAPublicKeySpec(BigInteger y,
                            BigInteger p, BigInteger q, BigInteger g)
    {
        this.y = y;
        this.p = p;
        this.q = q;
        this.g = g;
    }


    public BigInteger getG() {
        return this.g;
    }


    public BigInteger getP() {
        return this.p;
    }


    public BigInteger getQ() {
        return this.q;
    }


    public BigInteger getY() {
        return this.y;
    }
}
