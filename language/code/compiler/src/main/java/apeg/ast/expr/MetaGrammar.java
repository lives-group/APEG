package apeg.ast.expr;

import apeg.util.SymInfo;
import apeg.ast.rules.APEG;
import apeg.visitor.Visitor;
import apeg.ast.*;

public class MetaGrammar extends MetaASTNode {

	private Expr listMetaRule;
	
	public MetaGrammar(SymInfo s, Expr listMetaRule) {
            super (s);
            this.listMetaRule = listMetaRule;
	}
	
        public Expr getListMetaRule(){
            return listMetaRule;
        }

        public void accept(Visitor v){
            v.visit(this);
        }

        public String toString(){
            return "(metagrammar " + listMetaRule.toString() + ")";
        }
}
