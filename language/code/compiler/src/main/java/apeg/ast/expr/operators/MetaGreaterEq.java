package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaGreaterEq extends MetaBinaryOP{

    private GreaterEq embeedNode;
    
    public MetaGreaterEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new GreaterEq(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public GreaterEq getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}