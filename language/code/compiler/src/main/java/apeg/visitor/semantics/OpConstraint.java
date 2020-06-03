package apeg.visitor.semantics;

public class OpConstraint extends Constraint {

	
	private String s;
	private VType t;

	public OpConstraint(String s , VType t) {
		
		this.s = s;
		this.t = t;
	}
	
	public String getOpName() {
		return s;
	}
	
	public VType getType() {
		return t;
	}
}
