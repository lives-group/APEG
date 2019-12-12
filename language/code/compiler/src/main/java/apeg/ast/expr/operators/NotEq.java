package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.Expr;

public class NotEq extends BinaryOP{

    
    public NotEq(SymInfo s,Expr l,Expr r){
        super(s,l,r);
    }
    public void accept(Visitor v){ v.visit(this); }

}