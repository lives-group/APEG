package apeg.parse.ast;

import java.util.List;

public interface SequencePegNode extends PegNode {
	/**
	 * @return set of parsing expressions of the sequence
	 */
	public List<PegNode> getPegs();
	

}
