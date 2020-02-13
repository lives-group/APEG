package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class PKleene extends APEG{

    private APEG pegExp;
    
    public PKleene(SymInfo s,APEG e){
        super(s);
        this.pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

}
