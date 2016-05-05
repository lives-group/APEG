/* $Id: TestMD2.java,v 1.5 2000/07/28 20:06:11 gelderen Exp $
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


final class TestMD2
extends MessageDigestTest
{
    private static final String NAME="MD2";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
        {"",               "8350E5A3E24C153DF2275C9F80692773"}, // A.5 1
        {"a",              "32EC01EC4A6DAC72C0AB96FB34C0B5D1"}, // A.5 2
        {"abc",            "DA853B0D3F88D99B30283A69E6DED6BB"}, // A.5 3
        {"message digest", "AB4F496BFB2A530B219FF33031FE06B0"}, // A.5 4
        {"abcdefghijklmnopqrstuvwxyz",
                           "4E8DDFF3650292AB5A4108C3AA47940B"}, // A.5 5
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                           "DA33DEF2A42DF13975352846C30338CD"}, // A.5 6
        {"1234567890123456789012345678901234567890123456789" +
         "0123456789012345678901234567890",
                           "D5976F79D83D3A0DC9806C3C66F3EFD8"}  // A.5 7
    };


    protected TestMD2()
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
