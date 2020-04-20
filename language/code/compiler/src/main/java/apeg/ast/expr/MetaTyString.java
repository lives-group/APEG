package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyString;
import apeg.util.SymInfo;

public class MetaTyString extends MetaType{

    private TyString embeedNode;
    
    public MetaTyString(SymInfo s){
        super(s);
        embeedNode = new TyString(s);
    }
    public TyString getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}