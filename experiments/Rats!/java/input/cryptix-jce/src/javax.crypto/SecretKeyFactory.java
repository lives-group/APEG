/* $Id: SecretKeyFactory.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class SecretKeyFactory {

    private final SecretKeyFactorySpi spi;
    private final Provider            provider;
    private final String              algorithm;


    protected
    SecretKeyFactory(SecretKeyFactorySpi keyFacSpi, Provider provider,
                     String algorithm)
    {
        this.spi       = keyFacSpi;
        this.provider  = provider;
        this.algorithm = algorithm;
    }


    public static final SecretKeyFactory getInstance(String algorithm)
    throws NoSuchAlgorithmException {

        Object[] o = Support.getImplementation("SecretKeyFactory", algorithm);

        return new SecretKeyFactory((SecretKeyFactorySpi)o[0],
                                    (Provider)o[1],
                                    algorithm);
    }


    public static final SecretKeyFactory
    getInstance(java.lang.String algorithm, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException {

        Object[] o = Support.getImplementation("SecretKeyFactory",
                                               algorithm,
                                               provider);

        return new SecretKeyFactory((SecretKeyFactorySpi)o[0],
                                    (Provider)o[1],
                                    algorithm);
    }


    public final Provider getProvider() {
        return this.provider;
    }


    public final String getAlgorithm() {
        return this.algorithm;
    }


    public final SecretKey generateSecret(KeySpec keySpec)
    throws InvalidKeySpecException {
        return this.spi.engineGenerateSecret(keySpec);
    }


    public final KeySpec getKeySpec(SecretKey key, Class keySpec)
    throws InvalidKeySpecException {
        return this.spi.engineGetKeySpec(key, keySpec);
    }


    public final SecretKey translateKey(SecretKey key)
    throws InvalidKeyException {
        return this.spi.engineTranslateKey(key);
    }


}
