package apeg.rts;


public class Teste extends BaseParser{


	public Teste(String fname) {
		super(fname);
		startRule("root");
	}

	// S -> ABC / A*BD
	// A->  "bcd"  / "bc" (ab)*
	// B-> cc
	// C -> "."
	// D -> ";"

	public boolean s(){
		startRule("s");
		if(d1()) { return endSuccess();}
		alternate();
		if(d2()) { return endSuccess();}
		return endFail();
	}
	
    // Private nos auxiliares
	public boolean d1(){
		System.out.println("marca d1");
		mark();
		if(!a()) { 
			System.out.println("restaura d1 (a)");
			restore(); 
			return false;
		} 
		if(!b()) { 
			System.out.println("restaura d1 (b)");
			restore(); 
			return false;
		} 
		if(!c()) {
			System.out.println("restaura d1 (c)");
			restore(); 
			return false;
		} 	
		System.out.println("desmarca d1");
		unmark();

		return true;
	}


	public boolean d2(){
		System.out.println("marca d2");
		mark();
		if(!e1()) { 
			System.out.println("restaura d2 (e)");
			restore(); 
			return false;
		}
		if(!b()) {
			System.out.println("restaura d2 (b)");
			restore(); 
			return false;
		} 
		if(!d()) { 
			System.out.println("restaura d2 (d)");
			restore(); 
			return false;
		} 
		System.out.println("desmarca d2");
		unmark();
		return true;
	}
	
	
	public boolean e1(){
		while(a()) {}
		return true;
	}
	
	public boolean a(){
		startRule("a");
		if(!c1()){
			if(!c2()){
				return endFail();
			}
		}

		return endSuccess();
	}

	public boolean b(){
		startRule("b");
		if(!match("cc")){
			return endFail();	
		}
		return endSuccess();
	}

	public boolean c(){
		startRule("c");
		if(!match(".")){
			return endFail();	
		}
		return endSuccess();
	}

	public boolean d(){
		startRule("d");
		if(!match(";")){
			return endFail();	
		}
		return endSuccess();
	}


	public boolean c1(){
		System.out.println("marca1");
		mark();
		if(!match("bcd")){
			System.out.println("restaura1");
			restore();
			return false;	
		}
		System.out.println("desmarca1");
		unmark();
		return true;
	}

	public boolean c2(){
		System.out.println("marca2");
		mark();
		if(!match("bc")){
			System.out.println("restaura2");
			restore();
			return false;	
		}

		while(match("ab")){ }

		System.out.println("desmarca2");
		unmark();
		return true;
	}


}
