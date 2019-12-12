package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyFloat;
import apeg.util.SymInfo;

public class MetaTyFloat extends MetaType{

    private TyFloat embeedNode;
    
    public MetaTyFloat(SymInfo s){
        super(s);
        embeedNode = new TyFloat(s);
    }
    public TyFloat getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}