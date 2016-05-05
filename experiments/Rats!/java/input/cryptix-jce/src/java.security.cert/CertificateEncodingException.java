/* $Id: CertificateEncodingException.java,v 1.1 2000/07/20 23:21:19 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


/**
 * Thrown during certificate encoding.
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class CertificateEncodingException extends CertificateException {

    public CertificateEncodingException() {
        super();
    }


    public CertificateEncodingException(String msg) {
        super(msg);
    }
}
