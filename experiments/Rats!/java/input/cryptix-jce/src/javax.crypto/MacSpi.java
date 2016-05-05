/* $Id: MacSpi.java,v 1.4 2000/01/15 03:25:04 gelderen Exp $
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
import java.security.spec.AlgorithmParameterSpec;


public abstract class MacSpi {

    protected abstract int engineGetMacLength();


    protected abstract void engineInit(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException;


    protected abstract void engineUpdate(byte input);


    protected abstract void engineUpdate(byte[] input, int offset, int len);


    protected abstract byte[] engineDoFinal();


    protected abstract void engineReset();


    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
