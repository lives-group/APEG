package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class AttributeGrammar extends Attribute {
    
    public AttributeGrammar(SymInfo s){
        super(s, "$g");
    }

    public void accept(Visitor v){ v.visit(this); }

}
