package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.OptionalPEG;
import apeg.util.SymInfo;

public class MetaOptionalPEG extends MetaAPEG{

    private OptionalPEG embeedNode;
    
    public MetaOptionalPEG(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new OptionalPEG(s,e.getEmbeedNode());
    }
    public OptionalPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}