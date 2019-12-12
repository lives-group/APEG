package apeg.ast.expr;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

public class MetaVar extends MetaExpr{

    private String name;
    private Expr embeedNode;
    
    public MetaVar(SymInfo s,String name){
        super(s);
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public Expr getEmbeedNode(){
        return embeedNode;
    }
    public void accept(Visitor v){ v.visit(this); }
    public void setEmbeedNode(Expr embeedNode){
        this.embeedNode = embeedNode;
    }

}