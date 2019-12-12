package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.BindPEG;
import apeg.util.SymInfo;

public class MetaBindPEG extends MetaAPEG{

    private BindPEG embeedNode;
    
    public MetaBindPEG(SymInfo s,String attribute,MetaExpr e){
        super(s);
        embeedNode = new BindPEG(s,attribute,e.getEmbeedNode());
    }
    public BindPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}