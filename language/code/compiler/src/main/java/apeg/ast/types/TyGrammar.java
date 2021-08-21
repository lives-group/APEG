package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyGrammar extends Type{

    
    public TyGrammar(SymInfo s){
        super(s, "grammar");
    }
    public boolean match(Type t){
        return t instanceof TyGrammar;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
    
    public String toString(){
         return "gram";
    }    
}
