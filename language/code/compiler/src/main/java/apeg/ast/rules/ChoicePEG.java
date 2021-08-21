package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class ChoicePEG extends APEG{

    private APEG leftPeg;
    private APEG rightPeg;
    
    public ChoicePEG(SymInfo s,APEG leftPeg,APEG rightPeg){
        super(s);
        this.leftPeg = leftPeg;
        this.rightPeg = rightPeg;
    }
    public APEG getLeftPeg(){
        return leftPeg;
    }
    public APEG getRightPeg(){
        return rightPeg;
    }
    public void accept(Visitor v){ v.visit(this); }
    
    
    public String toString(){
        return "(/ " + leftPeg.toString() + " " + rightPeg.toString() + ")";
    } 

}
