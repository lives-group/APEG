package apeg.rts;

public class VInt extends Value{

	int x;
	
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void set(Object o) {
		// TODO Auto-generated method stub
		x = ((Integer)o).intValue();
	}

}
