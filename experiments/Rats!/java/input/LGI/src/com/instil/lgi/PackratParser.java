/*
 * PackratParser.java
 *
 * Created on October 23, 2005, 12:26 PM
 *
 * @author Edgar A. Duenez-Guzman
 *
 * This code is an open source code, protected by the GNU General Public License.
 * If you didn't get a copy of that license with this code, contact the author
 * immediately at duenez@cs.utk.edu
 */

package com.instil.lgi;

import java.util.regex.*;

/**
 * <p>
 * This class comprises the parsing of Strings into Block trees. The
 * PackratParser is the only necesary part to parse String lines into Blocks
 * ready for execution.</p>
 * <p>
 * It is based on the Packrat Parser algorithm described by 
 * <a href="http://pdos.csail.mit.edu/~baford/packrat/">Bryan Ford</a> and uses
 * Parsing Expression Grammars as the underlying grammar description mechanism.</p>
 * <p>
 * The grammars are themselves, parsed trees with the appropriate structure.
 * Theoretically, this class could parse the formal definition of the Parsing
 * Expression Grammar (ans it is one of the examples included here). In practice,
 * however, a hand-tuned implementation of the first grammar is provided in the
 * class ParsingExpressionGrammar which is the default grammar used by this
 * PackratParser.</p>
 * <p>
 * A parsed grammar to be used by this parser must have the following structure:
 * <pre>
 *               G
 *               |
 *       ------------------
 *       |       |   ...  |
 *       D       D        D
 *      /|\     /|\      /|\
 *    I <- E  I <- E   I <- E
 * </pre>
 * where <b>G</b> denotes the grammar tree root, <b>D</b> denotes a definition node
 * (type ParsingExpressionGrammar.DEFINITION), <b><-</b> denotes a Block of type
 * LEFT_ARROW, and finally, <b>E</b> denotes an expression (type EXPRESSION) Block. </p>
 * <p>
 * This parser runs in linear time on the input string, and the constant is at most
 * the number of different types in the grammar (and typically on the order of 2).</p>
 * <p>
 * As a final note, this class makes a difference between types and meta-types.
 * Types are the numerical values associated with parsed Blocks given the description
 * contained in the grammar and are always non-negatives. Meta-types are auxiliary
 * types referring to types in the ParsingExpressionGrammar class. From these,
 * LITERAL, SEQUENCE and IDENTIFIER are the ones more frequently used, but some
 * others might be used also. These meta-types are always negative, and have no
 * sintactic meanning other than group together a set of Blocks. All the sintactic
 * meanning of a Block (or group of Blocks) comes from the actual grammar used.</p>
 * @see ParsingExpressionGrammar
 * @author Edgar A. Duenez-Guzman
 */
public class PackratParser implements java.io.Serializable
{
    /**
     * The parsed grammar with the structure as follows:
     *               G
     *               |
     *       ------------------
     *       |       |   ...  |
     *       D       D        D
     *      /|\     /|\      /|\
     *     I<-exp  I<-exp   I<-exp
     */
    private ParsingGrammar grammar;

    //The implementation is for performance, so in order to allocate memory efficiently,
    //we invalidate a previously created data by simply incrementing a counter
    //which is used as a timestamp for parsing.
    private int fingercount;        //The fingerprint value to be used to automatically invalidate table entries over time.

    //This tables contain partially parsed results, and is what guarantees linear time,
    //since no type of Block is attempted to be parsed at the same position in the
    //String twice.
    private Block[][] table;        //Indexed by type and position.
    //This is used to detect circular references in the parsing. One of the
    //easiest errors to catch in a grammar definition.
    private boolean[][] used;       //Indexed by type and position.
    //This is a fingerprint for each parsed Block attempted, used to know when a
    //value in the table is valid. (valid means its value is equal to fingercount).
    private int[][] fingerprint;    //Indexed by type and position.

