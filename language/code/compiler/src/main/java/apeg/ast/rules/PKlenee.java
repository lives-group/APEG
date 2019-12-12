package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class PKlenee extends APEG{

    private APEG pegExp;
    
    public PKlenee(SymInfo s,APEG e){
        super(s);
        pegExp = e;
    }
    public APEG getPegExp(){
        return pegExp;
    }
    public void accept(Visitor v){ v.visit(this); }

}