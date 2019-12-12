package apeg.ast.expr;
import apeg.ast.types.Type;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public abstract class MetaType extends Expr{

    
    public MetaType(SymInfo s){
        super(s);
    }
    public abstract Type getEmbeedNode();

}