package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaNotEq extends MetaBinaryOP{

    private NotEq embeedNode;
    
    public MetaNotEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new NotEq(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public NotEq getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}