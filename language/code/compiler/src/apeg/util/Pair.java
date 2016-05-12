package apeg.util;

public class Pair<A,B> {
	private A x;
	private B y;
	
	public Pair(A x, B y) {
		this.x = x;
		this.y = y;
	}
	
	public A getFirst() {
		return x;
	}
	
	public B getSecond() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pair) {
			Pair<A,B> pair = (Pair<A,B>) obj;
			return x.equals(pair.x) && y.equals(pair.y);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x.toString() + ", " + y.toString() + ")";
	}
	

}
