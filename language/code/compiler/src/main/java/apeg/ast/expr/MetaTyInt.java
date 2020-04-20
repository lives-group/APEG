package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyInt;
import apeg.util.SymInfo;

public class MetaTyInt extends MetaType{

    private TyInt embeedNode;
    
    public MetaTyInt(SymInfo s){
        super(s);
        embeedNode = new TyInt(s);
    }
    public TyInt getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}