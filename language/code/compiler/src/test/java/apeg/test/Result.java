package apeg.test;

public class Result<E> {
    /**
     * Value to indicate if the test executes without throws any excpetions
     */
    private boolean executed;
    /**
     * This value informs if the test executes as expected;
     */
    private boolean succeeds;
    /**
     * The result retorned by the test
     */
    private E result;
    /**
     * The expected result
     */
    private E expectedResult;

    public Result(boolean executed, boolean succeeds, E expectedResult, E result) {
	this.executed = executed;
	this.succeeds = succeeds;
	this.expectedResult = expectedResult;
	this.result = result;
    }

    public boolean isProcceeded() {
	return executed;
    }

    public boolean isSucceeded() {
	return succeeds;
    }

    public E getResultValue() {
	return result;
    }

    public E getExpectedResult() {
	return expectedResult;
    }
}
