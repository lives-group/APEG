package apeg.visitor.semantics;

public class VTyLang extends VType {
	
private static VTyLang instance = new VTyLang();
	
	public static VTyLang getInstance() {
		
		return instance;
	}
	
    private  VTyLang(){
        super("language");
    }
    public boolean match(VType t){
    	if (t instanceof VTyLang) {
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

	@Override
	public boolean matchCT(VType t, CTM ct) {
    	if(t instanceof VTyVar) {
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}
    	return match(t);
	}

	@Override
	public boolean Unify(VType t) {
		if(t instanceof VTyLang) {
			return true;
		}
		else {
			if(t instanceof VTyVar) {
				if(((VTyVar)t).solve() == null) {
					((VTyVar)t).setInstance(this);
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
	}

	public VType simplify(){
	    return this;
	}

}
