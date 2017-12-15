package apeg.parse.ast;

public abstract class LogicalBinExpr extends ExprNode {
	
	private ExprNode left, right;
	
	public LogicalBinExpr(ExprNode left, ExprNode right) {
		this.left = left;
		this.right = right;
	}
    
	public abstract String getOpName();
	
	public ExprNode getLeftExpr() {
		return left;
	}

	public ExprNode getRightExpr() {
		return right;
	}

}
