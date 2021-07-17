package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.PKleene;
import apeg.util.SymInfo;

public class MetaPKleene extends MetaAPEG{

    private Expr e;
    
    public MetaPKleene(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getPegExpr(){ return e;}

    public void accept(Visitor v){ v.visit(this); }

}
