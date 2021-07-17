package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyChar;
import apeg.util.SymInfo;

public class MetaTyChar extends MetaType{
  
    public MetaTyChar(SymInfo s){
        super(s);
     }
    
    public void accept(Visitor v){ v.visit(this); }

}