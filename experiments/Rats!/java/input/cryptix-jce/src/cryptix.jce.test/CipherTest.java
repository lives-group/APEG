/* $Id: CipherTest.java,v 1.11 2003/02/15 13:47:10 gelderen Exp $
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


import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


abstract class CipherTest extends Test {

    private static SecureRandom random = new SecureRandom();
    
    private final String alg;

    private final boolean testStreamCipher;
    
    
    protected CipherTest(String name) 
    {
        super(name);
        this.alg = name;
        this.testStreamCipher = false;
    }


    protected CipherTest(String name, boolean testStreamCipher) 
    {
        super(name);
        this.alg = name;
        this.testStreamCipher = testStreamCipher;
    }
    
    
    protected void testExistence(String alg)
    throws Exception 
    {
        beginTest("Algorithm available as "+alg);
        boolean res = true;
        try 
        {
            Cipher c = Cipher.getInstance(alg, "CryptixCrypto");
        } 
        catch(NoSuchAlgorithmException e) 
        {
            res=false;
        }
        passIf(res);
    }
    
    
    protected void testValuesCFB(String alg, String[][] testValues)
    throws Exception
    {
        beginTest("CFB Test Values (" + alg +")");
        boolean res = true;
        try {
            for( int i=0; i<testValues.length; i++) {
                byte[] key = Util.hexFromString(testValues[i][0]);
                byte[] iv  = Util.hexFromString(testValues[i][1]);
                byte[] pt  = Util.hexFromString(testValues[i][2]);
                byte[] ct  = Util.hexFromString(testValues[i][3]);

                IvParameterSpec ivs = new IvParameterSpec(iv);
                Cipher c = Cipher.getInstance(alg, "CryptixCrypto");
                c.init( Cipher.ENCRYPT_MODE, new K(alg, key), ivs );
                byte[] trial_ct = c.doFinal(pt);
            
                c.init( Cipher.DECRYPT_MODE, new K(alg, key), ivs );
                byte[] trial_pt = c.doFinal(ct);
            
                if( !Util.areEqual(ct, trial_ct) 
                    || !Util.areEqual(pt, trial_pt) )
                {
                    res = false;
                }
            }
        } 
        catch(Throwable e) {
            e.printStackTrace();
            res = false;
        }
        passIf(res);
    }


    protected void testValuesECB(String alg, String[][] testValues)
    throws Exception
    {
        beginTest("ECB Test Values");
        boolean res = true;
        try 
        {
            for( int i=0; i<testValues.length; i++) 
            {
                byte[] key = Util.hexFromString(testValues[i][0]);
                byte[] pt  = Util.hexFromString(testValues[i][1]);
                byte[] ct  = Util.hexFromString(testValues[i][2]);

                String transform = alg+"/ECB/None";
                if(testStreamCipher)
                    transform = alg;

                Cipher c = Cipher.getInstance(transform, "CryptixCrypto");
                c.init( Cipher.ENCRYPT_MODE, new K(alg, key) );
                byte[] trial_ct = c.doFinal(pt);
            
                c.init( Cipher.DECRYPT_MODE, new K(alg, key) );
                byte[] trial_pt = c.doFinal(ct);
            
                if( !Util.areEqual(ct, trial_ct) 
                    || !Util.areEqual(pt, trial_pt) )
                {
                    System.out.println(testValues[i][0]);
                    res = false;
                }
            }
            
        } 
        catch(Throwable e) 
        {
            e.printStackTrace();
            res = false;
        }
        passIf(res);
    }
    

    protected void testKeyGenExistence(String alg)
    throws Exception
    {
        beginTest("KeyGenerator available");
        boolean res = true;
        try 
        {
            KeyGenerator keygen = KeyGenerator.getInstance(alg, "CryptixCrypto");
        } 
        catch(NoSuchAlgorithmException e) 
        {
            res=false;
        }
        passIf(res);
    }
    


    /**
     * Test whether the KeyGenerator for this algorithm works.
     * Try and generate a lot of keys and for each key test 
     * whether x = Dk(Ek(x)).
     */ 
    protected void testKeyGenWorks(String alg)
    throws Exception
    {
        boolean res = true;
        beginTest("KeyGenerator works");
        try 
        {
            KeyGenerator keygen = KeyGenerator.getInstance(alg,"CryptixCrypto");
            keygen.init(random);

            String transform = alg+"/ECB/None";
            if(testStreamCipher)
                transform = alg;

            Cipher c = Cipher.getInstance(transform, "CryptixCrypto");
            int block_size = c.getBlockSize();
            byte[] pt = new byte[block_size];
            for(int i=0; i<pt.length; i++)
                pt[i] = (byte)i;
                
            for(int i=0; i<1000; i++) 
            {
                SecretKey k = keygen.generateKey();
                c.init(Cipher.ENCRYPT_MODE, k);
                byte[] tmp = c.doFinal(pt);
                c.init(Cipher.DECRYPT_MODE, k);
                tmp = c.doFinal(tmp);
                res = Util.areEqual(tmp, pt);
                if(!res)
                    break;
            }
            
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
            res=false;
        }
        passIf(res);
    }
    
    
    protected void testCipher(String[][] testValues)
    throws Exception
    {
        testExistence(alg);
        testValuesECB(alg, testValues);
        testKeyGenExistence(alg);
        testKeyGenWorks(alg);
    }
    
    
    private class K 
    implements Key 
    {
        private final byte[] keyBytes;
        private final String alg;
        
        K(String alg, byte[] keyBytes) 
        {
            this.alg      = alg;
            this.keyBytes = keyBytes;
        }
        
        public String getAlgorithm() { return alg;  }
        public String getFormat()    { return "RAW"; }
        public byte[] getEncoded()   { return (byte[])keyBytes.clone(); }
    }
}       
