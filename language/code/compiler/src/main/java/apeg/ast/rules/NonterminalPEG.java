package apeg.ast.rules;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.ast.expr.Expr;
import apeg.util.SymInfo;

public class NonterminalPEG extends APEG{

    private String name;
    private List<Expr> args;
    
    public NonterminalPEG(SymInfo s,String name,List<Expr> args){
        super(s);
    }
    public String getName(){
        return name;
    }
    public List<Expr> getArgs(){
        return args;
    }
    public void accept(Visitor v){ v.visit(this); }

}