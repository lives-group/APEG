/* $Id: PBEBase.java,v 1.1 2000/06/09 21:43:45 pw Exp $
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


import cryptix.jce.provider.key.RawSecretKey;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import javax.crypto.spec.PBEParameterSpec;


/**
 * This is the base class for any PBE cipher.
 * It is derived from CipherSpi as other ciphers are.
 *
 * @author: Josef Hartmann (jhartmann@bigfoot.com)
 * @version: $Revision: 1.1 $
 */
public abstract class PBEBase extends CipherSpi
{

// Variables and constants
//............................................................................

    /** PBE ciphers are run in CBC mode. */
    protected static final String mode = "CBC";
    // Although it does not fit into the naming scheme I am using this one to be
    // compatible.
    /** PBE ciphers do PKCS#5 padding. */
    protected static final String padding = "PKCS#5";
    /** Provider we use. */
    protected static final String provider = "Cryptix";
    /** Used cipher.*/
    protected Cipher cipher = null;

// Constructors and Object() methods
//............................................................................


    protected PBEBase ()
    {
        super();
    }


    /**
     * Always throws a CloneNotSupportedException (cloning of ciphers is not
     * supported for security reasons).
     *
     * @exception CloneNotSupportedException always throw this exception.
     */
    public final Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }


// CipherSPI implementation
//............................................................................


    /**
     * Sets the mode of a cipher. PBE only uses CBC for its cipher so we only
     * allow CBC as parameter.
     *
     * @param mode String name of the requested mode.
     *
     * @exception NoSuchAlgorithmException if param is not CBC.
     */
    protected void engineSetMode(String mode)
    throws NoSuchAlgorithmException
    {
        if (!mode.equalsIgnoreCase(this.mode))
        {
            throw new NoSuchAlgorithmException("Only CBC supported by PBE.");
        }
    }


    /**
     * Sets the padding of the cipher. PBE uses PKCS#5 padding scheme so we only
     * allow PKCS#5 padding strings as arguments.
     *
     * @param padding String name for the requested padding scheme.
     *
     * @exception NoSuchPaddingException if param is not PKCS#5 like.
     */
    protected void engineSetPadding(String padding)
    throws NoSuchPaddingException
    {
        if (!padding.equalsIgnoreCase(this.padding)||
            !padding.equalsIgnoreCase("PKCS5") ||
            !padding.equalsIgnoreCase("PKCS5Padding"))
        {
                throw new NoSuchPaddingException("Only PKCS#5"+
                                                 " suppported by PBE.");
        }
    }


    /**
     * Returns the blocksize of the underlying cipher.
     *
     * @return the blocksize of the underlying cipher.
     */
    protected int engineGetBlockSize()
    {
         // Return inside cipher's blocksize.
         return this.cipher.getBlockSize();
    }


    /**
     * Returns the output size of the underlying cipher if
     * input has given length.
     *
     * @param inputLen int number of input bytes.
     *
     * @return output size of the underlying cipher.
     */
    protected int engineGetOutputSize(int inputLen)
    {
        return this.cipher.getOutputSize(inputLen);
    }


    /**
     * Returns the IV of the underlying cipher.
     *
     * @return the IV.
     */
    protected byte[] engineGetIV()
    {
        return this.cipher.getIV();
    }


    /**
     * Returns the algorithm parameters of the underlying cipher.
     *
     * @return algorithm parameters.
     */
    protected AlgorithmParameters engineGetParameters()
    {
        return this.cipher.getParameters();
    }


    /**
     * Initializes the cipher. As this method does not provide all
     * input data needed for PBE it always throws an exception.
     *
     * @param opmode int
     * @param key Key
     * @param random SecureRandom
     * @exception InvalidKeyException
     */
    protected void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException
    {
        throw new InvalidKeyException(
                  "This is not an appropriate method for initializing PBEs.");
    }


    /**
     * Initializes the cipher. This init method gives us all the needed
     * information (through AlgorithmParameterSpec) for PBE.
     *
     * @param opmode int
     * @param key Key
     * @param params AlgorithmParameterSpec
     * @param random SecureRandom
     *
     * @exception InvalidKeyException if we don't get a PBEKey.
     * @exception InvalidAlgorithmParameterException
     *                   if params is not PBEParameterSpec.
     */
    protected void engineInit(int opmode, Key key,
                              AlgorithmParameterSpec params,
                              SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        PBEParameterSpec pparams = null;
        // Check if we got proper parameters.
        // FIXME: If we get here a RawSecretKey of a different provider
        //        it does not work, however it should do, shouldn't it?
        if ((key == null) || !(key instanceof RawSecretKey))
        {
            throw new InvalidKeyException("Key not usable for PBE.");
        }
        if (params == null)
        {
            throw new InvalidAlgorithmParameterException(
                             "Inappropriate AlgorithmParameterSpec.");
        }
        else
        {
            pparams = (PBEParameterSpec) params;
        }

        // salt has to be at least 8 byte.
        if (pparams.getSalt().length < 8)
        {
            throw new InvalidAlgorithmParameterException("Salt too short!");
        }

        coreInit(opmode, key.getEncoded(), pparams.getSalt(),
                 pparams.getIterationCount());
    }


    /**
     * Initialize the cipher using mainly AlgorithmParameters for
     * further information needed by PBE.
     * Get the AlgorithmParameterSpec of AlgorithmParameters call the
     * other init method.
     *
     * @param opmode int Either Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @param key Key key used for init.
     * @param params AlgorithmParameters algorithm parameters
     * @param random random values for the engine.
     *
     * @exception InvalidKeyException
     * @exception InvalidAlgorithmParameterException
     */
    protected void engineInit(int opmode, Key key, AlgorithmParameters params,
                              SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        PBEParameterSpec pparams = null;
        // Check parameters.
        if ((key == null) || !(key instanceof RawSecretKey))
        {
            throw new InvalidKeyException("Key not usable for PBE.");
        }
        if (params == null)
        {
            throw new InvalidAlgorithmParameterException(
                             "Inappropiate AlgorithmParameters");
        }
        else
        {
            // Get the AlgorithmParameterSpec
            try
            {
                Class pbeParamSpecClass =
                      Class.forName("javax.crypto.spec.PBEParameterSpec");
                pparams = (PBEParameterSpec)
                      params.getParameterSpec(pbeParamSpecClass);
            }
            catch (ClassNotFoundException cnfe)
            {
                throw new InvalidAlgorithmParameterException(
                      "Error dealing with AlgorithmParameters parameter.");
            }
            catch (InvalidParameterSpecException ipse)
            {
                throw new InvalidAlgorithmParameterException(
                      "Error dealing with AlgorithmParameters parameter.");
            }
            // let's run the other init method.
            engineInit(opmode, key, pparams, random);
        }
    }


    /**
     * Feed the cipher with given length of data of the input buffer starting
     * at offset.
     *
     * @param input byte[] array of bytes used as input data.
     * @param inputOffset int start of data.
     * @param inputLen int length of input data.
     *
     * @return Encrypted or decrypted array of bytes.
     */
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen)
    {
        return this.cipher.update(input, inputOffset, inputLen);
    }


    /**
     * Feed the cipher with given length of data of the input buffer starting
     * at offset. The result is written into the provided buffer starting
     * at provided offset.
     *
     * @param input byte[] array of bytes used as input data.
     * @param inputOffset int starting index of input buffer.
     * @param inputLen int number of bytes of input buffer used to
     *        feed to the cipher.
     * @param output byte[] array of bytes where output will be written to.
     * @param outputOffset int starting index of output buffer.
     *
     * @return number of bytes written to the provided buffer
     */
    protected int engineUpdate(byte[] input, int inputOffset, int inputLen,
                               byte[] output, int outputOffset)
    throws ShortBufferException
    {
        return this.cipher.update(input, inputOffset, inputLen,
                                  output, outputOffset);
    }


    /**
     * Do the last step of the encryption or decryption.
     *
     * @param input byte[]
     * @param inputOffset int
     * @param inputLen int
     *
     * @return the en- or decrypted bytes of input bytes.
     */
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
    {
        return this.cipher.doFinal(input, inputOffset, inputLen);
    }


    /**
     * Do the last step of encryption or decryption.
     *
     * @param input byte[] array of bytes used for input.
     * @param inputOffset int starting index
     * @param inputLen int number of input bytes
     * @param output byte[] buffer for output.
     * @param outputOffset int starting index where to start writing.
     *
     * @return number of bytes written to the output buffer.
     */
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen,
                                byte[] output, int outputOffset)
    throws ShortBufferException, IllegalBlockSizeException,
           BadPaddingException
    {
        return this.cipher.doFinal(input, inputOffset, inputLen,
                                   output, outputOffset);
    }


