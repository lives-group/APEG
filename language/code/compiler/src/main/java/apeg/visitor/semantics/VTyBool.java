package apeg.visitor.semantics;

public class VTyBool extends VType{
	
private static VTyBool instance = new VTyBool();
	
	public static VTyBool getInstance() {
		
		return instance;
	}
	
    private  VTyBool(){
        super("boolean");
    }
    public boolean match(VType t){
    	return (t instanceof VTyBool) || (t instanceof VTyVar);
    }
    
    public boolean matchCT(VType t, CTM ct) {
    	
    	if(t instanceof VTyVar) {
    
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;	
    	}
		return match(t);
    	
    }
    
    public boolean Unify (VType t) {
    	
    	if(t instanceof VTyBool) {
    		
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