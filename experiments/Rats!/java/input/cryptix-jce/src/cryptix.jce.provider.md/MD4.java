/* $Id: MD4.java,v 1.8 2001/06/25 15:39:55 gelderen Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject to
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */

package cryptix.jce.provider.md;


/**
 * MD4 message digest algorithm.
 *
 * <ul>
 *   <li> Ronald L. Rivest,
 *        "<a href="http://www.ietf.org/rfc/rfc1320.html">
 *        The MD4 Message-Digest Algorithm</a>",
 *        IETF RFC-1320 (informational).</li>
 * </ul>
 *
 * @version $Revision: 1.8 $
 * @author  Raif S. Naffah
 * @author  Jeroen C. van Gelderen
 */
public final class MD4
extends PaddingMD
implements Cloneable
{

// Constants
//...........................................................................

    /** Size (in bytes) of this hash */
    private static final int HASH_SIZE = 16;



// Instance variables
//...........................................................................

    /** 4 32-bit words (interim result) */
    private int[] context = new int[4];

    /** 512 bits work buffer = 16 x 32-bit words */
    private int[] X = new int[16];



// Constructors
//...........................................................................

    public MD4()
    {
        super(HASH_SIZE, PaddingMD.MODE_MD);
        coreReset();
    }

    private MD4(MD4 src) {
        super(src);
        this.context = (int[])src.context.clone();
        this.X       = (int[])src.X.clone();
    }

    public Object clone()
    {
        return new MD4(this);
    }


// Concreteness
//...........................................................................

    protected void coreDigest(byte[] buf, int off)
    {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                buf[off+(i * 4 + j)] = (byte)(context[i] >>> (8 * j));
    }


    protected void coreReset()
    {
        // initial values of MD4 i.e. A, B, C, D
        // as per rfc-1320; they are low-order byte first
        context[0] = 0x67452301;
        context[1] = 0xEFCDAB89;
        context[2] = 0x98BADCFE;
        context[3] = 0x10325476;
    }


    protected void coreUpdate(byte[] block, int offset)
    {
        // encodes 64 bytes from input block into an
        // array of 16 32-bit entities.
        for (int i = 0; i < 16; i++)
            X[i] = (block[offset++] & 0xFF)       |
                   (block[offset++] & 0xFF) <<  8 |
                   (block[offset++] & 0xFF) << 16 |
                   (block[offset++] & 0xFF) << 24;

        int A = context[0];
        int B = context[1];
        int C = context[2];
        int D = context[3];

        A = FF(A, B, C, D, X[ 0],  3);
        D = FF(D, A, B, C, X[ 1],  7);
        C = FF(C, D, A, B, X[ 2], 11);
        B = FF(B, C, D, A, X[ 3], 19);
        A = FF(A, B, C, D, X[ 4],  3);
        D = FF(D, A, B, C, X[ 5],  7);
        C = FF(C, D, A, B, X[ 6], 11);
        B = FF(B, C, D, A, X[ 7], 19);
        A = FF(A, B, C, D, X[ 8],  3);
        D = FF(D, A, B, C, X[ 9],  7);
        C = FF(C, D, A, B, X[10], 11);
        B = FF(B, C, D, A, X[11], 19);
        A = FF(A, B, C, D, X[12],  3);
        D = FF(D, A, B, C, X[13],  7);
        C = FF(C, D, A, B, X[14], 11);
        B = FF(B, C, D, A, X[15], 19);

        A = GG(A, B, C, D, X[ 0],  3);
        D = GG(D, A, B, C, X[ 4],  5);
        C = GG(C, D, A, B, X[ 8],  9);
        B = GG(B, C, D, A, X[12], 13);
        A = GG(A, B, C, D, X[ 1],  3);
        D = GG(D, A, B, C, X[ 5],  5);
        C = GG(C, D, A, B, X[ 9],  9);
        B = GG(B, C, D, A, X[13], 13);
        A = GG(A, B, C, D, X[ 2],  3);
        D = GG(D, A, B, C, X[ 6],  5);
        C = GG(C, D, A, B, X[10],  9);
        B = GG(B, C, D, A, X[14], 13);
        A = GG(A, B, C, D, X[ 3],  3);
        D = GG(D, A, B, C, X[ 7],  5);
        C = GG(C, D, A, B, X[11],  9);
        B = GG(B, C, D, A, X[15], 13);

        A = HH(A, B, C, D, X[ 0],  3);
        D = HH(D, A, B, C, X[ 8],  9);
        C = HH(C, D, A, B, X[ 4], 11);
        B = HH(B, C, D, A, X[12], 15);
        A = HH(A, B, C, D, X[ 2],  3);
        D = HH(D, A, B, C, X[10],  9);
        C = HH(C, D, A, B, X[ 6], 11);
        B = HH(B, C, D, A, X[14], 15);
        A = HH(A, B, C, D, X[ 1],  3);
        D = HH(D, A, B, C, X[ 9],  9);
        C = HH(C, D, A, B, X[ 5], 11);
        B = HH(B, C, D, A, X[13], 15);
        A = HH(A, B, C, D, X[ 3],  3);
        D = HH(D, A, B, C, X[11],  9);
        C = HH(C, D, A, B, X[ 7], 11);
        B = HH(B, C, D, A, X[15], 15);

        context[0] += A;
        context[1] += B;
        context[2] += C;
        context[3] += D;
    }


// The basic MD4 atomic functions.
// ..........................................................................

    private int FF (int a, int b, int c, int d, int x, int s)
    {
        int t = a + ((b & c) | (~b & d)) + x;
        return t << s | t >>> (32 - s);
    }

    private int GG (int a, int b, int c, int d, int x, int s)
    {
        int t = a + ((b & (c | d)) | (c & d)) + x + 0x5A827999;
        return t << s | t >>> (32 - s);
    }

    private int HH (int a, int b, int c, int d, int x, int s)
    {
        int t = a + (b ^ c ^ d) + x + 0x6ED9EBA1;
        return t << s | t >>> (32 - s);
    }
}