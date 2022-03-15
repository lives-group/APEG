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
    
	public boolean matchCT(VType t, CTM ct) {
		if(t instanceof VTyVar) {
    		ct.addConstraint(new VarConstraint((VTyVar)t, this));
    		return true;
    	}else if ((t instanceof VTyMap) && tyParameter instanceof VTyVar ) {
            VTyMap tym = (VTyMap) t;
            ct.addConstraint(new VarConstraint( (VTyVar)tyParameter, tym.tyParameter));
            return true;
    	}else if( (t instanceof VTyMap) &&  ((VTyMap) t).tyParameter instanceof VTyVar  ){
    	    VTyMap tym = (VTyMap) t;
            ct.addConstraint(new VarConstraint( (VTyVar)tym.tyParameter, tyParameter));
            return true;
    	}
    	return match(t);
	}
	
	public boolean Unify(VType t) {
		if(t instanceof VTyMap){
            VTyMap tym = (VTyMap)t;
		    return tyParameter.Unify( tym.tyParameter);
		}
		return false;
	}
	
	public String toString() {
    	return "{ " + tyParameter.toString() + " }" ;
    }

    public VType simplify(){
	    tyParameter = tyParameter.simplify();
	    return this;
	}
}
