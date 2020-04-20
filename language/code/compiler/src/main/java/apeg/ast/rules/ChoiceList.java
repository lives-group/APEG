package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.CharInterval;
import apeg.util.SymInfo;

public class ChoiceList extends APEG{

    private CharInterval interval;
    
    public ChoiceList(SymInfo s,CharInterval i){
        super(s);
        this.interval = i;
    }
    public CharInterval getInterval(){
        return interval;
    }
    public void accept(Visitor v){ v.visit(this); }

}