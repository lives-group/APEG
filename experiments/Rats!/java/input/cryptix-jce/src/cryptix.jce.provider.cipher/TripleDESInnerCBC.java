/* $Id: TripleDESInnerCBC.java,v 1.2 2003/03/24 02:05:27 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.cipher;


import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
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
import javax.crypto.spec.IvParameterSpec;

import cryptix.jce.provider.key.RawSecretKey;


/**
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @version $Revision: 1.2 $
 */
public final class TripleDESInnerCBC extends CipherSpi
{
    private static final String PROVIDER = "CryptixCrypto";

    private static final int BLOCK_SIZE = 64;

    // if you change this, fix the getOutputSize function implementation!
    private static final String ALG_NAME = "DES/CBC/None";

    private static final int KEY_SIZE = 24;

    private final Cipher _c1, _c2, _c3;

    private static final int IV_SIZE = 24;

    private byte[] _ivBuf;
    

    public TripleDESInnerCBC()
    {
        try
        {
            _c1 = Cipher.getInstance(ALG_NAME, PROVIDER);
            _c2 = Cipher.getInstance(ALG_NAME, PROVIDER);
            _c3 = Cipher.getInstance(ALG_NAME, PROVIDER);
        }
        catch(NoSuchAlgorithmException ex1)
        {
            throw new InternalError("Unreachable code reached.");
        }
        catch(NoSuchPaddingException ex2)
        {
            throw new InternalError("Unreachable code reached.");
        }
        catch(NoSuchProviderException ex3)
        {
            throw new InternalError("Unreachable code reached.");
        }
    }


    /**
     * Always throws a CloneNotSupportedException (cloning of ciphers is not
     * supported for security reasons).
     */
    public final Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }


