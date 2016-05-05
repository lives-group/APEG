/* $Id: FileDEncryption.java,v 1.6 2000/01/20 14:59:23 gelderen Exp $
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


import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Key;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.crypto.spec.IvParameterSpec;

/**
 * This class encrypts or decrypts one file using a specified key (and IV).
 * Algorithm, mode, padding and provider can be set dynamicly.
 *
 * @version $Revision: 1.6 $
 * @author  Josef Hartmann (jhartmann@bigfoot.com)
 */
public final class FileDEncryption {
    /** Filename of the input file. */
    private String inputFile;
    /** Filename for output file. */
    private String outputFile;
    /** Variable for storing the secret key used by the cipher. */
    private Key secretKey;
    /** Filename for the file which contains the key. */
    private String keyfile;
    /** Name of the algorithm. */
    private String alg;
    /** true if encryption <p> false if decryption */
    private boolean running_enc;
    /** Providername */
    private String provider;
    /** The mode for the cipher (ECB, CBC, ...) */
    private String mode;
    /** Initialization vector for ciphers which need one. */
    private byte[] iv;


    /**
     * Constructor which inits some values. All parameters (including key
     * and iVector) must be created before and valid values.
     *
     * @param filename java.lang.String
     * @param key java.security.Key
     * @param iVector byte[] initialisation vector
     * @param algorithm java.lang.String
     * @param mode java.lang.String
     * @param padding java.lang.String
     * @param prov java.lang.String provider
     */
    public FileDEncryption(String filename, Key key, byte[] iVector,
               String algorithm, String amode, String padding, String prov)
    {
        super();
        inputFile=filename;
        outputFile=filename+"."+algorithm+"."+amode+"."+padding;
        alg = algorithm+"/"+amode+"/"+padding;
        mode = amode;
        secretKey=key;
        iv=iVector;
        keyfile=outputFile+"."+"key";
        provider = prov;
    }


    /**
     * This method does the ciphering job. Depending on the value of
     * running_enc either encryption or decryption is done.
     *
     * @param way int Cipher.ENCRYPT or Cipher.DECRYPT
     * @return boolean
     */
    private boolean ciphering(int way) {
        boolean ret = false;
        boolean ok = false;
        IvParameterSpec ivSpec = null;

        Cipher cipher = null;
        try{
            // Create new cipher object using algorithm/mode/padding stored
            // in "alg" from provider "provider".
            cipher=Cipher.getInstance(alg, provider);

            // Check if ECB is used as it does not need an IV.
            if ((mode == "ECB")){
                cipher.init(way, secretKey);
            }
            else {
                ivSpec = new IvParameterSpec(iv);
                cipher.init(way, secretKey, ivSpec);
            }

            ok = true;
        }
        catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
        }
        catch (NoSuchPaddingException nspe){
            nspe.printStackTrace();
        }
        catch (NoSuchProviderException nspe) {
            nspe.printStackTrace();
        }
        catch (InvalidKeyException ike){
            ike.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException iape) {
            iape.printStackTrace();
        }

        if (ok!=true)
            return false;

        FileInputStream fInput=null;
        FileOutputStream fOutput = null;
        CipherOutputStream cStr = null;

        try {
            // Check which way we are running: encryption or decryption!
            if (running_enc == true) {
                // Read file to encrypt.
                fInput = new FileInputStream(inputFile);
                // Set output file.
                fOutput = new FileOutputStream(outputFile+".encrypted");
                // Create CipherOutputStream using cipher "cipher".
                cStr = new CipherOutputStream(fOutput, cipher);

                // Read input bytes into buffer and run them through the
                // cipher stream.
                byte[] buffer = new byte[8192];
                int length=0;
                while ((length=fInput.read(buffer))!= -1)
                    cStr.write(buffer, 0, length);

                // Close Streams.
                fInput.close();
                cStr.close();
            }
            else {
                // Here we are in decryption mode.
                CipherInputStream ciStr=null;
                // Open file containing encrypted data.
                fInput = new FileInputStream(outputFile+".encrypted");
                ciStr = new CipherInputStream(fInput,cipher);
                fOutput = new FileOutputStream(outputFile+".decrypted");

                // Read bytes and run them through cipher and store them to
                // file back again.
                byte[] buffer=new byte[8192];
                int length=0;
                while ((length=ciStr.read(buffer))!=-1)
                    fOutput.write(buffer,0,length);

                // Close streams.
                ciStr.close();
                fOutput.close();

            }

            ret = true;
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found! "+fnfe.getMessage());
            fnfe.printStackTrace();
        }
        catch (IOException ioe) {
            System.out.println("IOException! "+ioe.getMessage());
            ioe.printStackTrace();
        }

        return ret;
    }


    /**
     * Read key and iv from a file.
     *
     * @return boolean
     */
    private boolean getKeyWithIV() {
        boolean res=false;
        FileInputStream fInput = null;
        try {
            // Create FileInputStream on keyfile.
            fInput = new FileInputStream(keyfile);
            ObjectInputStream objIStream = new ObjectInputStream(fInput);
            // Read key object from file.
            secretKey=(Key)objIStream.readObject();
            // Read the IV from the file.
            iv = new byte[fInput.available()];
            if (mode!="ECB");
                fInput.read(iv);

            objIStream.close();
            System.out.println("Read key!\n");
            res=true;
        }
        catch (StreamCorruptedException sce){
            sce.printStackTrace();
        }
        catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
        catch (OptionalDataException ode){
            ode.printStackTrace();
        }
        catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        return res;
    }


    /**
     * Run the cipher.
     *
     * @return boolean
     */
    public synchronized final boolean go() {
        // Set way we are running ciphering().
        running_enc=true;
        // Save key and IV to the same file.
        boolean result=saveKeyWithIV();
        if (result)
            result = ciphering(Cipher.ENCRYPT_MODE);
        else
            result=!result;
        return result;
    }


    /**
     * Do the decryption setup and run it.
     *
     * @return boolean
     */
    public synchronized final boolean reTurn() {
        // Set the way we are running ciphering().
        running_enc=false;
        // Read key and IV.
        boolean result = getKeyWithIV();

        if (result)
            // Run decryption.
            result = ciphering(Cipher.DECRYPT_MODE);
        else
            result=!result;

        return result;
    }

    /**
     * Save the key and the iv to a file.
     *
     * @return boolean
     */
    private boolean saveKeyWithIV() {
        boolean res=false;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(keyfile);
            ObjectOutputStream objStr = new ObjectOutputStream(fOut);
            objStr.writeObject(secretKey);
            // Write the IV to the file after the key.
            if (mode!="ECB")
                fOut.write(iv);
            // Close streams.
            objStr.close();

            fOut.close();
            res = true;
        }
        catch (FileNotFoundException fnfe){
            System.out.println("File not found!\n");
        }
        catch (IOException ioe){
            System.out.println("IOException!\n");
        }

        return res;
    }
}
