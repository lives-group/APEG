/* $Id: SecretKey.java,v 1.6 2000/01/15 03:25:04 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto;


import java.security.Key;


/**
 * This interface is implemented by all secret keys. It does not specify any
 * methods, it is implemented to provide type safety.
 *
 * @version $Revision: 1.6 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public interface SecretKey extends Key
{
}
