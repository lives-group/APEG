package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AndExprNode extends LogicalBinOp {

	
	public AndExprNode(ExprNode left, ExprNode right) {
		super(left,right);
	}
	
	public String getOpName(){
		return "AND" ; 
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
