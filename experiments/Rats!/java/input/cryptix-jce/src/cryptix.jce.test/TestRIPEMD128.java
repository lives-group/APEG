/* $Id: TestRIPEMD128.java,v 1.6 2001/06/25 15:41:13 gelderen Exp $
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


final class TestRIPEMD128
extends MessageDigestTest
{
    private static final String NAME="RIPEMD128";

    private static final String[][] TEST_VALUES =
    {
        //    data, md
        {"",
            "CDF26213A150DC3ECB610F18F6B38B46"},
        {"a",
            "86BE7AFA339D0FC7CFC785E72F578D33"},
        {"abc",
            "C14A12199C66E4BA84636B0F69144C77"},
        {"message digest",
            "9E327B3D6E523062AFC1132D7DF9D1B8"},
        {"abcdefghijklmnopqrstuvwxyz",
            "FD2AA607F71DC8F510714922B371834E"},
        {"abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
            "A1AA0689D0FAFA2DDC22E88B49133A06"},
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
            "D1E959EB179C911FAEA4624C60C5C702"},
        {"123456789012345678901234567890123456789012345678901234567890"+
         "12345678901234567890",
            "3F45EF194732C2DBB2C4A2C769795FA3"}
    };

    private static final String LARGE_STRING_VALUE =
        "4A7F5723F954EBA1216C9D8F6320431F";


    protected TestRIPEMD128()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testExistence("RIPEMD-128");
        testValues(NAME, TEST_VALUES);
        testLargeString(NAME, (byte)'a', 1000000, LARGE_STRING_VALUE);
        testCloning(NAME);
    }
}
