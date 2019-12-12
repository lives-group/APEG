package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class BindPEG extends APEG{

    private Expr expr;
    private String attribute;
    
    public BindPEG(SymInfo s,String attribute,Expr e){
        super(s);
        expr = e;
    }
    public Expr getExpr(){
        return expr;
    }
    public String getAttribute(){
        return attribute;
    }
    public void accept(Visitor v){ v.visit(this); }

}