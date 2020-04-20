package apeg.ast.types;
import apeg.visitor.Visitor;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

public abstract class Type extends ASTNode{

    
    public Type(SymInfo s){
        super(s);
    }
    abstract public boolean match(Type t);

}