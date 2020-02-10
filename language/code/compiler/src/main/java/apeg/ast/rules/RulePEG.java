package apeg.ast.rules;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.ast.types.Type;
import apeg.ast.expr.Expr;
import apeg.ast.ASTNode;
import apeg.util.SymInfo;

public class RulePEG extends ASTNode{

    private String ruleName;
    private RulePEG.Annotation anno;
    private List<Pair<Type,String>> inh;
    private List<Expr> syn;
    private APEG peg;
    
    public RulePEG(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<Type,String>> inh,List<Expr> syn,APEG peg){
        super(s);
        this.syn = syn;
        this.inh = inh;
    }
    public String getRuleName(){
        return ruleName;
    }
    public RulePEG.Annotation getAnno(){
        return anno;
    }
    public List<Pair<Type,String>> getInh(){
        return inh;
    }
    public List<Expr> getSyn(){
        return syn;
    }
    public APEG getPeg(){
        return peg;
    }
    public void accept(Visitor v){ v.visit(this); }
    public enum Annotation { MEMOIZE, TRANSIENT, NONE}

}
