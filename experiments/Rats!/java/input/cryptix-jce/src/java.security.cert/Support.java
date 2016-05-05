/* $Id: Support.java,v 1.1 2000/08/11 18:18:53 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;


/**
 * Helper methods for grovelling trough the installed providers and obtaining
 * (instantiating) CertificateFactorySpi objects. 
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
/*package*/ final class Support {

    /**
     * Given a Spi-type and implementation name, search the installed Providers
     * and return a tuple consisting of the instantiated Spi and a Provider 
     * instance representing the provider it came from.
     */
    /*package*/ static Object[]
    getImplementation(String type, String algorithm)
    throws CertificateException
    {
        Provider[] providers = Security.getProviders();
        if( (providers==null) || (providers.length==0) )
            throw new CertificateException("No providers installed");

        for(int i=0; i<providers.length; i++) {
            Object[] res = getImplementation(type, algorithm, providers[i]);
            if(res != null)
                return res;
        }

        throw new CertificateException(
            "Certificate type not found. [" + type + "." + algorithm + "]");
    }


    /**
     * Given a Spi-type and implementation name, search the given provider and
     * return a tuple consisting of the instantiated Spi and the Provider.
     */
    /*package*/ static Object[]
    getImplementation(String type, String algorithm, String provider)
    throws CertificateException, NoSuchProviderException
    {
        Provider p = Security.getProvider(provider);
        if(p==null)
            throw new NoSuchProviderException(
                "Provider not found. [" + provider + "]");

        Object[] res = getImplementation(type, algorithm, p);
        if(res != null)
            return res;

        throw new CertificateException(
            "Certificate type not found. [" + type + "." + algorithm + "]");
    }


    private static Object[]
    getImplementation(String algType, String algName, Provider p) {

        try {
            String class_name =
                p.getProperty("Alg.Alias." + algType + "." + algName);

            if(class_name==null)
                class_name = p.getProperty(algType + "." + algName);
            else
                class_name = p.getProperty(algType + "." + class_name);

            if(class_name == null)
                return null;

            Object[] res = new Object[2];
            res[0] = Class.forName(class_name).newInstance();
            res[1] = p;
            return res;

        } catch(LinkageError e) {
            e.printStackTrace();
            // FIXME: Throw a RuntimeException with a sensible message????

        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            // FIXME: Throw a RuntimeException with a sensible message????

        } catch(InstantiationException e) {
            e.printStackTrace();
            // FIXME: Throw a RuntimeException with a sensible message????

        } catch(IllegalAccessException e) {
            e.printStackTrace();
            // FIXME: Throw a RuntimeException with a sensible message????
        }

        return null;
    }


    private static String getClassName(String algType, String algName, 
                                       Provider p)
    {
        String class_name =
            p.getProperty("Alg.Alias." + algType + "." + algName);

        if(class_name==null)
            class_name = p.getProperty(algType + "." + algName);
        else
            class_name = p.getProperty(algType + "." + class_name);

        return class_name;
    }
}
