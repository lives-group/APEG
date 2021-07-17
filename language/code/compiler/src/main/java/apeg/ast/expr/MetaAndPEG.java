package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.AndPEG;
import apeg.util.SymInfo;

public class MetaAndPEG extends MetaAPEG{

    Expr e; 

    public MetaAndPEG(SymInfo s,Expr e){
        super(s);
        this.e = e;
    }
        
    public Expr getPeg(){ return e;}
    
    public void accept(Visitor v){ v.visit(this); }

}
