package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaAttribute extends MetaExpr{

    private Attribute embeedNode;
    
    public MetaAttribute(SymInfo s,String name){
        super(s);
        embeedNode = new Attribute(s,name);
    }
    public Attribute getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}