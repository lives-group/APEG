/* $Id: IvParameterSpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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


import java.security.spec.AlgorithmParameterSpec;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class IvParameterSpec
implements AlgorithmParameterSpec 
{
    private final byte[] iv;


    public IvParameterSpec(byte[] iv) 
    {
        this.iv = (byte[])iv.clone();
    }


    public IvParameterSpec(byte[] iv, int offset, int len) 
    {
        this.iv = new byte[len];
        System.arraycopy(iv, offset, this.iv, 0, len);
    }


    public byte[] getIV() 
    {
        return (byte[])this.iv.clone();
    }
}
