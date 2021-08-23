package apeg.test;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import apeg.util.path.*;

import apeg.test.expr.ExprContainer;

import apeg.ast.expr.*;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.util.SymInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestExprs {

    private Path samples;
    private ASTFactory factory;

    public TestExprs() {
	   samples = new AbsolutePath(System.getProperty("user.dir")
						   + File.separator + "examples"
						   + File.separator + "expr");
	   factory = new ASTFactoryImpl();
    }
    /**
     * Test literals
     */
    @Test
    void testLiteralInt() throws IOException {
	    // Create a ANTLR CharStream from a string
	    CharStream stream = CharStreams.fromReader(new StringReader("1"));
	    TContainer<Expr> test = new ExprContainer("literalInt", stream);
	    IntLit e = (IntLit) test.execute();
	   // Expected Result
	    assertEquals(e.getValue(), 1);	
    }

    @Test
    void testLiteralTrue() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("true"));

	TContainer<Expr> test = new ExprContainer("literalTrue", stream);
	BoolLit e = (BoolLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), true);	
    }

    @Test
    void testLiteralFalse() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("false"));

	TContainer<Expr> test = new ExprContainer("literalFalse", stream);
	BoolLit e = (BoolLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), false);	
    }

    @Test
    void testLiteralFloat00() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("0."));

	TContainer<Expr> test = new ExprContainer("literalFloat00", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.0);	
    }

    @Test
    void testLiteralFloat01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("0.1"));

	TContainer<Expr> test = new ExprContainer("literalFloat01", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.1F);	
    }

    @Test
    void testLiteralFloat02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(".1"));

	TContainer<Expr> test = new ExprContainer("literalFloat02", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.1F);	
    }

    @Test
    void testLiteralFloat03() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(".1e-2"));

	TContainer<Expr> test = new ExprContainer("literalFloat03", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.1e-2F);	
    }
    
    /**
     * Test operators
     */
    @Test
    void testArithmetic00() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("2+3"));

	TContainer<Expr> test = new ExprContainer("plus00", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(+ 2 3)", e.toString());
    }

    @Test
    void testArithmetic01() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("2+3+7"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(+ (+ 2 3) 7)", e.toString());
    }

    @Test
    void testArithmetic02() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("7+3*2"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(+ 7 (* 3 2))", e.toString());
    }

    @Test
    void testArithmetic03() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(7+3)*2"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(* (+ 7 3) 2)", e.toString());
    }
    
    @Test
    void testArithmetic04() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(10/8)*100"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(* (/ 10 8) 100)", e.toString());
    } 
    
    @Test
    void testArithmeticSUb() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(13 - 9)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(- 13 9)", e.toString());
    }
    
    @Test
    void testArithmeticMod() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(27 % 7)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(% 27 7)", e.toString());
    }
    
    @Test
    void testArithmeticDiv() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(31 / 15)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(/ 31 15)", e.toString());
    }
    
    @Test
    void testArithmetic05() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("((9 /2) % 18 * (1 - (13 + 7)))"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(* (% (/ 9 2) 18) (- 1 (+ 13 7)))", e.toString());
    }
    
    @Test
    void testArithmetic06() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(x + 2)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(+ x 2)", e.toString());
    }
    
    @Test
    void testArithmeticDelta() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(b*b) - (4*a*c)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(- (* b b) (* (* 4 a) c))", e.toString());
    }
    
    @Test
    void testRelational05() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("true || false && false == true == a && false || true" + 
			""));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals("(|| (|| true (&& (&& false (== (== false true) a)) false)) true)", e.toString());
    }
    
    @Test
    void testRelational04() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1 <= -6+a*b"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(<= 1 (+ -6 (* a b)))", e.toString());
    }
  
    @Test
    void testRelational03() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1<2 == false"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(== (< 1 2) false)", e.toString());
    }
    
    @Test
    void testRelational03Failure() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(1+2 3)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(null,e );
    }
    
    @Test
    void testRelational02() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("a*z+b-c == false"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(== (- (+ (* a z) b) c) false)", e.toString());
    }
    
    @Test
    void testRelational01() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("a*2+b-c < 10"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(< (- (+ (* a 2) b) c) 10)", e.toString());
    }
     
    @Test
    void testRelational01Failure() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1>2>3"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(null, e);
    }
   
    
    @Test
    void testBoolean01() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1 > 2 && b <= a && true"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(&& (&& (> 1 2) (<= b a)) true)", e.toString());
    }
    
    @Test
    void testBoolean02() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1 > 2 && b <= a || true && v"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(|| (&& (> 1 2) (<= b a)) (&& true v))", e.toString());
    }
    
    @Test
    void testBoolean03() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("1 > 2 || b <= a || true || v"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(|| (|| (|| (> 1 2) (<= b a)) true) v)", e.toString());
    }
    
    @Test
    void testBoolean04() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("a == 2 != false == b < c"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "(== (!= (== a 2) false) (< b c))", e.toString());
    }
    
    @Test
    void testBoolean01Failure() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("true && || false"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(null , e);
    }
   
    @Test
    void testBoolean02Failure() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(true && (true || false)"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(null, e );
    }
    
    @Test
    void testBoolean05() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("true && a<b || false"));

	TContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	
	assertEquals( "(|| (&& true (< a b)) false)" , e.toString());
    }

    @Test
    void testMetaAdd01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("(| 10 + 20 |)"));

	TContainer<Expr> test = new ExprContainer("MetaAdd01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "'(+ 10 20)", e.toString());
    }

    @Test
    void testMetaSub01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("(| 10 - 20 |)"));

	TContainer<Expr> test = new ExprContainer("MetaSub01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "'(- 10 20)", e.toString());
    }

    @Test
    void testMetaMult01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("(| 10 * 20 |)"));

	TContainer<Expr> test = new ExprContainer("MetaMult01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "'(* 10 20)", e.toString());
    }

    @Test
    void testMetaAdd02() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("(| 10 + 20 * 30 |)"));

	TContainer<Expr> test = new ExprContainer("MetaAdd02", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "'(+ 10 '(* 20 30))", e.toString());
    }

    @Test
    void testMetaAddFailure01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("( 10 + 20 * 30 |)"));

	TContainer<Expr> test = new ExprContainer("MetaAddFailure01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(null, e);
    }

    @Test
    void testMetaBooleanExpr01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("(| true && false || true |)"));

	TContainer<Expr> test = new ExprContainer("MetaBooleanExpr", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "'(|| '(&& true false) true)", e.toString());
    }

    @Test
    void testMapLit01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("{: 'abc' -> 10, 'def' -> 20 :}"));

	TContainer<Expr> test = new ExprContainer("MapLit01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "({: 'abc', 10 'def', 20)", e.toString());
    }

    @Test
    void testMapAcces01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("m['key']"));

	TContainer<Expr> test = new ExprContainer("MapLit01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "([] m 'key')", e.toString());
    }

    @Test
    void testMapExtension01() throws IOException {
	CharStream stream = CharStreams.fromReader(new StringReader("m['key' -> 10]"));

	TContainer<Expr> test = new ExprContainer("MapExtension01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals( "([->] m 'key' 10)", e.toString());
    }
	
	@Test
	void testBoolean06() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader("true && up != down || false "));
		
		TContainer<Expr> test = new ExprContainer("Bool06", stream);
		Expr e = test.execute();
		
		assertEquals(  "(|| (&& true (!= up down)) false)", e.toString());
		
	}
	
	
	@Test
	void testBoolean07() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader(" 5*5 != ok || 5*5 == 25 "));
		
		TContainer<Expr> test = new ExprContainer("Bool07", stream);
		Expr e = test.execute();
		
		assertEquals(  "(|| (!= (* 5 5) ok) (== (* 5 5) 25))", e.toString());
		
	}
	
	
	@Test
	void testBoolean08() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader(" true || ( false || false && true )"));
		
		TContainer<Expr> test = new ExprContainer("Bool08", stream);
		Expr e = test.execute();
		
		assertEquals(  "(|| true (|| false (&& false true)))", e.toString());
		
	}
	
		@Test
		void testMetaMapLit01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| {: 'abc' -> 10 :} |)"));
	
		TContainer<Expr> test = new ExprContainer("MetaMapLit01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'({: 'abc', 10)", e.toString());
		}
	
	@Test
    void testMetaMapLitKeyMult01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| {: (2 * 4) -> 'mult' :} |)"));
		
		TContainer<Expr> test = new ExprContainer("MetaMapLitKeyMult01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'({: '(* 2 4), 'mult')", e.toString());
    }
	
	@Test
    void testMapLitKeyMetaMult01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("({: (| (2 * 4) |) -> 'mult' :})"));
		
		TContainer<Expr> test = new ExprContainer("MapLitKeyMetaMult01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "({: '(* 2 4), 'mult')", e.toString());
    }
	
	@Test
    void testMapLitKeyMult01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("({: (2 * 4) -> 'mult' :})"));
		
		TContainer<Expr> test = new ExprContainer("MapLitKeyMult01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "({: (* 2 4), 'mult')", e.toString());
    }
	
}
