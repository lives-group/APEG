package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class CallExprNode extends ExprNode {

	private String name;
	private List<ExprNode> params;
	
	public CallExprNode(String name, List<ExprNode> param) {
		this.name = name;
		this.params = param;
	}
	
	public String getName() {
		return name;
	}

	public List<ExprNode> getParameters() {
		return params;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
