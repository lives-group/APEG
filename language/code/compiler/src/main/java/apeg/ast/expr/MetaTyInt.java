package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyInt;
import apeg.util.SymInfo;

public class MetaTyInt extends MetaType{

     public MetaTyInt(SymInfo s){
        super(s);
    }
    
    public void accept(Visitor v){ v.visit(this); }

}