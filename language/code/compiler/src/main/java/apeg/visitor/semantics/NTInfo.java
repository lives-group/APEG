package apeg.visitor.semantics;

import apeg.util.Environment;

public class NTInfo {
	
	private NTType sig;
	private Environment<String, VType> locals;
	
	
	public NTInfo(NTType sig, Environment<String, VType> locals){
		this.sig = sig;
		this.locals = locals;
	}

	public NTType getSig() {
		return sig;
	}

	public Environment<String, VType> getLocals() {
		return locals;
	}
	
	public String toString(){
		String s = "";
		s += "    sig: " + sig.toString() + "\n";
		s += "    locals: \n"; 
		s += locals.toStringf("    ");
		
		return s;
	}

	public void simplify(){
	     System.out.println("Replacing signature for " + sig.toString());
	     sig = (NTType)sig.simplify();
	     locals.replace((k,v) -> v.simplify());
	}

}
