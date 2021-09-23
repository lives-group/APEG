package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class AnyPEG extends APEG{

    
    public AnyPEG(SymInfo s){
        super(s);
    }
    public void accept(Visitor v){ v.visit(this); }
    
    
    public String toString(){
        return "_";
    } 

}
