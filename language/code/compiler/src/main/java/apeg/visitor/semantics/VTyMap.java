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
	@Override
	public boolean matchCT(VType t, CTM ct) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean Unify(VType t) {
		// TODO Auto-generated method stub
		return false;
	}
}
