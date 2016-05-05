/* $Id: PBEWithMD2AndDES.java,v 1.1 2000/06/09 21:43:45 pw Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General License along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.pbe;

import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 */
public final class PBEWithMD2AndDES extends PBEBase
{

// Variables and constants
//............................................................................

    /** Name of the used symmetric algorithm. */
    private static final String algorithm = "DES";
    /** Name of the used message digest function. */
    private static final String messageDigest = "MD2";

// Constructors
//............................................................................
    /**
     * This is the constructor. It creates a cipher object using
     * provider Cryptix, cipher DES in CBC mode and PKCS#5
     * padding.
     *
     */
    public PBEWithMD2AndDES ()
    {
        super();
        try
        {
            this.cipher = Cipher.getInstance(algorithm+"/"+mode+"/"+
                                             padding, provider);
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
        catch (NoSuchPaddingException nspe)
        {
            nspe.printStackTrace();
        }
        catch (NoSuchProviderException nspe)
        {
            nspe.printStackTrace();
        }
    }


// PBEBase implementation
//............................................................................
    /**
     *
     */
    protected void coreInit(int opmode, byte[] password,
                           byte[] salt, int iteration)
                   throws InvalidKeyException
    {
        //
        int keysize = DESKeySpec.DES_KEY_LEN;

        // Let's run the message digest on the password
        byte [] key = coreKeyGen(messageDigest, password, salt,
                                 iteration, keysize);
        // Create a DES key using the key bytes as input.
        // For this we have to use a KeySpec and KeyFactory
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory skf = null;
        SecretKey desKey = null;

        try
        {
            skf = SecretKeyFactory.getInstance(algorithm);
            desKey = skf.generateSecret(desKeySpec);

            // We have to use AlgorithmParameterSpec for cipher init.
            IvParameterSpec ivSpec = new IvParameterSpec(key);

            // Initialize the cipher.
            // PBE needs this init method.
            cipher.init(opmode, desKey, ivSpec);
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new InvalidKeyException("Cannot create secret key!");
        }
        catch (InvalidKeySpecException ikse)
        {
            throw new InvalidKeyException("Cannot generate secret key!");
        }
        catch (InvalidAlgorithmParameterException iape)
        {
            throw new InvalidKeyException("Cannot init cipher!");
        }

    }
}
