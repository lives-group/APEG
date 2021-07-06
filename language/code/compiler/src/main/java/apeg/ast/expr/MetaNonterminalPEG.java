package apeg.ast.expr;
import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.ast.rules.NonterminalPEG;
import apeg.util.SymInfo;

public class MetaNonterminalPEG extends MetaAPEG{

    private NonterminalPEG embeedNode;
    private List<Expr> args
    private Expr name
    
    public MetaNonterminalPEG(SymInfo s,Expr name,List<Expr> args){
        super(s);
        List<Expr> args_T2 = new LinkedList<Expr>();
        for(MetaExpr i:args){
            args_T2.add(i.getEmbeedNode());
        }
        embeedNode = new NonterminalPEG(s,name,args_T2);
    }
    public NonterminalPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
