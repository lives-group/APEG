/* $Id: CertificateFactory.java,v 1.1 2000/08/18 20:38:39 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;


/**
 * Stripped down version of the JDK 1.2 CertificateFactory. The methods that
 * are present have the same signature as Sun's. The methods that were omitted
 * depend on JDK 1.2 features.
 *
 * This implementation is just sufficient to run Systemics' WebFunds on JDK 1.1
 * and that is okay because they sponsored the implementation :-)
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class CertificateFactory {

    /** CertificateFactory delegate. */
    private final CertificateFactorySpi spi;


    protected CertificateFactory(CertificateFactorySpi certFacSpi,
                                 Provider provider, String type)
    {
        this.spi = certFacSpi;
    }


    public final Certificate generateCertificate(InputStream inStream)
        throws CertificateException
    {
        return this.spi.engineGenerateCertificate(inStream);
    }


    public static CertificateFactory getInstance(String type)
        throws CertificateException
    {
        Object[] o = Support.getImplementation("CertificateFactory", type);
        return new CertificateFactory(
                (CertificateFactorySpi)o[0], (Provider)o[1], type);
    }


    public static final CertificateFactory getInstance(String type,
                                                       String provider)
    throws CertificateException, NoSuchProviderException
    {
        Object[] o =
            Support.getImplementation("CertificateFactory", type, provider);
        return new CertificateFactory(
            (CertificateFactorySpi)o[0], (Provider)o[1], type);
    }
}
