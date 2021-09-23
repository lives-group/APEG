package apeg.parser;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import apeg.util.path.*;

import apeg.parser.expr.ExprContainer;
import apeg.TContainer;


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
	void testBoolean06() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader("true && up != down || false "));
		
		TContainer<Expr> test = new ExprContainer("Bool06", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals(  "(|| (&& true (!= up down)) false)", e.toString());
	}
	
	@Test
	void testBoolean07() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader(" 5*5 != ok || 5*5 == 25 "));
		
		TContainer<Expr> test = new ExprContainer("Bool07", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals(  "(|| (!= (* 5 5) ok) (== (* 5 5) 25))", e.toString());
	}
	
	@Test
	void testBoolean08() throws IOException {
		
		CharStream stream = CharStreams.fromReader(new StringReader(" true || ( false || false && true )"));
		
		TContainer<Expr> test = new ExprContainer("Bool08", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals(  "(|| true (|| false (&& false true)))", e.toString());
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
	void testMapExtension02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("m['key' -> k[key01]]"));
	
		TContainer<Expr> test = new ExprContainer("MapExtension02", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([->] m 'key' ([] k key01))", e.toString());
		
	}
	
	@Test
	void testMetaMapLit01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| {: 'abc' -> 10 :} |)"));
	
		TContainer<Expr> test = new ExprContainer("MetaMapLit01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'({: ''abc', 10:})", e.toString());
		}
	
	@Test
    void testMetaMapLitKeyMult01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| {: (2 * 4) -> 'mult' :} |)"));
		
		TContainer<Expr> test = new ExprContainer("MetaMapLitKeyMult01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'({: '(* 2 4), ''mult':})", e.toString());
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

	@Test
    void testMapListValue() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("({: Y -> [ 1, 2] :})"));
		
		TContainer<Expr> test = new ExprContainer("MapLitKeyMult01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "({: Y, ([ 1 2]))", e.toString());

	}

	@Test
    void testMap01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("({: :})"));
		
		TContainer<Expr> test = new ExprContainer("Map01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "({:)", e.toString());
    }

	@Test
    void testMetaMap01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(|{: :}|)"));
		
		TContainer<Expr> test = new ExprContainer("Map01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'({::})", e.toString());
    }
	
	@Test
    void testMetaMeta01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| (| (| 2 * 2 |) |) |)"));
		
		TContainer<Expr> test = new ExprContainer("MetaMeta01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "'(* 2 2)", e.toString());
    }

	@Test
    void testList01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ 1 , 2 , 3 ] )"));
		
		TContainer<Expr> test = new ExprContainer("List01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ 1 2 3])", e.toString());
    }

	@Test
    void testList02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ 'a' , 'b' , 'c' ] )"));
		
		TContainer<Expr> test = new ExprContainer("List01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ 'a' 'b' 'c'])", e.toString());
    }

	@Test
    void testListBang01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( m !! 1 )"));
		
		TContainer<Expr> test = new ExprContainer("ListBang01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(!! m 1)", e.toString());
    }

	@Test
    void testListBang02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ 1 , 2 , 3 ] !! 0 )"));
		
		TContainer<Expr> test = new ExprContainer("ListBang02", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "(!! ([ 1 2 3]) 0)", e.toString());
    }

	@Test
    void testList03() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( m ++ n )"));
		
		TContainer<Expr> test = new ExprContainer("List03", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "(++ m n)", e.toString());
    }

	@Test
    void testList04() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("([ '[' , ']' ])"));
		
		TContainer<Expr> test = new ExprContainer("List04", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ '[' ']'])", e.toString());
    }

	@Test
    void testList05() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("([ '[::]' , '' ])"));
		
		TContainer<Expr> test = new ExprContainer("List05", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ '[::]' ''])", e.toString());
    }
	
	@Test
    void testList06() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("([ (| '[::]' |) ])"));
		
		TContainer<Expr> test = new ExprContainer("List0", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ ''[::]'])", e.toString());
    }

	@Test
	void testList07() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("([ (| '[::]' |), (|''|) ])"));
		
		TContainer<Expr> test = new ExprContainer("List07", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ ''[::]' '''])", e.toString());
	}

	@Test
	void testList08() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("[ (| '' |) ]"));
		
		TContainer<Expr> test = new ExprContainer("List08", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ '''])", e.toString());
	}

	@Test
    void testListConcat01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( m ++ [ 1 , 2 , 3 ] )"));
		
		TContainer<Expr> test = new ExprContainer("ListConcat01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "(++ m ([ 1 2 3]))", e.toString());
    }

	@Test
    void testListConcat02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ 1 , 2 , 3 ] ++ [ 4 , 5 , 6 ,7 ] )"));
		
		TContainer<Expr> test = new ExprContainer("ListConcat02", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "(++ ([ 1 2 3]) ([ 4 5 6 7]))", e.toString());
    }

	@Test
    void testListConcatBang01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ 1 , 2 , 3 ] ++ m !! 0 )"));
		
		TContainer<Expr> test = new ExprContainer("ListConcatBang01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(++ ([ 1 2 3]) (!! m 0))", e.toString());
    }
	@Test
    void testListConcatBang02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("([ 4 , 5 , 6 ,7 ] ++ (m !! 0 ++ n) )"));
		
		TContainer<Expr> test = new ExprContainer("ListConcatBang02", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(++ ([ 4 5 6 7]) (++ (!! m 0) n))", e.toString());
    }

	@Test
	void testListMap01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( [ m['key1' -> 1] , n['key2' -> 2] , o['key3' -> 3] ] )"));
		
		TContainer<Expr> test = new ExprContainer("testListMap01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals( "([ ([->] m 'key1' 1) ([->] n 'key2' 2) ([->] o 'key3' 3)])", e.toString());
	}

	@Test
    void testBooleanExpression() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("(| (20 < 30 && 40 > 20) || (a + m[l !! 2] + l !! 666 == (b + m['abc'] < d[h])) |)"));
		
		TContainer<Expr> test = new ExprContainer("TestBooleanExpression", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("'(|| '(&& '(< 20 30) '(> 40 20)) '(== '(+ '(+ 'a ([] 'm (!! 'l 2))) (!! 'l 666)) '(< '(+ 'b ([] 'm ''abc')) ([] 'd 'h))))", e.toString());
    }

	@Test
    void testBooleanExpression02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader("( '\t' || '\n')"));
		
		TContainer<Expr> test = new ExprContainer("TestBooleanExpressiono2", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(|| '\t' '\n')", e.toString());
    }

	@Test
    void TestListList01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader
		("( [ m['key1' -> 1] , n['key2' -> 2] , o['key3' -> 3] , [ 1 , 2 , 3 ] ++ m !! 0 , [ 1 , 2 , 3 ] ++ [ 4 , 5 , 6 ,7 ] , ( m ++ n )])"));
		
		TContainer<Expr> test = new ExprContainer("ListList01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("([ ([->] m 'key1' 1) ([->] n 'key2' 2) ([->] o 'key3' 3) (++ ([ 1 2 3]) (!! m 0)) (++ ([ 1 2 3]) ([ 4 5 6 7])) (++ m n)])", e.toString());
	}

	@Test
    void TestListList02() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader
		("( [ m['key1' -> 1], n['key2' -> 2], o['key3' -> 3], [ 1 , 2 , 3] ,[ 1 , 2 , 3] ++ [ 4 , 5 , 6 ,7]] ++ m !! 0 ++ n)"));
		
		TContainer<Expr> test = new ExprContainer("ListList02", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(++ (++ ([ ([->] m 'key1' 1) ([->] n 'key2' 2) ([->] o 'key3' 3) ([ 1 2 3]) (++ ([ 1 2 3]) ([ 4 5 6 7]))]) (!! m 0)) n)", e.toString());
    }

	@Test
    void TestListList03() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader ("[ [ ] ++ [ 1, 2] ]"));
		
		TContainer<Expr> test = new ExprContainer("ListList03", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("([ (++ ([]) ([ 1 2]))])", e.toString());
    }

	@Test
    void MetaRuleTest01() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader
		("{| foobar[int x, {int} m, string str] returns y: noterm<metafoo, metabar>* {y = 30;}; |}"));
		
		TContainer<Expr> test = new ExprContainer("MetaRule01", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals("(metagrammar ([ (metaRule 'foobar' ([ 'int 'map 'string]) ([ 'x' 'm' 'str']))]))", e.toString());
    }

	@Test
    void testArithmetic07() throws IOException {
		CharStream stream = CharStreams.fromReader(new StringReader (" ( ) + 2 * 3 * [ 4 * 5]"));
		
		TContainer<Expr> test = new ExprContainer("Arithmetic07", stream);
		Expr e = test.execute();
		// Expected Result
		assertEquals(null, e);
    }

	

}
