/* $Id: DHParameterSpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto.spec;


import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class DHParameterSpec
implements AlgorithmParameterSpec
{
    private final BigInteger p, g;


    private final int l;


    // assumes l=0
    public DHParameterSpec(BigInteger p, BigInteger g) 
    {
        this(p, g, 0);
    }


    public DHParameterSpec(BigInteger p, BigInteger g, int l) 
    {
        this.p = p;
        this.g = g;
        this.l = l;
    }


    public BigInteger getP() 
    {
        return this.p;
    }


    public BigInteger getG() 
    {
        return this.g;
    }


    // returns 0 if l not set
    public int getL() 
    {
        return this.l;
    }
}
