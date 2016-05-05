/* $Id: TestSKIPJACK.java,v 1.5 2000/07/28 20:06:11 gelderen Exp $
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


final class TestSKIPJACK
extends CipherTest
{
    private static final String NAME="SKIPJACK";

    //
    // Test values taken from
    // - http://csrc.nist.gov/encryption/skipjack-kea.htm
    //
    private static final String[][] TEST_VALUES =
    {
        {"00998877665544332211", "33221100ddccbbaa", "2587cae27a12d300"},
    };


    protected TestSKIPJACK()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
