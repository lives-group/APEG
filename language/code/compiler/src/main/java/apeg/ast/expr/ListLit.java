package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.util.SymInfo;
import java.util.ArrayList;

public class ListLit extends Expr{

    private ArrayList<Expr> elems;
    
    public ListLit(SymInfo s,ArrayList<Expr> elems){
        super(s);
        this.elems = elems;
    }

    public ArrayList<Expr> getElems(){
        return elems;
    }

    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        String res = "([:";
        for(Expr p : elems){
            res += " " + p.toString();
        }
        return res + ":]";
    }
}
