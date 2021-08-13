package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class StrLit extends Expr{

    private String value;
    
    public StrLit(SymInfo s,String value){
        super(s);
        this.value = value;
    }
    public String getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return '\'' + value + '\''; }
}
