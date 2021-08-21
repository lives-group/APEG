package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class LambdaPEG extends APEG {
    
    public LambdaPEG(SymInfo s){
        super(s);
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        return "Lam";
    } 
}
