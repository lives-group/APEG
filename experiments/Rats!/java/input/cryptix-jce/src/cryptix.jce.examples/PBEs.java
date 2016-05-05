/* $Id: PBEs.java,v 1.4 2000/01/20 14:59:23 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.examples;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;


/**
 * This class is intended to show usage of PBE algorithms.
 * @version $Revision: 1.4 $
 * @author  Josef Hartmann (jhartmann@bigfoot.com)
 */
public final class PBEs
{
    private String algorithm = null;
    private String filename = null;
    private Key secretKey=null;

    // Use this salt.
    // In practice you should use a really random salt.
    // For encryption and decryption you have to use the same salt.
    /** salt. */
    private final byte [] salt = {
        (byte)0x20, (byte)0x121, (byte)0x010, (byte)0x55,
        (byte)0x83, (byte)0x001, (byte)0x101, (byte)0x91
    };

    /**
     * Iteration count for running hash function on password.
     * E.g. md5(md5(md5(md5(password))))
     */
    private final int iterations = 20;


    /**
     * Constructor initialized with the algorithm name and
     * the password for use in the PBE algorithm.
     *
     * @param algorithm  String
     * @param way        String
     * @param passphrase String
     * @param filename   String
     */
    public PBEs(String algorithm, String passphrase)
    {
        // Store the algorithm.
        this.algorithm = algorithm;

        // Stripe passphrase into an array of chars.
        char [] pw = new char[passphrase.length()];
        passphrase.getChars(0,passphrase.length(),pw,0);

        // Create a KeySpec of the password chars.
        KeySpec ks = new PBEKeySpec(pw);

        // Create a secret key based on the passphrase
        // for the specified algorithm.
        SecretKeyFactory skf = null;
        try
        {
            // Use a keyfactory in order to create a key
            // for the PBE algorithm.
            skf = SecretKeyFactory.getInstance(algorithm);
            // Generate the secret key.
            secretKey = skf.generateSecret(ks);
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
        catch (InvalidKeySpecException ikse)
        {
            ikse.printStackTrace();
        }


    }


    /**
     * This method runs the PBE algorithm on the file.
     *
     * @param mode int decrypt or encrypt.
     * @param filename String name of file to encrypt
     * @return boolean
     */
    public boolean run(int mode, String filename) {
        boolean ret = false;

        // Create an algorithm parameter spec object based
        // on the used salt and iterations.
        AlgorithmParameterSpec aps = new PBEParameterSpec(salt, iterations);
        Cipher cipher = null;

        try
        {
            // Create the cipher object.
            cipher = Cipher.getInstance(algorithm);
            // Init cipher with mode, key and algorithm parameter spec.
            cipher.init(mode, secretKey, aps);
            ret = true;
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
        catch (NoSuchPaddingException nspe)
        {
            nspe.printStackTrace();
        }
        catch (InvalidKeyException ike)
        {
            ike.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException iape)
        {
            iape.printStackTrace();
        }

        if (ret)
            ret=false;
        else
            return ret;

        CipherOutputStream cOutStream = null;
        CipherInputStream  cInpStream = null;
        FileInputStream fInput = null;
        FileOutputStream fOutput = null;

        try
        {
            if (mode == Cipher.ENCRYPT_MODE) {

                fInput = new FileInputStream (filename);
                fOutput = new FileOutputStream (
                                  filename+".PBEencrypted."+algorithm);

                cOutStream = new CipherOutputStream(fOutput, cipher);

                // Read input bytes into buffer and run them through the
                // cipher stream.
                byte[] buffer = new byte[8192];
                int length=0;

                while ((length=fInput.read(buffer)) != -1)
                {
                    cOutStream.write(buffer, 0, length);
                }

                // Close Streams.
                fInput.close();
                cOutStream.close();
                ret = true;

            } // if
            else
            {
                // Cipher.DECRYPT_MODE
                // Here we are in decryption mode.
                // CipherInputStream cInpStream=null;
                // Open file containing encrypted data.
                fInput = new FileInputStream(
                                 filename+".PBEencrypted."+algorithm);

                cInpStream = new CipherInputStream(fInput,cipher);

                fOutput = new FileOutputStream(
                                  filename+".PBEdecrypted."+algorithm);

                // Read bytes and run them through cipher and
                // store them to file back again.
                byte[] buffer=new byte[8192];
                int length=0;

                while ((length=cInpStream.read(buffer)) != -1) {
                    fOutput.write(buffer, 0, length);
                }

                // Close streams.
                cInpStream.close();
                fOutput.close();
                ret = true;
            }
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
            return false;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }

        return ret;
    }
}
