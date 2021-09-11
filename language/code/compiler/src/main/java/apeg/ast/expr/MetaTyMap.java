package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.types.Type;
import apeg.ast.types.TyMap;
import apeg.util.SymInfo;

public class MetaTyMap extends MetaType{
   
    private Expr e;
    
    public MetaTyMap(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e;}

    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "'map"; }  
}
