package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.MetaASTNode;

public class MetaStrLit extends MetaExpr{

    private Expr v;
    
    public MetaStrLit(SymInfo s,Expr value){
        super(s);
        v = value;
    }
    
    public Expr getExpr(){ return v; }
    
    public void accept(Visitor v){ v.visit(this); }
}
