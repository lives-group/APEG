package apeg.ast.expr;
import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.ast.rules.NonterminalPEG;
import apeg.util.SymInfo;

public class MetaNonterminalPEG extends MetaAPEG{

    private List<Expr> args;
    private Expr name;
    
    public MetaNonterminalPEG(SymInfo s,Expr name,List<Expr> args){
        super(s);
        this.args = agrs;
        this.name = name;
       
    }
    
    public List<Expr> getExprArgs(){ return args;}
    public Expr getExprName(){ return name;}
    
    public void accept(Visitor v){ v.visit(this); }

}
