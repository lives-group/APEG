/* $Id: PBEKeySpec.java,v 1.4 2000/01/15 03:16:15 gelderen Exp $
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


import java.security.spec.KeySpec;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class PBEKeySpec
implements KeySpec 
{

    private final char[] password;


    public PBEKeySpec(char[] password) 
    {
        this.password = (char[])password.clone();
    }


    public final char[] getPassword() 
    {
        return (char[])this.password.clone();
    }
}