    //This is an array of stored ParseErrors indexed by position in the String.
    //These contain possible causes for parse failures that can hint an actual sintax error.
    private ParseError[] errors;    //Indexed by position.
    //Again, a fingerprint to know if the value is valid for the current fingercount.
    private int[] errorfp;       //Indexed by position.

    private boolean debug = false;  //Set to true to print all [type,pos] pairs while parsing.

    private GrammarUtility utility = new NullUtility(); //The utility to optimize parsed grammars and errors.

    /**
     * Creates a new instance of PackratParser with no associated grammar. In order
     * to be able to use this parser, a grammar must be set by calling setGrammar.
     */
    public PackratParser() { super(); }
    /**
     * Creates a new instance of PackratParser with the provided grammar.
     * @param grammar The ParsingGrammar used to parse Strings.
     */
    public PackratParser( ParsingGrammar grammar )
    {
        this();
        setGrammar( grammar );
    }

    /**
     * Sets the grammar to be used by this parser and clear all information about previos
     * parses and current state.
     * @param gram The grammar to be used.
     */
    public void setGrammar( ParsingGrammar gram )
    {
        grammar = gram;
        table = new Block[ grammar.getNumDescriptions() ][0];
        used = new boolean[ grammar.getNumDescriptions() ][0];
        fingerprint = new int[ grammar.getNumDescriptions() ][0];
    }
    /**
     * Gets a reference to the grammar used by this parser. This is not a copy of the
     * ParsingGrammar.
     * @return The grammar of this parser.
     */
    public ParsingGrammar getGrammar() { return grammar; }
    /**
     * Obtains the GrammarUtility used to post-process parsed trees and ParseErrors
     * @return A reference to the inner GrammarUtility.
     */
    public GrammarUtility getUtility() { return utility; }
    /**
     * Sets the GrammarUtility used to post-process parsed trees and ParseErrors
     * @param u A reference to the new GrammarUtility to be used.
     */
    public void setUtility( GrammarUtility u ) { utility = u; }

