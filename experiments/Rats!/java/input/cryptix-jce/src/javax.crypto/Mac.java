/* $Id: Mac.java,v 1.5 2000/01/15 03:25:04 gelderen Exp $
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
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;


/**
 * A MAC (Message Authentication Code).
 *
 * @author Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @version $Revision: 1.5 $
 */
public class Mac
{
    private static final int
        STATE_UNDEFINED   = 0,
        STATE_INITIALIZED = 1;

    private       int      state;
    private final MacSpi   spi;
    private final Provider provider;
    private final String   algorithm;


    protected Mac(MacSpi macSpi, Provider provider, String algorithm) {
        this(macSpi, provider, algorithm, STATE_UNDEFINED);
    }


    /**
     * Construct a Mac with a given initial state.
     *
     * This constructor comes in handy when cloning.
     */
    private Mac(MacSpi macSpi, Provider provider, String algorithm, int state) {
        this.spi       = macSpi;
        this.provider  = provider;
        this.algorithm = algorithm;
        this.state     = state;
    }


    public final String getAlgorithm() {
        return this.algorithm;
    }


    public static final Mac getInstance(String algorithm)
    throws NoSuchAlgorithmException {

        Object[] o = Support.getImplementation("Mac", algorithm);
        return new Mac((MacSpi)o[0], (Provider)o[1], algorithm);
    }


    public static final Mac getInstance(String algorithm, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException {

        Object[] o = Support.getImplementation("Mac", algorithm, provider);
        return new Mac((MacSpi)o[0], (Provider)o[1], algorithm);
    }


    public final Provider getProvider() {
        return this.provider;
    }


    public final int getMacLength() {
        return this.spi.engineGetMacLength();
    }


    public final void init(Key key)
    throws InvalidKeyException {
        try {
            this.spi.engineInit(key, null);
        }
        catch(InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(
                "This Mac requires an AlgorithmParameterSpec");
        }
        this.state = STATE_INITIALIZED;
    }


    public final void init(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.spi.engineInit(key, params);
        this.state = STATE_INITIALIZED;
    }


    public final void update(byte input)
    throws IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        this.spi.engineUpdate(input);
    }


    public final void update(byte[] input)
    throws IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        this.spi.engineUpdate(input, 0, input.length);
    }


    public final void update(byte[] input, int offset, int len)
    throws IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        this.spi.engineUpdate(input, offset, len);
    }


    public final byte[] doFinal()
    throws IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        byte[] res = this.spi.engineDoFinal();
        reset();
        return res;
    }


    public final void doFinal(byte[] output, int outOffset)
    throws ShortBufferException, IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        int my_len = getMacLength();

        if( (output.length-outOffset) < my_len )
            throw new ShortBufferException("Buffer too short");

        byte[] res = this.spi.engineDoFinal();
        System.arraycopy(res, 0, output, outOffset, my_len);

        reset();
    }


    public final byte[] doFinal(byte[] input)
    throws IllegalStateException {

        if(this.state != STATE_INITIALIZED)
            throw new IllegalStateException();

        update(input);
        return doFinal();
    }


    public final void reset() {
        this.spi.engineReset();
    }


    public final Object clone()
    throws CloneNotSupportedException {

        MacSpi clone_spi = (MacSpi)this.spi.clone();
        return new Mac(clone_spi, provider, algorithm, state);
    }
}