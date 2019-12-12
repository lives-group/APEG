package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaEquals extends MetaBinaryOP{

    private Equals embeedNode;
    
    public MetaEquals(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Equals(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Equals getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}