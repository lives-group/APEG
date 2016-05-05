/* $Id: RunSamples.java,v 1.9 2001/07/10 18:25:45 gelderen Exp $
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


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import cryptix.jce.provider.CryptixCrypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Base class for running implemented samples.
 * Is there any provider independent way of getting all ciphers, mds? FIXME!
 *
 * @version: $Revision: 1.9 $
 * @author: Josef Hartmann (jhartmann@bigfoot.com)
 */
public final class RunSamples 
{
    /**
     * Add provider cryptix dynamicly. Due to the fact that each provider has 
     * its own location for the provider class I do not know how to create 
     * different provider instances dynamicly. E.g. one function for raising 
     * ABA, Cryptix, Forge or Entrust provider. But the few providers can be 
     * hardcoded, I think.
     */
    private static boolean addProviderCryptix() 
    {
        // First add the provider (in this case cryptix) dynamicly to the 
        // provider list. If this should be staticly edit the java.security 
        // file and add the line:
        //    security.provider.X=cryptix.jce.provider.Cryptix
        // where X specifies the desired priority of the provider in the 
        // provider list.

        // create a new Cryptix provider.
        Provider cryptix_provider = new CryptixCrypto();
    
        // Result either returns the position of the provider or -1 if the 
        // provider already exists.
        //
        // If you want to add the provider at a specific position run:
        // int position=2; // insert at position 2.
        // result=Security.insertProviderAt(cryptix_provider, position);
        int result=Security.addProvider(cryptix_provider);
        
    
        if (result==-1)
        {
            System.out.println("Provider entry already in file.\n");
            return false;
        }
        else 
        {
            System.out.println("Provider added at position: "+result);
            return true;
        }
    }


    /**
     * Starts the application. It instantiates a few other classes which do 
     * the really though job of showing usage of ciphers, MDs ...
     *
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) 
    {
        FileInputStream fis      = null;
        String          filename = null;
        
        if (args.length<1)
        {
            System.out.println("You did not specify a file to encrypt!?");
            System.out.println("If you want one to encrypt just place a " +
                               "filename as first argument to the program.");
        }
        else 
        {
            try 
            {
                fis = new FileInputStream(args[0]);
                fis.close();
                filename=args[0];
            }
            catch (FileNotFoundException fnfe)
            {
                System.out.println("Filename is not valid!");
                fnfe.printStackTrace();
                System.exit(-1);
            }
            catch (IOException ioe) 
            {
                System.out.println("IO Error!");
                ioe.printStackTrace();
                System.exit(-1);
            }
        }
        
        // Add provider. 
        // It does not have to be Cryptix, necessarily!
        if (!addProviderCryptix()) 
        {
            System.out.println("Could not add provider cryptix.\n");
            System.exit(-1);
        }
        
        
        // Provider name
        String provider="CryptixCrypto";

        // Array of all secret ciphers
        String secret_algorithm[]={ /* "Blowfish","CAST5","DES","DESede",
            "IDEA","RC2","RC4","Rijndael","SKIPJACK","Square","TripleDES"*/ };
            
        // Array of all secret cipher modes
        String mode[] = { "ECB","CBC","OFB" };
        
        // Array of all paddings
        String padding[] = { "None","NoPadding","PKCS#5" /*,"PKCS5Padding"*/ };
        
        // Loop through all paddings, modes and ciphers and run them
        int i=0;
        int j=0;
        int k=0;
        for (i=0;i<secret_algorithm.length;i++)
        {
            for (j=0;j<mode.length;j++)
            {
                for (k=0; k<padding.length;k++)
                {

                    System.out.println("***************\nNew cipher object!");
                    System.out.println("Using:"+secret_algorithm[i]+"/"+
                                       mode[j]+"/"+padding[k]+"\n");
                                       
                    SymmetricCipher symmetricCipher=new SymmetricCipher();
                    symmetricCipher.run(secret_algorithm[i], mode[j], 
                                        padding[k], provider, filename);
                }
            }
        }

        System.out.println("**************\nRunning hash functions:");
        // run all hash algorithms
        String hashfunctions[] ={ "MD2","MD4","MD5","RIPEMD128","RIPEMD160",
                                  "SHA","SHA0","SHA1"};
        if (filename!=null) 
        {
            for (int ii=0; ii<hashfunctions.length; ii++) 
            {
                System.out.println("***************");
                System.out.println("New hash object.");
                System.out.println("Using: "+hashfunctions[ii]+"/"+provider);
                                   
                FileHashing fh = new FileHashing();
                fh.run(hashfunctions[ii], provider, filename);
            }
        }
        else 
        {
            System.out.println("You did not provide any file to hash.");
        }

        System.out.println("**************");
        System.out.println();

        
        // run RSA samples
        /*
        CryptixRawDSASignature sign=new CryptixRawDSASignature();
        System.out.println("init dsa..");
        System.out.println();
        sign.init();
        */
    }
}
