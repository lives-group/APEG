package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyChar;
import apeg.util.SymInfo;

public class MetaTyChar extends MetaType{

    private TyChar embeedNode;
    
    public MetaTyChar(SymInfo s){
        super(s);
        embeedNode = new TyChar(s);
    }
    public TyChar getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}