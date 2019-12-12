package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.AnyPEG;
import apeg.util.SymInfo;

public class MetaAnyPEG extends MetaAPEG{

    private AnyPEG embeedNode;
    
    public MetaAnyPEG(SymInfo s){
        super(s);
        embeedNode = new AnyPEG(s);
    }
    public AnyPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}