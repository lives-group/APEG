package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyFloat extends Type{

    
    public TyFloat(SymInfo s){
        super(s);
    }
    public boolean match(Type t){
        return t instanceof TyFloat;
    }

}