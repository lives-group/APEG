package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

public abstract class APEG extends ASTNode{

    
    public APEG(SymInfo s){
        super(s);
    }

}