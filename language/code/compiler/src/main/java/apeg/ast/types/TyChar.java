package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyChar extends Type{

    
    public TyChar(SymInfo s){
        super(s);
    }
    public boolean match(Type t){
        return t instanceof TyChar;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }

}