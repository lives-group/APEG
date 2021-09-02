package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.ConstraintPEG;
import apeg.util.SymInfo;

public class MetaConstraintPEG extends MetaAPEG{

    private Expr e;
    
    public MetaConstraintPEG(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    public Expr getExpr(){
        return e;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        return "'{? " + e.toString() + "}";
    }
}
