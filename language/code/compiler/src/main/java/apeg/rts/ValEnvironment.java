package apeg.rts;

public class ValEnvironment {
	
	private Value[] v;
	
	public ValEnvironment(int n) {
		v = new Value[n];
	}
	
	public void setAt(int i, Value y) {
		v[i] = y;
	}
	
	public Value getAt(int i) {
		return v[i];
	}

}
