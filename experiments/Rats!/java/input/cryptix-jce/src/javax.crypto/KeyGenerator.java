/* $Id: KeyGenerator.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


public class KeyGenerator
{
    private final KeyGeneratorSpi spi;
    private final Provider        provider;
    private final String          algorithm;


    protected KeyGenerator(KeyGeneratorSpi keyGenSpi, Provider provider,
                           String algorithm)
    {
        this.spi       = keyGenSpi;
        this.provider  = provider;
        this.algorithm = algorithm;
    }


    public final String getAlgorithm() {
        return this.algorithm;
    }


    public static final KeyGenerator
    getInstance(String algorithm)
    throws NoSuchAlgorithmException
    {
        Object[] o =
            Support.getImplementation("KeyGenerator", algorithm);
        return
            new KeyGenerator((KeyGeneratorSpi)o[0], (Provider)o[1], algorithm);
    }


    public static final KeyGenerator
    getInstance(String algorithm, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException
    {
        Object[] o =
            Support.getImplementation("KeyGenerator", algorithm, provider);
        return
            new KeyGenerator((KeyGeneratorSpi)o[0], (Provider)o[1], algorithm);
    }


    public final Provider getProvider() {
        return this.provider;
    }


    public final void init(SecureRandom random) {
        this.spi.engineInit(random);
    }


    public final void init(AlgorithmParameterSpec params)
    throws InvalidAlgorithmParameterException {
        this.spi.engineInit(params, new SecureRandom());
    }


    public final void init(AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidAlgorithmParameterException {
        this.spi.engineInit(params, random);
    }


    public final void init(int strength) {
        this.spi.engineInit(strength, new SecureRandom());
    }


    public final void init(int strength, SecureRandom random) {
        this.spi.engineInit(strength, random);
    }


    public final SecretKey generateKey() {
        return this.spi.engineGenerateKey();
    }
}    