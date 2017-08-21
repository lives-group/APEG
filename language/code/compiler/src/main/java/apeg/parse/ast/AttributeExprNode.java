package apeg.parse.ast;

import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AttributeExprNode extends ExprNode {

	private String name;
	
	public AttributeExprNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
