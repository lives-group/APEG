/*
 * BinaryOperatorParser.java
 *
 * Created on 29 de noviembre de 2004, 09:08 PM
 *
 * @author Edgar A. Duenez-Guzman
 *
 * This code is an open source code, protected by the GNU General Public License.
 * If you didn't get a copy of that license with this code, contact the author
 * immediately at duenez@cs.utk.edu
 */

package com.instil.lgi;

import java.lang.reflect.*;
import java.util.*;

/**
 * <p>
 * This class comprises the parsing of a binary operator Block into a tree of
 * Blocks where each one is a function (method) call and the precedence in the
 * operators is represented by a tree structure. The associativity is assumed to
 * be from left to right (as in normal arithmetic).</p>
 * <p>
 * Small precedence is created closer to the root. The operator with the
 * smallest precedence is at the root. Note that being in the root means being
 * the LAST operator being grouped.</p>
 * <p>
 * The Blocks to be transformed must have one the following structures
 * <pre>
 *        B         S                S
 *        -         |                |
 *               -------        -----------
 *               |     |        |         |
 *               B     S        B         S
 *                     |                  |
 *                   -----         --------------
 *                   |   |         |    |       |
 *                   O   B         S    S  ...  S
 *                                 |    |       |
 *                                ---  --- ... ---
 *                                | |  | |     | |
 *                                O B  O B ... O B
 * </pre>
 * Depending on whether there is only 1, 2 or more operands (B) (i.e. 0, 1 or
 * more operators). Here, <b>B</b> represents one operand Block, <b>S</b> is a
 * simple Sequence Block with no (semantic) content, and <b>O</b> represents a
 * binary operator.</p>
 * <p>
 * These types of Block trees are typically obtained from a grammar rule of the
 * form
 * <pre>
 *    BinOperator <- Operand ( Operator Operand )*
 * </pre>
 * which is easy to define and not worry about operator precedence in the
 * grammar definition, and only later we can specify precedence and transform
 * the grammar tree into one with precedence information.
 * </p>
 * 
 * @author Edgar Alfredo Duenez-Guzman
 */
