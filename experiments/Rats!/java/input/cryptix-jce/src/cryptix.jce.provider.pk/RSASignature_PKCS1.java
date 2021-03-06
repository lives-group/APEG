/* $Id: RSASignature_PKCS1.java,v 1.6 2001/11/18 02:35:22 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.pk;


import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureSpi;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.AlgorithmParameterSpec;

import cryptix.jce.provider.util.Util;


/**
 * An abstract class to digest a message and sign/verify the resulting
 * hash value, using any JCA MessageDigest algorithm with the RSA
 * digital signature scheme, and the formatting and padding conventions
 * defined by PKCS#1. These conventions are compatible with PEM (RFC-1423).
 * <p>
 * <b>References:</b>
 * <ol>
 *   <li> <a href="mailto:schneier@counterpane.com">Bruce Schneier</a>,
 *        "Section 19.3 RSA,"
 *        <cite>Applied Cryptography, 2nd edition</cite>,
 *        John Wiley &amp; Sons, 1996.
 *        </li>
 *   <li> PKCS#1 
 *        </li>
 *   <li> RFC 1423</li>
 * </ol>
 *
 * @author $Revision: 1.6 $
 * @author Raif S. Naffah
 * @author David Hopwood
 * @author Jeroen C. van Gelderen
 * @since  Cryptix 2.2.2
 */
