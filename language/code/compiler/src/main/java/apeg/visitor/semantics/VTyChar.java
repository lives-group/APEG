package apeg.visitor.semantics;

public class VTyChar extends VType {

	private static VTyChar instance = new VTyChar();

	public static VTyChar getInstance() {

		return instance;
	}

	private  VTyChar(){
		super("char");
	}
	public boolean match(VType t){
    	if (t == instance || t == TypeError.getInstance()) {
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

		if(t instanceof VTyChar) {

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
