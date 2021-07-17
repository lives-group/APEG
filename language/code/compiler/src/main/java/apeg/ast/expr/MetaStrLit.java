package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.MetaASTNode;

public class MetaStrLit extends MetaExpr{

    private Expr e;
    
    public MetaStrLit(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e; }
    
    public void accept(Visitor v){ v.visit(this); }
}
