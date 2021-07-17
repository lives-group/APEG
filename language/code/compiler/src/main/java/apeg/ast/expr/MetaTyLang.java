package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyLang;
import apeg.util.SymInfo;

public class MetaTyLang extends MetaType{
  
    public MetaTyLang(SymInfo s){
        super(s);
    }
    
    public void accept(Visitor v){ v.visit(this); }

}