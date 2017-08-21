package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NonterminalPegNode extends PegNode {
	
	private String name;
	private List<ExprNode> attrs;
	
	public NonterminalPegNode(String name, List<ExprNode> attrs) {
		this.name = name;
		this.attrs = attrs;
	}

	public String getName() {
		return name;
	}

	public List<ExprNode> getExprs() {
		return attrs;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
