package apeg.ast;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

import java.io.Serializable;

public abstract class ASTNode implements Serializable{

    private SymInfo symInfo;
    
    public ASTNode(SymInfo s){
        this.symInfo = s;
    }
    public SymInfo getSymInfo(){
        return symInfo;
    }
    public abstract void accept(Visitor v);

}