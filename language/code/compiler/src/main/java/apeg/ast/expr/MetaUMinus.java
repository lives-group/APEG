package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.operators.BinaryOP;
import apeg.ast.expr.operators.UMinus;
import apeg.util.SymInfo;

public class MetaUMinus extends MetaExpr{

    private Expr e;
    
    public MetaUMinus(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e;}
    
    public void accept(Visitor v){ v.visit(this); }

}