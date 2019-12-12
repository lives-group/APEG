package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyBool;
import apeg.util.SymInfo;

public class MetaTyBool extends MetaType{

    private TyBool embeedNode;
    
    public MetaTyBool(SymInfo s){
        super(s);
        embeedNode = new TyBool(s);
    }
    public TyBool getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}