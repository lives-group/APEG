package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class NotPEG extends APEG{

    private APEG pegExp;
    
    public NotPEG(SymInfo s,APEG e){
        super(s);
        this.pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

}