package apeg.parser;

import java.util.List;
import java.util.ArrayList;
import apeg.*;

public class TestMachine {
    private List<TContainer> tests;

    public TestMachine(List tests) {
	this.tests = tests;
    }
    
    public void runTests() {
	Result r;
	for(TContainer test : tests) {
	    System.out.print("Testing " + test.getTestName() + " ..... ");
	    r = (Result)test.execute();
	    if(r.isSucceeded())
		System.out.println("[ OK ]");
	    else
		System.out.println("[FAIL]");
	}
    }

    public static void main(String args[]) {
	/**
	 * Create a set of tests
	 */
	List<TContainer> tests = new ArrayList<TContainer>();


	TestMachine t = new TestMachine(tests);
	t.runTests();
    }
}