public abstract class RSASignature_PKCS1
extends SignatureSpi
{

// Constants and variables
//...........................................................................

    private static final BigInteger 
        ZERO = BigInteger.valueOf(0L),
        ONE  = BigInteger.valueOf(1L);

    private BigInteger 
        n,
        exp, // e if state is ENCRYPT, d if DECRYPT.
        p,   // null if the factors of n are unknown.
        q,
        u;

    private final MessageDigest md;


// Constructor
//...........................................................................

    /**
     * Constructor for an Any_RSA_PKCS1Signature.
     *
     * @param  mdAlgorithm  the standard JCA algorithm name of the message
     *                      digest to be used.
     */
    RSASignature_PKCS1(String mdAlgorithm)
    {
        super();
        try 
        {
            this.md = MessageDigest.getInstance(mdAlgorithm);
        } 
        catch(Exception e) 
        {
            // Panic as MDs are delivered in the same provider
            throw new InternalError(
                "Unable to instantiate messagedigest:" + mdAlgorithm);
        }
    }


// Signature abstract methods implementation
//...........................................................................

    /**
     * Initializes this signature object for verification, using
     * the given public key.
     *
     * @param key
     *      the public key this signature is assumed to have been
     *      generated with.
     * @exception InvalidKeyException
     *      If the key class does not implement
     *      java.security.interfaces.RSAPrivateKey or if the size of the
     *      minimal PKCS#1 frame generated by the engineSign() method will
     *      be larger than the public key modulus.
     */
    protected void engineInitVerify(PublicKey key)
    throws InvalidKeyException
    {
        if( !(key instanceof RSAPublicKey) )
            throw new InvalidKeyException("Not an RSA public key");

        RSAPublicKey rsa = (RSAPublicKey) key;
        this.n   = rsa.getModulus();
        this.exp = rsa.getPublicExponent();
        this.p = this.q = this.u = null;

        initCommon();
    }


    /**
     * Initializes this signature object for signing, using the
     * given private key.
     *
     * @param  key  the private key to be used to generate signatures.
     * @exception InvalidKeyException
     *      If the key class does not implement
     *      java.security.interfaces.RSAPrivateKey or
     *      If the size of the minimal PKCS#1 frame generated by the
     *      engineSign() method will be larger than the public key modulus.
     */
    protected void engineInitSign(PrivateKey key)
    throws InvalidKeyException
    {
        if( !(key instanceof RSAPrivateKey) )
            throw new InvalidKeyException("Not an RSA private key");

        RSAPrivateKey rsa = (RSAPrivateKey) key;
        n   = rsa.getModulus();
        exp = rsa.getPrivateExponent();

        if(key instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey crt = (RSAPrivateCrtKey)key;
            p = crt.getPrimeP();
            q = crt.getPrimeQ();
            u = crt.getCrtCoefficient();
        } else {
            this.p = this.q = this.u = null;
        }

        initCommon();
    }


    private void initCommon() throws InvalidKeyException {
        md.reset();

        // result will have as many bytes as the public modulus n
        int mdl = md.digest().length;
        int length = modulusByteLength();
        int aidl = getAlgorithmEncoding().length;
        int padLen = length - 3 - aidl - mdl;
        if (padLen < 0) throw new
            InvalidKeyException("Signer's key modulus too short.");
    }


    protected void engineInitSign(PrivateKey privateKey, SecureRandom random)
    throws InvalidKeyException
    {
        // silently ignore 'random'
        engineInitSign(privateKey);
    }


    /**
     * Updates the data to be signed or verified, using one byte.
     *
     * @param  b  the byte to use for the update process.
     * @exception SignatureException if the engine is not initialised properly.
     */
    protected void engineUpdate (byte b)
    throws SignatureException
    {
        md.update(b);
    }

    /**
     * Updates the data to be signed or verified, using the specified
     * sub-array of bytes, starting at the specified offset.
     *
     * @param  in      the array of bytes.
     * @param  offset  the offset to start from in <i>in</i>.
     * @param  length  the number of bytes to use, starting at <i>offset</i>.
     * @exception SignatureException if the engine is not initialised properly.
     */
    protected void engineUpdate (byte[] in, int offset, int length)
    throws SignatureException 
    {
        md.update(in, offset, length);
    }

    /**
     * Terminates the update process and returns the signature bytes of
     * all the data signed so far.
     * <p>
     * <b>NOTES:</b> Sun's documentation talks about the bytes returned
     * being X.509-encoded. For this RSA/PKCS#1 implementation, they
     * conform to PKCS#1 section 10. Practically, the return value will
     * be formed by concatenating a leading <i>NULL</i> byte, a block type
     * <i>BT</i>, a padding block <i>PS</i>, another <i>NULL</i>byte, and
     * finally a data block <i>D</i>;
     * ie:
     * <pre>
     *     return = 0x00 || BT || PS || 0x00 || D.
     * </pre>
     * For signing, <i>PKCS#1 block type 01</i> encryption-block formatting
     * scheme is employed. The block type <i>BT</i> is a single byte valued
     * 0x01 and the padding block <i>PS</i> is enough 0xFF bytes to make the
     * length of the complete RSA Multi Precision Integer equal to the length
     * of the public modulus. The data block <i>D</i> consists of the MIC --
     * Message Integrity Check, or message digest value-- and the MIC
     * algorithm ASN.1 encoded identifier. The formal syntax in ASN.1
     * notation is:
     * <pre>
     *   SEQUENCE {
     *     digestAlgorithm  AlgorithmIdentifier,
     *     digest           OCTET STRING
     *   }
     *
     *   AlgorithmIdentifier ::= SEQUENCE {
     *     algorithm        OBJECT IDENTIFIER,
     *     parameters       ANY DEFINED BY algorithm OPTIONAL
     *   }
     * </pre>
     *
     * @return  the signature bytes of the signing operation's result.
     * @exception  SignatureException
     *     if the engine is not initialised properly.
     */
    protected byte[] engineSign()
    throws SignatureException
    {
        BigInteger pkcs   = makePKCS1();
        BigInteger result = RSAAlgorithm.rsa(pkcs, n, exp, p, q, u);

        /*
         * Return a fixed length result without sign bit.
         */
        return Util.toFixedLenByteArray(result, modulusByteLength());
    }


    /**
     * Terminates the update process and verifies that the passed signature
     * equals that of a generated one based on the updated data so far.
     * <p>
     * <b>NOTES:</b> Sun's documentation talks about the bytes received
     * being X.509-encoded. For this RSA/PKCS#1 implementation, the bytes
     * received are assumed to conform to PKCS#1 section 10, or have
     * been generated by a previous invocation of the <code>engineSign</code>
     * method.
     *
     * @param  signature    the signature bytes to be verified.
     * @return true if the signature was verified successfully, false
     *                      otherwise.
     * @exception SignatureException if the engine is not initialised
     *                      properly, the received signature data is improperly
     *                      encoded or of the wrong type, etc.
     */
    protected boolean engineVerify(byte[] signature)
    throws SignatureException 
    {
        /* 
         * signature lacks a sign bit so we must force it positive...
         */
        BigInteger M = new BigInteger(1, signature);

        BigInteger computed = RSAAlgorithm.rsa(M, n, exp, p, q, u);
        BigInteger actual = makePKCS1();
        return computed.equals(actual);
    }

    protected void engineSetParameter(String param, Object value)
    throws InvalidParameterException 
    {
        throw new InvalidParameterException(
            "This algorithm does not accept parameters.");
    }


    protected void engineSetParameter(AlgorithmParameterSpec params)
    throws InvalidAlgorithmParameterException
    {
        throw new InvalidAlgorithmParameterException(
            "This algorithm does not accept AlgorithmParameterSpec.");
    }


    protected Object engineGetParameter(String param)
    throws InvalidParameterException 
    {
        throw new InvalidParameterException(
            "This algorithm does not have parameters.");
    }


// Own methods
//...........................................................................

    /**
     * Returns a byte array consisting of a padded message digest value,
     * previously computed. This packet will be RSA-encrypted with the
     * private key of this object to act as an authentication for whatever
     * was digested.
     * <p>
     * As described in the <i>engineSign()</i> method above, the return
     * array will consist of:
     * <pre>
     *    MSB                                                            LSB
     *    00  01   FF-1 ... FF-n  00   AID-1 ... AID-n   04 LL MD-1 ... MD-n
     *      | BT |----- PS -----|    |-- AlgorithmId --|------ digest ------|
     * </pre>
     * <p>
     * The <i>AID<i> bytes form the <i>AlgorithmIdentifier</i> token.
     * The OCTET STRING tag is <i>04</i> and <i>LL</i> is the length byte
     * (the number of bytes in the message digest proper, i.e. <i>n</i>).
     * <p>
     * Bytes <i>MD-1</i> to <i>MD-n</i> are the message digest value
     * of the material updated so far, thus completing the <i>digest</i>
     * token in the SEQUENCE described in <i>engineSign()</i> above.
     *
     * @return the result of the updating process framed in a PKCS#1
     *         type 01 block structure as a BigInteger.
     * @exception SignatureException If the length of the minimal PKCS#1 frame
     *      generated by this method will be longer than the public key modulus.
     */
    private BigInteger makePKCS1() throws SignatureException 
    {
        byte[] theMD = md.digest();                           // stop hashing
        int mdl = theMD.length;

        // result has as many bytes as the public modulus
        int length = modulusByteLength();
        byte[] r = new byte[length];

        r[1] = 0x01;                          // PKCS#1 encryption block type

        byte[] aid = getAlgorithmEncoding();             // get the AID bytes
        int aidl = aid.length;

        int padLen = length - 3 - aidl - mdl;            // put padding bytes
        if(padLen < 0)
            throw new SignatureException(
                "Signer's public key modulus too short.");

        for(int i = 0; i < padLen; ) 
            r[2 + i++] = (byte) 0xFF;

        System.arraycopy(aid, 0, r, padLen + 3, aidl);   // copy the AID bytes
        System.arraycopy(theMD, 0, r, length - mdl, mdl); // now the md per se

        return new BigInteger(r);        // always positive because r[0] is 0
    }


    /**
     * Return the length (in bytes) of our modulus.
     */
    private int modulusByteLength() {
        return (this.n.bitLength() + 7) / 8;
    }


// Abstract method
//...........................................................................

    /**
     * Returns the ASN.1 bytes of the <i>AlgorithmIdentifier</i> token 
     * described in <code>engineSign()</code> method above.
     *
     * @return the <i>AlgorithmIdentifier</i> bytes.
     */
    protected abstract byte[] getAlgorithmEncoding();
}
