package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyLang extends Type{

    
    public TyLang(SymInfo s){
        super(s, "language");
    }
    public boolean match(Type t){
        return t instanceof TyLang;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
}
