package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.PegNode;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class SequencePegNodeImpl implements SequencePegNode {

	private List<PegNode> pegs;
	
	public SequencePegNodeImpl(List<PegNode> pegs) {
		this.pegs = pegs;
	}
	
	@Override
	public List<PegNode> getPegs() {
		return pegs;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
