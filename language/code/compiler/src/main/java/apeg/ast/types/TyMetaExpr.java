package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMetaExpr extends Type{

    
    public TyMetaExpr(SymInfo s){
        super(s, "metaExpr");
    }
    public boolean match(Type t){
        return t instanceof TyMetaExpr;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
    
    public String toString(){
         return "Mexpr";
    }
}
