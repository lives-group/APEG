package apeg.ast.expr;
import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.ast.rules.NonterminalPEG;
import apeg.util.SymInfo;

public class MetaNonterminalPEG extends MetaAPEG{

    private Expr name,args[];
    
    public MetaNonterminalPEG(SymInfo s,Expr name,Expr args[]){
        super(s);
        this.name = name;
        this.args = args;
    }
    public Expr getName(){
        return name;
    }
    public Expr[] getArgs(){
        return args;
    }
    
    public void accept(Visitor v){ v.visit(this); }

}
