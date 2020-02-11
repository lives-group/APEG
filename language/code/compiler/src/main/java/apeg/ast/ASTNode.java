package apeg.ast;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class ASTNode {

    private SymInfo symInfo;
    
    public ASTNode(SymInfo s){
        this.symInfo = s;
    }
    public SymInfo getSymInfo(){
        return symInfo;
    }
    public abstract void accept(Visitor v);

}