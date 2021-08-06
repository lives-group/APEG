package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.SeqPEG;
import apeg.util.SymInfo;

public class MetaSeqPEG extends MetaAPEG{

    private Expr e;
    
    public MetaSeqPEG(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
    
    public Expr getExpr(){ return e;}

    public void accept(Visitor v){ v.visit(this); }

}