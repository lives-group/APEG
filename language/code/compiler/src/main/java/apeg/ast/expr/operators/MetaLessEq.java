package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaLessEq extends MetaBinaryOP{

    private LessEq embeedNode;
    
    public MetaLessEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new LessEq(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public LessEq getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}