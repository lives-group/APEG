package apeg.visitor.semantics;

public class VarConstraint extends Constraint{
	
	private VTyVar v;
	private VType t;

	public VarConstraint(VTyVar v , VType t) {
		
		this.v = v;
		this.t = t;
	}
	
	public VTyVar getVarName() {
		return v;
	}
	
	public VType getType() {
		return t;
	}
}
