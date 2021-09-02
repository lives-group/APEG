package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;
import java.util.ArrayList;

public class ListAcces extends Expr{

    private Expr list;
    private Expr index;
    
    public ListAcces(SymInfo s,Expr list,Expr index){
        super(s);
        this.list = list;
        this.index = index;
    }
    public Expr getList(){ return list; }
    public Expr getIndex(){ return index;}
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "(!! " + list.toString() + " " + index.toString() + ")"; }
}
