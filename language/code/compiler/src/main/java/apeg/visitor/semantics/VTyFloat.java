package apeg.visitor.semantics;

public class VTyFloat extends VType {
	
private static VTyFloat instance = new VTyFloat();
	
	public static VTyFloat getInstance() {
		
		return instance;
	}
	
    private  VTyFloat(){
        super("float");
    }
    public boolean match(VType t){
    	return (t instanceof VTyFloat) || (t instanceof VTyVar);
    }
    
    public boolean matchCT(VType t, CTM ct) {
    	
    	if(t instanceof VTyVar) {
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}
    	return match(t);
    }

	public boolean Unify (VType t) {

		if(t instanceof VTyFloat) {

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
