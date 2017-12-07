package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class OrExprNode extends LogicalBinOp {
	

	public OrExprNode(ExprNode left, ExprNode right) {
		super(left,right);
	}

	public String getOpName(){
		return "OR";
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
