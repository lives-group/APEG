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
    	if (t instanceof VTyGrammar) {
    		return true;
    	}
    	else {
    		if(t instanceof VTyVar) {
    			return t.match(this);
    		}
    		else {
    			return false;
    		}
    	}
    }
    
    public boolean matchCT(VType t, CTM ct) {
    	
    	if(t instanceof VTyVar) {
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}
    	return match(t);
    }

	public boolean Unify (VType t) {

		if(t instanceof VTyGrammar) {

			return true;
		}
		else {
			if(t instanceof VTyVar) {
				if(((VTyVar)t).solve() == null) {
					((VTyVar)t).setInstance(this);
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		return false;

	}
}
