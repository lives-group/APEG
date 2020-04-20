package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaCharLit extends MetaExpr{

    private CharLit embeedNode;
    
    public MetaCharLit(SymInfo s,char value){
        super(s);
        embeedNode = new CharLit(s,value);
    }
    public CharLit getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}