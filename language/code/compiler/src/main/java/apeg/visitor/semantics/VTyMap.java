package apeg.visitor.semantics;


public class VTyMap extends VType{
	

	private VType tyParameter;
		
    public  VTyMap(VType tyParameter){
        super("map");
        this.tyParameter = tyParameter;
        
    }
    public boolean match(VType t){
    	 if(t instanceof VTyMap ){
             return tyParameter.match(((VTyMap)t).getTyParameter());
          }
          return false;
    }

    public VType getTyParameter(){
        return tyParameter;
    }
}
