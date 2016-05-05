/* $Id: RSAPrivateKeyCryptix.java,v 1.4 2001/11/18 02:35:22 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.provider.pk;


import cryptix.jce.util.MPIOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;


/**
 * @version $Revision: 1.4 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public final class RSAPrivateKeyCryptix implements RSAPrivateKey
{
    private final BigInteger n, d;
    
    
    public RSAPrivateKeyCryptix(BigInteger n, BigInteger d)
    {
        this.n = n;
        this.d = d;
    }
    
    
    public BigInteger getModulus()
    {
        return this.n;
    }
    
    
    public BigInteger getPrivateExponent()
    {
        return this.d;
    }
    
    
    public String getAlgorithm()
    {
        return "RSA";
    }
    
    
    public String getFormat()
    {
        return "Cryptix";
    }
    
    
    public byte[] getEncoded()
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MPIOutputStream       mos  = new MPIOutputStream(baos);
            mos.write(this.n);
            mos.write(this.d);
            mos.flush();
            mos.close();
            return baos.toByteArray();
        }
        catch(IOException e)
        {
            throw new RuntimeException("PANIC");
        }
    }
}
