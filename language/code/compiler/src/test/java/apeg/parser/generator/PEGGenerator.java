package apeg.parser.generator;

// import apeg.ast.ASTFactory;
// import apeg.ast.ASTFactoryImpl;
// import apeg.ast.ASTFactoryMetaImpl;
import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;

import apeg.util.CharInterval;
import apeg.util.SymInfo;

import apeg.util.path.AbsolutePath;
import apeg.util.path.Path;
import apeg.util.path.RelativePath;

import apeg.visitor.*;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

import net.jqwik.api.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.StringReader;
import java.io.IOException;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

class PEGGenerator {

  private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
  private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String ALL_MY_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS;

  private ASTFactory factory;

  public PEGGenerator() {
    // factory = new ASTFactoryImpl();
  }

  
  //after-failure supostamente deve fazer com seja executado a amostra que falhou anteriormente
  @Property(tries = 10, generation = GenerationMode.RANDOMIZED, afterFailure = AfterFailureMode.SAMPLE_FIRST)
  void gen(@ForAll("shortInt") int h) throws IOException{

    factory = new ASTFactoryImpl();


     Visitor prettyprint = new PrettyPrint(new RelativePath(new AbsolutePath("."),"src/main/templates/prettyprint.stg"));

    // //gera a AST passando a altura gerada automaticamente
     APEG generated = gen(h, true);

   generated.accept(prettyprint);

     System.out.println(((PrettyPrint) prettyprint).renderPEG());
     System.out.println(generated.toString());
    
    // CharStream stream = CharStreams.fromReader(new StringReader("/*colocar a apeg gerada da ast aqui*/"));

    // RulePEG e = execute(stream);

  
    // Assertions.assertEquals( "/*generated.toString()*/", e.toString());

  }

  @Provide
  public Arbitrary<Integer> shortInt() {
    //Altura da arvore a ser gerada (min, max)
    return Arbitraries.integers().between(2, 4);
  }

  public String genLit(Random arbitrary) {
    String str = "";
    int j = arbitrary.nextInt(5);
    for (int i = 0; i < j; i++) {
      str += ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length()));
    }
    return str;
  }

  public APEG[] seqArray(Random arbitrary, int h) {
    int n = arbitrary.nextInt(4) + 2;
    APEG a[] = new APEG[n];
    for (int i = 0; i < n; i++) {
      a[i] = gen(h, false);
    }
    return a;
  }

  public APEG gen(int h, Boolean b) {
    SymInfo s = new SymInfo(-1, -1);
    int aux;
    int aux2;
    List<Expr> l = new LinkedList();
    Random arbitrary = new Random();
    // Se a expressao vai ter bind ou nao
    if (b) {
      aux = arbitrary.nextInt(2);
      if (aux == 1) {
        Attribute attribute = new Attribute(s, genLit(arbitrary));
        return factory.newBindPEG(s, attribute, gen(h, false));
      }
    }
    b = false;
    if (h <= 0) {
      aux = arbitrary.nextInt(5);
      char charAux, charAux2;
      String str = "";
      if (aux == 0) {
        return factory.newAnyPEG(s);
      } else if (aux == 1) {
        return factory.newLambdaPEG(s);
      } else if (aux == 2) {
        return factory.newLiteralPEG(
          s,
          ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length())) + str
          );
      } else if (aux == 3) {
        return factory.newNonterminalPEG(s, genLit(arbitrary), l);
      } else {
        aux = arbitrary.nextInt(1);
        charAux = ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length() - 1));
        charAux2 = ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length() - 1));
        return factory.newRangePEG(s, new CharInterval(charAux, charAux2));
      }
    } else {
      aux = arbitrary.nextInt(6);
      switch (aux) {
      case 0:
        return factory.newChoicePEG(s, gen(h - 1, b), gen(h - 1, b));
      case 1:
        return factory.newAndPEG(s, gen(h - 1, b));
      case 2:
        return factory.newNotPEG(s, gen(h - 1, b));
      case 3:
        return factory.newSequencePEG(s, seqArray(arbitrary, h - 1));
      case 4:
        return factory.newStarPEG(s, gen(h - 1, b));
      case 5:
        return factory.newPositiveKleenePEG(s, gen(h - 1, b));
      default:
        break;
      }
    }
    return null;
  }

  public RulePEG execute(CharStream stream) {   
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
   RulePEG e = parser.production().rule;
   if(parser.getNumberOfSyntaxErrors() != 0 ||
    parser.getCurrentToken().getType() != Recognizer.EOF) {
    return null;
}
return e;
}


}
