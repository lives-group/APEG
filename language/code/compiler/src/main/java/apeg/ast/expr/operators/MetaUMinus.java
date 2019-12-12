package apeg.ast.expr.operators;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class MetaUMinus extends BinaryOP{

    private UMinus embeedNode;
    
    public MetaUMinus(SymInfo s,Expr l,Expr r,Expr e){
        super(s,l,r);
        embeedNode = new UMinus(s,e);
    }
    public UMinus getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}