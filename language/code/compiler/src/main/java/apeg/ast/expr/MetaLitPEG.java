package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.LitPEG;
import apeg.util.SymInfo;

public class MetaLitPEG extends MetaAPEG{

    private LitPEG embeedNode;
    
    public MetaLitPEG(SymInfo s,String lit){
        super(s);
        embeedNode = new LitPEG(s,lit);
    }
    public LitPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}