package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class AndPEG extends APEG{

    private APEG pegExp;
    
    public AndPEG(SymInfo s,APEG e){
        super(s);
        this.pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        return "(& " + pegExp.toString()+ ")";
    } 
}
