package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.operators.BinaryOP;
import apeg.ast.expr.operators.Not;
import apeg.util.SymInfo;

public class MetaNot extends MetaExpr{

    private Not embeedNode;
    private MetaExpr e;
    
    public MetaNot(SymInfo s,MetaExpr e){
        super(s);
        this.e = e;
        
        embeedNode = new Not(s,e.getEmbeedNode());
        
    }
    public Not getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}