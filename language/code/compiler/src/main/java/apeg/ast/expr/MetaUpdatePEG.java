package apeg.ast.expr;
import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.ast.rules.UpdatePEG;
import apeg.util.SymInfo;

public class MetaUpdatePEG extends MetaAPEG{

    private Expr e;
        
    public MetaUpdatePEG(SymInfo s,Expr e ){
        super(s);
        this.e = e;
    }
    
    public Expr getPegExpr(){ return e;}
    
    public void accept(Visitor v){ v.visit(this); }

}