public class BinaryOperatorParser
{
    /** Default type for every block created from this binary operator parser */
    public static final int BINARY_OP_PARSED = 34709274;
    private String[] prefix, infix;
    private int[] precedence, indices;
    private int type;
    /**
     * Creates a new instance of BinaryOperatorParser with the defailt numerical
     * type <b>BINARY_OP_PARSED</b>.
     */
    public BinaryOperatorParser() { this( BINARY_OP_PARSED ); }
    /**
     * Creates a new instance of BinaryOperatorParser specifying the numerical type
     * for all Blocks created while transforming the binary operator to precedence
     * driven Blocks (function calls typically).
     * @param type The numerical type to be used for the generated operator Blocks.
     */
    public BinaryOperatorParser( int type ) { this.type = type; }
    /**
     * <p>
     * This method sets (prepares) the prefix names of the available operators to the
     * specified values. It also sets the precedences of the infix operators. </p>
     * <p>
     * The three arrays must correspond index by index in the sense that the inf[i]
     * must be the infix representation of the i-th operator, and have precedence
     * prec[i], and so on.
     * These arrays will be sorted to optimize searching time. </p>
     * @param inf The available operators in infix notation.
     * @param pref The available operators in prefix notation (functional notation).
     * @param prec The precedence of the operators. The higher the precedence, the
     * sooner the operator is going to be executed.
     */
    public void setOperators( String[] inf, String[] pref, int[] prec )
    {
        if( pref.length != inf.length || prec.length != pref.length )
            throw new IllegalArgumentException( "The arguments MUST have the same size." );
        infix = inf;
        prefix = pref;
        precedence = prec;
        indices = new int[ infix.length ];
        Integer[] ind = new Integer[ indices.length ];
        for( int i = 0; i < indices.length; i++ )
            ind[i] = i;
        //Sort by infix.
        Arrays.sort( ind, new Comparator<Integer>() {
            public int compare( Integer i, Integer j )
            {
                return infix[i].compareTo( infix[j] );
            }
        } );
        for( int i = 0; i < indices.length; i++ )
            indices[i] = ind[i];
    }
    /**
     * <p>
     * This method constructs a block tree replacing the binary operators found with
     * its equivalent in functional notation. The contents of the Block and
     * potentially of the sub-Blocks will be changed by this call.</p>
     * <p>
     * This method assumes the Block b has the required structure. </p>
     * @param b The block to reconstruct in functional notation.
     * @return The reconstructed block, with all intances of binary operators replaced
     * with its functional notation defined by its prefix names and precedences.
     */
    public Block constructFunctionTree( Block b )
    {
        if( b.getNumChildren() == 0 )
            return b;
        String[] opers = getOpers( b );
        Block[] operands = getOperands( b, opers.length );
        String[] prefix = new String[ opers.length ];
        for( int i = 0; i < prefix.length; i++ )
            prefix[i] = getPrefixName( opers[i] );
        int[] prec = getOpPrecedence( opers );
        return constructFunctionTree( prefix, prec, 0, opers.length-1, operands );
    }
    /**
     * This recursive method constructs a block tree replacing the binary operators found with
     * its equivalent in functional notation. It uses the provided information to achieve
     * this goal. This method is called from whithin the <tt>constructFunction( Block )</tt>
     * method, and in general should not be called directly.
     * @return The reconstructed block, with all intances of binary operators replaced
     * with its functional notation defined by its prefix names and precedences,
     * and the biggest precedence as a root.
     * @param prefix The prefix names of the operators.
     * @param prec The precedences of the operators.
     * @param min The minimum subblock to be considered.
     * @param max The maximum subblock to be considered.
     * @param operands The child nodes (operands) of the binary operator Block.
     */
    protected Block constructFunctionTree( String[] prefix, int[] prec, int min, int max, Block[] operands )
    {
        if( min > max )
            return operands[min];
        int index = min;
        //Find minimum precedence
        for( int i = min+1; i <= max; i++ )
            if( prec[i] < prec[index] )
                index = i;
        Block[] child = new Block[2];
        child[0] = constructFunctionTree( prefix, prec, min, index-1, operands );    //The two blocks associated with this operator
        child[1] = constructFunctionTree( prefix, prec, index+1, max, operands );
        return new Block( prefix[index], 0, prefix[index].length(), type, child );
    }
    /**
     * Gets the operators in array fromat. This method uses the java.util.StringTokenizer
     * class to perform its job.
     * @param b The block to get the binary operators from.
     * @return The array containing the binary operators as an array and in the same
     * order as they were in the block.
     */
    private String[] getOpers( Block b )
    {
        if( b.getChild(1).getChild(0).getNumChildren() == 0 )   //Only 1 operator.
        {
            b = b.getChild(1).getChild(0);
            return new String[] { b.getSubtext() };
        }

        b = b.getChild(1);
        String[] opers = new String[ b.getNumChildren() ];
        for( int k = 0; k < opers.length; k++ )
            opers[k] = b.getChild(k).getChild(0).getSubtext();
        return opers;
    }
    /**
     * Gets num operands from the Block b.
     */
    private Block[] getOperands( Block b, int num )
    {
        if( num == 1 )
            return new Block[]{ b.getChild(0), b.getChild(1).getChild(1) };
        Block[] operands = new Block[num+1];
        operands[0] = b.getChild(0);
        b = b.getChild(1);
        for( int i = 0; i < num; i++ )
            operands[i+1] = b.getChild(i).getChild(1);
        return operands;
    }
    /**
     * Gets the corresponding precedences of the specified binary operators
     * @param opers The array containing the binary operators in order.
     * @return An array containing the precedence of the binary operators in the same order as given.
     */
    private int[] getOpPrecedence( String[] opers )
    {
        int[] opPrecedence = new int[ opers.length ];
        for( int k = 0; k < opers.length; k++ )
            opPrecedence[k] = getPrecedence( opers[k] );
        return opPrecedence;
    }
    /**
     * Gets the precedence of a given binary operator.
     * @param operator The operator to get the precedence from.
     * @return The precedence of the operator. (The higher the sooner it will be executed).
     */
    public int getPrecedence( String operator )
    {
        int low = 0, high = infix.length-1, middle, cmp;
        while( low <= high )
        {
            middle = low + (high - low)/2;
            cmp = infix[ indices[middle] ].compareTo( operator );
            if( cmp == 0 )
                return precedence[ indices[middle] ];
            else if( cmp > 0 )
                high = middle - 1;
            else
                low = middle + 1;
        }
        throw new IllegalArgumentException( "The operator \'" + operator + "\' was not found." );
    }
    /**
     * Gets the prefix name of a given binary operator.
     * @param operator The operator to get the prefiz name from.
     * @return The prefix name of the operator.
     */
    public String getPrefixName( String operator )
    {
        int low = 0, high = infix.length-1, middle, cmp;
        while( low <= high )
        {
            middle = low + (high - low)/2;
            cmp = infix[ indices[middle] ].compareTo( operator );
            if( cmp == 0 )
                return prefix[ indices[middle] ];
            else if( cmp > 0 )
                high = middle - 1;
            else
                low = middle + 1;
        }
        throw new IllegalArgumentException( "The operator \'" + operator + "\' was not found." );
    }
}