    /**
     * Utility function to post process the result of a parse.
     */
    private void optimizeAll( Block b )
    {
        if( b != Block.NO_PARSE )
            utility.optimizeParsedTree( b );
        utility.optimizeParseError( getParseError() );
    }
    /**
     * This method attempts to parse the specified text for a Block of the
     * specified type.
     * @see PackratParser#parse( String, int boolean )
     * @return The parsed Block tree, or Block.NO_PARSE if failed.
     * @param text The source text to parse from.
     * @param name The name of the Block type to be produced.
     */
    public Block parseNamedBlock( String text, String name ) { return parse( text, grammar.getTypeByName( name ), true ); }
    /**
     * This method attempts to parse the specified text for a Block of the
     * specified type, and possible post-processing the result with the
     * GrammarUtility.
     * @see PackratParser#parse( String, int boolean )
     * @return The parsed Block tree, or Block.NO_PARSE if failed.
     * @param text The source text to parse from.
     * @param optimize If true, both, the tree and the errors will be post
     *        processed by the GrammarUtility, neither otherwise.
     * @param name The name of the Block type to be produced.
     */
    public Block parseNamedBlock( String text, String name, boolean optimize ) { return parse( text, grammar.getTypeByName( name ), optimize ); }
    /**
     * This method attempts to parse the specified text for a Block of the
     * first (usually top-level in the hierarchy) type in the grammar.
     * @see PackratParser#parse( String, int boolean )
     * @return The parsed Block tree, or Block.NO_PARSE if failed.
     * @param text The source text to parse from.
     */
    public Block parse( String text ) { return parse( text, 0, true ); }
    /**
     * <p>
     * This method attempts to produce a parsed tree from the specified text,
     * and allows the user to specify if the tree and possible error causes
     * should be optimized after generated.</p>
     * <p>
     * If the parsing fails, only the errors could be optimized, on success,
     * both are.</p>
     * @param text The text to parse.
     * @param optimize If true, both, the tree and the errors will be post
     *        processed by the GrammarUtility, neither otherwise.
     * @return The Block tree generated from the text.
     */
    public Block parse( String text, boolean optimize ) { return parse( text, 0, optimize ); }
    /**
     * This method attempts to parse the specified text for a Block of the
     * specified type.
     * @return The parsed Block tree, or Block.NO_PARSE if failed.
     * @see PackratParser#parse( String, int boolean )
     * @param type The type to attempt to parse.
     * @param text The source text to parse from.
     */
    public Block parse( String text, int type ) { return parse( text, type, true ); }
    /**
     * <p>
     * Attempts to parse a tree of the provided numerical type from the
     * specified text. If the third parameter is true, then the result will be
     * post-processed with the GrammarUtility to optimize it. </p>
     * <p>
     * If a parsing fails, then the parsed tree is not optimized. The ParseErrors
     * are always optimized when the flag is set, regardless of the outcome of
     * the parsing.</p>
     * <p>
     * This method, along with <tt>parse( String )</tt>, <tt>parse( String, boolean )</tt>,
     * <tt>parse( String, String )</tt>, <tt>parse( String, String, boolean )</tt>, and
     * <tt>parse( String, int )</tt> represent the interface for a user to parse
     * Strings.</p>
     * <p>
     * These methods set-up a new parse step, and invalidate all previous states,
     * including ParseErrors and parsed trees. All of these call the recursive
     * implementation <tt>parse( String, int, int )</tt> to perform the actual
     * parsing.</p>
     * @return The parsed Block tree or NO_PARSE.
     * @see PackratParser#parse( String, int, int )
     * @param text The text to parse.
     * @param type The type to attempt to parse.
     * @param optimize If true, both, the tree and the errors will be post
     *        processed by the GrammarUtility, neither otherwise.
     */
    public Block parse( String text, int type, boolean optimize )
    {
        if( table[0].length <= text.length() )
        {
            errors = new ParseError[ text.length()+1 ];
            errorfp = new int[ text.length()+1 ];
            for( int i = 0; i < table.length; i++ )
            {
                table[i] = new Block[ text.length()+1 ];
                used[i] = new boolean[ text.length()+1 ];
                fingerprint[i] = new int[ text.length()+1 ];
            }
        }
        fingercount++;
        parse( text, type, 0 );
        if( optimize )
            optimizeAll( table[type][0] );
        return table[type][0];
    }
    /**
     * A recursive parsing method. This will attempt to create a parsed tree of
     * the specified type, starting from the specified position. This, along
     * with <tt>parse( String, Block, int )</tt> provide the underlying
     * implementation of the PackratParser. Ideally, these methods should not be
     * called directly, and instead, the other <tt>parse</tt> methods should be
     * used, since these do not create a new fingerprint, and instead rely on
     * the structure created by the other methods.
     * @param text The test to parse.
     * @param type The numerical type of the Block to try to parse.
     * @param position The starting position.
     */
    public void parse( String text, int type, int position )
    {
        Block b;
        //If we have updated this value in this fingercount.
        if( debug )
            System.out.println( "[ " + type + ", " + position + "]" );
        if( fingercount == fingerprint[type][position] )
            if( used[type][position] )
                throw new Error( "The grammar has a circular dependence." );
            else    //TODO: Might have to put this before the recursive call
                return; //The result is already computed

        //Place the marker to detect circular references.
        fingerprint[type][position] = fingercount;
        used[type][position] = true;
        table[type][position] = Block.NO_PARSE;

        //Get the description to try to parse.
        //table[type][position] = parse( text, grammar.getBlockDescription( type ), position );
        //table[type][position].setType( type );
        b = parse( text, grammar.getBlockDescription( type ), position );
        if( b == Block.NO_PARSE || b.getType() < 0 )    //If it is a NO_PARSE or a meta-type, simply pass it up.
        {
            b.setType( type );
            table[type][position] = b;
        }
        else
            table[type][position] = new Block( b.getText(), b.getBegin(), b.getEnd(), type, new Block[] {b} );

        //Unset the used to allow others to use this result
        used[type][position] = false;
    }
    /**
     * A recursive parsing method. This will attempt to create a parsed tree,
     * given the specified Block description (obtained from a grammar, starting
     * from the specified position. This, along with <tt>parse( String, int, int )</tt>
     * provide the underlying implementation of the PackratParser. Ideally,
     * these methods should not be called directly, and instead, the other
     * <tt>parse</tt> methods should be used, since these do not create a new
     * fingerprint, and instead rely on the structure created by the other
     * methods.
     * @param text The test to parse.
     * @param description A tree defining the structure the String should have.
     * @param position The starting position.
     * @return The parsed Block tree.
     */
    public Block parse( String text, Block description, int position )
    {
        Block b, retBlock;
        int type, newPos;

        //If an error in this position has not yet been created in this parse phase...
        if( errorfp[position] != fingercount )
        {
            errors[position] = new ParseError( text, position );
            errorfp[position] = fingercount;
        }
        //If the blocks are created here (i.e. are only grouping Blocks) then
        //the blocks returned will have a meta-type associated with them. (negative)
        //If not, they are going to be Blocks with a corresponding grammar type
        //(depending on which type was successfully parsed first).
        //The type of the expression part of the block description can only be:
        switch( description.getType() )
        {
            case ParsingExpressionGrammar.EXPRESSION:   //Parse both type and meta-type.
            case -ParsingExpressionGrammar.EXPRESSION:
                retBlock = parse( text, description.getChild(0), position );
                if( retBlock != Block.NO_PARSE )
                    return retBlock;
                //Assume it is optimized (it is a grammar). If it only has one (/ S) block
                if( description.getNumChildren() == 2 && description.getChild(1).getChild(0).getType() == ParsingExpressionGrammar.SLASH )
                {
                    //If the result is valid, approve.
                    retBlock = parse( text, description.getChild(1).getChild(1), position );
                    if( retBlock != Block.NO_PARSE )
                        return new Block( retBlock.getText(), retBlock.getBegin(), retBlock.getEnd(), -ParsingExpressionGrammar.EXPRESSION, new Block[]{ retBlock } );
                }
                else    //It has at least 2 (/ S) blocks...
                {
                    description = description.getChild(1);  //Go to the (/ S)* Block
                    for( int i = 0; i < description.getNumChildren(); i++ )
                    {
                        //Try to parse each one and if the result is valid, approve.
                        retBlock = parse( text, description.getChild(i).getChild(1), position );
                        if( retBlock != Block.NO_PARSE )
                            return new Block( retBlock.getText(), retBlock.getBegin(), retBlock.getEnd(), -ParsingExpressionGrammar.EXPRESSION, new Block[]{ retBlock } );
                    }
                }
                return Block.NO_PARSE;
            case ParsingExpressionGrammar.SEQUENCE: //Parse both type and meta-type.
            case -ParsingExpressionGrammar.SEQUENCE:
                newPos = position;
                //b will contain all the parsed Blocks.
                b = new Block( text, position, position, -ParsingExpressionGrammar.SEQUENCE, null );
                if( description.getChild(0).getType() == ParsingExpressionGrammar.OPEN )
                {
                    retBlock = parse( text, description.getChild(1), position );
                    if( retBlock == Block.NO_PARSE )
                        return Block.NO_PARSE;
                    b.setEnd( retBlock.getEnd() );
                    b.addChild( retBlock );
                    return b;
                }
                for( int i = 0; i < description.getNumChildren(); i++ )
                {
                    //Try to parse each one, updating the position
                    retBlock = parse( text, description.getChild(i), newPos );
                    //If the result is NoParse, propagate it.
                    if( retBlock == Block.NO_PARSE )
                        return Block.NO_PARSE;
                    //Otherwise, add the parsed child and update the position
                    b.addChild( retBlock );
                    newPos = retBlock.getEnd();
                }
                //Update this entry's end index
                b.setEnd( newPos );
                return b;
            case ParsingExpressionGrammar.PREFIX:   //Parse both type and meta-type.
            case -ParsingExpressionGrammar.PREFIX:
                if( description.getNumChildren() > 1 )  //There is a '&' or '!' and then an Identifier
                {
                    //try to parse
                    retBlock = parse( text, description.getChild(1), position );
                    errors[position].addPossibleError( grammar.getNameByType( -Math.abs(description.getChild(1).getType()) ) + " expected", description.getChild(1).getType() );
                    //Check if parse succeeded and parse accordingly.
                    if( description.getChild(0).getType() == ParsingExpressionGrammar.AND )
                        return ( retBlock == Block.NO_PARSE ? 
                            Block.NO_PARSE : new Block( text, position, position, -ParsingExpressionGrammar.PREFIX, null ) );
                    else
                        return ( retBlock == Block.NO_PARSE ? 
                            new Block( text, position, position, -ParsingExpressionGrammar.PREFIX, null ) : Block.NO_PARSE );
                }
                //else, simply return child block
                return parse( text, description.getChild(0), position );
            case ParsingExpressionGrammar.SUFFIX:
            case -ParsingExpressionGrammar.SUFFIX: //Meta type SUFFIX
                if( description.getNumChildren() > 1 )  //There is a '?', '+' or '*' after an Identifier
                {
                    newPos = position;
                    //try to parse
                    retBlock = parse( text, description.getChild(0), newPos );

                    //Allows for general case block consumed. Block b will contain all generated Blocks.
                    b = new Block( text, position, position, -ParsingExpressionGrammar.SUFFIX, null );

                    errors[position].addPossibleError( grammar.getNameByType( -Math.abs(description.getChild(0).getType()) ) + " expected", description.getChild(0).getType() );
                    //Check if parse succeeded and parse accordingly.
                    switch( description.getChild(1).getType() )
                    {
                        case ParsingExpressionGrammar.QUESTION:
                            return ( retBlock != Block.NO_PARSE ? retBlock : b );    //If succeeded, use it, if not, use empty
                        case ParsingExpressionGrammar.PLUS:
                            if( retBlock == Block.NO_PARSE )
                                return Block.NO_PARSE;  //A plus requires a match
                            else
                            {
                                b.addChild( retBlock );
                                newPos = retBlock.getEnd();
                            }
                            retBlock = parse( text, description.getChild(0), newPos );
                        case ParsingExpressionGrammar.STAR:
                            while( retBlock != Block.NO_PARSE )
                            {
                                b.addChild( retBlock );
                                newPos = retBlock.getEnd();
                                retBlock = parse( text, description.getChild(0), newPos );
                            }
                            //Update ending place.
                            b.setEnd( newPos );
                    }
                    return b;
                }
                else   //Simply point to child block
                    return parse( text, description.getChild(0), position );
            case ParsingExpressionGrammar.IDENTIFIER:   //Parse both type and meta-type.
            case -ParsingExpressionGrammar.IDENTIFIER:
                //Find the appropriate block to match
                type = grammar.getTypeByName( description.getSubtext() );
                parse( text, type, position );
                errors[position].addPossibleError( grammar.getNameByType( type ) + " expected", type );

                return table[type][position];
            case ParsingExpressionGrammar.LITERAL:  //Parse both type and meta-type.
            case -ParsingExpressionGrammar.LITERAL:
                String lit = description.getSubtext();
                errors[position].addPossibleError( "Match for " + lit + " expected", -ParsingExpressionGrammar.LITERAL );
                Pattern p = grammar.getPattern( lit );
                Matcher m = p.matcher( text.substring( position ) );
                if( m.lookingAt() )
                    //Meta-Type LITERAL (negative)
                    return new Block( text, position, position + m.end(), -ParsingExpressionGrammar.LITERAL, null );
                else
                    return Block.NO_PARSE;
            case ParsingExpressionGrammar.DOT:  //Parse both type and meta-type.
            case -ParsingExpressionGrammar.DOT:
                errors[position].addPossibleError( "Any character expected", -ParsingExpressionGrammar.DOT );
                return ( position < text.length() ? 
                    //Meta-Type DOT (negative)
                    new Block( text, position, position+1, -ParsingExpressionGrammar.DOT, null ) : Block.NO_PARSE );
        }
        //Print the debug information that this String went unprocessed. This is
        //generally an error, since it means the grammar did not cover this case.
        if( debug )
            System.out.println("Used default for type " + description.getType() );
        return Block.NO_PARSE;
    }
    /**
     * Gets the first ParseError associated with the last parsing. If the parsing
     * was successfull, then it returns the ParseError corresponding to the
     * position immediately after the last index in the text. I no error was
     * generated at all during the last parse, or no parse has been performed,
     * this method returns null.
     * @return The error associated with the first position in the text that
     *         produced a ParseError.
     */
    public ParseError getParseError()
    {
        for( int i = errors.length-1; i >= 0; i-- )
            if( errorfp[i] == fingercount )
                return errors[i];
        return null;
    }
    /**
     * <p>
     * Debugging method. This should not be called by the user, unless it is
     * absolutely necessary. This prints the memory map of the current parse.</p>
     * <p>
     * The parser builds a table of all possible String positions versus all
     * types of Blocks. At each place, it stores the Block that corresponds
     * to the attempt of parsing the String at that position for a Block of that
     * type (even if it is a Block.NO_PARSE).</p>
     * <p>
     * This method displays an X in the table cells that correspond to actual
     * attempts to parse, and a dot in positions where a parse has not been
     * attempted. The output goes to standard out.</p>
     */
    public void printMemUsage()
    {
        for( int i = 0; i < table.length; i++ )
        {
            for( int j = 0; j < table[i].length; j++ )
                System.out.print( (table[i][j] == null) ? "X" : "." );
            System.out.println();
        }
    }
    /**
     * This is a utility method used to print all the errors associated with
     * every position of the text, one per line, to standard out.
     */
    public void printAllErrors()
    {
        for( int i = 0; i < errors.length; i++ )
            if( errorfp[i] == fingercount )
                System.out.println( i + ": " +errors[i] );
    }

/*
    public static void main( String args[] )
    {
        String text = "Additive  <- Multitive '\\+'  Additive / Multitive\n" +
                      "Multitive <- Primary '\\*' Multitive / Primary\n" +
                      "Primary   <- '\\(' Additive '\\)' / Decimal\n" +
                      "Decimal   <- '[0-9]+'";
        try {
            String line;
            java.io.BufferedReader r = new java.io.BufferedReader( new java.io.FileReader( "../PEG.txt" ) );
            text = "";
            line = r.readLine();
            while( line != null )
            {
                text += line + ParseError.NEW_LINE;
                line = r.readLine();
            }
            //System.out.println( text );
        } catch( Exception e ) { e.printStackTrace(); }

        //System.out.println( text + ": matches = " + Pattern.matches( "[a-zA-Z_][a-zA-Z_0-9]*", text ) );
        PackratParser parser = new PackratParser( new ParsingExpressionGrammar() );
        Block b = parser.parse( text );

        //TreeOptimizer.removeEmpty( b );
        //TreeOptimizer.removeAllOfType( b, 20 );
        //TreeOptimizer.removeChildrenOfType( b, 7 );
        //TreeOptimizer.removeChildrenOfType( b, 8 );
        //for( int i = 10; i < 20; i++ )
        //    TreeOptimizer.removeChildrenOfType( b, i );
        //TreeOptimizer.collapse( b );

        System.out.println( b );
        //Print the memory usage pattern of this parsed text.
        parser.printMemUsage();
    }
*/
}
