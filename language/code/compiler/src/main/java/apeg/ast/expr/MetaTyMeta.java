package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyMeta;
import apeg.util.SymInfo;

public class MetaTyMeta extends MetaType{

    private TyMeta embeedNode;
    
    public MetaTyMeta(SymInfo s){
        super(s);
        embeedNode = new TyMeta(s);
    }
    public TyMeta getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}