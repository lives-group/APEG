/* $Id: X509Extension.java,v 1.1 2000/08/07 18:43:34 gelderen Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


import java.util.Set;


/**
 * Refer to Sun's JDK 1.2 (or 1.3) doco.
 *
 * @version $Revision: 1.1 $
 * @author  Jeroen C. van Gelderen (gelderen@cryptix.org)
 */
public interface X509Extension {

    Set getCriticalExtensionOIDs();

    byte[] getExtensionValue(String oid);

    Set getNonCriticalExtensionOIDs();

    boolean hasUnsupportedCriticalExtension();
}
