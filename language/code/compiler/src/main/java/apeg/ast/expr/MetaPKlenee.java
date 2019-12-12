package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.PKlenee;
import apeg.util.SymInfo;

public class MetaPKlenee extends MetaAPEG{

    private PKlenee embeedNode;
    
    public MetaPKlenee(SymInfo s,MetaAPEG e){
        super(s);
        embeedNode = new PKlenee(s,e.getEmbeedNode());
    }
    public PKlenee getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}