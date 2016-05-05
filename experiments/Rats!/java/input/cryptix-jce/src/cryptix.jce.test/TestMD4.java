/* $Id: TestMD4.java,v 1.6 2001/06/25 15:41:13 gelderen Exp $
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


final class TestMD4
extends MessageDigestTest
{
    private static final String NAME="MD4";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
        {"",               "31D6CFE0D16AE931B73C59D7E0C089C0"}, // A.5 1
        {"a",              "BDE52CB31DE33E46245E05FBDBD6FB24"}, // A.5 2
        {"abc",            "A448017AAF21D8525FC10AE87AA6729D"}, // A.5 3
        {"message digest", "D9130A8164549FE818874806E1C7014B"}, // A.5 4
        {"abcdefghijklmnopqrstuvwxyz",
                           "D79E1C308AA5BBCDEEA8ED63DF412DA9"}, // A.5 5
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                           "043F8582F241DB351CE627E153E7F0E4"}, // A.5 6
        {"12345678901234567890123456789012345678901234567890"+
         "123456789012345678901234567890",
                           "E33B4DDC9C38F2199C3E7B164FCC0536"}  // A.5 7
    };


    protected TestMD4()
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
