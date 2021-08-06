package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.AnyPEG;
import apeg.util.SymInfo;

public class MetaAnyPEG extends MetaAPEG{
 
    public MetaAnyPEG(SymInfo s){
        super(s);
    }
    public void accept(Visitor v){ v.visit(this); }

}