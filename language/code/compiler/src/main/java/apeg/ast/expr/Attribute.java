package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class Attribute extends Expr{

    private String name;
    
    public Attribute(SymInfo s,String name){
        super(s);
        this.name =name;
    }
    public String getName(){
        return name;
    }
    public void accept(Visitor v){ v.visit(this); }
    
    public String toString() {return getName();}
}