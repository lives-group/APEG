package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaBoolLit extends MetaExpr{

    private BoolLit embeedNode;
    
    public MetaBoolLit(SymInfo s,boolean value){
        super(s);
        embeedNode = new BoolLit(s,value);
    }
    public BoolLit getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}