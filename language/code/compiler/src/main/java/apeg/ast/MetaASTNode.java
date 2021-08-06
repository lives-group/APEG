package apeg.ast;
import apeg.ast.expr.Expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class MetaASTNode extends Expr{

    
    public MetaASTNode(SymInfo s){
        super(s);
    }
   // public abstract ASTNode getEmbeedNode();

}
