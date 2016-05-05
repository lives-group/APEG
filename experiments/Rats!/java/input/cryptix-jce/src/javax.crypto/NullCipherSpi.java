/* $Id: NullCipherSpi.java,v 1.5 2000/01/15 03:25:04 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto;


import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


/**
 * Implements the identity cipher.  Encrypting and decrypting do
 * not alter the data. The NullCipher has a block size of 1.
 * <p>
 * This class does not belong in the API, it should reside a a provider.
 * Let's hope that Javasoft fixes this in a future release.
 */
class NullCipherSpi
extends CipherSpi
{
    protected void engineSetMode(String mode)
    throws NoSuchAlgorithmException {
        return;
    }


    protected void engineSetPadding(String padding)
    throws NoSuchPaddingException {
        return;
    }


    protected int engineGetBlockSize() {
        return 1;
    }


    protected int engineGetOutputSize(int inputLen) {
        // We don't buffer...
        return inputLen;
    }


    protected byte[] engineGetIV() {
        // Sun returns 8 bytes. Whatever...
        // FIXME: handle setting the IV and returning it
        return new byte[8];
    }


    protected AlgorithmParameters engineGetParameters() {
        return null;
    }


    protected void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException {
        return;
    }


    protected void
    engineInit(int opmode, Key key, AlgorithmParameterSpec params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException {
        return;
    }


    protected void
    engineInit(int opmode, Key key, AlgorithmParameters params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        return;
    }


    protected byte[]
    engineUpdate(byte[] input, int inputOffset, int inputLen)
    {
        byte[] b = new byte[inputLen];
        System.arraycopy(input, inputOffset, b, 0, inputLen);
        return b;
    }


    protected int
    engineUpdate(byte[] input, int inputOffset, int inputLen,
                 byte[] output, int outputOffset)
    throws ShortBufferException
    {
        if( (output.length-outputOffset) < inputLen )
            throw new ShortBufferException();

        System.arraycopy(input, inputOffset, output, outputOffset, inputLen);
        return inputLen;
    }


    protected byte[]
    engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
    {
        return engineUpdate(input, inputOffset, inputLen);
    }


    protected int
    engineDoFinal(byte[] input, int inputOffset, int inputLen,
                  byte[] output, int outputOffset)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
    {
        return engineUpdate(input, inputOffset, inputLen, output, outputOffset);
    }


}









    