package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.util.CharInterval;
import apeg.ast.rules.RangePEG;
import apeg.util.SymInfo;

import java.util.List;

public class MetaRangePEG extends MetaAPEG {

    private RangePEG embeedNode;
    private Expr i,f;
    
    public MetaRangePEG(SymInfo s, CharInterval ci){
        super(s);
        embeedNode = new RangePEG(s,ci);
        i = new CharLit(s,ci.getStart());
        f = new CharLit(s,ci.getEnd());
    }

    public MetaRangePEG(SymInfo s, Expr i, Expr f){
        super(s);
        this.i = i;
        this.f = f;
    }

    public RangePEG getEmbeedNode(){
        return embeedNode;
    }

    public Expr getStartExpr(){ return i;}
    public Expr getEndExpr(){ return f;}

    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        return "'[.." + embeedNode.toString() + "]";
    }
}
