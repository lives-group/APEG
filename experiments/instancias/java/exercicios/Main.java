import java.io.FileReader;
import xtc.parser.Result;


public class Main {

    public static void main(String args[]) throws Exception {
        FileReader file = new FileReader("Main.java");
        javarats parser = new javarats(file, "input");
	long beginTime = System.currentTimeMillis();
        Result r = parser.pcompilation_unit(0);
	long endTime = System.currentTimeMillis();
	System.out.println("Time in millis: " + (endTime - beginTime));
        if(!(r instanceof xtc.parser.ParseError))
            System.out.println("OK");
        else
            System.out.println("NOT OK");
    }
}
