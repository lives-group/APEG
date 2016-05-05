/* $Id: Certificate.java,v 1.3 2000/08/18 20:39:40 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;


/**
 * @version $Revision: 1.3 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public abstract class Certificate implements Serializable {

    private final String type;


    /**
     * Cached version of our hashCode as it is quite expensive to compute.
     * This field is considered valid if it is != -1.
     */
    private transient int cachedHashCode = -1;


    protected Certificate(String type) {
        this.type = type;
    }


    /**
     * Do equality comparison based on equality of the byte[]s returned by
     * getEncoded(). Certificates are considered equal if the getEncoded() 
     * byte[]s have the same contents (and length). If either getEncoded()
     * throws an exception or returns a null ptr, we return false.
     *
     * We try and shortcut the test by comparing hashCodes first because a 
     * full comparison can be quite expensive.
     */
    public boolean equals(Object other) {

        try {
            if ( !(other instanceof Certificate) ) return false;

            Certificate that = (Certificate)other;
            if (this.hashCode() != that.hashCode()) return false;

            byte[] thisBytes = this.getEncoded();
            byte[] thatBytes = that.getEncoded();

            if (thisBytes.length != thatBytes.length) return false;

            for(int i=0; i<thisBytes.length; i++)
                if (thisBytes[i] != thatBytes[i]) return false;

            return true;

        } catch(NullPointerException e) {
            /*
             * Okay, so getEncoded() returned a null ptr. It doesn't make
             * sense but let's not crash...
             */
            return false;

        } catch(CertificateEncodingException e) {
            return false;
        }
    }


    public abstract byte[] getEncoded() throws CertificateEncodingException;


    public abstract PublicKey getPublicKey();


    public final String getType() {
        return this.type;
    }


    /**
     * Returns hashCode for this object. The hashCode calculation is quite
     * expensive (depending on the cost of a getEncoded() call and the length
     * of the encoded Certificate) but the result of the calculation is cached
     * to speed up subsequent invocations.
     */
    public int hashCode() {

        if (this.cachedHashCode != -1) return this.cachedHashCode;

        try {
            byte[] thisBytes = this.getEncoded();
            int hash = 0;
            for(int i=0; i<thisBytes.length; i++)
                hash = ((hash<<7) | (hash>>>25)) ^ (thisBytes[i]&0xFF);

            return this.cachedHashCode = hash;
        
        } catch(NullPointerException e) {
            /*
             * Okay, so getEncoded() returned a null ptr. It doesn't make
             * sense but let's not crash...
             */
            return 0;

        } catch(CertificateEncodingException e) {
            return 0;
        }
    }


    public abstract String toString();


    public abstract void verify(PublicKey key)
        throws CertificateException, NoSuchAlgorithmException,
               InvalidKeyException, NoSuchProviderException, SignatureException;


    public abstract void verify(PublicKey key, String sigProvider)
        throws CertificateException, NoSuchAlgorithmException,
               InvalidKeyException, NoSuchProviderException, SignatureException;


    protected Object writeReplace() throws ObjectStreamException {
        throw new RuntimeException("NYI");
    }
}
