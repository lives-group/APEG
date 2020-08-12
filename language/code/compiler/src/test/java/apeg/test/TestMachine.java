package apeg.test;

import java.util.List;
import java.util.ArrayList;

public class TestMachine {
    private List<TestContainer> tests;

    public TestMachine(List tests) {
	this.tests = tests;
    }
    
    public void runTests() {
	Result r;
	for(TestContainer test : tests) {
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
	List<TestContainer> tests = new ArrayList<TestContainer>();


	TestMachine t = new TestMachine(tests);
	t.runTests();
    }
}
