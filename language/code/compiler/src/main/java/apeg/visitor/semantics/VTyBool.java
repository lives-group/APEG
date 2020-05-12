package apeg.visitor.semantics;

public class VTyBool extends VType{
	
private static VTyBool instance = new VTyBool();
	
	public static VTyBool getInstance() {
		
		return instance;
	}
	
    private  VTyBool(){
        super("boolean");
    }
    public boolean match(VType t){
        return t instanceof VTyBool;
    }
}
