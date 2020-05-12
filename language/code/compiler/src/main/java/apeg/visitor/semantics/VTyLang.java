package apeg.visitor.semantics;

public class VTyLang extends VType {
	
private static VTyLang instance = new VTyLang();
	
	public static VTyLang getInstance() {
		
		return instance;
	}
	
    private  VTyLang(){
        super("language");
    }
    public boolean match(VType t){
        return t instanceof VTyLang;
    }

}
