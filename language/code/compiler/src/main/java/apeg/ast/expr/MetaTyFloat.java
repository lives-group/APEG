package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyFloat;
import apeg.util.SymInfo;

public class MetaTyFloat extends MetaType{

    public MetaTyFloat(SymInfo s){
        super(s);
    }
        
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){ return "'float"; }
}
