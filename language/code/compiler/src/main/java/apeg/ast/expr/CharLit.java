package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class CharLit extends Expr{

    private char value;
    
    public CharLit(SymInfo s,char value){
        super(s);
        this.value = value;
    }
    public char getValue(){
        return value;
    }
    public void accept(Visitor v){ v.visit(this); }

}