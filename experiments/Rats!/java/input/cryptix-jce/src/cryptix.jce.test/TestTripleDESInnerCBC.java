/* $Id: TestTripleDESInnerCBC.java,v 1.1 2003/03/24 01:54:58 gelderen Exp $
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

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


final class TestTripleDESInnerCBC extends CipherTest
{
    private static final String NAME="TripleDESInnerCBC/InnerCBC/None";
    
   
    protected TestTripleDESInnerCBC() 
    {
        super(NAME);
    }        


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testValuesCFB(NAME, VECS);

    }


    private String[][] VECS =
    {
        {
            "000000000000000000000000000000000000000000000000",
            "000000000000000000000000000000000000000000000000",
            "31366279746573617265656E6F756768",
            "844758AE22ADF08AC357F0587188242D"
        }
    };


    protected void testValuesCFB(String alg, String[][] testValues)
    throws Exception
    {
        beginTest("XXX...");
        boolean res = true;
        try {
            for( int i=0; i<testValues.length; i++) {
                byte[] key = Util.hexFromString(testValues[i][0]);
                byte[] iv  = Util.hexFromString(testValues[i][1]);
                byte[] pt  = Util.hexFromString(testValues[i][2]);
                byte[] ct  = Util.hexFromString(testValues[i][3]);
                IvParameterSpec ivs = new IvParameterSpec(iv);

                Cipher c = Cipher.getInstance(NAME, "CryptixCrypto");

                c.init( Cipher.ENCRYPT_MODE, new K(NAME, key), ivs );
                byte[] trial_ct = c.doFinal(pt);

                c.init( Cipher.DECRYPT_MODE, new K(NAME, key), ivs );
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

    private class K implements Key
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