// Own abstract methods.
// As done in BlockCipher.java+Mode.java, we build up an abstract method for
// subclasses which implement thes for doing the real ciphering job.
//.............................................................................

    /**
     * This method is only implemented by subclasses of PBEBase.
     * All params which are needed for PBE are passed to it
     *
     * @param opmode int either Cipher.ENCRYPT_MODE or CIPHER.DECRYPTD_MODE
     * @param password byte[] password as array of bytes as stored in PBEKey
     * @param salt byte[] salt
     * @param iteration int iteration count of message digest.
     */

    protected abstract void coreInit(int opmode, byte[] password,
                                     byte[] salt, int iteration)
                                     throws InvalidKeyException;

    /**
     * This method produces the key using specified message digest
     * function, password, salt and iteration count.
     *
     *
     *
     * @return array of bytes which will be used as key for PBE.
     */
    protected byte [] coreKeyGen(String mdStr, byte[] password,
                              byte[] salt, int iteration, int keysize)
                      throws InvalidKeyException
    {
        // Get the message digest.
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(mdStr, provider);
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new
                  InvalidKeyException("Message digest algorithm not found.");
        }
        catch (NoSuchProviderException nspe)
        {
            throw new InvalidKeyException("Provider not found.");
        }
        // concatenate password and salt.
        byte [] pwAndSalt = new byte[password.length+salt.length];
        System.arraycopy(password, 0, pwAndSalt, 0, password.length);
        System.arraycopy(salt, 0, pwAndSalt, password.length, salt.length);

        byte[] hash = pwAndSalt;
        // Create the key as md(md(md(md(...(pw+salt))...)
        for (int i=0; i<iteration; i++)
        {
            md.update(hash);
            hash = md.digest();
        }
        byte [] key = new byte[keysize];
        System.arraycopy(hash, 0, key, 0, keysize);
        return key;
    }
}
