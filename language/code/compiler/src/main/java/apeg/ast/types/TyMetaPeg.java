package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMetaPeg extends Type{

    
    public TyMetaPeg(SymInfo s){
        super(s, "metaPeg");
    }
    public boolean match(Type t){
        return t instanceof TyMetaPeg;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
}
