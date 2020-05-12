package apeg.visitor.semantics;

public class VTyFloat extends VType {
	
private static VTyFloat instance = new VTyFloat();
	
	public static VTyFloat getInstance() {
		
		return instance;
	}
	
    private  VTyFloat(){
        super("float");
    }
    public boolean match(VType t){
        return t instanceof VTyFloat;
    }

}
