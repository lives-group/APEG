/* $Id: TestRSA_OAEP.java,v 1.4 2003/02/20 02:45:16 gelderen Exp $
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


import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import cryptix.jce.provider.rsa.*;



/**
 */
public final class TestRSA_OAEP extends Test {

    private static final String PROVIDER = "CryptixCrypto";

    private static final String KALG = "RSA";

    private static final String ALG = "RSAES-OAEP-SHA1";

    private static int SIZE = 768;


    private static final String
        N = "a8b3b284af8eb50b387034a860f146c4" +
            "919f318763cd6c5598c8ae4811a1e0ab" +
            "c4c7e0b082d693a5e7fced675cf46685" +
            "12772c0cbc64a742c6c630f533c8cc72" +
            "f62ae833c40bf25842e984bb78bdbf97" +
            "c0107d55bdb662f5c4e0fab9845cb514" +
            "8ef7392dd3aaff93ae1e6b667bb3d424" +
            "7616d4f5ba10d4cfd226de88d39f16fb",
        E = "010001",
        D = "53339cfdb79fc8466a655c7316aca85c" +
            "55fd8f6dd898fdaf119517ef4f52e8fd" +
            "8e258df93fee180fa0e4ab29693cd83b" +
            "152a553d4ac4d1812b8b9fa5af0e7f55" +
            "fe7304df41570926f3311f15c4d65a73" +
            "2c483116ee3d3d2d0af3549ad9bf7cbf" +
            "b78ad884f84d5beb04724dc7369b31de" +
            "f37d0cf539e9cfcdd3de653729ead5d1",
        P = "d32737e7267ffe1341b2d5c0d150a81b" +
            "586fb3132bed2f8d5262864a9cb9f30a" +
            "f38be448598d413a172efb802c21acf1" +
            "c11c520c2f26a471dcad212eac7ca39d",
        Q = "cc8853d1d54da630fac004f471f281c7" +
            "b8982d8224a490edbeb33d3e3d5cc93c" +
            "4765703d1dd791642f1f116a0dd852be" +
            "2419b2af72bfe9a030e860b0288b5d77",
        dP= "0e12bf1718e9cef5599ba1c3882fe804" +
            "6a90874eefce8f2ccc20e4f2741fb0a3" +
            "3a3848aec9c9305fbecbd2d76819967d" +
            "4671acc6431e4037968db37878e695c1",
        dQ= "95297b0f95a2fa67d00707d609dfd4fc" +
            "05c89dafc2ef6d6ea55bec771ea33373" +
            "4d9251e79082ecda866efef13c459e1a" +
            "631386b7e354c899f5f112ca85d71583",
        qI= "4f456c502493bdc0ed2ab756a3a6ed4d" +
            "67352a697d4216e93212b127a63d5411" +
            "ce6fa98d5dbefd73263e372814274381" +
            "8166ed7dd63687dd2a8ca1d2f4fbd8e1",

        PT = "6628194e12073db03ba94cda9ef95323" +
             "97d50dba79b987004afefe34",
        SD = "18b776ea21069d69776a33e96bad48e1" +
             "dda0a5ef",
        CT = "354fe67b4a126d5d35fe36c777791a3f" +
             "7ba13def484e2d3908aff722fad468fb" +
             "21696de95d0be911c2d3174f8afcc201" +
             "035f7b6d8e69402de5451618c21a535f" +
             "a9d7bfc5b8dd9fc243f8cf927db31322" +
             "d6e881eaa91a996170e657a05a266426" +
             "d98c88003f8477c1227094a0d9fa1e8c" +
             "4024309ce1ecccb5210035d47ac72e8a";


    public TestRSA_OAEP() {
        super("RSAES-OAEP");
    }
  

    protected void doIt() throws Exception {
        doTest1();
        doTest2();
    }


    protected void doTest1() throws Exception {

        beginTest("Test Vectors");

        RSAPublicKey pub = new RSAPublicKey() {
            public BigInteger getModulus() {
                return new BigInteger(1, Util.hexFromString(N));
            }
            public BigInteger getPublicExponent() {
                return new BigInteger(1, Util.hexFromString(E));
            }
            public String getAlgorithm() { return "RSA"; }
            public String getFormat() { return "RSA"; }
            public byte[] getEncoded() { return null; }
        };

        RSAPrivateKey priv = new RSAPrivateKey() {
            public BigInteger getModulus() {
                return new BigInteger(1, Util.hexFromString(N));
            }
            public BigInteger getPrivateExponent() {
                return new BigInteger(1, Util.hexFromString(D));
            }
            public String getAlgorithm() { return "RSA"; }
            public String getFormat() { return "RSA"; }
            public byte[] getEncoded() { return null; }
        };

        SecureRandom rng = new InsecureRandom(Util.hexFromString(SD));

        byte[] pt = Util.hexFromString(PT);

        Cipher c = Cipher.getInstance(ALG, PROVIDER);
        c.init(Cipher.ENCRYPT_MODE, pub, rng);
        byte[] ct = c.doFinal(pt);

        c.init(Cipher.DECRYPT_MODE, priv, rng);
        byte[] pt1 = c.doFinal(ct);

        passIf(Util.areEqual(pt, pt1));
    }


    protected void doTest2() throws Exception {
        int ROUNDS = 100;
        beginTest(ROUNDS + " Random Keys");

        for(int i=0; i<ROUNDS; i++)
            cryptWithRandomKey();

        passIf(true);
    }
            

    private void cryptWithRandomKey() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KALG, PROVIDER);
        kpg.initialize(SIZE);

        KeyPair kp = kpg.genKeyPair();
        RSAPrivateKey priv = (RSAPrivateKey)kp.getPrivate();
        RSAPublicKey pub = (RSAPublicKey)kp.getPublic();

        Cipher c = Cipher.getInstance(ALG, PROVIDER);
        c.init(Cipher.ENCRYPT_MODE, pub);
        byte[] pt = Util.hexFromString(PT);
        byte[] ct = c.doFinal(pt);

        c = Cipher.getInstance(ALG, PROVIDER);
        c.init(Cipher.DECRYPT_MODE, priv);
        byte[] pt1 = c.doFinal(ct);

        if(!Util.areEqual(pt, pt1))
            throw new Exception("Failed!");
    }
}
