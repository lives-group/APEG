package apeg.rts;

public class AttrEnvironment {
	
	private Object[] v;
	
	public AttrEnvironment(int n) {
		v = new Object[n];
	}
	
	public void setAt(int i, Object y) {
		v[i] = y;
	}
	
	public Object getAt(int i) {
		return v[i];
	}
	
	public int size(){return v.length;} 

}
