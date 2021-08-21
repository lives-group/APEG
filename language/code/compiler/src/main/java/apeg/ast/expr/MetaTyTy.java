package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyMeta;
import apeg.util.SymInfo;

public class MetaTyTy extends MetaType{

  
    public MetaTyTy(SymInfo s){
        super(s);
    }
    
    public void accept(Visitor v){ v.visit(this); }

}
