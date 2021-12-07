package apeg.visitor.semantics;

public class VTyMeta extends VType{
    
      
    
    private static VTyMeta instance = new VTyMeta();
    
    public static VTyMeta getInstance() {
        
        return instance;
    }
    
    private  VTyMeta(){
        super("meta");
    }
    
    public boolean match(VType t){
        if ((t == VTyMetaExpr.getInstance()) ||
            (t == VTyMetaPeg.getInstance())  ||
            (t == VTyMetaType.getInstance()) ||
            (t == TypeError.getInstance()) ) {
            return true;
        }
        else {
            if(t instanceof VTyVar) {
                return t.match(this);
            }
            else {
                return false;
            }
        }
    }
    
    public boolean matchCT(VType t, CTM ct) {
        
        if(t instanceof VTyVar) {
            ct.addConstraint(new VarConstraint((VTyVar)t, this));
            return true;
        }
        return match(t);
    }

    public boolean Unify (VType t) {
        if(t instanceof VTyMetaExpr || 
           t instanceof VTyMetaPeg  ||
           t instanceof VTyMetaType) { return true; }
        else {
            if(t instanceof VTyVar) {
                if(((VTyVar)t).solve() == null) {
                    ((VTyVar)t).setInstance(this);
                    return true;
                }
                else { return false;}
            }
            else {return false;}
        }
    }

    public VType simplify(){
        return this;
    }
}
