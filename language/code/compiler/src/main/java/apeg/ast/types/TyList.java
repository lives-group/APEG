package apeg.ast.types;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyList extends Type{

    private Type tyParameter;
    
    public TyList(SymInfo s,Type tyParameter){
        super(s, "map");
        this.tyParameter = tyParameter;
    }
    public boolean match(Type t){
        if(t instanceof TyList ){
           return tyParameter.match(((TyList)t).getTyParameter());
        }
        return false;
    }
    public Type getTyParameter(){
        return tyParameter;
    }
    public void accept (Visitor v) {
    	v.visit(this);
    }

    public String toString(){
        return "list";
    }
}
