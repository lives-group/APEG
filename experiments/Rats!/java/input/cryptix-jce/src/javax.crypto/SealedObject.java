/* $Id: SealedObject.java,v 1.6 2000/07/31 13:21:35 pw Exp $
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

/**
 * <B>Please read the comments in the source together
 * with the JCE API documentation from Sun.</B>
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OptionalDataException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.security.Key;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;

public class SealedObject extends Object
  implements Serializable {

  private byte[] encBuf;
  private byte[] encParams = null;
  private String sealAlgorithm, paramsAlgorithm;

  // Constructor
  public SealedObject(Serializable object, Cipher c)
    throws IOException, IllegalBlockSizeException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);

    try {
      oos.writeObject(object);
      oos.flush();
    } catch (InvalidClassException e) { 
      throw new IOException(e.getMessage());
    } catch (NotSerializableException e) {
      throw new IOException(e.getMessage());
    }
    
    try {
      encBuf = c.doFinal(baos.toByteArray());
    } catch (BadPaddingException e) {
         throw new RuntimeException("PANIC: Internal error!"); // Should never be reached.
   }
    
    // Store algorithm parameters
    AlgorithmParameters params = c.getParameters();
    if (params != null) {
      encParams = params.getEncoded();
      paramsAlgorithm = params.getAlgorithm();
    }
    sealAlgorithm = c.getAlgorithm();

    // Clean-up
    oos.close();
    baos.close();
  }
  
  // Public methods
  public final String getAlgorithm() {
    return sealAlgorithm;
  }

  public final Object getObject(Key key)
    throws IOException, ClassNotFoundException, 
           NoSuchAlgorithmException, InvalidKeyException {
    Cipher c = null;
    AlgorithmParameters p = null;

    if (encParams != null) {
      p.getInstance(paramsAlgorithm); 
      p.init(encParams);
    }

    try {
      c = Cipher.getInstance(sealAlgorithm);
      if (p != null)
        c.init(Cipher.DECRYPT_MODE, key, p);
      else
        c.init(Cipher.DECRYPT_MODE, key);
    } catch (InvalidAlgorithmParameterException e){
      throw new InvalidKeyException(e.getMessage());
    } catch (NoSuchPaddingException e) {
      throw new NoSuchAlgorithmException(e.getMessage());
    }
    return deCrypt(c);
  }  

  public final Object getObject(Key key, String provider)
    throws IOException, ClassNotFoundException, NoSuchAlgorithmException,
           NoSuchProviderException, InvalidKeyException {
    Cipher c = null;
    AlgorithmParameters p = null;

    if (encParams != null) {
      p.getInstance(paramsAlgorithm, provider); 
      p.init(encParams);
    }

    try {
      c = Cipher.getInstance(sealAlgorithm);
      if (p != null)
        c.init(Cipher.DECRYPT_MODE, key, p);
      else
        c.init(Cipher.DECRYPT_MODE, key);
    } catch (InvalidAlgorithmParameterException e){ 
      throw new InvalidKeyException(e.getMessage());
    } catch (NoSuchPaddingException e) {
      throw new NoSuchAlgorithmException(e.getMessage());
    }

    return deCrypt(c);
  }

  public final Object getObject(Cipher c)
    throws IOException, ClassNotFoundException,
           IllegalBlockSizeException, BadPaddingException {
    /* This is basically the same as in deCrypt(), but we don't catch 
     * the exceptions from doFinal().
     */
    Object o = null;
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    
    bais = new ByteArrayInputStream(c.doFinal(encBuf));
    try {
      ois = new ObjectInputStream(bais);
      o = ois.readObject();
    } catch (StreamCorruptedException e) { 
      throw new IOException(e.getMessage());
    } catch (OptionalDataException e) {
      throw new IOException(e.getMessage());
    } catch (InvalidClassException e) {
      throw new IOException(e.getMessage());
    } 
    // Clean-up
    ois.close();
    bais.close();
    
    return o;
  }

  // Private method
  private Object deCrypt(Cipher c) 
    throws ClassNotFoundException, IOException {
    /* This function decrypts encBuf using the cipher provided as an argument.
     * It then de-serializes the rawBuf using a ByteArrayInputStream and an
     * ObjectInputStream.
     */
    Object o = null;
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    
    try {
      bais = new ByteArrayInputStream(c.doFinal(encBuf));
    } catch (BadPaddingException e) {
      throw new IOException(e.getMessage());
    } catch (IllegalBlockSizeException e) {
      throw new IOException(e.getMessage());
    }

    try {
      ois = new ObjectInputStream(bais);
      o = ois.readObject();
    } catch (StreamCorruptedException e) { 
      throw new IOException(e.getMessage());
    } catch (OptionalDataException e) {
      throw new IOException(e.getMessage());
    } catch (InvalidClassException e) {
      throw new IOException(e.getMessage());
    } 
    // Clean-up
    ois.close();
    bais.close();
    
    return o;
 }
}
