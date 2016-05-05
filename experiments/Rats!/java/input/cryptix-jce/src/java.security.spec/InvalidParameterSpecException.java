/* $Id: InvalidParameterSpecException.java,v 1.2 2000/01/15 03:25:23 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.spec;


import java.security.GeneralSecurityException;


/**
 * @author Josef Hartmann
 */
public class InvalidParameterSpecException extends GeneralSecurityException
{
	public InvalidParameterSpecException()
	{
		super();
	}


	public InvalidParameterSpecException(String msg)
	{
		super(msg);
	}
}