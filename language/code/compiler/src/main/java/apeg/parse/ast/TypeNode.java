package apeg.parse.ast;

public abstract class TypeNode extends ASTNode {

	private String type;
	
	public TypeNode(String type) {
		this.type = type;
	}
	
	public String getName() {
		return type;
	}

	public boolean match(TypeNode type) {
		return this.type.equals(type.getName());
	}
}
