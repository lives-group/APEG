/* $Id: TestCAST5.java,v 1.5 2000/07/28 20:06:11 gelderen Exp $
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


final class TestCAST5
extends CipherTest
{
    private static final String NAME="CAST5";

    private static final String[][] TEST_VALUES =
    {
        {"0123456712345678234567893456789A",
                                     "0123456789ABCDEF", "238B4FE5847E44B2"},
        {"01234567123456782345",     "0123456789ABCDEF", "EB6A711A2C02271B"},
        {"0123456712",               "0123456789ABCDEF", "7AC816D16E9B302E"}
    };


    protected TestCAST5()
    {
        super(NAME);
    }


    protected void doIt()
    throws Exception
    {
        testCipher(TEST_VALUES);
    }
}
