/* $Id: TestTiger.java,v 1.6 2001/06/25 20:30:27 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


final class TestTiger
extends MessageDigestTest
{
    private static final String NAME="Tiger";

    String[][] TEST_VALUES =
    {
        //    data, md
        //  ......................
        {"",               "3293AC630C13F0245F92BBB1766E16167A4E58492DDE73F3"},
        {"abc",            "2AAB1484E8C158F2BFB8C5FF41B57A525129131C957B5F93"},
        {"Tiger",          "DD00230799F5009FEC6DEBC838BB6A27DF2B9D6F110C7937"},
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-",
                           "F71C8583902AFB879EDFE610F82C0D4786A3A534504486B5"},
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZ=abcdefghijklmnopqrstuvwxyz+0123456789",
                           "48CEEB6308B87D46E95D656112CDF18D97915F9765658957"},
        {"Tiger - A Fast New Hash Function, by Ross Anderson and Eli Biham",
                           "8A866829040A410C729AD23F5ADA711603B3CDD357E4C15E"},
        {"Tiger - A Fast New Hash Function, by Ross Anderson and Eli Biham, " +
         "proceedings of Fast Software Encryption 3, Cambridge.",
                           "CE55A6AFD591F5EBAC547FF84F89227F9331DAB0B611C889"},
        {"Tiger - A Fast New Hash Function, by Ross Anderson and Eli Biham, " +
         "proceedings of Fast Software Encryption 3, Cambridge, 1996.",
                           "631ABDD103EB9A3D245B6DFD4D77B257FC7439501D1568DD"},
        {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-" +
         "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-",
                           "C54034E5B43EB8005848A7E0AE6AAC76E4FF590AE715FD25"}
    };


    protected TestTiger()
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
