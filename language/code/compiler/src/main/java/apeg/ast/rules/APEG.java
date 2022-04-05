package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

import java.io.Serializable;

public abstract class APEG extends ASTNode implements Serializable{

    
    public APEG(SymInfo s){
        super(s);
    }

}