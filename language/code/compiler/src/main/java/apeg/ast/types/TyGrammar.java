package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyGrammar extends Type{

    
    public TyGrammar(SymInfo s){
        super(s);
    }
    public boolean match(Type t){
        return t instanceof TyGrammar;
    }

}