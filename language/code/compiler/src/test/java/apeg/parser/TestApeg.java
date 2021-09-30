package apeg.parser;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import apeg.util.path.*;

import apeg.parser.expr.ApegContainer;
import apeg.TContainer;


import apeg.ast.rules.*;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.util.SymInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestApeg {

    private Path samples;
    private ASTFactory factory;

    public TestApeg() {
	   samples = new AbsolutePath(System.getProperty("user.dir")
						   + File.separator + "examples"
						   + File.separator + "expr");
	   factory = new ASTFactoryImpl();
    }

		
	@Test
	void TestBind01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Bind : exp = 'exp' ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestBind01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Bind NONE () () (= exp 'exp'))", e.toString());
	}	
		
	@Test
	void TestUpdate01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Update :{ exp = 'exp' ;} ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestUpdate01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Update NONE () () { (= exp 'exp')})", e.toString());
	}	
		
	@Test
	void TestConstraint01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Constraint : A? ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestConstraint01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Constraint NONE () () (? (A)))", e.toString());
	}	
		
	@Test
	void TestNonTerminalWithAtributest01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Nntrml[int x] returns w+1: atribute<'char'> ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestNonTerminalWithAtributest01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Nntrml NONE ( (:: int x)) ( (+ w 1)) (atribute 'char'))", e.toString());
	}	

}
