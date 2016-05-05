/* $Id: RawSecretKey.java,v 1.8 2003/03/24 01:57:42 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.key;


import javax.crypto.SecretKey;


/**
 * FIXME: make package protected. fix tests first.
 *
 * @version $Revision: 1.8 $
 * @author  Jeroen C. van Gelderen <gelderen@cryptix.org>
 */
public class RawSecretKey implements SecretKey
{
    private final String algorithm;
    private final byte[] keyBytes;


    // FIXME: make protected
    public RawSecretKey(String algorithm, byte[] keyBytes)
    {
        this.algorithm = algorithm;
        this.keyBytes  = (byte[])keyBytes.clone();
    }


    public RawSecretKey(String algorithm, byte[] keyBytes, int off, int len)
    {
        this.algorithm = algorithm;
        this.keyBytes  = new byte[len];
        System.arraycopy(keyBytes, off, this.keyBytes, 0, len);
    }


    public String getAlgorithm()
    {
        return algorithm;
    }


    public String getFormat()
    {
        return "RAW";
    }


    public byte[] getEncoded()
    {
        return (byte[])keyBytes.clone();
    }
}
