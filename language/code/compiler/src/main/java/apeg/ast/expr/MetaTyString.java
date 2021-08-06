package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.util.Pair;
import apeg.visitor.Visitor;
import apeg.ast.types.TyString;
import apeg.util.SymInfo;

public class MetaTyString extends MetaType{

  public MetaTyString(SymInfo s){
        super(s);
    }
        
    public void accept(Visitor v){ v.visit(this); }

}