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
		return (t instanceof VTyChar) || (t instanceof VTyVar);
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
