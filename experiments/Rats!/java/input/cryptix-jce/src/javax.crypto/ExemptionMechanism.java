/* $Id: ExemptionMechanism.java,v 1.4 2000/06/09 20:46:00 pw Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto;

import java.security.Key;
import java.security.Provider;
import java.security.AlgorithmParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidAlgorithmParameterException;

/**
 * This class is used for granting applications and applets stronger
 * encryption capabilities.
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */

public class ExemptionMechanism
    extends Object {

    private final ExemptionMechanismSpi spi;
    private final Provider provider;
    private final String mechanism;
    private boolean isInitialized = false;

    protected ExemptionMechanism(ExemptionMechanismSpi exmechSpi,
				 Provider provider, String mechanism) {
	this.spi = exmechSpi;
	this.provider = provider;
	this.mechanism = mechanism;
    }

    public final String getName() {
	return mechanism;
    }

    public static final ExemptionMechanism getInstance(String mechanism)
    throws NoSuchAlgorithmException {
	/* FIXME: I noticed that there are no new name in the JCE API
	 * Specification & Reference! Wait and see if Sun releases some 
	 * more info on this, but until then be smart and assume. =) (pw)
	 */
	Object [] o = Support.getImplementation("ExemptionMechanism",
					       mechanism);
	return new ExemptionMechanism((ExemptionMechanismSpi)o[0], 
				      (Provider)o[1], mechanism);
    }

    public static final ExemptionMechanism getInstance(String mechanism,
						       String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException {
	/* FIXME: I noticed that there are no new name in the JCE API
	 * Specification & Reference! Wait and see if Sun releases some 
	 * more info on this, but until then be smart and assume. =) (pw)
	 */
	Object [] o = Support.getImplementation("ExemptionMechanism",
					       mechanism, provider);
	return new ExemptionMechanism((ExemptionMechanismSpi)o[0], 
				      (Provider)o[1], mechanism);
    }

    public final java.security.Provider getProvider() {
	return provider;
    }
    
    public final boolean isCryptoAllowed(Key key)
    throws ExemptionMechanismException {
	/*
	 * Returns whether the result blob has been generated successfully 
	 * by this exemption mechanism. 
	 *
	 * The method also makes sure that the key passed in is the same as 
	 * the one this exemption mechanism used in initializing and 
	 * generating phases.
	 * Parameters:
         *   key - the key the crypto is going to use.
	 * Returns:
         *   whether the result blob of the same key has been generated 
	 *   successfully by this exemption mechanism.
	 * Throws:
         *   ExemptionMechanismException - if problem(s) encountered while 
	 *   determining whether the result blob has been generated 
	 *   successfully by this
         *   exemption mechanism object.
	 */
	throw new RuntimeException("Method not yet implemented");
    }
    
    public final int getOutputSize(int inputLen)
    throws IllegalStateException {
	if (!isInitialized)
	    throw new IllegalStateException("ExemptionMechanism not "+
					    "initialized!");
	return this.spi.engineGetOutputSize(inputLen);
    }

    public final void init(Key key)
    throws InvalidKeyException, ExemptionMechanismException {
	spi.engineInit(key);
	isInitialized = true;
	return;
    }

    public final void init(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException,
    ExemptionMechanismException {
	this.spi.engineInit(key, params);
	isInitialized = true;
	return;
    }

    public final void init(Key key, AlgorithmParameters params)
    throws InvalidKeyException, InvalidAlgorithmParameterException,
    ExemptionMechanismException {
	this.spi.engineInit(key, params);
	isInitialized = true;
	return;
    }

    public final byte[] genExemptionBlob()
    throws IllegalStateException, ExemptionMechanismException {
	if (!isInitialized)
	    throw new IllegalStateException("ExemptionMechanism not "+
					    "initialized!");
	return this.spi.engineGenExemptionBlob();
    }

    public final int genExemptionBlob(byte[] output)
    throws IllegalStateException, ShortBufferException,
    ExemptionMechanismException {
	if (!isInitialized)
	    throw new IllegalStateException("ExemptionMechanism not "+
					    "initialized!");
	if (output.length < this.getOutputSize(0))
	    throw new ShortBufferException("Buffer to short!");

	return this.spi.engineGenExemptionBlob(output, 0);
    }

    public final int genExemptionBlob(byte[] output,
				      int outputOffset)
    throws IllegalStateException, ShortBufferException,
    ExemptionMechanismException {
	if (!isInitialized)
	    throw new IllegalStateException("ExemptionMechanism not "+
					    "initialized!");
	if ((output.length - outputOffset) < this.getOutputSize(0))
	    throw new ShortBufferException("Buffer to short!");

	return this.spi.engineGenExemptionBlob(output, outputOffset);
    }

    protected void finalize() {
	/*
	 * Ensures that the key stored away by this ExemptionMechanism 
	 * object will be wiped out when there are no more references to it.
	 * Overrides:
         *   finalize in class java.lang.Object
	 */

	/* Well, what to do here beats me.. ..i don't know (yet) why we 
	 * need to store a key. Will do this one when the knowledge is 
	 * some what better. (pw) FIXME: 
	 */
	throw new RuntimeException("Method not yet implemented!");
    }
}
