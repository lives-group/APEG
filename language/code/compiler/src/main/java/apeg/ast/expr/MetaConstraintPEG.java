package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.ConstraintPEG;
import apeg.util.SymInfo;

public class MetaConstraintPEG extends MetaAPEG{

    private ConstraintPEG embeedNode;
    
    public MetaConstraintPEG(SymInfo s,MetaExpr e){
        super(s);
        embeedNode = new ConstraintPEG(s,e.getEmbeedNode());
    }
    public ConstraintPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}