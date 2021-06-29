package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyLan extends Type{

    
    public TyLan(SymInfo s){
        super(s, "lan");
    }
    public boolean match(Type t){
        return t instanceof TyLan;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
}
