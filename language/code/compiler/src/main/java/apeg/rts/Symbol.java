package apeg.rts;



public abstract class Symbol{

	
	private int line;
	private int column;
	
	public Symbol(int l, int c){
		line = l;
		column = c;
	}
	
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	
	public void pprint() { pprint(0); }
	
	abstract protected void pprint(int i);
	
}
