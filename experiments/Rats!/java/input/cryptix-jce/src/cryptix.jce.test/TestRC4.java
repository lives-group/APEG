/* $Id: TestRC4.java,v 1.1 2003/02/15 13:45:14 gelderen Exp $
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


final class TestRC4
extends CipherTest
{
    private static final String NAME="RC4";

    //
    // Values taken from
    // - draft-kaukonen-cipher-arcfour-03.txt
    //
    private static final String[][] TEST_VALUES =
    {
        {"0123456789ABCDEF", "0000000000000000","7494C2E7104B0879"},
        {"618a63d2fb",       "dcee4cf92c",      "f13829c9de"      }
    };


    protected TestRC4()
    {
        super(NAME, true);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
