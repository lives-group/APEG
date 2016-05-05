/* $Id: RSAPublicKey.java,v 1.4 2000/02/28 20:23:14 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.interfaces;


import java.math.BigInteger;
import java.security.PublicKey;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public interface RSAPublicKey extends PublicKey
{
    BigInteger getModulus();
    BigInteger getPublicExponent();
}
