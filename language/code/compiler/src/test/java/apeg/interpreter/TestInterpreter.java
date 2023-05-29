package apeg.interpreter;

import java.io.*;
import java.util.Scanner;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import apeg.util.path.*;

import apeg.interpreter.inter.VMContainer;
import apeg.TContainer;


import apeg.ast.rules.*;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.util.SymInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestInterpreter {

        private Path samples;
        private ASTFactory factory;
        private String prefix;

        public TestInterpreter() {
               this.samples = null;
               this.factory = null;
               this.prefix = "../../examples/grammar/";
        }

        @Test
        void TestBind01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader("apeg foo; prog[language g]: str=(a..z)+ {? str == 'hello' } ;"));
            StringReader input = new StringReader("world");

            TContainer<VMContainer.Result> test = new VMContainer("TestBind01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(false, e.b);
            assertEquals(0, e.line);
            assertEquals(5, e.column);
        }

        @Test
        void TestAdd01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader("apeg foo; prog[language g]: (0..9)+ '+' (0..9)+;"));
            StringReader input = new StringReader("500+300");

            TContainer<VMContainer.Result> test = new VMContainer("TestAdd01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(7, e.column);
        }

        @Test
        void VarDeclaration01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.VAR_DECLARATION));
            StringReader input = new StringReader("x = 10+20*30;");

            TContainer<VMContainer.Result> test = new VMContainer("VarDeclaration01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(13, e.column);
        }

        @Test
        void ExprDeclaration01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.EXPR_DECLARATION));
            StringReader input = new StringReader("+ : x = 10;");

            TContainer<VMContainer.Result> test = new VMContainer("ExprDeclaration01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(false, e.b);
            assertEquals(0, e.line);
            assertEquals(11, e.column);
        }

        @Test
        void QuoteValue01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.NUMBER_PROGRAM));
            StringReader input = new StringReader("3");

            TContainer<VMContainer.Result> test = new VMContainer("QuoteValue", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(1, e.column);
        }

        @Test
        void MetaParameter01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.METAPARAMETER));
            StringReader input = new StringReader("abc");

            TContainer<VMContainer.Result> test = new VMContainer("MetaParameter01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(3, e.column);
        }

        /*
        @Test
        void RuleBuild01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.RULEBUILD));
            StringReader input = new StringReader("foo");

            TContainer<VMContainer.Result> test = new VMContainer("RuleBuild01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(3, e.column);
        }
        */

        @Test
        void MetaNtcall01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.METANTCALL));
            StringReader input = new StringReader("abc");

            TContainer<VMContainer.Result> test = new VMContainer("MetaNtcall01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(3, e.column);
        }

        @Test
        void Grammar01() throws IOException {
            // Reading grammar .apeg file
            Path fpath = new RelativePath(new AbsolutePath(System.getProperty("user.dir")), this.prefix + "grammar01.apeg");
            String content = new Scanner(new File(fpath.getFile().toString())).useDelimiter("\\Z").next();

            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(content));
            StringReader input = new StringReader("b1");

            TContainer<VMContainer.Result> test = new VMContainer("Grammar01", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(2, e.column);
        }

        @Test
        void AbsGrammar() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.ABSGRAMMAR));
            StringReader input = new StringReader("|-20|=20");

            TContainer<VMContainer.Result> test = new VMContainer("AbsGrammar", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(8, e.column);
        }

        @Test
        void MetaSum() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.METASUM));
            StringReader input = new StringReader("10+20+30=80");

            TContainer<VMContainer.Result> test = new VMContainer("MetaSum", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(false, e.b);
            assertEquals(0, e.line);
            assertEquals(11, e.column);
        }

        @Test
        void VarDeclaration() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.VARDECLARATION));
            StringReader input = new StringReader("int a = 10;int b = 20;a = b;");

            TContainer<VMContainer.Result> test = new VMContainer("VarDeclaration", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(28, e.column);
        }

        @Test
        void Predicate() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(Programs.PREDICATE));
            StringReader input = new StringReader("barend");

            TContainer<VMContainer.Result> test = new VMContainer("Predicate", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(0, e.column);
        }

        @Test
        void Fatorial() throws IOException {
            // Reading grammar .apeg file
            Path fpath = new RelativePath(new AbsolutePath(System.getProperty("user.dir")), this.prefix + "grammarFat.apeg");
            String content = new Scanner(new File(fpath.getFile().toString())).useDelimiter("\\Z").next();

            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader(content));
            StringReader input = new StringReader("3!=6");

            TContainer<VMContainer.Result> test = new VMContainer("Fatorial", stream, input);
            VMContainer.Result e = test.execute();

            // Expected Result
            assertEquals(true, e.b);
            assertEquals(0, e.line);
            assertEquals(4, e.column);
        }
}
