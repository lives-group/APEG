/* $Id: ExemptionMechanismException.java,v 1.2 2000/06/09 20:46:00 pw Exp $
 *
 * Copyright (C) 1995-2000 The Cryptix Foundation Limited.
 * All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package javax.crypto;

import java.security.GeneralSecurityException;

/**
 * Thrown if problem(s) encountered in the process of initializing.
 *
 * @author Paul Waserbrot (pw@cryptix.org)
 */

public class ExemptionMechanismException
    extends GeneralSecurityException {

    public ExemptionMechanismException() {
	super();
    }

    public ExemptionMechanismException(java.lang.String msg) {
	super(msg);
    }
}
