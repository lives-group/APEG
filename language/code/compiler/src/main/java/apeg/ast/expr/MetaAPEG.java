package apeg.ast.expr;
import apeg.ast.MetaASTNode;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class MetaAPEG extends MetaASTNode{

    
    public MetaAPEG(SymInfo s){
        super(s);
    }
    public abstract APEG getEmbeedNode();

}