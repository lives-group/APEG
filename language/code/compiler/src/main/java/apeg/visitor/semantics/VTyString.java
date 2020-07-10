package apeg.visitor.semantics;

public class VTyString extends VType{
	
private static VTyString instance = new VTyString();
	
	public static VTyString getInstance() {
		
		return instance;
	}
	
    private  VTyString(){
        super("string");
    }
    public boolean match(VType t){
    	return (t instanceof VTyString) || (t instanceof VTyVar);
    }
    
    public boolean matchCT (VType t, CTM ct) {
    	
    	if(t instanceof VTyVar) {
    		
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}
    	return match(t);
    }

	public boolean Unify (VType t) {

		if(t instanceof VTyString) {

			return true;
		}
		else {
			if(t instanceof VTyVar) {
				if((VTyVar)t.solve() == null) {
					t.setInstance(this);
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
