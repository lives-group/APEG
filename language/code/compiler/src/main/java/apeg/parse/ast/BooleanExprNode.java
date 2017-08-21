package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BooleanExprNode extends ExprNode {

	private boolean value;
	
	public BooleanExprNode(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
