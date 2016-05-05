/* $Id: DESedeKeySpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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


import java.security.InvalidKeyException;
import java.security.spec.KeySpec;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class DESedeKeySpec
implements KeySpec 
{
    public static final int DES_EDE_KEY_LEN = 24;


    private final byte[] key;


    public DESedeKeySpec(byte[] key)
    throws InvalidKeyException 
    {
        this(key, 0);
    }


    public DESedeKeySpec(byte[] key, int offset)
    throws InvalidKeyException 
    {
        if( (key.length-offset) < DES_EDE_KEY_LEN )
            throw new InvalidKeyException("Wrong size");

        this.key = new byte[DES_EDE_KEY_LEN];
        System.arraycopy(key, offset, this.key, 0, DES_EDE_KEY_LEN);
    }


    public byte[] getKey() 
    {
        return (byte[])this.key.clone();
    }


    public static boolean isParityAdjusted(byte[] key, int offset)
    throws InvalidKeyException 
    {
        throw new RuntimeException("Method not yet implemented");
    }
}





    