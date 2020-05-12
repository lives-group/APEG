package apeg.visitor.semantics;

public class VTyInt extends VType{
	
	private static VTyInt instance = new VTyInt();
	
	public static VTyInt getInstance() {
		
		return instance;
	}
	
    private  VTyInt(){
        super("int");
    }
    public boolean match(VType t){
        return t instanceof VTyInt;
    }
}
