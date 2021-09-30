package apeg.parser.expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

import apeg.ast.ASTFactory;					
import apeg.ast.ASTFactoryImpl;
import apeg.ast.ASTFactoryMetaImpl;
import apeg.ast.rules.APEG;
import apeg.ast.rules.RulePEG;

import apeg.util.path.Path;

import apeg.TContainer;

public class ApegContainer extends TContainer<RulePEG> {

    private CharStream stream;

    public ApegContainer(String name, CharStream stream) {
	super(name);
	this.stream = stream;
    }


    public RulePEG execute() {   
	 // create a lexer that feeds off of stream
	 APEGLexer lexer = new APEGLexer(stream);
	 // create a buffer of tokens pulled from the lexer
	 CommonTokenStream tokens = new CommonTokenStream(lexer);
				
	 // create an AST factory
	 ASTFactory factory = new ASTFactoryImpl();				
	 ASTFactory factoryMeta = new ASTFactoryMetaImpl();
	 // create a parser that feeds off the tokens buffer
	 APEGParser parser = new APEGParser(factory, factoryMeta, tokens);
	 // tell ANTLR to does not automatically build an AST
	 parser.setBuildParseTree(false);
				
	 // Parse phase: extract the AST from the grammar source code
     RulePEG e = parser.production().rule;
     if(parser.getNumberOfSyntaxErrors() != 0 ||
        parser.getCurrentToken().getType() != Recognizer.EOF) {
        return null;
     }
	 return e;
    }
}
