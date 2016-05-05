/* $Id: MACs.java,v 1.2 2000/01/20 14:59:23 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.examples;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;


/**
 * This class shows using macs.
 *
 * @author: Josef Hartmann (jhartmann@bigfoot.com)
 * @version: $Revision: 1.2 $
 */
public final class MACs 
{
    /** MAC */
    private javax.crypto.Mac mac = null;
    
    /** key used for the mac. */
    private Key key = null;
    
    /**
     * Run the mac process of provider on the given file.
     *
     * @return boolean
     * @param algorithm java.lang.String Signature algorithm.
     * @param provider java.lang.String provider name.
     * @param filename java.lang.String file to create signature.
     */
    public boolean run(String algorithm, String provider, String filename)
    {

        try
        {
            // Get the KeyGenerator
            // FIXME: How do other providers provide a keygen for HMACs?
            //
            KeyGenerator kg = KeyGenerator.getInstance("HMAC",provider);
            // Init the KeyGenerator using some random values.
            kg.init(new SecureRandom());
            // Create a key.
            key = kg.generateKey();
            // Get the HMAC algorithm of provider.
            mac = Mac.getInstance(algorithm,provider);
            // Initialize the algorithm.
            mac.init(key);
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
            return false;
        }
        catch (NoSuchProviderException nspe)
        {
            nspe.printStackTrace();
            return false;
        }
        catch (InvalidKeyException ike)
        {
            ike.printStackTrace();
            return false;
        }

        try
        {
            // Read file to encrypt.
            FileInputStream fInput = new FileInputStream(filename);
            // Set output file.
            FileOutputStream fOutput =
                             new FileOutputStream(filename+"."+algorithm);

            byte[] buffer = new byte[8192];
            int length=0;
            // Read input bytes into buffer and update the HMAC engine.
            while ((length=fInput.read(buffer))!= -1)
            {
                mac.update(buffer);
            }

            // Run the algorithm and write output to file.
            fOutput.write(mac.doFinal());

            // Close Streams.
            fInput.close();
            fOutput.close();
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
            return false;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }

        // VERIFY:
        // You have to do it on your own.
        // Just use the same key and compute the hmac again.
        // Then compare the values.

        return true;
    }
}