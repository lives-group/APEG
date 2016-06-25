package apeg.parse.ast.impl;

import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class GroupPegNodeImpl implements GroupPegNode {

	private String ranges;
	
	public GroupPegNodeImpl(String ranges) {
		this.ranges = ranges;
	}
	
	@Override
	public String getRanges() {
		return ranges;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
