/* $Id: TestRC2.java,v 1.5 2000/07/28 20:06:11 gelderen Exp $
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


final class TestRC2
extends CipherTest
{
    private static final String NAME="RC2";

    //
    // Values taken from
    // - On the Design and Security of RC2, Knudsen et al.
    //
    private static final String[][] TEST_VALUES =
    {
            {"88bca90e90875a7f0f79c384627bafb2",
                                 "0000000000000000","2269552ab0f85ca6"},
            {"ffffffffffffffff", "ffffffffffffffff","278b27e42e2f0d49"},
            {"3000000000000000", "1000000000000001","30649edf9be7d2c2"},
            {"3000000000000000", "1000000000000001","30649edf9be7d2c2"}
    };


    protected TestRC2()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
