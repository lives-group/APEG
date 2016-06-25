package apeg.parse.ast;

public interface ChoicePegNode extends PegNode {
	/**
	 * @return first parsing expression of the choice
	 */
	public PegNode getLeftPeg();
	/**
	 * @return last parsing expression of the choice
	 */
	public PegNode getRightPeg();
}
