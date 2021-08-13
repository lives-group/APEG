package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaMod extends MetaBinaryOP{

    public MetaMod(SymInfo s,Expr ml,Expr mr){
        super(s,ml,mr);
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "'(% " + getLeft().toString() + " " + getRight().toString() + ")"; }
}
