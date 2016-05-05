/* $Id: TestDSA.java,v 1.5 2000/07/28 20:06:11 gelderen Exp $
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


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

final class TestDSA
extends Test
{
    private static SecureRandom random = new SecureRandom();

    private static final String NAME = "DSA";


    protected TestDSA()
    {
        super(NAME);
    }


    protected void testExistence(String alg)
    throws Exception
    {
        beginTest("Algorithm available as "+alg);
        boolean res = true;
        try
        {
            Signature c = Signature.getInstance(alg);
        } catch(NoSuchAlgorithmException e)
        {
            res=false;
        }
        passIf(res);
    }


    protected void doIt()
    throws Exception
    {
        testExistence("RawDSA");
    }
}       
