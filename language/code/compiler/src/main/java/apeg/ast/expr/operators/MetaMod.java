package apeg.ast.expr.operators;
import apeg.ast.expr.Expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;
import apeg.ast.expr.MetaExpr;

public class MetaMod extends MetaBinaryOP{

    private Mod embeedNode;
    
    public MetaMod(SymInfo s,MetaExpr ml,MetaExpr mr){
        super(s,ml,mr);
        embeedNode = new Mod(s,ml.getEmbeedNode(),mr.getEmbeedNode());
    }
    public Mod getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
