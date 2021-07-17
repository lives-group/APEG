package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.ChoicePEG;
import apeg.util.SymInfo;

public class MetaChoicePEG extends MetaAPEG{

    private Expr leftPeg,rightPeg;
    
    public MetaChoicePEG(SymInfo s,Expr leftPeg,Expr rightPeg){
        super(s);
        this.leftPeg = leftPeg;
        this.rightPeg = rightPeg;
    }
    public Expr getRightPeg(){
        return rightPeg;
    }
    public Expr getLeftPeg(){
        return leftPeg;
    }
    public void accept(Visitor v){ v.visit(this); }

}