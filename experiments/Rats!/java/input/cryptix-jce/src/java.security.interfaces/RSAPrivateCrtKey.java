/* $Id: RSAPrivateCrtKey.java,v 1.4 2000/02/13 20:48:24 gelderen Exp $
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


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public interface RSAPrivateCrtKey
    extends RSAPrivateKey
{
    BigInteger getPublicExponent();
    BigInteger getPrimeP();
    BigInteger getPrimeQ();
    BigInteger getPrimeExponentP();
    BigInteger getPrimeExponentQ();
    BigInteger getCrtCoefficient();
}
