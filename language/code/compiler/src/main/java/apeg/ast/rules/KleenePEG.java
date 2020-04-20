package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class KleenePEG extends APEG{

    private APEG pegExp;
    
    public KleenePEG(SymInfo s,APEG e){
        super(s);
        this.pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

}
