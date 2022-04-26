package apeg.parser.generator;

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
import net.jqwik.api.lifecycle.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.StringReader;

import java.io.IOException;
import java.io.FileNotFoundException;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

import apeg.parser.expr.PegContainer;
import apeg.TContainer;


import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class PEGGenerator {

  private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
  private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String ALL_MY_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS;
  
  private ASTFactory factory;

  public PEGGenerator() {
    // factory = new ASTFactoryImpl();
  }
  
  //Recieve a APEG and stringfy it, then try do parse the result to see if it matches with the original AST  
  @Property(tries = 10000, generation = GenerationMode.RANDOMIZED, afterFailure = AfterFailureMode.PREVIOUS_SEED)
  void gerador(@ForAll("generateAPEG") APEG generated) throws IOException{

    Visitor prettyprint = new PrettyPrint(new RelativePath(new AbsolutePath("."),"src/main/templates/prettyprint.stg"));

    generated.accept(prettyprint);

    // System.out.println(((PrettyPrint) prettyprint).renderPEG());
    // System.out.println(generated.toString()+"\n");

    CharStream stream = CharStreams.fromReader(new StringReader(((PrettyPrint) prettyprint).renderPEG()));

    TContainer<APEG> test = new PegContainer("Testando gerador", stream);
    APEG e = test.execute();

    if(e == null || !generated.toString().equals( e.toString()) )
    {
      writeBinFile(generated);
    }
    assertEquals( generated.toString(), e.toString());
  }

  //Generates a AST of APEG
  @Provide
  public Arbitrary<APEG> generateAPEG() {
    factory = new ASTFactoryImpl();
    return Arbitraries.randomValue(random -> gen(random.nextInt(2)+1,true));
  }

  //Function to write the AST object, that faild, in a file 
  private void writeBinFile(APEG data) throws IOException {

    try( ObjectOutputStream oos = new ObjectOutputStream(
      new FileOutputStream(path().getRelativePath()))
    ){
      oos.writeObject(data);
    }  catch (IOException e){
      return;
    }
  }

  //function to return the relative path of the directory and name of the file to be printed the faild object
  public RelativePath path(){
    Calendar c =Calendar.getInstance();
    String str= c.getTime().toString().replaceAll(" ","_");
    RelativePath pth= (new RelativePath(new AbsolutePath("."),
      "src/test/java/apeg/parser/generator/faild/outputFaildSamples_"+ str.replaceAll(":","-") +".bin"));
    return pth;
  }


  public String genLit(Random arbitrary) {
    String str = "";
    int j=0;
    do{
      j = arbitrary.nextInt(3)+1;
    }while (j == 3);
    for (int i = 0; i < j; i++) {
      str += ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length()));
    }

    return str;
  }

  //generate a sequence of AST rules
  public APEG[] seqArray(Random arbitrary, int h) {
    int n = arbitrary.nextInt(4) + 2;
    APEG a[] = new APEG[n];
    for (int i = 0; i < n; i++) {
      a[i] = gen(h, false);
    }
    return a;
  }

  //Generate the AST tree recieving as parameters:
  // h (high of the tree) and b (to kown if the tree will have a bind rule or not) 
  public APEG gen(int h, Boolean b) {
    SymInfo s = new SymInfo(-1, -1);
    int aux;
    //for the NonterminalPeg parameters
    List<Expr> l = new LinkedList();
    //Variable to sort the rules
    Random arbitrary = new Random();
    // Se a expressao vai ter bind ou nao
    //Check the bind status
    if (b) {
    //nextInt(n) 0 inclusive, n exclusive
      aux = arbitrary.nextInt(2);
      //If the bind will be in the tree or not
      if (aux == 1) {
        Attribute attribute = new Attribute(s, genLit(arbitrary));
        return factory.newBindPEG(s, attribute, gen(h, false));
      }
    }
    b = false;
    //Base case 
    if (h <= 0) {
      aux = arbitrary.nextInt(5);
      char charAux, charAux2;
      String str = "";
      switch (aux){
      case 0:     
        return factory.newAnyPEG(s);
      case 1:
        return factory.newLambdaPEG(s);
      case 2:        
        return factory.newLiteralPEG(s,ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length())) + str);
      case 3:
        return factory.newNonterminalPEG(s, genLit(arbitrary), l);
      default:
        charAux = ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length() - 1));
        charAux2 = ALL_MY_CHARS.charAt(arbitrary.nextInt(ALL_MY_CHARS.length() - 1));
        return factory.newRangePEG(s, new CharInterval(charAux, charAux2));
      } 
    }
    //call recursivly this own function
    else {
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
}