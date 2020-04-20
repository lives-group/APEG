package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyGrammar;
import apeg.util.SymInfo;

public class MetaTyGrammar extends MetaType{

    private TyGrammar embeedNode;
    
    public MetaTyGrammar(SymInfo s){
        super(s);
        embeedNode = new TyGrammar(s);
    }
    public TyGrammar getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}