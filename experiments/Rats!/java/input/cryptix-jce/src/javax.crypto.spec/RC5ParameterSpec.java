/* $Id: RC5ParameterSpec.java,v 1.9 2000/09/01 17:37:37 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto.spec;


import java.security.spec.AlgorithmParameterSpec;

import cryptix.jce.util.Util;


/**
 * @version $Revision: 1.9 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @author  Paul Waserbrot (pw@cryptix.org)
 */
public class RC5ParameterSpec implements AlgorithmParameterSpec {

    private final int version;
    private final int rounds;
    private final int wordSize;
    private final byte[] iv;


    public RC5ParameterSpec(int version, int rounds, int wordSize) {
        this.version  = version;
        this.rounds   = rounds;
        this.wordSize = wordSize;
        this.iv       = null;
    }


    public RC5ParameterSpec(int version, int rounds, int wordSize, byte[] iv) {
        this.version  = version;
        this.rounds   = rounds;
        this.wordSize = wordSize;
        this.iv       = (byte[])iv.clone();
    }


    public RC5ParameterSpec(int version, int rounds, int wordSize,
                            byte[] iv, int offset)
    {
        this.version  = version;
        this.rounds   = rounds;
        this.wordSize = wordSize;

        this.iv = new byte[2*(wordSize/8)];
        System.arraycopy(iv, offset, this.iv, 0, this.iv.length);
    }


    public int getVersion() {
        return this.version;
    }


    public int getRounds() {
        return this.rounds;
    }


    public int getWordSize() {
        return this.wordSize;
    }


    public byte[] getIV() {
        return (this.iv==null) ? this.iv : (byte[])this.iv.clone();
    }


    public boolean equals(Object obj) {
        
        if( !(obj instanceof RC5ParameterSpec) ) return false;

        RC5ParameterSpec that = (RC5ParameterSpec)obj;

        return (this.version  == that.version ) &&
               (this.rounds   == that.rounds  ) &&
               (this.wordSize == that.wordSize) &&
               (Util.equals(this.iv, that.iv) );
    }

    public int hashCode() {
        return 0;
    }
}
