/* $Id: TestSealedObject.java,v 1.4 2000/07/28 20:06:11 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 * 
 * Use, modification, copying and distribution of this software is subject 
 * the terms and conditions of the Cryptix General Licence. You should have 
 * received a copy of the Cryptix General Licence along with this library; 
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SealedObject;
import javax.crypto.KeyGenerator;


/**
 * <B>This is a testclass for SealedObject and 
 * we use DES/ECB/NoPadding as default</B>
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */
final class TestSealedObject 
extends Test 
{
    private static final String name = "SealedObject";
    private Cipher c, d;
    private SecretKey sk;
    private final String s = "ThisIsASecretSecretMessag";
    
    private String algo;
    private int KEY_SIZE;

    
    protected TestSealedObject (String algo, int KEY_SIZE) 
    {
        super(name);
        this.algo = algo;
        this.KEY_SIZE = KEY_SIZE;
    }


    protected TestSealedObject () 
    {
        super(name);
        this.algo = "DES";
        this.KEY_SIZE = 56;
    }


    protected void doIt() 
    throws Exception 
    {
        init();
        beginTest("Decrypt with the cipher");
        passIf(test1(c,d,s));
        beginTest("Decrypting with the secretkey");
        passIf(test2(c,sk,s));
    
        return;
    }


    private void init () 
    throws Exception 
    {
        // Generate and initilize keys and ciphers.
        KeyGenerator kg = KeyGenerator.getInstance(algo);
        kg.init(KEY_SIZE);
        sk = kg.generateKey();
    
        algo = algo + "/ECB/NoPadding";
        c = Cipher.getInstance(algo);
        d = Cipher.getInstance(algo);
    
        c.init(Cipher.ENCRYPT_MODE, sk);
        d.init(Cipher.DECRYPT_MODE, sk);
        return;
    }


    private boolean test1(Cipher c, Cipher d, String s) 
    throws Exception 
    {
        String rs = "";
        SealedObject so = new SealedObject(s, c);
        rs = (String)so.getObject(d);
    
        return rs.equals(s);
    }


    private boolean test2(Cipher c, SecretKey sKey, String s)
    throws Exception 
    {
        String rs = "";
        SealedObject so = new SealedObject(s, c);
        rs = (String)so.getObject(sKey);
        
        return rs.equals(s);
    }
}
