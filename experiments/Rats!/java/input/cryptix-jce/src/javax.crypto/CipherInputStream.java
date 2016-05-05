/* $Id: CipherInputStream.java,v 1.7 2000/07/31 13:21:35 pw Exp $
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

import java.io.InputStream;
import java.io.FilterInputStream;
import java.io.IOException;


/**
 * <B>Please read the comments in the source together
 * with the JCE API documentation from Sun.</B>
 *
 * @version $revision: $
 * @author Paul Waserbrot (pw@cryptix.org)
 */
public class CipherInputStream extends FilterInputStream
{
    private int bufBegin = 0;
    private int bufEnd = 0;

    private boolean isClosed = false;

    private Cipher c;

    private byte encBuf[];
    private byte rawBuf[] = null;

    private InputStream is;

// Constructors
// ..........................................................................

    public CipherInputStream(InputStream is, Cipher c)
    {
        super(is);
        this.c = c;
        this.is = is;
        encBuf = new byte[1024];
    }

    protected CipherInputStream(InputStream is)
    {
        this(is, new NullCipher());
    }

// Public methods
// ..........................................................................

    public int read() throws IOException
    {
        // Do buffering if needed.
        if (bufBegin >= bufEnd)
        {
            // Block and do more buffering
            // and if no more data return -1
            if (doBuffering()== -1)
                    return -1;
        }
        return (int) (rawBuf[bufBegin++] & 0xFF);
    }


    public int read(byte[] b) throws IOException
    {
        return read(b,0,b.length);
    }

    public int read(byte[] b, int off, int len)
    throws IOException
    {
        int l;

        // Do buffering if needed.
        if (bufBegin >= bufEnd)
        {
          // Block and do more buffering
          // and if no more data return -1
            if (doBuffering()== -1)
                    return -1;
        }

        // Special case, if len<=0, return 0.
        if (len <= 0)
            return 0;
        else
        { // Ok, copy the accurate length to the buffer
            if ((l = bufEnd - bufBegin)> len)
                    l = len;

            if (b != null)
                    for (int i = 0; i < l; i++)
                        b[off + i] = rawBuf[bufBegin + i];
        }
        bufBegin += l;
        return l;
    }


    public long skip(long n) throws IOException
    {
        long i;

        if ((i = (long) (bufEnd - bufBegin)) <= 0 || n <= 0)
            return 0;

        if (i < n)
            i = n;

        bufBegin += (int) i;
        return i;
    }


    public int available() throws IOException
    {
        return bufEnd - bufBegin;
    }


    public void close() throws IOException
    {
        // Close the InputStream and retrieve the things that might be
        // left in the encoded buffer.
        is.close();
        doClose();
        return;
    }


    public boolean markSupported()
    {
        return false;
    }


// Private methods
// ..........................................................................

    private int doBuffering() throws IOException
    {
        /* 
         * Reads from the InputStream into encBuf. It then decrypts the buffer 
         * and stores in the buffer rawBuf.
         * If no data exists in the stream, do additional clean-up and if we
         * already have done clean-up return -1;
         */
        if (isClosed)
            return -1;

        int len = is.read(encBuf);

        if (len < 0)
            doClose(); // InputStream empty, do clean-up.
        else
        {
            try
            {
                rawBuf = c.update(encBuf, 0, len);
            }
            catch(IllegalStateException e)
            {
                throw new RuntimeException("PANIC: Internal error!"); // Should never be reached.
            }
        }
        len = rawBuf.length;
        bufBegin = 0;
        bufEnd = len;

        return len;
    }


    private void doClose()
    {
        // Call doFinal and mark InputStream as closed. The user must call
        // close() separatly for the "real" close of InputStream.
        isClosed = true;
        try
        {
            rawBuf = c.doFinal();
        }
        catch(IllegalStateException e)
        {
            throw new RuntimeException("PANIC: Internal error!"); // Should never be reached.
        }
        catch(IllegalBlockSizeException e)
        {
            throw new RuntimeException("PANIC: Internal error!"); // Should never be reached.
        }
        catch(BadPaddingException e)
        {
            throw new RuntimeException("PANIC: Internal error!"); // Should never be reached.
        }
        return;
    }
}
