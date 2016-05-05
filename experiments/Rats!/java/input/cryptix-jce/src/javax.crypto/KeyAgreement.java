/* $Id: KeyAgreement.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


public class KeyAgreement {

    private final KeyAgreementSpi spi;
    private final Provider        provider;
    private final String          algorithm;


    protected
    KeyAgreement(KeyAgreementSpi keyAgreeSpi, Provider provider,
                 String algorithm)
    {
        this.spi       = keyAgreeSpi;
        this.provider  = provider;
        this.algorithm = algorithm;
    }


    public final String getAlgorithm() {
        return this.algorithm;
    }


    public static final KeyAgreement getInstance(String algorithm)
    throws NoSuchAlgorithmException {

        Object[] o =
            Support.getImplementation("KeyAgreement", algorithm);
        return
            new KeyAgreement((KeyAgreementSpi)o[0], (Provider)o[1], algorithm);
    }


    public static final KeyAgreement
    getInstance(String algorithm, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException {

        Object[] o =
            Support.getImplementation("KeyAgreement", algorithm, provider);
        return
            new KeyAgreement((KeyAgreementSpi)o[0], (Provider)o[1], algorithm);
    }


    public final Provider getProvider() {
        return this.provider;
    }


    public final void init(Key key)
    throws InvalidKeyException {
        this.spi.engineInit(key, new SecureRandom());
    }


    public final void init(Key key, SecureRandom random)
    throws InvalidKeyException {
        this.spi.engineInit(key, random);
    }


    public final void init(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.spi.engineInit(key, params, new SecureRandom());
    }


    public final void
    init(Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.spi.engineInit(key, params, random);
    }


    public final Key doPhase(Key key, boolean lastPhase)
    throws InvalidKeyException, IllegalStateException {
        return this.spi.engineDoPhase(key, lastPhase);
    }


    public final byte[] generateSecret()
    throws IllegalStateException {
        return this.spi.engineGenerateSecret();
    }


    public final int generateSecret(byte[] sharedSecret, int offset)
    throws IllegalStateException, ShortBufferException {
        return this.spi.engineGenerateSecret(sharedSecret, offset);
    }


    public final SecretKey generateSecret(String algorithm)
    throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException
    {
        return this.spi.engineGenerateSecret(algorithm);
    }
}






