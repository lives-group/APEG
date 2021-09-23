package apeg.parser;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import apeg.util.path.*;

import apeg.parser.expr.PegContainer;
import apeg.TContainer;


import apeg.ast.rules.*;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.util.SymInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestPeg {

    private Path samples;
    private ASTFactory factory;

    public TestPeg() {
	   samples = new AbsolutePath(System.getProperty("user.dir")
						   + File.separator + "examples"
						   + File.separator + "expr");
	   factory = new ASTFactoryImpl();
    }

    @Test
    void TestChar01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" literal = 'char' "));

	TContainer<APEG> test = new PegContainer("TestChar01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= literal 'char')", e.toString());
    }

	@Test
	void TestSequence01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" A B "));
	
	TContainer<APEG> test = new PegContainer("TestSequence01)", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(seq  (A) (B))", e.toString());
	}	
	
	
	
	@Test
	void TestAny01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Any = _"));
	
	TContainer<APEG> test = new PegContainer("TestAny01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= Any _)", e.toString());
	}	
	
	
	@Test
	void TestChoice01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Choice = ( A / B ) "));
	
	TContainer<APEG> test = new PegContainer("TestChoice01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= Choice (/ (A) (B)))", e.toString());
	}	
	
	
	@Test
	void TestOptional01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Op = A? "));
	
	TContainer<APEG> test = new PegContainer("TestOptional01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= Op (? (A)))", e.toString());
	}
		
	@Test
	void TestNot01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" NOT = !A "));
	
	TContainer<APEG> test = new PegContainer("TestNot01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= NOT (! (A)))", e.toString());
	}	
		
	@Test
	void TestKleene01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" rule = AB* "));
	
	TContainer<APEG> test = new PegContainer("TestKleene01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= rule (* (AB)))", e.toString());
	}	
		
	@Test
	void TestpKleene01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" rule = AB+ "));
	
	TContainer<APEG> test = new PegContainer("TestpKleene01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= rule (+ (AB)))", e.toString());
	}	
		
	@Test
	void TestAnd01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("AND = (A&B)"));
	
	TContainer<APEG> test = new PegContainer("TestAnd01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= AND (& (A) (B)))", e.toString());
	}	
	
		
	@Test
	void TestNonTerminal01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Rule "));
	
	TContainer<APEG> test = new PegContainer("TestNonTerminal01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(Rule)", e.toString());
	}	

}
