package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public abstract class MetaBinaryOP extends MetaExpr{

    private MetaExpr left;
    private MetaExpr right;
    
    public MetaBinaryOP(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s);
        this.left = ml;
        this.right = mr;
    }
    public MetaExpr getLeft(){
        return left;
    }
    public MetaExpr getRight(){
        return right;
    }
    public abstract Expr getEmbeedNode();

}