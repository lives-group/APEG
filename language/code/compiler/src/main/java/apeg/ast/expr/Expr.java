package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

public abstract class Expr extends ASTNode{

    
    public Expr(SymInfo s){
        super(s);
    }

}