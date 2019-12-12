package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public abstract class BinaryOP extends Expr{

    private Expr left;
    private Expr right;
    
    public BinaryOP(SymInfo s,Expr l,Expr r){
        super(s);
        left = l;
        right = r;
    }
    public Expr getLeft(){
        return left;
    }
    public Expr getRight(){
        return right;
    }

}