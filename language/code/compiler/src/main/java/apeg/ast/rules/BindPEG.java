package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class BindPEG extends APEG{

    private APEG peg;
    private String attribute;
    
    public BindPEG(SymInfo s,String attribute,APEG peg){
        super(s);
        expr = peg;
    }
    public APEG getExpr(){
        return peg;
    }
    public String getAttribute(){
        return attribute;
    }
    public void accept(Visitor v){ v.visit(this); }

}
