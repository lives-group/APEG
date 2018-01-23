package apeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import apeg.option.HandlerOption;
import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;
import apeg.parse.ast.ASTFactory;
import apeg.parse.ast.ASTFactoryImpl;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;
import apeg.parse.ast.visitor.BuildRuleEnvironmetVisitor;
import apeg.parse.ast.visitor.DOTVisitor;
import apeg.parse.ast.visitor.PrettyPrintVisitor;
import apeg.parse.ast.visitor.VerifyVisitor;
import apeg.parse.ast.visitor.Environments.OperatorTables;
import apeg.util.lang.LangInfo;
import apeg.util.lang.java.JavaInfo;
import apeg.util.path.AbsolutePath;
import apeg.util.path.Path;
import apeg.util.path.RelativePath;

public class Tool {

	/**
	 * Fields for the compiler options
	 * */
	private Path outputPath; // the output directory
	private String namespace; // the package/namespace of the generated code
	private Path extdir; // path of the external functions
	private LangInfo targetLang; // informations about the target language and
									// external functions
	private Stack<String> warnings;

	public Tool() {
		// Set the default output location
		String currentDir = System.getProperty("user.dir");
		outputPath = new AbsolutePath(currentDir);

		// Set the default target language as Java
		targetLang = new JavaInfo();

		// Set the warning message stack
		warnings = new Stack<String>();
	}

	public static void main(String[] args) {
		/**
		 * Handling the command lines args
		 */
		Tool tool = new Tool();
		String[] grammars = HandlerOption.handle(args, tool);
		for (String s : tool.getWarningMessage())
			System.out.println(s); // print option arguments warnings

		for (String s : grammars) {
			// Check if the file ends with the correct extension: apeg
			if (!s.endsWith(".apeg")) {
				System.err.println("Invalid file extension: " + s);
				continue; // go to next file
			}
			// Open a the grammar file
			Path fpath;
			String fName;
			if (AbsolutePath.isAbsolute(s))
				fpath = new AbsolutePath(s);
			else
				fpath = new RelativePath(new AbsolutePath(
						System.getProperty("user.dir")), s);
			int index = s.lastIndexOf(File.separatorChar, s.length()-5);
			if(index != -1) // the string has a file separator
				fName = s.substring(index+1, s.length()-5);
			else // the string do not have a file separator
				fName = s.substring(0, s.length()-5);
			try {
				FileReader file = new FileReader(fpath.getFile());
				// Create an ANTLR input stream
				ANTLRInputStream input = new ANTLRInputStream(file);
				// create a lexer that feeds off of input stream
				APEGLexer lexer = new APEGLexer(input);
				// create a buffer of tokens pulled from the lexer
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				
				// create an AST factory
				ASTFactory factory = new ASTFactoryImpl();				
				// create a parser that feeds off the tokens buffer
				APEGParser parser = new APEGParser(factory, tokens);
				// tell ANTLR to does not automatically build an AST
				parser.setBuildParseTree(false);
				
				// Parse phase: extract the AST from the grammar source code
				GrammarNode g = parser.grammarDef().gram;
				
				// Check is there is any parse error
				if(parser.getNumberOfSyntaxErrors() != 0 ||
				   parser.getCurrentToken().getType() != Recognizer.EOF) {
					// ARTLR has already printed error messages, thus we stop
					continue;
				}
								
				// Pretty printing the grammar. Just for testing
				ASTNodeVisitor prettyprint = new PrettyPrintVisitor(
						new RelativePath(new AbsolutePath("."),
								"src/main/templates/prettyprint.stg"));
				g.accept(prettyprint);
				
				BuildRuleEnvironmetVisitor build = new BuildRuleEnvironmetVisitor();
				g.accept(build);
				build.printTable();
				
				
     			VerifyVisitor verifica = new VerifyVisitor(build.getTable(),OperatorTables.mkArithmeticEnv());
				g.accept(verifica);
			    if (verifica.hasErrors()){ 
			        System.err.println("---------- Errors --------- ");
			        for (String i : verifica.getErros()){
			        	System.err.println(i);
			        }
			    }
			    if(verifica.hasWarnings()){
			    	System.err.println("---------- Warnings --------- ");
			        for (String i : verifica.getWarnings()){
			        	System.err.println(i);
			        }
			    }
			    
		
				
				// Generating a graphical view from AST 			
				DOTVisitor dot = new DOTVisitor(
						new RelativePath(tool.outputPath, fName + ".dot"),
						new RelativePath(new AbsolutePath("."),
								"src/main/templates/dot.stg"));
				g.accept(dot);
				
				
			} catch (FileNotFoundException e) {
				System.err.println("File " + s + " do not exist");
				continue;
			} catch (IOException e) {
				System.err.println(e.getMessage());
				continue;
			}
		}

	}

	/**
	 * ***************** Functions for Fields Accesses **********************
	 */
	public Path getOutput() {
		return outputPath;
	}

	public void setOutput(Path output) {
		this.outputPath = output;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public LangInfo getTargetLang() {
		return targetLang;
	}

	public void setTargetLang(LangInfo target) {
		this.targetLang = target;
	}

	public Path getExternalDir() {
		return extdir;
	}

	public void setExternalDir(Path path) {
		this.extdir = path;
	}

	public Iterable<String> getWarningMessage() {
		return warnings;
	}

	public void addWarning(String message) {
		warnings.push(message);
	}
}
