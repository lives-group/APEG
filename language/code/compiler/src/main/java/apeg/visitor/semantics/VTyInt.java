package apeg.visitor.semantics;

public class VTyInt extends VType{
	
	private static VTyInt instance = new VTyInt();
	
	public static VTyInt getInstance() {
		
		return instance;
	}
	
    private  VTyInt(){
        super("int");
    }
    public boolean match(VType t){
        return (t instanceof VTyInt) || (t instanceof VTyVar);
    }
    
    public boolean matchCT (VType t, CTM ct) {
    	
    	if(t instanceof VTyVar) {
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}
    	return match(t);
    }

	public boolean Unify (VType t) {

		if(t instanceof VTyInt) {

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
