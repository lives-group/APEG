/* $Id: TestSerpent.java,v 1.2 2000/07/28 20:06:11 gelderen Exp $
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


final class TestSerpent
extends CipherTest
{
    private static final String NAME="Serpent";


    //
    // Test Values taken from the Serpent AES Submission Package
    //
    private static final String[][] TEST_VALUES =
    {
        {"80000000000000000000000000000000", 
         "00000000000000000000000000000000", 
         "49AFBFAD9D5A34052CD8FFA5986BD2DD"},
        
        {"00000000000000000000000000000000", 
         "80000000000000000000000000000000", 
         "10b5ffb720b8cb9002a1142b0ba2e94a"},

        {"0000000000000000000000000000000000000000000000000000000000000000",
         "80000000000000000000000000000000",
         "da5a7992b1b4ae6f8c004bc8a7de5520"}
    };


    protected TestSerpent()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
