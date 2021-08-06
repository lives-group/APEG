package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.util.SymInfo;

public class MetaMapLit extends MetaExpr{

    private Expr e;
    
    public MetaMapLit(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    public Expr getExpr(){
        return e;
    }
    public void accept(Visitor v){ v.visit(this); }

}