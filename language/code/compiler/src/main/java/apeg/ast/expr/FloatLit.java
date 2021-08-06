package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class FloatLit extends Expr{

    private float value;
    
    public FloatLit(SymInfo s,float value){
        super(s);
        this.value = value;
    }
    public float getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString() {return String.valueOf(value);}

}
