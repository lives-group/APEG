/* $Id: RSASignature_PKCS1_SHA384.java,v 1.2 2001/11/18 02:35:22 gelderen Exp $
 *
 * Copyright (C) 2001 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.pk;


/**
 * A class to digest a message with SHA384, and sign/verify the
 * resulting hash using the RSA digital signature scheme, with PKCS#1
 * block padding.
 *
 * @version $Revision: 1.2 $
 * @author  Jeroen C. van Gelderen
 */
public class RSASignature_PKCS1_SHA384 extends RSASignature_PKCS1 {

// Constants and variables
//...........................................................................

    private static final byte[] SHA384_ASN_DATA = {
        /* Taken from PKCS#1 1v2-1d1. */
        0x30, 0x41,                                    // SEQUENCE 65
          0x30, 0x0d,                                    // SEQUENCE 13
            0x06, 0x09, 0x60, (byte)0x86, 0x48, 0x01,      // OID
                        0x65, 0x03, 0x04, 0x02, 0x02,
            0x05, 0x00,                                    // NULL
          0x04, 0x30                                     // OCTET STRING 48
    };


// Constructor
//...........................................................................

    public RSASignature_PKCS1_SHA384() { super("SHA-384"); }


// RSASignature_PKCS1 abstract method implementation
//...........................................................................

    protected byte[] getAlgorithmEncoding() { return SHA384_ASN_DATA; }
}
