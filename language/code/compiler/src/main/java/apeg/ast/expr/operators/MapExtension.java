package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class MapExtension extends Expr{

    private Expr map;
    private Expr key;
    private Expr value;
    
    public MapExtension(SymInfo s,Expr map,Expr key,Expr value){
        super(s);
        this.map = map;
        this.key = key;
        this.value = value;
    }
    public Expr getMap(){
        return map;
    }
    public Expr getKey(){
        return key;
    }
    public Expr getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "([->] " + map.toString() + " " + key.toString() + " " + value.toString() + ")"; }
}
