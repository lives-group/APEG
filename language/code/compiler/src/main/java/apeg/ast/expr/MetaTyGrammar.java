package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyGrammar;
import apeg.util.SymInfo;

public class MetaTyGrammar extends MetaType{

    public MetaTyGrammar(SymInfo s){
        super(s);
    }
    
    public void accept(Visitor v){ v.visit(this); }

}