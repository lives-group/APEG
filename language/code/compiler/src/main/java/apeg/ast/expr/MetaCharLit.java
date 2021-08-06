package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaCharLit extends MetaExpr{

    private Expr e;
    
    public MetaCharLit(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    public Expr getExpr(){
        return e;
    }
    public void accept(Visitor v){ v.visit(this); }

}