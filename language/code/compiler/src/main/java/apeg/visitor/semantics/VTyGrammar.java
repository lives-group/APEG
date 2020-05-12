package apeg.visitor.semantics;

public class VTyGrammar extends VType{
	
	  
	
	private static VTyGrammar instance = new VTyGrammar();
	
	public static VTyGrammar getInstance() {
		
		return instance;
	}
	
    private  VTyGrammar(){
        super("Grammar");
    }
    public boolean match(VType t){
        return t instanceof VTyGrammar;
    }
}
