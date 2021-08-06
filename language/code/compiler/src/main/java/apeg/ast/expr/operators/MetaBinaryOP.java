package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public abstract class MetaBinaryOP extends MetaExpr{

    private Expr left,right;
    
    public MetaBinaryOP(SymInfo s,Expr left,Expr right){
        super(s);
        this.left = left;
        this.right = right;
    }
    public Expr getLeft(){
        return left;
    }
    public Expr getRight(){
        return right;
    }


}
