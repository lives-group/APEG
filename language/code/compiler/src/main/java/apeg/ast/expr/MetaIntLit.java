package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaIntLit extends MetaExpr{

    private IntLit embeedNode;
    
    public MetaIntLit(SymInfo s,int value){
        super(s);
        embeedNode = new IntLit(s,value);
    }
    public IntLit getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}