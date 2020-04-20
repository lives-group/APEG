package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyLang;
import apeg.util.SymInfo;

public class MetaTyLang extends MetaType{

    private TyLang embeedNode;
    
    public MetaTyLang(SymInfo s){
        super(s);
        embeedNode = new TyLang(s);
    }
    public TyLang getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}