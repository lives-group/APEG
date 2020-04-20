package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaGreater extends MetaBinaryOP{

    private Greater embeedNode;
    
    public MetaGreater(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Greater(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Greater getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}