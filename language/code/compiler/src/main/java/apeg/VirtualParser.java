package apeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import java.net.URL;
import java.net.URI;
import java.lang.StringBuilder;
import java.io.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.option.HandlerOption;
import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;
import apeg.ast.ASTFactory;
import apeg.ast.ASTFactoryImpl;
import apeg.ast.ASTFactoryMetaImpl;
import apeg.ast.Grammar;
import apeg.util.Pair;
import apeg.visitor.semantics.VType;
import apeg.visitor.semantics.ErrorsMsg;
import apeg.visitor.semantics.ErrorEntry;

import apeg.visitor.DOTVisitor;
import apeg.visitor.semantics.TypeCheckerVisitor;
import apeg.util.lang.LangInfo;
import apeg.util.lang.java.JavaInfo;
import apeg.util.path.AbsolutePath;
import apeg.util.path.Path;
import apeg.util.path.RelativePath;

import apeg.visitor.*;

public class VirtualParser {

    private String grammar, input;

    // Factory
    private ASTFactory factory, factoryMeta;

    // Visitors
    private VMVisitor vm;
    private TypeCheckerVisitor tc;

    // Grammar node
    private Grammar g;

    public VirtualParser(String grammar){
        if(checkExtension(grammar, 1)){
            this.grammar = grammar;
            this.factory = new ASTFactoryImpl();
            this.factoryMeta = new ASTFactoryMetaImpl();
            this.vm = null;
            this.tc = null;
            this.g = getAST();
        }
        else {
            System.out.println("Check your grammar file extension");
            this.grammar = "";
            this.factory = this.factoryMeta = null;
        }
    }

    public VirtualParser(String grammar, String input){
        this(grammar);

        if(checkExtension(input, 2)){
            this.input = input;
            this.vm = null;
            this.tc = null;
        }
        else {
            System.out.println("Check your input file extension");
            this.grammar = "";
            this.factory = this.factoryMeta = null;
        }
    }

    public boolean checkExtension(String filename, int option){
        switch(option){
            case 1: return filename.endsWith(".apeg");
            case 2: return filename.endsWith(".txt");
            default: return false;
        }
    }

    // Obtains the generated AST root node (can also be used to check for syntax)
    private Grammar getAST(){
        Path fpath;

        if(AbsolutePath.isAbsolute(grammar))
            fpath = new AbsolutePath(grammar);
        else
            fpath = new RelativePath(new AbsolutePath(System.getProperty("user.dir")), grammar);

        try{
            FileReader file = new FileReader(fpath.getFile());
            ANTLRInputStream input = new ANTLRInputStream(file);
            APEGLexer lexer = new APEGLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            APEGParser parser = new APEGParser(factory, factoryMeta, tokens);
            parser.setBuildParseTree(false);

            Grammar g = parser.grammarDef().gram;
            
            // If some syntax error exists, it will be catch right here
            if(parser.getNumberOfSyntaxErrors() != 0 || parser.getCurrentToken().getType() != Recognizer.EOF){
                System.out.println("You have syntax errors");
                return null;
            }

            return g;
        }
        catch (FileNotFoundException e){
            System.out.println("File " + grammar + " do not exist");
            return null;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Obtains the boolean value that specifies if types are correct
    public boolean TypeCheckGrammar(boolean debug){
        this.tc = new TypeCheckerVisitor(debug);
        this.g.accept(this.tc);

        if(!((TypeCheckerVisitor)this.tc).getError().isEmpty()){
            System.out.println("Types are not correct. Fix the following errors:");
            ErrorsMsg errm = ErrorsMsg.getInstance();
            for(ErrorEntry perr : ((TypeCheckerVisitor)this.tc).getError())
                System.out.println(errm.translate(perr));
            return false;
        }

        System.out.println("Types are correct");
        return true;
    }

    // Interprets a file using a given grammar
    public void interpretsFile(boolean debug){
        this.vm = new VMVisitor(this.input, ((TypeCheckerVisitor)this.tc).getEnv());
        this.vm.setDebugMode(debug);

        System.out.println("Executing: " + this.input);
        this.g.accept(this.vm);
    }

    // Interprets a program using the constructor grammar
    public boolean checkInterpretation(){
        if(vm != null) return vm.succeed();
        else return interpreterError();
    }

    // Check an attribute value
    public Object getAttribute(String varName){
        if(vm != null) return this.vm.getLastCTX().readValue(varName);
        else return interpreterError();
    }

    // Generates a DOT file
    public void generateDOT(String output){
        String currentDir = System.getProperty("user.dir");
        DOTVisitor dv = new DOTVisitor(new RelativePath(new AbsolutePath(currentDir), output + ".dot"), "templates/dot.stg");
        this.g.accept(dv);
    }

    // Error handling
    public boolean interpreterError(){
        System.out.println("You should run interpretsFile() first");
        return false;
    }

    public void setGrammar(String grammarFilename){
        this.grammar = grammarFilename;
    }

    public void setInput(String inputFilename){
        this.input = inputFilename;
    }

    public void setVM(VMVisitor vm){
        this.vm = vm;
    }

    public String getGrammar(){
        return this.grammar;
    }

    public String getInput(){
        return this.input;
    }

    public VMVisitor getVM(){
        return this.vm;
    }
}
