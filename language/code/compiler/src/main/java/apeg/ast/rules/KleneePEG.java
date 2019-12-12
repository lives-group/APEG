package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class KleneePEG extends APEG{

    private APEG pegExp;
    
    public KleneePEG(SymInfo s,APEG e){
        super(s);
        pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

}