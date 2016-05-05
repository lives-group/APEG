/* $Id: FileHashing.java,v 1.6 2000/01/20 14:59:23 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.examples;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import sun.misc.BASE64Encoder;

/**
 * This class produces a message digest of a file and writes it into a file
 * with the extension >>.messagedigest<<.
 *
 * @version $Revision: 1.6 $
 * @author  Josef Hartmann (jhartmann@bigfoot.com)
 */
public class FileHashing
{
    /** Variable containing the MD object. */
    private MessageDigest md=null;
    /** member holding raw digest output. */
    private byte[] hash_raw=null;


    /**
     * Running the specified message digest algorithm of a provider on a file.
     *
     * @param hashfunction String Name of the hashfunction<P>
     *                            which should be used.
     * @param provider String Name of the provider that should be used.
     * @param filename String Name of the file which has to be hashed.
     * @return true or false.
     */
    public boolean run(String hashfunction, String provider, String filename)
    {
        boolean ret = false;
        try {
            // Obtain a message digest object of provider "provider" using
            // algorithm "hashfunction".
            md = MessageDigest.getInstance(hashfunction, provider);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            return false;
        }
        catch(NoSuchProviderException nspe){
            nspe.printStackTrace();
            return false;
        }

        FileInputStream fis = null;

        try {
            // Get FileInputStream for reading a few bytes from a file.
            fis = new FileInputStream(filename);
            byte[] buffer = new byte [8192];
            int length=0;
            // Read bytes into buffer and feed these bytes into the message
            // digest object.
            while ((length = fis.read(buffer)) != -1) {
            md.update(buffer,0,length);
            }
            // Run the algorithm, so it produces the message digest.
            hash_raw = md.digest();
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        // Unfortunately the result of the digest() method produces non
        // readable output. Therefore we are using base64 encoder to move
        // these bytes into a readable format.
        BASE64Encoder enc = new BASE64Encoder();
        String base64 = enc.encode(hash_raw);
        System.out.println(base64);
        try {

            FileOutputStream fout = new FileOutputStream(
                                            filename+"."+hashfunction);
            DataOutputStream dout = new DataOutputStream(fout);

            // Write hash (base64) into a file.
            dout.writeBytes(base64);
            dout.close();
            ret = true;
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        return ret;
    }
}
