package apeg.rts;

public class VBool extends Value{
	
	boolean x;

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void set(Object o) {
		// TODO Auto-generated method stub
		x = (boolean) o;
		
	}

}
