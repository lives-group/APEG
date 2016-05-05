/* $Id: NullCipher.java,v 1.5 2000/01/15 03:25:04 gelderen Exp $
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
 * This class implements the identity cipher. Encrypting and decrypting do
 * not alter the data. The NullCipher has a block size of 1.
 * <p>
 * <strong>Note:</strong> we consider using this class deprecated as it breaks
 * API consistency. Use <code>Cipher.getInstance("Null")</code> instead and
 * hope that Sun will deprecate this interface soon.
 *
 * @version $Revision: 1.5 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public class NullCipher extends Cipher
{
    /**
     * Construct a NullCipher.
     * <p>
     * <strong>Note:</strong> we consider using this class deprecated as it
     * breaks API consistency. Use <code>Cipher.getInstance("Null")</code>
     * instead and hope that Sun will deprecate this interface soon.
     */
    public NullCipher() {
        super(new NullCipherSpi(), null, null);
    }
}