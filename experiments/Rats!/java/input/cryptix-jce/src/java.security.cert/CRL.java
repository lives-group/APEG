/* $Id: CRL.java,v 1.1 2000/08/07 17:54:53 edwin Exp $
 *
 * Copyright (C) 2000 The Cryptix Foundation Limited. All rights reserved.
 *
 * Use, modification, copying and distribution of this software is subject
 * the terms and conditions of the Cryptix General Licence. You should have
 * received a copy of the Cryptix General Licence along with this library;
 * if not, you can download a copy from http://www.cryptix.org/ .
 */
package java.security.cert;


/**
 * Base class of a Certificate Revocation List
 *
 * @version $Revision: 1.1 $
 * @author  Edwin Woudt (edwin@cryptix.org)
 */
public abstract class CRL {


// Instance variables
//.............................................................................


    /** The type of this CRL */
    private String type;



// Constructor
//.............................................................................


    /**
     * Constructor that takes a type
     *
     * @param type a String that represents the type of this CRL, e.g. 'X.509'
     *        or 'OpenPGP'.
     */
    protected CRL(String type) {
        this.type = type;
    }



// Getter
//.............................................................................


    /**
     * Return the type of this CRL.
     */
    public String getType() { return type; }

    

// Abstract methods
//.............................................................................


    /**
     * Checks if the given certificate is revoked according to this CRL.
     */
    public abstract boolean isRevoked(Certificate cert);
    

    /**    
     * Returns a human-readable string that is descriptive for this certificate.
     */
    public abstract String toString();
    

}
