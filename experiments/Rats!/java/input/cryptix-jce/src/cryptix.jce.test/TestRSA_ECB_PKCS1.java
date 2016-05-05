/* $Id: TestRSA_ECB_PKCS1.java,v 1.3 2001/02/26 16:21:08 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 * 
 * Use, modification, copying and distribution of this software is subject 
 * the terms and conditions of the Cryptix General Licence. You should have 
 * received a copy of the Cryptix General Licence along with this library; 
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;


/**
 * <B>This is a testclass for RSA/ECB/PKCS#1
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */
final class TestRSA_ECB_PKCS1 extends Test {

    private static final String name = "RSA_ECB_PKCS1";
    private Cipher c, d;
    private final String s = "ThisIsASecretSecretMessageOfNull";
    
    private String algo;
    private int KEY_SIZE;
  
    protected TestRSA_ECB_PKCS1 (String algo, int KEY_SIZE) 
    {
        super(name);
        this.algo = algo;
        this.KEY_SIZE = KEY_SIZE;
    }
  
    protected TestRSA_ECB_PKCS1 () 
    {
        super(name);
        this.algo = "RSA";
        this.KEY_SIZE = 1024;
    }

    protected void doIt() 
    throws Exception 
    {
        beginTest("ECB with PKCS1");
        init();
        passIf(test(c,d,s));
          
        return;
    }

    private void init () 
    throws Exception 
    {
        this.algo = "RSA";
        this.KEY_SIZE = 1024;
    
        // Generate and initilize keys and ciphers.
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algo);
        kpg.initialize(KEY_SIZE);
        KeyPair kp = kpg.generateKeyPair(); // JDK 1.1 call
        RSAPrivateKey prk = (RSAPrivateKey)kp.getPrivate();
        RSAPublicKey puk = (RSAPublicKey)kp.getPublic();
    
        algo = algo + "/ECB/PKCS#1";
        c = Cipher.getInstance(algo);
        d = Cipher.getInstance(algo);
    
        c.init(Cipher.ENCRYPT_MODE, puk);
        d.init(Cipher.DECRYPT_MODE, prk);
        return;
    }

    private boolean test(Cipher c, Cipher d, String s) 
    throws Exception 
    {
        byte [] b = s.getBytes(); 
        byte [] ca = d.doFinal(c.doFinal(b));

        return (new String(ca).equals(s));
    }
}
