package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public class MetaMapExtension extends MetaExpr{

    private MapExtension embeedNode;
    
    public MetaMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        super(s);
        embeedNode = new MapExtension(s,map,key,value);
    }
    public MapExtension getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}