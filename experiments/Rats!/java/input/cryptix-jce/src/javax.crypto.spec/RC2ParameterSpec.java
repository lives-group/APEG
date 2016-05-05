/* $Id: RC2ParameterSpec.java,v 1.7 2000/06/09 20:46:00 pw Exp $
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


/**
 * @version $Revision: 1.7 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 * @author  Paul Waserbrot (pw@cryptix.org)
 */
public class RC2ParameterSpec
implements AlgorithmParameterSpec
{
    private final int    bits;
    private final byte[] iv;


    public RC2ParameterSpec(int effectiveKeyBits) 
    {
        this.bits = effectiveKeyBits;
        this.iv   = null;
    }


    public RC2ParameterSpec(int effectiveKeyBits, byte[] iv) 
    {
        this.bits = effectiveKeyBits;
        this.iv   = iv;
    }


    public RC2ParameterSpec(int effectiveKeyBits, byte[] iv, int offset) 
    {
        this.bits = effectiveKeyBits;
        this.iv = new byte[8];
        System.arraycopy(iv, offset, this.iv, 0, this.iv.length);
    }


    public int getEffectiveKeyBits() 
    {
        return this.bits;
    }


    public byte[] getIV() 
    {
        return (byte[])this.iv.clone();
    }

    public boolean equals(Object obj)
    {
	if (!(obj instanceof RC2ParameterSpec))
	    return false;

	// FIXME: Add so we check if the IVs are equal. Did a fast try with
	// two byte arrays and .equals(...) but it seems not to work (pw)
	return ((this.bits == ((RC2ParameterSpec)obj).getEffectiveKeyBits()) 
		&&
		((this.iv == null) && 
		 (((RC2ParameterSpec)obj).getIV() == null)));
    }
    public int hashCode()
    {
	// FIXME: Implement this! (pw)
	throw new RuntimeException("NYI: method not implemented!");
    }
}
