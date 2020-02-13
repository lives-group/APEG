package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.KleneePEG;
import apeg.util.SymInfo;

public class MetaKleneePEG extends MetaAPEG{

    private KleneePEG embeedNode;
    
    public MetaKleneePEG(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new KleneePEG(s,e.getEmbeedNode());
    }
    public KleneePEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}