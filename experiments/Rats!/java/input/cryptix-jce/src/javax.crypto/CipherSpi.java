/* $Id: CipherSpi.java,v 1.7 2000/06/01 17:26:30 pw Exp $
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
import java.security.Key ;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


public abstract class CipherSpi {

    public
    CipherSpi() {}


    protected abstract void
    engineSetMode(String mode)
    throws NoSuchAlgorithmException;


    protected abstract void
    engineSetPadding(String padding)
    throws NoSuchPaddingException;


    protected abstract int
    engineGetBlockSize();


    protected abstract int
    engineGetOutputSize(int inputLen);


    protected abstract byte[]
    engineGetIV();


    protected abstract AlgorithmParameters
    engineGetParameters();


    protected abstract void
    engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException;


    protected abstract void
    engineInit(int opmode, Key key, AlgorithmParameterSpec params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException;


    protected abstract void
    engineInit(int opmode, Key key, AlgorithmParameters params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException;


    protected abstract byte[]
    engineUpdate(byte[] input, int inputOffset, int inputLen);


    protected abstract int
    engineUpdate(byte[] input, int inputOffset, int inputLen,
                 byte[] output, int outputOffset)
    throws ShortBufferException;


    protected abstract byte[]
    engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException;


    protected abstract int
    engineDoFinal(byte[] input, int inputOffset, int inputLen,
                  byte[] output, int outputOffset)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException;

    protected byte[] 
    engineWrap(Key key)
    throws IllegalBlockSizeException, InvalidKeyException {
	throw new UnsupportedOperationException("Not implemented by the provider!");
    }

    protected Key 
    engineUnwrap(byte[] wrappedKey, 
		 String wrappedKeyAlgorithm, 
		 int wrappedKeyType)
    throws InvalidKeyException, NoSuchAlgorithmException {
	throw new UnsupportedOperationException("Not implemented by the provider!");
    }

    
    protected int 
    engineGetKeySize(Key key)
    throws InvalidKeyException {
	throw new UnsupportedOperationException("Not implemented by the provider!");
    }
}






