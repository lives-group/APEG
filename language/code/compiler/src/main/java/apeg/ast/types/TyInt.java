package apeg.ast.types;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyInt extends Type {

    
    public TyInt(SymInfo s){
        super(s, "int");
    }
    public boolean match(Type t){
        return t instanceof TyInt;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }
    
    public String toString(){
         return "int";
    }    
}
