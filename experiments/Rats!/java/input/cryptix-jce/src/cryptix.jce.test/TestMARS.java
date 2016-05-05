/* $Id: TestMARS.java,v 1.2 2000/07/28 20:06:11 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


final class TestMARS
extends CipherTest
{
    private static final String NAME="MARS";


    //
    // Test Values taken from the Twofish AES Submission Package
    //
    private static final String[][] TEST_VALUES =
    {
        {"80000000000000000000000000000000",
         "00000000000000000000000000000000",
         "B3E2AD5608AC1B6733A7CB4FDF8F9952"},
        {"40000000000000000000000000000000",
         "00000000000000000000000000000000",
         "8655D5CCAF76A3A8AA09841F04689465"},
        {"20000000000000000000000000000000",
         "00000000000000000000000000000000",
         "F611F21A70C0AB5FB3D52AD5E8196E09"},
        {"10000000000000000000000000000000",
         "00000000000000000000000000000000",
         "6676D02EAE3EE58FA396BE40A3A6A234"},
        {"08000000000000000000000000000000",
         "00000000000000000000000000000000",
         "786E147E5D66764A16DFE1DCB10F3F13"},
         //[...]
         
        {"00000000000000000000000000000000",
         "80000000000000000000000000000000",
         "D962EFEBA75817BF6ED24BBBB5B7820D"},
        // [...]
        
        {"000000000000000000000000000000000000000000000000",
         "80000000000000000000000000000000",
         "4433FCCBF2D4AA14783835D0968BB7A6"},
        {"000000000000000000000000000000000000000000000000",
         "00000000000008000000000000000000",
         "589606504D2BBC4A85507D61AD1AE6E1"},
         
        {"0000000000000000000000000000000000000000000000000000000000000000",
         "80000000000000000000000000000000",
         "7DEC45D013733A2FC3ACB05AC5EDCDA0"},
        {"8000000000000000000000000000000000000000000000000000000000000000",
         "00000000000000000000000000000000",
         "EA4FCDBA1EB0C533938AA9FA32B740F3"},
        {"0000000000000000000000000000000000000000000000000000000000000001",
         "00000000000000000000000000000000",
         "B5C218F26198EE763B863EDA24446609"},
    };


    protected TestMARS()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
