/* $Id: KeyAgreementSpi.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


public abstract class KeyAgreementSpi {

    protected abstract void
    engineInit(Key key, SecureRandom random)
    throws InvalidKeyException;


    protected abstract void
    engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException;


    protected abstract Key
    engineDoPhase(Key key, boolean lastPhase)
    throws InvalidKeyException, IllegalStateException;


    protected abstract byte[]
    engineGenerateSecret()
    throws IllegalStateException;


    protected abstract int
    engineGenerateSecret(byte[] sharedSecret, int offset)
    throws IllegalStateException, ShortBufferException;


    protected abstract SecretKey
    engineGenerateSecret(java.lang.String algorithm)
    throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException;
}






