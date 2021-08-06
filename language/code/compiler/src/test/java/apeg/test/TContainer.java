package apeg.test;

/***
 * An interface for defining a test for each compiler phase
 * It represents an individual sample test
 */
public abstract class TContainer<E> {
    protected E resultValue;
    protected String name;


    public TContainer(String name) {
	    this.name = name;
    }
    
    /**
     *This function encapsulates the test
     */
    public abstract E execute();

    /**
     * Get the test name
     */
    public String getTestName() {
	   return name;
    }
}

