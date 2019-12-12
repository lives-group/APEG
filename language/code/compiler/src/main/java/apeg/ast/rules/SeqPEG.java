package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class SeqPEG extends APEG{

    private APEG[] pegs;
    
    public SeqPEG(SymInfo s,APEG[] p){
        super(s);
        pegs = p;
    }
    public APEG[] getPegs(){
        return pegs;
    }
    public int getSize(){return pegs.length;}
    public APEG getAt(int i){return pegs[i];}
    public void accept(Visitor v){ v.visit(this); }

}