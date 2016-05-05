/* $Id: TestSHA384.java,v 1.2 2001/06/25 15:41:13 gelderen Exp $
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


/*package*/ final class TestSHA384 extends MessageDigestTest {

    private static final String NAME="SHA-384";

    private static final String[][] TEST_VALUES = {
        {"abc",
         "cb00753f45a35e8bb5a03d699ac65007272c32ab0eded1631a8b605a43ff5bed"+
         "8086072ba1e7cc2358baeca134c825a7"},
        {"abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno"+
         "ijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu",
         "09330c33f71147e83d192fc782cd1b4753111b173b3b05d22fa08086e3b0f712"+
         "fcc7c71a557e2db966c3e9fa91746039"}
    };


    protected TestSHA384() {
        super(NAME);
    }


    protected void doIt() throws Exception {
        testExistence(NAME);
        testValues(NAME, TEST_VALUES);
        testCloning(NAME);
    }
}
