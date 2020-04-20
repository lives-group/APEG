package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.ChoicePEG;
import apeg.util.SymInfo;

public class MetaChoicePEG extends MetaAPEG{

    private ChoicePEG embeedNode;
    
    public MetaChoicePEG(SymInfo s,MetaAPEG leftPeg,MetaAPEG rightPeg){
        super(s);
        embeedNode = new ChoicePEG(s,leftPeg.getEmbeedNode(),rightPeg.getEmbeedNode());
    }
    public ChoicePEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}