package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMeta extends Type{

    
    public TyMeta(SymInfo s){
        super(s);
    }
    public boolean match(Type t){
        return t instanceof TyMeta;
    }

}