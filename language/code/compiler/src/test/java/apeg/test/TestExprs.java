package apeg.test;

import org.junit.jupiter.api.Test;
import apeg.test.expr.ExprContainer;
import java.io.File;
import apeg.util.path.*;
import apeg.ast.expr.*;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.util.SymInfo;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import java.io.StringReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	TestContainer<Expr> test = new ExprContainer("literalInt", stream);
	IntLit e = (IntLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 1);	
    }

    @Test
    void testLiteralTrue() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("true"));

	TestContainer<Expr> test = new ExprContainer("literalTrue", stream);
	BoolLit e = (BoolLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), true);	
    }

    @Test
    void testLiteralFalse() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("false"));

	TestContainer<Expr> test = new ExprContainer("literalFalse", stream);
	BoolLit e = (BoolLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), false);	
    }

    @Test
    void testLiteralFloat00() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("0."));

	TestContainer<Expr> test = new ExprContainer("literalFloat00", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.0);	
    }

    @Test
    void testLiteralFloat01() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("0.1"));

	TestContainer<Expr> test = new ExprContainer("literalFloat01", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.1F);	
    }

    @Test
    void testLiteralFloat02() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(".1"));

	TestContainer<Expr> test = new ExprContainer("literalFloat02", stream);
	FloatLit e = (FloatLit) test.execute();
	// Expected Result
	assertEquals(e.getValue(), 0.1F);	
    }

    @Test
    void testLiteralFloat03() throws IOException {
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader(".1e-2"));

	TestContainer<Expr> test = new ExprContainer("literalFloat03", stream);
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

	TestContainer<Expr> test = new ExprContainer("plus00", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(+ 2 3)");
    }

    @Test
    void testArithmetic01() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("2+3+7"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(+ (+ 2 3) 7)");
    }

    @Test
    void testArithmetic02() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("7+3*2"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(+ 7 (* 3 2))");
    }

    @Test
    void testArithmetic03() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(7+3)*2"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(* (+ 7 3) 2)");
    }
    
    @Test
    void testArithmetic04() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(10/8)*100"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(* (/ 10 8) 100)");
    } 
    
    @Test
    void testArithmeticSUb() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(13 - 9)"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(- 13 9)");
    }
    
    @Test
    void testArithmeticMod() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(27 % 7)"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(% 27 7)");
    }
    
    @Test
    void testArithmeticDiv() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(31 / 15)"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(/ 31 15)");
    }
    
    @Test
    void testArithmetic05() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("((9 /2) % 18 * (1 - (13 + 7)))"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(* (% (/ 9 2) 18) (- 1 (+ 13 7)))");
    }
    
    @Test
    void testArithmetic06() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(x + 2)"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(+ x 2)");
    }
    
    @Test
    void testArithmeticDelta() throws IOException {
	// Path input= new RelativePath(samples, "arithmetic00.apeg"); // the input sample
	// Create a ANTLR CharStream from a string
	CharStream stream = CharStreams.fromReader(new StringReader("(b*b) - (4*a*c)"));

	TestContainer<Expr> test = new ExprContainer("plus01", stream);
	Expr e = test.execute();
	// Expected Result
	assertEquals(e.toString(), "(- (* b b) (* (* 4 a) c))");
    }
    
   
}
