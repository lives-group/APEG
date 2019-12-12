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

public class MetaRulePEG extends MetaAPEG{

    private RulePEG embeedNode;
    
    public MetaRulePEG(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<MetaType,String>> inh,List<Pair<MetaType,MetaExpr>> syn,MetaAPEG peg){
        super(s);
        List<Pair<Type,String>> inh_T3 = new LinkedList<Pair<Type,String>>();
        for(Pair<MetaType,String> i:inh){
            inh_T3.add( new  Pair<Type,String>(i.getLeft().getEmbeedNode(),i.getRight()));
        }
        List<Pair<Type,Expr>> syn_T4 = new LinkedList<Pair<Type,Expr>>();
        for(Pair<MetaType,MetaExpr> i:syn){
            syn_T4.add( new  Pair<Type,Expr>(i.getLeft().getEmbeedNode(),i.getRight().getEmbeedNode()));
        }
        embeedNode = new RulePEG(s,ruleName,anno,inh_T3,syn_T4,peg.getEmbeedNode());
    }
    public RulePEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}