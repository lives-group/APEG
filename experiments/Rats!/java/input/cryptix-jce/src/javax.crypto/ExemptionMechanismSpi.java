/* $Id: ExemptionMechanismSpi.java,v 1.3 2000/06/09 20:46:00 pw Exp $
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
import java.security.InvalidKeyException;
import java.security.AlgorithmParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.security.InvalidAlgorithmParameterException;

/**
 * Abstract class for communication between the JCE and the provider.
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */

public abstract class ExemptionMechanismSpi
  extends Object {

  public ExemptionMechanismSpi() {}

  protected abstract int engineGetOutputSize(int inputLen);

  protected abstract void engineInit(Key key)
  throws InvalidKeyException, ExemptionMechanismException;


  protected abstract void engineInit(Key key, AlgorithmParameterSpec params)
  throws InvalidKeyException, InvalidAlgorithmParameterException,
  ExemptionMechanismException;
    
  protected abstract void engineInit(Key key, AlgorithmParameters params)
  throws InvalidKeyException, InvalidAlgorithmParameterException,
  ExemptionMechanismException;

  protected abstract byte[] engineGenExemptionBlob()
  throws ExemptionMechanismException;

  protected abstract int engineGenExemptionBlob(byte[] output,
						int outputOffset)
  throws ShortBufferException, ExemptionMechanismException;
}

