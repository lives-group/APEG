package apeg.visitor.semantics;


public abstract  class VType{

    private String name;
    protected VType(String name){
        this.name = name;
    }

    abstract public boolean match(VType t);
    abstract public boolean matchCT(VType t, CTM ct);

    /** Simplify the type by removing instanciated type varibales;
     */
    abstract public VType simplify();

    public abstract boolean Unify(VType t);

    public String getName() {
	    return name;
    }

    public String toString() {
    	return name;
    }

}
