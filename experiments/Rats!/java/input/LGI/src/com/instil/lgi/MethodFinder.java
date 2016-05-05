/*
 * MethodFinder.java
 *
 * Created on March 23, 2006, 10:17 PM
 *
 * @author Edgar A. Duenez-Guzman
 *
 * This code is an open source code, protected by the GNU General Public License.
 * If you didn't get a copy of that license with this code, contact the author
 * immediately at duenez@cs.utk.edu
 */

package com.instil.lgi;

import java.lang.reflect.*;

/**
 * This is a utility interface used during execution of a Block whose operator
 * represents a Class method. Thus the Block can call any of these methods to find
 * and, presumably, execute the method with its children as arguments.
 * @author Edgar Alfredo Duenez-Guzman
 */
public interface MethodFinder
{
    /**
     * Searches for a Method with the given name. If there is no such method
     * registered, then an exception is thrown.
     * @param name The name of the Method to search for.
     * @return The Method found.
     * @throws NoSuchMethodException if the Method is not found.
     */
    public Method findMethod( String name )  throws NoSuchMethodException;
    /**
     * Searches for a Method with the given name and with the given argument
     * class signature. If there is no such method registered, then an exception
     * is thrown.
     * @param name The name of the Method to search for.
     * @param args An array (variable length arguments) of classes representing
     * the Method signature to search for.
     * @return The Method found.
     * @throws NoSuchMethodException if the Method is not found.
     */
    public Method findMethod( String name, Class... args )  throws NoSuchMethodException;
}
