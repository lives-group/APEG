package apeg.rts;

public class VString extends Value{

	String x;
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void set(Object o) {
		// TODO Auto-generated method stub
		x = (String) o;
	}

}
