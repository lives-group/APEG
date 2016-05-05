/* $Id: TestNull.java,v 1.2 2000/07/28 20:06:11 gelderen Exp $
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


final class TestNull extends CipherTest
{
    private static final String NAME="Null";
    
    private static final String[][] TEST_VALUES = 
    {
        {"00010002000300040005000600070008", 
                                    "0000000100020003", "0000000100020003"}
    };
        
   
    protected TestNull() {
        super(NAME);
    }        


    protected void doIt() throws Exception {
        testExistence(NAME);
        testValuesECB(NAME, TEST_VALUES);
    }    
}
