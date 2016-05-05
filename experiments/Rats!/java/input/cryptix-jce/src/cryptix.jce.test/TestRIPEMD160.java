/* $Id: TestRIPEMD160.java,v 1.6 2001/06/25 15:41:13 gelderen Exp $
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


final class TestRIPEMD160
extends MessageDigestTest
{
    private static final String NAME="RIPEMD160";

    private static final String[][] TEST_VALUES =
    {
            //    data, md
            {"",
                "9C1185A5C5E9FC54612808977EE8F548B2258D31"},
            {"a",
                "0BDC9D2D256B3EE9DAAE347BE6F4DC835A467FFE"},
            {"abc",
                "8EB208F7E05D987A9B044A8E98C6B087F15A0BFC"},
            {"message digest",
                "5D0689EF49D2FAE572B881B123A85FFA21595F36"},
            {"abcdefghijklmnopqrstuvwxyz",
                "F71C27109C692C1B56BBDCEB5B9D2865B3708DBC"},
            {"abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
                "12A053384A9C0C88E405A06C27DCF49ADA62EB2B"},
            {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                "B0E20B6E3116640286ED3A87A5713079B21F5189"},
            {"123456789012345678901234567890123456789012345678901234567890"+
             "12345678901234567890",
                "9B752E45573D4B39F4DBD3323CAB82BF63326BFB"}
    };


    private static final String LARGE_STRING_VALUE =
        "52783243C1697BDBE16D37F97F68F08325DC1528";


    protected TestRIPEMD160()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testExistence("RIPEMD-160");
        testValues(NAME, TEST_VALUES);
        testLargeString(NAME, (byte)'a', 1000000, LARGE_STRING_VALUE);
        testCloning(NAME);
    }
}
