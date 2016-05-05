/* $Id: SecretKeySpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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


import java.security.spec.KeySpec;
import javax.crypto.SecretKey;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class SecretKeySpec
implements KeySpec, SecretKey 
{
    private final byte[] key;
    private final String alg;


    public SecretKeySpec(byte[] key, String algorithm) 
    {
        this.key = (byte[])key.clone();
        this.alg = algorithm;
    }


    public SecretKeySpec(byte[] key, int offset, int len, String algorithm) 
    {
        this.key = new byte[len];
        System.arraycopy(key, offset, this.key, 0, len);
        this.alg = algorithm;
    }


    public String getAlgorithm() 
    {
        return this.alg;
    }


    public String getFormat() 
    {
        return "RAW";
    }


    public byte[] getEncoded() 
    {
        return (byte[])this.key.clone();
    }


    public int hashCode() 
    {
        // XXX todo...
        throw new RuntimeException("Method not yet implemented.");
    }


    public boolean equals(Object obj) 
    {
        // XXX todo
        throw new RuntimeException("Method not yet implemented.");
    }
}
