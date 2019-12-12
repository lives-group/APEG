package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaStrLit extends MetaExpr{

    private StrLit embeedNode;
    
    public MetaStrLit(SymInfo s,String value){
        super(s);
        embeedNode = new StrLit(s,value);
    }
    public StrLit getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}