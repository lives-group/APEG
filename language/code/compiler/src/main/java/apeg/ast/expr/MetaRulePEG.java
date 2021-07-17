package apeg.ast.expr;

import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.ast.types.Type;
import apeg.ast.rules.APEG;
import apeg.ast.rules.RulePEG;
import apeg.util.SymInfo;
import apeg.ast.*;

public class MetaRulePEG extends MetaASTNode{

     private Expr ruleName,anno,inh,syn,peg;
    
    public MetaRulePEG(SymInfo s,Expr ruleName,Expr anno,Expr inh,Expr syn,Expr peg){
        super(s);
        this.ruleName = ruleName;
        this.anno = anno;
        this.inh = inh;
        this.syn = syn;
        this.peg = peg;
    }
    public Expr getRuleName(){
        return ruleName;
    }
    public Expr getAnno(){
        return anno;
    }
    public Expr getInh(){
        return inh;
    }
    public Expr getSyn(){
        return syn;
    }
    public Expr getPeg(){
        return peg;
    }
    public void accept(Visitor v){ v.visit(this); }

}