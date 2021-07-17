package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaVar extends MetaExpr{

    private Expr e;
    
    public MetaVar(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e;}

    public void accept(Visitor v){ v.visit(this); }
    
    public void setE(Expr e){
        this.e = e;
    }

}