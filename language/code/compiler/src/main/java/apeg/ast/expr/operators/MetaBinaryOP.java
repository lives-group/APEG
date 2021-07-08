package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public abstract class MetaBinaryOP extends MetaExpr{

    private Expr left;
    private Expr right;
    
    public MetaBinaryOP(SymInfo s,Expr ml,Expr mr){
        super(s);
        this.left = ml;
        this.right = mr;
    }
    public Expr getLeft(){
        return left;
    }
    public Expr getRight(){
        return right;
    }


}
