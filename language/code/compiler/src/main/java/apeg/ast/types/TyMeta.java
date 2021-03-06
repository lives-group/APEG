package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMeta extends Type{

    
    public TyMeta(SymInfo s){
        super(s, "meta");
    }
    public boolean match(Type t){
        return t instanceof TyMeta;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
}
