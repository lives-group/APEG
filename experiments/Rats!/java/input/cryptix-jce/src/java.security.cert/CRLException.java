/* $Id: CRLException.java,v 1.1 2000/08/07 17:54:53 edwin Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


import java.security.GeneralSecurityException;


/**
 * Base class of CRL related exceptions.
 *
 * @version $Revision: 1.1 $
 * @author  Edwin Woudt (edwin@cryptix.org)
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class CRLException extends GeneralSecurityException {

    public CRLException() {
        super();
    }


    public CRLException(String msg) {
        super(msg);
    }
}
