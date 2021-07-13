package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.NotPEG;
import apeg.util.SymInfo;
import apeg.ast.MetaASTNode;

public class MetaNotPEG extends MetaAPEG{

    private Expr e;
    
    public MetaNotPEG(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e; }
    
    public void accept(Visitor v){ v.visit(this); }

}
