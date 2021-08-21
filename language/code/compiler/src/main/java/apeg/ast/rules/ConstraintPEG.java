package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class ConstraintPEG extends APEG{

    private Expr expr;
    
    public ConstraintPEG(SymInfo s,Expr e){
        super(s);
        this.expr = e;
    }
    public Expr getExpr(){
        return expr;
    }
    public void accept(Visitor v){ v.visit(this); }
    
    public String toString(){
        return "{? " + expr.toString() +"}";
    } 

}
