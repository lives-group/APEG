package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public class MetaMapAcces extends MetaExpr{

    private MapAcces embeedNode;
    
    public MetaMapAcces(SymInfo s,Expr map,Expr index){
        super(s);
        embeedNode = new MapAcces(s,map,index);
    }
    public MapAcces getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}