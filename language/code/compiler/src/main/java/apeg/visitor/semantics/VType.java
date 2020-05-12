package apeg.visitor.semantics;

import apeg.ast.ASTNode;
import apeg.ast.types.Type;
import apeg.util.SymInfo;

public abstract  class VType{

    private String name;
    
    protected VType( String name){
        this.name = name;
    }
   
    abstract public boolean match(VType t);

    public String getName() {
	return name;
    }
    
    

}
