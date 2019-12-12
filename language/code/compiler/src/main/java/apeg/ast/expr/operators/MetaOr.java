package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaOr extends MetaBinaryOP{

    private Or embeedNode;
    
    public MetaOr(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Or(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Or getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}