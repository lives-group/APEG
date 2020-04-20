package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.operators.BinaryOP;
import apeg.ast.expr.operators.UMinus;
import apeg.util.SymInfo;

public class MetaUMinus extends MetaExpr{

    private UMinus embeedNode;
    private MetaExpr e;
    
    public MetaUMinus(SymInfo s,MetaExpr e){
        super(s);
        this.e =e;
        
        embeedNode = new UMinus(s,e.getEmbeedNode());
    }
    public UMinus getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}