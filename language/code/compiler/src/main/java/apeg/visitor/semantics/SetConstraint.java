package apeg.visitor.semantics;

public class SetConstraint extends Constraint{
	
	private VTyVar v;
	private VType[] t;

	public SetConstraint(VTyVar v , VType[] t) {
		
		this.v = v;
		this.t = t;
	}
	
	public VTyVar getVarName() {
		return v;
	}
	
	public VType[] getSet() {
		return t;
	}
	public String toString() {
		String s =  v + " in { ";
		if(t.length > 0){
		  s += t[0].toString();
		  for(int i  = 1; i < t.length; i++){
		     s += ", " + t[i].toString();
		  }
		}
		return s + "} ";
	}
}
