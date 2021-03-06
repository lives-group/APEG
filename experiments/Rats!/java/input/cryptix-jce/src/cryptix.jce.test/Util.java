/* $Id: Util.java,v 1.4 2000/01/20 14:59:35 gelderen Exp $
 *
 * Copyright (C) 1995-1999 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


abstract class Util
{
    /**
     * Returns a byte array from a string of hexadecimal digits.
     */
    static byte[] hexFromString(String hex)
    {
        int len = hex.length();
        byte[] buf = new byte[((len + 1) / 2)];

        int i = 0, j = 0;
        if ((len % 2) == 1)
            buf[j++] = (byte) fromDigit(hex.charAt(i++));

        while (i < len)
        {
            buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) |
                                fromDigit(hex.charAt(i++)));
        }
        return buf;
    }


    /**
     * Returns the number from 0 to 15 corresponding to the hex digit <i>ch</i>.
     */
    static int fromDigit(char ch)
    {
        if (ch >= '0' && ch <= '9')
            return ch - '0';
        if (ch >= 'A' && ch <= 'F')
            return ch - 'A' + 10;
        if (ch >= 'a' && ch <= 'f')
            return ch - 'a' + 10;

        throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
    }


    /**
     * Compares two byte arrays for equality.
     *
     * @return true if the arrays have identical contents
     */
    static boolean areEqual (byte[] a, byte[] b)
    {
        int aLength = a.length;
        if (aLength != b.length)
            return false;
        for (int i = 0; i < aLength; i++)
            if (a[i] != b[i])
                return false;
        return true;
    }


    /**
     * Returns a string of hexadecimal digits from a byte array. Each
     * byte is converted to 2 hex symbols.
     */
    static String toString( byte[] ba )
    {
        int length = ba.length;
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; )
        {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[ k        & 0x0F];
        }
        return new String(buf);
    }

    private static final char[] HEX_DIGITS =
    {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
}