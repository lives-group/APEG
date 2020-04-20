package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.KleenePEG;
import apeg.util.SymInfo;

public class MetaKleenePEG extends MetaAPEG{

    private KleenePEG embeedNode;
    
    public MetaKleenePEG(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new KleenePEG(s,e.getEmbeedNode());
    }
    public KleenePEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
