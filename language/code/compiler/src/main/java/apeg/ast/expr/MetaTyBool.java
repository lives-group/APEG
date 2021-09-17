package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyBool;
import apeg.util.SymInfo;

public class MetaTyBool extends MetaType{

    public MetaTyBool(SymInfo s){
        super(s);
    }
    
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "'bool"; }
}
