package apeg.visitor.semantics;

public class VTyMetaExpr extends VType{
    
      
    
    private static VTyMetaExpr instance = new VTyMetaExpr();
    
    public static VTyMetaExpr getInstance() {
        
        return instance;
    }
    
    private  VTyMetaExpr(){
        super("metaExpr");
    }
    public boolean match(VType t){
        if (t == instance ||
            t == VTyMeta.getInstance() ||
            t == TypeError.getInstance()) {
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
        if(t instanceof VTyMetaExpr) { return true; }
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
