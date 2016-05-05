/* $Id: TestSHA0.java,v 1.7 2001/06/25 15:41:13 gelderen Exp $
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


final class TestSHA0
extends MessageDigestTest
{
    private static final String NAME="SHA0";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
         { "",    "F96CEA198AD1DD5617AC084A3D92C6107708C0EF" },
         { "a",   "37F297772FAE4CB1BA39B6CF9CF0381180BD62F2" },
         { "aa",  "5173EC2335C575DEE032B01562A41330EB803503" },
         { "aaa", "5DFC8A87381AA03E963AB26A645F0FDD60847DFA" },
         { "abc", "0164B8A914CD2A5E74C4F7FF082C4D97F1EDF880" }, // 180 App A
         { "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
                  "d2516ee1acfa5baf33dfc1c471e438449ef134c8" }, // 180 App B
         { "message digest",
                  "C1B0F222D150EBB9AA36A40CAFDC8BCBED830B14" },
         { "abcdefghijklmnopqrstuvwxyz",
                  "B40CE07A430CFD3C033039B9FE9AFEC95DC1BDCD" }
    };


    private static final String LARGE_STRING_VALUE =
        "3232affa48628a26653b5aaa44541fd90d690603";


    protected TestSHA0()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testExistence("SHA-0");
        testValues(NAME, TEST_VALUES);
        testLargeString(NAME, (byte)'a', 1000000, LARGE_STRING_VALUE);
        testCloning(NAME);
    }
}
