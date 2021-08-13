package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.util.SymInfo;

public class MapLit extends Expr{

    private Pair<Expr,Expr>[] assocs;
    
    public MapLit(SymInfo s,Pair<Expr,Expr>[] assocs){
        super(s);
        this.assocs = assocs;
    }
    public Pair<Expr,Expr>[] getAssocs(){
        return assocs;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        String res = "({:";

        for(Pair<Expr, Expr> p : assocs){
            res += " " + p.getFirst().toString() + ", " + p.getSecond().toString();
        }

        return res + ")";
    }
}
