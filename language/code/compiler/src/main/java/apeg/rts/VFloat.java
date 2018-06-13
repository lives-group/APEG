package apeg.rts;

public class VFloat extends Value {

	float x;
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void set(Object o) {
		// TODO Auto-generated method stub
		x = (float) o;
	}

}
