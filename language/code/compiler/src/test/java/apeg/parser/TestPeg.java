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
	CharStream stream = CharStreams.fromReader(new StringReader(" ('char')"));

	TContainer<APEG> test = new PegContainer("TestChar01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "'char'", e.toString());
    }
	
	
	@Test
	void TestSequence02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" A B C "));

	TContainer<APEG> test = new PegContainer("TestSequence01)", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(seq (A) (B) (C))", e.toString());
	}	
	
	
	@Test
	void TestAny01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(_)"));
	
	TContainer<APEG> test = new PegContainer("TestAny01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "_", e.toString());
	}	
	
	
	@Test
	void TestChoice01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("( A / B )"));
	
	TContainer<APEG> test = new PegContainer("TestChoice01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(/ (A) (B))", e.toString());
	}	
	
	
	@Test
	void TestOptional01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("  A? "));
	
	TContainer<APEG> test = new PegContainer("TestOptional01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(? (A))", e.toString());
	}
		
	@Test
	void TestNot01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" !'A' "));
	
	TContainer<APEG> test = new PegContainer("TestNot01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(! 'A')", e.toString());
	}

	@Test
	void TestNot02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" !A "));
	
	TContainer<APEG> test = new PegContainer("TestNot02", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(! (A))", e.toString());
	}	
		
	@Test
	void TestKleene01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" AB* "));
	
	TContainer<APEG> test = new PegContainer("TestKleene01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(* (AB))", e.toString());
	}	
		
	@Test
	void TestpKleene01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" AB+ "));
	
	TContainer<APEG> test = new PegContainer("TestpKleene01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(+ (AB))", e.toString());
	}	
		
	@Test
	void TestAnd01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("& 'a' "));
	
	TContainer<APEG> test = new PegContainer("TestAnd01", stream);	
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(& 'a')", e.toString());
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
		
	@Test
	void TestLambda01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" 'A' / \u03bb"));
	
	TContainer<APEG> test = new PegContainer("TestLambda01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(/ 'A' \u03bb)", e.toString());
	}	
		
	@Test
	void TestGroup01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" (a..b) "));
	
	TContainer<APEG> test = new PegContainer("TestGroup01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "[.. a b]", e.toString());
	}	
		
	@Test
	void TestBind01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" A = 'B' "));
	
	TContainer<APEG> test = new PegContainer("TestBind01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(= A 'B')", e.toString());
	}	

	@Test
	void TestNotOpcional01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" ( !A? ) "));
	
	TContainer<APEG> test = new PegContainer("TestGroup01", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(! (? (A)))", e.toString());
	}	
	
	@Test
	void TestNotOpcional02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" (!A)?"));
	
	TContainer<APEG> test = new PegContainer("TestGroup02", stream);
	APEG e = test.execute();
	// Expected Result
	assertEquals( "(? (! (A)))", e.toString());
	}	
}
