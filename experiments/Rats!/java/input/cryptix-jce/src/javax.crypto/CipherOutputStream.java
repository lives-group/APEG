/* $Id: CipherOutputStream.java,v 1.6 2000/07/31 13:21:35 pw Exp $
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

import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

/**
 * <B>Please read the comments in the source together
 * with the JCE API documentation from Sun.</B>
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */

public class CipherOutputStream extends FilterOutputStream {

  private OutputStream os;
  
  private Cipher c;

  private byte[] encBuf;
  private byte[] ba = new byte[1];
  
  // Constructors
  public CipherOutputStream(OutputStream os, Cipher c) {
    super(os);
    this.os = os;
    this.c = c;
  }
  
  protected CipherOutputStream(OutputStream os) {
    this(os, new NullCipher());
  }
  
  // Public methods
  public void write(int b)
    throws IOException {
    ba[0] = (byte) b;
    write(ba, 0, 1);
    return;
  }

  public void write(byte[] b)
    throws IOException {
    write(b, 0, b.length);
    return;
  }
  
  public void write(byte[] b, int off, int len)
    throws IOException {
    if ((encBuf = c.update(b, off, len)) != null)
      os.write(encBuf);
    return;
  }

  public void flush()
    throws IOException {
    os.flush();
    return;
  }
  
  public void close()
    throws IOException {

    try {
      encBuf = c.doFinal();
    } catch (IllegalStateException e) {
        throw new RuntimeException("PANIC: Internal error!"); //Should never be reached.
    } catch (IllegalBlockSizeException e) {
        throw new RuntimeException("PANIC: Internal error!"); //Should never be reached.
    } catch (BadPaddingException e) {
        throw new RuntimeException("PANIC: Internal error!"); //Should never be reached.
    }
    
    os.write(encBuf);
    os.flush();
    os.close();
    return;
  }
}
