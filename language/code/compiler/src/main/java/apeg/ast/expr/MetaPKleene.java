package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.PKleene;
import apeg.util.SymInfo;

public class MetaPKleene extends MetaAPEG{

    private PKleene embeedNode;
    
    public MetaPKleene(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new PKleene(s,e.getEmbeedNode());
    }
    public PKleene getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
