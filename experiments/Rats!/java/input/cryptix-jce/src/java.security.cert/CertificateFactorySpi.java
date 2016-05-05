/* $Id: CertificateFactorySpi.java,v 1.1 2000/08/18 20:38:39 gelderen Exp $
 *
 * Copyright (c) 2000 The Cryptix Foundation Limited. All rights reserved.
 */

package java.security.cert;


import java.io.InputStream;
import java.util.Collection;


public abstract class CertificateFactorySpi {

    public CertificateFactorySpi() {
    }


    public abstract Certificate engineGenerateCertificate(InputStream inStream)
        throws CertificateException;

        
    public abstract Collection engineGenerateCertificates(InputStream inStream)
        throws CertificateException;


    public abstract CRL engineGenerateCRL(InputStream inStream)
        throws CRLException;


    public abstract Collection engineGenerateCRLs(InputStream inStream)
        throws CRLException;
}
