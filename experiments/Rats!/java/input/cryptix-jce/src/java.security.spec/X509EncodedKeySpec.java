/* $Id: X509EncodedKeySpec.java,v 1.1 2000/07/26 21:52:25 gelderen Exp $
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
 * KeySpec for a public key encoded as per X.509.
 *
 * <p>Immutable.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class X509EncodedKeySpec extends EncodedKeySpec {

    public X509EncodedKeySpec(byte[] encodedKey) {
        super(encodedKey);
    }


    public String getFormat() {
        return "X.509";
    }
}
