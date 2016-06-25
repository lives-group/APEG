package apeg.parse;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import apeg.parse.ast.AstFactory;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.impl.AstFactoryImpl;

/**
 * Class for testing the APEG syntax
 * 
 * @author Leonardo Reis
 *
 */
public class TestSyntax {

	/**
	 * This function receives a file name (input stream) and a code informing
	 * what is to test: expressions, parsing expression, rules, meta-rules or
	 * grammars
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// my test
		args[0] = "./../../examples/grammar/grammar07.apeg";
		//args[0] = "./../../examples/grammar/grammar01_failure.apeg";
		args[1] = "grammar";

		if (args.length < 2)
			throw new Exception("Wrong number of arguments");
		String fileName = args[0];
		String nonTerminal = args[1];

		// create an AST factory
		AstFactory factory = new AstFactoryImpl();
		// create a CharStream that reads from a file
		ANTLRInputStream input = new ANTLRFileStream(fileName);
		// create a lexer that feeds off of input CharStream
		APEGLexer lexer = new APEGLexer(input);
		// create a buffer of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// create a parser that feeds off the tokens buffer
		APEGParser parser = new APEGParser(factory, tokens);
		// tell ANTLR to does not automatically build an AST
		parser.setBuildParseTree(false);

		switch (nonTerminal) {
		case "expr":
			parser.expr();
			break;
		case "pexpr":
			parser.peg_expr();
			break;
		case "rule":
			parser.rules();
			break;
		case "grammar":
			GrammarNode g = parser.grammarDef().gram;
			break;
		case "metarule":
			parser.meta_peg();
			break;
		default:
			throw new Exception("The language class " + nonTerminal
					+ " is not supported");
		}

		System.out.println(parser.getNumberOfSyntaxErrors());
		System.out.println(parser.getCurrentToken().getType() == Recognizer.EOF);
		
		if (parser.getNumberOfSyntaxErrors() > 0
				|| parser.getCurrentToken().getType() != Recognizer.EOF)
			System.exit(1);
		else
			System.exit(0);
		
	}
}
