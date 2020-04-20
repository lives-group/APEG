package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.AndPEG;
import apeg.util.SymInfo;

public class MetaAndPEG extends MetaAPEG{

    private AndPEG embeedNode;
    
    public MetaAndPEG(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new AndPEG(s,e.getEmbeedNode());
    }
    public AndPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}