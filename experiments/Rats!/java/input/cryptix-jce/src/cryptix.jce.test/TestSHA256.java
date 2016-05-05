/* $Id: TestSHA256.java,v 1.2 2001/06/25 15:41:13 gelderen Exp $
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


/*package*/ final class TestSHA256 extends MessageDigestTest {

    private static final String NAME="SHA-256";

    private static final String[][] TEST_VALUES = {
        {"abc",
         "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"},
        {"abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
         "248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1" }
    };


    protected TestSHA256() {
        super(NAME);
    }


    protected void doIt() throws Exception {
        testExistence(NAME);
        testValues(NAME, TEST_VALUES);
        testCloning(NAME);
    }
}
