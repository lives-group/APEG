/* $Id: MessageDigestTest.java,v 1.7 2000/07/28 20:06:10 gelderen Exp $
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


import java.security.MessageDigest;


abstract class MessageDigestTest
extends Test
{
    protected MessageDigestTest(String name)
    {
        super(name);
    }


    protected void testExistence(String alg)
    throws Exception
    {
        beginTest("Algorithm available as "+alg);
        boolean res = true;
        try
        {
            MessageDigest md = MessageDigest.getInstance(alg, "CryptixCrypto");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            res=false;
        }
        passIf(res);
    }


    protected void testCloning(String alg)
    throws Exception
    {
        beginTest("Cloning");
        boolean res = true;
        try
        {
            MessageDigest md1 = MessageDigest.getInstance(alg, "CryptixCrypto");
            MessageDigest md2 = (MessageDigest)md1.clone();
            md1.update((byte)'a');
            MessageDigest md3 = (MessageDigest)md1.clone();
            md1.update((byte)'a');
            md2.update((byte)'a');
            md2.update((byte)'a');
            md3.update((byte)'a');

            byte[] res1 = md1.digest();
            byte[] res2 = md2.digest();
            byte[] res3 = md3.digest();

            if( !Util.areEqual(res1, res2) || !Util.areEqual(res1, res3))
                res=false;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            res=false;
        }
        passIf(res);
    }


    protected void testLargeString(String alg, byte c, int count, String result)
    throws Exception
    {
        beginTest("Test Values (Large String)");
        boolean res = true;
        try
        {
            byte[] hash = Util.hexFromString(result);
            MessageDigest md = MessageDigest.getInstance(alg, "CryptixCrypto");
            for(int i=0; i<count; i++)
                md.update(c);

            byte[] out = md.digest();
            if( !Util.areEqual(out, hash) )
                    res = false;

        }
        catch(Throwable e)
        {
            e.printStackTrace();
            res = false;
        }
        passIf(res);
    }


    protected void testValues(String alg, String[][] testValues)
    throws Exception
    {
        beginTest("Test Values");
        boolean res = true;
        try
        {
            for( int i=0; i<testValues.length; i++)
            {
                byte[] pt   = testValues[i][0].getBytes();
                byte[] hash = Util.hexFromString(testValues[i][1]);

                MessageDigest md = MessageDigest.getInstance(alg, "CryptixCrypto");
                byte[] out = md.digest(pt);

                if( !Util.areEqual(out, hash) )
                    res = false;
            }

        }
        catch(Throwable e)
        {
            e.printStackTrace();
            res = false;
        }
        passIf(res);
    }
}
