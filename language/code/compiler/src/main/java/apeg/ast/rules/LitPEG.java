package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class LitPEG extends APEG{

    private String lit;
    
    public LitPEG(SymInfo s,String lit){
        super(s);
    }
    public String getLit(){
        return lit;
    }
    public void accept(Visitor v){ v.visit(this); }

}