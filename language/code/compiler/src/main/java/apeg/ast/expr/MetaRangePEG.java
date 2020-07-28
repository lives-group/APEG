package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.CharInterval;
import apeg.ast.rules.RangePEG;
import apeg.util.SymInfo;

import java.util.List;

public class MetaRangePEG extends MetaAPEG {

    private RangePEG embeedNode;
    
    public MetaRangePEG(SymInfo s, CharInterval i){
        super(s);
        embeedNode = new RangePEG(s,i);
    }
    public RangePEG getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }

}
