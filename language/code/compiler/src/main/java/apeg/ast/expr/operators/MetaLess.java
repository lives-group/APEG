package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaLess extends MetaBinaryOP{

    private Less embeedNode;
    
    public MetaLess(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Less(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Less getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}