package apeg.ast.rules;

import java.util.List;

import apeg.visitor.Visitor;
import apeg.util.CharInterval;
import apeg.util.SymInfo;

public class RangePEG extends APEG {

    private CharInterval interval;
    
    public RangePEG(SymInfo s, CharInterval i){
        super(s);
        this.interval = i;
    }
    public CharInterval getInterval(){
        return interval;
    }
    public void accept(Visitor v){ v.visit(this); }
    
    public String toString(){
        return "(.. " + interval.toString() + ")"; 
    } 

}
