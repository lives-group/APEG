package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class BoolLit extends Expr{

    private boolean value;
    
    public BoolLit(SymInfo s,boolean value){
        super(s);
        this.value = value;
    }
    public boolean getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString() {return value ? "true" : "false";}

}
