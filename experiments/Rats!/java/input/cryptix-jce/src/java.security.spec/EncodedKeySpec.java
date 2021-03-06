/* $Id: EncodedKeySpec.java,v 1.1 2000/07/26 21:52:25 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.spec;


/**
 * Base class for 'encoded' KeySpecs such as PKCS#x and ASN.1 DER.
 *
 * <p>Immutable.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public abstract class EncodedKeySpec implements KeySpec {

    private byte[] encodedKey;


    public EncodedKeySpec(byte[] encodedKey) {
        this.encodedKey = (byte[])encodedKey.clone();
    }


    public byte[] getEncoded() {
        return (byte[])this.encodedKey.clone();
    }


    public abstract String getFormat();
}
