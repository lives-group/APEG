/*
 * ParserTester.java
 *
 * Created on September 24, 2006, 11:08 PM
 *
 * This code is an open source code, protected by the GNU General Public License.
 * If you didn't get a copy of that license with this code, contact the author
 * immediately at duenez@cs.utk.edu
 */

package com.instil.lgi;

import javax.swing.*;

/**
 * This is a utility class, used to do a quick test for a grammar specification
 * and parsing of a String.
 * @author Edgar A. Duenez-Guzman
 */
public class ParserTester
{
    /**
     * A frame used as parent component for the dialog.
     */
    static JFrame f = new JFrame();
    /**
     * A file dialog used to select the files to open.
     */
    static JFileChooser jfc = new JFileChooser();
    /**
     * Reads the entire file into memory. It displays a dialog for the user to
     * select a file, then it open the contents (plain text) of the file and
     * returns it as a String.
     * @return The text contained in the selected file file.
     */
    public static String readFile()
    {
        int retVal;
        String line, text = "";
        try {
            retVal = jfc.showOpenDialog( f );
            if( retVal == jfc.APPROVE_OPTION )
            {
                java.io.BufferedReader r = new java.io.BufferedReader( new java.io.FileReader( jfc.getSelectedFile() ) );
                line = r.readLine();
                while( line != null )
                {
                    text += line + ParseError.NEW_LINE;
                    line = r.readLine();
                }
            }
        } catch( Exception e ) { e.printStackTrace(); }
        return text;
    }
    /**
     * The main method of the class. It ignores the arguments, and promts the
     * user to select two files. The first one will be parsed with the PEG
     * parser, and used as a grammar to parse the second file. It will then
     * print the parsed tree (of Block.NO_PARSE if failed) and any ParseErrors
     * associated with the parsing attempt.
     * It will then continue to prompt the user for files to parse until the
     * user cancells the selection or an empty file is selected.
     * @param args The arguments to the program. These are ignored.
     */
    public static void main( String args[] )
    {
        try {
            String text = readFile();

            PackratParser parser = new PackratParser( new ParsingExpressionGrammar() );
            Block b = parser.parse( text );
            //parser.printMemUsage();

            if( b == Block.NO_PARSE )
            {
                System.out.println( b );
                System.out.println( parser.getParseError() );
                System.exit(1);
            }
            PEGUtility.utility.optimizeParsedTree( b );

            ParsingGrammar gr = new ParsingGrammar( b, false );
            PackratParser parser2 = new PackratParser( gr );

            text = readFile();
            while( !text.equals( "" ) )
            {
                System.out.println( parser2.parse( text ) );
                //parser2.printMemUsage();
                ParseError error = parser2.getParseError();
                System.out.println( error );
                text = readFile();
            }
        } catch( Exception e ) { e.printStackTrace(); }
        System.exit(0);
    }
}
