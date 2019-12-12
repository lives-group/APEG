package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.types.Type;
import apeg.ast.types.TyMap;
import apeg.util.SymInfo;

public class MetaTyMap extends MetaType{

    private TyMap embeedNode;
    
    public MetaTyMap(SymInfo s,MetaType tyParameter){
        super(s);
        embeedNode = new TyMap(s,tyParameter.getEmbeedNode());
    }
    public TyMap getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}