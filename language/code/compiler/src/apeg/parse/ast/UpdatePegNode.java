package apeg.parse.ast;

import java.util.List;

public interface UpdatePegNode extends PegNode {
	/**
	 * @return set of assignments
	 */
	public List<AssignmentNode> getAssignments();

}
