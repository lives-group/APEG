package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaFloatLit extends MetaExpr{

    private Expr e;
    
    public MetaFloatLit(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    public Expr getExpr(){
        return e;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return e.toString(); }
}
