package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaAdd extends MetaBinaryOP{

    private Add embeedNode;
    
    public MetaAdd(SymInfo s,Expr ml,Expr mr){
        super(s,ml,mr);
        embeedNode = new Add(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Add getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
