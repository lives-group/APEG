package apeg.visitor.semantics;

public class VTyChar extends VType {
	
private static VTyChar instance = new VTyChar();
	
	public static VTyChar getInstance() {
		
		return instance;
	}
	
    private  VTyChar(){
        super("char");
    }
    public boolean match(VType t){
        return t instanceof VTyChar;
    }

}
