package apeg.ast.expr;
import apeg.ast.rules.APEG;
import java.util.List;
import java.util.LinkedList;
import apeg.visitor.Visitor;
import apeg.util.Pair;
import apeg.ast.rules.UpdatePeg;
import apeg.util.SymInfo;

public class MetaUpdatePeg extends MetaAPEG{

    private UpdatePeg embeedNode;
    
    public MetaUpdatePeg(SymInfo s,List<Pair<MetaAttribute,MetaExpr>> assigs){
        super(s);
        List<Pair<Attribute,Expr>> assigs_T1 = new LinkedList<Pair<Attribute,Expr>>();
        for(Pair<MetaAttribute,MetaExpr> i:assigs){
            assigs_T1.add( new  Pair<Attribute,Expr>(i.getLeft().getEmbeedNode(),i.getRight().getEmbeedNode()));
        }
        embeedNode = new UpdatePeg(s,assigs_T1);
    }
    public UpdatePeg getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}