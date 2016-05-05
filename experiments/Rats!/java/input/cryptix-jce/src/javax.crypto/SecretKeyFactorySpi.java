/* $Id: SecretKeyFactorySpi.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public abstract class SecretKeyFactorySpi {

    public SecretKeyFactorySpi() {
    }


    protected abstract SecretKey
    engineGenerateSecret(KeySpec keySpec)
    throws InvalidKeySpecException;


    protected abstract KeySpec
    engineGetKeySpec(SecretKey key, Class keySpec)
    throws InvalidKeySpecException;


    protected abstract SecretKey
    engineTranslateKey(SecretKey key)
    throws InvalidKeyException;
}
