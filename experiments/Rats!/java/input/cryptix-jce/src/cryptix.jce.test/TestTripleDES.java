/* $Id: TestTripleDES.java,v 1.8 2001/08/06 21:22:55 edwin Exp $
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


final class TestTripleDES
extends CipherTest
{
    private static final String NAME="TripleDES";
    
    private static final String[][] TEST_VALUES = 
    {
    //           key ............................................
    //           plain text .....    cipher text ....
    //
          {     // same key ==> DES
                "010101010101010101010101010101010101010101010101",
                "95F8A5E5DD31D900", "8000000000000000" },
          {     // same key ==> DES
                "010101010101010101010101010101010101010101010101",
                "9D64555A9A10B852", "0000001000000000" },
          {     // same key ==> DES
                "3849674C2602319E3849674C2602319E3849674C2602319E",
                "51454B582DDF440A", "7178876E01F19B2A" },
          {     // same key ==> DES
                "04B915BA43FEB5B604B915BA43FEB5B604B915BA43FEB5B6",
                "42FD443059577FA2", "AF37FB421F8C4095" },
          {     // for checking first phase of below, defers to same
                "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF",
                "736F6D6564617461", "3D124FE2198BA318" },
          {     // note k1 == k3
                "0123456789ABCDEF55555555555555550123456789ABCDEF",
                "736F6D6564617461", "FBABA1FF9D05E9B1" },
          {
                "0123456789ABCDEF5555555555555555FEDCBA9876543210",
                "736F6D6564617461", "18d748e563620572" },
          {
                "0352020767208217860287665908219864056ABDFEA93457",
                "7371756967676C65", "c07d2a0fa566fa30" },
          {     // some of the weak(?) keys found in the test data
                "010101010101010180010101010101010101010101010102",
                "0000000000000000", "e6e6dd5b7e722974" },
          {     // some of the weak(?) keys found in the test data
                "10461034899880209107D0158919010119079210981A0101",
                "0000000000000000", "e1ef62c332fe825b" },
          {     // 168 bit key instead of 192 bit
                "000000000000000000000000000000000000000000",
                "95F8A5E5DD31D900", "8000000000000000" },
          {     // 168 bit key instead of 192 bit
                "000000000000000000000000000000000000000000",
                "9D64555A9A10B852", "0000001000000000" },
          {     // 168 bit key instead of 192 bit
                "000000000000008000000000000000000000000001",
                "0000000000000000", "e6e6dd5b7e722974" },
    };
        
   
    protected TestTripleDES() 
    {
        super(NAME);
    }        


    protected void doIt()
    throws Exception
    {
        testExistence(NAME);
        testExistence("DESede");
        testValuesECB(NAME, TEST_VALUES);
        testKeyGenExistence(NAME);
        testKeyGenWorks(NAME);
    }    
}
