package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaFloatLit extends MetaExpr{

    private FloatLit embeedNode;
    
    public MetaFloatLit(SymInfo s,float value){
        super(s);
        embeedNode = new FloatLit(s,value);
    }
    public FloatLit getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}