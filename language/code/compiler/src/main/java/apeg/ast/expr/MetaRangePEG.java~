package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.CharInterval;
import apeg.ast.rules.ChoiceList;
import apeg.util.SymInfo;

public class MetaChoiceList extends MetaAPEG{

    private ChoiceList embeedNode;
    
    public MetaChoiceList(SymInfo s,CharInterval i){
        super(s);
        embeedNode = new ChoiceList(s,i);
    }
    public ChoiceList getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}