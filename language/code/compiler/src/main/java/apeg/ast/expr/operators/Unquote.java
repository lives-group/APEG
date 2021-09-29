package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class Unquote extends Expr{

    private Expr expr;
    
    public Unquote(SymInfo s,Expr e){
        super(s);
        this.expr = e;
    }
    public Expr getExpr(){
        return expr;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString() {return "(# " + getExpr().toString() + ")"; }

}

