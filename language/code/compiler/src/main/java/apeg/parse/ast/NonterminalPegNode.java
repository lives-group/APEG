package apeg.parse.ast;

import java.util.List;

public interface NonterminalPegNode extends PegNode {
	/**
	 * @return nonterminal name
	 */
	public String getName();
	/**
	 * @return set of attributes values
	 */
	public List<ExprNode> getExprs();
}
