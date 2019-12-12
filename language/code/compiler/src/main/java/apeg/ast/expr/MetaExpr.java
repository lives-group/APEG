package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class MetaExpr extends Expr{

    
    public MetaExpr(SymInfo s){
        super(s);
    }
    public abstract Expr getEmbeedNode();

}