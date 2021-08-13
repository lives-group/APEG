package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.ast.expr.MetaExpr;
import apeg.util.SymInfo;

public class MetaMapAcces extends MetaExpr{

    private Expr map,index;
    
    public MetaMapAcces(SymInfo s,Expr map,Expr index){
        super(s);
        this.map = map;
        this.index = index;
    }
    public Expr getMap(){
        return map;
    }
    public Expr getIndex(){
        return index;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "([] " + map.toString() + " " + index.toString() + ")"; }
}
