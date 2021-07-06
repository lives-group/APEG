package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.AndPEG;
import apeg.util.SymInfo;

public class MetaAndPEG extends MetaAPEG{

    private AndPEG embeedNode;
    Expr e; 
    
    public MetaAndPEG(SymInfo s,Expr e){
        super(s);
        embeedNode = new AndPEG(s,e.getEmbeedNode());
        this.e = e;
    }
    
    public AndPEG getEmbeedNode(){
        return embeedNode;
    }
    
    public Expr getPeg(){ return e;}
    
    public void accept(Visitor v){ v.visit(this); }

}
