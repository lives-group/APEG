package apeg.visitor.semantics;


public class VTyVar extends VType {

	private VType t;

	public VTyVar( String name ) {
		super(name);
	}

	@Override
	public boolean match(VType t) {
		if(this.t == null) {
		    return true;
		}
		else {
			return this.t.match(t);
		}
	}
		

	@Override
	public boolean matchCT(VType t, CTM ct) {
		ct.addConstraint(new VarConstraint(this, t));
		return match(t);
	}

	public VType solve() {
		return t;
	}

	public void setInstance(VType t) {
		this.t = t;
	}


	@Override
	public boolean Unify(VType t) {
		if(t instanceof VTyVar) {
			if(((VTyVar)t).solve() == null) {
				if(solve() == null) {
					return false;
				}
				else {
					((VTyVar)t).setInstance(solve());
					return true;
				}
			}
			else {
				if(solve() == null) {
					setInstance(((VTyVar)t).solve());
					return true;
				}
				else {
					return match(((VTyVar)t).solve());
				}
			}
		}
		else {
			if(solve() == null) {
				setInstance(t);
				return true;
			}
			else {
				return match(t);
			}
		}
	}
	
	public String toString() {
		if(t == null) {
			 return super.toString();
		}
		else {
			return t.toString() + ":" + getName();
		}
	}
	
    public VType simplify(){
	    return (t == null) ? this : t.simplify();
	}


}
