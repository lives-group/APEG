/* $Id: TestTwofish.java,v 1.2 2000/07/28 20:06:11 gelderen Exp $
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


final class TestTwofish
extends CipherTest
{
    private static final String NAME="Twofish";


    //
    // Test Values taken from the Twofish AES Submission Package
    //
    private static final String[][] TEST_VALUES =
    {
        // Variable Key Known Answer Tests (128-bit key)
        {"80000000000000000000000000000000", 
         "00000000000000000000000000000000", 
         "6BFD32804A1C3206C4BF85EB11241F89"},
        
        // Variable Key Known Answer Tests (192-bit key)
        {"800000000000000000000000000000000000000000000000", 
         "00000000000000000000000000000000", 
         "B5AED133641004F4121B66E7DB8F2FF0"},

        // Variable Key Known Answer Tests (256-bit key)
        {"8000000000000000000000000000000000000000000000000000000000000000", 
         "00000000000000000000000000000000", 
         "785229B51B515F30A1FCC88B969A4E47"}
    };


    protected TestTwofish()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
