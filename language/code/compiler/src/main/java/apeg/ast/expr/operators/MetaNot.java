package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class MetaNot extends BinaryOP{

    private Not embeedNode;
    
    public MetaNot(SymInfo s,Expr l,Expr r,Expr e){
        super(s,l,r);
        embeedNode = new Not(s,e);
    }
    public Not getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}