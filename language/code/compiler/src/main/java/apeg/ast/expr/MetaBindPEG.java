package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.BindPEG;
import apeg.util.SymInfo;

import apeg.ast.expr.Attribute;

public class MetaBindPEG extends MetaAPEG{

    private BindPEG embeedNode;
    
    public MetaBindPEG(SymInfo s, Attribute attribute,MetaAPEG p){
        super(s);
        embeedNode = new BindPEG(s,attribute,p.getEmbeedNode());
    }
    public BindPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
