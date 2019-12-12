package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.util.SymInfo;

public class MetaMapLit extends MetaExpr{

    private MapLit embeedNode;
    private Pair<Expr,Expr>[] assocs;
    
    public MetaMapLit(SymInfo s,Pair<Expr,Expr>[] assocs){
        super(s);
        embeedNode = new MapLit(s,assocs);
    }
    public MapLit getEmbeedNode(){
        return embeedNode;
    }
    public Pair<Expr,Expr>[] getAssocs(){
        return assocs;
    }
    public void accept(Visitor v){ v.visit(this); }

}