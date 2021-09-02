package apeg.ast.expr;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.rules.BindPEG;
import apeg.util.SymInfo;

import apeg.ast.expr.Attribute;

public class MetaBindPEG extends MetaAPEG{

    private Expr att,p;
    
    public MetaBindPEG(SymInfo s,Expr att,Expr p){
        super(s);
        this.p = p;
        this.att = att;
    }
    public Expr getExprAtt(){
        return att;
    }
    public Expr getExprP(){
        return p;
    }
    public void accept(Visitor v){ v.visit(this); }

    public String toString(){
        return "'(= " + att.toString() + " " + p.toString() + ")";
    }
}