// CipherSPI implementation
//............................................................................

    protected final void engineSetMode(String mode)
    throws NoSuchAlgorithmException
    {
        if(!mode.equals("InnerCBC"))
            throw new NoSuchAlgorithmException(mode);
    }


    protected final void engineSetPadding(String padding)
    throws NoSuchPaddingException
    {
        if(!padding.equals("None"))
            throw new NoSuchPaddingException(padding);
    }


    protected final int engineGetBlockSize()
    {
        return BLOCK_SIZE;
    }


    protected int engineGetKeySize(Key key)
    throws InvalidKeyException
    {
        if( key==null )
            throw new IllegalArgumentException("Key missing");

        if( !key.getFormat().equalsIgnoreCase("RAW") )
            throw new InvalidKeyException("Wrong format: RAW bytes needed");

        byte[] userkey = key.getEncoded();
        if(userkey == null)
            throw new InvalidKeyException("RAW bytes missing");

        return (userkey.length * 8);
    }


    /**
     * Returns the length in bytes that an output buffer would need to be in
     * order to hold the result of the next update or doFinal operation, given
     * the input length <code>inputLen</code> (in bytes).
     *
     * This call takes into account any unprocessed (buffered) data from a
     * previous update call(s), and padding.
     *
     * The actual output length of the next <code>update or doFinal</code> call
     * may be smaller than the length returned by this method. For ciphers with
     * a padding, calling the update method will generally return less data
     * than predicted by this function.
     *
     * @param  inputLen the length in bytes.
     *
     * @return the maximum amount of data that the cipher will return.
     */
    protected final int engineGetOutputSize(int len)
    {
        // result is exact because the three ciphers don't use padding
        return _c1.getOutputSize(_c2.getOutputSize(_c3.getOutputSize(len)));
    }


    /**
     * Returns a copy of the initialization vector (IV) used in this cipher.
     *
     * @return A copy of the IV  or null if this cipher does not have an IV or
     *         null if the IV has not yet been set.
     */
    protected final byte[] engineGetIV()
    {
        return (byte[])_ivBuf.clone();
    }


    protected final AlgorithmParameters engineGetParameters()
    {
        return null;
    }


    protected final void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException
    {
        try
        {
            engineInit(opmode, key, (AlgorithmParameterSpec)null, random);
        }
        catch(InvalidAlgorithmParameterException ex)
        {
            throw new InternalError("Unreachable code reached.");
        }
    }


    protected final void
    engineInit(int opmode, Key key, AlgorithmParameterSpec params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        if(opmode != Cipher.ENCRYPT_MODE && opmode != Cipher.DECRYPT_MODE)
            throw new IllegalArgumentException("opmode invalid ("+opmode+")");

        if(key == null)
            throw new IllegalArgumentException("key invalid ("+key+")");
        byte[] keyBuf = key.getEncoded();
        if(keyBuf == null)
            throw new InvalidKeyException("key.getEncoded() == null");
        if(keyBuf.length != KEY_SIZE)
            throw new InvalidKeyException("Invalid key length");

        Key k1 = new RawSecretKey("DES", keyBuf,  0, 8);
        Key k2 = new RawSecretKey("DES", keyBuf,  8, 8);
        Key k3 = new RawSecretKey("DES", keyBuf, 16, 8);

        if(params == null) {
            _ivBuf = new byte[IV_SIZE];
            random.nextBytes(_ivBuf);
        } else if(params instanceof IvParameterSpec) {
            _ivBuf = ((IvParameterSpec)params).getIV();
            if(_ivBuf.length != IV_SIZE)
                throw new InvalidAlgorithmParameterException("IV too short");
        } else {
            throw new InvalidAlgorithmParameterException("params = "+params);
        }

        IvParameterSpec iv1 = new IvParameterSpec(_ivBuf,  0, 8);
        IvParameterSpec iv2 = new IvParameterSpec(_ivBuf,  8, 8);
        IvParameterSpec iv3 = new IvParameterSpec(_ivBuf, 16, 8);

        if(opmode == Cipher.ENCRYPT_MODE) {
            _c1.init(Cipher.ENCRYPT_MODE, k1, iv1, random);
            _c2.init(Cipher.DECRYPT_MODE, k2, iv2, random);
            _c3.init(Cipher.ENCRYPT_MODE, k3, iv3, random);
        } else {
            _c1.init(Cipher.DECRYPT_MODE, k3, iv3, random);
            _c2.init(Cipher.ENCRYPT_MODE, k2, iv2, random);
            _c3.init(Cipher.DECRYPT_MODE, k1, iv1, random);
        }
    }


    protected final void
    engineInit(int opmode, Key key, AlgorithmParameters params,
               SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        if(params != null)
            throw new InvalidAlgorithmParameterException();

        engineInit(opmode, key, (AlgorithmParameterSpec)null, random);
    }


    protected final int
    engineUpdate(byte[] in, int inOff, int inLen, byte[] out, int outOff)
    throws ShortBufferException
    {
        int outLen;
        outLen = _c1.update(in,  inOff,  inLen,  out, outOff);
        outLen = _c2.update(out, outOff, outLen, out, outOff);
        outLen = _c3.update(out, outOff, outLen, out, outOff);
        return outLen;
    }


    protected final byte[] engineUpdate(byte[] in, int inOff, int inLen)
    {
        byte[] buf;
        buf = _c1.update(in, inOff,  inLen);
        buf = _c2.update(buf, 0, buf.length);
        buf = _c3.update(buf, 0, buf.length);
        return buf;
    }


    /**
     * @throws BadPaddingException
     *         (decryption only) if padding is expected but not found at the
     *         end of the data or the padding is found but corrupt
     * @throws IllegalBlockSizeException
     *         if no padding is specified and the input data is not a multiple
     *         of the blocksize.
     * @throws ShortBufferException if the given buffer is to short to hold
     *         the result.
     */
    protected final int
    engineDoFinal(byte[] in, int inOff, int inLen, byte[] out, int outOff)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
    {
        int outLen;
        outLen = _c1.doFinal(in,  inOff,  inLen,  out, outOff);
        outLen = _c2.doFinal(out, outOff, outLen, out, outOff);
        outLen = _c3.doFinal(out, outOff, outLen, out, outOff);            
        return outLen;
    }


    /**
     * Implemented in terms of engineDoFinal(byte[], int, int, byte[], int)
     *
     * @throws BadPaddingException
     *         (decryption only) if padding is expected but not found at the
     *         end of the data.
     * @throws IllegalBlockSizeException
     *         if no padding is specified and the input data is not a multiple
     *         of the blocksize.
     */
    protected final byte[]
    engineDoFinal(byte[] in, int inOff, int inLen)
    throws IllegalBlockSizeException, BadPaddingException
    {
        byte[] buf;
        buf = _c1.doFinal(in, inOff,  inLen);
        buf = _c2.doFinal(buf, 0, buf.length);
        buf = _c3.doFinal(buf, 0, buf.length);
        return buf;
    }
}
