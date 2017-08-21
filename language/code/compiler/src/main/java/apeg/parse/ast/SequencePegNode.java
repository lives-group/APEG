package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class SequencePegNode extends PegNode {

	private List<PegNode> pegs;
	
	public SequencePegNode(List<PegNode> pegs) {
		this.pegs = pegs;
	}
	
	public List<PegNode> getPegs() {
		return pegs;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
