/* $Id: DESKeySpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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
 * @author  David Hopwood
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @author  Raif Naffah
 */
public class DESKeySpec
implements KeySpec 
{
    public static final int DES_KEY_LEN = 8;


    private final byte[] key;


    public DESKeySpec(byte[] key)
    throws InvalidKeyException 
    {
        this(key, 0);
    }


    public DESKeySpec(byte[] key, int offset)
    throws InvalidKeyException
    {
        if( (key.length-offset) < DES_KEY_LEN )
            throw new InvalidKeyException("Wrong size");

        this.key = new byte[DES_KEY_LEN];
        System.arraycopy(key, offset, this.key, 0, DES_KEY_LEN);
    }


    public byte[] getKey() 
    {
        return (byte[])this.key.clone();
    }


    public static boolean isParityAdjusted(byte[] key, int offset)
    throws InvalidKeyException 
    {
        throw new RuntimeException("Method not yet implemented.");
    }



    /**
     * Returns true iff the bytes at key[offset..offset+7] represent a weak
     * or semi-weak single DES key. It can be called either before or after
     * setting parity bits.
     * <p>
     * (This checks for the 16 weak and semi-weak keys as given by Schneier,
     * <cite>Applied Cryptography 2nd ed.</cite>, tables 12.11 and 12.12. It
     * does not check for the possibly-weak keys in table 12.13.)
     *
     * @throws InvalidKeyException
     */
    public static boolean isWeak(byte[] key, int offset)
    throws InvalidKeyException
    {
        int a = (key[offset  ] & 0xFE) << 8 | (key[offset+1] & 0xFE);
        int b = (key[offset+2] & 0xFE) << 8 | (key[offset+3] & 0xFE);
        int c = (key[offset+4] & 0xFE) << 8 | (key[offset+5] & 0xFE);
        int d = (key[offset+6] & 0xFE) << 8 | (key[offset+7] & 0xFE);

        return (a == 0x0000 || a == 0xFEFE) &&
               (b == 0x0000 || b == 0xFEFE) &&
               (c == 0x0000 || c == 0xFEFE) &&
               (d == 0x0000 || d == 0xFEFE);
    }
}

