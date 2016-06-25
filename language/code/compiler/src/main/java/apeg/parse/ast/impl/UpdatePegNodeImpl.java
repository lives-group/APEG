package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class UpdatePegNodeImpl implements UpdatePegNode {
	
	private List<AssignmentNode> assigs;
	
	public UpdatePegNodeImpl(List<AssignmentNode> assigs) {
		this.assigs = assigs;
	}

	@Override
	public List<AssignmentNode> getAssignments() {
		return assigs;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
