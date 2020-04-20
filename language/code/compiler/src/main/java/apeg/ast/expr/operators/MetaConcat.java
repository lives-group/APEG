package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaConcat extends MetaBinaryOP{

    private Concat embeedNode;
    
    public MetaConcat(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Concat(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Concat getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}