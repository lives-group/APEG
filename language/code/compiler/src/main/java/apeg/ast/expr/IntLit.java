package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class IntLit extends Expr{

    private int value;
    
    public IntLit(SymInfo s,int value){
        super(s);
    }
    public int getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

}