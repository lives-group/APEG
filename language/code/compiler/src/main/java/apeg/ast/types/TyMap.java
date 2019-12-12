package apeg.ast.types;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class TyMap extends Type{

    private Type tyParameter;
    
    public TyMap(SymInfo s,Type tyParameter){
        super(s);
        this.tyParameter = tyParameter;
    }
    public boolean match(Type t){
        if(t instanceof TyMap ){
           return match(((TyMap)t).getTyParameter());
        }
        return false;
    }
    public Type getTyParameter(){
        return tyParameter;
    }

}