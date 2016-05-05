/* $Id: TestRSA.java,v 1.6 2001/02/26 16:23:15 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited.
 * All rights reserved.
 * 
 * Use, modification, copying and distribution of this software is subject 
 * the terms and conditions of the Cryptix General Licence. You should have 
 * received a copy of the Cryptix General Licence along with this library; 
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;

import java.security.interfaces.RSAPublicKey;

import javax.crypto.*;


class TestRSA
extends Test
{
    private static SecureRandom random = new SecureRandom();

    
    TestRSA() 
    {
        super("RSA");
    }
    
    
    protected void doIt()
    {
        int PASSES=50;
        for(int i=0; i<PASSES; i++)
        {
            long start = System.currentTimeMillis();
            String pass = "(pass " + i + "/" + PASSES + ")";
            doItPrivate2(pass);
            doItPrivate1(pass);
            long delta = (System.currentTimeMillis() - start) / 1000;
            //System.out.println("duration: " + delta + "s");
        }
    }
    
    
    private void doItPrivate1(String pass)
    {
        beginTest("RSA Signing " + pass);
        boolean res = false;
        try
        {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(768);
            
            KeyPair pair = gen.generateKeyPair();
            //System.out.println(pair);
            
            //System.out.println( ((RSAPublicKey)pair.getPublic()).getModulus() );
            
            Signature sig = Signature.getInstance("SHA1withRSA");
            //System.out.println("sig: " + sig);
            
            sig.initSign(pair.getPrivate());
            //System.out.println("sig: " + sig);
            
            sig.update((byte)0);
            
            byte[] signed = sig.sign();
            //System.out.println("signed: " + signed);
            
            sig.initVerify(pair.getPublic());
            //System.out.println("sig: " + sig);
            
            sig.update((byte)0);
            
            //System.out.println("" + sig.verify(signed) );
            
            signed[0] = (byte)~signed[0];
            //System.out.println("" + sig.verify(signed) );

            res = true;
        } 
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        passIf(res);
    }
    
    private void doItPrivate2(String pass)
    {
        beginTest("RSA Crypting " + pass);
        boolean res = false;
        try
        {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(768);
            
            KeyPair pair = gen.generateKeyPair();
            //System.out.println(pair);
            
            //System.out.println( ((RSAPublicKey)pair.getPublic()).getModulus() );
            
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS#1");
            //System.out.println("c: " + c);

            res = true;
        } 
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        passIf(res);
    }
}       
