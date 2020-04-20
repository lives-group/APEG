package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class MapAcces extends Expr{

    private Expr map;
    private Expr index;
    
    public MapAcces(SymInfo s,Expr map,Expr index){
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

}