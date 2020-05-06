package apeg.ast.types;
import apeg.visitor.Visitor;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

public abstract class Type extends ASTNode{

    private String name;
    
    public Type(SymInfo s, String name){
        super(s);
        this.name = name;
    }
    
    abstract public boolean match(Type t);

    public String getName() {
	return name;
    }

}
