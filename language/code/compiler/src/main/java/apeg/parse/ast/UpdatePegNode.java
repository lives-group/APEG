package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class UpdatePegNode extends PegNode {
	
	private List<AssignmentNode> assigs;
	
	public UpdatePegNode(List<AssignmentNode> assigs) {
		this.assigs = assigs;
	}

	public List<AssignmentNode> getAssignments() {
		return assigs;
	}

	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
