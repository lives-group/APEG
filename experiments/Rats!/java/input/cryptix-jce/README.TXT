
Cryptix JCE Provider (for JDK 1.4, 1.3, 1.2 and 1.1.x)


DESCRIPTION
-----------

The Cryptix JCE Provider is a cryptography plugin for Sun Microsystems'
Java Cryptography Extensions (JCE) framework.


OVERVIEW
--------

The Cryptix JCE includes:


FEATURES
--------

Digital Signatures:

  RSASSA-PSS (with choice of MD2, MD4, MD5, RIPEMD-128, RIPEMD-160, SHA-1,
              SHA-256, SHA-384, SHA-512 or Tiger hash algorithms)

  NOTE: The hash function you choose when you instantiate RSASSA-PSS is used
        for both message hashing and for the Mask Generation Function (MGF1).
        It is presently not possible to use one hash function for message
        hashing and another for MGF1.

  RSASSA-PKCS1 (with MD4, MD5, RIPEMD-128, RIPEMD-160 or SHA-1)

Hash Functions:

  MD2
  MD4
  MD5
  RIPEMD-128
  RIPEMD-160
  SHA-0
  SHA-1
  SHA-256
  SHA-384
  SHA-512
  Tiger

Public Key Crypto:

  RSASSA-OAEP (with choice of MD2, MD4, MD5, RIPEMD-128, RIPEMD-160, SHA-1,
               SHA-256, SHA-384, SHA-512 or Tiger hash algorithms)

  RSASSA-PKCS1

Secure Random Numbers (RNG)

  On UNIX systems with the CryptixRandom provider installed, SecureRandom makes
  use of the system RNG (/dev/random) if present. This is essential because the
  RNG built into the JVM is slow and possibly not as secure.

  Note that Apple got it right on their port of the 1.3 JVM on OS X. The Apple
  port uses /dev/random by default. This functionality can be managed by
  changing the securerandom.source property in the java.security file.
  
Symmetric Ciphers:

  Each of the ciphers listed below is available in CBC, CFB, ECB, OFB and
  OpenPGP CFB modes with a choice of 'no padding' or PKCS #5 padding.

  Name         Free?    Key Size       Block    Comments
  ----------------------------------------------------------------------------
  AES          yes      128/192/256    128      Successor of DES, recommended
                                                for new applications
  Blowfish     yes      32 - 448       64       Very low key agility :-(
  CAST5        yes      ?
  DES          yes      56             64       Generally considered insecure
  IDEA         NO       128            64
  MARS         NO       128/192/256    128
  Null         yes      n/a            n/a?     Offers no security
  RC2          ?
  RC4          yes                     n/a
  RC6          NO       128/192/256    128
  Rijndael     yes      128/192/256    128      Now known as AES
  SKIPJACK     yes      80             64       Formerly classified.
  Serpent      yes      128/192/256    128      Very high security margin
  Square       yes
  TripleDES    yes      168            64       
  Twofish      yes      128/192/256    128


PATENTS
-------

Various companies hold various patents for various algorithms in various
locations around the world. _YOU_ are responsible for ensuring that your use
of any algorithms is legal by checking if there exist any patents enforcable
in your jurisdiction.  The following it a partial list: 
 
RSA Security holds software patents on the RC5 algorithm.  If you
intend to use this cipher, you must contact RSA Security for
licensing conditions. Their web page is http://www.rsasecurity.com/.
 
RC4 is a trademark of RSA Security, so use of this label should perhaps
only be used with RSA Security's permission.
 
The IDEA algorithm is patented by Ascom in Austria, France, Germany, Italy,
Japan, Netherlands, Spain, Sweden, Switzerland, UK and the USA.  They should
be contacted if that algorithm is to be used, their web page is
http://www.ascom.ch/.


INSTALLATION
------------

Using the Cryptix JCE Provider is as simple as adding cryptix-jce-provider.jar
to your CLASSPATH. There are some caveats though, as described below:

JDK 1.4 (Java 2 version 1.4)

  Cryptix JCE works out-of-the-box on JDK 1.4 but its functionality is 
  restricted to what Sun allows trough their Jurisdiction Policy Files.
  In practice this means that you are limited to what the French government
  allows in terms of key sizes. Why Sun forces the French policy on the 
  rest of the world, and especially the USA, is an open question.

  If you require using Cryptix without any restrictions whatsoever, 
  you must have the "Java Cryptography Extension (JCE) Unlimited Strength
  Jurisdiction Policy Files" installed. At the time of writing they can be
  found at: http://java.sun.com/j2se/1.4/ . Installing these is non-trivial
  for casual users.


JDK 1.2 and JDK 1.3 (Java 2 version 1.2/1.3)

  Neither JDK 1.2 nor JDK 1.3 (nor JDK 1.1.x, see below) ships with the
  JCE API included so you must manually install a JCE API implementation
  before you can use the Cryptix JCE Provider. You can either use the
  Cryptix JCE API that is included in Cryptix JCE or you can opt for a
  third-party JCE API implementation.

  To use the Cryptix JCE API, simply put cryptix-jce-api.jar in your CLASSPATH,
  in addition to cryptix-jce-provider.jar.

  To use a third-party JCE API, follow its installation instructions. Please
  note that even though the Cryptix JCE is 100% compatible with the published
  JCE API specification, not all JCE API implementations will work. The most
  notable exception is Sun's own JCE API implementation which requires each
  JCE Provider to be signed. In such a case you are probably better off using
  the Cryptix JCE API.


JDK 1.1.x

  The JCE API was designed for use with JDK 1.2 and higher and is therefore
  dependent on JDK 1.2 specific features. We have included a compatibility
  layer that will allow you to use most of the JCE features with JDK 1.1.x.

  The JDK 1.1.x compatibility layer is cryptix-jce-compat11.jar . Just add it
  to your CLASSPATH. Now follow the instructions for JDK 1.2/1.3 as described
  above.

  NOTE: The JDK 1.1.x compatibility layer is no longer actively maintained.
        If possible you should upgrade to JDK 1.2 or higher instead of using
        the JDK 1.1.x compatibility layer.


ACKNOWLEDGEMENTS
----------------

This Cryptix JCE implementation borrows heavily from the original Cryptix 3
implementation.

Paul Waserbrot (pw@cryptix.org) became a Cryptix Team member and got the
project up to speed again after I let it lapse.

Joseph Hartmann (jhartmann@bigfoot.com) provided example code and invaluable
debugging assistance.

Jyrki Oinas (oinas@necsom.com) provided excellent bug reports and helped us
fix the RSASSA-PKCS1 and RSAES-PKCS1 implementations.

Edwin Woudt (edwin@cryptix.org) was responsible for lots of feedback and
filtered JCE bug reports for me.

Erwin van der Koogh (erwin@cryptix.org) contributed the initial ElGamal
implementation.
