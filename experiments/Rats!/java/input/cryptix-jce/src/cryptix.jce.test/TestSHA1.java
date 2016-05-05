/* $Id: TestSHA1.java,v 1.7 2001/06/25 15:41:13 gelderen Exp $
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


final class TestSHA1
extends MessageDigestTest
{
    private static final String NAME="SHA1";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
        {"",    "da39a3ee5e6b4b0d3255bfef95601890afd80709"},
        {"1",   "356a192b7913b04c54574d18c28d46e6395428ab"},
        {"a",   "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8"},
        {"abc", "a9993e364706816aba3e25717850c26c9cd0d89d"},
        {"abcdefghijklmnopqrstuvwxyz",
                "32d10c7b8cf96570ca04ce37f2a19d84240d3a89"},
        {"abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
                "84983E441C3BD26EBAAE4AA1F95129E5E54670F1"}, // 180-1 App B
        {"Anyone got any SHA-1 test data?",
                "09b9e9c04a84ce274942048acf3a6f2ff4a8a39c"}, // old Cj data
        {"Of cabbages and kings",
                "5f093d74a9cb1f2f14537bcf3a8a1ffd59b038a2"}  // old Cj data
    };


    private static final String LARGE_STRING_VALUE =
        "34AA973CD4C4DAA4F61EEB2BDBAD27316534016F";


    protected TestSHA1()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        cryptix.jce.provider.md.SHA1 foo = new cryptix.jce.provider.md.SHA1();

        testExistence(NAME);
        testExistence("SHA");
        testExistence("SHA-1");
        testValues(NAME, TEST_VALUES);
        testLargeString(NAME, (byte)'a', 1000000, LARGE_STRING_VALUE);
        testCloning(NAME);
    }
}
