package apeg.visitor.Environments;

public class NTInfo {
	
	private NTType sig;
	private Environment<String, VarType> locals;
	
	
	public NTInfo(NTType sig, Environment<String, VarType> locals){
		this.sig = sig;
		this.locals = locals;
	}

	public NTType getSig() {
		return sig;
	}

	public Environment<String, VarType> getLocals() {
		return locals;
	}
	
	public String toString(){
		String s = "";
		s += "    sig: " + sig.toString() + "\n";
		s += "    locals: \n"; 
		s += locals.toStringf("    ");
		
		return s;
	}

}
