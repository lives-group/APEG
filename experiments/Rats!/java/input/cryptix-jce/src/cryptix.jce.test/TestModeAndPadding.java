/* $Id: TestModeAndPadding.java,v 1.7 2001/08/06 18:06:51 edwin Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package cryptix.jce.test;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;


/**
 * <B>This is a testclass for Modes and Padding
 * using DES as algorithm as default</B>
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 * @version $Revision: 1.7 $
 */
final class TestModeAndPadding
extends Test
{
    private static final String name = "ModeAndPadding";

    private final String 
        s1 = "ThisIsSecretSecretMessageInText!",
        s2 = "ThisIsSecretMessage";
    
    private Cipher    c, d;
    private SecretKey sk;
    private String    algo;
    private int       KEY_SIZE;

    protected TestModeAndPadding (String algo, int KEY_SIZE)
    {
        super(name);
        this.algo = algo;
        this.KEY_SIZE = KEY_SIZE;
    }


    protected TestModeAndPadding ()
    {
        super(name);
        this.algo = "DES";
        this.KEY_SIZE = 56;
    }

    protected void doIt()
    throws Exception
    {
        init();

        // ECB
        beginTest("ECB mode with no padding");
        changeMode("ECB", "NONE");
        passIf(test1(c,d,s1) && test2(c,d,s1));
        beginTest("ECB mode with PKCS5 padding");
        changeMode("ECB", "PKCS5");
        passIf(test1(c,d,s2) && test2(c,d,s2));

        // CBC
        beginTest("CBC mode with no padding");
        changeMode("CBC", "NoPadding");
        passIf(test1(c,d,s1) && test2(c,d,s1));
        beginTest("CBC mode with PKCS5 padding");
        changeMode("CBC", "PKCS5");
        passIf(test1(c,d,s2) && test2(c,d,s2));

        // OFB
        beginTest("OFB mode with no padding");
        changeMode("OFB", "NONE");
        passIf(test1(c,d,s2) && test2(c,d,s2));
        beginTest("OFB mode with PKCS5 padding");
        changeMode("OFB", "PKCS5");
        passIf(test1(c,d,s2) && test2(c,d,s2));

        // CFB
        beginTest("CFB mode with no padding");
        changeMode("CFB56", "None");
        passIf(test1(c,d,s2) && test2(c,d,s2));
        beginTest("CFB mode with PKCS5 padding");
        changeMode("CFB", "PKCS5");
        passIf(test1(c,d,s2) && test2(c,d,s2));

        return;
    }

    private void init ()
    throws Exception
    {
        // Generate key
        KeyGenerator kg = KeyGenerator.getInstance(algo);
        kg.init(KEY_SIZE);
        sk = kg.generateKey();

        return;
    }

    private void changeMode (String mode, String padding)
    throws Exception
    {
        // init the ciphers
        String alg = algo +"/"+ mode +"/"+ padding;
        c = Cipher.getInstance(alg);
        d = Cipher.getInstance(alg);

        c.init(Cipher.ENCRYPT_MODE, sk);
        if (mode.equals("ECB"))
            d.init(Cipher.DECRYPT_MODE, sk);
        else
            d.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(c.getIV()));

        return;
    }

    private boolean test1 (Cipher c, Cipher d, String s)
    throws Exception
    {
        // Test the update + doFinal
        byte [] e = c.update(s.getBytes());
        byte [] f = c.doFinal();
        byte [] g = new byte[e.length + f.length];
        System.arraycopy(e,0,g,0,e.length);
        System.arraycopy(f,0,g,e.length,f.length);

        return s.equals(new String(d.doFinal(g)));
    }

    private boolean test2 (Cipher c, Cipher d, String s)
    throws Exception
    {
        // Test with doFinal only
        byte [] e = c.doFinal(s.getBytes());
        return s.equals(new String(d.doFinal(e)));
    }
}
