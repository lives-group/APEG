package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.NotPEG;
import apeg.util.SymInfo;

public class MetaNotPEG extends MetaAPEG{

    private NotPEG embeedNode;
    
    public MetaNotPEG(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new NotPEG(s,e.getEmbeedNode());
    }
    public NotPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}