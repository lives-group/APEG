package apeg.test.expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.ast.expr.Expr;

import apeg.util.path.Path;

import apeg.test.TestContainer;

public class ExprContainer extends TestContainer<Expr> {

    private CharStream stream;

    public ExprContainer(String name, CharStream stream) {
	super(name);
	this.stream = stream;
    }


    public Expr execute() {   
	 // create a lexer that feeds off of stream
	 APEGLexer lexer = new APEGLexer(stream);
	 // create a buffer of tokens pulled from the lexer
	 CommonTokenStream tokens = new CommonTokenStream(lexer);
				
	 // create an AST factory
	 ASTFactory factory = new ASTFactoryImpl();				
	 // create a parser that feeds off the tokens buffer
	 APEGParser parser = new APEGParser(factory, tokens);
	 // tell ANTLR to does not automatically build an AST
	 parser.setBuildParseTree(false);
				
	 // Parse phase: extract the AST from the grammar source code
	 Expr e = parser.expr().exp;
	 return e;
    }
}
