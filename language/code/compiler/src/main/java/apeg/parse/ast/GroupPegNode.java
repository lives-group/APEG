package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

//import java.util.List;
//import apeg.util.Pair;

public class GroupPegNode extends PegNode {

	private String ranges;
	
	public GroupPegNode(String ranges) {
		this.ranges = ranges;
	}
	
	/**
	 * @return a set of ranges 
	 */
	//public List<Pair<Character,Character> > getRanges();
	
	public String getRanges() {
		return ranges;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
