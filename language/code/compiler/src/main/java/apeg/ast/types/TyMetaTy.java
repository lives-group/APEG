package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMetaTy extends Type{

    
    public TyMetaTy(SymInfo s){
        super(s, "metaTy");
    }
    public boolean match(Type t){
        return t instanceof TyMetaTy;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
    
    public String toString(){
         return "Mtype";
    }    
}
