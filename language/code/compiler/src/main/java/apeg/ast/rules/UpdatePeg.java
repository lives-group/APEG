package apeg.ast.rules;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.ast.expr.Attribute;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class UpdatePeg extends APEG{

    private List<Pair<Attribute,Expr>> assigs;
    
    public UpdatePeg(SymInfo s,List<Pair<Attribute,Expr>> assigs){
        super(s);
        this.assigs = assigs;
    }
    public List<Pair<Attribute,Expr>> getAssigs(){
        return assigs;
    }
    public void accept(Visitor v){ v.visit(this); }

}