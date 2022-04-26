package apeg.parser.generator;

import static org.assertj.core.api.Assertions.*;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.*;

import java.io.*;

import apeg.util.path.AbsolutePath;
import apeg.util.path.Path;
import apeg.util.path.RelativePath;

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

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import apeg.parser.expr.PegContainer;
import apeg.TContainer;

import java.util.List;
import java.io.File;
import net.jqwik.api.*;

//Class to be use to try the faild APEGS
class FaildSample{

	//colect the Faild Objects from the directory to try then again 
	void samples() throws IOException{
		try{ 
			File f = new File((new RelativePath(new AbsolutePath("."),
				"src/test/java/apeg/parser/generator/faild")).toString());

			File vet[] = f.listFiles();
			for (File file : vet) {

				tester(readBinFile(file.getCanonicalPath()));

				//if the object pass trough the test, it is deleted from the directory
				file.delete();
			}
		}
		catch (FileNotFoundException e){
			System.out.println("No Faild Samples\n");
		} 
		catch(IOException e){
			System.out.println("IOException\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Try to run the tests over the faild object again
	private void tester(APEG generated) throws Exception{
		Visitor prettyprint = new PrettyPrint(new RelativePath(new AbsolutePath("."),"src/main/templates/prettyprint.stg"));
		generated.accept(prettyprint);

		System.out.println(((PrettyPrint) prettyprint).renderPEG());
		System.out.println(generated.toString());

		CharStream stream = CharStreams.fromReader(new StringReader(((PrettyPrint) prettyprint).renderPEG()));

		TContainer<APEG> test = new PegContainer("Last faild try", stream);
		APEG e = test.execute();

		assertEquals( generated.toString(), e.toString());
	}

	//Read the Object from the path 
	private APEG readBinFile(String str) throws Exception {
		try(
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(str) ) 
			) 
		{
			APEG a =  (APEG) ois.readObject();
			return  a; 
		} 
		catch (Exception e){
			throw e;
		}
	}
}