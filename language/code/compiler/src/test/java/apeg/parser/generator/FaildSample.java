package apeg.parser.generator;

import static org.assertj.core.api.Assertions.*;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.*;

import java.io.*;

import apeg.util.path.AbsolutePath;
import apeg.util.path.Path;
import apeg.util.path.RelativePath;


//_____________________________________________________________________________________________________________________

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Recognizer;

import apeg.visitor.*;


// import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import apeg.parse.APEGLexer;
import apeg.parse.APEGParser;

import apeg.parser.expr.PegContainer;
import apeg.TContainer;

//_____________________________________________________________________________________________________________________


class FaildSample{
	@BeforeContainer
	@Example
	void beforeProperty() throws IOException{
    // Visitor prettyprint = new PrettyPrint(new RelativePath(new AbsolutePath("."),"src/main/templates/prettyprint.stg"));
    // APEG generated = new newNotPEG(new SymInfo(-1, -1),readerTxt());

    // generated.accept(prettyprint);

    // System.out.println(((PrettyPrint) prettyprint).renderPEG());
    // System.out.println(generated.toString()+"\n");

    // CharStream stream = CharStreams.fromReader(new StringReader(((PrettyPrint) prettyprint).renderPEG()));

    // TContainer<APEG> test = new PegContainer("Rodando teste", stream);
    // APEG e = test.execute();

    // assertEquals( generated.toString(), e.toString());
		String str=readerTxt();
		System.out.println("_____________________________________________________________________________________\n"
			+str +
			"\n_____________________________________________________________________________________");
		assertEquals( str,str+" 1");
	}

	private String readerTxt() throws IOException {
		try{
			RelativePath rpth = new RelativePath(
				new AbsolutePath("."),"src/test/java/apeg/parser/generator/outputFaildSamples.txt");
			FileReader fr = new FileReader(rpth.getAbsolutePath());
			String data="";
			int i;
			while((i=fr.read())!= -1){
				data+= (char)+i +"";
			}
			fr.close();
			return data;
		}
		catch (Exception ex){
			return "";
		}
	}


}