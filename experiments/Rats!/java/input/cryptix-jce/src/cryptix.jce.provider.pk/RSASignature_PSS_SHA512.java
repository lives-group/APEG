/* $Id: RSASignature_PSS_SHA512.java,v 1.2 2001/11/18 02:31:37 gelderen Exp $
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
 * @version $Revision: 1.2 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class RSASignature_PSS_SHA512 extends RSASignature_PSS {
    public RSASignature_PSS_SHA512 () { super("SHA-512"); }
}
