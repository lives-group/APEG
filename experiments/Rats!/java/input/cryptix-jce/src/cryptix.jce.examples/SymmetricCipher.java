/* $Id: SymmetricCipher.java,v 1.7 2000/07/28 20:09:20 gelderen Exp $
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


import sun.misc.BASE64Encoder;
import java.security.Security;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

import javax.crypto.spec.IvParameterSpec;


/**
 * Sample app for creation of ciphers, using them on a few bytes.
 *
 * @author: Josef Hartmann (jhartmann@bigfoot.com)
 * @version: $Revision: 1.7 $
 */
public final class SymmetricCipher 
{
	/** cipher object */
	private Cipher cipher=null;
	
	/** keygenerator object */
	private KeyGenerator kg=null;

    /**
     * Starts the application. Create keys, iv´s, run cipher on a few bytes 
     * and pass values for using the FileDEncryption class.
     *
     * @param algorithm Algorithm.
     * @param mode      Cipher mode.
     * @param padding   Padding scheme the cipher should use.
     * @param provider  Name of provider as string.
     * @param filename  Name of the file to [de|en]crypt.
     */
    public void run(String algorithm, String mode, String padding, 
                    String provider, String filename) 
    {
        try
        {
            // create a cipher object: ("algorithm/mode/padding", provider)
            // currently cryptix only supports padding types: NoPadding, None
            cipher = Cipher.getInstance(
                         algorithm+"/"+mode+"/"+padding,provider);
          
            // get a key generator for the algorithm.
            kg = KeyGenerator.getInstance(algorithm,provider);
          
            int strength = 0; // strength of key
            IvParameterSpec spec = null; // initialization vector
            byte[]          iv   = null; // initialization vector as byte[]
        
            // Check which algorithm is used and define key size.
            if (algorithm=="Blowfish")
            {
                // valid values are: starting from 40bit up to 
                // 448 in 8-bit increments
                strength=448; 
            }
            else if (algorithm=="CAST5")
            {
                // valid values are: starting from 40bit up to 128bit using 
                // 8bit steps.
                strength=128;
            }
            else if (algorithm=="DES")
            {
                strength=56;
            }
            else if (algorithm=="TripleDES"||algorithm=="DESede")
            {
                // FIXME: is that correct ? Waiting for cryptix' suppport 
                // if 3DES.
                strength=3*64; 
            }
            else if (algorithm=="Rijndael")
            {
                strength=256; //valid values are: 128, 192, 256
            }
            else if (algorithm=="SKIPJACK")
            {
                // fixed size: 80 bits
                strength=80;
            }
            else if (algorithm=="Square")
            {
                strength=128;
            }
            else 
            {
                throw new RuntimeException();
            }
        
            System.out.println("Using keylength: "+strength+" bits.");
            System.out.println("Blocksize: "+cipher.getBlockSize()*8+" bits.");
            System.out.println();
          
            // init key generator with the key size and some random data. 
            // Use SecureRandom only if use trust it. It is not a really 
            // verified PRNG, yet.
            kg.init(strength, new SecureRandom());
          
            // create a secret key from the keygenerator.
            SecretKey key = kg.generateKey();
          
            // init cipher for encryption
          
            // ECB does not need an initialization vector others do.
            if (mode=="ECB")
            {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            else 
            {
                // These modes need an iv with a valid block size in order 
                // to be used.
                SecureRandom sr = new SecureRandom();
                // allocate memory for iv.
                iv = new byte[cipher.getBlockSize()];
                // Get next bytes from the PRNG.
                sr.nextBytes(iv);
                // create the IV class.
                spec = new IvParameterSpec(iv);
            }
        
            if (filename!=null) 
            {
                FileDEncryption fe = new FileDEncryption(filename, key, iv,
                                             algorithm,mode,padding,provider);
          
                System.out.println("******** BEGIN file encryption! *******");
                System.out.println();
                
                // encrypt a file
                fe.go();
                System.out.println("******** END file encryption! *******");
                System.out.println();
        
           
                System.out.println("******** BEGIN file decryption! *******");
                System.out.println();
            
                // decrypt a file
                fe.reTurn(); 
                System.out.println("******** END file decryption! *******");
                System.out.println();
            }

            // Build a few bytes for encryption.
            byte[] text1 = ("text for encryption. You will not recognize it " +
                            "after encryption.").getBytes();
            byte[] text2 = ("!holdrio! more bytes for encryption. " +
                            "aaaaaaaaaaaaaaaaaaEND").getBytes();
        
            // Usage of [de|en]cryption for a few bytes.
            try 
            {
                // use encryption mode using specified key and IV.
                cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            }
            catch(InvalidAlgorithmParameterException iape)
            {
                System.out.println(
                    "cipher.init: InvalidAlgorithmParameterException.");
                iape.printStackTrace();
            }
          
            // Ask the cipher how big the output will be. 
            // Depending on the padding there will be more 
            // output bytes than the sum of input bytes.
            int outLength = cipher.getOutputSize(text1.length+text2.length);
            System.out.println("Output bytes: "+outLength);
            System.out.println();
        
            byte [] encr1 = cipher.update(text1);
        
            byte [] encr2 = cipher.doFinal(text2);
        
            System.out.println("cipher: "+new String(encr1)+new String(encr2));
            System.out.println();
          
            /*
            BASE64Encoder encoder=new BASE64Encoder();
            String result=encoder.encode(encr1+encr2);
            System.out.println("BASE64: "+result+"\n");
            */
          
            try 
            {
                // decryption
                Cipher decipher = Cipher.getInstance(
                                      algorithm+"/"+mode+"/"+padding,provider);
                decipher.init(Cipher.DECRYPT_MODE, key, spec);
                byte[] deciph1 = decipher.update(encr1);
        
                byte[] deciph2 = decipher.doFinal(encr2);
                System.out.println(algorithm+"/"+mode+"/"+padding+
                    " decrypted: "+new String(deciph1)+new String(deciph2)+
                    " "+" "+decipher.getOutputSize(encr1.length+encr2.length));
        
            }
            catch(InvalidAlgorithmParameterException iape)
            {
                System.out.println(
                    "cipher.init: InvalidAlgorithmParameterException.");
                iape.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException nsae)
        {
            System.out.println("No such algorithm!\n");
            nsae.printStackTrace();
        }
        catch (NoSuchPaddingException nspe)
        {
            System.out.println("No such padding!\n");
            nspe.printStackTrace();
        }
        catch (NoSuchProviderException nspre)
        {
            System.out.println("No such provider found!\n");
            nspre.printStackTrace();
        }
        catch (InvalidKeyException ike)
        {
            System.out.println("Invalidkey Exception!\n");
            ike.printStackTrace();
        }
        /*
        catch (ShortBufferException sbe)
        {
            System.out.println("Short buffer exception!\n");
            sbe.printStackTrace();
        }
        */
        catch (IllegalBlockSizeException ibse)
        {
            System.out.println("Illegal block size exception!\n");
            ibse.printStackTrace();
        }
        catch (BadPaddingException bpe){
            System.out.println("Bad padding exception!\n");
            bpe.printStackTrace();
        }
    }
}
