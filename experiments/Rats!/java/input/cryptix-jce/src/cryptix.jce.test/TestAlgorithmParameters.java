/* $Id: TestAlgorithmParameters.java,v 1.5 2003/02/15 13:43:11 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 * 
 * Use, modification, copying and distribution of this software is subject 
 * the terms and conditions of the Cryptix General Licence. You should have 
 * received a copy of the Cryptix General License along with this library; 
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;

/**
 * <B>This is a testclass for AlgorithmParameters
 * using DES as algorithm as default</B>
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 * @version $Revision: 1.5 $
 */

import java.security.SecureRandom;
import java.security.AlgorithmParameters;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

final class TestAlgorithmParameters
    extends Test 
{
    private static final String PROVIDER = "CryptixCrypto";   
    private static final String name = "AlgorithmParameters";
    private Cipher c;
    private SecretKey sk;
    
    private String algo;
    private int KEY_SIZE;
    
    protected TestAlgorithmParameters (String algo, int KEY_SIZE) 
    {
        super(name);
        this.algo = algo;
        this.KEY_SIZE = KEY_SIZE;
    }
  
    protected TestAlgorithmParameters () 
    {
        super(name);
        this.algo = "DES";
        this.KEY_SIZE = 56;
    }

    protected void doIt() 
    throws Exception 
    {
        init();

        beginTest("test with parameters only AlgorithmParameters");
        passIf(test1());
        beginTest("test with parameters from Cipher");
        passIf(test2(c) && test3(c));
        
        return;
    }
    
    private void init () 
    throws Exception {
        // Generate key
        KeyGenerator kg = KeyGenerator.getInstance(algo, PROVIDER);
        kg.init(KEY_SIZE);
        sk = kg.generateKey();
        
        c = Cipher.getInstance(algo+"/CBC/NONE", PROVIDER);
        c.init(Cipher.ENCRYPT_MODE, sk);
        
        return;
    }

    private boolean test1 () 
    throws Exception 
    {
        byte [] iv = new byte[8];
        new SecureRandom().nextBytes(iv);
        
        AlgorithmParameters ap1 =
            AlgorithmParameters.getInstance("DES", PROVIDER);
        ap1.init(new IvParameterSpec(iv));
        String a = ap1.getAlgorithm();
        String ivs = ap1.toString();
        String p = ap1.getProvider().getName();
        byte [] enc = ap1.getEncoded();
        
        AlgorithmParameters ap2 = AlgorithmParameters.getInstance(a, p);
        ap2.init(enc);
        
        return (a.equals(ap2.getAlgorithm()) && 
                ivs.equals(ap2.toString()) &&
                p.equals(ap2.getProvider().getName()));
    }

    private boolean test2 (Cipher c) 
    throws Exception 
    {
        AlgorithmParameters ap1 = c.getParameters();
        String a = ap1.getAlgorithm();
        String ivs = ap1.toString();
        String p = ap1.getProvider().getName();
        byte [] enc = ap1.getEncoded();
        
        AlgorithmParameters ap2 = AlgorithmParameters.getInstance(algo, PROVIDER);
        ap2.init(enc);
        
        return (a.equals(ap2.getAlgorithm()) && 
                ivs.equals(ap2.toString()) &&
                p.equals(ap2.getProvider().getName()));
    }

    private boolean test3 (Cipher c) 
    throws Exception 
    {
        byte [] iv = c.getIV();
        AlgorithmParameters ap1 = c.getParameters();
        String a = ap1.getAlgorithm();
        String ivs = ap1.toString();
        String p = ap1.getProvider().getName();
        
        AlgorithmParameters ap2 = AlgorithmParameters.getInstance(algo, PROVIDER);
        ap2.init(new IvParameterSpec(iv));
        
        return (a.equals(ap2.getAlgorithm()) && 
                ivs.equals(ap2.toString()) &&
                p.equals(ap2.getProvider().getName()));
    }
}
