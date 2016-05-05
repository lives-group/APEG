/* $Id: TestElGamal.java,v 1.2 2003/02/17 18:24:43 gelderen Exp $
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

import javax.crypto.*;

import java.math.BigInteger;

import cryptix.jce.ElGamalKey;
import cryptix.jce.ElGamalPublicKey;
import cryptix.jce.ElGamalPrivateKey;

/**
 * Very crude testing class, should be expanded lot.
 * TODO: add testvectors.
 *
 * @version $Revision: 1.2 $
 * @author Erwin van der Koogh (erwin@cryptix.org)
 */
 
class TestElGamal
extends Test
{
    private static SecureRandom random = new SecureRandom();
    
    private KeyPair kp;
    private Cipher  c;

    
    TestElGamal() 
    {
        super("ElGamal");
    }
    
    
    protected void doIt()
    {
        doItPrivate2();
        doItPrivate3();
    }
    
    private void doItPrivate2()
    {
        beginTest("ElGamal KeyGen");
        boolean res = false;
        try
        {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("ElGamal");
            gen.initialize(2048, new SecureRandom());
            
            kp = gen.generateKeyPair();
            
            c = Cipher.getInstance("ElGamal/ECB/PKCS#1");

            res = true;
        } 
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        passIf(res);
    }
    
    private void doItPrivate3()
    {
        beginTest("ElGamal encrypting");
        boolean res = true;
        try
        {
            byte[] data = new byte[20];
            random.nextBytes(data);
            c.init(c.ENCRYPT_MODE, kp.getPublic(), random );
            byte[] encrypted = c.doFinal(data);
            c.init(c.DECRYPT_MODE, kp.getPrivate(), random );
            byte[] data2 = c.doFinal(encrypted);
            
            for(int i = 0; i < data2.length;i++)
            {
                if(data[i] != data2[i])
                {
                    res = false;
                    break;
                }
            }
        }
        catch(Throwable t)
        {
            res = false;
            t.printStackTrace();
        }
        passIf(res);
    }
    
}
