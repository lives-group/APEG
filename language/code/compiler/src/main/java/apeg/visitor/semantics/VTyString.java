package apeg.visitor.semantics;

public class VTyString extends VType{
	
private static VTyString instance = new VTyString();
	
	public static VTyString getInstance() {
		
		return instance;
	}
	
    private  VTyString(){
        super("string");
    }
    public boolean match(VType t){
        return t instanceof VTyString;
    }

}
