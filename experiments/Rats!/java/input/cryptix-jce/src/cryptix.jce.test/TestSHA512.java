/* $Id: TestSHA512.java,v 1.2 2001/06/25 15:41:13 gelderen Exp $
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


/*package*/ final class TestSHA512 extends MessageDigestTest {

    private static final String NAME="SHA-512";

    private static final String[][] TEST_VALUES = {
        {"abc",
         "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a"+
         "2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f"},
        {"abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno"+
         "ijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu",
         "8e959b75dae313da8cf4f72814fc143f8f7779c6eb9f7fa17299aeadb6889018"+
         "501d289e4900f7e4331b99dec4b5433ac7d329eeb6dd26545e96e55b874be909"}
    };


    protected TestSHA512() {
        super(NAME);
    }


    protected void doIt() throws Exception {
        testExistence(NAME);
        testValues(NAME, TEST_VALUES);
        testCloning(NAME);
    }
}
