/* $Id: DHPrivateKeySpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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
import java.security.spec.KeySpec;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class DHPrivateKeySpec
implements KeySpec
{
    private final BigInteger x, p, g;


    public DHPrivateKeySpec(BigInteger x, BigInteger p, BigInteger g) 
    {
        this.x = x;
        this.p = p;
        this.g = g;
    }


    public BigInteger getX() 
    {
        return this.x;
    }


    public BigInteger getP() 
    {
        return this.p;
    }


    public BigInteger getG() 
    {
        return this.g;
    }
}
