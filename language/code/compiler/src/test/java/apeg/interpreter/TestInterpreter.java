package apeg.interpreter;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

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

        public TestInterpreter() {
               this.samples = null;
               this.factory = null;
        }

        @Test
        void TestBind01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader("apeg foo; prog[language g]: str=(a..z)+ {? str == 'hello' } ;"));
            String input = "hello";

            TContainer<Boolean> test = new VMContainer("TestBind01", stream, input);
            Boolean e = test.execute();

            // Expected Result
            assertEquals(false, e.toString());
        }

        /*
        @Test
        void TestAdd01() throws IOException {
            // Create a ANTLR CharStream from a string
            CharStream stream = CharStreams.fromReader(new StringReader("apeg foo; prog[language g]: (0..9)+ '+' (0..9)+;"));
            String input = "500+300";

            TContainer<Boolean> test = new VMContainer("TestAdd01", stream, input);
            Boolean e = test.execute();

            // Expected Result
            assertEquals(false, e.toString());
        }
        */
}
