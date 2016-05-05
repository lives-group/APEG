/* $Id: AlgorithmParametersSpi.java,v 1.2 2000/01/15 03:25:44 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security;


import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

import java.io.IOException;


/**
 * This is an abstract class. It provides the Security Provider Interface
 * for algorithm parameters.
 *
 * THIS IS UNDER DEVELOPMENT NOT TESTED YET (1999-24-12)!!!
 * As this is only an abstract class it should do.
 *
 * @author Josef Hartmann
 */
public abstract class AlgorithmParametersSpi
{
	/*
	 * This is specified in the documentation but produces only errors if it
	 * is used this way.
	 */
	// AlgorithmParametersSpi();

	/**
	 *
	 */
	protected abstract void engineInit(AlgorithmParameterSpec paramSpec)
        throws InvalidParameterSpecException;

	/**
	 *
	 */
	protected abstract void engineInit(byte[] params)
        throws IOException;

	/**
	 *
	 */
	protected abstract void engineInit(byte[] params, String format)
        throws IOException;

	/**
	 *
	 */
	protected abstract AlgorithmParameterSpec 
    engineGetParameterSpec(Class paramSpec)
        throws InvalidParameterSpecException;

	/**
	 *
	 */
	protected abstract byte[] engineGetEncoded()
        throws IOException;

	/**
	 *
	 */
	protected abstract byte[] engineGetEncoded(String format)
        throws IOException;

	/**
	 *
	 */
	protected abstract String engineToString();

}