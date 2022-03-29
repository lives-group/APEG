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

import apeg.util.path.Path;

import apeg.TContainer;

public class VMContainer extends TContainer<Boolean> {

    CharStream stream;
    String input;
    File temp;

    public VMContainer(String name, CharStream stream, String input){
        super(name);
        this.stream = stream;
        try{
            this.temp = File.createTempFile(name, ".txt", new File());
        }
        catch(IOException e){
            System.exit(1);
        }
        this.input = input;
    }

    public Boolean execute() {
        try{
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
            Visitor typechecker = new TypeCheckerVisitor(true);
            g.accept(typechecker);

            if(!((TypeCheckerVisitor)typechecker).getError().isEmpty()){
                ErrorsMsg errm = ErrorsMsg.getInstance();
               for(ErrorEntry perr:((TypeCheckerVisitor)typechecker).getError())
                   System.out.println(errm.translate(perr) );
                System.exit(1);
            }

            // Writing tmp file to serve as VMVisitor input parameter
            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write(input);
            out.close();

            File f = new File(temp.toString());
            VMVisitor vm = new VMVisitor(f.getAbsolutePath(), ((TypeCheckerVisitor)typechecker).getEnv());
            vm.setDebugMode(true);
            g.accept(vm);

            return vm.succeed();
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
}
