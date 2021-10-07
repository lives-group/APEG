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
	CharStream stream = CharStreams.fromReader(new StringReader(" Update :{ exp = exp ;} ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestUpdate01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Update NONE () () { (= exp exp)})", e.toString());
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
		
	@Test
	void TestBindUpdate01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Bdup : x = { sum = x + x ;} ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestBindUpdate01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Bdup NONE () () (= x { (= sum (+ x x))}))", e.toString());
	}	
		
	@Test
	void TestNotUpdate01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Bdup : !(x = { sum = x + x ;}) ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestNotUpdate01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Bdup NONE () () (! (= x { (= sum (+ x x))})))", e.toString());
	}	
		
	@Test
	void TestBindUpdate02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(" Bdup : x = { sum = x + x ; mult = x * 4; } ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestBindUpdate02", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule Bdup NONE () () (= x { (= sum (+ x x)) (= mult (* x 4))}))", e.toString());
	}	
		
	@Test
	void TestLit01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("rule : 'something' ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestLit01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule rule NONE () () 'something')", e.toString());
	}
		
	@Test
	void TestSeqRule01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("rule_one : rule_two ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestSeqRule01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule rule_one NONE () () (rule_two))", e.toString());
	}
		
	@Test
	void TestMetaMapLit01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("a:{x = (| {: 'x' -> 2, 'y' -> 3:} |); };"));
	
	TContainer<RulePEG> test = new ApegContainer("TestMetaMapLit01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule a NONE () () { (= x '({: ''x', 2 ''y', 3:}))})", e.toString());
	}
		
	@Test
	void TestRuleSeq01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("A : B C D ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestRuleSeq01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule A NONE () () (seq (B) (C) (D)))", e.toString());
	}
		
	@Test
	void TestRuleSeqAny01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("A : B _ ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestRuleSeqAny01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule A NONE () () (seq (B) _))", e.toString());
	}
	
	@Test
	void TestBindList01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("A : {x = [a , b, c] ;} ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestBindList01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule A NONE () () { (= x ([ a b c]))})", e.toString());
	}
	
	@Test
	void Test() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("A : \u03bb ;"));
	
	TContainer<RulePEG> test = new ApegContainer("TestBindList01", stream);
	RulePEG e = test.execute();
	// Expected Result
	assertEquals( "(rule A NONE () () \u03bb)", e.toString());
	}
 
	

}