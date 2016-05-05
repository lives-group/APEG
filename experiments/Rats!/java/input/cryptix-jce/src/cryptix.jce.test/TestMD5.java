/* $Id: TestMD5.java,v 1.3 2001/06/25 15:41:13 gelderen Exp $
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


final class TestMD5
extends MessageDigestTest
{
    private static final String NAME="MD5";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
        {"",               "d41d8cd98f00b204e9800998ecf8427e"}, // A.5 1
        {"a",              "0cc175b9c0f1b6a831c399e269772661"},
        {"abc",            "900150983cd24fb0d6963f7d28e17f72"}, // A.5 3
        {"message digest", "f96b697d7cb7938d525a2f31aaf161d0"}, // A.5 4
        {"abcdefghijklmnopqrstuvwxyz",
                           "c3fcd3d76192e4007dfb496cca67e13b"}, // A.5 5
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                           "d174ab98d277d9f5a5611c2c9f419d9f"}, // A.5 6
        {"12345678901234567890123456789012345678901234567890"+
         "123456789012345678901234567890",
                           "57edf4a22be3c955ac49da2e2107b67a"}  // A.5 7
    };


    protected TestMD5()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testValues(NAME, TEST_VALUES);
        testCloning(NAME);
    }
}
