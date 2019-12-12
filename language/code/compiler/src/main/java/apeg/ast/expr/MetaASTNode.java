package apeg.ast.expr;
import apeg.ast.ASTNode;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class MetaASTNode extends Expr{

    
    public MetaASTNode(SymInfo s){
        super(s);
    }
    public abstract ASTNode getEmbeedNode();

}