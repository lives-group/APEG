package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaCompose extends MetaBinaryOP{

    private Compose embeedNode;
    
    public MetaCompose(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Compose(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Compose getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}