package apeg.interpreter.inter;

import java.io.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

import apeg.ast.*;
import apeg.visitor.*;
import apeg.visitor.semantics.*;
import apeg.vm.*;

import apeg.util.path.Path;

import apeg.TContainer;

public class VMContainer extends TContainer<VMContainer.Result> {

    CharStream stream;
    StringReader input;

    public class Result {
        public int line, column;
        public boolean b; 
    }

    public VMContainer(String name, CharStream stream, StringReader input){
        super(name);
        this.stream = stream;
        this.input = input;
    }

    public Result execute() {
        Result r = new Result();

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
        Grammar g = parser.grammarDef().gram;
        Visitor typechecker = new TypeCheckerVisitor(false);
        g.accept(typechecker);

        if(!((TypeCheckerVisitor)typechecker).getError().isEmpty()){
            ErrorsMsg errm = ErrorsMsg.getInstance();
           for(ErrorEntry perr:((TypeCheckerVisitor)typechecker).getError())
               System.out.println(errm.translate(perr) );
            System.exit(1);
        }

        VMVisitor vm = new VMVisitor(input, ((TypeCheckerVisitor)typechecker).getEnv());
        vm.setDebugMode(true);
        g.accept(vm);

        r.line = vm.getVM().getLine();
        r.column = vm.getVM().getColumn();
        r.b = vm.getVM().succeed();

        return r;
    }
}
