package apeg.visitor.semantics;


public class VTyList extends VType{
	

	private VType tyParameter;
		
    public  VTyList(VType tyParameter){
        super("list");
        this.tyParameter = tyParameter;
        
    }
    public boolean match(VType t){
    	 if(t instanceof VTyList ){
             return tyParameter.match(((VTyList)t).getTyParameter());
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
    	}else if ((t instanceof VTyList) && tyParameter instanceof VTyVar ) {
            VTyList tym = (VTyList) t;
            ct.addConstraint(new VarConstraint( (VTyVar)tyParameter, tym.tyParameter));
            return true;
    	}else if( (t instanceof VTyList) &&  ((VTyList) t).tyParameter instanceof VTyVar  ){
    	    VTyList tym = (VTyList) t;
            ct.addConstraint(new VarConstraint( (VTyVar)tym.tyParameter, tyParameter));
            return true;
    	}
    	return match(t);
	}
	
	public boolean Unify(VType t) {
		if(t instanceof VTyList){
            VTyList tym = (VTyList)t;
		    return tyParameter.Unify( tym.tyParameter);
		}
		return false;
	}
	
	public String toString() {
    	return "[ " + tyParameter.toString() + " ]" ;
    }

    public VType simplify(){
	    tyParameter = tyParameter.simplify();
	    return this;
	}
}
