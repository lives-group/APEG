package apeg.parse.ast;

import apeg.parse.ast.visitor.ElementVisitor;

public abstract class ASTNode implements ElementVisitor {
	
	protected int line, colunm; // line and column position 
	protected char character; // character of the respective position
	
	public char getChar() {
		return character;
	}

	public void setChar(char character) {
		this.character = character;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColunm() {
		return colunm;
	}

	public void setColunm(int colunm) {
		this.colunm = colunm;
	}	
}
