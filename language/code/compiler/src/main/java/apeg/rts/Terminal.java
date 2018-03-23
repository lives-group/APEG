package apeg.rts;


public class Terminal extends Symbol {

	private String symb;
	
	public Terminal(int l, int c, String s) {
		super(l,c);
		symb = s;
		
	}

	public String getSymb() {
		return symb;
	}

	protected void pprint(int i){
		for(int j = 0; j < i; j++) {System.out.print(" ");};
		System.out.println("[ ] " + symb);
	}

}
