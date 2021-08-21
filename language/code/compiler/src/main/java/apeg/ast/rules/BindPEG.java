package apeg.ast.rules;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

import apeg.ast.expr.Attribute;

public class BindPEG extends APEG{

    private APEG peg;
    private Attribute attribute;
    
    public BindPEG(SymInfo s, Attribute attribute,APEG peg){
       super(s);
       this.peg =peg;
       this.attribute = attribute;
    }
    public APEG getExpr(){
        return peg;
    }
    public Attribute getAttribute(){
        return attribute;
    }
    public void accept(Visitor v){ v.visit(this); }
    
    
    public String toString(){
        return "(= " +attribute.toString()+ " "+ peg.toString()+ ")";
    } 

}
