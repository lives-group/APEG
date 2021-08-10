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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unify(VType t) {
		// TODO Auto-generated method stub
		return false;
	}

	public VType simplify(){
	    return this;
	}

}
