package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.ast.expr.operators.BinaryOP;
import apeg.ast.expr.operators.Not;
import apeg.util.SymInfo;
import apeg.ast.MetaASTNode;

public class MetaNot extends MetaExpr{

    private Not embeedNode;
    private Expr e;
    
    public MetaNot(SymInfo s,Expr e){
        super(s);
        this.e = e;
        if(e istanceof MetaASTNode){
           embeedNode = new Not(s,e.getEmbeedNode());
        }
    }
    
    public Not getEmbeedNode(){
        return embeedNode;
    }
    
    public Expr getPegExpr(){ return e;}
    
    public void accept(Visitor v){ v.visit(this); }

}
