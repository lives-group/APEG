/* $Id: Test.java,v 1.24 2003/03/24 01:58:28 gelderen Exp $
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


public abstract class Test
{
    private static final String
        CLASS_PREFIX = "cryptix.jce.test.Test",
        VERSION      = "$Revision: 1.24 $";

    private static boolean verbose=true;

    private static String[] TESTS =
    {
        "DSA",
        
        "MD2", "MD4", "MD5", "RIPEMD128", "RIPEMD160", 
        "SHA0", "SHA1", "SHA256", "SHA384", "SHA512", "Tiger",
        
        "Blowfish", "CAST5", "DES","IDEA", "MARS", "Null", "RC2", "RC4", "RC6",
        "Rijndael", "Serpent", "SKIPJACK", "Square", "TripleDES",
        "TripleDESInnerCBC", "Twofish",
        
        "RSA_ECB_PKCS1", "RSA_OAEP", "ElGamal",
        
	    "ModeAndPadding", "AlgorithmParameters", "CipherStream", "SealedObject"
    };

    private static String currentTest;

    public static void main(String[] argv)
    {
        printVerbose("Cryptix JCE Test Driver - " + VERSION + 
                     " - http://www.cryptix.org/");
        printVerbose("");
        printVerbose("Platform: JDK " +
                     System.getProperty("java.version") +
                     " (" + System.getProperty("java.vendor") + ") on " +
                     System.getProperty("os.name") +
                     " ("+ System.getProperty("os.version") + ", " +
                     System.getProperty("os.arch") + ")" );
        printVerbose("Classpath: " + System.getProperty("java.class.path") );
        printVerbose("");

        if( argv.length==0 )
        {
            System.out.println("Usage: Test <testname>  or  Test All");
            System.exit(0);
        }

        try
        {
            java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto());
            String[] tests = (argv[0].equalsIgnoreCase("All")) ? TESTS : argv;
            for( int i=0; i<tests.length; i++ )
            {
                Test t = getTest( tests[i] );
                t.doIt();
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Test not found.");
            e.printStackTrace();
            System.exit(1);
        }
        catch(Throwable t)
        {
            System.out.println("Failed");
            t.printStackTrace();
            System.exit(1);
        }
        System.exit(10);
    }

    protected Test(String name)
    {
        currentTest = name;
    }

    private static void printVerbose(String s)
    {
        if(verbose) System.out.println(s);
    }

    private static Test getTest(String name)
    throws Exception
    {
        Class c = Class.forName( CLASS_PREFIX+name );
        Object o = c.newInstance();
        return (Test)o;
    }


    protected void beginTest(String s)
    {
        System.out.print("Testing " + currentTest + " (" + s + ")... ");
    }


    protected void passIf(boolean success)
    {
        if( !success ) throw new RuntimeException("Test failed");
        System.out.println("Ok");
    }


    protected abstract void doIt() throws Exception;
}

