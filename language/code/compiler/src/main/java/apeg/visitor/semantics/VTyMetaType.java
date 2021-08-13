package apeg.visitor.semantics;

public class VTyMetaType extends VType{
    
      
    
    private static VTyMetaType instance = new VTyMetaType();
    
    public static VTyMetaType getInstance() {
        
        return instance;
    }
    
    private  VTyMetaType(){
        super("metaType");
    }
    
    public boolean match(VType t){
        if (t == instance || t == TypeError.getInstance()) {
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
        if(t instanceof VTyMetaType) { return true; }
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
