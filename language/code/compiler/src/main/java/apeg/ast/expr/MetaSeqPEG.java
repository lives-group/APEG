package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.APEG;
import apeg.ast.rules.SeqPEG;
import apeg.util.SymInfo;

public class MetaSeqPEG extends MetaAPEG{

    private SeqPEG embeedNode;
    
    public MetaSeqPEG(SymInfo s,MetaAPEG[] p){
        super(s);
        APEG[] p_T1 = new APEG[p.length];
        for(int i = 0;i < p_T1.length;i++){
            p_T1[i] = p[i].getEmbeedNode();
        }
        embeedNode = new SeqPEG(s,p_T1);
    }
    public SeqPEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}